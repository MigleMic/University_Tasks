package kt.golaycodekt;


import java.util.ArrayList;

public class MatrixConvertions {
    //Dvimacio masyvo konvertavimas i string masyva
    //Ieitis - dvimatis masyvas, kuris bus paverciamas i vienmati
    static String[] convertToStringArray(int[][] array) {
        int rows = array.length;
        int columns = array[0].length;

        String[] stringArray = new String[rows];
        for(int i = 0; i < rows; i++){
            StringBuilder sb = new StringBuilder();
            for(int j = 0; j < 12; j++){
                sb.append(array[i][j]);
            }
            sb.setLength(sb.length() - 5);

            stringArray[i] = sb.toString();
        }
        return stringArray;
    }

    //String masyvo konvertavimas i int masyva
    //Ieitis - string masyvas, kuris bus verciamas i int masyva
    public static int[] convertToIntArray(String [] array){
        int[] intArray = new int[array.length];

        for(int i = 0; i < array.length; i++) {
            try {
                intArray[i] = Integer.parseInt(array[i].trim());
            } catch (NumberFormatException e) {
                intArray[i] = 0;
            }
        }

        return intArray;
    }

    //Vienmacio masyvo vertimas dvimaciu
    //Ieitis - masyvas, kuris is vienmacio bus paverciamas dvimaciu
    static int[][] multiDimension(int[] array) {
        int result[][] = new int[1][array.length];

        for (int i = 0; i < array.length; i++) {
            result[0][i] = array[i];
        }
        return result;
    }

    //Dvimacio masyvo pavertimas vienmaciu
    //Ieitis - dvimatis masyvas, kuris bus verciamas vienmaciu
    static int[] oneDimension(int[][] array) {
        int rows = array.length;
        int columns = array[0].length;

        int [] result = new int[rows * columns];

        int index = 0;

        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                result[index++] = array[i][j];
            }
        }

        return result;
    }
}