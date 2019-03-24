package sidomik.bigdata;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import static com.google.common.collect.Lists.newArrayListWithExpectedSize;

public class MergingIterator<T> implements Iterator<T> {

    private T nextValue;
    private List<T> currentValues;

    private final List<Iterator<T>> iterators;
    private final Comparator<T> comparator;

    public MergingIterator(List<Iterator<T>> iterators, Comparator<T> comparator) {
        this.iterators = iterators;
        this.comparator = comparator;

        currentValues = newArrayListWithExpectedSize(iterators.size());
        for (Iterator<T> iterator : iterators) {
            currentValues.add(nextOrNull(iterator));
        }
    }

    @Override
    public boolean hasNext() {
        // todo: find more efficient solution than using linear search every time to find min
        // todo: possibly use BST to keep the order
        int minIndex = findMinIndex(currentValues, comparator);
        nextValue = currentValues.get(minIndex);
        currentValues.set(minIndex, nextOrNull(iterators.get(minIndex)));

        return nextValue != null;
    }

    @Override
    public T next() {
        return nextValue;
    }

    @Nullable
    private static <T> T nextOrNull(Iterator<T> it) {
        if (it.hasNext()) {
            return it.next();
        } else {
            return null;
        }
    }

    private static <T> int findMinIndex(List<T> list, Comparator<T> comparator) {
        int minIndex = 0;
        // finding first non null value
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) != null) {
                minIndex = i;
                break;
            }
        }

        for (int i = 0; i < list.size(); i++) {
            T curr = list.get(i);
            if (curr == null) {
                continue;
            }

            T min = list.get(minIndex);
            if (comparator.compare(min, curr) > 0) {
                minIndex = i;
            }
        }
        return minIndex;
    }
}
