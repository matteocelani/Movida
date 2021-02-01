package movida.dalessandrocelani;

import movida.commons.Movie;

public class QuickSort extends Sort{

    public void quicksort(Movie X[], int b, int e) {
        if (b<e) {
            int partitionIndex = partition(X, b, e);

            quicksort(X, b, partitionIndex-1);
            quicksort(X, partitionIndex+1, e);
        }
    }

    private int partition(Movie X[], int b, int e) {
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

    public void sort(Movie m[]) {
        this.quicksort(m, 0, m.length-1);
    }
}
