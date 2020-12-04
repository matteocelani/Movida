//
//  MovidaDictionary.java
//  Movida
//
//  Created by Francesco D'Alessandro on 02/11/2020.
//  Copyright © 2020 Matteo Celani Francesco D'Alessandro. All rights reserved.
//  ...

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

public interface MovidaDictionary<K,V> {

    void put(K key, V value);

    V get(K key);

    V remove(K key);

    ArrayList<V> printAll();

    int size();
}