package pt.up.fe.iart.core.structures.board;

import org.junit.Before;
import org.junit.Test;
import pt.up.fe.iart.core.BoardOperationsTestImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


public class BoardTest {
    private Board victim;
    private BoardOperations boardOperations;

    @Before
    public void setUp() {
        boardOperations = new BoardOperationsTestImpl();
    }

    @Test
    public void instantiateBoardClass() {
        victim = new Board(20, 20);
        assertNotNull(victim);
        assertEquals(victim.getCells().size(), 0);
        assertEquals(victim.getBlocks().size(), 0);
    }

    @Test
    public void duplicateBoard() {
        victim = new Board(3, 3);
        victim.generateSquaredBoard();
        Block block = new Block(1, Arrays.asList(new Position(2, 2)));
        boardOperations.addBlock(victim, block);
        Board duplicated = victim.duplicateBoard();
        duplicated.getCells().get(0).setEmpty(false);
        duplicated.getBlocks().get(0).addPosition(new Position(2, 1));
        assertNotEquals(victim.getCells().get(0).isEmpty(), duplicated.getCells().get(0).isEmpty());
        assertNotEquals(victim.getBlocks().get(0).getOccupiedPositions().size(),
                duplicated.getBlocks().get(0).getOccupiedPositions().size());
        assertTrue(victim.getCells().get(0).isEmpty());
        assertFalse(duplicated.getCells().get(0).isEmpty());
        assertEquals(1, victim.getBlocks().get(0).getOccupiedPositions().size());
        assertEquals(2, duplicated.getBlocks().get(0).getOccupiedPositions().size());
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
        assertFalse(boardOperations.addBlock(victim, block));
    }

    @Test
    public void addBlockInOccupiedPositionByAnotherBlock() {
        victim = new Board(20, 20);
        victim.generateSquaredBoard();
        Position position1 = new Position(16, 16);
        Position position2 = new Position(17, 17);
        Block block01 = new Block(1, Arrays.asList(position1, position2));
        boolean firstBlockAdded = boardOperations.addBlock(victim, block01);
        assertTrue(firstBlockAdded);
        Block block02 = new Block(2, Collections.singletonList(position1));
        assertFalse(boardOperations.addBlock(victim, block02));
    }

    @Test
    public void updateBlockWithNewCells() {
        victim = new Board(20, 20);
        victim.generateSquaredBoard();
        Position position1 = new Position(16, 16);
        Position position2 = new Position(17, 16);
        Block block01 = new Block(1, Arrays.asList(position1, position2));

        boolean firstBlockAdded = boardOperations.addBlock(victim, block01);
        assertTrue(firstBlockAdded);
        assertEquals(1, victim.getBlocks().size());
        assertEquals(2, victim.getCells().stream().filter(cell -> !cell.isEmpty()).count());
        block01.addPosition(new Position(18, 16));
        block01.addPosition(new Position(19, 16));

        boolean firstBlockUpdated = boardOperations.addBlock(victim, block01);
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

        boolean firstBlockAdded = boardOperations.addBlock(victim, block01);
        assertTrue(firstBlockAdded);
        assertEquals(1, victim.getBlocks().size());
        assertEquals(2, victim.getCells().stream().filter(cell -> !cell.isEmpty()).count());
        block01.addPosition(new Position(18, 16));
        block01.addPosition(new Position(19, 16));

        Position position03 = new Position(3, 6);
        Position position04 = new Position(3, 7);
        Position position05 = new Position(3, 8);
        Block block02 = new Block(2, Arrays.asList(position03, position04, position05));

        boolean secondBlockAdded = boardOperations.addBlock(victim, block02);
        assertTrue(secondBlockAdded);
        assertEquals(2, victim.getBlocks().size());
        assertEquals(5, victim.getCells().stream().filter(cell -> !cell.isEmpty()).count());
    }

    @Test
    public void equalBoards() {
        victim = new Board(20, 20);
        victim.generateSquaredBoard();
        Position position01 = new Position(16, 16);
        Position position02 = new Position(17, 16);
        boardOperations.addBlock(victim, new Block(victim.getNextBlockId(), Arrays.asList(position01, position02)));

        Board equalBoard = new Board(20, 20);
        equalBoard.generateSquaredBoard();
        Position position03 = new Position(0, 0);
        Position position04 = new Position(0, 1);
        boardOperations.addBlock(equalBoard, new Block(equalBoard.getNextBlockId(), Arrays.asList(position03, position04)));

        assertNotEquals(victim, equalBoard);
        victim.getBlockById(1).addAllPositions(new HashSet<>(Arrays.asList(position03, position04)));
        equalBoard.getBlockById(1).addAllPositions(new HashSet<>(Arrays.asList(position01, position02)));
        assertNotEquals(victim, equalBoard);

        boardOperations.addBlock(victim, victim.getBlockById(1));
        boardOperations.addBlock(equalBoard, equalBoard.getBlockById(1));
        assertEquals(victim, equalBoard);

        boardOperations.addBlock(victim, new Block(3, Collections.singletonList(new Position(3, 4))));
        boardOperations.addBlock(equalBoard, new Block(2, Collections.singletonList(new Position(6, 7))));
        assertNotEquals(victim, equalBoard);

        boardOperations.addBlock(victim, new Block(2, Collections.singletonList(new Position(6, 7))));
        boardOperations.addBlock(equalBoard, new Block(3, Collections.singletonList(new Position(3, 4))));
        assertEquals(victim, equalBoard);
    }
}
