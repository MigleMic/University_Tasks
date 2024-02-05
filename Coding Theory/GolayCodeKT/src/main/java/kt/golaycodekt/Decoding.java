package kt.golaycodekt;

public class Decoding {

    //Vektoriaus dekodavimas
   //Gaunam uzkoduota skaiciu masyva, graziname dekoduota masyva
    public int[] decode(int[] code) {

        int[] decodedMessage = null;
        //Ilgio 24 vektorius
        int[] u = new int[24];

        boolean decoded = false;

        //Dekodavimo algoritmas
        for (int iteration = 0; iteration < 2; iteration++) {
            //1 punktas. Pirmasis sindromas s = code * H
            int[] firstSyndrome = calculateSyndrome(code, Matrix.H);

            //2 punktas. svoris(s) <= 3, radome u, stabdome darba
            if (MatrixCalculations.vectorWeight(firstSyndrome) <= 3) {
                for(int j = 0; j < 12; j++)
                {
                    //u = [s,0]
                    u[j] = firstSyndrome[j];
                }
                decoded = true;
                break;
            }

            //3 punktas. svoris(s + bi) <= 2 kazkuriai i eilutei B matricoje
            if (attemptDecodingWithBMatrix(firstSyndrome, u)) {
                decoded = true;
                break;
            }

            //4 punktas. Antras sindromas s2 = s * B
            int[] secondSyndrome = calculateSyndrome(firstSyndrome, Matrix.B);

            //5 punktas. Jei svoris(s2) <= 3
            if (MatrixCalculations.vectorWeight(secondSyndrome) <= 3) {
                //u = s[0, s2]
                for (int j = 12; j < 24; j++) {
                    u[j] = secondSyndrome[j - 12];
                }
                decoded = true;
                break;
            }

            //6 punktas. svoris(s2 + bi) <= 2 kazkokiai bi eilutei
            if (attemptDecodingWithBMatrix2(secondSyndrome, u)) {
                decoded = true;
                break;
            }
        }

        if (decoded) {
            decodedMessage = MatrixCalculations.vectorSum(code, u);
            MatrixCalculations.modArray(decodedMessage);
        }

        return decodedMessage;
    }

    private int[] calculateSyndrome(int[] code, int[][] matrix) {
        int[][] multiDimCode = MatrixConvertions.multiDimension(code);
        int[] syndrome = MatrixConvertions.oneDimension(MatrixCalculations.matrixMultiplication(multiDimCode, matrix));
        MatrixCalculations.modArray(syndrome);
        return syndrome;
    }

    private boolean attemptDecodingWithBMatrix(int[] syndrome, int[] u) {
        for (int j = 0; j < 12; j++) {
            int[] row = MatrixCalculations.getMatrixRow(Matrix.B, j);
            int[] syndromeRow = MatrixCalculations.vectorSum(syndrome, row);
            MatrixCalculations.modArray(syndromeRow);

            if (MatrixCalculations.vectorWeight(syndromeRow) <= 2) {
                //u = s + bi, ei, tik i pozicijoje 1, kitur 0
                for (int k = 0; k < 12; k++) {
                    u[k] = syndromeRow[k];
                }
                u[12 + j] = 1;
                return true;
            }
        }
        return false;
    }

    private boolean attemptDecodingWithBMatrix2(int[] syndrome, int[] u) {
        for (int j = 0; j < 12; j++) {
            int[] row = MatrixCalculations.getMatrixRow(Matrix.B, j);
            int[] syndromeRow = MatrixCalculations.vectorSum(syndrome, row);
            MatrixCalculations.modArray(syndromeRow);

            //u = [ei, s2 + bi], kur ei yra 1
            if (MatrixCalculations.vectorWeight(syndromeRow) <= 2) {
                u[j] = 1;
                for (int k = 12; k < 24; k++) {
                    u[k] = syndromeRow[k - 12];
                }
                return true;
            }
        }
        return false;
    }
}

