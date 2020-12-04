//
//  MovidaDictionary.java
//  Movida
//
//  Created by Francesco D'Alessandro on 04/12/2020.
//  Copyright © 2020 Matteo Celani Francesco D'Alessandro. All rights reserved.
//

package movida.dalessandrocelani;

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

public class Node<K,V> {
    K key;
    V value;

    Node<K,V> next;

    public Node() {
        this.key = key;
        this.value = value;
    }

    public K getKey(){ return this.key; }
    public V getValue(){ return this.value; }
}
