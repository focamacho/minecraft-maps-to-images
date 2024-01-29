public class Util {

    public static int getR(int col) {
        return (col & 0x00ff0000) >> 16;
    }

    public static int getG(int col) {
        return (col & 0x0000ff00) >> 8;
    }

    public static int getB(int col) {
        return col & 0x000000ff;
    }

}
