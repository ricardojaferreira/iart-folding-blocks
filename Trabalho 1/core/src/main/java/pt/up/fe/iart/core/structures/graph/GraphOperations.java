package pt.up.fe.iart.core.structures.graph;

import java.util.List;

public interface GraphOperations<V> {
    boolean reachObjective(Vertex<V> vertex);

    /**
     * Returns the number of new vertex added
     * @param graph
     * @param vertex
     * @return
     */
    int expandGraph(Graph<V> graph, Vertex<V> vertex);
    void addVertexToGraph(Graph<V> graph, Vertex<V> source, Vertex<V> destination, String edgeLabel);
    List<Vertex<V>> getShortestPath(Graph<V> graph, Vertex<V> source, Vertex<V> destination);
}
