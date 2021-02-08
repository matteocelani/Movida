package movida.dalessandrocelani;
import movida.commons.*;

import java.io.*;
import java.util.*;

public class MovidaCore implements IMovidaDB, IMovidaSearch, IMovidaConfig, IMovidaCollaborations {

    private DBUtils dbutils;
    private MovidaDictionary<String, Movie> movies;
    private MovidaDictionary<String, DetailPerson> people;
    private MovidaSort s;
    private MovidaGraph collaboration;

    /**
     * Il costuttore inizializza solo il Data Base e il grafico delle collaborazioni
     *
     * Movie e People potranno essere una ListaCollegataNonOrdinata oppure un Albero23.
     * L'inizialializzazione di queste variabili sarà fatta da setMap()
     *
     * S è un algoritmo di ordinamento generale, tramite setSort() riuscieremo a riordinare una
     * ListaCollegataNon Ordinata tramite QuickSort e BubbleSort
     */
    MovidaCore() {
        this.dbutils = new DBUtils();
        this.collaboration = new MovidaGraph();
    }

    //-------------------------------------------------------------
    //IMOVIDA DATABASE
    //-------------------------------------------------------------

    //Carica i dati da un file, organizzato secondo il formato MOVIDA
    @Override
    public void loadFromFile(File f) {
        /**
         * Controllo le informazioni presenti sul DB e inserisco i dati:
         *      • Riempi il grafo delle collaborazioni
         *
         */

        //Carico i dati usando loadFilm()
        Movie[] mov = this.dbutils.loadFilm(f);

        for(Movie movie: mov) {

            String keyTitle = movie.getTitle().toLowerCase().trim().replaceAll("\\s", "");
            //Se il titolo è già presente: elimino il film con lo stesso titolo e carico il nuovo (aggiornamento)
            /*if ( !this.movies.containsKey(title) ){
                this.movies.remove(title);
            }*/
            this.movies.put(keyTitle, movie);

            //Inserisco il cast e il direttore

            //Direttore: se il direttore non è presente lo inserisco
            String keyDirector = movie.getDirector().getName().toLowerCase().trim().replaceAll("\\s", "");
            String director = movie.getDirector().getName().trim();
            if ( ! this.people.containsKey(keyDirector) ) {
                this.people.put(keyDirector, new DetailPerson(director, 1, false));
            } else {
                this.people.get(keyDirector).addMovie();
            }

            //Cast: controllo se la persona è già presente se è presente incremento il suo numero di film, altrimenti se non è presente la inserisco
            for (Person actor : movie.getCast()) {
                String keyCurrentActor = actor.getName().toLowerCase().trim().replaceAll("\\s", "");
                String currentActor = actor.getName();
                //Movie detailActor = this.people.get(currentActor);
                if ( ! this.people.containsKey(keyCurrentActor) ) {
                    this.people.put(keyCurrentActor, new DetailPerson(currentActor, 1, true));
                }
                else {
                    this.people.get(keyCurrentActor).addMovie();
                }
            }
        }

        Movie[] collaborationMovie = this.movies.values().toArray(new Movie[0]);
        for (Movie m : collaborationMovie) {
            this.collaboration.loadCollaboration(m);
        }

    }

    //Salva tutti i dati su un file.
    @Override
    public void saveToFile(File f) {
        /**
         *  • Controllo i permessi --> Altrimenti eccezione
         *  • Inizializzo il file .txt
         *  • Inserisco i dati
         *  • Se non si riesce a salvare --> eccezione
         */
        Movie[] mov = this.movies.values().toArray(new Movie[0]);;
        this.dbutils.save(f, mov);
    }

    //Cancella tutti i dati.
    @Override
    public void clear() { new MovidaCore(); }

    //Restituisce il numero di film
    @Override
    public int countMovies() { return this.movies.size(); }

    //Restituisce il numero di persone
    @Override
    public int countPeople() { return this.people.size(); }

    //Cancella il film con un dato titolo, se esiste.
    @Override
    public boolean deleteMovieByTitle(String title) {
        Movie tmp = getMovieByTitle(title);
        if(tmp != null){
            // Decremento il numero di film a cui ogni attore del cast ha partecipato
            for(Person p : tmp.getCast()){
                DetailPerson actor = this.people.get(p.getName().toLowerCase().trim().replaceAll("\\s", ""));
                if (actor != null) {
                    actor.removeMovie();
                    if (actor.getNumMov() <= 0){
                        this.people.remove(actor.getName().toLowerCase().trim().replaceAll("\\s", ""));
                    }
                } else { this.people.dSize(); }
            }
            DetailPerson d = this.people.get(tmp.getDirector().getName().toLowerCase().trim().replaceAll("\\s", ""));
            if (d != null) {
                d.removeMovie();
                if ( d.getNumMov() == 0 ) {
                    this.people.remove(tmp.getDirector().getName().toLowerCase().trim().replaceAll("\\s", ""));
                }
            } else { this.people.dSize(); }
            this.movies.remove(title);
            return true;
        } else
            return false;
    }

