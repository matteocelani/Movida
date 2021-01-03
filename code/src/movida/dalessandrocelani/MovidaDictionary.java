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

public interface MovidaDictionary<K,V> {

    void put(K key, V value);

    V get(K key);

    void remove(K key);

    LinkedList<V> values();

    int size();
}