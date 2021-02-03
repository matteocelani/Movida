//
//  MovidaDictionary.java
//  Movida
//
//  Created by Matteo Celani on 01/12/2021.
//  Copyright © 2020 Matteo Celani Francesco D'Alessandro. All rights reserved.
//

package movida.dalessandrocelani;

import movida.commons.Movie;

public interface Sort {
    MovidaDictionary sort(MovidaDictionary<String, Movie> m);
}