    //Restituisce il record associato ad un film
    @Override
    public Movie getMovieByTitle(String title){ return this.movies.get(title); }

    //Restituisce il record associato ad una persona, attore o regista
    @Override
    public Person getPersonByName(String name) { return this.people.get(name); }

    //Restituisce il vettore di tutti i film
    @Override
    public Movie[] getAllMovies() { return this.movies.values().toArray(new Movie[0]); }

    //Restituisce il vettore di tutte le persone
    @Override
    public Person[] getAllPeople() { return this.people.values().toArray(new Person[0]); }

    //-------------------------------------------------------------
    //MOVIDA SEARCH
    //-------------------------------------------------------------
    //Ricerca film per titolo.
    @Override
    public Movie[] searchMoviesByTitle(String title) {
        LinkedList<Movie> x = new LinkedList<>();
        Movie[] m = this.movies.values().toArray(new Movie[0]);
        //Trasformo il titolo inserito
        String low = title.toLowerCase().trim().replaceAll("\\s","");

        for (Movie y: m){
            //Trasformo il titolo selezionato
            String ylow = y.getTitle().toLowerCase().trim().replaceAll("\\s","");

            if(ylow.contains(low)){     //Se il film è stato trovato, lo aggiunge al vettore vuoto
                x.add(y);
            }
        }
        return x.toArray(new Movie[0]);
    }

    //Ricerca film per anno.
    @Override
    public Movie[] searchMoviesInYear(Integer year) {
        LinkedList<Movie> x = new LinkedList<>();
        Movie[] m = this.movies.values().toArray(new Movie[0]);

        for (Movie y: m){
            if(y.getYear().equals(year)){     //Se il film è stato trovato, lo aggiunge al vettore vuoto
                x.add(y);
            }
        }
        return x.toArray(new Movie[0]);
    }

    //Ricerca film per regista.
    @Override
    public Movie[] searchMoviesDirectedBy(String name) {
        LinkedList<Movie> x = new LinkedList<>();
        Movie[] m = this.movies.values().toArray(new Movie[0]);
        //Trasformo il Regista inserito
        String low = name.toLowerCase().trim().replaceAll("\\s","");

        for (Movie y: m){
            //Trasformo il Regista selezionato
            String ylow = y.getDirector().getName().toLowerCase().trim().replaceAll("\\s","");

            if(ylow.contains(low)){     //Se il film è stato trovato, lo aggiunge al vettore vuoto
                x.add(y);
            }
        }
        return x.toArray(new Movie[0]);
    }

    //Ricerca film per attore.
    @Override
    public Movie[] searchMoviesStarredBy(String name) {
        LinkedList<Movie> x = new LinkedList<>();
        Movie[] m = this.movies.values().toArray(new Movie[0]);
        //Trasformo l'Attore inserito
        String low = name.toLowerCase().trim().replaceAll("\\s","");

        for (Movie y: m){
            Person[] ceckCast = y.getCast();
            for (Person a : ceckCast) {
                //Trasformo l'Attore selezionato
                String ylow = a.getName().toLowerCase().trim().replaceAll("\\s","");
                if(ylow.contains(low)){     //Se il film è stato trovato, lo aggiunge al vettore vuoto
                    x.add(y);
                }

            }
        }
        return x.toArray(new Movie[0]);
    }

    //Ricerca film più votati.
    @Override
    public Movie[] searchMostVotedMovies(Integer N) {
        LinkedList<Movie> x = new LinkedList<>();
        Movie[] list = this.movies.values().toArray(new Movie[0]);
        for (int i = 1; i < list.length; i++) {
            for (int j = 1; j <= list.length - 1; j ++) {
                if (list[j-1].getVotes() <= list[j].getVotes() ) {
                    Movie temp = list[j-1];
                    list[j-1] = list[j];
                    list[j] = temp;
                }
            }
        }
        for (int i = 0; i < N ; i++) {
            x.add(list[i]);
        }

        return x.toArray(new Movie[0]);
    }

