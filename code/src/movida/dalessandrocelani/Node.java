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
 * ULTIMA MODIFICA: 03/01/2021
 * ************************************************
 *
 *  ************************************************
 *  DATA ULTIMO TEST: 03/01/2021
 *  BUILD: Exception in thread "main" java.lang.NullPointerException: Cannot invoke "movida.commons.Movie.getTitle()" because the return value of "movida.dalessandrocelani.MovidaCore.getMovieByTitle(String)" is null
 *  at movida.dalessandrocelani.MovidaCore.main(MovidaCore.java:249)
 *  ************************************************
 **/

public class Node<K,V> {
    K key;
    V value;

    Node<K,V> next;
    Node<K,V> prev;

    public Node() {
        this.key = key;
        this.value = value;
    }

    public K getKey(){ return this.key; }
    public V getValue(){ return this.value; }
}
