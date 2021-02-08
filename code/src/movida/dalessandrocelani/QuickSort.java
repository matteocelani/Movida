package movida.dalessandrocelani;

import movida.commons.Movie;

public class QuickSort implements MovidaSort{

    private void QuickSort(Movie X[], int b, int e) {
        if (b<e) {
            int partitionIndex = this.partition(X, b, e);

            QuickSort(X, b, partitionIndex-1);
            QuickSort(X, partitionIndex+1, e);
        }
    }

    /**
     * Questa funzione prende il pivot come ultimo elemento. Quindi, controlla ogni elemento e lo scambia
     * prima del pivot se il suo valore è inferiore.
     *
     * Alla fine del partizionamento, tutti gli elementi minori del pivot si trovano a sinistra di esso e tutti gli elementi
     * più grandi del pivot si trovano a destra di esso.
     * Il pivot si trova nella sua posizione ordinata finale e la funzione restituisce questa posizione.
     *
     * @param X
     * @param b
     * @param e
     * @return
     */
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

    @Override
    public MovidaDictionary MovidaSort(MovidaDictionary<String, Movie> m) {

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
