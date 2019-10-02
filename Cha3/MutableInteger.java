

public class MutableInteger {
    private static volatile boolean asleep;
    private int value;
    public int get() {return value;}
    public void set(int value) { this.value = value;}


    public static void main(String[] args) {
        while (!asleep) {
            ;
        }
    }
}
