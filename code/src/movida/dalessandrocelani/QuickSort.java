package movida.dalessandrocelani;

import movida.commons.Movie;

public class QuickSort implements Sort{

    public void QuickSort(Movie X[], int b, int e) {
        if (b<e) {
            int partitionIndex = this.partition(X, b, e);

            QuickSort(X, b, partitionIndex-1);
            QuickSort(X, partitionIndex+1, e);
        }
    }

    int partition(Movie X[], int b, int e) {
        Movie pivot = X[e];
        int i = b-1;

        for (int j = b ; j < e ; j++ ) {
            if (X[j].compareTo(pivot) <= 0 ) {
                i ++;
                Movie temp = X[i];
                X[i] = X[j];
                X[j] = temp;
            }
        }

        Movie temp = X[i+1];
        X[i+1] = X[e];
        X[e] = temp;

        return i+1;
    }

    @Override
    public MovidaDictionary sort(MovidaDictionary<String, Movie> m) {

        Movie[] x = m.values().toArray(new Movie[0]);
        this.QuickSort(x, 0, x.length-1);

        m = new ListaCollegataNonOrdinata<>();

        for(int i=0 ; i<= x.length-1 ; i++) {

            String keyTitle = x[i].getTitle().toLowerCase().trim().replaceAll("\\s", "");
            m.put(keyTitle, x[i]);
        }
        return m;
    }
}
