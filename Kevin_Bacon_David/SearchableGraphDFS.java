import java.util.*;
public class SearchableGraphDFS<V, E> extends Graph<V, E> implements SearchableGraph<V, E>{

    public boolean reachable(V v1, V v2){
        if(v1==null||v2==null)
            throw new NullPointerException();
        else if((!containsVertex(v1))||(!containsVertex(v2)))
            throw new IllegalArgumentException();
        boolean answer = reachableHelper(v1, v2);
        clearVertexInfo();
        return answer;
    }

    public boolean reachableHelper(V v1, V v2){
        Iterator iter = adjacencyMap.get(v1).keySet().iterator();
        V v;
        if(v1.equals(v2))
            return true;
        else{
            vertexInfo.get(v1).visited = true;
            while(iter.hasNext()){
                v = (V)iter.next();
                if(!vertexInfo.get(v).visited)
                    return reachableHelper(v, v2)||false;
            }
            return false;
        }
    }

    List<V> theList;
    List<V> tempL;

    public List<V> shortestPath(V v1, V v2){
        if(v1==null||v2==null)
            throw new NullPointerException();
        else if((!containsVertex(v1))||(!containsVertex(v2)))
            throw new IllegalArgumentException();
        if(!reachable(v1, v2))
            return null;
        theList = new LinkedList<V>();
        tempL = new LinkedList<V>();
        tempL.add(v1);
        vertexInfo.get(v1).visited = true;
        shortestPathHelper(v1, v2);
        clearVertexInfo();
        return theList;
    }

    public void shortestPathHelper(V v1, V v2){
        V v;
        if(v1.equals(v2)){
            if(tempL.size()<theList.size()||theList.size()==0){
                theList.clear();
                for(int x=0;x<tempL.size();x++)
                    theList.add(tempL.get(x));
            }
        }
        else{
            Iterator iter = adjacencyMap.get(v1).keySet().iterator();
            while(iter.hasNext()){
                v = (V)iter.next();
                if(!vertexInfo.get(v).visited){
                    vertexInfo.get(v).visited = true;
                    tempL.add(v);
                    shortestPathHelper(v, v2);
                    vertexInfo.get(v).visited = false;
                    tempL.remove(tempL.size()-1);
                }
            }
        }
    }
}
