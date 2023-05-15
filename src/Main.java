import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * The Main Class that runs every thing
 *
 * @author Ahmed Hussein
 */

public class Main {

    // PrintWriter is used for faster program
    static PrintWriter pw = new PrintWriter(System.out);
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to the Linear System Solver!");
        System.out.println("Please enter the number of equations: ");
        int numOfEquations = sc.nextInt();
        System.out.println("Ok, now please enter the coefficients row by row : ");
        double [][] matrix = new double[numOfEquations][numOfEquations+1];
        for (int i = 0 ; i < numOfEquations ; i++){
            for(int j = 0 ; j < numOfEquations+1 ; j++){
                matrix[i][j] = sc.nextDouble() ;
            }
        }
        boolean infiniteNumOfSolutions = convertToREF(matrix);
        if(!infiniteNumOfSolutions)
            showResults(matrix);
        pw.flush();

    }

    private static void displayMatrix (double [][] matrix){
        for(int i = 0 ; i < matrix.length ;i++){
            System.out.println(Arrays.toString(matrix[i]));
        }
    }

    private static boolean convertToREF (double [][] matrix){
        for(int i = 0 ; i < matrix.length ; i++){
            //making pivot
            int index = firstNonZeroIndex(matrix[i]);
            if(index == -1){
                pw.println("infinitely many solutions!");
                return true;
            }
            double divider = matrix[i][index];
            multiplyRow(matrix[i], 1/ divider);
            //all rows above:
            for(int j = i-1 ; j > -1 ; j--){
                double [] tmp = Arrays.copyOf(matrix[i] , matrix[i].length);
                double[] current = matrix[j];
                double valueToBeMultipliedWith = current[index];
                if(valueToBeMultipliedWith == 0)
                    continue;
                multiplyRow(tmp , -valueToBeMultipliedWith);
                addRow(current,tmp);
            }
            //all rows below:
            for(int j = i+1 ; j < matrix.length ;j++){
                double [] tmp = Arrays.copyOf(matrix[i] , matrix[i].length);
                double [] current = matrix[j];
                double valueToBeMultipliedWith = current[index];
                if(valueToBeMultipliedWith == 0)
                    continue;
                multiplyRow(tmp , -valueToBeMultipliedWith);
                addRow(current,tmp);
            }
        }
        return false ;
    }

    private static int firstNonZeroIndex(double [] row){
        for (int i = 0; i < row.length; i++) {
            if(row[i] != 0)
                return i;
        }
        return -1;
    }

    private static void multiplyRow (double [] row , double value){
        for(int i = 0 ; i < row.length ; i++){
            row[i] *= value;
        }
    }

    private static void addRow (double [] row1 , double [] row2){
        for (int i = 0; i < row1.length; i++) {
            row1[i] += row2[i] ;
        }
    }

    private static void showResults(double [][] matrix){
        int lastColumn = matrix[0].length -1 ;
        for (int i = 0; i < matrix.length; i++) {
            pw.println("x"+i+" = "+matrix[i][lastColumn]);
        }
    }


}
