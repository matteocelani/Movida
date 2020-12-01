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
 * ULTIMA MODIFICA: 09/11/2020
 * ************************************************
 * Finchè non si implementano tutte le classe in IMovidaDb il compilatore restituisce errore:
 * java: movida.dalessandrocelani.MovidaCore is not abstract and does not override abstract method getMovieByTitle(java.lang.String) in movida.commons.IMovidaDB
 *
 * Finchè le classi non hanno un tipo di ritorno il compilatore segna errore:
 * java: missing return statement
 *
 * Da Fare:
 * Implementare le funzioni presenti in IMovidaDB quindi di conseguenza implementare la classe DBUtils
 *
 * ************************************************
 *  DATA ULTIMO TEST: 09/11/2020, OK
 *  BUILD: OK
 * ************************************************
**/
public class MovidaCore implements IMovidaDB {

    private DBUtils dbutils;
    private HashMap<String, Movie> movies;
    private HashMap<String, DetailPerson> people;

    MovidaCore() {
        this.dbutils = new DBUtils();
        this.movies = new HashMap<String, Movie>();
        this.people = new HashMap<String, DetailPerson>();
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

    public void stampa(HashMap<String, Movie> films) {
        for (String film: films.keySet()){
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
        this.movies = new HashMap<>();
        this.people = new HashMap<>();
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
                DetailPerson actor = this.people.get(p.getName());
                actor.removeMovie();
                if(actor.getNumMov()==0){
                    this.people.remove(actor);
                }
            }
            this.movies.remove(tmp.getTitle());
            return true;
        } else
            return false;
    }

    //Restituisce il record associato ad un film
    public Movie getMovieByTitle(String title){ return this.movies.get(title); }

    //Restituisce il record associato ad una persona, attore o regista
    public Person getPersonByName(String name) { return this.people.get(name); }

    //Restituisce il vettore di tutti i film
    public Movie[] getAllMovies() { return this.movies.values().toArray(new Movie[0]); }

    //Restituisce il vettore di tutte le persone
    public Person[] getAllPeople() { return this.people.values().toArray(new Person[0]); }

    public static void main(String[] args) {
        MovidaCore prova = new MovidaCore();
        System.out.println("Hello, World!");
        prova.loadFromFile(new File("/Users/matteocelani/Documents/GitHub/Movida/code/src/movida/commons/esempio-formato-daticopia.txt"));
        prova.stampa(prova.movies);
        prova.saveToFile(new File("/Users/matteocelani/Documents/GitHub/Movida/code/src/movida/commons/esempio-formato-daticopia.txt"));
        System.out.println("Hello, World!");
    }
}
