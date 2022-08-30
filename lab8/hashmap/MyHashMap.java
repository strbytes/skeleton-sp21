package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private final double loadFactor;
    private int size;

    /** Constructors */
    public MyHashMap() {
        buckets = createTable(16);
        loadFactor = 0.75;
    }

    public MyHashMap(int initialSize) {
        buckets = createTable(initialSize);
        loadFactor = 0.75;
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        buckets = createTable(initialSize);
        loadFactor = maxLoad;
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        size = 0;
        return new Collection[tableSize];
    }

    /** Removes all of the mappings from this map. */
    public void clear() {
        buckets = createTable(buckets.length);
        size = 0;
	}

    /** Returns true if this map contains a mapping for the specified key. */
    public boolean containsKey(K key) {
        int b = Math.floorMod(key.hashCode(), buckets.length);
        if (buckets[b] != null) {
            for (Node n : buckets[b]) {
                if (key.equals(n.key)) {
                    return true;
                }
            }
        }
        return false;
	}

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    public V get(K key) {
        int b = Math.floorMod(key.hashCode(), buckets.length);
        if (buckets[b] != null) {
            for (Node n : buckets[b]) {
                if (key.equals(n.key)) {
                    return n.value;
                }
            }
        }
        return null;
	}

    /** Returns the number of key-value mappings in this map. */
    public int size() {
        return size;
	}

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key,
     * the old value is replaced.
     */
    public void put(K key, V value) {
        int b = Math.floorMod(key.hashCode(), buckets.length);
        if (buckets[b] == null) {
            buckets[b] = createBucket();
            buckets[b].add(createNode(key, value));
            size += 1;
        } else {
            boolean found = false;
            for (Node n : buckets[b]) {
                if (n.key.equals(key)) {
                    n.value = value;
                    found = true;
                }
            }
            if (!found) {
                buckets[b].add(createNode(key, value));
                size += 1;
            }
        }
        if ((float) size / buckets.length > loadFactor) {
            resize(buckets.length * 2);
        }
	}

    /** Returns a Set view of the keys contained in this map. */
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        for (K k : this) {
            keys.add(k);
        }
        return keys;
	}

    /**
     * Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    public V remove(K key) {
        int b = Math.floorMod(key.hashCode(), buckets.length);
        V returnVal = null;
        if (buckets[b] != null) {
            for (Node n : buckets[b]) {
                if (key.equals(n.key)) {
                    returnVal = n.value;
                    buckets[b].remove(n);
                    size -= 1;
                }
            }
        }
        return returnVal;
	}

    /**
     * Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.
     */
    public V remove(K key, V value) {
        int b = Math.floorMod(key.hashCode(), buckets.length);
        V returnVal = null;
        if (buckets[b] != null) {
            for (Node n : buckets[b]) {
                if (key.equals(n.key) && value.equals(n.value)) {
                    returnVal = n.value;
                    buckets[b].remove(n);
                    size -= 1;
                }
            }
        }
        return returnVal;
	}

    private void resize(int s) {
        Set<Node> vals = new HashSet<>();
        for (Collection<Node> b : buckets) {
            if (b != null) {
                vals.addAll(b);
            }
        }
        buckets = createTable(s);
        for (Node n : vals) {
            put(n.key, n.value);
        }
    }

    @Override
    public Iterator<K> iterator() {
        return new MyHashMapIterator();
    }

    private class MyHashMapIterator implements Iterator<K> {
        /** Current bucket being iterated over. */
        int b;
        /** Iterator of bucket being iterated over. */
        Iterator<Node> iter;

        public MyHashMapIterator() {
            b = 0;
        }

        @Override
        public boolean hasNext() {
            while (b < buckets.length) {
                if (iter != null && iter.hasNext()) {
                    return true;
                } else if (buckets[b] != null) {
                    iter = buckets[b].iterator();
                }
                b += 1;
            }
            return false;
        }

        @Override
        public K next() {
            while (b < buckets.length) {
                if (iter != null && iter.hasNext()) {
                    return iter.next().key;
                }
                b += 1;
                if (buckets[b] != null) {
                    iter = buckets[b].iterator();
                }
            }
            throw new NoSuchElementException();
        }
    }
}
