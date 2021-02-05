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

    /**
     * Inserimento del nodo all'interno del database
     *
     * @param key la chiave nel nodo da inserire
     * @param value i dati del nodo
     */
    void put(K key, V value);

    /**
     * Ricerca di un nodo all'interno del database
     *
     * @param key chiave del nodo da trovare
     * @return il valore del nodo se lo trova
     */
    V get(K key);

    /**
     * Rimuove un elemento dal database
     *
     * @param key chiave dell'elemento da rimuovere
     */
    void remove(K key);

    /**
     * Trasforma il database in una LinkedList<V>
     *
     * @return ritorna tutti i valori collegati con una LinkedList
     */
    LinkedList<V> values();

    /**
     * Determina la grandezza del database
     *
     * @return numero di nodi presenti
     */
    int size();

    /**
     * @return una vista dell'insieme delle chiavi contenute nel databse
     */
    Set<K> keySet();

    /**
     * Determina se la chiave è presente nel database
     *
     * @param key la chiave da cercare
     * @return true se la chiave è presente, false atrimenti
     */
    boolean containsKey(K key);

}