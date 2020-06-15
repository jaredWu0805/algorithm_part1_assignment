/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Arrays;

public class Test {
    int[] test = { 1, 2, 3, 4, 5, 6 };

    Test() {
        int[] x = test;

        x[3] = 10;
        System.out.println("test: ");
        for (int i : test) {
            System.out.println(i);
        }

        test[3] = 100;
        System.out.println("x: ");
        for (int i : x) {
            System.out.println(i);
        }
    }

    Test(int[] p) {
        int[] copy = p;
        Arrays.sort(copy);

        System.out.println("p: ");
        for (int i : p) {
            System.out.println(i);
        }
    }

    public static void main(String[] args) {
        int[] p = { 50, 40, 30, 20, 10 };

        Test t = new Test();

        Test t2 = new Test(p);
    }
}
