package movida.dalessandrocelani;

import movida.commons.*;
import java.util.*;

public class MovidaGraph {

    private HashMap<Person, ArrayList<Collaboration>> collabGraph;

    MovidaGraph() {
        this.collabGraph = new HashMap<Person, ArrayList<Collaboration>>();
    }

}
