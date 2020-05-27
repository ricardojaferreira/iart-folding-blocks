package pt.up.fe.iart.application.searchimpl;

import org.junit.Test;
import pt.up.fe.iart.application.searchimpl.astarheuristic.FilledPositionsBoardOperationsImpl;
import pt.up.fe.iart.application.searchimpl.astarheuristic.NoRulesBoardOperationsImpl;
import pt.up.fe.iart.application.searchimpl.astarheuristic.OutsideGameAreaBoardOperationsImpl;
import pt.up.fe.iart.application.structures.BoardOperationsImpl;
import pt.up.fe.iart.application.structures.GraphOperationsImpl;
import pt.up.fe.iart.core.structures.board.Block;
import pt.up.fe.iart.core.structures.board.Board;
import pt.up.fe.iart.core.structures.board.BoardOperations;
import pt.up.fe.iart.core.structures.board.Position;
import pt.up.fe.iart.core.structures.graph.Graph;
import pt.up.fe.iart.core.structures.graph.GraphOperations;
import pt.up.fe.iart.core.structures.graph.Vertex;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AstarImplTest {
    private AstarImpl victim;
    private BoardOperations boardOperations;
    private GraphOperations<Board> graphOperations;

    @Test
    public void getResultNodeWithFilledPositions() {
        boardOperations = new BoardOperationsImpl();
        graphOperations = new GraphOperationsImpl(boardOperations);
        GraphOperations<Board> heuristicGraphOperations = new GraphOperationsImpl(new FilledPositionsBoardOperationsImpl());
        victim = new AstarImpl(graphOperations, heuristicGraphOperations);

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

        Vertex<Board> goal = victim.getResultNode(graph, root);

        Board expectedBoard = new Board(4, 4);
        expectedBoard.generateSquaredBoard();
        boardOperations.addBlock(expectedBoard,
                new Block(1, Arrays.asList(
                        new Position(0, 0),
                        new Position(0, 1),
                        new Position(0, 2),
                        new Position(0, 3),
                        new Position(1, 0),
                        new Position(1, 1),
                        new Position(1, 2),
                        new Position(1, 3)
                )
                ));
        boardOperations.addBlock(expectedBoard, new Block(2, Arrays.asList(
                new Position(2, 0),
                new Position(2, 1),
                new Position(2, 2),
                new Position(2, 3),
                new Position(3, 0),
                new Position(3, 1),
                new Position(3, 2),
                new Position(3, 3)
                )
                )
        );

        Vertex<Board> expectedVertex = new Vertex<>(expectedBoard);
        assertEquals(expectedVertex.getContent(), goal.getContent());
    }

    @Test
    public void getResultNodeWithOutsideGameArea() {
        boardOperations = new BoardOperationsImpl();
        graphOperations = new GraphOperationsImpl(boardOperations);
        GraphOperations<Board> heuristicGraphOperations = new GraphOperationsImpl(new OutsideGameAreaBoardOperationsImpl());
        victim = new AstarImpl(graphOperations, heuristicGraphOperations);

        Board board = new Board(4, 4);
        board.generateSquaredBoard();
        board.getCells().get(3).setBelongsToBoard(false);
        board.getCells().get(7).setBelongsToBoard(false);
        Block block01 = new Block(board.getNextBlockId());
        block01.addPosition(new Position(0, 0));
        boardOperations.addBlock(board, block01);
        Block block02 = new Block(board.getNextBlockId());
        block02.addPosition(new Position(3, 2));
        block02.addPosition(new Position(3, 3));
        boardOperations.addBlock(board, block02);
        Block block03 = new Block(board.getNextBlockId());
        block03.addPosition(new Position(2, 1));
        boardOperations.addBlock(board, block03);

        Vertex<Board> root = new Vertex<>(board);
        Graph<Board> graph = new Graph<>();
        graph.addVertex(root);

        Vertex<Board> goal = victim.getResultNode(graph, root);

        assertNotNull(goal);
    }

    @Test
    public void getResultNodeWithNoRules() {
        boardOperations = new BoardOperationsImpl();
        graphOperations = new GraphOperationsImpl(boardOperations);
        GraphOperations<Board> heuristicGraphOperations = new GraphOperationsImpl(new NoRulesBoardOperationsImpl());
        victim = new AstarImpl(graphOperations, heuristicGraphOperations);

        Board board = new Board(4, 4);
        board.generateSquaredBoard();
        board.getCells().get(3).setBelongsToBoard(false);
        board.getCells().get(7).setBelongsToBoard(false);
        Block block01 = new Block(board.getNextBlockId());
        block01.addPosition(new Position(0, 0));
        boardOperations.addBlock(board, block01);
        Block block02 = new Block(board.getNextBlockId());
        block02.addPosition(new Position(3, 2));
        block02.addPosition(new Position(3, 3));
        boardOperations.addBlock(board, block02);
        Block block03 = new Block(board.getNextBlockId());
        block03.addPosition(new Position(2, 1));
        boardOperations.addBlock(board, block03);

        Vertex<Board> root = new Vertex<>(board);
        Graph<Board> graph = new Graph<>();
        graph.addVertex(root);

        Vertex<Board> goal = victim.getResultNode(graph, root);

        assertNotNull(goal);
    }

    @Test
    public void getShortestPath() {
        boardOperations = new BoardOperationsImpl();
        graphOperations = new GraphOperationsImpl(boardOperations);
        GraphOperations<Board> heuristicGraphOperations = new GraphOperationsImpl(new FilledPositionsBoardOperationsImpl());
        victim = new AstarImpl(graphOperations, heuristicGraphOperations);

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

        Vertex<Board> goal = victim.getResultNode(graph, root);
        List<Vertex<Board>> path = victim.getShortestPath(graph, root, goal);

        assertEquals(6, path.size());
    }

    @Test
    public void getHeuristicRate() {
        boardOperations = new BoardOperationsImpl();
        graphOperations = new GraphOperationsImpl(boardOperations);
        GraphOperations<Board> heuristicGraphOperations = new GraphOperationsImpl(new FilledPositionsBoardOperationsImpl());
        victim = new AstarImpl(graphOperations, heuristicGraphOperations);

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

        assertEquals(4, victim.getHeuristicRate(root));
        graphOperations.expandGraph(graph, root);
        assertEquals(4, victim.getHeuristicRate(root.getAdjacent().get(0).getDestination()));
        assertEquals(3, victim.getHeuristicRate(root.getAdjacent().get(2).getDestination()));
    }
}
