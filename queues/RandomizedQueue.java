/* *****************************************************************************
 *  Name: Jared Wu
 *  Date: 6/1/2020
 *  Description: RQ
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size;
    private Item[] queue;
    private int capacity;


    // construct an empty randomized queue
    public RandomizedQueue() {
        queue = (Item[]) new Object[2];
        size = 0;
        capacity = 2;
    }

    private void resize(int n) {
        Item[] temp = (Item[]) new Object[n];
        for (int i = 0; i < size; i++) {
            temp[i] = queue[i];
        }
        queue = temp;
        capacity = n;
    }

    private Item swap(int dequeue, int end) {
        Item temp = queue[dequeue];
        queue[dequeue] = queue[end];
        return temp;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (size == capacity) {
            resize(capacity * 2);
        }
        queue[size++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        Item temp = swap(StdRandom.uniform(size), (size - 1));
        queue[--size] = null;
        if (size > 0 && size == capacity / 4) resize(capacity / 2);
        return temp;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        return queue[StdRandom.uniform(size)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RQIterator();
    }


    private class RQIterator implements Iterator<Item> {
        private int i = 0;
        private int[] q;

        RQIterator() {
            q = StdRandom.permutation(size);
        }

        public boolean hasNext() {
            return i < size;
        }

        public Item next() {
            if (size == 0 || i >= size) throw new NoSuchElementException();

            return queue[q[i++]];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }


    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> test = new RandomizedQueue<Integer>();
        for (int i = 0; i < 10; i++) {
            test.enqueue(i);
        }

        StdOut.println("Size: " + test.size + " IsEmpty: " + test.isEmpty());

        for (int i = 0; i < test.size(); i++) StdOut.println("Sample: " + test.sample());
        int n = test.size();
        StdOut.println("Test ");


        for (int a : test) {
            for (int b : test) {
                StdOut.print(a + "-" + b + " ");
            }
            System.out.println();
        }

        for (int i = 0; i < n; i++)
            StdOut.println("Sample: " + test.dequeue() + " Size: " + test.size());


    }

}
