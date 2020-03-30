package pt.up.fe.iart.application.searchimpl;

import pt.up.fe.iart.core.search.informed.Greedy;
import pt.up.fe.iart.core.structures.board.Board;
import pt.up.fe.iart.core.structures.board.Cell;
import pt.up.fe.iart.core.structures.graph.GraphOperations;
import pt.up.fe.iart.core.structures.graph.Vertex;

import java.util.Comparator;

public class MostFilledGreedyImpl extends Greedy<Board> {

    public MostFilledGreedyImpl(GraphOperations<Board> graphOperations) {
        super(graphOperations);
    }

    /**
     *
     * @return
     */
    @Override
    public Comparator<Vertex<Board>> heuristic() {
        return Comparator.comparingLong(v -> v.getContent().getCells().stream().filter(Cell::isFilled).count());
    }
}
