package sidomik.samples.cache;

import java.util.*;

public class LruCache<K, V> {

    private final Map<K, Node<K, V>> items = new HashMap<>();
    private DoublyLinkedList<K, V> list = new DoublyLinkedList<>();

    private final int size;

    public LruCache(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("size should be > 0");
        }
        this.size = size;
    }

    public V get(K key) {
        Node<K, V> node = items.get(key);
        if (node == null) {
            return null;
        }

        list.removeNode(node);
        list.addNode(node);

        return node.value;
    }

    public void put(K key, V value) {
        Node<K, V> node = new Node<>(key, value);
        Node<K, V> prevValue = items.put(key, node);
        if (prevValue != null) {
            list.removeNode(prevValue);
        }
        list.addNode(node);

        // evict
        if (items.size() > size) {
            items.remove(list.last.key);
            list.removeLast();
        }
    }

    private static class DoublyLinkedList<K, V> {
        private Node<K, V> first;
        private Node<K, V> last;

        private void removeLast() {
            removeNode(last);
        }

        private void removeNode(Node<K, V> node) {
            Node<K, V> prev = node.previous;
            Node<K, V> next = node.next;

            if (next != null) {
                next.previous = prev;
            }
            if (prev != null) {
                prev.next = next;
            }
            if (node == first) {
                first = next;
            }
            if (node == last) {
                last = prev;
            }
        }

        private void addNode(Node<K, V> node) {
            node.next = first;
            node.previous = null;
            if (first != null) {
                first.previous = node;
            }
            first = node;
            if (last == null) {
                last = node;
            }
        }
    }

    private static class Node<K, V> {
        private final K key;
        private final V value;
        private Node<K, V> next;
        private Node<K, V> previous;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}