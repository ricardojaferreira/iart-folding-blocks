package pt.up.fe.iart.core.search.uninformed;

import org.junit.Before;
import org.junit.Test;
import pt.up.fe.iart.core.BoardOperationsTestImpl;
import pt.up.fe.iart.core.Constants;
import pt.up.fe.iart.core.structures.board.Block;
import pt.up.fe.iart.core.structures.board.Board;
import pt.up.fe.iart.core.structures.board.BoardOperations;
import pt.up.fe.iart.core.structures.board.Cell;
import pt.up.fe.iart.core.structures.board.Operators;
import pt.up.fe.iart.core.structures.board.Position;
import pt.up.fe.iart.core.structures.graph.Edge;
import pt.up.fe.iart.core.structures.graph.Graph;
import pt.up.fe.iart.core.structures.graph.GraphOperations;
import pt.up.fe.iart.core.structures.graph.Vertex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class IterativeDepthFirstTest {

    private BoardOperations boardOperations;

    @Before
    public void setUp() {
        boardOperations = new BoardOperationsTestImpl();
    }

    @Test
    public void getResultNode() {

        IterativeDepthFirst<Board> victim = new IterativeDepthFirst<>(new GraphOperations<Board>() {
            @Override
            public boolean reachObjective(Vertex<Board> vertex) {
                Board board = vertex.getContent();
                return board.getCells().stream().filter(Cell::belongsToBoard).allMatch(Cell::isFilled);
            }

            @Override
            public int expandGraph(Graph<Board> graph, Vertex<Board> vertex) {
                Board board = vertex.getContent();
                List<Vertex<Board>> destinations = new ArrayList<>();
                board.getBlocks().forEach(block -> {
                    for (Operators o : Operators.values()) {
                        Set<Position> symmetric = o.getSymmetricBlockPositions(block, board, boardOperations);
                        if (!symmetric.isEmpty()) {
                            Board newBoard = board.duplicateBoard();
                            boardOperations.updateBlockWithNewPositions(newBoard, block.getBlockId(), symmetric);
                            Vertex<Board> tempDestination = new Vertex<>(newBoard);
                            Vertex<Board> destination = graph.getVertexList().stream()
                                    .filter(v -> v.equals(tempDestination))
                                    .findAny()
                                    .orElse(tempDestination);
                            addVertexToGraph(graph, vertex, destination, o.getMovementName() + " - " + block.getBlockId());
                            destinations.add(destination);
                        }
                    }
                });
                return destinations.size();
            }

            @Override
            public void addVertexToGraph(Graph<Board> graph, Vertex<Board> source, Vertex<Board> destination, String edgeLabel) {
                Edge<Board> edge = new Edge<>(edgeLabel, destination, Constants.TRAVERSAL_COST);
                source.addEdge(edge);
                if (!graph.getVertexList().contains(destination)) {
                    graph.addVertex(destination);
                }
            }

            @Override
            public List<Vertex<Board>> getShortestPath(Graph<Board> graph, Vertex<Board> source, Vertex<Board> destination) {
                return null;
            }
        });

        Board board = new Board(4, 4);
        board.generateSquaredBoard();
        Block block = new Block(board.getNextBlockId(), Arrays.asList(new Position(0, 0)));
        boardOperations.addBlock(board, block);

        Vertex<Board> vertex = new Vertex<>(board);
        Graph<Board> graph = new Graph<>();
        graph.addVertex(vertex);

        Board goalBoard = new Board(4, 4);
        goalBoard.generateSquaredBoard();
        Block goalBlock = new Block(goalBoard.getNextBlockId());
        goalBoard.getCells().stream().map(Cell::getPosition).forEach(goalBlock::addPosition);
        boardOperations.addBlock(goalBoard, goalBlock);
        Vertex<Board> goalVertex = new Vertex<>(goalBoard);

        assertEquals(goalVertex, victim.getResultNode(graph, vertex));
    }
}

