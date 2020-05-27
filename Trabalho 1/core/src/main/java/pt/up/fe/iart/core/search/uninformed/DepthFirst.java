package pt.up.fe.iart.core.search.uninformed;

import pt.up.fe.iart.core.search.TraversalStrategy;
import pt.up.fe.iart.core.structures.graph.Edge;
import pt.up.fe.iart.core.structures.graph.Graph;
import pt.up.fe.iart.core.structures.graph.GraphOperations;
import pt.up.fe.iart.core.structures.graph.Vertex;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedHashSet;
import java.util.Set;

public class DepthFirst<V> extends TraversalStrategy<V> {

    public DepthFirst(GraphOperations<V> graphOperations) {
        super(graphOperations);
    }

    /**
     *
     * @param graph
     * @param root
     * @return
     */
    @Override
    public Vertex<V> getResultNode(Graph<V> graph, Vertex<V> root) {
        Set<Vertex<V>> expansion = new LinkedHashSet<>();
        Deque<Vertex<V>> stack = new ArrayDeque<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            Vertex<V> vertex = stack.pop();
            if (super.getGraphOperations().reachObjective(vertex)) {
                return vertex;
            } else {
                super.getGraphOperations().expandGraph(graph, vertex);
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

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Depth first Search";
    }
}
