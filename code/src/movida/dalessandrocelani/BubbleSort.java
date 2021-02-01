package movida.dalessandrocelani;

import movida.commons.Movie;

public class BubbleSort extends Sort{

    public void BubbleSort(Movie X[]) {
        for (int i = 1; i < X.length; i++) {
            for (int j = 0; j <= X.length - 1; j ++) {
                if (X[j-1].compareTo(X[j]) > 0 ) {
                    Movie temp = X[j-1];
                    X[j-1] = X[j];
                    X[j] = temp;
                }
            }
        }
    }

    @Override
    public void sort(Movie m[]) {

    }
}
