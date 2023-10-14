package hyperloglog;
import java.util.Scanner;

/**
 * This class provides our implementation of the HyperLogLog algorithm to
 * estimate the cardinality of a read-only stream of integers.
 * 
 * @author Christian Vilen
 * @author Mads Wedel
 * @author Oskar Rumle Rasmussen
 * 
 */
public class HyperLogLog {

    /**
     * The main method for running the HyperLogLog algorithm.
     * 
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        if ("HyperLogLog".equals(args[0])) {
            run(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
        } else {
            System.out.println("Unknown algorithm");
        }
    }

    public static void run(int registerAmount, int inputSize) {
        HyperLogLogEstimator estimator = new HyperLogLogEstimator(registerAmount);
        long[] inputArr = readInput(inputSize);
        for (int i = 0; i < inputSize; i++) {
            estimator.processInput((int) inputArr[i]);
        }
        System.out.print(estimator.estimateCardinality());
    }

    public static long[] readInput(int inputSize){
        Scanner in = new Scanner(System.in);
        long[] inputArr = new long[inputSize];
        for (int i = 0; i < inputSize; i++) {
            inputArr[i] = in.nextLong();
        }
        in.close();
        return inputArr;
    }

    /**
     * This class defines the HLL Data structure used.
     */
    public static class HyperLogLogEstimator {
        private final int m;
        private int V;
        private final double amMagic;
        private int[] registers;

        /**
         * Constructs a HyperLogLog estimator with the specified number of registers.
         * 
         * @param m The number of registers (bit array) to use.
         * @value V Holds the number of empty registers
         * @value amMagic Is our magic constant used in getting the raw estimate
         * @value registers Is our register in the form of a byte array.
         */
        public HyperLogLogEstimator(int m) {
            this.m = m;
            this.V = m;
            this.amMagic = 0.7213 / (1 + 1.079 / m);
            this.registers = new int[m];
        }

        /**
         * Processes an input element by first hashing the value, picking the register
         * and finally updating the register based on the pComputation of the hashed
         * value.
         * 
         * @param y The input element.
         */
        public void processInput(int y) {
            int hx = computeHash(y);
            int registerIndex = pickRegister(y, m);
            if (registers[registerIndex] < pComputations(hx)) {
                if (registers[registerIndex] == 0) {
                    V--;
                }
                registers[registerIndex] = pComputations(hx);
            }
        }

        /**
         * Calculates the raw estimate of cardinality.
         * 
         * @return The raw estimate.
         */
        private double calculateRawEstimate() {
            double regSum = 0.0;
            for (int i = 0; i < registers.length; i++) {
                regSum += Math.pow(2, -registers[i]);
            }
            regSum = 1 / regSum;
            return regSum * amMagic * m * m;
        }

        /**
         * Applies range corrections to the raw estimate if needed.
         * 
         * @param rawN The raw estimate.
         * @return The corrected estimate.
         */
        private double rangeCorrections(double rawN) {
            if (rawN <= (5.0 / 2.0) * m && V > 0) {
                return m * Math.log((double) m / V);
            }
            if (rawN > (1.0 / 30.0) * Math.pow(2, 32)) {
                return -1.0 * Math.pow(2, 32) * Math.log(1 - (rawN / Math.pow(2, 32)));
            }
            return rawN;
        }

        /**
         * Estimates the cardinality and returns the result.
         * 
         * @return The estimated cardinality.
         */
        public long estimateCardinality() {
            double rawN = calculateRawEstimate();
            double correctedN = rangeCorrections(rawN);
            return Math.round(correctedN);
        }

        /**
         * Computes the hash value of a long integer using a matrix with values given in
         * Exercise 3.
         * 
         * @param x The input value.
         * @return The hash value.
         */
        public static int computeHash(long x) {
            int[] A = getMatrix();

            int result = 0;
            for (int i = 0; i < 32; i++) {
                int innerProduct = 0;
                for (int j = 0; j < 32; j++) {
                    innerProduct += (A[i] >> j & 1) * (x >> j & 1);
                }
                result |= (innerProduct % 2) << i;
            }

            return result;
        }

        /**
         * Picks a register index based on the input value.
         * This function was given in the Instructions to the task.
         * 
         * @param y The input value.
         * @return The register index.
         */
        public static int pickRegister(int y, int m) {
            int shift = Integer.numberOfLeadingZeros(m);
            int j = ((y * 0xbc164501) & 0x7fffffff) >> (shift);
            return j;
        }

        /**
         * Computes pComputations for a given input value.
         * 
         * @param y The input value.
         * @return The position of the first 1 bit in the input.
         */
        public static int pComputations(int y) {
            int x = Integer.numberOfLeadingZeros(y)+1;
            return x;
        }

        /**
         * Gets the predefined matrix for hash computation as given in Exercise 3.
         * 
         * @return The matrix.
         */
        private static int[] getMatrix() {
            int[] A = {
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

        /**
         * Prints the contents of the registers (for testing purposes).
         */
        public void printRegister() {
            for (int b : registers) {
                System.out.println(b);
            }
        }

        /**
         * Gets the registers (for testing purposes).
         * 
         * @return The registers of type byte array.
         */
        public int[] getRegisters() {
            return registers;
        }

    }

}