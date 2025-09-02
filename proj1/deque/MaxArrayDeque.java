package deque;
import java.util.Comparator;


public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> comparator;

    public MaxArrayDeque(Comparator<T> c){
       super();
       comparator = c;
    }

    public T max() {
        if (this.isEmpty()) {
            return null;
        }
        T maxElement = this.get(0);
        for (int i = 1; i < this.size(); ++i) {
            T element = this.get(i);
            if (comparator.compare(maxElement, element) < 0) {
                maxElement = element;
            }
        }
        return maxElement;
    }

    public T max(Comparator<T> c){
        if (this.isEmpty()) {
            return null;
        }
        T maxElement =this.get(0);
        for (int i = 1; i < this.size(); ++i) {
        T element = this.get(i);
            if (c.compare(maxElement, element) < 0) {
                maxElement = element;
            }
        }
        return maxElement;
    }

}
