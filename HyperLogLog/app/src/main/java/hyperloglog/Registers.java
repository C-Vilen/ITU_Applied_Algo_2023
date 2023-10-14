package hyperloglog;
import java.util.Scanner;

/**
 * This file provides our implementation of the Registers and auxillary methods for testing in codeGrade.
 * Functions and methods are commented in depth in the Hyperloglog.java file.
 * 
 * @author Christian Vilén
 * @author Mads Wedel
 * @author Oskar Rumle Rasmussen
 * 
 */

public class Registers {

    public static void main(String[] args) {
        HyperLogLogEstimator estimator = new HyperLogLogEstimator(1024);
        Scanner in = new Scanner(System.in);

        while (in.hasNext()) {
            long x = in.nextLong(16);
            estimator.processInput((int) x);
        }
        
        byte[] reg = estimator.getRegisters();
        for (int i = 0; i < 1024; i++) {
            System.out.println((int) reg[i]);
        }   
        in.close();
    }

    public static class HyperLogLogEstimator {
        // private final int m;
        // private int V;
        // private final double amMagic;
        public byte[] registers;

        public HyperLogLogEstimator(int m) {
            // this.m = m;
            // this.V = m; // Initial value
            // this.amMagic = 0.7213 / (1 + 1.079 / m);
            this.registers = new byte[m];
        }

        public void processInput(int y) {
            int hx = computeHash(y);
            int registerIndex = pickRegister(y);
            if (registers[registerIndex] < pComputations(hx)) {
                if (registers[registerIndex] == 0) {
                    // V--;
                }
                registers[registerIndex] = (byte) pComputations(hx);
            }
        }

        public static int computeHash(int x) {
            int[] A = getMatrixA(); // Initiate Matrix

            int result = 0;
            for (int i = 0; i < 32; i++) {
                int innerProduct = 0;
                for (int j = 0; j < 32; j++) {
                    innerProduct += (A[i] >> j & 1) * (x >> j & 1); // Calculate inner product
                }
                result |= (innerProduct % 2) << i; // Set the i-th bit of the result
            }

            return result;
        }

        private static int[] getMatrixA() {
            int[] A = { // Values given from Exercise
                    0x21ae4036, 0x32435171, 0xac3338cf, 0xea97b40c,
                    0x0e504b22, 0x9ff9a4ef, 0x111d014d, 0x934f3787,
                    0x6cd079bf, 0x69db5c31, 0xdf3c28ed, 0x40daf2ad,
                    0x82a5891c, 0x4659c7b0, 0x73dc0ca8, 0xdad3aca2,
                    0x00c74c7e, 0x9a2521e2, 0xf38eb6aa, 0x64711ab6,
                    0x5823150a, 0xd13a3a9a, 0x30a5aa04, 0x0fb9a1da,
                    0xef785119, 0xc9f0b067, 0x1e7dde42, 0xdda4a7b2,
                    0x1a1c2640, 0x297c0633, 0x744edb48, 0x19adce93
            };

            return A;
        }

        public byte[] getRegisters() {
            return registers;
        }

        public static int pickRegister(int y) {
            int j = ((y * 0xbc164501) & 0x7fffffff) >> 21;
            return j;
        }

        public static int pComputations(int y) {
            int x = Integer.numberOfLeadingZeros(y);
            return x + 1;
        }
    }
}
