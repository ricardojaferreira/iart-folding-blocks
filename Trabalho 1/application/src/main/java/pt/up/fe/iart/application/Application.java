package pt.up.fe.iart.application;

import pt.up.fe.iart.application.game.Game;
import pt.up.fe.iart.application.game.GameStrategy;
import pt.up.fe.iart.application.game.PlayingStrategy;
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
        if (game.getPlayingStrategy() == PlayingStrategy.AI) {
            printPathMessage(game);
        }
        System.out.println(goodbyeMessage());
    }
}
