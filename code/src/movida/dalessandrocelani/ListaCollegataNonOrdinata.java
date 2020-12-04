//
//  MovidaDictionary.java
//  Movida
//
//  Created by Francesco D'Alessandro on 04/12/2020.
//  Copyright © 2020 Matteo Celani Francesco D'Alessandro. All rights reserved.
//

package movida.dalessandrocelani;

import java.util.ArrayList;

/**
 * ************************************************
 * COMMENTI DA ELIMINARE ALLA CONSEGNA
 * ULTIMA MODIFICA: 04/12/2020
 * ************************************************
 *
 *  ************************************************
 *  DATA ULTIMO TEST: MAI TESTATO
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

    @Override
    public void put(K key, V value) {
        Node<K,V> newNode = new Node<K,V>();
        newNode.key = key;
        newNode.value = value;

        if (start == null) {        //Se la lista è vuota, start==null ,allora il nostro nodo start punterà direttamente al nodo inserito
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
        return null;
    }

    @Override
    public V remove(K key) {
        return null;
    }

    @Override
    public ArrayList printAll() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }
}
