package pt.up.fe.iart.application.searchimpl;

import org.junit.Before;
import org.junit.Test;
import pt.up.fe.iart.core.structures.board.Block;
import pt.up.fe.iart.core.structures.board.Board;
import pt.up.fe.iart.core.structures.board.BoardOperations;
import pt.up.fe.iart.core.structures.board.Position;
import pt.up.fe.iart.core.structures.graph.Graph;
import pt.up.fe.iart.core.structures.graph.Vertex;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MostFilledGreedyImplTest {

    private BoardOperations boardOperations;

    @Before
    public void setUp() {
        boardOperations = new BoardOperationsImpl();
    }

    @Test
    public void getResultNode() {
        MostFilledGreedyImpl victim = new MostFilledGreedyImpl(new GraphOperationsImpl(boardOperations));

        Board board = new Board(4, 4);
        board.generateSquaredBoard();
        Block block01 = new Block(board.getNextBlockId());
        block01.addPosition(new Position(0, 0));
        boardOperations.addBlock(board, block01);
        Block block02 = new Block(board.getNextBlockId());
        block02.addPosition(new Position(3, 2));
        block02.addPosition(new Position(3, 3));
        boardOperations.addBlock(board, block02);

        Vertex<Board> root = new Vertex<>(board);
        Graph<Board> graph = new Graph<>();
        graph.addVertex(root);

        Vertex<Board> destination = victim.getResultNode(graph, root);

        Board expectedBoard = new Board(4, 4);
        expectedBoard.generateSquaredBoard();
        boardOperations.addBlock(expectedBoard,
                new Block(1, Arrays.asList(
                        new Position(0, 0),
                        new Position(1, 0),
                        new Position(2, 0),
                        new Position(3, 0),
                        new Position(0, 1),
                        new Position(1, 1),
                        new Position(2, 1),
                        new Position(3, 1)
                    )
                ));
        boardOperations.addBlock(expectedBoard, new Block(2, Arrays.asList(
                        new Position(0, 2),
                        new Position(1, 2),
                        new Position(2, 2),
                        new Position(3, 2),
                        new Position(0, 3),
                        new Position(1, 3),
                        new Position(2, 3),
                        new Position(3, 3)
                    )
                )
        );

        Vertex<Board> expectedVertex = new Vertex<>(expectedBoard);
        assertEquals(expectedVertex.getContent(), destination.getContent());
    }

    @Test
    public void testPathForMostFilledMovement() {
        MostFilledGreedyImpl victim = new MostFilledGreedyImpl(new GraphOperationsImpl(new BoardOperationsImpl()));

        Board board = new Board(4, 4);
        board.generateSquaredBoard();
        Block block01 = new Block(board.getNextBlockId());
        block01.addPosition(new Position(0, 0));
        boardOperations.addBlock(board, block01);
        Block block02 = new Block(board.getNextBlockId());
        block02.addPosition(new Position(3, 2));
        block02.addPosition(new Position(3, 3));
        boardOperations.addBlock(board, block02);

        Vertex<Board> root = new Vertex<>(board);
        Graph<Board> graph = new Graph<>();
        graph.addVertex(root);

        Vertex<Board> destination = victim.getResultNode(graph, root);

        List<Vertex<Board>> pathToVictory = victim.getShortestPath(graph, root, destination);

        Vertex<Board> nextVertex = pathToVictory.get(1);
        assertEquals("Double Left - 2", pathToVictory.get(0).getAdjacent().stream()
                .filter(e -> e.getDestination().equals(nextVertex))
                .findFirst().get().getLabel());
    }
}
