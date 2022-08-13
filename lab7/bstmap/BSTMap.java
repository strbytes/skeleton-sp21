package bstmap;

import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

public class BSTMap<K extends Comparable, V> implements Map61B<K, V> {

    /* Pointer to the beginning of the BST. */
    private Node root;
    public int size;

    /** BST data structure. */
    private class Node {
        private K key;
        private V value;
        private Node left, right;

        public Node(K k, V v) {
            key = k;
            value = v;
            left = null;
            right = null;
        }

        @Override
        public String toString() {
            return "Node: " + key + " / " + value;
        }
    }

    public BSTMap() {
        root = null;
        size = 0;
    }

    /** Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /* Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) {
        return containsKey(root, key);
    }

    /** Returns true if this Node or any of it's children contain a mapping
     * for the specified key. */
    private boolean containsKey(Node n, K key) {
        if (n == null) {
            return false;
        }
        int comp = key.compareTo(n.key);
        if (comp < 0) {
            return containsKey(n.left, key);
        } else if (comp > 0) {
            return containsKey(n.right, key);
        }
        return true;
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return get(root, key);
    }

    /** Returns the value associated with the specified key if it is mapped,
     * or null.
     * @param n Node to be searched.
     * @param key Key to search for.
     */
    private V get(Node n, K key) {
        if (n == null) {
            return null;
        }
        int comp = key.compareTo(n.key);
        if (comp < 0) {
            return get(n.left, key);
        } else if (comp > 0) {
            return get(n.right, key);
        }
        return n.value;
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        root = put(root, key, value);
    }

    private Node put(Node n, K key, V value) {
        if (n == null) {
            size += 1;
            return new Node(key, value);
        }
        int comp = key.compareTo(n.key);
        if (comp < 0) {
            n.left = put(n.left, key, value);
        } else if (comp > 0) {
            n.right = put(n.right, key, value);
        } else {
            n.value = value;
        }
        return n;
    }

    /* Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException. */
    @Override
    public Set<K> keySet() {
        Set s = new HashSet();
        for (K key: this) {
            s.add(key);
        }
        return s;
    }

    /* Removes the mapping for the specified key from this map if present.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        V val = get(key);
        root = remove(root, key);
        if (val != null) {
            size -= 1;
        }
        return val;
    }

    public Node remove(Node n, K key) {
        if (n == null) {
            return null;
        }

        int comp = key.compareTo(n.key);
        if (comp < 0) {
            n.left = remove(n.left, key);
        } else if (comp > 0) {
            n.right = remove(n.right, key);
        } else {
            if (n.left == null) {
                return n.right;
            } else if (n.right == null) {
                return n.left;
            } else {
                Node temp = n.left;
                while (temp != null && temp.right != null) {
                    temp = temp.right;
                }
                remove(n, temp.key);
                n.key = temp.key;
                n.value = temp.value;
            }
        }
        return n;
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 7. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        if (get(key).equals(value)) {
            remove(key);
            return value;
        }
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return new BSTMapIterator();
    }

    private class BSTMapIterator implements Iterator<K> {
        K curr;
        K nextKey;
        K maxKey;

        BSTMapIterator() {
            curr = findMin(root);
            maxKey = findMax(root);
        }

        @Override
        public boolean hasNext() {
            return curr != null;
        }

        @Override
        public K next() {
            if (curr == null) {
                throw new NoSuchElementException();
            } else if (curr == maxKey) {
                curr = null;
                return maxKey;
            }
            nextKey = curr;
            curr = findNext(root, curr);
            return nextKey;
        }

        private K findMin(Node n) {
            if (n == null) {
                return null;
            }
            K min = findMin(n.left);
            if (min == null) {
                return n.key;
            }
            return min;
        }

        private K findMax(Node n) {
            if (n == null) {
                return null;
            }
            K max = findMax(n.right);
            if (max == null) {
                return n.key;
            }
            return max;
        }

        private K findNext(Node n, K k) {
            if (n == null) {
                return null;
            }
            int comp = k.compareTo(n.key);
            K next;
            if (comp < 0) {
                next = findNext(n.left, k);
            } else {
                next = findNext(n.right, k);
            }
            if (next == null || next.compareTo(k) <= 0) {
                return n.key;
            }
            return next;
        }
    }
}
