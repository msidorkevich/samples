package sidomik.samples;

import java.util.List;
import java.util.stream.Stream;

public interface FirstMinNDataProvider<T> {

    Stream<T> firstMinN(List<DataContainer<T>> allData, int n);
}
