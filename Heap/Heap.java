import java.util.ArrayList;

public class Heap<E> implements PriorityQueue<E> { // E should implement Comparable
    // this ArrayList holds the tree, with element 0 as the root
    private ArrayList<E> tree; 

    /**
     * default (and only) constructor for the Heap class
     */
    public Heap() {
        this.tree = new ArrayList<E>();
    }

    /**
     * determines if the Heap has any elements
     * @return true if empty, false if not
     */
    public boolean isEmpty() {
        return this.tree.size() == 0;
    }

    /**
     * adds the given Object (which must be Comparable) to
     * the Heap, percolating it up to its proper location
     * @param x the Object to add
     * @throws ClassCastException if x is not Comparable
     */
    public boolean add(E obj) {
        this.tree.add(obj);
        this.percolateUp();
        return true;
    }

    /**
     * returns the smallest Object in the Heap
     * @return the smallest Object in the Heap
     * @throws IndexOutOfBoundsException if the Heap is empty
     */
    public E peekMin() {
        return this.tree.get(0);
    }

    /**
     * removes and returns the smallest Object in the
     * Heap, and then rearranges the remaining elements
     * to make them a valid Heap again
     * @return the smallest element
     * @throws IndexOutOfBoundsException if the Heap is empty
     */
    public E remove() {
        E retVal = this.tree.get(0);
        this.tree.set(0, this.tree.get(this.tree.size()-1));
        this.tree.remove(this.tree.size()-1);
        this.percolateDown();
        return retVal;
    }

    /**
     * returns a String representation of this Heap as a list of
     * all the node values in order
     * @return a list of all node values in order
     */
    public String toString() {
        return this.tree.toString();
    }

    /**
     * returns the index of the parent node of the given node
     * @param node the index of a given node
     * @return the index of the parent of that node
     */
    private int parent(int node) {
        return (node - 1) / 2;
    }

    /**
     * returns the index of the left child of a given node
     * @param node the index of a given node
     * @return the index of the node's left child
     */
    private int leftChild(int node) {
        return 2 * node + 1;
    }

    /**
     * returns the index of the rightChild of a given node
     * @param node the index of a given node
     * @return the index of the node's right child
     */
    private int rightChild(int node) {
        return 2 * node + 2;
    }

    /**
     * moves the last element of the heap up the array,
     * swapping it with its parent until it is greater than
     * its parent or it has reached the top of the Heap
     * (note: my code is 8 lines long, but uses auxiliary functions)
     */
    private void percolateUp() {
        int index = this.tree.size()-1;
        Comparable val = (Comparable)this.tree.get(index);
        Comparable tempVal;
        for(int temp = index; val.compareTo((Comparable)tree.get(parent(temp)))<0;temp=parent(temp)){
            tempVal=(Comparable)tree.get(parent(temp));
            tree.set(parent(temp), tree.get(temp));
            tree.set(temp, (E)tempVal);
        }
    }

    /**
     * moves the top element of the heap down, swapping it
     * with its smallest child until it's smaller than both
     * children or doesn't have any children
     * (note: my code is 10 lines long, but uses auxiliary functions)
     */
    private void percolateDown() {
        if(tree.size()<1)
            return;
        Comparable val = (Comparable)tree.get(0);
        int index = 0;
        Comparable leftVal;
        Comparable rightVal;

        if(val==null)
            return;

        while(true){
            if(leftChild(index)<tree.size())
                leftVal = (Comparable)tree.get(leftChild(index));
            else
                break;
            if(rightChild(index)<tree.size())
                rightVal = (Comparable)tree.get(rightChild(index));
            else
                rightVal=null;
           
            if(rightVal==null){
                if(val.compareTo(leftVal)>0){
                    tree.set(leftChild(index), (E)val);
                    tree.set(index, (E)leftVal);
                    break;
                }
                else
                    break;
            }
            if(rightVal.compareTo(leftVal)<0){
                if(val.compareTo(rightVal)>0){
                    tree.set(index, (E)rightVal);
                    tree.set(rightChild(index), (E)val);
                    index = rightChild(index);
                }
                else
                    break;
            }
            else if(val.compareTo(leftVal)>0){
                tree.set(index, (E)leftVal);
                tree.set(leftChild(index), (E)val);
                index = leftChild(index);
            }
            else
                break;
        }
    }     
    // include any auxiliary functions you need here
    /**
     * Prints out the Heap as a tree structure, useful for debugging
     */
    public void drawAsTree() {
        if (this.tree.size() == 0) {
            System.out.println("[empty heap]");
        } else {
            System.out.println(makeSingleString(drawTreeFromNode(0, "none")));
        }
    }

