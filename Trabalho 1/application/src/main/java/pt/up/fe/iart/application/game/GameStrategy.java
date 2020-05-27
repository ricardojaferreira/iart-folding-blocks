package pt.up.fe.iart.application.game;

import pt.up.fe.iart.application.game.levels.Level;
import pt.up.fe.iart.application.game.levels.LevelEasy;
import pt.up.fe.iart.application.game.levels.LevelExpert;
import pt.up.fe.iart.application.game.levels.LevelHard;
import pt.up.fe.iart.application.game.levels.LevelMedium;
import pt.up.fe.iart.application.game.levels.LevelWarmUp;
import pt.up.fe.iart.application.searchimpl.AstarImpl;
import pt.up.fe.iart.application.searchimpl.MostFilledGreedyImpl;
import pt.up.fe.iart.application.searchimpl.astarheuristic.FilledPositionsBoardOperationsImpl;
import pt.up.fe.iart.application.searchimpl.astarheuristic.NoRulesBoardOperationsImpl;
import pt.up.fe.iart.application.searchimpl.astarheuristic.OutsideGameAreaBoardOperationsImpl;
import pt.up.fe.iart.application.structures.BoardOperationsImpl;
import pt.up.fe.iart.application.structures.GraphOperationsImpl;
import pt.up.fe.iart.core.Constants;
import pt.up.fe.iart.core.search.uninformed.BreadthFirst;
import pt.up.fe.iart.core.search.uninformed.DepthFirst;
import pt.up.fe.iart.core.search.uninformed.IterativeDepthFirst;
import pt.up.fe.iart.core.search.uninformed.UniformCost;
import pt.up.fe.iart.core.structures.board.BoardOperations;

import java.util.Scanner;

public class GameStrategy {

    private static final String LINE_SEPARATOR = "=======================================";

    private Game.GameBuilder gameBuilder;
    private Scanner scanner;

    /**
     *
     */
    public GameStrategy() {
        gameBuilder = Game.builder();
        scanner = new Scanner(System.in);
    }

    /**
     *
     * @return
     */
    public Game selectStrategy() {
        selectLevel();
        selectAI();
        selectPlayingStrategy();
        return changeSelection();
    }

