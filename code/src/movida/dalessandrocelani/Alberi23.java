package movida.dalessandrocelani;

import java.util.*;

public class Alberi23<K extends Comparable<K>,V> implements MovidaDictionary<K,V> {

    /**
     * Gli alberi 2-3 sono formati da nodi che contengono gli elementi della struttura.
     *
     * Ogni nodo contiene al massimo 2 elementi e minimo 1. Nel caso in cui c'è un solo elemento
     * sarà sempre a sinistra, in quel caso la destra è null.
     *
     * La struttura degli alberi 2-3 definisce due tipi di nodi/figli:
     *
     *  - Nodo 2: Questo nodo ha solo due figli, sempre a sinistra e al centro. L'elemento di destra è null e anche
     *            il figlio destro è null.
     *
     *  - Nodo 3: Questo nodo ha due elementi, quindi ha 3 figli: sinistro, centrale e destro. Quindi è pieno.
     */
    private class Node<K,V> {
        //Elemento di sinistra
        K keyLeft;
        V valueLeft;

        //Elemento di destra
        K keyRight;
        V valueRight;

        //Puntatori ai figli
        Node<K,V> left;
        Node<K,V> mid;
        Node<K,V> right;

        /**
         * Costruttore di Base, costuisce un nodo vuoto
         */
        private Node() {
            this.keyLeft = this.keyRight = null;
            this.valueLeft = this.valueRight = null;
            this.left = this.mid = this.right = null;
        }

        /**
         * Costruttore Nodo Base: costruisce un nodo con l'elemento di sinistra pieno
         * e tutto il resto vuoto.
         * @param key
         * @param value
         */
        private Node(K key, V value) {
            this.keyLeft = key;
            this.valueLeft = value;
            this.keyRight = null;
            this.valueRight = null;
            this.left = this.mid = this.right = null;
        }

        /**
         * Costruttore Nodo3: costruisce un con due elementi ma senza figli (devo ancora definirli)
         *
         * @param leftElement
         * @param rightElement
         */
        private Node(Node leftElement, Node rightElement) {
            this.keyLeft = (K) leftElement.getKeyLeft();
            this.valueLeft = (V) leftElement.getValueLeft();

            this.keyRight = (K) rightElement.getKeyLeft();
            this.valueRight = (V) rightElement.getValueLeft();

            this.left = this.mid = this.right = null;
        }

        /**
         * Costruttore Nodo3: costruisce un nodo con due elementi e due due figli (sinistro e destro)
         *
         * @param leftElement
         * @param rightElement
         * @param left
         * @param mid
         */
        private Node(Node leftElement, Node rightElement, Node left, Node mid){
            this.keyLeft = (K) leftElement.getKeyLeft();
            this.valueLeft = (V) leftElement.getValueLeft();

            this.keyRight = (K) rightElement.getKeyLeft();
            this.valueRight = (V) rightElement.getValueLeft();

            this.left = left;
            this.mid = mid;

        }

        //Get element
        private K getKeyLeft(){ return this.keyLeft; }
        private V getValueLeft(){ return this.valueLeft; }
        private K getKeyRight(){ return this.keyRight; }
        private V getValueRight(){ return this.valueRight; }
        private Node getLeftElement() { if (this.keyLeft == null && this.valueLeft == null) {return null;}else return new Node(this.keyLeft, this.valueLeft); }
        private Node getRightElement() { if (this.valueRight == null && this.keyRight == null) {return null;}else return new Node(this.keyRight, this.valueRight); }

        //Set Element
        private void setLeftElement(Node element) {
            this.keyLeft = (K) element.keyLeft;
            this.valueLeft = (V) element.valueLeft;
        }
        private void setRightElement(Node element) {
            this.keyRight = (K) element.keyLeft;
            this.valueRight = (V) element.valueLeft;
        }

        //Get child
        private Node getLeft(){ return this.left; }
        private Node getMid(){ return this.mid; }
        private Node getRight(){ return this.right; }

        //Set child
        private void setLeft(Node element) { this.left = element; }
        private void setMid(Node element) { this.mid = element; }
        private void setRight(Node element) { this.right = element; }

