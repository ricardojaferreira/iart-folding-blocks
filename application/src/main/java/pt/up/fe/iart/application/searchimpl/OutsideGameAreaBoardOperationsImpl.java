package pt.up.fe.iart.application.searchimpl;

import pt.up.fe.iart.core.structures.board.Block;
import pt.up.fe.iart.core.structures.board.Board;
import pt.up.fe.iart.core.structures.board.BoardOperations;
import pt.up.fe.iart.core.structures.board.Cell;
import pt.up.fe.iart.core.structures.board.Position;

import java.util.Set;
import java.util.stream.Collectors;

public class OutsideGameAreaBoardOperationsImpl implements BoardOperations {

    /**
     *
     * @param positions
     * @param board
     * @return
     */
    @Override
    public boolean checkIfPositionsAreAvailable(Set<Position> positions, Board board) {
        return board.getCells().stream()
                .filter(Cell::isEmpty)
                .map(Cell::getPosition)
                .collect(Collectors.toList()).containsAll(positions);
    }

    /**
     *
     * @param board
     * @param blockId
     * @param positions
     * @return
     */
    @Override
    public boolean updateBlockWithNewPositions(Board board, int blockId, Set<Position> positions) {
        Block block = board.getBlockById(blockId);
        block.addAllPositions(positions);
        return addBlock(board, block);
    }

    /**
     *
     * @param board
     * @param block
     * @return
     */
    @Override
    public boolean addBlock(Board board, Block block) {
        return validateOccupiedPositions(board, block) && addBlockAfterValidation(board, block);
    }
}
