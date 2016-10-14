import java.util.*;
import java.io.*;

public class KevinBacon {
    public static void main(String[] args) throws FileNotFoundException {
        SearchableGraph<String, String> graph = buildGraph();
        System.out.println(graph);
        System.out.println("Welcome to Six Degrees of Kevin Bacon! \n Which actor would you like to search?");
        Scanner console = new Scanner(System.in);
        String actor = console.next();
        if(!actor.equals("all")){
            String act2 = console.next();
            baconate(graph, actor+" "+act2);
        }
        else
            baconate(graph, actor);
    }

    public static SearchableGraph<String, String> buildGraph() throws FileNotFoundException {
        Scanner input = new Scanner(new File("movies.txt"));
        SearchableGraph<String, String> graph = new SearchableGraphDFS<String, String>();

        while (input.hasNextLine()) {
            Scanner line = new Scanner(input.nextLine()).useDelimiter(";");

            String movie = line.next();

            // get all of the actors in the movie
            List<String> actors = new ArrayList<String>();
            while (line.hasNext()) {
                String actor = line.next();
                graph.addVertex(actor);
                actors.add(actor);
            }

            // connect all of the actors
            for (int i = 0; i < actors.size(); i++) {
                for (int j = 1; j < actors.size(); j++) {
                    graph.addEdge(actors.get(i), actors.get(j), movie);
                }
            }

        }

        return graph;
    }

    public static void baconate(SearchableGraph<String, String> graph, String act){
        List<String> path;
        String kev = "Kevin Bacon";
        Iterator<String> iter;
        if(!act.equals("all")){
            try{
                path = graph.shortestPath(act, kev);
            } 
            catch(Exception e){
                System.out.println(act+" has a Bacon Number of Infinity...");
                return;
            }
            if(path==null)
                System.out.println(act+" has a Bacon Number of Infinity...");
            else{
                System.out.println(act+" has a Bacon Number of "+(path.size()-1));
                iter = path.iterator();
                String v = iter.next();
                String vv;
                System.out.print(v+" ");
                while(iter.hasNext()){
                    vv = iter.next();
                    System.out.print(graph.edge(v, vv));
                    System.out.print(vv);
                    v = vv;
                }
                System.out.println("");
            }
        }
        else{
            Iterator<String> iter2 = graph.vertices().iterator();
            String a = "";
            while(iter2.hasNext()){
                a = iter2.next();
                path = graph.shortestPath(a, kev);
                if(path!=null){
                    System.out.println(a+" has a Bacon Number of "+(path.size()-1));
                    iter = path.iterator();
                    String v = iter.next();
                    String vv;
                    System.out.print(v+" ");
                    while(iter.hasNext()){
                        vv = iter.next();
                        System.out.print(graph.edge(v, vv));
                        System.out.print(vv);
                        v = vv;
                    }
                    System.out.println("");
                }
                else
                    System.out.println(a+" has a Bacon Number of Infinity...");
                System.out.println("");
            }
        }
    }

}