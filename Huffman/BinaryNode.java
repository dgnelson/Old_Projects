

public class BinaryNode implements Comparable{
   
    char val;
    int frequency;
    BinaryNode left;
    BinaryNode right;
    
    public BinaryNode(char v, int f, BinaryNode l, BinaryNode r){
       val = v;
       frequency = f;
       left=l;
       right=r;
    }
    
    public BinaryNode(char v, int f){
       val = v;
       frequency = f;
    }
    
    public int compareTo(Object node){
        if(frequency>((BinaryNode)node).frequency)
            return 1;
        else if(frequency<((BinaryNode)node).frequency)
            return -1;
        return 0;
    }
    
    public char getVal(){
        return val;
    }
    
    public int getFrequency(){
        return frequency;
    }
    
    public BinaryNode getLeft(){
        return left;
    }
    
    public BinaryNode getRight(){
        return right;
    }
    
    public void setVal( char c){
        val = c;
    }
    
    public void setFrequency(int f){
        frequency = f;
    }
    
    public void setLeft(BinaryNode l){
        left = l;
    }
    
    public void setRight(BinaryNode r){
        right = r;
    }

}