        /**
         * @return true se è una foglia e false altrimenti
         */
        private boolean isLeaf() {
            return left == null && mid == null && right == null;
        }

        /**
         * @return true se l'albero è bilanciato false altrimenti
         */
        private boolean isBalanced() {
            boolean balanced = false;

            if (isLeaf()) {
                balanced = true;
            } else if (left.getLeftElement() != null && mid.getLeftElement() != null) {
                if (getRightElement() != null) {
                    if (right.getLeftElement() != null) {
                        balanced = true;
                    }
                } else {
                    balanced = true;
                }
            }
            return balanced;
        }

        /**
         * TODO: Inserire commenti rebalance()
         */
        private void rebalance() {

            while(!isBalanced()) {
                if (getLeft().getLeftElement() == null) {
                    getLeft().setLeftElement(getLeftElement());
                    setLeftElement(getMid().getLeftElement());

                    if (getMid().getRightElement() != null) {
                        getMid().setLeftElement(getMid().getRightElement());
                        getMid().setRightElement(new Node());
                    } else {
                        getMid().setLeftElement(new Node());
                    }
                } else if (getMid().getLeftElement() == null) {
                    if (getRightElement() == null) {
                        if (getLeft().getLeftElement() != null && getLeft().getRightElement() == null && getMid().getLeft() == null ) {
                            setRightElement(getLeftElement());
                            setLeftElement(getLeft().getLeftElement());

                            setLeft(null);
                            setMid(null);
                            setRight(null);
                        } else {
                            getMid().setLeftElement(getLeftElement());
                            if (getLeft().getRightElement() == null) {
                                setLeftElement(getLeft().getLeftElement());
                                getLeft().setLeftElement(new Node());
                            } else {
                                setLeftElement(getLeft().getRightElement());
                                getLeft().setRightElement(new Node());
                            }
                            if (getLeft().getLeftElement() == null && getMid().getLeftElement() == null) {
                                setLeft(null);
                                setMid(null);
                                setRight(null);
                            }
                        }
                    } else {
                        getMid().setLeftElement(getRightElement());
                        setRightElement(getRight().getLeftElement());
                        if(getRight().getRightElement() != null) {
                            getRight().setLeftElement(getRight().getRightElement());
                            getRight().setRightElement(new Node());
                        }
                        else {
                            getRight().setLeftElement(new Node());
                        }
                    }
                } else if(getRightElement() != null && getRight().getLeftElement() == null) {
                    if(getMid().getRightElement() != null) { // (1)
                        getRight().setLeftElement(getRightElement());
                        setRightElement(getMid().getRightElement());
                        getMid().setRightElement(new Node());
                    }
                    else {
                        getMid().setRightElement(getRightElement());
                        setRightElement(new Node());
                    }
                }
            }
        }

        /**
         * TODO: Inserire commenti replaceMax()
         * @return
         */
        private Node replaceMax() {
            Node max = new Node();

            if (!isLeaf()) {
                if (getKeyRight() != null) {
                    max = right.replaceMax();
                } else max = mid.replaceMax();
            } else {
                if (getKeyRight() != null) {
                    max.setLeftElement(getRightElement());
                    setRightElement(new Node());
                } else {
                    max.setLeftElement(getLeftElement());
                    setLeftElement(new Node());
                }
            }
            if (!isBalanced()) rebalance();
            return max;
        }

        /**
         * TODO: Inserire commenti replaceMin()
         * @return
         */
        private Node replaceMin() {
                Node min = new Node();

                if (!isLeaf()) {
                    min = left.replaceMin();
                } else {
                    min.setLeftElement(getLeftElement());
                    setLeftElement(new Node());

                    if(getKeyRight() != null) {
                        setLeftElement(getRightElement());
                        setRightElement(new Node());
                    }
                }
                if(!isBalanced()) rebalance();
                return min;
        }

        /**
         * @return true se è un Nodo 3, false altrimenti
         */
        private Boolean is3Node() {
            return this.keyRight != null;
        }

        /**
         * @return true se è un Nodo 2, false altrimenti
         */
        private Boolean is2Node() {
            return this.keyRight == null;
        }

