package pt.up.fe.iart.application.searchimpl;

import pt.up.fe.iart.core.search.informed.Astar;
import pt.up.fe.iart.core.structures.CustomPair;
import pt.up.fe.iart.core.structures.board.Board;
import pt.up.fe.iart.core.structures.board.Cell;
import pt.up.fe.iart.core.structures.graph.Graph;
import pt.up.fe.iart.core.structures.graph.GraphOperations;
import pt.up.fe.iart.core.structures.graph.Vertex;

import java.util.Comparator;

public class AstarImpl extends Astar<Board> {

    private GraphOperations<Board> heuristicGraphOperations;

    public AstarImpl(GraphOperations<Board> graphOperations, GraphOperations<Board> heuristicGraphOperations) {
        super(graphOperations);
        this.heuristicGraphOperations = heuristicGraphOperations;
    }

    /**
     *
     * @return
     */
    @Override
    public Comparator<CustomPair<Vertex<Board>, Integer>> aStarOrder() {
        Comparator<CustomPair<Vertex<Board>, Integer>> comparator =  super.aStarOrder();
        return comparator.thenComparing(p -> p.fst.getContent().getCells().stream().filter(Cell::isEmpty).count());
    }

    /**
     *
     * @param vertex
     * @return
     */
    @Override
    public int getHeuristicRate(Vertex<Board> vertex) {
        Board board = vertex.getContent().duplicateBoard();
        Vertex<Board> root = new Vertex<>(board);
        Graph<Board> graph = new Graph<>();
        graph.addVertex(root);
        MostFilledGreedyImpl greedy = new MostFilledGreedyImpl(heuristicGraphOperations);
        Vertex<Board> goal = greedy.getResultNode(graph, root);
        if (goal != null) {
            return heuristicGraphOperations.getShortestPath(graph, root, goal).size();
        }
        return Integer.MAX_VALUE;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "A-Star search with " + heuristicGraphOperations.toString() + " heuristic";
    }
}
