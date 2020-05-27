package pt.up.fe.iart.core.structures.board;

import java.util.Collection;
import java.util.Set;

public interface BoardOperations {
    boolean checkIfPositionsAreAvailable(Set<Position> positions, Board board);
    boolean updateBlockWithNewPositions(Board board, int blockId, Set<Position> positions);
    boolean addBlock(Board board, Block block);

    default boolean addBlockAfterValidation(Board board, Block block) {
        if (board.getBlocks().contains(block)) {
            board.getBlocks().set(board.getBlocks().indexOf(block), block);
        } else {
            board.getBlocks().add(block);
        }

        board.getCells().stream()
                .filter(cell -> block.getOccupiedPositions().contains(cell.getPosition())).forEach(cell -> cell.setEmpty(false));
        return true;
    }

    default boolean validateOccupiedPositions(Board board, Block block) {
        return board.getBlocks().stream()
                .filter(boardBlock -> !boardBlock.equals(block))
                .map(Block::getOccupiedPositions)
                .flatMap(Collection::stream)
                .noneMatch(p -> block.getOccupiedPositions().contains(p));
    }

    default boolean validateBoundaries(Board board, Block block) {
        return block.getOccupiedPositions().stream().allMatch(
                blockPos -> board.getCells().stream()
                        .filter(Cell::belongsToBoard).map(Cell::getPosition).anyMatch(cellPos -> cellPos.equals(blockPos))
        );
    }
}
