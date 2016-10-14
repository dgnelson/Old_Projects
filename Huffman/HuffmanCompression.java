import javax.swing.JOptionPane;
import java.util.*;
import java.io.*;
import java.lang.*;
public class HuffmanCompression{

    TreeMap frequencies;
    PriorityQueue<BinaryNode> heap;
    BinaryNode root;
    TreeMap indexes;
    boolean useMap = true;

    public HuffmanCompression(){
        frequencies = countFrequencies();
        heap = makeHeap();
        root = buildTree();
        indexes = new TreeMap();
        makeIndexMap();
        byteCompress();
        byteDecode();
    }

    private TreeMap countFrequencies(){ //counts frequency of characters
        TreeMap freq = new TreeMap();
        try{
            BufferedReader br = new BufferedReader(new FileReader("inFile.txt"));
            char c = (char)br.read();
            int val = 0;
            while(c!=((char)-1)){
                if(freq.containsKey(c)){
                    val = (int)freq.get(c)+1;
                    freq.remove(c);
                    freq.put(c, val);
                }
                else
                    freq.put(c, 1);
                c = (char)br.read();
            }
            return freq;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private PriorityQueue<BinaryNode> makeHeap(){// makes a heap using frequencies as priorities
        PriorityQueue<BinaryNode> queue = new PriorityQueue<BinaryNode>();
        Iterator iter = frequencies.keySet().iterator();
        BinaryNode temp;
        char key;
        while(iter.hasNext()){
            key = (char)iter.next();
            temp = new BinaryNode((char)key, (int)frequencies.get(key));
            queue.offer(temp);
        }
        return queue;
    }

    private BinaryNode buildTree(){ //builds heap into tree
        BinaryNode temp1;
        BinaryNode temp2;
        while(!heap.isEmpty()){
            temp1 = heap.remove();
            if(heap.isEmpty()){
                heap.add(temp1);
                break;
            }
            temp2 = heap.remove();
            heap.add(new BinaryNode(' ', temp1.getFrequency()+temp2.getFrequency(), temp1, temp2));
        }
        return heap.remove();
    }

    private void makeIndexMap(){ //makes a map of chars to paths
        indexHelper(root, "");
    }

    public void indexHelper(BinaryNode node, String path){
        if(node.getLeft()==null&&node.getRight()==null)
            indexes.put(node.getVal(), path);
        else{
            indexHelper(node.getLeft(), path+"0");
            indexHelper(node.getRight(), path+"1");
        }
    }

    public void compress(){
        try{ 
            BufferedReader br = new BufferedReader(new FileReader("inFile.txt"));
            FileWriter writer=new FileWriter("compressedFile.txt");
            Iterator iter;
            int indexLength = 0;
            char c;

            if(useMap){
                //find length of index
                iter = indexes.keySet().iterator();
                while(iter.hasNext()){
                    c = (char)iter.next();
                    indexLength++;
                }
                writer.write(""+indexLength);
                writer.write(",");

                //write index map to file
                iter = indexes.keySet().iterator();
                while(iter.hasNext()){
                    c = (char)iter.next();
                    writer.write(c);
                    writer.write((String)(indexes.get(c)));
                    writer.write(",");
                }
            }
            //switch to path values
            int d = br.read();
            while(d!=-1){
                writer.write((String)indexes.get((char)d));
                d = br.read();
            }
            writer.write(",");

            writer.flush();
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void decode(){
        try{ 
            Scanner scan = new Scanner(new FileReader("compressedFile.txt"));
            scan.useDelimiter(",");
            FileWriter writer=new FileWriter("decompressedFile.txt");
            TreeMap decodeIndex = new TreeMap();

            if(useMap){
                int indexLength = Integer.parseInt(scan.next());
                String keyVal;
                char val;
                String key;
                while(indexLength>0){  //read line now instead and get first char then use rest of 01010101 as key, delimiter to get line
                    keyVal = scan.next();
                    val = keyVal.charAt(0);
                    key = keyVal.substring(1);
                    indexLength--;
                    decodeIndex.put(key, val);
                }
            }
            else{ //switch indexes key and val for decodeindex, this is just for testing purposes
                Iterator iter = indexes.keySet().iterator();
                char c;
                while(iter.hasNext()){
                    c = (char)iter.next();
                    decodeIndex.put(indexes.get(c), c);
                }
            }

            String str = scan.next();
            int n = 1;
            while(str.length()>0){
                while(!decodeIndex.containsKey(str.substring(0,n)))
                    n++;
                writer.write((char)decodeIndex.get(str.substring(0,n)));
                str = str.substring(n);
                n=1;
            }

            writer.flush();
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void byteCompress(){
        try{ 
            BufferedReader br = new BufferedReader(new FileReader("inFile.txt"));
            FileWriter writer=new FileWriter("compressedFile.txt");
            Iterator iter;
            int indexLength = 0;
            char c;

            if(useMap){
                //find length of index
                iter = indexes.keySet().iterator();
                while(iter.hasNext()){
                    c = (char)iter.next();
                    indexLength++;
                }
                writer.write(""+indexLength);
                writer.write("~,~");

                //write index map to file
                iter = indexes.keySet().iterator();
                while(iter.hasNext()){
                    c = (char)iter.next();
                    writer.write(c);
                    writer.write((String)(indexes.get(c)));
                    writer.write("~,~");
                }
            }
            //switch to path values
            int d = br.read();
            String file = "";
            int chars = 0;
            while(d!=-1){
                chars++;
                file = file+((String)(indexes.get((char)d)));
                d = br.read();
            }
            writer.write(""+chars); //put the number of characters in the file 
            writer.write("~,~");

            //parse bit with 0+7 101011
            String str;
            Byte b;
            while(file.length()>=7){
                str = file.substring(0,7);
                b = Byte.parseByte("0"+str, 2);
                writer.write(b);
                file = file.substring(7);
            }
            
            String temp = "";
            for(int r = 7-file.length(); r>0; r--)
                temp = temp+"0";
                
            writer.write(Byte.parseByte("0"+file+temp, 2));
            writer.write("~,~");

            writer.flush();
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void byteDecode(){
        try{ 
            Scanner scan = new Scanner(new FileReader("compressedFile.txt"));
            scan.useDelimiter("~,~");
            FileWriter writer=new FileWriter("decompressedFile.txt");
            TreeMap decodeIndex = new TreeMap();

            if(useMap){
                int indexLength = Integer.parseInt(scan.next());
                String keyVal;
                char val;
                String key;
                while(indexLength>0){  //read line now instead and get first char then use rest of 01010101 as key, delimiter to get line
                    keyVal = scan.next();
                    val = keyVal.charAt(0);
                    key = keyVal.substring(1);
                    indexLength--;
                    decodeIndex.put(key, val);
                }
            }
            else{ //switch indexes key and val for decodeindex, this is just for testing purposes
                Iterator iter = indexes.keySet().iterator();
                char c;
                while(iter.hasNext()){
                    c = (char)iter.next();
                    decodeIndex.put(indexes.get(c), c);
                }
            }
            int numChars = Integer.parseInt(scan.next());
            
            String paths = "";
            
            String str = scan.next();
            byte[] bytes = str.getBytes();
            StringBuilder binary = new StringBuilder();
            for (byte b : bytes)
            {
                int val = b;
                val <<= 1;  //BIT SHIFTS PAST THE SIGN BIT
                for (int i = 0; i < 7; i++) //INCREMENTS THROUGH THE NEXT 7 BITS
                {
                    binary.append((val & 128) == 0 ? 0 : 1);
                    val <<= 1;
                }
            }
            
            String file = binary.toString();

            int n = 1;
            while(file.length()>0 && numChars>0){
                while(!decodeIndex.containsKey(file.substring(0,n)))
                    n++;
                writer.write((char)decodeIndex.get(file.substring(0,n)));
                file = file.substring(n);
                n=1;
                numChars--;
            }

            writer.flush();
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
