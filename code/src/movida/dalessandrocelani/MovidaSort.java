package movida.dalessandrocelani;

import movida.commons.Movie;

public interface MovidaSort {
    /**
     * Funzione che prende in imput un MovidaDictionary lo trasforma in un array
     * tramite l'algoritmo di ordinamento scelto ordina l'array poi lo ri inserisce nel database ordinato
     *
     * @param m lista di Movie
     * @return lista di Movie ordinata
     */
    MovidaDictionary MovidaSort(MovidaDictionary<String, Movie> m);
}