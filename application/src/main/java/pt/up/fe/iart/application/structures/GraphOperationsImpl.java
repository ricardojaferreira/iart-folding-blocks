package pt.up.fe.iart.application.structures;

import pt.up.fe.iart.core.Constants;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class GraphOperationsImpl implements GraphOperations<Board> {

    private BoardOperations boardOperations;

    public GraphOperationsImpl(BoardOperations boardOperations) {
        this.boardOperations = boardOperations;
    }

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

    /**
     *
     * @param graph
     * @param source
     * @param destination
     */
    @Override
    public void addVertexToGraph(Graph<Board> graph, Vertex<Board> source, Vertex<Board> destination, String edgeLabel) {
        Edge<Board> edge = new Edge<>(edgeLabel, destination, Constants.TRAVERSAL_COST);
        source.addEdge(edge);
        if (!graph.getVertexList().contains(destination)) {
            graph.addVertex(destination);
        }
    }

    /**
     *
     * @param graph
     * @param source
     * @param destination
     * @return
     */
    @Override
    public List<Vertex<Board>> getShortestPath(Graph<Board> graph, Vertex<Board> source, Vertex<Board> destination) {
        graph.getVertexList().forEach(vertex -> {
            vertex.setDistanceFromRoot(Integer.MAX_VALUE);
            vertex.setPathFromRoot(new ArrayList<>());
        });

        source.setDistanceFromRoot(0);
        Queue<Vertex<Board>> queue = new LinkedList<>();
        queue.add(source);
        while (!queue.isEmpty()) {
            Vertex<Board> vertex = queue.poll();
            for (Edge<Board> e : vertex.getAdjacent()) {
                Vertex<Board> adjacent = e.getDestination();
                if (adjacent.getDistanceFromRoot() > vertex.getDistanceFromRoot() + 1) {
                    queue.add(adjacent);
                    adjacent.setDistanceFromRoot(vertex.getDistanceFromRoot() + 1);
                    adjacent.getPathFromRoot().addAll(vertex.getPathFromRoot());
                    adjacent.getPathFromRoot().add(vertex);
                    if (adjacent.equals(destination)) {
                        return destination.getPathFromRoot();
                    }
                }
            }
        }

        return destination.getPathFromRoot();
    }
}
