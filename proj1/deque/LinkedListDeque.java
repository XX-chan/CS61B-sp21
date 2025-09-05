package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {

    private class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(T i, Node<T>  n, Node<T>  p) {
            item = i;
            next = n;
            prev = p;
        }
    }

    private Node<T> sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new Node<>(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    @Override
    public void addFirst(T i) {
        Node<T> newNode = new Node(i, sentinel.next, sentinel);
        sentinel.next.prev = newNode;
        sentinel.next = newNode;
        size++;
    }

    @Override
    public void addLast(T i) {
        Node<T> newNode = new Node(i, sentinel, sentinel.prev);
        sentinel.prev.next = newNode;
        sentinel.prev = newNode;
        size++;
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        Node<T> current = sentinel.next;
        for (int i = 0; i < size; i++) {
            System.out.print(sentinel.next.item + " ");
            current = current.next;
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        Node<T> first = sentinel.next;
        T item = first.item;

        sentinel.next  = first.next;
        first.next.prev = sentinel;

        first.next = null;
        first.prev = null;
        size--;
        return item;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        Node<T> last = sentinel.prev;
        T item = last.item;

        sentinel.prev  = last.prev;
        last.prev.next = sentinel;

        last.next = null;
        last.prev = null;
        size--;
        return item;
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        Node<T> p = sentinel.next;
        for (int i = 0; i < index; i++) {
            p = p.next;
        }
        return p.item;
    }

    public T getRecursive(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        return getRecursiveHelper(sentinel.next, index);
    }

    private T getRecursiveHelper(Node<T> p, int index) {
        if (index == 0) {
            return p.item;
        }
        return getRecursiveHelper(p.next, index - 1);
    }

    public Iterator<T> iterator() {
        return new LinkedListDequeIterator<T>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o instanceof Deque<?>) {
            Deque<?> olld = (Deque<?>) o;
            if (this.size != olld.size()) {
                return false;
            }
            for (int i = 0; i < this.size; i++) {
                if (!olld.get(i).equals(this.get(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private class LinkedListDequeIterator<T> implements Iterator<T> {
        private int index;

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                return null;
            }
            T returnnext = (T) get(index);
            index++;
            return returnnext;
        }
    }
}
