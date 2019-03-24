package sidomik.samples;

import java.util.Comparator;
import java.util.Iterator;

public interface DataContainer<T> {

    Iterator<T> data();

    void sort(Comparator<T> comparator);
}
