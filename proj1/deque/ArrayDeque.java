package deque;

public class ArrayDeque<T> implements Deque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;
    private static final int MIN_CAPACITY = 8;
    private static final int DOUBLE_CAPACITY = 16;


    public ArrayDeque() {
        items = (T[]) new Object[MIN_CAPACITY];
        size = 0;
        nextFirst = 4;
        nextLast = 5;
    }

    public ArrayDeque(ArrayDeque other) {
        items = (T[]) new Object[MIN_CAPACITY];
        size = 0;
        nextFirst = 4;
        nextLast = 5;

        for (int i = 0; i < other.size; i++) {
            addLast((T) other.get(i));
        }
    }

    @Override
    public void addFirst(T item) {
        if (size == items.length) {
            newSize();
        }
        items[nextFirst] = item;
        nextFirst = (nextFirst - 1 + items.length) % items.length;
        size++;
    }

    private void newSize() {
        if (items.length == size) {
            resize(items.length * 2);
        }
        double resizeFactor = 0.25;
        if (items.length >= DOUBLE_CAPACITY && size < items.length * resizeFactor) {
            resize(items.length / 2);
        }
    }

    private void resize(int newSize) {
        int firstIndex = (nextFirst + 1) % items.length;
        T[] newItems = (T[]) new Object[newSize];
        if (firstIndex + size <= items.length) {
            System.arraycopy(items, firstIndex, newItems, 0, size);
        } else {
            int firstPartLen = items.length - firstIndex;
            System.arraycopy(items, firstIndex, newItems, 0, firstPartLen);
            System.arraycopy(items, 0, newItems, firstPartLen, size - firstPartLen);
        }
        nextFirst = newSize - 1;
        nextLast = size;
        items = newItems;
    }

    @Override
    public void addLast(T item) {
        if (size == items.length) {
            newSize();
        }
        items[nextLast] = item;
        nextLast = (nextLast + 1) % items.length;
        size++;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        int firstIndex = (nextFirst + 1) % items.length;
        size--;
        nextFirst = firstIndex;
        T item = items[firstIndex];
        items[firstIndex] = null;
        return item;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        int lastIndex = (nextLast - 1 + items.length) % items.length;
        size--;
        nextLast = lastIndex;
        T item = items[lastIndex];
        items[lastIndex] = null;
        return item;
    }

    @Override
    public T get(int index) {
        int actuacIndex = (nextFirst + 1 + index) % items.length;
        return items[actuacIndex];
    }

    @Override
    public int size() {
        return size;
    }



    @Override
    public void printDeque() {
        for (int i = 0; i < size; i++) {
            int index = (nextFirst + 1 + i) % items.length;
            System.out.print(items[index] + " ");
        }
        System.out.println();
    }

}
