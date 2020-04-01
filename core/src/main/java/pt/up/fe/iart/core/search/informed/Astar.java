package pt.up.fe.iart.core.search.informed;

import pt.up.fe.iart.core.Constants;
import pt.up.fe.iart.core.search.TraversalStrategy;
import pt.up.fe.iart.core.structures.CustomPair;
import pt.up.fe.iart.core.structures.graph.Edge;
import pt.up.fe.iart.core.structures.graph.Graph;
import pt.up.fe.iart.core.structures.graph.GraphOperations;
import pt.up.fe.iart.core.structures.graph.Vertex;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public abstract class Astar<V> extends TraversalStrategy<V> {

    /**
     * @param graphOperations
     */
    public Astar(GraphOperations<V> graphOperations) {
        super(graphOperations);
    }

    public abstract int getHeuristicRate(Vertex<V> vertex);

    /**
     *
     * @param graph
     * @param root
     * @return
     */
    @Override
    public Vertex<V> getResultNode(Graph<V> graph, Vertex<V> root) {
        List<CustomPair<Vertex<V>, Integer>> expWithAStarRate = new LinkedList<>(); //TotalCost
        List<CustomPair<Vertex<V>, CustomPair<Integer, Integer>>> expWithHeuristicRate = new LinkedList<>(); //
        List<CustomPair<Vertex<V>, Integer>> stack = new LinkedList<>(); //TotalCost f(n) = g(n) + h(n)
        stack.add(CustomPair.of(root, 0));
        expWithHeuristicRate.add(CustomPair.of(root, CustomPair.of(0, 0)));

        while (!stack.isEmpty()) {
            CustomPair<Vertex<V>, Integer> vertexWithStarRate = stack.remove(0);
            if (super.getGraphOperations().reachObjective(vertexWithStarRate.fst)) {
                return vertexWithStarRate.fst;
            } else {
                expandAndCalculateHeuristicRate(expWithHeuristicRate, graph, vertexWithStarRate.fst);
            }

            Optional<CustomPair<Vertex<V>, Integer>> expandedGraph = expWithAStarRate.stream()
                    .filter(p -> p.fst.equals(vertexWithStarRate.fst))
                    .findFirst();

            if (!expandedGraph.isPresent()) {
                expWithAStarRate.add(vertexWithStarRate);
                vertexWithStarRate.fst.getAdjacent().stream().map(Edge::getDestination).forEach(v -> {
                    Optional<CustomPair<Integer, Integer>> totCost = expWithHeuristicRate
                            .stream()
                            .filter(p -> p.fst.equals(v))
                            .map(p -> p.snd)
                            .findFirst();
                    totCost.ifPresent(h -> stack.add(CustomPair.of(v, h.snd == Integer.MAX_VALUE ? h.snd : h.fst + h.snd)));
                });
            }

            stack.sort(aStarOrder());
        }
        return null;
    }

    /**
     *
     * @return
     */
    public Comparator<CustomPair<Vertex<V>, Integer>> aStarOrder() {
        return Comparator.comparingLong(p -> p.snd);
    }

    /**
     *
     * @param heuristicRates
     * @param graph
     * @param vertex
     */
    private void expandAndCalculateHeuristicRate(List<CustomPair<Vertex<V>, CustomPair<Integer, Integer>>> heuristicRates,
                                                 Graph<V> graph, Vertex<V> vertex) {
       super.getGraphOperations().expandGraph(graph, vertex);
       int actualCost = heuristicRates.stream().filter(p -> p.fst.equals(vertex)).map(p -> p.snd.fst).findFirst().orElse(0);
       vertex.getAdjacent().stream().map(Edge::getDestination).forEach(v -> {
           Optional<CustomPair<Vertex<V>, CustomPair<Integer, Integer>>> node =
                   heuristicRates.stream().filter(p -> p.fst.equals(v)).findFirst();
                   if (!node.isPresent()) {
                       heuristicRates.add(CustomPair.of(v, CustomPair.of(actualCost + Constants.TRAVERSAL_COST, getHeuristicRate(v))));
                   } else {
                       if (actualCost + Constants.TRAVERSAL_COST < node.get().snd.fst) {
                           for (int i = 0; i < heuristicRates.size(); i++) {
                               if (heuristicRates.get(i).equals(node.get())) {
                                   heuristicRates.set(i, CustomPair.of(v,
                                                   CustomPair.of(actualCost + Constants.TRAVERSAL_COST, node.get().snd.snd)));
                               }
                           }
                       }
                   }
               }
            );
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "A-Star search";
    }
}