    private void selectLevel() {
        StringBuilder selectLevelMessage = new StringBuilder()
                .append("Select the level difficulty").append(Constants.NEW_LINE)
                .append(LINE_SEPARATOR).append(Constants.NEW_LINE)
                .append("1 - Warm Up").append(Constants.NEW_LINE)
                .append("2 - Easy").append(Constants.NEW_LINE)
                .append("3 - Medium").append(Constants.NEW_LINE)
                .append("4 - Hard").append(Constants.NEW_LINE)
                .append("5 - Expert").append(Constants.NEW_LINE);

        System.out.println(selectLevelMessage.toString());
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                Level warmUp = new LevelWarmUp();
                warmUp.bootstrapLevel();
                gameBuilder.level(warmUp);
                break;
            case "2":
                Level easy = new LevelEasy();
                easy.bootstrapLevel();
                gameBuilder.level(easy);
                break;
            case "3":
                Level medium = new LevelMedium();
                medium.bootstrapLevel();
                gameBuilder.level(medium);
                break;
            case "4":
                Level hard = new LevelHard();
                hard.bootstrapLevel();
                gameBuilder.level(hard);
                break;
            case "5":
                Level expert = new LevelExpert();
                expert.bootstrapLevel();
                gameBuilder.level(expert);
                break;
            default:
                System.out.println("Invalid selection");
                selectLevel();
        }
    }

    private void selectAI() {
        StringBuilder selectSearchStrategyMessage = new StringBuilder()
                .append("Select the search strategy").append(Constants.NEW_LINE)
                .append(LINE_SEPARATOR).append(Constants.NEW_LINE)
                .append("1 - Breadth First").append(Constants.NEW_LINE)
                .append("2 - Depth First").append(Constants.NEW_LINE)
                .append("3 - Iterative Depth First").append(Constants.NEW_LINE)
                .append("4 - Uniform Cost").append(Constants.NEW_LINE)
                .append("5 - Greedy").append(Constants.NEW_LINE)
                .append("6 - A*").append(Constants.NEW_LINE);

        System.out.println(selectSearchStrategyMessage.toString());
        String choiceSearch = scanner.nextLine();
        switch (choiceSearch) {
            case "1":
                gameBuilder.traversalStrategy(new BreadthFirst<>(new GraphOperationsImpl(new BoardOperationsImpl())));
                break;
            case "2":
                gameBuilder.traversalStrategy(new DepthFirst<>(new GraphOperationsImpl(new BoardOperationsImpl())));
                break;
            case "3":
                gameBuilder.traversalStrategy(new IterativeDepthFirst<>(new GraphOperationsImpl(new BoardOperationsImpl())));
                break;
            case "4":
                gameBuilder.traversalStrategy(new UniformCost<>(new GraphOperationsImpl(new BoardOperationsImpl())));
                break;
            case "5":
                gameBuilder.traversalStrategy(new MostFilledGreedyImpl(new GraphOperationsImpl(new BoardOperationsImpl())));
                break;
            case "6":
                BoardOperations heuristic = selectEuristic();
                gameBuilder.traversalStrategy(
                        new AstarImpl(new GraphOperationsImpl(new BoardOperationsImpl()), new GraphOperationsImpl(heuristic)));
                break;
            default:
                System.out.println("Invalid selection");
                selectAI();
        }
    }

    private BoardOperations selectEuristic() {
        StringBuilder selectHeuristicStrategy = new StringBuilder()
                .append("Select heuristic to use with A*").append(Constants.NEW_LINE)
                .append(LINE_SEPARATOR).append(Constants.NEW_LINE)
                .append("1 - Fill occupied positions").append(Constants.NEW_LINE)
                .append("2 - Fill outside game area").append(Constants.NEW_LINE)
                .append("3 - Fill without rules").append(Constants.NEW_LINE);

        System.out.println(selectHeuristicStrategy.toString());
        String choiceHeuristic = scanner.nextLine();
        switch (choiceHeuristic) {
            case "1":
                return new FilledPositionsBoardOperationsImpl();
            case "2":
                return new OutsideGameAreaBoardOperationsImpl();
            case "3":
                return new NoRulesBoardOperationsImpl();
            default:
                System.out.println("Invalid Selection");
                return selectEuristic();
        }
    }

    private void selectPlayingStrategy() {
        StringBuilder selectPlayingStrategy = new StringBuilder()
                .append("Select the Playing Mode").append(Constants.NEW_LINE)
                .append(LINE_SEPARATOR).append(Constants.NEW_LINE)
                .append("1 - Human").append(Constants.NEW_LINE)
                .append("2 - AI").append(Constants.NEW_LINE);
        System.out.println(selectPlayingStrategy.toString());
        String choicePlayingStrategy = scanner.nextLine();
        switch (choicePlayingStrategy) {
            case "1":
                gameBuilder.playingStrategy(PlayingStrategy.HUMAN);
                break;
            case "2":
                gameBuilder.playingStrategy(PlayingStrategy.AI);
                break;
            default:
                System.out.println("Invalid Selection");
                selectPlayingStrategy();
        }
    }

    private Game changeSelection() {
        StringBuilder changeSelectionMessage = new StringBuilder()
                .append("/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/").append(Constants.NEW_LINE)
                .append("Your selection is").append(Constants.NEW_LINE)
                .append(gameBuilder.printLevel()).append(Constants.NEW_LINE)
                .append("Search Strategy: ").append(gameBuilder.printTraversalStrategy()).append(Constants.NEW_LINE)
                .append("Playing Mode: ").append(gameBuilder.printPlayingStrategy()).append(Constants.NEW_LINE)
                .append(LINE_SEPARATOR).append(Constants.NEW_LINE)
                .append("Do you wish to change something: ").append(Constants.NEW_LINE)
                .append("1 - Level").append(Constants.NEW_LINE)
                .append("2 - Search Strategy").append(Constants.NEW_LINE)
                .append("3 - Playing Mode").append(Constants.NEW_LINE)
                .append("4 - Everything").append(Constants.NEW_LINE)
                .append("5 - Let's Start the Game").append(Constants.NEW_LINE);
        System.out.println(changeSelectionMessage.toString());
        String choiceChangeSelection = scanner.nextLine();
        switch (choiceChangeSelection) {
            case "1":
                selectLevel();
                break;
            case "2":
                selectAI();
                break;
            case "3":
                selectPlayingStrategy();
                break;
            case "4":
                selectStrategy();
                break;
            case "5":
                break;
            default:
                System.out.println("Invalid Selection");
                changeSelection();
        }
        return gameBuilder.build();
    }
}
