package movida.dalessandrocelani;
import movida.commons.*;

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
