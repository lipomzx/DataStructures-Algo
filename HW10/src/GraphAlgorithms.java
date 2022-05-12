
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Set;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Map;
import java.util.HashMap;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Zexin Chen
 * @userid zchen849
 * @GTID 903649317
 * @version 1.0
 * Resources: https://www.baeldung.com/java-prim-algorithm
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null || !graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("Invalid Arguments");
        }
        List<Vertex<T>> vertices = new ArrayList<>();
        Queue<Vertex<T>> queue = new LinkedList<>();
        Set<Vertex<T>> visited = new HashSet<>();

        queue.add(start);
        visited.add(start);
        while (!queue.isEmpty()) {
            Vertex<T> v = queue.remove();
            vertices.add(v);
            for (VertexDistance<T> vd : graph.getAdjList().get(v)) {
                if (!visited.contains(vd.getVertex())) {
                    queue.add(vd.getVertex());
                    visited.add((vd.getVertex()));
                }
            }
        }
        return vertices;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     *
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null || !graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("Invalid Arguments");
        }
        List<Vertex<T>> vertices = new ArrayList<>();
        Set<Vertex<T>> visited = new HashSet<>();
        dfs(start, graph, vertices, visited);
        return vertices;
    }

    /**
     * Helper recursive function of dfs
     * @param start start vertex of the graph
     * @param graph graph to be traversed
     * @param vertices List to store all vertices
     * @param visited Set to store vertices been visited
     * @param <T> generic type
     */
    private static <T> void dfs(Vertex<T> start, Graph<T> graph, List<Vertex<T>> vertices, Set<Vertex<T>> visited) {
        vertices.add(start);
        visited.add(start);
        for (VertexDistance<T> v : graph.getAdjList().get(start)) {
            if (!visited.contains(v.getVertex())) {
                dfs(v.getVertex(), graph, vertices, visited);
            }
        }
    }


    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null || graph == null || !graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("Invalid Arguments");
        }
        Queue<VertexDistance<T>> pq = new PriorityQueue<>();
        Map<Vertex<T>, Integer> distanceMap = new HashMap<>();
        Set<Vertex<T>> visited = new HashSet<>();
        for (Vertex<T> v : graph.getAdjList().keySet()) {
            if (v.equals(start)) {
                distanceMap.put(v, 0);
            } else {
                distanceMap.put(v, Integer.MAX_VALUE);
            }
        }
        visited.add(start);
        pq.add(new VertexDistance<>(start, 0));
        while (!pq.isEmpty() && visited.size() < graph.getVertices().size()) {
            VertexDistance<T> top = pq.remove();
            for (VertexDistance<T> vd : graph.getAdjList().get(top.getVertex())) {
                if (!visited.contains(vd.getVertex())) {
                    visited.add(vd.getVertex());
                }
                int dis = top.getDistance() + vd.getDistance();
                if (distanceMap.get(vd.getVertex()) > dis) {
                    distanceMap.put(vd.getVertex(), dis);
                    pq.add(new VertexDistance<>(vd.getVertex(), dis));
                }
            }
        }
        return distanceMap;
    }

    /**
     * Runs Prim's algorithm on the given graph and returns the Minimum
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * You should NOT allow self-loops or parallel edges in the MST.
     *
     * You may import/use PriorityQueue, java.util.Set, and any class that 
     * implements the aforementioned interface.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for this method (storing the adjacency list in a variable is fine).
     *
     * @param <T> the generic typing of the data
     * @param start the vertex to begin Prims on
     * @param graph the graph we are applying Prims to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Set<Edge<T>> prims(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null || !graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("Invalid Arguments");
        }
        PriorityQueue<Edge<T>> pq = new PriorityQueue<>();
        Set<Vertex<T>> visited = new HashSet<>();
        Set<Edge<T>> edges = new HashSet<>();

        visited.add(start);
        for (VertexDistance<T> vs : graph.getAdjList().get(start)) {
            pq.add(new Edge<>(start, vs.getVertex(), vs.getDistance()));
        }
        while (!pq.isEmpty() && edges.size() < 2 * (graph.getVertices().size() - 1)) {
            Edge<T> temp = pq.remove();
            if (!visited.contains(temp.getV())) {
                edges.add(temp);
                edges.add(new Edge<>(temp.getV(), temp.getU(), temp.getWeight()));
                visited.add(temp.getV());
                for (VertexDistance<T> v : graph.getAdjList().get(temp.getV())) {
                    if (!visited.contains(v.getVertex())) {
                        pq.add(new Edge<>(temp.getV(), v.getVertex(), v.getDistance()));
                    }
                }
            }
        }

        if (edges.size() < 2 * (graph.getVertices().size() - 1)) {
            return null;
        }
        return edges;
    }
}