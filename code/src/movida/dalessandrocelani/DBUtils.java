//
//  DBUtils.java
//  Movida
//
//  Created by Matteo Celani on 27/10/2020.
//  Copyright © 2020 Matteo Celani Francesco D'Alessandro. All rights reserved.
//

package movida.dalessandrocelani;
import movida.commons.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * ************************************************
 * COMMENTI DA ELIMINARE ALLA CONSEGNA
 * ULTIMA MODIFICA: 27/10/2020
 * ************************************************
 *  In questa classe ci occupiamo del caricamento dei dati da un file dizionario
 *  loadFilm() si occupa di ciò
 *  carica il documento f e analizzando riga per riga,
 *  inizialmente separa i contentuti e cerca solo i campi che ci interessano
 *  salva i campi nell'array di tipo Movie
 *  restitutisce il DB appena creato
 *
 *  ************************************************
 *  DATA ULTIMO TEST: MAI TESTATO
 *  BUILD:  27/10/2020, 18:38 - Build completed successfully in 1 s 201 ms
 *  ************************************************
 **/
public class DBUtils {

    //Caricamento dei film tramite file
    public Movie[] loadFilm (File f) {
        String[] movieString = new String[5];      //Contiene i 5 campi della classe Movie
        ArrayList<Movie> movies = new ArrayList<>();

        try {
            Scanner sc = new Scanner(f);
            //Scanerrizzo il file per righe fino alla fine
            while (sc.hasNextLine()) {
                //Il file "dizionario" è organizzato per righe
                for (int i=0; i < 5 ; i++) {
                    String row = sc.nextLine();         //Leggo il contenuto della riga
                    if (!row.matches("(.*):(.*)")) {        //Controllo il contenuto della riga
                        throw new MovidaFileException();          //MovidaFileException in caso di errore di caricamento
                    }
                    movieString[i] = row;           //Salvo le 5 righe nell'array creato in precedenza
                }

                Movie x = this.extractDetail(movieString);     //Estraggo i campi che ci interessano e popolo Movie
                movies.add(x);      //Aggiungo il film alla lista di Movie

                //Controllo il separatore dopo i 5 valori inseriti
                if (sc.hasNextLine()) {     //Il documento non è concluso
                    if (!sc.nextLine().trim().isEmpty()) {      //Controllo se la riga è vuota una volta eliminati gli spazi
                        System.out.println("ATTENZIONE! Per separare un contentuto è possbile utilizzare solo spazi. ATTENZIONE!");
                        throw new MovidaFileException();        //MovidaFileException in caso di errore di caricamento
                    }
                }
            }

        } catch (Exception e) {
            new MovidaFileException().getMessage();
        }

        Movie[] movieTot = new Movie[movies.size()];         //Creiamo una lista di Movie grande quanto i film trovati nel file
        return movies.toArray(movieTot);           //Ritorno la lista in formato array
    }

    //Estrae i dettagli da una array e inserisce i contenuti all'interno della Classe Movie
    public  Movie extractDetail(String[] data){
        String[] contents = this.divideRow(data);       //Separo i contenuti e salvo in un array

        String title = contents[0];
        Integer year = Integer.parseInt(contents[1].trim());
        Integer votes = Integer.parseInt(contents[4].trim());
        //I membri del casto possono essere più di uno, sono separati da una virgola
        String[] castMembers = contents[3].split(",");     //Separa i membri del cast
        //Il cast è un array di Person
        Person[] cast = this.divideCast(castMembers);     //Inserisco dentro l'array tutti i membri del cast

        Person director = new Person(contents[2]);

        //Ritorno la classe con i contenuti inseriti
        return new Movie(title,year,votes,cast,director);
    }

    //Separo il contentuo a destra e a sinistra dei due punti
    public String[] divideRow(String[] data){
        String[] filmDetail = new String[5];        //Creo una stringa che conterrà i 5 campi del Movie

        for(int i=0;i<5;i++) {
            String[] goodValues = data[i].split(":");       //Separo il contenuto
            filmDetail[i] = goodValues[1];      //salvo il contenuto alla DX dei due punti
        }

        return filmDetail;      //Ritorno solo le informazioni che ci interessano
    }

    //Separo i membri del cast
    public Person[] divideCast(String[] name){
        Person[] cast = new Person[name.length];

        for(int i = 0; i < name.length; i++){
            cast[i] = new Person(name[i].trim());
        }

        return cast;    //Ritorno la singola persona che fa parte del cast
    }
}
