package pt.up.fe.iart.core.search;

import pt.up.fe.iart.core.structures.graph.Graph;
import pt.up.fe.iart.core.structures.graph.GraphOperations;
import pt.up.fe.iart.core.structures.graph.Vertex;

import java.util.List;

public abstract class TraversalStrategy<V> {

    private GraphOperations<V> graphOperations;

    /**
     *
     * @param graphOperations
     */
    public TraversalStrategy(GraphOperations<V> graphOperations) {
        this.graphOperations = graphOperations;
    }

    /**
     *
     * @param graph
     * @param source
     * @param destination
     * @return
     */
    public List<Vertex<V>> getShortestPath(Graph<V> graph, Vertex<V> source, Vertex<V> destination) {
        return graphOperations.getShortestPath(graph, source, destination);
    }

    /**
     *
     * @param graph
     * @param root
     * @return
     */
    public abstract Vertex<V> getResultNode(Graph<V> graph, Vertex<V> root);

    /**
     *
     * @return
     */
    protected GraphOperations<V> getGraphOperations() {
        return graphOperations;
    }
}
