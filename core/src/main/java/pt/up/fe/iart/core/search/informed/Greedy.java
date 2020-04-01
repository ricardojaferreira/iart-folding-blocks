package pt.up.fe.iart.core.search.informed;

import pt.up.fe.iart.core.search.TraversalStrategy;
import pt.up.fe.iart.core.structures.graph.Edge;
import pt.up.fe.iart.core.structures.graph.Graph;
import pt.up.fe.iart.core.structures.graph.GraphOperations;
import pt.up.fe.iart.core.structures.graph.Vertex;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class Greedy<V> extends TraversalStrategy<V> {

    public Greedy(GraphOperations<V> graphOperations) {
        super(graphOperations);
    }

    public abstract Comparator<Vertex<V>> heuristic();

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
                List<Vertex<V>> vertexByPriority = vertex.getAdjacent().stream()
                        .map(Edge::getDestination)
                        .sorted(heuristic())
                        .collect(Collectors.toList());
                vertexByPriority.forEach(v -> {
                    if (!expansion.contains(v)) {
                        stack.push(v);
                    }
                });
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
        return "Greedy search";
    }
}
