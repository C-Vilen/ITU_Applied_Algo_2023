package threesum;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class ThreeSum {
    public static void main(String[] args) {
        int[] x = readData();
        int[] y = null;
        
        if ("cubic".equals(args[0])) {
            y = threeSumCubic(x);
        }
        else if ("quadratic".equals(args[0])) {
            y = threeSumQuadratic(x);
        }
        else if ("hashmap".equals(args[0])) {
            y = threeSumHashMap(x);
        }
        else if ("new-hashmap".equals(args[0])) {
            y = newThreeSumHashMap(x);
        }
    
        if (y == null) {
            System.out.println("null");
        }
        else {
            System.out.println(String.format("%d %d %d", 
                y[0], y[1], y[2]));
        }
    }

    public static int[] readData() {
        Scanner s = new Scanner(System.in);
        int[] x = null;
        try {
            int n = s.nextInt();
            x = new int[n];
            for (int i = 0; i < n; ++i) {
                x[i] = s.nextInt();
            }    
        }
        finally {
            s.close();
        }
        return x;
    }

    public static int[] threeSumCubic(int[] x) {
        int n = x.length;
        for (int i = 0; i < n; ++i) {
            int a = x[i];
            for (int j = i+1; j < n; ++j) {
                int b = x[j];
                for (int k = j+1; k < n; ++k) {
                    int c = x[k];
                    if (a + b + c == 0) {
                        return new int[] { a, b, c };
                    }
                }
            }
        }
        return null;
    }
    

    public static int[] threeSumQuadratic(int[] x) {
        int n = x.length;
        int[] y = x.clone();
        Arrays.sort(y);
        for (int i = 0; i < n; ++i) {
            int a = y[i];
            int left = i+1;
            int right = n-1;
            while (left < right) {
                int b = y[left];
                int c = y[right];
                if (a+b+c == 0) {
                    return new int[] { a, b, c};
                }
                else if (a+b+c < 0) {
                    ++left;
                }
                else {
                    --right;
                }
            }
        }

        return null;
    }


    public static int[] threeSumHashMap(int[] x) {
        int n = x.length;
        HashMap<Integer,Integer> H = 
            new HashMap<Integer,Integer>();
        for (int i = 0; i < n; ++i) {
            H.put(x[i], i);
        }
        for (int i = 0; i < n; ++i) {
            int a = x[i];
            for (int j = i+1; j < n; ++j) {
                int b = x[j];
                int c = -a - b;
                Integer k = H.get(c);
                if (k != null && j < k) {
                    return new int[] { a, b, c };
                }
            }
        }
        return null;
    }

    public static int[] newThreeSumHashMap(int[] x) {
        int n = x.length;
        HashMap<Integer,Integer> H = 
            new HashMap<Integer,Integer>();
        for (int i = 0; i < n; ++i) {
            H.put(x[i], i);
        }
        for (int i = 0; i < n; ++i) {
            int a = x[i];
            for (int j = i+1; j < n; ++j) {
                int b = x[j];
                int c = -a - b;
                Integer k = H.get(c);
                if (k != null) {
                    return new int[] { a, b, c };
                }
            }
        }
        return null;
    }
}
