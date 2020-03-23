package pt.up.fe.iart.core.structures.board;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


public class BoardTest {
    private Board victim;

    @Test
    public void instantiateBoardClass() {
        victim = new Board(20, 20);
        assertNotNull(victim);
        assertEquals(victim.getCells().size(), 0);
        assertEquals(victim.getBlocks().size(), 0);
    }

    @Test
    public void generateSquaredBoard() {
        victim = new Board(20, 20);
        victim.generateSquaredBoard();

        assertEquals(20 * 20, victim.getCells().size());
        assertTrue(victim.getCells().stream().allMatch(Cell::belongsToBoard));
        assertTrue(victim.getCells().stream().allMatch(Cell::isEmpty));
    }

    @Test
    public void addBlockOutsideBounderies() {
        victim = new Board(20, 20);
        victim.generateSquaredBoard();
        Position blockPosition = new Position(20, 20);
        Block block = new Block(1, Collections.singletonList(blockPosition));
        assertFalse(victim.addBlock(block));
    }

    @Test
    public void addBlockInOccupiedPositionByAnotherBlock() {
        victim = new Board(20, 20);
        victim.generateSquaredBoard();
        Position position1 = new Position(16, 16);
        Position position2 = new Position(17, 17);
        Block block01 = new Block(1, Arrays.asList(position1, position2));
        boolean firstBlockAdded = victim.addBlock(block01);
        assertTrue(firstBlockAdded);
        Block block02 = new Block(2, Collections.singletonList(position1));
        assertFalse(victim.addBlock(block02));
    }

    @Test
    public void updateBlockWithNewCells() {
        victim = new Board(20, 20);
        victim.generateSquaredBoard();
        Position position1 = new Position(16, 16);
        Position position2 = new Position(17, 16);
        Block block01 = new Block(1, Arrays.asList(position1, position2));

        boolean firstBlockAdded = victim.addBlock(block01);
        assertTrue(firstBlockAdded);
        assertEquals(1, victim.getBlocks().size());
        assertEquals(2, victim.getCells().stream().filter(cell -> !cell.isEmpty()).count());
        block01.addPosition(new Position(18, 16));
        block01.addPosition(new Position(19, 16));

        boolean firstBlockUpdated = victim.addBlock(block01);
        assertTrue(firstBlockUpdated);
        assertEquals(1, victim.getBlocks().size());
        assertEquals(4, victim.getCells().stream().filter(cell -> !cell.isEmpty()).count());
    }

    @Test
    public void addNewBlockToBoard() {
        victim = new Board(20, 20);
        victim.generateSquaredBoard();
        Position position1 = new Position(16, 16);
        Position position2 = new Position(17, 16);
        Block block01 = new Block(1, Arrays.asList(position1, position2));

        boolean firstBlockAdded = victim.addBlock(block01);
        assertTrue(firstBlockAdded);
        assertEquals(1, victim.getBlocks().size());
        assertEquals(2, victim.getCells().stream().filter(cell -> !cell.isEmpty()).count());
        block01.addPosition(new Position(18, 16));
        block01.addPosition(new Position(19, 16));

        Position position03 = new Position(3, 6);
        Position position04 = new Position(3, 7);
        Position position05 = new Position(3, 8);
        Block block02 = new Block(2, Arrays.asList(position03, position04, position05));

        boolean secondBlockAdded = victim.addBlock(block02);
        assertTrue(secondBlockAdded);
        assertEquals(2, victim.getBlocks().size());
        assertEquals(5, victim.getCells().stream().filter(cell -> !cell.isEmpty()).count());
    }
}