    /**
     * Ignore everything from here on, unless you're a big masochist
     * It was a pain to get the tree drawing to work
     */
    private String makeSingleString(ArrayList<String> lines) {
        String retVal = "";
        for (int i = 0; i < lines.size(); i++) {
            retVal += lines.get(i);
            if (i < lines.size() - 1) {
                retVal += "\n";
            }
        }
        return retVal;
    }

    private ArrayList<String> combine(ArrayList<String> left, int leftWidth, ArrayList<String> right, int rightWidth) {
        int maxSize = (left.size() > right.size()) ? left.size() : right.size();
        ArrayList<String> retVal = new ArrayList<String>(maxSize + 1);
        if (maxSize > 0) {
            retVal.add(copies(" ", leftWidth) + "|" + copies(" ", rightWidth));
        }
        for (int i = 0; i < maxSize; i++) {
            String leftSide = (i < left.size()) ? left.get(i) : copies(" ", leftWidth);
            String rightSide = (i < right.size()) ? right.get(i) : copies(" ", rightWidth);
            if (leftSide.length() < leftWidth) {
                leftSide = copies(" ", leftWidth - leftSide.length()) + leftSide;
            }
            if (rightSide.length() < rightWidth) {
                rightSide = rightSide + copies(" ", rightWidth - rightSide.length());
            }         
            String joinChar = (i == 0) ? "+" : " ";
            retVal.add(leftSide + joinChar + rightSide);
        }
        return retVal;
    }

    private int maxLength(ArrayList<String> lines) {
        int maxLength = 0;
        for (String line : lines) {
            if (line.length() > maxLength) {
                maxLength = line.length();
            }
        }
        return maxLength;
    }

    private String copies(String val, int num) {
        String retVal = "";
        for (int i = 0; i < num; i++) {
            retVal += val;
        }
        return retVal;
    }

    private ArrayList<String> drawTreeFromNode(int nodeNum, String branch) {
        if (nodeNum >= this.tree.size()) {
            return new ArrayList<String>();
        } else {
            String val = "" + this.tree.get(nodeNum);
            ArrayList<String> lines = new ArrayList<String>();
            ArrayList<String> leftSubTree = drawTreeFromNode(leftChild(nodeNum), "left");
            int leftWidth = Math.max(val.length() / 2, maxLength(leftSubTree));
            ArrayList<String> rightSubTree = drawTreeFromNode(rightChild(nodeNum), "right");
            int rightWidth = Math.max((val.length() - 1) / 2, maxLength(rightSubTree));
            if (branch.equals("left")) {
                lines.add(copies(" ", leftWidth) + "+" + copies("-", rightWidth));
                lines.add(copies(" ", leftWidth) + "|" + copies(" ", rightWidth));
            } else if (branch.equals("right")) {
                lines.add(copies("-", leftWidth) + "+" + copies(" ", rightWidth));
                lines.add(copies(" ", leftWidth) + "|" + copies(" ", rightWidth));
            }
            lines.add(copies(" ", leftWidth - val.length() / 2) + val
                + copies(" ", rightWidth - (val.length() - val.length() / 2 - 1)));
            ArrayList<String> subTree = combine(leftSubTree, leftWidth, rightSubTree, rightWidth);
            for (String line : subTree) {
                lines.add(line);
            }
            return lines;
        }
    }
}
