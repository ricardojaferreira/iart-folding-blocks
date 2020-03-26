package pt.up.fe.iart.application.searchimpl;

import pt.up.fe.iart.core.Constants;
import pt.up.fe.iart.core.structures.board.Board;
import pt.up.fe.iart.core.structures.board.Cell;
import pt.up.fe.iart.core.structures.board.Operators;
import pt.up.fe.iart.core.structures.board.Position;
import pt.up.fe.iart.core.structures.graph.Edge;
import pt.up.fe.iart.core.structures.graph.Graph;
import pt.up.fe.iart.core.structures.graph.GraphOperations;
import pt.up.fe.iart.core.structures.graph.Vertex;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GraphOperationsImpl implements GraphOperations<Board> {

    /**
     *
     * @param vertex
     * @return
     */
    @Override
    public boolean reachObjective(Vertex<Board> vertex) {
        Board board = vertex.getContent();
        return board.getCells().stream().filter(Cell::belongsToBoard).allMatch(Cell::isFilled);
    }

    /**
     *
     * @param graph
     * @param vertex
     * @return
     */
    @Override
    public int expandGraph(Graph<Board> graph, Vertex<Board> vertex) {
        Board board = vertex.getContent();
        List<Vertex<Board>> destinations = new ArrayList<>();
        board.getBlocks().forEach(block -> {
            for (Operators o: Operators.values()) {
                Set<Position> symmetric = o.getSymmetricBlockPositions(block, board);
                if (!symmetric.isEmpty()) {
                    Board newBoard = board.duplicateBoard();
                    newBoard.updateBlockWithNewPositions(block.getBlockId(), symmetric);
                    Vertex<Board> tempDestination = new Vertex<>(newBoard);
                    Vertex<Board> destination = graph.getVertexList().stream()
                            .filter(v -> v.equals(tempDestination))
                            .findAny()
                            .orElse(tempDestination);
                    destinations.add(destination);
                }
            }
        });
        destinations.forEach(destination -> addVertexToGraph(graph, vertex, destination));
        return destinations.size();
    }

    /**
     *
     * @param graph
     * @param source
     * @param destination
     */
    @Override
    public void addVertexToGraph(Graph<Board> graph, Vertex<Board> source, Vertex<Board> destination) {
        Edge<Board> edge = new Edge<>(destination, Constants.TRAVERSAL_COST);
        source.addEdge(edge);
        if (!graph.getVertexList().contains(destination)) {
            graph.addVertex(destination);
        }
    }
}