    //Ricerca film più recenti.
    @Override
    public Movie[] searchMostRecentMovies(Integer N) {
        LinkedList<Movie> x = new LinkedList<>();
        Movie[] list = this.movies.values().toArray(new Movie[0]);
        for (int i = 1; i < list.length; i++) {
            for (int j = 1; j <= list.length - 1; j ++) {
                if (list[j-1].getYear() <= list[j].getYear() ) {
                    Movie temp = list[j-1];
                    list[j-1] = list[j];
                    list[j] = temp;
                }
            }
        }
        for (int i = 0; i < N ; i++) {
            x.add(list[i]);
        }

        return x.toArray(new Movie[0]);
    }

    //Ricerca gli attori più attivi.
    @Override
    public Person[] searchMostActiveActors(Integer N) {
        LinkedList<Person> x = new LinkedList<>();
        DetailPerson[] list = this.people.values().toArray(new DetailPerson[0]);
        for (int i = 1; i < list.length; i++) {
            for (int j = 1; j <= list.length - 1; j ++) {
                if (list[j-1].getNumMov() <= list[j].getNumMov() ) {
                    DetailPerson temp = list[j-1];
                    list[j-1] = list[j];
                    list[j] = temp;
                }
            }
        }
        for (int i = 0; i < N ; i++) {
            x.add(list[i]);
        }

        return x.toArray(new Person[0]);
    }

    //-------------------------------------------------------------
    //IMOVIDA CONFIG
    //-------------------------------------------------------------

    //Setta l'algoritmo di Ordinamento
    @Override
    public boolean setSort(SortingAlgorithm a) {
        if( a == SortingAlgorithm.BubbleSort){
            this.s = new BubbleSort();
            this.movies = s.MovidaSort(this.movies);
            System.out.print("Algoritmo di ordinamento trovato.\n");
            System.out.print("I Movie sono stati ordinati usando Bubble Sort.\n");
            return true;
        } else if( a == SortingAlgorithm.QuickSort){
            this.s = new QuickSort();
            this.movies = s.MovidaSort(this.movies);
            System.out.print("Algoritmo di ordinamento trovato.\n");
            System.out.print("I Movie sono stati ordinati usando QuickSort.\n");
            return true;
        } else{
            this.s = new QuickSort();
            this.movies = s.MovidaSort(this.movies);
            System.out.print("Algoritmo di ordinamento non trovato. \n");
            System.out.print("Algoritmo di ordinamento di default: QuickSort.\n");
        }
        return false;
    }

    //Setta l'Implementazione dei dati
    @Override
    public boolean setMap(MapImplementation m) {
        if(m == MapImplementation.ListaNonOrdinata){
            this.movies = new ListaCollegataNonOrdinata<>();
            this.people = new ListaCollegataNonOrdinata<>();
            System.out.print("Implmentazione trovata. \n");
            System.out.print("Implmentata una ListaNonOrdinata. \n");
            return true;
        } else if (m == MapImplementation.Alberi23) {
            this.movies = new Alberi23<>();
            this.people = new Alberi23<>();
            System.out.print("Implmentazione trovata. \n");
            System.out.print("Implmentato un Albero23.\n ");
            return true;
        } else {
            this.movies = new ListaCollegataNonOrdinata<>();
            this.people = new ListaCollegataNonOrdinata<>();
            System.out.print("Implmentazione non trovata. \n");
            System.out.print("Implementazione di default: Lista Collegata Non Ordinata.\n");
        }
        return false;
    }

    //-------------------------------------------------------------
    //IMOVIDA COLLABORATION
    //-------------------------------------------------------------

    //Restituisce gli attori che hanno fatto almeno un film con actor
    @Override
    public Person[] getDirectCollaboratorsOf(Person actor) { return collaboration.getDirectCollaborators(actor); }

    //Restituisce gli attori che hanno collaborazioni dirette e indiretta con actor
    @Override
    public Person[] getTeamOf(Person actor) { return collaboration.getTeam(actor); }

    //TODO: Inserire commento maximizeCollaborationsInTheTeamOf()
    @Override
    public Collaboration[] maximizeCollaborationsInTheTeamOf(Person actor) { return collaboration.maximizeCollaborationsInTheTeam(actor); }

    //-------------------------------------------------------------
    //IMOVIDA STAMPA
    //-------------------------------------------------------------

    public void stampa(MovidaDictionary<String, Movie> films) {
        int i =1;
        for (String film: films.keySet()){
            System.out.print(i + " - ");
            i++;
            String key = film;
            Movie value = films.get(film);
            Person[] cast = value.getCast();
            Person direttore = value.getDirector();
            System.out.println(key + " " + value.getTitle());
            System.out.println(key + " " + value.getYear());
            System.out.println(key + " " + cast[1].getName());
            System.out.println(key + " " + direttore.getName());
            System.out.println(key + " " + value.getVotes());
        }
    }

