package pt.up.fe.iart.application.searchimpl;

import org.junit.Before;
import org.junit.Test;
import pt.up.fe.iart.application.structures.BoardOperationsImpl;
import pt.up.fe.iart.application.structures.GraphOperationsImpl;
import pt.up.fe.iart.core.structures.board.Block;
import pt.up.fe.iart.core.structures.board.Board;
import pt.up.fe.iart.core.structures.board.BoardOperations;
import pt.up.fe.iart.core.structures.board.Operators;
import pt.up.fe.iart.core.structures.board.Position;
import pt.up.fe.iart.core.structures.graph.Graph;
import pt.up.fe.iart.core.structures.graph.Vertex;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class GraphOperationsImplTest {

    private BoardOperations boardOperations;

    @Before
    public void setUp() {
        boardOperations = new BoardOperationsImpl();
    }

    @Test
    public void expandGraph() {
        GraphOperationsImpl victim = new GraphOperationsImpl(new BoardOperationsImpl());
        Board board = new Board(5, 5);
        board.generateSquaredBoard();
        boardOperations.addBlock(board, new Block(board.getNextBlockId(), Arrays.asList(new Position(0, 0))));
        Vertex<Board> root = new Vertex<>(board);
        Graph<Board> graph = new Graph<>();
        graph.addVertex(root);

        assertEquals(1, graph.getVertexList().size());
        assertEquals(root, graph.getVertexList().get(0));

        int exp01 = victim.expandGraph(graph, root);
        assertEquals(2, exp01);
        assertEquals(3, graph.getVertexList().size());
        assertEquals(graph.getVertexList().get(1), graph.getVertexList().get(0).getAdjacent().get(0).getDestination());
        assertEquals(graph.getVertexList().get(2), graph.getVertexList().get(0).getAdjacent().get(1).getDestination());

        int exp02 = victim.expandGraph(graph, graph.getVertexList().get(1));
        int exp03 = victim.expandGraph(graph, graph.getVertexList().get(2));
        assertEquals(2, exp02);
        assertEquals(2, exp03);
        assertEquals(6, graph.getVertexList().size());
        assertEquals(graph.getVertexList().get(3), graph.getVertexList().get(1).getAdjacent().get(0).getDestination());
        assertEquals(graph.getVertexList().get(4), graph.getVertexList().get(1).getAdjacent().get(1).getDestination());
        assertEquals(graph.getVertexList().get(4), graph.getVertexList().get(2).getAdjacent().get(0).getDestination());
        assertEquals(graph.getVertexList().get(5), graph.getVertexList().get(2).getAdjacent().get(1).getDestination());

        int exp04 = victim.expandGraph(graph, graph.getVertexList().get(3));
        int exp05 = victim.expandGraph(graph, graph.getVertexList().get(4));
        int exp06 = victim.expandGraph(graph, graph.getVertexList().get(5));
        assertEquals(1, exp04);
        assertEquals(2, exp05);
        assertEquals(1, exp06);
        assertEquals(8, graph.getVertexList().size());
        assertEquals(graph.getVertexList().get(6), graph.getVertexList().get(3).getAdjacent().get(0).getDestination());
        assertEquals(graph.getVertexList().get(6), graph.getVertexList().get(4).getAdjacent().get(0).getDestination());
        assertEquals(graph.getVertexList().get(7), graph.getVertexList().get(4).getAdjacent().get(1).getDestination());

        int exp07 = victim.expandGraph(graph, graph.getVertexList().get(6));
        int exp08 = victim.expandGraph(graph, graph.getVertexList().get(7));
        assertEquals(1, exp07);
        assertEquals(1, exp08);
        assertEquals(9, graph.getVertexList().size());
        assertEquals(graph.getVertexList().get(8), graph.getVertexList().get(6).getAdjacent().get(0).getDestination());
        assertEquals(graph.getVertexList().get(8), graph.getVertexList().get(7).getAdjacent().get(0).getDestination());

        int exp09 = victim.expandGraph(graph, graph.getVertexList().get(8));
        assertEquals(0, exp09);
        assertEquals(9, graph.getVertexList().size());
    }

    @Test
    public void getShortestPath() {
        GraphOperationsImpl victim = new GraphOperationsImpl(new BoardOperationsImpl());
        Board board = new Board(4, 4);
        board.generateSquaredBoard();
        boardOperations.addBlock(board, new Block(board.getNextBlockId(), Arrays.asList(new Position(0, 0))));
        Vertex<Board> root = new Vertex<>(board);
        Graph<Board> graph = new Graph<>();
        graph.addVertex(root);
        int i = 1;
        int index = 0;
        while (i != 0) {
            i = victim.expandGraph(graph, graph.getVertexList().get(index));
            index++;
        }

        assertEquals(9, graph.getVertexList().size());

        List<Vertex<Board>> path01 = victim.getShortestPath(graph, graph.getVertexList().get(0), graph.getVertexList().get(3));
        List<String> expectedMovements =
                Arrays.asList(
                        "Block 1 - " + Operators.DOUBLE_DOWN.getMovementName(),
                        "Block 1 - " + Operators.DOUBLE_DOWN.getMovementName(),
                        "Block 1 - " + Operators.DOUBLE_DOWN.getMovementName());
        checkMovementOrder(expectedMovements, path01, graph.getVertexList().get(3));

        List<Vertex<Board>> path02 = victim.getShortestPath(graph, graph.getVertexList().get(0), graph.getVertexList().get(8));
        List<String> expectedMovements02 = Arrays.asList(
                "Block 1 - " + Operators.DOUBLE_DOWN.getMovementName(),
                "Block 1 - " + Operators.DOUBLE_DOWN.getMovementName(),
                "Block 1 - " + Operators.DOUBLE_RIGHT.getMovementName(),
                "Block 1 - " + Operators.DOUBLE_RIGHT.getMovementName(),
                "Block 1 - " + Operators.DOUBLE_RIGHT.getMovementName());
        checkMovementOrder(expectedMovements02, path02, graph.getVertexList().get(8));
    }

    private void checkMovementOrder(List<String> expectedMovents, List<Vertex<Board>> path, Vertex<Board> destination) {
        assertEquals(expectedMovents.size(), path.size());
        for (int i = 0; i < path.size() - 1; i++) {
             Optional<Vertex<Board>> nextVertex = Optional.ofNullable(path.get(i + 1));
             if (nextVertex.isPresent()) {
                 assertEquals(expectedMovents.get(i),
                         path.get(i).getAdjacent().stream()
                                 .filter(e -> e.getDestination().equals(nextVertex.get()))
                                 .findFirst().get()
                                 .getLabel());
             } else {
                 assertEquals(expectedMovents.get(i),
                         path.get(i).getAdjacent().stream()
                                 .filter(e -> e.getDestination().equals(destination))
                                 .findFirst().get()
                                 .getLabel());
             }
        }
    }
}
