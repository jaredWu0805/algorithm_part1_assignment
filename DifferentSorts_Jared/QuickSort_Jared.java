/* *****************************************************************************
 *  Name:              Jared Wu
 *  Coursera User ID:
 *  Last modified:     6/10/2-19
 **************************************************************************** */

public class QuickSort_Jared {

    QuickSort_Jared() {
    }

    public static void sort(int[] array) {
        if (array == null || array.length == 0) throw new IllegalArgumentException();
        if (array.length > 1) {
            partition(array, 0, array.length - 1);
        }
    }

    private static void sort(int[] a, int lo, int hi) {
        if (hi >= lo) return;
        int p = partition(a, lo, hi);
        sort(a, lo, p - 1);
        sort(a, p + 1, hi);
    }

    private static int partition(int[] a, int lo, int hi) {
        int i = lo, j = hi;
        i++;

        while (true) {
            while (a[j] > a[lo]) j--;
            if (j <= i) break;

            while (a[i] < a[lo]) i++;
            if (j <= i) break;

            exchange(a, i, j);
        }
        exchange(a, lo, i);
        return i;
    }

    private static void exchange(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
