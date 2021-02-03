//
//  MovidaDictionary.java
//  Movida
//
//  Created by Matteo Celani on 01/12/2021.
//  Copyright © 2020 Matteo Celani Francesco D'Alessandro. All rights reserved.
//

package movida.dalessandrocelani;

import movida.commons.Movie;

public class BubbleSort implements Sort{

    public void BubbleSort(Movie X[]) {
        for (int i = 1; i < X.length; i++) {
            for (int j = 1; j <= X.length - 1; j ++) {
                if (X[j-1].compareTo(X[j]) > 0 ) {
                    Movie temp = X[j-1];
                    X[j-1] = X[j];
                    X[j] = temp;
                }
            }
        }
    }

    @Override
    public MovidaDictionary sort(MovidaDictionary<String, Movie> m) {

        Movie[] x = m.values().toArray(new Movie[0]);
        this.BubbleSort(x);

        m = new ListaCollegataNonOrdinata<>();

        for(int i=0 ; i<= x.length-1 ; i++) {

            String keyTitle = x[i].getTitle().toLowerCase().trim().replaceAll("\\s", "");
            m.put(keyTitle, x[i]);
        }
        return m;
    }
}
