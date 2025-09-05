package deque;
import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;
    private double factor;



    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 0;
        nextLast = 0;
        factor = 0.5;
    }


    @Override
    public void addFirst(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextFirst] = item;
        nextFirst = (nextFirst - 1 + items.length) % items.length;
        size++;
    }


    private void resize(int newSize) {
        T[] newItems = (T[]) new Object[newSize];
        int firstIndex = (nextFirst + 1) % items.length;

        if (firstIndex + size <= items.length) {
            System.arraycopy(items, firstIndex, newItems, 0, size);
        } else {
            int firstPartLen = items.length - firstIndex;
            System.arraycopy(items, firstIndex, newItems, 0, firstPartLen);
            System.arraycopy(items, 0, newItems, firstPartLen, size - firstPartLen);
        }
        items = newItems;
        nextFirst = newSize - 1;
        nextLast = size;

    }

    @Override
    public void addLast(T item) {
        if (size == items.length) {
            resize(size * 2);
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
        nextFirst = (nextFirst + 1) % items.length;
        T item = items[nextFirst];
        items[nextFirst] = null;
        size--;
        if (items.length > 8 && (double) size / items.length <factor) {
            resize(items.length / 2);
        }
        return item;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        int lastIndex = (nextLast - 1 + items.length) % items.length;
        T item = items[lastIndex];
        items[lastIndex] = null;
        size--;
        if (items.length > 8 && (double) size / items.length < factor) {
            resize(items.length / 2);
        }
        return item;
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            return null;
        }
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


    public Iterator<T> iterator() {
        return new ArrayDeuqeIterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Deque<?>) {
            Deque<?> oas = (Deque<?>) o;
            if (size != oas.size()) {
                return false;
            }
            for (int i = 0; i < size; i++) {
                if (!this.get(i).equals(oas.get(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }





    private class ArrayDeuqeIterator implements Iterator<T> {
        private int index;

        public boolean hasNext() {
            return index < size;
        }

        public T next() {
            if (!hasNext()) {
                return null;
            }
            T returnnext = get(index);
            index += 1;
            return returnnext;
        }

    }

}
