package sidomik.bigdata;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

public class SortingBasedOrderedDataProviderTest {

    private SortedBasedDataProvider<Integer> sortingProvider;

    @Before
    public void prepare() {
        sortingProvider = new SortedBasedDataProvider<>(Comparator.<Integer>naturalOrder());
    }

    @Test
    public void basic() {
        List<DataContainer<Integer>> allDatas = new ArrayList<>();
        allDatas.add(new InMemoryDataContainer<>(List.of(5, 4, 1, 2, 0, 9)));
        allDatas.add(new InMemoryDataContainer<>(List.of(11, 20, 3, 7)));
        allDatas.add(new InMemoryDataContainer<>(List.of(0, 5, 6, 8)));
        Stream<Integer> stream = sortingProvider.sort(allDatas);
        List<Integer> actualList = stream.collect(toList());

        assertEquals(List.of(0, 0, 1, 2, 3, 4, 5, 5, 6, 7, 8, 9, 11, 20), actualList);
    }
}