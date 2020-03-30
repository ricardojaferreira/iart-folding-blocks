package pt.up.fe.iart.application.levels;

import pt.up.fe.iart.application.structures.BoardOperationsImpl;
import pt.up.fe.iart.core.structures.board.Block;
import pt.up.fe.iart.core.structures.board.Board;
import pt.up.fe.iart.core.structures.board.BoardOperations;
import pt.up.fe.iart.core.structures.board.Operators;
import pt.up.fe.iart.core.structures.board.Position;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

public class LevelWarmUp {

    private Board board;
    private BoardOperations boardOperations;

    public LevelWarmUp() {
        board = new Board(4, 2);
        boardOperations = new BoardOperationsImpl();
    }

    /**
     *
     * @return
     */
    public Board startLevel() {
        board.generateSquaredBoard();
        Block block01 = new Block(
                board.getNextBlockId(),
                Arrays.asList(new Position(0, 0), new Position(1, 0)));
        boardOperations.addBlock(board, block01);
        Block block02 = new Block(
                board.getNextBlockId(),
                Arrays.asList(new Position(2, 1), new Position(3, 1)));
        boardOperations.addBlock(board, block02);
        return board;
    }

    /**
     *
     * @param blockId
     * @param operator
     * @return
     */
    public Optional<Board> moveBlock(int blockId, Operators operator) {
        Block blockToMove = board.getBlockById(blockId);
        Set<Position> listOfSymmetricPositions = operator.getSymmetricBlockPositions(blockToMove, board, boardOperations);
        if (listOfSymmetricPositions.isEmpty()) {
            return Optional.empty();
        }

        blockToMove.addAllPositions(listOfSymmetricPositions);
        boardOperations.addBlock(board, blockToMove);
        return Optional.of(board);
    }
}
