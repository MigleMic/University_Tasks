package kt.golaycodekt;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

//Pagalbines funkcijos
public class AdditionalResources {

    //Vektoriaus uzpildymas 0
    static int[] nullVector() {
        int[] vector = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        return vector;
    }

    //Originalios zinutes gavimas
    //Ieitis - vektorius, kurio pirmi 12 simboliu yra musu dekoduotas pranesimas
    static int[] getOriginalMessage(int[] vector) {
        int[] originalVector = new int[vector.length / 2];
        for (int i = 0; i < 12; i++) {
            originalVector[i] = vector[i];
        }
        return originalVector;
    }


    //Uzpildyti vektoriaus likusias vietas 0
    //Ieitis - vektorius, kurio tuscios vietos yra uzpildomos 0
    static void informationNormalization(String[] information) {
        for (int i = 0; i < information.length; i++) {
            int length = information[i].length();
            for (int j = 0; j < 12 - length; j++) {
                information[i] += "0";
            }
        }
    }

    //Pakeiciame bituko reiksme priesinga
    //Ieitis - vektorius ir pozicija, kuri bus pakeiciama priesinge reiksme vektoriuje
    static void flipping(int[] vector, int position) {
        if (position < vector.length) {
            if (vector[position - 1] == 1)
                vector[position - 1] = 0;
            else
                vector[position - 1] = 1;
        }
    }

    //Skaitini masyva paverciame i string masyva, kad galetume ji atvaizduoti naudotojui
    //Ieitis - skaiciu masyvas, kuris bus paverstas i string
    public static String intCodeToString(int[] code) {
        StringBuilder binaryString = new StringBuilder();
        for (int num : code)
            binaryString.append(num);

        String showCode = binaryString.toString().replaceAll("(.)", "$1 ");

        return showCode;
    }

    public static String vectorToString(int[] code) {
        StringBuilder vector = new StringBuilder();
        for (int i = 0; i < 23; i++)
            vector.append(code[i]);

        String showCode = vector.toString().replaceAll("(.)", "$1 ");

        return showCode;
    }


    //Ieitis - bitu masyvas, kuri konvertuosime i string
    static String[] convertBytesToStrings(byte[] imageBytes) {
        int numStrings = (int) Math.ceil((double) imageBytes.length * 8 / 12);
        String[] result = new String[numStrings];

        int index = 0;
        int stringIndex = 0;
        StringBuilder sb = new StringBuilder();

        for (byte b : imageBytes) {
            for (int i = 7; i >= 0; i--) {
                if (index % 12 == 0 && index != 0) {
                    result[stringIndex++] = sb.toString();
                    sb.setLength(0);
                }

                if (((b >> i) & 1) == 1) {
                    sb.append('1');
                } else {
                    sb.append('0');
                }
                index++;
            }
        }

        // Jei trumpiau nei 12, papildome 0
        if (sb.length() > 0) {
            while (sb.length() < 12) {
                sb.append('0');
            }
            result[stringIndex] = sb.toString();
        }

        return result;
    }

    public static byte[] convertStringsToByteArray(String[] strings) {
        if (strings == null || strings.length == 0) {
            return new byte[0]; // Return an empty byte array if the input string array is empty or null
        }

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            for (String str : strings) {
                byte[] strBytes = str.getBytes(StandardCharsets.UTF_8); // Convert each string to bytes using UTF-8 encoding
                outputStream.write(strBytes); // Write the bytes to the output stream
            }
            return outputStream.toByteArray(); // Get the resulting byte array from the output stream
        } catch (IOException e) {
            System.out.println("Error converting strings to byte array: " + e.getMessage());
            return new byte[0]; // Return an empty byte array if an error occurs during conversion
        }
    }

    //Ieitis - paveikslelis, kuri konvertuosime i bitu masyva
    public static byte[] convertImageToBytes(BufferedImage image){
        try{
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "bmp", baos);
            baos.flush();
            byte[] bytes = baos.toByteArray();
            baos.close();
            return bytes;
        }
        catch(IOException e){
            System.out.println("Encountered error when processing an image");
            return new byte[0];
        }
    }

    //Ieitis - bitu masyvas, kuri konvertuosime i paveiksleli
//    public static Image convertBytesToImage(byte[] bytes){
//        try{
//            ByteArrayInputStream is = new ByteArrayInputStream(bytes);
//            System.out.println("ILGIS " + bytes.length);
//            BufferedImage bufferedImage = ImageIO.read(is);
//            return SwingFXUtils.toFXImage(bufferedImage, null);
//        }catch (IOException e){
//            System.out.println("Encountered error when converting to an image");
//            return null;
//        }
//    }

    public static Image convertBytesToImage(byte[] bytes) {
        try {
            if (bytes == null || bytes.length == 0) {
                System.out.println("Byte array is empty or null.");
                return null;
            }

            ByteArrayInputStream is = new ByteArrayInputStream(bytes);
            System.out.println("Byte array length: " + bytes.length);

            BufferedImage bufferedImage = ImageIO.read(is);

            if (bufferedImage == null) {
                System.out.println("BufferedImage is null. Failed to read image from byte array.");
                return null;
            }

            return SwingFXUtils.toFXImage(bufferedImage, null);
        } catch (IOException e) {
            System.out.println("Encountered error when converting to an image: " + e.getMessage());
            return null;
        }
    }

    public static BufferedImage convertIntArrayToImage(int[][] binaryIntArray) {
        int height = binaryIntArray.length;
        int width = 24;

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < height; y++) {
            int rowWidth = Math.min(binaryIntArray[y].length, width); // Determine the actual width of the current row

            for (int x = 0; x < width; x++) {
                if (x < rowWidth) {
                    int color = binaryIntArray[y][x] == 1 ? 0xFF000000 : 0xFFFFFFFF; // Black for 1, White for 0
                    bufferedImage.setRGB(x, y, color);
                } else {
                    // Set default color to white for the remaining pixels in the row
                    bufferedImage.setRGB(x, y, 0xFFFFFFFF);
                }
            }
        }

        return bufferedImage;
    }


    public static byte[] convertToByteArray(int[][] pixelValues) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        for (int[] row : pixelValues) {
            for (int pixel : row) {
                // Convert pixel to byte (adjust this based on your pixel format)
                baos.write((byte) pixel);
            }
        }

        return baos.toByteArray();
    }

    public static byte[] convertStringToByte2(String[] stringArray){
        byte[] byteArray = new byte[stringArray.length];

        for (int i = 0; i < stringArray.length; i++){
            byte value = (byte) Integer.parseInt(stringArray[i], 2);
            byteArray[i] = value;
        }
        return byteArray;
    }

}
