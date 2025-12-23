package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V>  {

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
    private int initialSize;
    private double loadFactor;
    private HashSet<K> keys;
    private int size;  //记录的是整个表格的keys数量；
    // You should probably define some more!

    /** Constructors */
    public MyHashMap() {
        buckets = new Collection[16];
        initialSize = 16;
        loadFactor = 0.75;
        size = 0;
        keys = new HashSet<>();
    }

    public MyHashMap(int initialSize) {
        buckets = new Collection[initialSize];
        loadFactor = 0.75;
        size = 0;
        keys = new HashSet<>();
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        buckets = new Collection[initialSize];
        loadFactor = maxLoad;
        size = 0;
        keys = new HashSet<>();
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
        return new Collection[tableSize];
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!

    /** Removes all of the mappings from this map. */
    @Override
    public void clear() {
        for (int i = 0; i < buckets.length; i++) {
            if (buckets[i] != null) {
                // 这里用的clear() 是collection的方法；
                buckets[i].clear();
            }
        }
        size = 0;
        keys.clear();
    }

    /** Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) {
        return keys.contains(key);
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        int index = index(key);
        Collection<Node> bucket = buckets[index];

        //如果 bucket 为空，初始化一个新bucket。
        if (bucket == null) {
            bucket = createBucket();
            buckets[index] = bucket;
        }

        for (Node node : bucket) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }
        return null;
    }

    /** 对hashCode进行取模后，再映射到桶索引。
     * key快速获取index。
     * */
    private int index(K key) {
        int h = key.hashCode();
        h ^= (h >>> 16);
        return h & (this.buckets.length - 1);
    }

    /** 对hashCode进行取模后，再映射到桶索引。
     * 扩容后，重新计算index用。
     * */
    private int index(K key, int length) {
        int h = key.hashCode();
        h ^= (h >>> 16);
        return h & (length - 1);
    }


    /** Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key,
     * the old value is replaced.
     */

    @Override
    public void put(K key, V value) {
        if (isOverload()) {
            resize();
        }
        int index = index(key);
        Collection<Node> bucket = buckets[index];
        //如果 bucket 为空，初始化一个新bucket。
        if (bucket == null) {
            bucket = createBucket();
            buckets[index] = bucket;
        }
        for (Node node : bucket) {
            if (node.key.equals(key)) {
                node.value = value;
                return;
            }
        }
        bucket.add(createNode(key, value));
        keys.add(key);
        size++;
    }

    /** 判断负载率是否超过loadFactor；
     * return ture 当超过时。
     */
    private boolean isOverload() {
        double  newloadFactor = size / buckets.length ;
        return newloadFactor >= loadFactor;
    }

    /** 增加buckets的数量。*/

    private void resize() {
        Collection<Node>[] newbuckets = createTable(buckets.length * 2);
        for (int i = 0; i < buckets.length; i++) {
            if (buckets[i] != null) {
                for (Node node : buckets[i]) {
                    //计算新索引
                    int newIndex = index(node.key, newbuckets.length);
                    if (newbuckets[newIndex] == null) {
                        newbuckets[newIndex] = createBucket();
                    }
                    newbuckets[newIndex].add(node);
                }
            }
        }
    }

    /** Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        return keys;
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.
     */
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        return new MyHashMapIterator();
    }

    private class MyHashMapIterator implements Iterator<K> {
        private Collection<Node>[] b;
        private int wisPos;  //是bucket的index，追踪到哪个bucket了。
        private Iterator<Node> curriter; // 是每个bucket里面的迭代器，是实例化的数据结构自带的。


        public MyHashMapIterator() {
            b = buckets;
            wisPos = 0;
            curriter = null;
        }


        @Override
        public boolean hasNext() {
            //跳过空的bucket，找到一个有元素的bucket。
            while (wisPos < b.length) {
                //确保bucket已经初始化 且不为空
                if (!b[wisPos].isEmpty()) {
                    return true;
                }
                wisPos++;
            }
            return false;
        }

        @Override
        public K next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return curriter.next().key;
        }
    }



}
