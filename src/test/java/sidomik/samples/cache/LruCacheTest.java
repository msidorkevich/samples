package sidomik.samples.cache;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class LruCacheTest {

    @Test
    public void putAndGet() {
        LruCache<Integer, String> cache = new LruCache<>(2);
        cache.put(1, "a");
        assertEquals("a", cache.get(1));
    }

    @Test
    public void oneShouldExpire() {
        LruCache<Integer, String> cache = new LruCache<>(2);
        cache.put(1, "a");
        cache.put(2, "b");
        cache.put(3, "c");

        assertNull(cache.get(1));
        assertEquals("b", cache.get(2));
        assertEquals("c", cache.get(3));
    }

    @Test
    public void sizeIsOne() {
        LruCache<Integer, String> cache = new LruCache<>(1);
        cache.put(1, "a");
        cache.put(2, "b");
        assertNull(cache.get(1));
        assertEquals("b", cache.get(2));
    }

    @Test (expected = IllegalArgumentException.class)
    public void sizeIsNull() {
        new LruCache<Integer, String>(0);
    }

    @Test (expected = IllegalArgumentException.class)
    public void sizeIsNegative() {
        new LruCache<Integer, String>(-1);
    }

    @Test
    public void leetCode1() {
        LruCache<Integer, Integer> cache = new LruCache<>(2);

        cache.put(1, 1);
        cache.put(2, 2);
        assertThat(cache.get(1)).isEqualTo(1);
        cache.put(3, 3);    // evicts key 2
        assertThat(cache.get(2)).isNull();
        cache.put(4, 4);    // evicts key 1
        assertThat(cache.get(1)).isNull();
        assertThat(cache.get(3)).isEqualTo(3);
        assertThat(cache.get(4)).isEqualTo(4);
    }

    @Test
    public void doubleAddDoesntEvictTheValue() {
        LruCache<Integer, Integer> cache = new LruCache<>(2);

        cache.put(2, 6);
        cache.put(1, 5);
        cache.put(1, 2);
        assertThat(cache.get(2)).isEqualTo(6);
    }

    @Test
    public void leetCode2() {
        LruCache<Integer, Integer> cache = new LruCache<>(2);

        cache.put(2, 1);
        cache.put(1, 1);
        cache.put(2, 3);
        cache.put(4, 1);
        assertThat(cache.get(1)).isEqualTo(null);
        assertThat(cache.get(2)).isEqualTo(3);
    }

    @Test
    public void singleSizePut() {
        LruCache<Integer, Integer> cache = new LruCache<>(1);

        cache.put(2, 1);
        cache.put(1, 1);
        cache.put(2, 3);
        assertThat(cache.get(1)).isEqualTo(null);
        assertThat(cache.get(2)).isEqualTo(3);
    }
}
