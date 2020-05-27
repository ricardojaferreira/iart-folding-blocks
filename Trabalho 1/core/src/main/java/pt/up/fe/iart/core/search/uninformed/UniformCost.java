package pt.up.fe.iart.core.search.uninformed;

import pt.up.fe.iart.core.Constants;
import pt.up.fe.iart.core.search.TraversalStrategy;
import pt.up.fe.iart.core.structures.CustomPair;
import pt.up.fe.iart.core.structures.graph.Edge;
import pt.up.fe.iart.core.structures.graph.Graph;
import pt.up.fe.iart.core.structures.graph.GraphOperations;
import pt.up.fe.iart.core.structures.graph.Vertex;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class UniformCost<V> extends TraversalStrategy<V> {

    /**
     * @param graphOperations
     */
    public UniformCost(GraphOperations<V> graphOperations) {
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
        List<CustomPair<Vertex<V>, Integer>> vertexWithCost = new ArrayList<>();
        List<CustomPair<Vertex<V>, Integer>> stack = new ArrayList<>();
        vertexWithCost.add(CustomPair.of(root, 0));
        stack.add(CustomPair.of(root, 0));

        while (!stack.isEmpty()) {
            CustomPair<Vertex<V>, Integer> vertex = stack.remove(0);
            if (super.getGraphOperations().reachObjective(vertex.fst)) {
                return vertex.fst;
            } else {
                expandWithCost(vertexWithCost, graph, vertex.fst);
            }

            if (!expansion.contains(vertex.fst)) {
                expansion.add(vertex.fst);
                vertex.fst.getAdjacent().stream().map(Edge::getDestination).forEach(v -> {
                    Integer totCost =
                            vertexWithCost.stream().filter(p -> p.fst.equals(v)).map(p -> p.snd).findFirst().orElse(Integer.MAX_VALUE);
                    stack.add(CustomPair.of(v, totCost));
                });
            }

            stack.sort(uniformSort());
        }
        return null;
    }

    private Comparator<CustomPair<Vertex<V>, Integer>> uniformSort() {
        return Comparator.comparingLong(p -> p.snd);
    }

    private void expandWithCost(List<CustomPair<Vertex<V>, Integer>> vertexWithCost, Graph<V> graph, Vertex<V> source) {
        super.getGraphOperations().expandGraph(graph, source);
        int actualCost = vertexWithCost.stream().filter(p -> p.fst.equals(source)).map(p -> p.snd).findFirst().orElse(0);
        source.getAdjacent().stream().map(Edge::getDestination).forEach(v -> {
            Optional<CustomPair<Vertex<V>, Integer>> node = vertexWithCost.stream().filter(p -> p.fst.equals(v)).findFirst();
            if (!node.isPresent()) {
                vertexWithCost.add(CustomPair.of(v, actualCost + Constants.TRAVERSAL_COST));
            } else {
                if (actualCost + Constants.TRAVERSAL_COST < node.get().snd) {
                    for (int i = 0; i < vertexWithCost.size(); i++) {
                        if (vertexWithCost.get(i).equals(node.get())) {
                            vertexWithCost.set(i, CustomPair.of(v, actualCost + Constants.TRAVERSAL_COST));
                        }
                    }
                }
            }
        });
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Uniform cost search";
    }
}
