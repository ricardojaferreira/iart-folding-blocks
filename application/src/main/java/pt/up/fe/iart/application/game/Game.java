package pt.up.fe.iart.application.game;

import pt.up.fe.iart.application.AppConstants;
import pt.up.fe.iart.application.game.levels.Level;
import pt.up.fe.iart.core.search.TraversalStrategy;
import pt.up.fe.iart.core.structures.board.Board;
import pt.up.fe.iart.core.structures.graph.Graph;
import pt.up.fe.iart.core.structures.graph.Vertex;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Game {
    private Level level;
    private TraversalStrategy<Board> traversalStrategy;
    private PlayingStrategy playingStrategy;
    private Graph<Board> graph;
    private List<Vertex<Board>> pathToVictory;
    private Statistics statistics;

    public Game(Level level, TraversalStrategy<Board> traversalStrategy, PlayingStrategy playingStrategy, Graph<Board> graph) {
        this.level = level;
        this.traversalStrategy = traversalStrategy;
        this.playingStrategy = playingStrategy;
        this.graph = graph;
        this.pathToVictory = new ArrayList<>();
        statistics = new Statistics();
    }

    /**
     *
     * @return
     */
    public PlayingStrategy getPlayingStrategy() {
        return playingStrategy;
    }

    /**
     *
     * @param playingStrategy
     */
    public void setPlayingStrategy(PlayingStrategy playingStrategy) {
        this.playingStrategy = playingStrategy;
    }

    /**
     *
     * @return
     */
    public Statistics startGame() {
        if (playingStrategy.equals(PlayingStrategy.AI)) {
            playAI();
        } else {
            playHuman();
        }

        return statistics;
    }

    /**
     *
     * @return
     */
    public static GameBuilder builder() {
        return new GameBuilder();
    }

    /**
     *
     */
    public void printPathToVictory() {
        List<String> movements = new ArrayList<>();
        List<Board> boardsList = new ArrayList<>();
        for (int i = 0; i < pathToVictory.size() - 1; i++) {
            boardsList.add(pathToVictory.get(i).getContent());
            Vertex<Board> nextVertex = pathToVictory.get(i + 1);
            movements.add(
                    pathToVictory.get(i).getAdjacent().stream()
                            .filter(e -> e.getDestination().equals(nextVertex)).findFirst().get().getLabel());
        }
        boardsList.add(pathToVictory.get(pathToVictory.size() - 1).getContent());

        System.out.println(AppConstants.SCREEN_SEPARATOR);

        boardsList.forEach(b -> {
            System.out.println(b.toString());
            System.out.println("---");
        });

        System.out.println("Movements to victory");
        movements.forEach(System.out::println);
    }

    /**
     *
     */
    private void playAI() {
        long elapsedTime = 0;
        Optional<Vertex<Board>> root = graph.getVertexList().stream().filter(v -> v.getContent().equals(level.getBoard())).findFirst();
        if (root.isPresent()) {
            long startTime = System.nanoTime();
            Vertex<Board> destination = traversalStrategy.getResultNode(graph, root.get());
            long endTime = System.nanoTime();
            elapsedTime = endTime - startTime;
            pathToVictory = traversalStrategy.getShortestPath(graph, root.get(), destination);
            setAIStatistics(elapsedTime, graph.getVertexList().size(), pathToVictory.size());
        }
    }

    /**
     *
     * @param elapsedTime
     * @param graphNodes
     * @param nrNodesToVictory
     */
    private void setAIStatistics(long elapsedTime, int graphNodes, int nrNodesToVictory) {
        statistics.setElapsedTime(elapsedTime);
        statistics.setGraphNrOfNodes(graphNodes);
        statistics.setNrNodesToVictory(nrNodesToVictory);
    }

    /**
     *
     */
    private void playHuman() {
        Vertex<Board> root = graph.getVertexList().get(0);
        showBoardAndPossibleMoves(root);

    }

    /**
     *
     * @param vertex
     */
    private void showBoardAndPossibleMoves(Vertex<Board> vertex) {
        long startTime = System.nanoTime();
        Scanner scanner = new Scanner(System.in);
        System.out.println(vertex.getContent().toString());
        if (this.traversalStrategy.getGraphOperations().reachObjective(vertex)) {
            System.out.println("Congratulations");
            long endTime = System.nanoTime();
            statistics.setElapsedTime(endTime - startTime);
            return;
        }

        this.traversalStrategy.getGraphOperations().expandGraph(graph, vertex);
        StringBuilder stringChoices = new StringBuilder().append(AppConstants.NEW_LINE)
                .append("Choose one Movement:").append(AppConstants.NEW_LINE);
        for (int i = 0; i < vertex.getAdjacent().size(); i++) {
            stringChoices.append(i + 1).append(" - ").append(vertex.getAdjacent().get(i).getLabel()).append(AppConstants.NEW_LINE);
        }
        stringChoices.append(vertex.getAdjacent().size()).append(" - ").append("Tip from AI");
        System.out.println(stringChoices.toString());
        int choice = Integer.valueOf(scanner.nextLine());
        if (choice < 1 || choice > vertex.getAdjacent().size()) {
            System.out.println("INVALID CHOICE");
        } else {
            if (choice == vertex.getAdjacent().size()) {
                statistics.incrementNumberOfTips();
                Vertex<Board> chosenVertex = giveTip(vertex);
                if (chosenVertex != null) {
                    vertex = chosenVertex;
                }
            } else {
                vertex = vertex.getAdjacent().get(choice).getDestination();
            }
        }
        showBoardAndPossibleMoves(vertex);
    }

    /**
     *
     * @param root
     * @return
     */
    private Vertex<Board> giveTip(Vertex<Board> root) {
        Vertex<Board> destination = this.traversalStrategy.getResultNode(graph, root);
        if (destination != null) {
            List<Vertex<Board>> path = this.traversalStrategy.getShortestPath(graph, root, destination);
            return path.get(0);
        }
        return null;
    }

    /**
     * Class used to create a Game based on the user choices
     */
    public static class GameBuilder {
        private Optional<Level> level;
        private Optional<TraversalStrategy<Board>> traversalStrategy;
        private Optional<PlayingStrategy> playingStrategy;
        private Graph<Board> graph;

        /**
         *
         * @param level
         * @return
         */
        public GameBuilder level(Level level) {
            this.level = Optional.of(level);
            return this;
        }

        /**
         *
         * @param traversalStrategy
         * @return
         */
        public GameBuilder traversalStrategy(TraversalStrategy<Board> traversalStrategy) {
            this.traversalStrategy = Optional.of(traversalStrategy);
            return this;
        }

        /**
         *
         * @param playingStrategy
         * @return
         */
        public GameBuilder playingStrategy(PlayingStrategy playingStrategy) {
            this.playingStrategy = Optional.of(playingStrategy);
            return this;
        }

        /**
         *
         * @return
         */
        public Game build() {
            if (level.isPresent() && traversalStrategy.isPresent() && playingStrategy.isPresent()) {
                graph = new Graph<>();
                graph.addVertex(new Vertex<>(level.get().getBoard()));
                return new Game(level.get(), traversalStrategy.get(), playingStrategy.get(), graph);
            }
            return null;
        }

        /**
         *
         * @return
         */
        public String printLevel() {
            if (level.isPresent()) {
                return level.get().toString();
            }
            return "Level not initialized";
        }

        /**
         *
         * @return
         */
        public String printTraversalStrategy() {
            if (traversalStrategy.isPresent()) {
                return traversalStrategy.get().toString();
            }
            return "Search strategy not defined";
        }

        /**
         *
         * @return
         */
        public String printPlayingStrategy() {
            if (playingStrategy.isPresent()) {
                return playingStrategy.get().toString();
            }
            return "Playing strategy not defined";
        }
    }

}
