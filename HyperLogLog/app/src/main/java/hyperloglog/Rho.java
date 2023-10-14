package hyperloglog;
import java.util.Scanner;

/**
 * This file provides our implementation of the Rho (pComputations)-method for testing in codeGrade.
 * Functions and methods are commented in depth in the Hyperloglog.java file.
 * 
 * @author Christian Vilén
 * @author Mads Wedel
 * @author Oskar Rumle Rasmussen
 * 
 */

public class Rho {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            long x = in.nextLong(16); // Reading long from input to avoid overflow
            int output = pComputations((int) x);
            System.out.println(output);
        }
        in.close();
    }
    
    public static int pComputations(int y) {
        int x = Integer.numberOfLeadingZeros(y);
        return x + 1;
    }
}
