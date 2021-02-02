//
//  MovidaDictionary.java
//  Movida
//
//  Created by Francesco D'Alessandro on 02/11/2020.
//  Copyright © 2020 Matteo Celani Francesco D'Alessandro. All rights reserved.
//  ...

package movida.dalessandrocelani;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;

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

public interface MovidaDictionary<K,V> {

    void put(K key, V value);

    V get(K key);

    void remove(K key);

    LinkedList<V> values();

    int size();

    Set<K> keySet();

    boolean containsKey(K key);

    void stampaLista();
}