        /**
         * Compara la chiave sinistra o destra con una chiave di un nodo preso in imput
         * @param compare nodo con la chiave da comparare
         * @return TODO: cosa ritorna?? (da sistamare sono stanco e fatto)
         */
        private int compareLeft(Node compare) {
            return this.keyLeft.toString().compareTo(compare.getKeyLeft().toString());
        }
        private int compareRight(Node compare) {
            return this.keyRight.toString().compareTo(compare.getKeyLeft().toString());
        }
    }


    private Node<K,V> root;
    private int size;

    public Alberi23() {
        this.root = new Node<>();
        this.size = 0;
    }

    @Override
    public void put(K key, V value) {
        Node element = new Node(key, value);
        if(this.root == null || this.root.getLeftElement() == null){ // first case
            if(this.root == null) {
                this.root = new Node();
            }
            this.root.setLeftElement(element);
        }
        else {
            Node newRoot = addElement(this.root, element); // Immersion
            if(newRoot != null){
                this.root = newRoot;
            }
        }
        this.size++;
    }

    @Override
    public V get(K key) {
        return iGet(this.root, key);
    }

    @Override
    public void remove(K key) {
        boolean deleted;
        // Decrementiamo size all'inizio,se l'elemento non è stato eliminato lo incrementiamo
        this.dSize();
        deleted = removeElement(root, key);
        root.rebalance();
        if(root.getLeftElement() == null){
            root = null; // Abbiamo eliminato l'ultimo elemento dell'albero
        }
        if(!deleted){
            this.size++;
        }
    }

