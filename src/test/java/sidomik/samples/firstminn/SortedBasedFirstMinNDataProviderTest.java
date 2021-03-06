package sidomik.samples.firstminn;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

public class SortedBasedFirstMinNDataProviderTest {

    private SortedBasedFirstMinNDataProvider<Integer> firstMinNDataProvider;

    @Before
    public void prepare() {
        OrderedDataProvider<Integer> orderedDataProvider = new SortedBasedDataProvider<>(Comparator.<Integer>naturalOrder());
        firstMinNDataProvider = new SortedBasedFirstMinNDataProvider<>(orderedDataProvider);
    }

    @Test
    public void basic() {
        List<DataContainer<Integer>> allDatas = new ArrayList<>();
        allDatas.add(new InMemoryDataContainer<>(List.of(5, 4, 1, 2, 0, 9)));
        allDatas.add(new InMemoryDataContainer<>(List.of(11, 20, 3, 7)));
        Stream<Integer> stream = firstMinNDataProvider.firstMinN(allDatas, 5);
        List<Integer> actualList = stream.collect(toList());

        assertEquals(List.of(0, 1, 2, 3, 4), actualList);
    }
}