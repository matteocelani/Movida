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

    /**
     * Gli alberi 2-3 sono formati da nodi che contengono gli elementi della struttura.
     *
     * Ogni nodo contiene al massimo 2 elementi e minimo 1. Nel caso in cui c'è un solo elemento
     * sarà sempre a sinistra, in quel caso la destra è null.
     *
     * La struttura degli alberi 2-3 definisce due tipi di nodi/figli:
     *
     *  - Nodo 2: Questo nodo ha solo due figli, sempre a sinistra e al centro. L'elemento di destra è null e anche
     *            il figlio destro è null.
     *
     *  - Nodo 3: Questo nodo ha due elementi, quindi ha 3 figli: sinistro, centrale e destro. Quindi è pieno.
     */
    private class Node<K,V> {
        //Elemento di sinistra
        K keyLeft;
        V valueLeft;

        //Elemento di destra
        K keyRight;
        V valueRight;

        //Puntatori ai figli
        Node<K,V> left;
        Node<K,V> mid;
        Node<K,V> right;

        boolean isLeaf = false;

        /**
         * Costruttore di Base, costuisce un nodo vuoto
         */
        private Node() {
            this.keyLeft = this.keyRight = null;
            this.valueLeft = this.valueRight = null;
            this.left = this.mid = this.right = null;
        }

        /**
         * Costruttore Nodo Base: costruisce un nodo con l'elemento di sinistra pieno
         * e tutto il resto vuoto.
         * @param key
         * @param value
         */
        private Node(K key, V value) {
            this.keyLeft = key;
            this.valueLeft = value;
            this.keyRight = null;
            this.valueRight = null;
            this.left = this.mid = this.right = null;
        }

        /**
         * Costruttore Nodo3: costruisce un con due elementi ma senza figli (devo ancora definirli)
         *
         * @param leftElement
         * @param rightElement
         */
        private Node(Node leftElement, Node rightElement) {
            this.keyLeft = (K) leftElement.getKeyLeft();
            this.valueLeft = (V) leftElement.getValueLeft();

            this.keyRight = (K) rightElement.getKeyLeft();
            this.valueRight = (V) rightElement.getValueLeft();

            this.left = this.mid = this.right = null;
        }

        /**
         * Costruttore Nodo3: costruisce un nodo con due elementi e due due figli (sinistro e destro)
         *
         * @param leftElement
         * @param rightElement
         * @param left
         * @param mid
         */
        private Node(Node leftElement, Node rightElement, Node left, Node mid){
            this.keyLeft = (K) leftElement.getKeyLeft();
            this.valueLeft = (V) leftElement.getValueLeft();

            this.keyRight = (K) rightElement.getKeyLeft();
            this.valueRight = (V) rightElement.getValueLeft();

            this.left = left;
            this.mid = mid;

        }


        private K getKeyLeft(){ return this.keyLeft; }
        private V getValueLeft(){ return this.valueLeft; }
        private K getKeyRight(){ return this.keyRight; }
        private V getValueRight(){ return this.valueRight; }
    }

    private Node<K,V> root;
    private int size;

    public Alberi23() {
        this.root = new Node<>();
        this.size = 0;
    }

    /**
     * Prende in imput un nodo con due elementi
     * Crea un nuovo elemento
     * Popola il nuovo nodo con un solo elemento a sinistra
     *
     * @param current Nodo la quale vogliamo prendere solo un elemento
     * @param flag se è true prende l'elemento a sinistra di current s è false prende l'elemento a destra di current
     * @return il nodo con un solo elemento piazzato sempre a sinistra
     */
    public Node support(Node current, Boolean flag) {
        if (flag) {
            Node newNode = new Node(current.getKeyLeft(), current.getValueLeft());
            return newNode;
        } else {
            Node newNode = new Node(current.getKeyRight(), current.getValueRight());
            return newNode;
        }
    }

    public Node split(Node current, Node insert) {
        Node newNode = new Node();

        //L'elemento di sinistra è maggiore, quindi salirà lasciando l'elemento nuovo sulla sinistra
        if(current.getKeyLeft().toString().compareTo(insert.getKeyLeft().toString()) == 1){
            Node left = new Node(insert, null);
            Node right = new Node(support(current, false), null);
            newNode = new Node(support(current, true), null, left, right);

        } else if(current.getKeyLeft().toString().compareTo(insert.getKeyLeft().toString()) == -1){

            //Il nuovo elemento è maggiore rispetto all'elemento destro di current, ma minore rispetto l'elemento a destra. Il nuovo elemento va su.
            if(current.getKeyRight().toString().compareTo(insert.getKeyLeft().toString()) == 1){
                Node left = new Node(support(current, true), null);
                Node right = new Node(support(current, false), null);
                newNode = new Node(insert, null, left, right);

            } else { //Il nuovo elemento è il più grande, quindi l'elemento destro di current va su
                Node left = new Node(support(current, true), null);
                Node right = new Node(insert, null);
                newNode = new Node(support(current, false), null, left, right);
            }
        }
        return newNode;
    }

    @Override
    public void put(K key, V value) {

       /* if(this.root.leftElement == null){
            Node<K,V> newNode = new Node<>(key, value);
            this.root = newNode;
            this.size++;
        } else{
            Node newRoot= new Node();
            newRoot.left = this.root;
            this.root = newRoot;
            size++;
        }*/
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
