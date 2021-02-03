//
//  MovidaDictionary.java
//  Movida
//
//  Created by Francesco D'alessandro on 02/12/2021.
//  Copyright © 2020 Matteo Celani Francesco D'Alessandro. All rights reserved.
//

package movida.dalessandrocelani;

import java.util.LinkedList;
import java.util.Set;

public class Alberi23<K extends Comparable<K>,V> implements MovidaDictionary<K,V> {

    private class Node<K,V> {
        K key;
        V value;

        Node<K,V> left;
        Node<K,V> mid;
        Node<K,V> right;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = this.mid = this.right =  null;
        }

        public K getKey(){ return this.key; }
        public V getValue(){ return this.value; }
    }

    private Node<K,V> root;
    private int size;

    public Alberi23() {
        this.root = null;
        this.size = 0;
    }

    @Override
    public void put(K key, V value) {
    }

    @Override
    public V get(K key) {
        return null;
    }

    @Override
    public void remove(K key) {

    }

    @Override
    public LinkedList<V> values() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        return false;
    }

    @Override
    public void stampaLista() {

    }
}
