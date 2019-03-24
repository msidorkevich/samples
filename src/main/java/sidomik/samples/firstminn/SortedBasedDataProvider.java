package sidomik.samples.firstminn;

import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class SortedBasedDataProvider<T> implements OrderedDataProvider<T> {

    private final Comparator<T> comparator;

    public SortedBasedDataProvider(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    @Override
    public Stream<T> sort(List<DataContainer<T>> allData) {
        allData.forEach(c -> c.sort(comparator));

        List<Iterator<T>> iterators = new ArrayList<>(allData.size());
        allData.forEach(data -> iterators.add(data.data()));

        Iterator<T> iterator = new MergingIterator<>(iterators, comparator);

        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator,
                        Spliterator.NONNULL | Spliterator.SORTED),false);
    }
}
