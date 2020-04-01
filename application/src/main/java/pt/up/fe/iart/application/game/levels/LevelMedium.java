package pt.up.fe.iart.application.game.levels;

import pt.up.fe.iart.application.structures.BoardOperationsImpl;
import pt.up.fe.iart.core.structures.board.Block;
import pt.up.fe.iart.core.structures.board.Board;
import pt.up.fe.iart.core.structures.board.BoardOperations;
import pt.up.fe.iart.core.structures.board.Position;

import java.util.Arrays;

public class LevelMedium implements Level {

    private Board board;
    private BoardOperations boardOperations;

    public LevelMedium() {
        board = new Board(5, 9);
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
                Arrays.asList(new Position(0, 0), new Position(0, 1)));
        boardOperations.addBlock(board, block01);
        Block block02 = new Block(
                board.getNextBlockId(),
                Arrays.asList(new Position(1, 0)));
        boardOperations.addBlock(board, block02);
        Block block03 = new Block(
                board.getNextBlockId(),
                Arrays.asList(new Position(1, 1)));
        boardOperations.addBlock(board, block03);
        board.getCellByPosition(new Position(0, 8)).setBelongsToBoard(false);
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
