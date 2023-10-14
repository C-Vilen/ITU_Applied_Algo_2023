package hyperloglog;
public class HashFunctionHLL {
    
    private int b;
    private int a;
    private int value;
    private String hexFinal;


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


    public HashFunctionHLL(){
        
    }

    public void hexToUnsignedInteger(int x) {
        
        for (int i = 0; i < 31; i++){
            String line = hexValues[i];
            line = line.substring(2);
            a = Integer.parseUnsignedInt(line, 16); // Parsing the hex to unsigned integer
            value = (x | a) % 2; // Outputs either 1 or 0
            System.out.println("Value: " + value);
            b = b << 1;
            b = b | (b+value); // Shifting the value either 1 or two positions
            System.out.println("B: " + b);
            System.out.println("A: " + a);
            
        }
        hexFinal = Integer.toHexString(b);
        System.out.println(b);
        System.out.println(hexFinal);
    }




    public static void main(String[] args) {
        HashFunctionHLL hll = new HashFunctionHLL();
        hll.hexToUnsignedInteger(3);
    }


}
