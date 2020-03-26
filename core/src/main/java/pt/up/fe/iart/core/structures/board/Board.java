package pt.up.fe.iart.core.structures.board;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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
    public Board duplicateBoard() {
        Board duplicated = new Board(this.width, this.height);
        List<Cell> duplicatedCells = this.getCells().stream().map(Cell::duplicateCell).collect(Collectors.toList());
        List<Block> duplicatedBlocks = this.getBlocks().stream().map(Block::duplicateBlock).collect(Collectors.toList());
        duplicated.addAllBlocks(duplicatedBlocks);
        duplicated.addAllCells(duplicatedCells);
        return duplicated;
    }

    /**
     * This function returns a copy of all cells, so that the list cannot be changed outside.
     * @return
     */
    public List<Cell> getCells() {
        return this.cells;
    }

    /**
     * This function returns a copy of all blocks, so that the list cannot be changed outside.
     * @return List of blocks
     */
    public List<Block> getBlocks() {
        return new ArrayList<>(blocks);
    }

    /**
     *
     * @param cells
     */
    public void addAllCells(List<Cell> cells) {
        this.cells.addAll(cells);
    }

    /**
     *
     * @param blocks
     */
    public void addAllBlocks(List<Block> blocks) {
        this.blocks.addAll(blocks);
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
     * @return
     */
    public int getNextBlockId() {
        return this.blocks.size() + 1;
    }

    /**
     *
     * @param blockId
     * @return
     */
    public Block getBlockById(int blockId) {
        return this.blocks.get(blockId - 1);
    }

    /**
     *
     * @param blockId
     * @param positions
     * @return
     */
    public boolean updateBlockWithNewPositions(int blockId, Set<Position> positions) {
        Block block = getBlockById(blockId);
        block.addAllPositions(positions);
        return addBlock(block);
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

    /**
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Board board = (Board) o;
        boolean equalCells = cells.size() == board.getCells().size()
                && cells.stream().allMatch(cell -> board.getCells().stream().anyMatch(c -> c.equals(cell)));
        boolean equalBlocks = blocks.size() == board.getBlocks().size()
                && blocks.stream().allMatch(block -> board.getBlocks().stream().anyMatch(b -> b.equals(block)));

        return width == board.width
                && height == board.height
                && equalCells
                && equalBlocks;
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(width, height, cells, blocks);
    }
}
