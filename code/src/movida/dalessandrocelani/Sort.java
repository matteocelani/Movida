package movida.dalessandrocelani;

import movida.commons.Movie;

public interface Sort {
    /**
     * Funzione che prende in imput un MovidaDictionary lo trasforma in un array
     * trmite l'algoritmo di ordinamento scelto ordina l'array poi lo ri inserisce nel database ordinato
     *
     * @param m lista di Movie
     * @return lista di Movie ordinata
     */
    MovidaDictionary sort(MovidaDictionary<String, Movie> m);
}
