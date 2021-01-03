//
//  MovidaCore.java
//  Movida
//
//  Created by Matteo Celani on 07/10/2020.
//  Copyright © 2020 Matteo Celani Francesco D'Alessandro. All rights reserved.
//

package movida.dalessandrocelani;
import movida.commons.*;

import java.io.*;
import java.util.*;

/**
 * ************************************************
 * COMMENTI DA ELIMINARE ALLA CONSEGNA
 * ULTIMA MODIFICA: 03/01/2021
 * ************************************************
 *
 * TODO: IMovidaSearch (50%)
 *
 * ************************************************
 *  DATA ULTIMO TEST: 03/01/2021
 *  BUILD: Exception in thread "main" java.lang.NullPointerException: Cannot invoke "movida.commons.Movie.getTitle()" because the return value of "movida.dalessandrocelani.MovidaCore.getMovieByTitle(String)" is null
 * 	at movida.dalessandrocelani.MovidaCore.main(MovidaCore.java:249)
 * ************************************************
**/
public class MovidaCore implements IMovidaDB, IMovidaSearch {

    private DBUtils dbutils;
    private ListaCollegataNonOrdinata<String, Movie> movies;
    private ListaCollegataNonOrdinata<String, DetailPerson> people;

    MovidaCore() {
        this.dbutils = new DBUtils();
        this.movies = new ListaCollegataNonOrdinata<String, Movie>();
        this.people = new ListaCollegataNonOrdinata<String, DetailPerson>();
    }

    //Carica i dati da un file, organizzato secondo il formato MOVIDA
    public void loadFromFile(File f) {
        /**
         * Controllo le informazioni presenti sul DB e inserisco i dati:
         *      • Riempi il grafo delle collaborazioni
         *
         */

        //Carico i dati usando loadFilm()
        Movie[] mov = this.dbutils.loadFilm(f);
        for(Movie movie: mov) {

            String title = movie.getTitle().toLowerCase().trim().replaceAll("\\s", "");
            //Se il titolo è già presente: elimino il film con lo stesso titolo e carico il nuovo (aggiornamento)
            /*if ( !this.movies.containsKey(title) ){
                this.movies.remove(title);
            }*/
            this.movies.put(title, movie);

            //Inserisco il cast e il direttore

            //Direttore: se il direttore non è presente lo inserisco
            String director = movie.getDirector().getName().trim();
            if ( ! this.people.containsKey(director) ) {
                this.people.put(director, new DetailPerson(director, 0, false));
            }

            //Cast: controllo se la persona è già presente se è presente incremento il suo numero di film, altrimenti se non è presente la inserisco
            for (Person actor : movie.getCast()) {
                String currentActor = actor.getName();
                //Movie detailActor = this.people.get(currentActor);
                if ( ! this.people.containsKey(currentActor) ) {
                    this.people.put(currentActor, new DetailPerson(currentActor, 0, true));
                }
                else {
                    this.people.get(currentActor).addMovie();
                }
            }
        }

    }

    //Salva tutti i dati su un file.
    public void saveToFile(File f) {
        /**
         *  • Controllo i permessi --> Altrimenti eccezione
         *  • Inizializzo il file .txt
         *  • Inserisco i dati
         *  • Se non si riesce a salvare --> eccezione
         */
        Movie[] mov = this.movies.values().toArray(new Movie[0]);
        this.dbutils.save(f, mov);
    }

    //Cancella tutti i dati.
    public void clear() {
        this.dbutils = new DBUtils();
        this.movies = new ListaCollegataNonOrdinata<>();
        this.people = new ListaCollegataNonOrdinata<>();
        //TODO: cambiare con nuove strutture dati implementate e aggiungere il reset del grafico
    }

    //Restituisce il numero di film
    public int countMovies() {
        return this.movies.values().toArray().length;
    }

    //Restituisce il numero di persone
    public int countPeople() {
        return this.people.values().size();
    }

    //Cancella il film con un dato titolo, se esiste.
    public boolean deleteMovieByTitle(String title) {
        Movie tmp = this.movies.get(title);
        if(tmp != null){
            // Decremento il numero di film a cui ogni attore del cast ha partecipato
            for(Person p : tmp.getCast()){
                System.out.println(p);
                DetailPerson actor = this.people.get(p.getName());
                actor.removeMovie();
            }
            this.movies.remove(title);
            return true;
        } else
            return false;
    }

    //Restituisce il record associato ad un film
    public Movie getMovieByTitle(String title){
        return this.movies.get(title);
    }

    //Restituisce il record associato ad una persona, attore o regista
    public Person getPersonByName(String name) {
        return this.people.get(name);
    }

    //Restituisce il vettore di tutti i film
    public Movie[] getAllMovies() {
        return this.movies.values().toArray(new Movie[0]);
    }

    //Restituisce il vettore di tutte le persone
    public Person[] getAllPeople() {
        return this.people.values().toArray(new Person[0]);
    }

    //MOVIDA SEARCH
    //Ricerca film per titolo.
    @Override
    public Movie[] searchMoviesByTitle(String title) {
        ArrayList<Movie> x = new ArrayList<>();
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
        ArrayList<Movie> x = new ArrayList<>();
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
        ArrayList<Movie> x = new ArrayList<>();
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
        ArrayList<Movie> x = new ArrayList<>();
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
        return new Movie[0];
    }

    //Ricerca film più recenti.
    @Override
    public Movie[] searchMostRecentMovies(Integer N) {
        return new Movie[0];
    }

    //Ricerca gli attori più attivi.
    @Override
    public Person[] searchMostActiveActors(Integer N) {
        return new Person[0];
    }

    public static void main(String[] args) {
        MovidaCore prova = new MovidaCore();
        System.out.println("Inizio");
        //Test lettura file
        prova.loadFromFile(new File("/Users/matteocelani/Documents/GitHub/Movida/code/src/movida/commons/esempio-formato-daticopia.txt"));
        //prova.loadFromFile(new File("/home/francesco/IdeaProjects/Movida/code/src/movida/commons/esempio-formato-daticopia.txt"));

        //Test getMovieByTitle()
        System.out.println(prova.getMovieByTitle("taxidriver").getTitle());

        //Test getPersonByName()
        System.out.println(prova.getPersonByName("Toni Collette").getName());

        //Test getAllMovies()
        System.out.println(prova.getAllMovies()[5].getTitle());

        //Test getAllPeople()
        System.out.println(prova.getAllPeople()[5].getName());

        //Test countMovies()
        System.out.println(prova.countMovies());

        //Test countPeople()
        System.out.println(prova.countPeople());

        //Test deleteMovieByTitle()
        //prova.deleteMovieByTitle("capefear");
        //System.out.println(prova.countMovies());
        //System.out.println(prova.countPeople());

        //Test searchMoviesByTitle()
        System.out.println(prova.searchMoviesByTitle("Die Hard")[0].getTitle());

        //Test searchMoviesInYear()
        System.out.println(prova.searchMoviesInYear(1993)[0].getTitle());

        //Test searchMoviesDirectedBy()
        System.out.println(prova.searchMoviesDirectedBy("Brian De Palma")[0].getTitle());

        //Test searchMoviesStarredBy()
        System.out.println(prova.searchMoviesStarredBy("Robert De Niro")[1].getTitle());

        //Test salva nuovo file
        //prova.saveToFile(new File("/Users/matteocelani/Documents/GitHub/Movida/code/src/movida/commons/esempio-formato-daticopia.txt"));
        System.out.println("Fine");
    }
}
