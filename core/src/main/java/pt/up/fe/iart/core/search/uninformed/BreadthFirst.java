package pt.up.fe.iart.core.search.uninformed;

import pt.up.fe.iart.core.search.TraversalStrategy;
import pt.up.fe.iart.core.structures.graph.Edge;
import pt.up.fe.iart.core.structures.graph.Graph;
import pt.up.fe.iart.core.structures.graph.GraphOperations;
import pt.up.fe.iart.core.structures.graph.Vertex;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class BreadthFirst<V> extends TraversalStrategy<V> {

    public BreadthFirst(GraphOperations<V> graphOperations) {
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
        Queue<Vertex<V>> queue = new LinkedList<>();
        queue.add(root);
        expansion.add(root);

        while (!queue.isEmpty()) {
            Vertex<V> vertex = queue.poll();
            if (super.getGraphOperations().reachObjective(vertex)) {
                return vertex;
            } else {
                super.getGraphOperations().expandGraph(graph, vertex);
            }
            for (Edge<V> e: vertex.getAdjacent()) {
                if (!expansion.contains(e.getDestination())) {
                    queue.add(e.getDestination());
                    expansion.add(e.getDestination());
                }
            }
        }
        return null;
    }
}
