package pt.up.fe.iart.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.up.fe.iart.application.levels.LevelWarmUp;
import pt.up.fe.iart.application.searchimpl.AstarImpl;
import pt.up.fe.iart.application.searchimpl.BoardOperationsImpl;
import pt.up.fe.iart.application.searchimpl.GraphOperationsImpl;
import pt.up.fe.iart.application.searchimpl.NoRulesBoardOperationsImpl;
import pt.up.fe.iart.core.search.informed.Astar;
import pt.up.fe.iart.core.structures.board.Board;
import pt.up.fe.iart.core.structures.graph.Graph;
import pt.up.fe.iart.core.structures.graph.Vertex;

import java.util.List;

public class Application {

    public static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        //Welcome Messages
        //Choose AI or Human
        //Choose Level
        LevelWarmUp level = new LevelWarmUp();
        Vertex<Board> root = new Vertex<>(level.startLevel());
        Graph<Board> graph = new Graph<>();
        graph.addVertex(root);

        System.out.println(root.getContent().toString());

        //Choose Traversal Strategy
//        BreadthFirst<Board> breadthFirst = new BreadthFirst<>(new GraphOperationsImpl(new BoardOperationsImpl()));
//        DepthFirst<Board> depthFirst = new DepthFirst<>(new GraphOperationsImpl(new BoardOperationsImpl()));
//        IterativeDepthFirst<Board> iterativeDepthFirst = new IterativeDepthFirst<>(new GraphOperationsImpl(new BoardOperationsImpl()));
//        Astar<Board> filledPositionsAstar = new AstarImpl(new GraphOperationsImpl(new BoardOperationsImpl()),
//                new GraphOperationsImpl(new FilledPositionsBoardOperationsImpl()));
//        Astar<Board> outsideGameAreaAstar = new AstarImpl(new GraphOperationsImpl(new BoardOperationsImpl()),
//                new GraphOperationsImpl(new OutsideGameAreaBoardOperationsImpl()));
        Astar<Board> noRulesGameAreaAstar = new AstarImpl(new GraphOperationsImpl(new BoardOperationsImpl()),
                new GraphOperationsImpl(new NoRulesBoardOperationsImpl()));

        long startTime = System.nanoTime();

//        Vertex<Board> destination = breadthFirst.getResultNode(graph, root);
//        Vertex<Board> destination = depthFirst.getResultNode(graph, root);
//        Vertex<Board> destination =iterativeDepthFirst.getResultNode(graph, root);
//        Vertex<Board> destination = filledPositionsAstar.getResultNode(graph, root);
//        Vertex<Board> destination = outsideGameAreaAstar.getResultNode(graph, root);
        Vertex<Board> destination = noRulesGameAreaAstar.getResultNode(graph, root);


        //Print list of movements
        List<Vertex<Board>> pathToVictory = noRulesGameAreaAstar.getShortestPath(graph, root, destination);

        for (int i = 0; i < pathToVictory.size(); i++) {
            if (i < pathToVictory.size() - 1) {
                Vertex<Board> nextVertex = pathToVictory.get(i + 1);
                LOGGER.info(
                        pathToVictory.get(i).getAdjacent().stream()
                                .filter(e -> e.getDestination().equals(nextVertex)).findFirst().get().getLabel()
                );
            } else {
                LOGGER.info(
                        pathToVictory.get(i).getAdjacent().stream()
                                .filter(e -> e.getDestination().equals(destination)).findFirst().get().getLabel());
            }
        }

        long endTime = System.nanoTime();
        LOGGER.info("Elapsed Time: " + ((endTime - startTime) / 1000));

    }
}
