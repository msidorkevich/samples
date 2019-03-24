package sidomik.samples.firstminn;

import java.util.List;
import java.util.stream.Stream;

public interface OrderedDataProvider<T> {

    Stream<T> sort(List<DataContainer<T>> allData);
}
