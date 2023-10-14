package hyperloglog;
import java.util.Scanner;

public class oldHash {
    private Integer[] bitArray = new Integer[32];


    private String[] hexValues = new String[] {
        "0x21ae4036",
        "0x32435171",
        "0xac3338cf",
        "0xea97b40c",
        "0x0e504b22",
        "0x9ff9a4ef",
        "0x111d014d",
        "0x934f3787",
        "0x6cd079bf",
        "0x69db5c31",
        "0xdf3c28ed",
        "0x40daf2ad",
        "0x82a5891c",
        "0x4659c7b0",
        "0x73dc0ca8",
        "0xdad3aca2",
        "0x00c74c7e",
        "0x9a2521e2",
        "0xf38eb6aa",
        "0x64711ab6",
        "0x5823150a",
        "0xd13a3a9a",
        "0x30a5aa04",
        "0x0fb9a1da",
        "0xef785119",
        "0xc9f0b067",
        "0x1e7dde42",
        "0xdda4a7b2",
        "0x1a1c2640",
        "0x297c0633",
        "0x744edb48",
        "0x19adce93"
    };


    public oldHash(){
        hexToArray();
    }

    public void hexToArray(){
        for (int i = 0; i < 32; i++){

            int hexInt = Integer.parseUnsignedInt(hexValues[i].substring(2), 16);
            bitArray[i] = hexInt;
        }
    }

    public void hashCalc(int x){
        int b = 0; // Initialize as zero
        for (int i = 0; i < 32; i++) {  
            int ax = Integer.bitCount((bitArray[i] | x)) % 2; // Counting number of 1 bits using bitcount, and take modulo 2
            b = b << 1; // Left-shifting in b, in order to make space for new bit.
            b = b | (b+ax); // Inserting new bit(ax) into b.
        }
        System.out.println(Integer.toHexString(b));
    }

    public static void main(String[] args) {
        oldHash hll = new oldHash();
        //int x = 00000001;
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()){
            int x = Integer.parseUnsignedInt(sc.nextLine(), 16);
            hll.hashCalc(x);
        }
        
        sc.close();
    }

}
