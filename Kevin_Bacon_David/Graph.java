
import java.util.*;
public class Graph<V, E> extends AbstractGraph<V, E>{

    public Graph(){

    }

    public void addVertex(V v){
        if(v==null)
            throw new NullPointerException();
        adjacencyMap.put( v, new HashMap<V, EdgeInfo<E>>());
        vertexInfo.put(v, new VertexInfo<V>(v));
    }

    public boolean containsVertex(V v){
        return vertexInfo.containsKey(v);
    }

    public Collection<V> neighbors(V v){
        if(v==null)
            throw new NullPointerException();
        else if(!containsVertex(v))
            throw new IllegalArgumentException();
        return adjacencyMap.get(v).keySet();
    }

    public void addEdge(V v1, V v2, E e){
        if(v1==null||v2==null||e==null)
            throw new NullPointerException();
        else if((!containsVertex(v1))||(!containsVertex(v2)))
            throw new IllegalArgumentException();
        adjacencyMap.get(v1).put(v2, new EdgeInfo<E>(e));
        adjacencyMap.get(v2).put(v1, new EdgeInfo<E>(e));
        edgeList.add(e);
    }

    public void addEdge(V v1, V v2, E e, int weight){
        if(v1==null||v2==null||e==null)
            throw new NullPointerException();
        else if((!containsVertex(v1))||(!containsVertex(v2)))
            throw new IllegalArgumentException();
        adjacencyMap.get(v1).put(v2, new EdgeInfo<E>(e, weight));
        adjacencyMap.get(v2).put(v1, new EdgeInfo<E>(e, weight));
        edgeList.add(e);
    }

    public boolean containsEdge(V v1, V v2){
        if(v1==null||v2==null)
            throw new NullPointerException();
        else if((!containsVertex(v1))||(!containsVertex(v2)))
            throw new IllegalArgumentException();
        return adjacencyMap.get(v1).containsKey(v2)||adjacencyMap.get(v2).containsKey(v1);
    }

    public E edge(V v1, V v2){
        if(v1==null||v2==null)
            throw new NullPointerException();
        else if((!containsVertex(v1))||(!containsVertex(v2)))
            throw new IllegalArgumentException();
        if(containsEdge(v1, v2))
            return adjacencyMap.get(v1).get(v2).e;
        return null;
    }

    public int edgeWeight(V v1, V v2){
        if(v1==null||v2==null)
            throw new NullPointerException();
        else if((!containsVertex(v1))||(!containsVertex(v2)))
            throw new IllegalArgumentException();
        if(containsEdge(v1, v2))
            return adjacencyMap.get(v1).get(v2).weight;
        return -1;
    }

}
