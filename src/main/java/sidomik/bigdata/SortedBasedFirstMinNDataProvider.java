package sidomik.bigdata;

import java.util.List;
import java.util.stream.Stream;

public class SortedBasedFirstMinNDataProvider<T> implements FirstMinNDataProvider<T> {

    private final OrderedDataProvider<T> orderedDataProvider;

    public SortedBasedFirstMinNDataProvider(OrderedDataProvider<T> orderedDataProvider) {
        this.orderedDataProvider = orderedDataProvider;
    }

    @Override
    public Stream<T> firstMinN(List<DataContainer<T>> allData, int n) {
        return orderedDataProvider.sort(allData).limit(n);
    }
}
