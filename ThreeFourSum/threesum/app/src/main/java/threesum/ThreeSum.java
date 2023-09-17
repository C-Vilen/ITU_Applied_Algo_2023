package threesum;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class ThreeSum {
    public static void main(String[] args) {
        int[] x = readData();
        int[] y = null;
        
        if ("three-cubic".equals(args[0])) {
            y = threeSumCubic(x);
        }
        else if ("three-quadratic".equals(args[0])) {
            y = threeSumQuadratic(x);
        }
        else if ("three-hashmap".equals(args[0])) {
            y = threeSumHashMap(x);
        }
        else if ("three-new-hashmap".equals(args[0])) {
            y = newThreeSumHashMap(x);
        }       
        else if ("four-cubic".equals(args[0])) {
            y = fourSumCubic(x);
        }
        else if ("four-quadratic".equals(args[0])) {
            y = fourSumQuadratic(x);
        }
        else if ("four-hashmap".equals(args[0])) {
            y = fourSumHashMap(x);
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

    // ThreeSum in cubic time
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
    
    // ThreeSum in quadratic time
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

    // ThreeSum in quadratic time using a HashMap
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

    // FourSum in cubic time
    public static int[] fourSumCubic(int[] x) {
        int n = x.length;
        for (int i = 0; i < n; ++i) {
            int a = x[i];
            for (int j = i+1; j < n; ++j) {
                int b = x[j];
                for (int k = j+1; k < n; ++k) {
                    int c = x[k];
                    for (int l = k+1; l < n; ++l) {
                        int d = x[l];
                        if (a + b + c + d == 0) {
                            return new int[] { a, b, c, d };
                        }
                    }
                }
            }
        }
        return null;
    }

    // FourSum in quadratic time
    public static int[] fourSumQuadratic(int[] x) {
        int n = x.length;
        int[] y = x.clone();
        Arrays.sort(y);
        for (int i = 0; i < n; ++i) {
            int a = y[i];
            for (int j = i+1; j < n; ++j) {
                int b = y[j];
                int left = j+1;
                int right = n-1;
                while (left < right) {
                    int c = y[left];
                    int d = y[right];
                    if (a+b+c+d == 0) {
                        return new int[] { a, b, c, d};
                    }
                    else if (a+b+c+d < 0) {
                        ++left;
                    }
                    else {
                        --right;
                    }
                }
            }
        }
        return null;
    }

    // FourSum in quadratic time using a HashMap
    public static int[] fourSumHashMap(int[] x) {
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
                for (int k = j+1; k < n; ++k) {
                    int c = x[k];
                    int d = -a - b - c;
                    Integer l = H.get(d);
                    if (l != null && k < l) {
                        return new int[] { a, b, c, d };
                    }
                }
            }
        }
        return null;
    }
}
