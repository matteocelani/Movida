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
 * ULTIMA MODIFICA: 18/01/2021
 * ************************************************
 *
 *  ************************************************
 *  DATA ULTIMO TEST: 18/01/2021
 *  BUILD:
 *  ************************************************
 **/

public class Node<K,V> {
    K key;
    V value;

    Node<K,V> next;
    Node<K,V> prev;

    public Node(K key, V value) {
        this.key = key;
        this.value = value;
        this.next = null;
    }

    public K getKey(){ return this.key; }
    public V getValue(){ return this.value; }
}
