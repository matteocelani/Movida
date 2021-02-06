package movida.dalessandrocelani;

import movida.commons.*;
import java.util.*;

public class MovidaGraph {

    private HashMap<String, ArrayList<Collaboration>> collabGraph;

    MovidaGraph() {
        this.collabGraph = new HashMap<String, ArrayList<Collaboration>>();
    }


    class SortCollaboration implements Comparator<Collaboration> {
        public int compare(Collaboration a, Collaboration b) {
            return b.getScore().compareTo(a.getScore());
        }
    }

    public void loadCollaboration(Movie m) {
        if (m != null) {
            for (Person p: m.getCast()) {
                if (this.collabGraph.containsKey(p) == false) {
                    this.collabGraph.put(p.getName(), new ArrayList<>());
                }
                for (Person a: m.getCast()) {
                    if (!p.equals(a)) {
                        ArrayList<Collaboration> collab = this.collabGraph.get(p.getName());
                        Collaboration c = new Collaboration(p,a);
                        if (collab.contains(c)) {
                            int i = collab.indexOf(c);
                            collab.get(i).addMovie(m);
                        } else {
                            c.addMovie(m);
                            collab.add(c);
                        }
                    }
                }
            }
        } else return;
    }
    //-------------------------------------------------------------
    //IMOVIDA COLLABORATION
    //-------------------------------------------------------------

    public Person[] getDirectCollaborators(Person actor) {
        ArrayList<Collaboration> c = collabGraph.get(actor.getName());
        if (collabGraph != null) {
            Person[] p = new Person[collabGraph.size()];
            int i = 0;
            for (Collaboration col : c) {
                p[i] = col.getActorB();
                i++;
            }
            System.out.print("I diretti collaboratori di " + actor.getName() + " sono:");
            return p;
        } else return null;
    }

    public Person[] getTeam(Person actor) {
        HashSet<String> find = new HashSet<>();
        ArrayList<Person> team = new ArrayList<>();
        ArrayDeque<Person> queue = new ArrayDeque<>();

        find.add(actor.getName());
        team.add(actor);
        queue.add(actor);

        while (!queue.isEmpty()) {
            Person x = queue.poll();
            ArrayList<Collaboration> list = collabGraph.get(x.getName());
            if (list != null) {
                for (Collaboration c: list) {
                    Person p = c.getActorB();
                    if (find.contains(p.getName()) == false) {
                        find.add(p.getName());
                        team.add(p);
                        queue.add(p);
                    }
                }
            } else return null;
        }
        Person[] actorTeam = new Person[team.size()];
        for (int i=0 ; i< actorTeam.length; i++) {
            actorTeam[i] = team.get(i);
        }
        System.out.print("Il Team di " + actor.getName() + " è formato da:");
        return actorTeam;
    }

    public Collaboration[] maximizeCollaborationsInTheTeam(Person actor) {
        HashSet<Person> actorFind = new HashSet<>();
        ArrayList<Collaboration> col = new ArrayList<>();
        PriorityQueue<Collaboration> queue = new PriorityQueue<Collaboration>(new SortCollaboration());
        for (Collaboration c: this.collabGraph.get(actor.getName())) {
            queue.add(c);
        }
        while (!queue.isEmpty()) {
            Collaboration x = queue.poll();

            if (!actorFind.contains(x.getActorB())) {
                actorFind.add(x.getActorA());
                actorFind.add(x.getActorB());
                col.add(x);
                for (Collaboration c: this.collabGraph.get(x.getActorB().getName())) {
                    if (!actorFind.contains(c.getActorB())) {
                        queue.add(c);
                    }
                }
            }
        }

        Collaboration[] teamCollab = new Collaboration[col.size()];
        for (int i = 0 ; i < teamCollab.length ; i++ ) {
            teamCollab[i] = col.get(i);
        }

        System.out.print("Il numero massimo di Collaborazioni in un team di " + actor.getName() + " è formata da:");
        return teamCollab;
    }

}
