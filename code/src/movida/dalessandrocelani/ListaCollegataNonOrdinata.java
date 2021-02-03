//
//  MovidaDictionary.java
//  Movida
//
//  Created by Francesco D'Alessandro on 04/12/2020.
//  Copyright © 2020 Matteo Celani Francesco D'Alessandro. All rights reserved.
//

package movida.dalessandrocelani;

import java.security.Key;
import java.util.*;

/**
 * ************************************************
 * COMMENTI DA ELIMINARE ALLA CONSEGNA
 * ULTIMA MODIFICA: 18/01/2021
 * ************************************************
 *
 *  ************************************************
 *  DATA ULTIMO TEST: 18/01/2021
 *  BUILD:
 *  ************************************************
 **/

public class ListaCollegataNonOrdinata<K,V> implements MovidaDictionary<K,V> {

    private class Node<K,V> {
        K key;
        V value;

        Node<K,V> next;

        private Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }

        private K getKey(){ return this.key; }
        private V getValue(){ return this.value; }
    }

    private Node<K,V> start;
    private int size;

    public ListaCollegataNonOrdinata() {
        this.size = 0;
        this.start = null;
    }

    @Override
    public boolean containsKey(K key) {
        if (this.start == null) {       //Se la lista è vuota, start==null, allora ritorna false
            return false;
        } else {                    //Altrimenti scannerizzo la lista
            Node<K,V> iter;
            for(iter=this.start; iter != null; iter = iter.next){
                if (key.equals(iter.getKey())) {         //Se la chive in input corrisponde alla chiave del nodo corrente
                    return true;                         //Ritorna true
                }
            }
        }

        return false;            //Caso lista non vuota, ma chiave non presente
    }

    @Override
    public void put(K key, V value) {
        Node<K,V> newNode = new Node<>(key, value);

        //System.out.print(newNode.getKey() + " - " + newNode.getValue() + "\n" );

        if (this.start == null) {        //Se la lista è vuota, start==null, allora il nostro nodo start punterà direttamente al nodo inserito
            this.start= newNode;
            this.size++;
            return;
        }
        //Scorro la lista fino a iter.next==null ,ci agganciamo al nodo che vogliamo inserire
        Node<K,V> iter = start;
        while (iter.next != null) {
            iter = iter.next;
            //System.out.print(iter.getKey() +" - ");
        }
        newNode.next = null;
        iter.next = newNode;
        this.size++;
    }
    @Override
    public V get(K key) {
        if (this.start == null) {       //Se la lista è vuota, start==null, allora ritorna null
            return null;
        } else {                    //Altrimenti scannerizzo la lista
            Node<K,V> iter;
            for(iter=this.start; iter != null; iter = iter.next){
                if (key.equals(iter.getKey())) {         //Se la chive in input corrisponde alla chiave del nodo corrente
                    return iter.getValue();     //Ritorna il valore del nodo corrente
                }
            }
        }

        return null;            //Caso lista non vuota, ma chiave non presente
    }

    @Override
    public void remove(K key) {
        Node<K,V> iter = this.start;
        /**
         * CASO 1: se l'elemento da eliminare è la testa, aggiorno il puntatore.
         **/
        if (iter != null && key.equals(iter.getKey())) {
            this.start = iter.next;
            this.size --;
            return;
        }
        /**
         * CASO 2: l'elemento su trova all'interno della lista:
         *      • scorro la lista
         *      • mantengo il puntatotore al nodo corrente e precedente
         *      • se trovo la chiave sposto il puntatore a quello successivo
         **/
        while (iter.next != null && !key.equals(iter.next.getKey())) {
            /**
             * se iter non contiene la chiave scorro al nodo successivo
             **/
            iter = iter.next;
        }
        /**
         * se la chiave è presente si trova in iter, quindi non è null
         * quindi iter viene staccato dalla lista
         **/
        if (iter != null && iter.next.next == null) {
            iter.next.next = null;
            iter.next = iter.next.next;
            this.size --;
        } else if (iter != null && iter.next.next != null) {
            iter.next = iter.next.next;
            this.size --;
        }
    }

    @Override
    public LinkedList<V> values() {
        LinkedList<V> values = new LinkedList<>();
        Node<K,V> iter = this.start;
        while ( iter.next != null ) {
            values.add((V) iter.getValue());
            iter = iter.next;
        }
        values.add((V) iter.getValue());
        return values;
    }

    public Set<K> keySet() {
        Set keys = new LinkedHashSet();
        Node<K,V> iter;
        for( iter= this.start; iter != null; iter = iter.next){
            keys.add((K) iter.getKey());
        }
        return keys;
    }

    public void stampaLista() {
        System.out.println("\n -------- STAMPA LISTA ---------");

        Node<K,V> iter = this.start;

        while (iter.next != null) {
            System.out.println(iter.getKey() + " - " + iter.getValue() );
            iter = iter.next;
        }
        System.out.println(iter.getKey() + " - " + iter.getValue() );

        System.out.println("\n ------ FINE STAMPA LISTA ------- \n");
    }

    @Override
    public int size() {
        return this.size;
    }
}
