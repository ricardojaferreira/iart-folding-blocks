package pt.up.fe.iart.core.search.uninformed;

import pt.up.fe.iart.core.search.TraversalStrategy;
import pt.up.fe.iart.core.structures.graph.Edge;
import pt.up.fe.iart.core.structures.graph.Graph;
import pt.up.fe.iart.core.structures.graph.GraphOperations;
import pt.up.fe.iart.core.structures.graph.Vertex;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;

public class IterativeDepthFirst<V> extends TraversalStrategy<V> {

    public IterativeDepthFirst(GraphOperations<V> graphOperations) {
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
        if (super.getGraphOperations().reachObjective(root)) {
            return root;
        }

        Set<Vertex<V>> expansion = new LinkedHashSet<>();
        Queue<Vertex<V>> queue;
        expansion.add(root);
        queue = getVertexPerLevel(graph, root);

        while (!queue.isEmpty()) {
            Optional<Vertex<V>> goal = depthLimitedSearch(root);
            if (goal.isPresent()) {
                return goal.get();
            } else {
                Queue<Vertex<V>> expandedLevel = new LinkedList<>();
                while (!queue.isEmpty()) {
                    Vertex<V> expand = queue.poll();
                    if (!expansion.contains(expand)) {
                        expansion.add(expand);
                        expandedLevel.addAll(getVertexPerLevel(graph, expand));
                    }
                }
                queue.addAll(expandedLevel);
            }
        }

        return null;
    }

    private Queue<Vertex<V>> getVertexPerLevel(Graph<V> graph, Vertex<V> expandVertex) {
       Queue<Vertex<V>> levelVertexes = new LinkedList<>();
       super.getGraphOperations().expandGraph(graph, expandVertex);
       for (Edge<V> e: expandVertex.getAdjacent()) {
           levelVertexes.add(e.getDestination());
        }
       return levelVertexes;
    }

    private Optional<Vertex<V>> depthLimitedSearch(Vertex<V> vertex) {
        Set<Vertex<V>> expansion = new LinkedHashSet<>();
        Deque<Vertex<V>> stack = new ArrayDeque<>();
        stack.push(vertex);

        while (!stack.isEmpty()) {
            Vertex<V> expandedVertex = stack.pop();
            if (super.getGraphOperations().reachObjective(expandedVertex)) {
                return Optional.of(expandedVertex);
            }
            if (!expansion.contains(expandedVertex)) {
                expansion.add(expandedVertex);
                for (Edge<V> e: expandedVertex.getAdjacent()) {
                    if (!expansion.contains(e.getDestination())) {
                        stack.push(e.getDestination());
                    }
                }
            }
        }

        return Optional.empty();
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Iterative depth first search";
    }
}
