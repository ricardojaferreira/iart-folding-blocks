package pt.up.fe.iart.application.levels;

import pt.up.fe.iart.core.structures.board.Block;
import pt.up.fe.iart.core.structures.board.Board;
import pt.up.fe.iart.core.structures.board.Cell;
import pt.up.fe.iart.core.structures.board.Operators;
import pt.up.fe.iart.core.structures.board.Position;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

public class LevelWarmUp {

    private Board board;

    public LevelWarmUp() {
        board = new Board(4, 2);

    }

    /**
     *
     * @return
     */
    public Board startLevel() {
        board.generateSquaredBoard();
        Block block = new Block(
                board.getNextBlockId(),
                Arrays.asList(new Position(0, 0), new Position(1, 0)));
        board.addBlock(block);
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
        Set<Position> listOfSymmetricPositions = operator.getSymmetricBlockPositions(blockToMove, board);
        if (listOfSymmetricPositions.isEmpty()) {
            return Optional.empty();
        }

        blockToMove.addAllPositions(listOfSymmetricPositions);
        board.addBlock(blockToMove);
        return Optional.of(board);
    }

    /**
     *
     * @return
     */
    public boolean isWinningState() {
        return board.getCells().stream().filter(Cell::belongsToBoard).allMatch(Cell::isFilled);
    }
}
