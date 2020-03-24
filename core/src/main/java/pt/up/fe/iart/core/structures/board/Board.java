package pt.up.fe.iart.core.structures.board;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Board {
    private static final Logger LOGGER = LoggerFactory.getLogger(Board.class);

    private final int width;
    private final int height;
    private List<Cell> cells;
    private List<Block> blocks;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        this.cells = new ArrayList<>();
        this.blocks = new ArrayList<>();
    }

    /**
     *
     * @return
     */
    public List<Cell> getCells() {
        return cells;
    }

    /**
     *
     * @return
     */
    public List<Block> getBlocks() {
        return blocks;
    }

    /**
     *
     */
    public void generateSquaredBoard() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                this.cells.add(new Cell(new Position(j, i), true, true));
            }
        }
    }

    /**
     *
     * @param block
     * @return
     */
    public boolean addBlock(Block block) {

        if (!block.getOccupiedPositions().stream().allMatch(
                blockPos -> cells.stream().filter(Cell::belongsToBoard).map(Cell::getPosition).anyMatch(cellPos -> cellPos.equals(blockPos))
        )) {
            LOGGER.debug("Trying to add a block with positions outside the bounderies!");
            return false;
        }

        if (blocks.stream()
                .filter(boardBlock -> !boardBlock.equals(block))
                .map(Block::getOccupiedPositions)
                .flatMap(Collection::stream)
                .anyMatch(p -> block.getOccupiedPositions().contains(p))) {
            LOGGER.debug("Trying to add a block in an occupied position.");
            return false;
        }

        if (this.blocks.contains(block)) {
            this.blocks.set(this.blocks.indexOf(block), block);
        } else {
            this.blocks.add(block);
        }

        cells.stream().filter(cell -> block.getOccupiedPositions().contains(cell.getPosition())).forEach(cell -> cell.setEmpty(false));
        LOGGER.debug("The block with id {} was added to the board.", block.getBlockId());
        return true;
    }
}
