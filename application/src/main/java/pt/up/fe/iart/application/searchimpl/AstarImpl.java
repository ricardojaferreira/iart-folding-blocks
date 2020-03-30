package pt.up.fe.iart.application.searchimpl;

import pt.up.fe.iart.core.search.informed.Astar;
import pt.up.fe.iart.core.structures.board.Board;
import pt.up.fe.iart.core.structures.graph.Graph;
import pt.up.fe.iart.core.structures.graph.GraphOperations;
import pt.up.fe.iart.core.structures.graph.Vertex;

public class AstarImpl extends Astar<Board> {

    private GraphOperations<Board> heuristicGraphOperations;

    public AstarImpl(GraphOperations<Board> graphOperations, GraphOperations<Board> heuristicGraphOperations) {
        super(graphOperations);
        this.heuristicGraphOperations = heuristicGraphOperations;
    }

//    @Override
//    public Comparator<Pair<Vertex<Board>, Integer>> aStarOrder() {
//        Comparator<Pair<Vertex<Board>, Integer>> comparator =  super.aStarOrder();
//        return comparator.thenComparing(p -> p.fst.getContent().getCells().stream().filter(Cell::isEmpty).count());
//    }

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
//        GraphOperationsImpl graphOperationsImpl = new GraphOperationsImpl(new FilledPositionsBoardOperationsImpl());
        MostFilledGreedyImpl greedy = new MostFilledGreedyImpl(heuristicGraphOperations);
        Vertex<Board> goal = greedy.getResultNode(graph, root);
        if (goal != null) {
            return heuristicGraphOperations.getShortestPath(graph, root, goal).size();
        }
        return Integer.MAX_VALUE;
    }
}
