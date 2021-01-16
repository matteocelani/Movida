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
 * ULTIMA MODIFICA: 13/01/2021
 * ************************************************
 *
 *  ************************************************
 *  DATA ULTIMO TEST: 13/01/2021
 *  BUILD:
 *  ************************************************
 **/

public class ListaCollegataNonOrdinata<K,V> implements MovidaDictionary<K,V> {

    private Node<K,V> start;
    private int size;

    public ListaCollegataNonOrdinata() {
        size = 0;
        start = null;
    }

    public boolean containsKey(K key) {
        if (start == null) {       //Se la lista è vuota, start==null, allora ritorna false
            return false;
        } else {                    //Altrimenti scannerizzo la lista
            for(Node<K,V> iter=start; iter.next != null; iter = iter.next){
                if (key == iter.getKey()) {         //Se la chive in input corrisponde alla chiave del nodo corrente
                    return true;     //Ritorna true
                }
            }
        }

        return false;            //Caso lista non vuota, ma chiave non presente
    }

    @Override
    public void put(K key, V value) {
        Node<K,V> newNode = new Node<>();
        newNode.key = key;
        newNode.value = value;

        if (start == null) {        //Se la lista è vuota, start==null, allora il nostro nodo start punterà direttamente al nodo inserito
            start = newNode;
            size++;
            return;
        }
        newNode.next = null;
        Node<K,V> last = start;
        while (last.next != null) { //Scorro la lista fino a last.next!=null ,ci agganciamo al nodo che vogliamo inserire
            last = last.next;
        }
        last.next = newNode;
        size++;
        return;
    }

    @Override
    public V get(K key) {
        if (start == null) {       //Se la lista è vuota, start==null, allora ritorna null
            return null;
        } else {                    //Altrimenti scannerizzo la lista
            for(Node<K,V> iter=start; iter.next != null; iter = iter.next){
                if (key.equals(iter.getKey())) {         //Se la chive in input corrisponde alla chiave del nodo corrente
                    /**
                     *                 V find = (V) iter.getValue();
                     *                 return find;
                     */
                    return iter.getValue();     //Ritorna il valore del nodo corrente
                }
            }
        }

        return null;            //Caso lista non vuota, ma chiave non presente
    }

    @Override
    public void remove(K key) {
        Node<K,V> iter = start;
        /**
         * CASO 1: se l'elemento da eliminare è la testa, aggiorno il puntatore.
         **/
        if (iter != null && key.equals(iter.getKey())) {
            start = iter.next;
            size --;
            System.out.print("PORCO DIOOOOO");
        }
        /**
         * CASO 2: l'elemento su trova all'interno della lista:
         *      • scorro la lista
         *      • mantengo il puntatotore al nodo corrente e precedente
         *      • se trovo la chiave sposto il puntatore a quello successivo
         **/
        while (iter != null && !key.equals(iter.getKey())) {
            /**
             * se iter non contiene la chiave scorro al nodo successivo
             **/
            iter.prev = iter;
            iter = iter.next;
            System.out.print("PORCA MADONNA");
        }
        /**
         * se la chiave è presente si trova in iter, quindi non è null
         * quindi iter viene staccato dalla lista
         **/
        if (iter != null) {
            iter.prev = iter.next;
            size --;
        }
    }

    @Override
    public LinkedList<V> values() {
        LinkedList<V> values = new LinkedList<>();
        for(Node<K,V> iter=start; iter.next != null; iter = iter.next){
            values.add((V) iter.getValue());
        }
        return values;
    }

    public Set<K> keySet() {
        Set keys = new LinkedHashSet();
        for(Node<K,V> iter=start; iter.next != null; iter = iter.next){
            keys.add((K) iter.getKey());
        }
        return keys;
    }

    public void stampaLista() {
        for(Node<K,V> iter=start; iter.next != null; iter = iter.next) {
            System.out.println(iter.getKey());
            System.out.println(iter.getValue());
        }
    }

    @Override
    public int size() {
        return 0;
    }
}
