package kt.golaycodekt;

import java.util.Arrays;

public class Coding {

    //Uzkoduojame gauta informacija
    //Ieitis - kodas, kuri mes uzkoduosime
    public int[] codeInformation(String code) {
        int[] encodedMessage;

        String[] codeArray = code.split("");

        int[] intElements = MatrixConvertions.convertToIntArray(codeArray);

        encodedMessage = MatrixConvertions.oneDimension(MatrixCalculations.matrixMultiplication(MatrixConvertions.multiDimension(intElements), Matrix.G11));
        MatrixCalculations.modArray(encodedMessage);

        return encodedMessage;
    }
}

