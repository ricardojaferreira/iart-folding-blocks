package pt.up.fe.iart.application;

import pt.up.fe.iart.application.game.Game;
import pt.up.fe.iart.application.game.GameStrategy;
import pt.up.fe.iart.application.game.Statistics;

import java.util.Scanner;

public class Application {

    public static String greetingMessage() {
        return new StringBuilder()
                .append("Welcome to Folding Blocks With AI")
                .append(AppConstants.NEW_LINE)
                .append(AppConstants.SCREEN_SEPARATOR)
                .append(AppConstants.NEW_LINE)
                .toString();
    }

    public static void printPathMessage(Game game) {
        Scanner scanner = new Scanner(System.in);
        String message = new StringBuilder()
                .append(AppConstants.SCREEN_SEPARATOR).append(AppConstants.NEW_LINE)
                .append("Do you wish to print the path to victory").append(AppConstants.NEW_LINE)
                .append("1 - YES").append(AppConstants.NEW_LINE)
                .append("2 - NO").append(AppConstants.NEW_LINE)
                .toString();

        System.out.println(message);
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                game.printPathToVictory();
                break;
            case "2":
                break;
            default:
                printPathMessage(game);
        }
    }

    public static String goodbyeMessage() {
        return new StringBuilder()
                .append(AppConstants.NEW_LINE)
                .append(AppConstants.END_SEPARATOR)
                .append(AppConstants.NEW_LINE)
                .append("Thank you for playing Folding Blocks")
                .append(AppConstants.NEW_LINE)
                .append(AppConstants.SCREEN_SEPARATOR)
                .append(AppConstants.NEW_LINE)
                .toString();
    }

    public static void main(String[] args) {
        System.out.println(greetingMessage());
        GameStrategy gameStrategy = new GameStrategy();
        Game game = gameStrategy.selectStrategy();
        Statistics statistics = game.startGame();
        statistics.printStatistics(game.getPlayingStrategy());
        printPathMessage(game);
        System.out.println(goodbyeMessage());

//
//
//
//
//
//
//        LevelWarmUp level = new LevelWarmUp();
//        Vertex<Board> root = new Vertex<>(level.bootstrapLevel());
//        Graph<Board> graph = new Graph<>();
//        graph.addVertex(root);
//
////        System.out.println(root.getContent().toString());
//
//        //Choose Traversal Strategy
////        BreadthFirst<Board> breadthFirst = new BreadthFirst<>(new GraphOperationsImpl(new BoardOperationsImpl()));
////        DepthFirst<Board> depthFirst = new DepthFirst<>(new GraphOperationsImpl(new BoardOperationsImpl()));
////        IterativeDepthFirst<Board> iterativeDepthFirst = new IterativeDepthFirst<>(new GraphOperationsImpl(new BoardOperationsImpl()));
//        UniformCost<Board> uniformCost = new UniformCost<>(new GraphOperationsImpl(new BoardOperationsImpl()));
////        Astar<Board> filledPositionsAstar = new AstarImpl(new GraphOperationsImpl(new BoardOperationsImpl()),
////                new GraphOperationsImpl(new FilledPositionsBoardOperationsImpl()));
////        Astar<Board> outsideGameAreaAstar = new AstarImpl(new GraphOperationsImpl(new BoardOperationsImpl()),
////                new GraphOperationsImpl(new OutsideGameAreaBoardOperationsImpl()));
////        Astar<Board> noRulesGameAreaAstar = new AstarImpl(new GraphOperationsImpl(new BoardOperationsImpl()),
////                new GraphOperationsImpl(new NoRulesBoardOperationsImpl()));
//
//        long startTime = System.nanoTime();
//
////        Vertex<Board> destination = breadthFirst.getResultNode(graph, root);
////        Vertex<Board> destination = depthFirst.getResultNode(graph, root);
////        Vertex<Board> destination =iterativeDepthFirst.getResultNode(graph, root);
////        Vertex<Board> destination = filledPositionsAstar.getResultNode(graph, root);
////        Vertex<Board> destination = outsideGameAreaAstar.getResultNode(graph, root);
//        Vertex<Board> destination = uniformCost.getResultNode(graph, root);
//
//
//        //Print list of movements
//        List<Vertex<Board>> pathToVictory = uniformCost.getShortestPath(graph, root, destination);
//
//        for (int i = 0; i < pathToVictory.size(); i++) {
//            if (i < pathToVictory.size() - 1) {
//                Vertex<Board> nextVertex = pathToVictory.get(i + 1);
//                LOGGER.info(
//                        pathToVictory.get(i).getAdjacent().stream()
//                                .filter(e -> e.getDestination().equals(nextVertex)).findFirst().get().getLabel()
//                );
//            } else {
//                LOGGER.info(
//                        pathToVictory.get(i).getAdjacent().stream()
//                                .filter(e -> e.getDestination().equals(destination)).findFirst().get().getLabel());
//            }
//        }
//
//        long endTime = System.nanoTime();
//        LOGGER.info("Elapsed Time: " + ((endTime - startTime) / 1000));
//
    }
}
