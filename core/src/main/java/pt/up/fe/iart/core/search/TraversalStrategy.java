package pt.up.fe.iart.core.search;

import pt.up.fe.iart.core.Constants;
import pt.up.fe.iart.core.structures.graph.Edge;
import pt.up.fe.iart.core.structures.graph.Graph;
import pt.up.fe.iart.core.structures.graph.Vertex;

import java.util.Set;

public interface TraversalStrategy<V> {
    Vertex<V> getResultNode(Graph<V> graph, Vertex<V> root);
    double getTraversalCost(Set<Vertex<V>> traversalExpansion);

    default void addEdgeToVertex(Graph<V> graph, Vertex<V> vertexStart, Vertex<V> vertexDestination) {
        if (!graph.getVertexList().contains(vertexDestination)) {
            Edge<V> edge = new Edge<>(vertexDestination, Constants.TRAVERSAL_COST);
            vertexStart.addEdge(edge);
            graph.addVertex(vertexDestination);
        }
    }
}
