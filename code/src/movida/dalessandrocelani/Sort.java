package movida.dalessandrocelani;

import movida.commons.Movie;

public interface Sort {
    MovidaDictionary sort(MovidaDictionary<String, Movie> m);
}
