package sidomik.samples.firstminn;

import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Spliterators.spliteratorUnknownSize;

public class HeapBasedFirstMinNDataProvider<T> implements FirstMinNDataProvider<T> {

    private final PriorityQueue<T> maxHeap;
    private final Comparator<T> comparator;

    public HeapBasedFirstMinNDataProvider(Comparator<T> comparator) {
        this.maxHeap = new PriorityQueue<>(comparator.reversed());
        this.comparator = comparator;
    }

    @Override
    public Stream<T> firstMinN(List<DataContainer<T>> allData, int n) {
        allData.stream()
                .flatMap(dc -> StreamSupport.stream(spliteratorUnknownSize(dc.data(), Spliterator.NONNULL), false))
                .forEach(curr -> {
                    T max = maxHeap.peek();
                    if (max == null || maxHeap.size() < n) {
                        maxHeap.offer(curr);
                    } else if (comparator.compare(curr, max) < 0) {
                        maxHeap.poll();
                        maxHeap.offer(curr);
                    }
                });

        return newArrayList(maxHeap).stream().sorted(comparator);
    }
}
