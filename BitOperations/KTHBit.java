import java.util.Scanner;

public class KTHBit {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();
        long[] arr = new long[n];
        for (int i = 0; i < n; i++) {
            String hex = sc.next();
            Long num = Long.parseLong(hex, 16); // Converts 8-byte hex to 64-bit integer
            arr[i] = num;
        }
        for (int j = 0; j < n; j++) {
            long num = arr[j];
            // Shifts the 64-bit to the right by k bits and checks if the last bit is 1 with a bitwise AND operation with 1
            // The mask is 1 if the kth bit is 1, and 0 if the kth bit is 0
            long mask = (num >> k) & 1; 
            System.out.println(mask);
        }
        sc.close();
    }
}