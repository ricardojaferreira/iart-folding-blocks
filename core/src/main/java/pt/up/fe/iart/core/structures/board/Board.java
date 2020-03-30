package pt.up.fe.iart.core.structures.board;

import pt.up.fe.iart.core.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Board {

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
     * @return
     */
    public List<Cell> getCells() {
        return this.cells;
    }

    /**
     * @return List of blocks
     */
    public List<Block> getBlocks() {
        return this.blocks;
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

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        char empty = '0';
        char notArea = '&';


        List<Character> board = this.cells.stream().map(cell -> {
            if (cell.belongsToBoard()) {
                return empty;
            } else {
                return notArea;
            }
        }).collect(Collectors.toList());

        this.blocks.forEach(block -> block.getOccupiedPositions().forEach(position -> {
            int index = (this.width - 1) * position.getyCoord() + position.getyCoord() + position.getxCoord();
            board.set(index, Character.forDigit(block.getBlockId(), 10));
        }));

        StringBuilder stringBuilder = new StringBuilder();
        addBoardBorder(stringBuilder);
        for (int i = 1; i <= board.size(); i++) {
            Character cellChar = board.get(i - 1) == '0' ? ' ' : board.get(i - 1);
            if (i % (width) != 0) {
                stringBuilder.append('|').append(' ').append(cellChar).append(' ');
            } else {
                stringBuilder.append('|').append(' ').append(cellChar).append(' ').append('|');
                stringBuilder.append(Constants.NEW_LINE);
                addBoardBorder(stringBuilder);
            }
        }

        return stringBuilder.toString();
    }

    private void addBoardBorder(StringBuilder stringBuilder) {
        String topBottomBorder = "+---";
        for (int w = 0; w < width; w++) {
            stringBuilder.append(topBottomBorder);
        }
        stringBuilder.append('+').append(Constants.NEW_LINE);
    }
}
