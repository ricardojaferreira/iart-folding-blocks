package pt.up.fe.iart.core.structures.graph;

public interface GraphOperations<V> {
    boolean reachObjective(Vertex<V> vertex);
    int expandGraph(Graph<V> graph, Vertex<V> vertex);
    void addVertexToGraph(Graph<V> graph, Vertex<V> source, Vertex<V> destination);
}
