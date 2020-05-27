package pt.up.fe.iart.application.game.levels;

import pt.up.fe.iart.application.structures.BoardOperationsImpl;
import pt.up.fe.iart.core.structures.board.Block;
import pt.up.fe.iart.core.structures.board.Board;
import pt.up.fe.iart.core.structures.board.BoardOperations;
import pt.up.fe.iart.core.structures.board.Position;

import java.util.Arrays;

public class LevelExpert implements Level {

    private Board board;
    private BoardOperations boardOperations;

    public LevelExpert() {
        board = new Board(6, 7);
        boardOperations = new BoardOperationsImpl();
    }

    /**
     *
     * @return
     */
    @Override
    public Board bootstrapLevel() {
        board.generateSquaredBoard();
        Block block01 = new Block(
                board.getNextBlockId(),
                Arrays.asList(new Position(1, 0),
                        new Position(0, 1)));
        boardOperations.addBlock(board, block01);
        Block block02 = new Block(
                board.getNextBlockId(),
                Arrays.asList(
                        new Position(2, 2)));
        boardOperations.addBlock(board, block02);
        Block block03 = new Block(
                board.getNextBlockId(),
                Arrays.asList(
                        new Position(0, 3)));
        boardOperations.addBlock(board, block03);
        Block block04 = new Block(
                board.getNextBlockId(),
                Arrays.asList(
                        new Position(2, 4),
                        new Position(3, 4)));
        boardOperations.addBlock(board, block04);
        Block block05 = new Block(
                board.getNextBlockId(),
                Arrays.asList(new Position(4, 3),
                        new Position(4, 4),
                        new Position(5, 4),
                        new Position(3, 5),
                        new Position(4, 5),
                        new Position(5, 5)));
        boardOperations.addBlock(board, block05);
        Block block06 = new Block(
                board.getNextBlockId(),
                Arrays.asList(
                        new Position(0, 6),
                        new Position(1, 6)));
        boardOperations.addBlock(board, block06);
        board.getCellByPosition(new Position(0, 0)).setBelongsToBoard(false);
        board.getCellByPosition(new Position(2, 0)).setBelongsToBoard(false);
        board.getCellByPosition(new Position(3, 0)).setBelongsToBoard(false);
        board.getCellByPosition(new Position(4, 0)).setBelongsToBoard(false);
        board.getCellByPosition(new Position(5, 0)).setBelongsToBoard(false);
        board.getCellByPosition(new Position(5, 1)).setBelongsToBoard(false);
        board.getCellByPosition(new Position(5, 2)).setBelongsToBoard(false);
        board.getCellByPosition(new Position(5, 3)).setBelongsToBoard(false);
        board.getCellByPosition(new Position(5, 6)).setBelongsToBoard(false);
        board.getCellByPosition(new Position(4, 6)).setBelongsToBoard(false);
        return board;
    }

    /**
     *
     * @return
     */
    @Override
    public Board getBoard() {
        return this.board;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return board.toString();
    }
}
