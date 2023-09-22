import java.util.ArrayList;
import java.util.Scanner;

public class OrthogonalVectorBitRepresentation {

    static final int M = 256;
    static final int BLOCKS = 4;

    public static void readInput(ArrayList<long[]> A, Scanner sc, int n) {
        for (int i = 0; i < n; i++) {
            long[] a = new long[BLOCKS];
	        String s = sc.nextLine();
            for (int j = 0; j < M; j++) {
                int current_block = j / 64;
                int current_index = j - current_block * 64;
                if (s.charAt(j) == '1') {
                    // set the bit in block current_block at position current_index
                    a[current_block] = a[current_block] | (1L << (63 - current_index));
                } 
            }
            A.add(a);
            // Some debug code to inspect bit representation
            // System.out.print(i + " ");
            // for (int j = 0; j < BLOCKS; j++) {
            //     String padding = "0000000000000000000000000000000000000000000000000000000000000000";
            //     String result = padding + Long.toBinaryString(a[j]);
            //     result = result.substring(result.length() - 64, result.length());
            //     System.out.print(result);
            // }
            // System.out.println();
        }
    }

    public static boolean is_orthogonal(long[] u, long[] v) {
        for (int i = 0; i < BLOCKS; i++) {
            if ((u[i] & v[i]) != 0) {
                return false;
            }
        }
        return true;
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine());

        ArrayList<long[]> A = new ArrayList<long[]>();
        readInput(A, sc, n);

        long t = System.nanoTime();
        for (int i = 0; i < A.size(); i++) {
            for (int j = i + 1; j < A.size(); j++) {
                if (is_orthogonal(A.get(i), A.get(j))) {
                    System.out.println("yes");
                    System.exit(0);
                }
            }
        }
        System.out.println("Search took " + (System.nanoTime() - t) / 1e6 + " ms." );
        System.out.println("no");
    }
}
