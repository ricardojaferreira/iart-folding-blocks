package pt.up.fe.iart.application.searchimpl;

import pt.up.fe.iart.core.search.uninformed.BreadthFirst;
import pt.up.fe.iart.core.structures.board.Board;
import pt.up.fe.iart.core.structures.graph.Vertex;

import java.util.Set;

public class BreadthFirstImpl extends BreadthFirst<Board> {

    public BreadthFirstImpl(GraphOperationsImpl graphOperations) {
        super(graphOperations);
    }

    /**
     *
     * @param traversalExpansion
     * @return
     */
    @Override
    public double getTraversalCost(Set<Vertex<Board>> traversalExpansion) {
        return 0;
    }
}
