package dado.lab;

public class BitTest {
    public static void main(String[] args) {
        System.out.println(Integer.toBinaryString(10));
        int a = 1 << 3; //1000 -> 8 1 << k 1 to (k+1) position
        System.out.println(a);//8
        int b = (1 << 32);//still 1 since in java 1 << n if int is (1 << (n & 31 -> n % 32
        System.out.println(Integer.bitCount(b));//1
        int c = -1;//32*1
        System.out.println(Integer.bitCount(c));//32

        //XOR-swap digit

    }
}
