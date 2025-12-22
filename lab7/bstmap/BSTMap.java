package bstmap;

import java.util.*;




public class BSTMap<K extends Comparable<K>,V> implements Map61B<K, V> {

    private class BSTNode {
        private K key;
        private V value;
        private BSTNode left;
        private BSTNode right;

        private BSTNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
        }

        /** 给BSTNode节点排序，为了BSTMap实现iterator。
         * @return 排序好的 List
         */

        private List<BSTNode> nodesInOrder() {
            List<BSTNode> keys = new ArrayList<>();
            if (left != null) {
                keys.addAll(left.nodesInOrder());
            }
            keys.add(this);
            if (right != null) {
                keys.addAll(right.nodesInOrder());
            }
            return keys;
        }
    }

    private BSTNode root;
    private int size;

    public BSTMap() {
        clear();
    }

    /** Removes all of the mappings from this map. */
    @Override
    public void clear(){
        this.root = null;
        this.size = 0;
    }

    /* Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) {
        return bSearch(root,key) != null;
    }


     /** 搜索key所在的node，不存在返回null。*/
    private BSTNode bSearch(BSTNode node, K key) {
        if (node== null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            return node;
        } else if (cmp < 0) {
            return bSearch(node.left, key);
        } else {
            return bSearch(node.right, key);
        }

    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if (this.root == null) {
            return null;
        }
        BSTNode node = bSearch(this.root, key);
        if (node != null) {
            return node.value;
        }
        return null;
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return this.size;
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        root = put(root, key, value);
    }
    private BSTNode put(BSTNode node, K key, V value) {
        if (node == null) {
            size++;
            return new BSTNode(key, value);
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = put(node.left, key, value);
        } else if (cmp > 0) {
            node.right = put(node.right, key, value);
        }  else {
            node.value = value;
        }
        return node;
    }


    /* Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException. */
    @Override
    public Set<K> keySet() {
        if (this.root == null) {
            return null;
        }
        Set<K> keys = new TreeSet<>();
        for (BSTNode node : this.root.nodesInOrder()) {
            keys.add(node.key);
        }
        return keys;
    }

    public void printInOrder() {
        StringBuilder vals = new StringBuilder();
        for (BSTNode node : this.root.nodesInOrder()) {
            vals.append(node.value.toString());
        }
        System.out.println(vals);
    }

    /* Removes the mapping for the specified key from this map if present.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        V removeValue = get(key);
        root = remove(root, key);
        return removeValue;
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 7. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        V removeValue = get(key);
        if (removeValue == value) {
            root = remove(root, key);
            return removeValue;
        }
        return null;
    }


    /* helper方法，remove
     */
    private BSTNode remove(BSTNode node, K key) {
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = remove(node.left, key);
        }  else if (cmp > 0) {
            node.right = remove(node.right, key);
        } else{
            size--;
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }
            BSTNode successor = findSmallest(node.right);
            node.key = successor.key;
            node.value = successor.value;
            node.right = deleteSmallest(node.right);
        }
        return node;
    }

    /** 删除最小的node。
     * return 删除最小值后的node。
     */
    private BSTNode deleteSmallest(BSTNode node) {
        if (node == null) {return null;}
        if (node.left == null) {return node.right;}
        node.left = deleteSmallest(node.left);
        return node;
    }



    /** 找到并返回最小的node。
     * 在树的最左边
     * @return
     */
    private BSTNode findSmallest(BSTNode node) {
        if (node.left == null) {
            return node;
        }
        return findSmallest(node.left);
    }


    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }
}