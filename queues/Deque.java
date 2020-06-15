/* *****************************************************************************
 *  Name: Jared Wu
 *  Date: 6/1/2020
 *  Description: Deque
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int size;
    private Node first;
    private Node last;

    private class Node {
        private Item item;
        private Node next;
        private Node prev;

        public Node(Item i, Node n, Node p) {
            item = i;
            next = n;
            prev = p;
        }
    }

    // construct an empty deque
    public Deque() {
        // first & last are dummy nodes;
        size = 0;
        first = new Node(null, null, null);
        last = new Node(null, null, first);
        first.next = last;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node newFirst = new Node(item, first.next, first);
        first.next.prev = newFirst;
        first.next = newFirst;
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node newLast = new Node(item, last, last.prev);
        last.prev.next = newLast;
        last.prev = newLast;
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = first.next.item;
        first.next.prev = null;
        first.next = first.next.next;
        first.next.prev.next = null;
        first.next.prev = first;
        size--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = last.prev.item;
        last.prev.next = null;
        last.prev = last.prev.prev;
        last.prev.next.prev = null;
        last.prev.next = last;
        size--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return !current.next.equals(last);
        }

        public Item next() {
            Item item = current.next.item;
            if (item == null) throw new NoSuchElementException();
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> test = new Deque<Integer>();

        // test addFirst()
        for (int i = 0; i < 10; i++) {
            test.addFirst(i);
        }
        // test removeFirst()
        while (!test.isEmpty()) {
            StdOut.print(test.removeFirst() + " Size: " + test.size() + " | ");
        }
        StdOut.println("Size: " + test.size());

        // test addLast()
        for (int i = 0; i < 10; i++) {
            test.addLast(i);
        }
        // test removeLast()
        while (!test.isEmpty()) {
            StdOut.print(test.removeLast() + " Size: " + test.size() + " | ");
        }
        StdOut.println("Size: " + test.size());

        // test iterator
        for (int i = 1; i <= 5; i++) {
            test.addLast(i);
        }
        for (int a : test) {
            for (int b : test)
                StdOut.print(a + "-" + b + " ");
            StdOut.println();
        }
    }


}
