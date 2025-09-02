package deque;
public class LinkedListDeque<T> implements Deque<T> {

    public class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(T i, Node<T>  n, Node<T>  p) {
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

    public LinkedListDeque(LinkedListDeque ohtet) {
        sentinel = new Node<>(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;

        for (int i = 0; i != ohtet.size(); ++i) {
            addLast((T) ohtet.get(i));
        }
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
    public boolean isEmpty() {
        return size == 0;
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

    public T getRecursiveHelper(Node<T> p, int index) {
        if (index == 0) {
            return p.item;
        }
        return getRecursiveHelper(p.next, index - 1);
    }
}
