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
import java.util.*;         //é grigia perche ancora non la utilizziamo!

/**
 * ************************************************
 * COMMENTI DA ELIMINARE ALLA CONSEGNA
 * ULTIMA MODIFICA: 27/10/2020
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
 *  DATA ULTIMO TEST: MAI TESTATO
 *  BUILD:  java: missing return statement
 * ************************************************
**/
public class MovidaCore implements IMovidaDB {
    private DBUtils dbutils;

    MovidaCore() {
        this.dbutils = new DBUtils();
    }

    //Carica i dati da un file, organizzato secondo il formato MOVIDA
    public void loadFromFile(File f) {
        /**
         * Carico i dati usando loadfilm()
         * Controllo le informazioni presenti sul DB e inserisoc i dati:
         *      • Se il titolo è già presente: elimino il film con lo stesso titolo e carico il nuovo (aggiornamento)
         *      • Inserisco il cast e il direttore:
         *              - Direttore: se il direttore non è presente lo inserisco
         *              - Cast: controllo se la persona è già presente se è presente incremento il suo numero di film, altrimenti
         *                se non è presente la inserisco
         *      • Riempi il grafo delle collaborazioni
         *
         */
        Movie[] mov = this.dbutils.loadFilm(f);
    }

    //Salva tutti i dati su un file.
    public void saveToFile(File f) {
        /**
         *  • Controllo i permessi --> Altrimenti eccezione
         *  • Inizializzo il file .txt
         *  • Inserisco i dati
         *  • Se non si riesce a salvare --> eccezione
         */
    }

    //Cancella tutti i dati.
    public void clear() {
    }

    //Restituisce il numero di film
    public int countMovies() {
    }

    //Restituisce il numero di persone
    public int countPeople() {
    }

    //Cancella il film con un dato titolo, se esiste.
    public boolean deleteMovieByTitle(String title) {
    }

    //Restituisce il record associato ad un film
    public Movie getMovieByTitle(String title){
    }

    //Restituisce il record associato ad una persona, attore o regista
    public Person getPersonByName(String name) {
    }

    //Restituisce il vettore di tutti i film
    public Movie[] getAllMovies() {
    }

    //Restituisce il vettore di tutte le persone
    public Person[] getAllPeople() {
    }

    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