    @Override
    public LinkedList<V> values() {
        LinkedList<V> values = new LinkedList<>();

        if (!isEmpty()){
            iValues(this.root, values);
        } else return null;

        return values;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int dSize() { return this.size--; }

    @Override
    public Set<K> keySet() {
        Set keys = new LinkedHashSet();
        if (!isEmpty()){
            iKeySet(this.root, keys);
        } else return null;

        return keys;
    }

    @Override
    public boolean containsKey(K key) {
        if (!isEmpty()){
            return iCK(this.root, key);
        } else return false;
    }


    /**
     * TODO: Inserire commenti generali su addElement()
     *
     * @param current
     * @param newNode
     * @return
     */
    private Node addElement(Node current, Node newNode) {
        Node newParent = null;
        // Non siamo ancora nel livello più basso dell'albero
        if(current.isLeaf() == false) {
            Node sonAscended = null;
            if (current.compareLeft(newNode) == 0 || (current.is3Node() && current.compareRight(newNode) == 0)) {
                //NON GESTIAMO QUESTO CASO
            }
            // Il nuovo elemento è minore dell'elemento a sinistra
            else if (current.compareLeft(newNode) >= 0) {
                sonAscended = addElement(current.left, newNode);
                // Case sonAscended != null --> L'elemento è stato aggiunto a un Nodo 3 (ci sono due elementi)
                if (sonAscended != null) { // A new node comes from the left branch
                    // Il nuovo elemento, in questo caso, è sempre minore rispetto a current.left
                    if (current.is2Node()) {
                        current.setRightElement(current);   // sposto l'elemento a sinistra di current a destra
                        current.setLeftElement(sonAscended);
                        current.setRight(current.mid);
                        current.setMid(sonAscended.mid);
                        current.setLeft(sonAscended.left);
                    } else { // In this case we have a new split, so the current element in the left will go up
                        // Copio la parte destra del sottoalbero
                        Node rightCopy = new Node(support(current, false), new Node(), current.mid, current.right);
                        // Creo la nuova struttura attaccando la parte destra
                        newParent = new Node(support(current, true), new Node(), sonAscended, rightCopy);
                    }
                }
            } else if (current.is2Node() || (current.is3Node() && current.compareRight(newNode) >= 0)) {
                    sonAscended = addElement(current.mid, newNode);
                    if (sonAscended != null) { // A new split
                        // The right element is empty, so we can set the ascended element in the left and the existing left element into the right
                        if (current.is2Node()) {
                            current.setRightElement(sonAscended);
                            current.setRight(sonAscended.mid);
                            current.setMid(sonAscended.left);
                        }
                        else { // Another case we have to split again
                            Node left = new Node(support(current, true), new Node(), current.left, sonAscended.left);
                            Node mid = new Node(support(current, false), new Node(), sonAscended.mid, current.right);
                            newParent = new Node(support(sonAscended, true), new Node(), left, mid);
                        }
                    }
            } else if (current.is3Node() && current.compareRight(newNode) < 0) {
                    sonAscended = addElement(current.right, newNode);
                    if (sonAscended != null) { // Split, the right element goes up
                        Node leftCopy   = new Node(support(current, true), new Node(), current.left, current.mid);
                        newParent       = new Node(support(current, false), new Node(), leftCopy, sonAscended);
                    }
            }
        }
        else { // Siamo al livello più basso dell'albero
            // L'elemento già esiste
            if (current.compareLeft(newNode) == 0 || (current.is3Node() && current.compareRight(newNode) == 0)) {
                //NON GESTIAMO QUESTO CASO
            }
            else if (current.is2Node()) { // an easy case, there is not a right element
                // if the current left element is bigger than the new one --> we shift the left element to the right
                if (current.compareLeft(newNode) >= 0) {
                    current.setRightElement(current);
                    current.setLeftElement(newNode);
                }
                // if the new element is bigger, we add it in the right directly
                else if (current.compareLeft(newNode) < 0){
                    current.setRightElement(newNode);
                }
            }
            // Case 3-node: there are 2 elements in the node and we want to add another one. We have to split the node
            else newParent = split(current, newNode);
        }
        return newParent;
    }

    /**
     * Funzione che prende in imput un nodo con due elementi e li separa
     * TODO:Finire di scrivere commenti
     * @param current
     * @param insert
     * @return
     */
    public Node split(Node current, Node insert) {
        Node newNode = new Node();

        //L'elemento di sinistra è maggiore, quindi salirà lasciando l'elemento nuovo sulla sinistra
        if(current.compareLeft(insert) >= 0){
            Node left = new Node(insert, new Node());
            Node right = new Node(support(current, false), new Node());
            newNode = new Node(support(current, true), new Node(), left, right);

        } else if(current.compareLeft(insert) < 0){

            //Il nuovo elemento è maggiore rispetto all'elemento destro di current, ma minore rispetto l'elemento a destra. Il nuovo elemento va su.
            if(current.compareRight(insert) >= 0){
                Node left = new Node(support(current, true), new Node());
                Node right = new Node(support(current, false), new Node());
                newNode = new Node(insert, new Node(), left, right);

            } else { //Il nuovo elemento è il più grande, quindi l'elemento destro di current va su
                Node left = new Node(support(current, true), new Node());
                Node right = new Node(insert, new Node());
                newNode = new Node(support(current, false), new Node(), left, right);
            }
        }
        return newNode;
    }

    /**
     * Prende in imput un nodo con due elementi
     * Crea un nuovo elemento
     * Popola il nuovo nodo con un solo elemento a sinistra
     *
     * @param current Nodo la quale vogliamo prendere solo un elemento
     * @param flag se è true prende l'elemento a sinistra di current s è false prende l'elemento a destra di current
     * @return il nodo con un solo elemento piazzato sempre a sinistra
     */
    private Node support(Node current, Boolean flag) {
        if (flag) {
            Node newNode = new Node(current.getKeyLeft(), current.getValueLeft());
            return newNode;
        } else {
            Node newNode = new Node(current.getKeyRight(), current.getValueRight());
            return newNode;
        }
    }

    /**
     * @return true se l'albero è vuoto false altrimenti
     */
    public boolean isEmpty() {
        if(this.root == null) return true;
        if(this.root.getLeftElement() == null) return true;
        return false;
    }

    /**
     * TODO: commento
     *
     * @param element
     * @param key
     * @return
     */
    private V iGet(Node element, K key) {
        Node x = new Node();
        x.keyLeft = key;
        V find = null;
        if(element != null) {
            if(element.keyLeft != null && element.keyLeft.equals(key)) {
                find = (V) element.valueLeft;
            } else {
                if (element.keyRight != null && element.keyRight.equals(key)) {
                    find = (V) element.valueRight;
                } else {
                    if (element.compareLeft(x) >= 0) {
                        find = (V) iGet(element.left, key);
                    } else if (element.getRightElement() == null || element.compareRight(x) >= 0){
                        find = (V) iGet(element.mid, key);
                    } else if (element.compareRight(x) < 0) {
                        find = (V) iGet(element.right, key);
                    }
                    else return null;
                }
            }
        }
        return find;
    }

    /**
     * TODO: TRADURRE COMMENTO
     * In a recursive way, the algorithm tries to find the element to delete from the tree.
     *
     * When it finds the element, we can have one of this two situations:
     *
     *
     * 		A. The element we have to delete was in the deepest level of the tree, where we know all the rebalance patterns
     * 			(see the method "rebalance" below implemented in the private class Node) so we will not have many troubles in
     * 			this case because there are not more levels below of the current one.
     *
     * 		B. The element to delete is not in the deepest level of the tree. In this situation we must force a swap.
     * 		   What we have to do is:
     *
     * 		   	- If we are deleting an element in the mid side, we are gonna replace it with the min value of the branch causing
     * 		   	  an unbalanced case but in the deepest level.
     *
     * 		   	- If the element to delete is in the right side of the tree, we replace that element with the max value of
     * 		   	  the branch. Then, we have a unbalanced case but in the deepest level.
     *
     * 			These processes achieves easy rebalance cases, excepting the critical case (full explained in the "rebalance" method):
     *
     * 				- If after the deletion of the element a node has been empty and we don't have enough elements in the deepest level
     * 				  of the tree to rebalance it, the tree will be reorganized from a higher level which increases the cost.
     *
     */
    private boolean removeElement(Node current, K key) {
        boolean deleted = true;
        Node newNode = new Node();
        newNode.keyLeft= key;

        // Siamo sul fondo dell'albero ma non abbiamo trovato l'elemento da eliminare (non esiste)
        if(current == null){
            deleted = false;
        }
        else {
            // Caso ricorsivo, stiamo ancora cercando l'elemento da eliminare
            if(!current.keyLeft.equals(newNode.keyLeft)) {
                // Se non ci sono elementi a destra (2 Node) o se l'elemento da cancellare è minore dell'elemento a destra
                if(current.keyRight == null || current.compareRight(newNode) > 0) {

                    // L'elemento a sinistra è maggiore dell'elemento da eliminare, quindi passiamo dal figlio sinistro
                    if(current.compareLeft(newNode) > 0) {
                        deleted = removeElement(current.left, key);
                    }
                    else { // Altrimenti passiamo dal figlio centrale
                        deleted = removeElement(current.mid, key);
                    }
                }
                else {
                    // Se l'elemento da cancellare non è l'elemento di destra, passiamo al figlio destro
                    if(!current.keyRight.equals(newNode.keyLeft)) {
                        deleted = removeElement(current.right, key);
                    }
                    else { // Altrimenti abbiamo trovato l'elemento

                        // L'elemento da eliminare è un elemento destro di una foglia, quindi dobbiamo solo cancellarla
                        if(current.isLeaf()){
                            current.setRightElement(new Node());
                        }
                        else { // Troviamo l'elemento ma non è una foglia

                            //Prendiamo l'elemento minore del sottoalbero destro, lo rimuoviamo dalla sua attuale posizione
                            // e lo inseriamo al posto dell'elemento da cancellare
                            Node replacement = new Node (current.right.replaceMin(), new Node());
                            current.setRightElement(replacement);
                        }
                    }
                }
            }
            // L'elemento da eliminare è l'elemento sinistro
            else {
                if(current.isLeaf()) {
                    // L'elemento a sinistra, cioè quello da rimuovere, è scambiato con l'elemento di destra
                    if(current.keyRight != null) {
                        current.setLeftElement(current.getRightElement());
                        current.setRightElement(new Node());
                    }
                    else { // Se non ci sono elementi a destra, bisogna bilanciare
                        current.setLeftElement(new Node()); // We let the node empty

                        //Avvisa che un nodo è stato eliminato (è vuoto) ed è necessario il bilanciament in uno specifico livello dell'albero
                        return true;
                    }
                }
                else {
                    // Spostiamo l'elemento massimo del sottoalbero sinistro dove troviamo l'elemento da eliminare
                    Node replacement = new Node(current.left.replaceMax(), new Node());
                    current.setLeftElement(replacement);
                }
            }
        }

        if(current.keyLeft != null && !current.isBalanced()) {
            current.rebalance();
        }
        else if(current.keyLeft != null && !current.isLeaf()) {

            boolean balanced = false;
            while(!balanced) {
                if(current.right == null) {

                    // Critical case of the situation B at the left child
                    if(current.left.isLeaf() && !current.mid.isLeaf()) {

                        Node replacement = new Node (current.mid.replaceMin(), new Node());
                        Node readdition= new Node (current.getLeftElement(), new Node());

                        current.setLeftElement(replacement);
                        put((K) readdition.keyLeft,(V) readdition.valueLeft);
                        this.dSize();
                        // Critical case of hte situation B at the right child
                    } else if(!current.left.isLeaf() && current.mid.isLeaf()) {

                        if(current.keyRight == null) {

                            Node replacement = new Node(current.left.replaceMax(), new Node());
                            Node readdition = new Node(current.getLeftElement(), new Node());
                            current.setLeftElement(replacement);
                            put((K) readdition.keyLeft,(V) readdition.valueLeft);
                            this.dSize();
                        }
                    }
                }
                if(current.right != null) {

                    if(current.mid.isLeaf() && !current.right.isLeaf()) {

                        current.right.rebalance();
                    }
                    if(current.mid.isLeaf() && !current.right.isLeaf()) {

                        Node replacement = new Node (current.right.replaceMin(), new Node());
                        Node readdition = new Node (current.getRightElement(), new Node());
                        current.setRightElement(replacement);
                        put((K) readdition.keyLeft,(V) readdition.valueLeft);
                        this.dSize();
                    }
                    else balanced = true;
                }
                if(current.isBalanced()){
                    balanced = true;
                }
            }
        }
        return deleted;
    }

    /**
     * TODO: INSERIRE COMMENTI iCK()
     * @param element
     * @param key
     * @return
     */
    private boolean iCK(Node element, K key) {
        Node x = new Node();
        x.keyLeft = key;
        boolean find = false;
        if(element != null) {
            if(element.keyLeft != null && element.keyLeft.equals(key)) {
                find = true;
            } else {
                if (element.keyRight != null && element.keyRight.equals(key)) {
                    find = true;
                } else {
                    if (element.compareLeft(x) >= 0) {
                        find = iCK(element.left, key);
                    } else if (element.right == null || element.compareRight(x) >= 0){
                        find = iCK(element.mid, key);
                    } else if (element.compareRight(x) < 0) {
                        find = iCK(element.right, key);
                    }
                    else return false;
                }
            }
        }
        return find;
    }

    /**
     * TODO: INSERIRE COMMENTI iValues()
     * @param element
     * @param values
     */
    private void iValues(Node element, LinkedList<V> values) {
        if (element != null) {
            if(element.isLeaf()) {
                values.add((V) element.getValueLeft());
                if(element.getRightElement() != null) values.add((V) element.getValueRight());
            }
            else {
                iValues(element.getLeft(), values);
                values.add((V) element.getValueLeft());
                iValues(element.getMid(), values);
                if(element.getRightElement() != null) {
                    if(!element.isLeaf()) values.add((V) element.getValueRight());
                    iValues(element.getRight(), values);
                }
            }
        }
    }

    /**
     * TODO: INSERIRE COMMENTI iKeySet()
     * @param element
     * @param keys
     */
    private void iKeySet(Node element, Set keys) {
        if (element != null) {
            if(element.isLeaf()) {
                keys.add((K) element.getKeyLeft());
                if(element.getRightElement() != null) keys.add((K) element.getKeyRight());
            }
            else {
                iKeySet(element.getLeft(), keys);
                keys.add((K) element.getKeyLeft());
                iKeySet(element.getMid(), keys);
                if(element.getRightElement() != null) {
                    if(!element.isLeaf()) keys.add((K) element.getKeyRight());
                    iKeySet(element.getRight(), keys);
                }
            }
        }
    }


}
