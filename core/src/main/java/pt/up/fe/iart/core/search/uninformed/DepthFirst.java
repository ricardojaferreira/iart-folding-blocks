package pt.up.fe.iart.core.search.uninformed;

import pt.up.fe.iart.core.search.TraversalStrategy;
import pt.up.fe.iart.core.structures.graph.Edge;
import pt.up.fe.iart.core.structures.graph.Graph;
import pt.up.fe.iart.core.structures.graph.Vertex;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Stack;

public abstract class DepthFirst<V> implements TraversalStrategy<V> {

    /**
     *
     * @param graph
     * @param root
     * @return
     */
    @Override
    public Vertex<V> getResultNode(Graph<V> graph, Vertex<V> root) {
        Set<Vertex<V>> expansion = new LinkedHashSet<>();
        Stack<Vertex<V>> stack = new Stack<>();
        stack.push(root);

        while (!stack.empty()) {
            Vertex<V> vertex = stack.pop();
            if (reachObjective(graph, vertex)) {
                return vertex;
            }
            if (!expansion.contains(vertex)) {
                expansion.add(vertex);
                for (Edge<V> e: vertex.getAdjacent()) {
                    if (!expansion.contains(e.getDestination())) {
                        stack.push(e.getDestination());
                    }
                }
            }
        }
        return null;
    }
}
