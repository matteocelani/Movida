package movida.dalessandrocelani;
import movida.commons.*;
/**
 * ************************************************
 * COMMENTI DA ELIMINARE ALLA CONSEGNA
 * ULTIMA MODIFICA: 02/11/2020
 * ************************************************
 *
 *  ************************************************
 *  DATA ULTIMO TEST: MAI TESTATO
 *  BUILD:  02/11/20, 16:32 - Build completed successfully in 1 s 798 ms
 *  ************************************************
 **/

public class DetailPerson extends Person {

    private Integer numMov;
    private Boolean actor;

    public DetailPerson(String name, Integer  numMov, Boolean actor) {
        super(name);
        this.numMov = numMov;
        this.actor = actor;
    }

    public int getNumMov() {
        return numMov;
    }

    public int addMovie() {
        return this.numMov++;
    }

    public int removeMovie() { return this.numMov--; }

    public Boolean getActor() {
        return this.actor;
    }

}
