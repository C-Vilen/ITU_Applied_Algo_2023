import java.util.Scanner;

public class PopCount {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        Long[] arr = new Long[n];
        for (int i = 0; i < n; i++) {
            String hex = sc.next();
            Long num = Long.parseLong(hex, 16); // Converts 16-byte hex to 64-bit integer
            arr[i] = num;
        }
        for (int i = 0; i < n; i++) {
            Long num = arr[i];
            int count = 0;
            for (int j = 0; j < 64; j++) {
                if ((num & 1) == 1) { // Checks if the last bit is 1 with a bitwise AND operation with 1
                    count++;
                }
                num >>= 1; // Shifts the 64-bit to the right by 1 bit
            }
            System.out.println(count);
        }
        sc.close();
    }
}
