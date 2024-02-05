package kt.golaycodekt;

public class MatrixCalculations {
    //Nukopijuojam masyva viena ant kito
    //Ieitis - du masyvai, kur antras masyvas bus nukopijuotas i pirmaji masyva
    static void copyArray(int[] array1, int[] array2) {
        for (int i = 0; i < array2.length; i++) {
            array1[i] = array2[i];
        }
    }

    //Matricu daugyba su dvimatem matricom
    //Ieitis - dvi matricos, kurios bus dauginamos
    static int[][] matrixMultiplication(int[][] matrix1, int[][] matrix2) {
        int rowsFirst = matrix1.length;
        int columnsFirst = matrix1[0].length;
        int columnsSecond = matrix2[0].length;

        if (columnsFirst != matrix2.length) {
            throw new IllegalArgumentException("ERROR! Matrices cannot be multiplied");
        }

        int[][] result = new int[rowsFirst][columnsSecond];


        for (int i = 0; i < rowsFirst; i++) {
            for (int j = 0; j < columnsSecond; j++) {
                for (int k = 0; k < columnsFirst; k++) {
                    result[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }
        return result;
    }

    //Matricu suma su vienmatemis matricomis
    //Ieitis - dvi matricos, kuriuos ketiname susumuoti
    static int[] vectorSum(int[] vector, int[] matrix) {

        int length = 0;

       if (vector.length > matrix.length)
           length = matrix.length;
       else
           length = vector.length;

        int[] result = new int[vector.length];
        for (int i = 0; i < matrix.length; i++) {
            result[i] += vector[i];
            result[i] += matrix[i];
        }
        return result;
    }

    //Modulio operacija masyvui
    //Ieitis - masyvas, kuriam atliksime modulio operacija
    static void modArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = array[i] % 2;
        }
    }

    //Vienos eilutes gavimas is matricos
    //Ieits - dvimate matrica ir eilute, kuria norime gauti is matricos
    static int[] getMatrixRow(int matrix[][], int row) {
        int[] result = new int[matrix[1].length];

        for (int i = 0; i < result.length; i++) {
            result[i] = matrix[row][i];
        }

        return result;
    }

    //Vektoriaus svoris
    //Ieitis - vektorius, kurio svori norime apskaiciuoti
    static int vectorWeight(int[] vector) {
        int result = 0;

        for (int i = 0; i < vector.length; i++) {
            if (vector[i] == 1)
                result++;
        }
        return result;
    }
}