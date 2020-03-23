package pt.up.fe.iart.core.search;

import pt.up.fe.iart.core.structures.graph.Graph;
import pt.up.fe.iart.core.structures.graph.Vertex;

import java.util.Set;

public interface TraversalStrategy<V> {
    boolean reachObjective(Graph<V> graph, Vertex<V> vertex);
    Vertex<V> getResultNode(Graph<V> graph, Vertex<V> root);
    double getTraversalCost(Set<Vertex<V>> traversalExpansion);
}
