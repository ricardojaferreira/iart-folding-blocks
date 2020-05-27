package pt.up.fe.iart.application.game.levels;

import pt.up.fe.iart.application.structures.BoardOperationsImpl;
import pt.up.fe.iart.core.structures.board.Block;
import pt.up.fe.iart.core.structures.board.Board;
import pt.up.fe.iart.core.structures.board.BoardOperations;
import pt.up.fe.iart.core.structures.board.Position;

import java.util.Arrays;

public class LevelHard implements Level {

    private Board board;
    private BoardOperations boardOperations;

    public LevelHard() {
        board = new Board(6, 8);
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
                        new Position(2, 0),
                        new Position(5, 3)));
        boardOperations.addBlock(board, block01);
        Block block02 = new Block(
                board.getNextBlockId(),
                Arrays.asList(new Position(0, 6),
                        new Position(0, 7),
                        new Position(1, 6)));
        boardOperations.addBlock(board, block02);
        Block block03 = new Block(
                board.getNextBlockId(),
                Arrays.asList(new Position(2, 6),
                        new Position(3, 6),
                        new Position(3, 7)));
        boardOperations.addBlock(board, block03);
        Block block04 = new Block(
                board.getNextBlockId(),
                Arrays.asList(new Position(4, 6),
                        new Position(5, 6),
                        new Position(4, 7)));
        boardOperations.addBlock(board, block04);
        board.getCellByPosition(new Position(5, 0)).setBelongsToBoard(false);
        board.getCellByPosition(new Position(1, 3)).setBelongsToBoard(false);
        board.getCellByPosition(new Position(1, 4)).setBelongsToBoard(false);
        board.getCellByPosition(new Position(2, 3)).setBelongsToBoard(false);
        board.getCellByPosition(new Position(2, 4)).setBelongsToBoard(false);
        board.getCellByPosition(new Position(5, 7)).setBelongsToBoard(false);
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
