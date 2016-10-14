import java.util.*;
public class SearchableGraphBFS<V, E> extends Graph<V, E> implements SearchableGraph<V, E>{

    public boolean reachable(V v1, V v2){
        if(v1==null||v2==null)
            throw new NullPointerException();
        else if((!containsVertex(v1))||(!containsVertex(v2)))
            throw new IllegalArgumentException();
        Queue<V> neighbors = new LinkedList<V>();
        Iterator<V> iter = neighbors(v1).iterator();
        V v;
        neighbors.add(v1);
        while(neighbors.size()>0){
            v = neighbors.remove();
            if(v.equals(v2)){
                clearVertexInfo();
                return true;
            }
            else{
                vertexInfo.get(v).visited = true;
                iter = neighbors(v).iterator();
                while(iter.hasNext()){
                    v = iter.next();
                    if(!vertexInfo.get(v).visited)
                        neighbors.add(v);
                }
            }
        }
        clearVertexInfo();
        return false;
    }

    public List<V> shortestPath(V v1, V v2){
        if(v1==null||v2==null)
            throw new NullPointerException();
        else if((!containsVertex(v1))||(!containsVertex(v2)))
            throw new IllegalArgumentException();
        List<V> theList = new LinkedList<V>();
        Queue<V> neighbors = new LinkedList<V>();
        Iterator<V> iter = neighbors(v1).iterator();
        V v;
        V vv;
        neighbors.add(v1);
        vertexInfo.get(v1).visited = true;
        while(neighbors.size()>0){
            v = neighbors.remove();
            if(v.equals(v2)){
                theList.add(v);
                while(vertexInfo.get(v).previous!=null){
                    v = vertexInfo.get(v).previous;
                    theList.add(0,v);
                }
                clearVertexInfo();
                return theList;
            }
            else {
                iter = neighbors(v).iterator();
                while(iter.hasNext()){
                    vv = iter.next();
                    if(!vertexInfo.get(vv).visited){
                        vertexInfo.get(vv).previous = v; 
                        vertexInfo.get(vv).visited = true;
                        neighbors.add(vv);
                    }
                }
            }
        }
        clearVertexInfo();
        return null;
    }
}
