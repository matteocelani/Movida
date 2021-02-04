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

        private Boolean is3Node() {
            return this.keyRight != null;
        }

        private Boolean is2Node() {
            return this.keyRight == null;
        }


        private int compareLeft(Node compare) {
            return this.keyLeft.toString().compareTo(compare.getKeyLeft().toString());
        }

        private int compareRight(Node compare) {
            return this.keyRight.toString().compareTo(compare.getKeyLeft().toString());
        }
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
    private Node support(Node current, Boolean flag) {
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
        if(current.compareLeft(insert) == 1){
            Node left = new Node(insert, null);
            Node right = new Node(support(current, false), null);
            newNode = new Node(support(current, true), null, left, right);

        } else if(current.compareLeft(insert) == -1){

            //Il nuovo elemento è maggiore rispetto all'elemento destro di current, ma minore rispetto l'elemento a destra. Il nuovo elemento va su.
            if(current.compareRight(insert) == 1){
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

    private Node addElement(Node current, Node newNode) {
        Node newParent = null;

        // Non siamo ancora nel livello più basso dell'albero
        if(current.isLeaf == false) {
            Node sonAscended = null;

            if (current.compareLeft(newNode) == 0 ||
                    (current.is3Node() && current.compareRight(newNode) == 0)) {

                // L'emento aggiunto già esiste. AGGIUNGERE UN MESSAGGIO DI ERRORE
            }

            // Il nuovo elemento è minore dell'elemento a sinistra
            else if (current.compareLeft(newNode) == 1) {

                sonAscended = addElement(current.left, newNode);

                // Case sonAscended != null --> L'elemento è stato aggiunto a un Nodo 3 (ci sono due elementi)
                if (sonAscended != null) { // A new node comes from the left branch

                    // Il nuovo elemento, in questo caso, è sempre minore rispetto a current.left
                    if (current.is2Node()) {

                        current.keyRight = current.keyLeft;  // sposto l'elemento a sinistra di current a destra
                        current.valueRight = current.valueLeft;
                        current.keyLeft = sonAscended.keyLeft;
                        current.valueLeft = sonAscended.valueLeft;
                        current.right = current.mid;
                        current.mid = sonAscended.mid;
                        current.left = sonAscended.left;
                    } else { // In this case we have a new split, so the current element in the left will go up

                        // Copio la parte destra del sottoalbero
                        Node rightCopy = new Node(support(current, false), null, current.mid, current.right);

                        // Creo la nuova struttura attaccando la parte destra
                        newParent = new Node(support(current, true), null, sonAscended, rightCopy);
                    }
                }
            } else if (current.is2Node() || (current.is3Node() && current.compareRight(newNode) == 1)) {

                    sonAscended = addElement(current.mid, newNode);

                    if (sonAscended != null) { // A new split

                        // The right element is empty, so we can set the ascended element in the left and the existing left element into the right
                        if (current.is2Node()) {
                            current.keyRight = sonAscended.keyLeft;
                            current.valueRight = sonAscended.valueLeft;
                            current.right = sonAscended.mid;
                            current.mid = sonAscended.left;
                        }
                        else { // Another case we have to split again

                            Node left = new Node(support(current, true), null, current.left, sonAscended.left);
                            Node mid = new Node(support(current, false), null, sonAscended.mid, current.right);
                            newParent = new Node(support(sonAscended, true), null, left, mid);
                        }
                    }
            } else if (current.is3Node() && current.compareRight(newNode) == -1) {

                    sonAscended = addElement(current.right, newNode);

                    if (sonAscended != null) { // Split, the right element goes up

                        Node leftCopy   = new Node(support(current, true), null, current.left, current.mid);
                        newParent       = new Node(support(current, false), null, leftCopy, sonAscended);
                    }
            }
        }
        else { // Siamo al livello più basso dell'albero

            // L'elemento già esiste
            if (current.compareLeft(newNode) == 0 || (current.is3Node() && current.compareRight(newNode) == 0)) {

                //AGGIUNGERE MESSAGGIO ERRORE
            }
            else if (current.is2Node()) { // an easy case, there is not a right element

                // if the current left element is bigger than the new one --> we shift the left element to the right
                if (current.compareLeft(newNode) == 1) {

                    current.keyRight = current.keyLeft;
                    current.valueRight = current.valueLeft;
                    current.keyLeft = newNode.keyLeft;
                    current.valueLeft = newNode.valueLeft;
                }
                // if the new element is bigger, we add it in the right directly
                else if (current.compareLeft(newNode) == -1){
                    current.keyRight = newNode.keyLeft;
                    current.valueRight = newNode.valueLeft;
                }
            }
            // Case 3-node: there are 2 elements in the node and we want to add another one. We have to split the node
            else newParent = split(current, newNode);
        }
        return newParent;
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
