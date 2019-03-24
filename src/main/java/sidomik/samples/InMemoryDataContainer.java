package sidomik.samples;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class InMemoryDataContainer<T> implements DataContainer<T> {

    private final List<T> data;

    public InMemoryDataContainer(Collection<T> data) {
        this.data = newArrayList(data);
    }

    @Override
    public Iterator<T> data() {
        return data.iterator();
    }

    @Override
    public void sort(Comparator<T> comparator) {
        data.sort(comparator);
    }
}
