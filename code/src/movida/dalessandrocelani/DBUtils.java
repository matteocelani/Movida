package movida.dalessandrocelani;
import movida.commons.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;

public class DBUtils {

    //Caricamento dei film tramite file
    public Movie[] loadFilm (File f) {
        String[] movieString = new String[5];   //Contiene i 5 campi della classe Movie
        LinkedList<Movie> movies = new LinkedList<>();
        try {
            Scanner sc = new Scanner(f);

            //Scanerrizzo il file per righe fino alla fine
            while (sc.hasNextLine()) {

                //Il file "dizionario" è organizzato per righe
                for (int i=0; i < 5 ; i++) {
                    String row = sc.nextLine(); //Leggo il contenuto della riga
                    if (!row.matches("(.*):(.*)")) {    //Controllo il contenuto della riga
                        throw new MovidaFileException();    //MovidaFileException in caso di errore di caricamento
                    }
                    movieString[i] = row;   //Salvo le 5 righe nell'array creato in precedenza
                }

                Movie x = this.extractDetail(movieString);  //Estraggo i campi che ci interessano e popolo Movie
                movies.add(x);  //Aggiungo il film alla lista di Movie

                //Controllo il separatore dopo i 5 valori inseriti
                if (sc.hasNextLine()) { //Il documento non è concluso
                    if (!sc.nextLine().trim().isEmpty()) {  //Controllo se la riga è vuota una volta eliminati gli spazi
                        System.out.println("ATTENZIONE! Per separare un contentuto è possbile utilizzare solo spazi. ATTENZIONE!");
                        throw new MovidaFileException();
                    }
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Movie[] movieTot = new Movie[movies.size()];    //Creiamo una lista di Movie grande quanto i film trovati nel file
        return movies.toArray(movieTot);    //Ritorno la lista in formato array
    }

    //Estrae i dettagli da una array e inserisce i contenuti all'interno della Classe Movie
    public  Movie extractDetail(String[] data){
        String[] contents = this.divideRow(data);   //Separo i contenuti e salvo in un array

        String title = contents[0].trim();
        Integer year = Integer.parseInt(contents[1].trim());
        Integer votes = Integer.parseInt(contents[4].trim());

        //I membri del casto possono essere più di uno, sono separati da una virgola
        String[] castMembers = contents[3].split(",");  //Separa i membri del cast

        //Il cast è un array di Person
        Person[] cast = this.divideCast(castMembers);   //Inserisco dentro l'array tutti i membri del cast
        Person director = new Person(contents[2]);

        //Ritorno la classe con i contenuti inseriti
        return new Movie(title,year,votes,cast,director);
    }

    //Separo il contentuo a destra e a sinistra dei due punti
    public String[] divideRow(String[] data){
        String[] filmDetail = new String[5];    //Creo una stringa che conterrà i 5 campi del Movie

        for(int i=0;i<5;i++) {
            String[] goodValues = data[i].split(":");   //Separo il contenuto
            filmDetail[i] = goodValues[1];  //salvo il contenuto alla DX dei due punti
        }

        return filmDetail;  //Ritorno solo le informazioni che ci interessano
    }

    //Separo i membri del cast
    public Person[] divideCast(String[] name){
        Person[] cast = new Person[name.length];

        for(int i = 0; i < name.length; i++){
            cast[i] = new Person(name[i].trim());
        }

        return cast;    //Ritorno la singola persona che fa parte del cast
    }

    public void save(File f,Movie[] movies){
            try {
                if (movies.length != 0) {
                    BufferedWriter bw = null;
                    FileWriter fw = new FileWriter(f);
                    bw = new BufferedWriter(fw);
                    for (Movie movie : movies) {
                        bw.write("Title: " + movie.getTitle());
                        bw.newLine();
                        bw.write("Year: " + movie.getYear().toString());
                        bw.newLine();
                        bw.write("Director: " + movie.getDirector().getName().trim());
                        bw.newLine();
                        bw.write("Cast: " + movie.getDetailCast());
                        bw.newLine();
                        bw.write("Votes: " + movie.getVotes().toString());
                        bw.newLine();
                        bw.newLine(); // Aggiungo una linea per separare i campi
                    }
                    bw.close();
                    System.out.println("File written Successfully");
                } else {
                    throw new MovidaFileException();
                }
            } catch (Exception e){
                System.out.println(e.getMessage());
                }
    }
}