    //-------------------------------------------------------------
    //IMOVIDA MAIN
    //-------------------------------------------------------------

    public static void main(String[] args) {
        MovidaCore prova = new MovidaCore();
        MapImplementation m = MapImplementation.ListaNonOrdinata;
        prova.setMap(m);
        System.out.println("Inizio\n");
        System.out.println("--------------------------------------------------------- ");
        //Test lettura file
        prova.loadFromFile(new File("/Users/matteocelani/Documents/GitHub/Movida/code/src/movida/commons/esempio-formato-daticopia.txt"));
        //prova.loadFromFile(new File("/home/francesco/IdeaProjects/Movida/code/src/movida/commons/esempio-formato-daticopia.txt"));

        prova.stampa(prova.movies);

        //Test getMovieByTitle()
        System.out.println("\n\n" + "Test getMovieByTitle() " + prova.getMovieByTitle("taxidriver").getTitle());

        //Test getPersonByName()
        System.out.println("Test getPersonByName() " + prova.getPersonByName("tonicollette").getName());

        //Test getAllMovies()
        System.out.println("Test getAllMovies(): " + prova.getAllMovies()[4].getTitle());

        //Test getAllPeople()
        System.out.println("Test getAllPeople(): " + prova.getAllPeople()[5].getName());

        //Test countMovies()
        System.out.println("Test countMovies(): " + prova.countMovies());

        //Test countPeople()
        System.out.println("Test countPeople(): " + prova.countPeople());

        //Test searchMoviesByTitle()
        System.out.println("Test searchMoviesByTitle(): " + prova.searchMoviesByTitle("The Fugitive")[0].getTitle());

        //Test searchMoviesInYear()
        System.out.println("Test searchMoviesInYear(): " + prova.searchMoviesInYear(2000)[0].getTitle());

        //Test searchMoviesDirectedBy()
        System.out.println("Test searchMoviesDirectedBy(): " + prova.searchMoviesDirectedBy("Brian De Palma")[0].getTitle());

        //Test searchMoviesStarredBy()
        System.out.println("Test searchMoviesStarredBy(): " + prova.searchMoviesStarredBy("Robert De Niro")[1].getTitle());

        //Test searchMostVotedMovie()
        System.out.println("Test searchMostVotedMovie(): " + prova.searchMostVotedMovies(2)[0].getTitle());
        System.out.println("Test searchMostVotedMovie(): " + prova.searchMostVotedMovies(2)[1].getTitle());

        //Test searchMostActiveActor()
        System.out.println("Test searchMostActiveActor(): " + prova.searchMostActiveActors(2)[0].getName());
        System.out.println("Test searchMostActiveActor(): " + prova.searchMostActiveActors(2)[1].getName());

        System.out.println("\n");

        //Sort p = new QuickSort();

        //Test clear()
        //prova.clear();

        System.out.println("--------------------------------------------------------- ");
        System.out.println("\nTEST ALGORITMO DI SORTING");
        prova.setSort(SortingAlgorithm.BubbleSort);
        prova.stampa(prova.movies);

        System.out.println("--------------------------------------------------------- ");
        System.out.println("\nTEST COLLABORAZIONI ");

        //TEST COLLABORAZIONI

        //Test getDirectCollaboratorsOf()
        System.out.println("Test getDirectCollaboratorsOf(): " + prova.getDirectCollaboratorsOf(prova.getPersonByName("jodiefoster"))[0].getName());

        //Test getTeamOf()
        System.out.println("Test getTeamOf(): " + prova.getTeamOf(prova.getPersonByName("michellepfeiffer"))[0].getName());

        //Test maximizeCollaborationsInTheTeamOf()
        System.out.println("Test maximizeCollaborationsInTheTeamOf(): " + prova.maximizeCollaborationsInTheTeamOf(prova.getPersonByName("glennclose"))[0].getActorA().getName());

        System.out.println("--------------------------------------------------------- ");
        System.out.println("\nTEST DELETE ");
        //Test deleteMovieByTitle()
        prova.deleteMovieByTitle("thefugitive");
        prova.deleteMovieByTitle("capefear");
        prova.deleteMovieByTitle("diehard");
        prova.deleteMovieByTitle("scarface");
        prova.deleteMovieByTitle("airforceone");
        prova.deleteMovieByTitle("taxidriver");
        prova.deleteMovieByTitle("whatliesbeneath");
        prova.deleteMovieByTitle("thesixthsense");
        System.out.println(prova.countMovies());
        System.out.println(prova.countPeople());


        System.out.println("--------------------------------------------------------- ");
        System.out.println("\nFine ListaCollegataNonOrdinata ");
        System.out.println("--------------------------------------------------------- ");
        System.out.println("--------------------------------------------------------- ");
        System.out.println("--------------------------------------------------------- ");

        /**
         *
         * TEST ALBERI
         *
         */
        MapImplementation n = MapImplementation.Alberi23;
        prova.setMap(n);
        System.out.println("Inizio Test Funzioni Alberi");
        //Test lettura file
        prova.loadFromFile(new File("/Users/matteocelani/Documents/GitHub/Movida/code/src/movida/commons/esempio-formato-daticopia.txt"));
        //prova.loadFromFile(new File("/home/francesco/IdeaProjects/Movida/code/src/movida/commons/esempio-formato-daticopia.txt"));

        prova.stampa(prova.movies);

        //Test getMovieByTitle()
        System.out.println("\n\n" + "Test getMovieByTitle() " + prova.getMovieByTitle("taxidriver").getTitle());

        //Test getPersonByName()
        System.out.println("Test getPersonByName() " + prova.getPersonByName("tonicollette").getName());

        //Test getAllMovies()
        System.out.println("Test getAllMovies(): " + prova.getAllMovies()[4].getTitle());

        //Test getAllPeople()
        System.out.println("Test getAllPeople(): " + prova.getAllPeople()[5].getName());

        //Test countMovies()
        System.out.println("Test countMovies(): " + prova.countMovies());

        //Test countPeople()
        System.out.println("Test countPeople(): " + prova.countPeople());

        //Test searchMoviesByTitle()
        System.out.println("Test searchMoviesByTitle(): " + prova.searchMoviesByTitle("The Fugitive")[0].getTitle());

        //Test searchMoviesInYear()
        System.out.println("Test searchMoviesInYear(): " + prova.searchMoviesInYear(2000)[0].getTitle());

        //Test searchMoviesDirectedBy()
        System.out.println("Test searchMoviesDirectedBy(): " + prova.searchMoviesDirectedBy("Brian De Palma")[0].getTitle());

        //Test searchMoviesStarredBy()
        System.out.println("Test searchMoviesStarredBy(): " + prova.searchMoviesStarredBy("Robert De Niro")[1].getTitle());

        //Test searchMostVotedMovie()
        System.out.println("Test searchMostVotedMovie(): " + prova.searchMostVotedMovies(2)[0].getTitle());
        System.out.println("Test searchMostVotedMovie(): " + prova.searchMostVotedMovies(2)[1].getTitle());

        //Test searchMostActiveActor()
        System.out.println("Test searchMostActiveActor(): " + prova.searchMostActiveActors(2)[0].getName());
        System.out.println("Test searchMostActiveActor(): " + prova.searchMostActiveActors(2)[1].getName());
        System.out.println("\n");

        //Sort p = new QuickSort();

        //Test clear()
        //prova.clear();

        System.out.println("--------------------------------------------------------- ");
        System.out.println("\nTEST COLLABORAZIONI ");

        //TEST COLLABORAZIONI

        //Test getDirectCollaboratorsOf()
        System.out.println("Test getDirectCollaboratorsOf(): " + prova.getDirectCollaboratorsOf(prova.getPersonByName("jodiefoster"))[0].getName());

        //Test getTeamOf()
        System.out.println("Test getTeamOf(): " + prova.getTeamOf(prova.getPersonByName("michellepfeiffer"))[0].getName());

        //Test maximizeCollaborationsInTheTeamOf()
        System.out.println("Test maximizeCollaborationsInTheTeamOf(): " + prova.maximizeCollaborationsInTheTeamOf(prova.getPersonByName("glennclose"))[0].getActorA().getName());

        System.out.println("--------------------------------------------------------- ");
        System.out.println("\nTEST DELETE ");
        //Test deleteMovieByTitle()
        prova.deleteMovieByTitle("thefugitive");
        prova.deleteMovieByTitle("capefear");
        prova.deleteMovieByTitle("diehard");
        prova.deleteMovieByTitle("scarface");
        prova.deleteMovieByTitle("airforceone");
        prova.deleteMovieByTitle("taxidriver");
        prova.deleteMovieByTitle("whatliesbeneath");
        prova.deleteMovieByTitle("thesixthsense");
        System.out.println(prova.countMovies());
        System.out.println(prova.countPeople());

        System.out.println("--------------------------------------------------------- ");
        System.out.println("\nFine Alberi23 ");


        //Test salva nuovo file
        //prova.saveToFile(new File("/Users/matteocelani/Documents/GitHub/Movida/code/src/movida/commons/esempio-formato-daticopia.txt"));
        System.out.println("\n Fine");
    }
}
