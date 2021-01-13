//
//  MovidaDictionary.java
//  Movida
//
//  Created by Francesco D'Alessandro on 04/12/2020.
//  Copyright © 2020 Matteo Celani Francesco D'Alessandro. All rights reserved.
//

package movida.dalessandrocelani;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * ************************************************
 * COMMENTI DA ELIMINARE ALLA CONSEGNA
 * ULTIMA MODIFICA: 03/01/2021
 * ************************************************
 *
 *  ************************************************
 *  DATA ULTIMO TEST: 03/01/2021
 *  BUILD: Exception in thread "main" java.lang.NullPointerException: Cannot invoke "movida.commons.Movie.getTitle()" because the return value of "movida.dalessandrocelani.MovidaCore.getMovieByTitle(String)" is null
 *  at movida.dalessandrocelani.MovidaCore.main(MovidaCore.java:249)
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
        Node<K,V> newNode = new Node<K,V>();
        newNode.key = key;
        newNode.value = value;

        if (start == null) {        //Se la lista è vuota, start==null, allora il nostro nodo start punterà direttamente al nodo inserito
            start = newNode;
            start.next = null;
        } else if (start.next==null) {                 //Se la lista contiene un solo nodo, start.next == null,allora agganciamo direttamente il nodo start al nodo inserito
            start.next = newNode;
        } else {                    //Se la lista contiene più di un nodo, start.next!=null ,allora iteriamo la lista fino all' ultimo nodo e lo agganciamo al nodo che vogliamo inserire
            Node iter = null;
            for(iter=start.next; iter.next != null; iter = iter.next);
            iter.next = newNode;
        }
        size++;
    }

    @Override
    public V get(K key) {
        if (start == null) {       //Se la lista è vuota, start==null, allora ritorna null
            return null;
        } else {                    //Altrimenti scannerizzo la lista
            for(Node<K,V> iter=start; iter.next != null; iter = iter.next){
                if (key == iter.getKey()) {         //Se la chive in input corrisponde alla chiave del nodo corrente
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
        Node<K,V> iter = start,
                prev = null;
        /**
         * CASO 1: se l'elemento da eliminare è la testa, aggiorno il puntatore.
         **/
        if (iter != null && iter.getKey() == key) {
            start = iter.next;
            size --;
        }
        /**
         * CASO 2: l'elemento su trova all'interno della lista:
         *      • scorro la lista
         *      • mantengo il puntatotore al nodo corrente e precedente
         *      • se trovo la chiave sposto il puntatore a quello successivo
         **/
        while (iter != null && iter.getKey() != key) {
            /**
             * se iter non contiene la chiave scorro al nodo successivo
             **/
            prev = iter;
            iter = iter.next;
        }
        /**
         * se la chiave è presente si trova in iter, quindi non è null
         * quindi iter viene staccato dalla lista
         **/
        if (iter != null) {
            prev.next = iter.next;
        }
    }

    @Override
    public ArrayList<V> values() {
        ArrayList<V> values = new ArrayList<>();
        for(Node<K,V> iter=start; iter.next != null; iter = iter.next){
            values.add((V) iter.getValue());
        }
        return values;
    }

    @Override
    public int size() {
        return 0;
    }
}
