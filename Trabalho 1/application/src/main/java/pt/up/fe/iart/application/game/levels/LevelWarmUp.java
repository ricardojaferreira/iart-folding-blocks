package pt.up.fe.iart.application.game.levels;

import pt.up.fe.iart.application.structures.BoardOperationsImpl;
import pt.up.fe.iart.core.structures.board.Block;
import pt.up.fe.iart.core.structures.board.Board;
import pt.up.fe.iart.core.structures.board.BoardOperations;
import pt.up.fe.iart.core.structures.board.Position;

import java.util.Arrays;

public class LevelWarmUp implements Level {

    private Board board;
    private BoardOperations boardOperations;

    public LevelWarmUp() {
        board = new Board(4, 4);
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
                Arrays.asList(new Position(0, 3)));
        boardOperations.addBlock(board, block01);
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
