package sidomik.samples;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.*;

public class HeapBasedFirstMinNDataProviderTest {

    private HeapBasedFirstMinNDataProvider<Integer> firstMinNDataProvider;

    @Before
    public void prepare() {
        firstMinNDataProvider = new HeapBasedFirstMinNDataProvider<>(Comparator.<Integer>naturalOrder());
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