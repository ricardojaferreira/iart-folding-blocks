package pt.up.fe.iart.core.structures.board;

import org.junit.Before;
import org.junit.Test;
import pt.up.fe.iart.core.BoardOperationsTestImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class OperatorsTest {
    private BoardOperations boardOperations;

    @Before
    public void setUp() {
        boardOperations = new BoardOperationsTestImpl();
    }

    @Test
    public void canBeDoubledUP() {
        Board board = new Board(5, 5);
        board.generateSquaredBoard();
        Position pos01 = new Position(2, 2);
        Position pos02 = new Position(2, 3);
        Position pos03 = new Position(1, 3);
        Block block = new Block(1, Arrays.asList(pos01, pos02, pos03));
        boardOperations.addBlock(board, block);
        assertFalse(board.getCells().get(12).isEmpty());
        assertFalse(board.getCells().get(16).isEmpty());
        assertFalse(board.getCells().get(17).isEmpty());

        Set<Position> symetricPositions = Operators.DOUBLE_UP.getSymmetricBlockPositions(block, board, boardOperations);
        Position expectedPos01 = new Position(2, 1);
        Position expectedPos02 = new Position(2, 0);
        Position expectedPos03 = new Position(1, 0);
        assertFalse(symetricPositions.isEmpty());
        assertTrue(symetricPositions.contains(expectedPos01));
        assertTrue(symetricPositions.contains(expectedPos02));
        assertTrue(symetricPositions.contains(expectedPos03));
    }

    @Test
    public void cantBeDoubledUpBecauseSymmetricIsOutsideBoundaries() {
        Board board = new Board(5, 5);
        board.generateSquaredBoard();
        Position pos01 = new Position(2, 1);
        Position pos02 = new Position(1, 2);
        Position pos03 = new Position(2, 2);
        Block block01 = new Block(1, Arrays.asList(pos01, pos02, pos03));
        boardOperations.addBlock(board, block01);
        assertFalse(board.getCells().get(7).isEmpty());
        assertFalse(board.getCells().get(11).isEmpty());
        assertFalse(board.getCells().get(12).isEmpty());

        Set<Position> symetricPositions = Operators.DOUBLE_UP.getSymmetricBlockPositions(block01, board, boardOperations);
        assertTrue(symetricPositions.isEmpty());
    }

    @Test
    public void cantBeDoubledUpBecauseSymmetricGoesToFilledPositions() {
        Board board = new Board(5, 5);
        board.generateSquaredBoard();
        Position pos01 = new Position(2, 2);
        Position pos02 = new Position(1, 3);
        Position pos03 = new Position(2, 3);
        Block block = new Block(1, Arrays.asList(pos01, pos02, pos03));
        Block block02 = new Block(2, Collections.singletonList(new Position(2, 1)));
        boardOperations.addBlock(board, block);
        boardOperations.addBlock(board, block02);
        assertFalse(board.getCells().get(7).isEmpty());
        assertFalse(board.getCells().get(12).isEmpty());
        assertFalse(board.getCells().get(16).isEmpty());
        assertFalse(board.getCells().get(17).isEmpty());

        Set<Position> symetricPositions = Operators.DOUBLE_UP.getSymmetricBlockPositions(block, board, boardOperations);
        assertTrue(symetricPositions.isEmpty());
    }

    @Test
    public void canBeDoubledDOWN() {
        Board board = new Board(6, 6);
        board.generateSquaredBoard();
        Position pos01 = new Position(1, 0);
        Position pos02 = new Position(4, 0);
        Position pos03 = new Position(2, 1);
        Position pos04 = new Position(3, 2);
        Block block = new Block(1, Arrays.asList(pos01, pos02, pos03, pos04));
        boardOperations.addBlock(board, block);
        assertFalse(board.getCells().get(1).isEmpty());
        assertFalse(board.getCells().get(4).isEmpty());
        assertFalse(board.getCells().get(8).isEmpty());
        assertFalse(board.getCells().get(15).isEmpty());

        Set<Position> symetricPositions = Operators.DOUBLE_DOWN.getSymmetricBlockPositions(block, board, boardOperations);
        Position expectedPos01 = new Position(1, 5);
        Position expectedPos02 = new Position(2, 4);
        Position expectedPos03 = new Position(3, 3);
        Position expectedPos04 = new Position(4, 5);
        assertFalse(symetricPositions.isEmpty());
        assertTrue(symetricPositions.contains(expectedPos01));
        assertTrue(symetricPositions.contains(expectedPos02));
        assertTrue(symetricPositions.contains(expectedPos03));
        assertTrue(symetricPositions.contains(expectedPos04));
    }

    @Test
    public void cantBeDoubledDownBecauseSymmetricIsOutsideBoundaries() {
        Board board = new Board(6, 6);
        board.generateSquaredBoard();
        Position pos01 = new Position(1, 1);
        Position pos02 = new Position(4, 1);
        Position pos03 = new Position(2, 2);
        Position pos04 = new Position(3, 3);
        Block block = new Block(1, Arrays.asList(pos01, pos02, pos03, pos04));
        boardOperations.addBlock(board, block);
        assertFalse(board.getCells().get(7).isEmpty());
        assertFalse(board.getCells().get(10).isEmpty());
        assertFalse(board.getCells().get(14).isEmpty());
        assertFalse(board.getCells().get(21).isEmpty());

        Set<Position> symetricPositions = Operators.DOUBLE_DOWN.getSymmetricBlockPositions(block, board, boardOperations);
        assertTrue(symetricPositions.isEmpty());
    }

    @Test
    public void cantBeDoubledDownBecauseSymmetricGoesToFilledPosition() {
        Board board = new Board(6, 6);
        board.generateSquaredBoard();
        Position pos01 = new Position(1, 0);
        Position pos02 = new Position(4, 0);
        Position pos03 = new Position(2, 1);
        Position pos04 = new Position(3, 2);
        Block block = new Block(1, Arrays.asList(pos01, pos02, pos03, pos04));

        Position pos05 = new Position(1, 5);
        Block block02 = new Block(2, Collections.singletonList(pos05));

        boardOperations.addBlock(board, block);
        boardOperations.addBlock(board, block02);
        assertFalse(board.getCells().get(1).isEmpty());
        assertFalse(board.getCells().get(4).isEmpty());
        assertFalse(board.getCells().get(8).isEmpty());
        assertFalse(board.getCells().get(15).isEmpty());
        assertFalse(board.getCells().get(31).isEmpty());

        Set<Position> symetricPositions = Operators.DOUBLE_DOWN.getSymmetricBlockPositions(block, board, boardOperations);
        assertTrue(symetricPositions.isEmpty());
    }

    @Test
    public void canBeDoubledLeft() {
        Board board = new Board(6, 6);
        board.generateSquaredBoard();
        Position pos01 = new Position(3, 1);
        Position pos02 = new Position(3, 2);
        Position pos03 = new Position(3, 3);
        Position pos04 = new Position(2, 2);

        Block block = new Block(1, Arrays.asList(pos01, pos02, pos03, pos04));
        boardOperations.addBlock(board, block);
        assertFalse(board.getCells().get(9).isEmpty());
        assertFalse(board.getCells().get(14).isEmpty());
        assertFalse(board.getCells().get(15).isEmpty());
        assertFalse(board.getCells().get(21).isEmpty());

        Set<Position> symmetricPositions = Operators.DOUBLE_LEFT.getSymmetricBlockPositions(block, board, boardOperations);
        Position expectedPos01 = new Position(0, 1);
        Position expectedPos02 = new Position(0, 2);
        Position expectedPos03 = new Position(0, 3);
        Position expectedPos04 = new Position(1, 2);
        assertFalse(symmetricPositions.isEmpty());
        assertTrue(symmetricPositions.contains(expectedPos01));
        assertTrue(symmetricPositions.contains(expectedPos02));
        assertTrue(symmetricPositions.contains(expectedPos03));
        assertTrue(symmetricPositions.contains(expectedPos04));
    }

    @Test
    public void cantBeDoubledLeftBecauseSymmetricIsOutsideBoundaries() {
        Board board = new Board(6, 6);
        board.generateSquaredBoard();
        Position pos01 = new Position(2, 1);
        Position pos02 = new Position(2, 2);
        Position pos03 = new Position(2, 3);
        Position pos04 = new Position(1, 2);

        Block block = new Block(1, Arrays.asList(pos01, pos02, pos03, pos04));
        boardOperations.addBlock(board, block);
        assertFalse(board.getCells().get(8).isEmpty());
        assertFalse(board.getCells().get(13).isEmpty());
        assertFalse(board.getCells().get(14).isEmpty());
        assertFalse(board.getCells().get(20).isEmpty());

        Set<Position> symmetricPositions = Operators.DOUBLE_LEFT.getSymmetricBlockPositions(block, board, boardOperations);
        assertTrue(symmetricPositions.isEmpty());
    }

    @Test
    public void cantBeDoubledLeftBecauseSymmetricGoesToFilledPosition() {
        Board board = new Board(6, 6);
        board.generateSquaredBoard();
        Position pos01 = new Position(3, 1);
        Position pos02 = new Position(3, 2);
        Position pos03 = new Position(3, 3);
        Position pos04 = new Position(2, 2);

        Block block = new Block(1, Arrays.asList(pos01, pos02, pos03, pos04));

        Position pos05 = new Position(0, 1);
        Block block1 = new Block(2, Collections.singletonList(pos05));

        boardOperations.addBlock(board, block);
        boardOperations.addBlock(board, block1);
        assertFalse(board.getCells().get(9).isEmpty());
        assertFalse(board.getCells().get(14).isEmpty());
        assertFalse(board.getCells().get(15).isEmpty());
        assertFalse(board.getCells().get(21).isEmpty());
        assertFalse(board.getCells().get(6).isEmpty());

        Set<Position> symmetricPositions = Operators.DOUBLE_LEFT.getSymmetricBlockPositions(block, board, boardOperations);
        assertTrue(symmetricPositions.isEmpty());
    }

    @Test
    public void canBeDoubledRight() {
        Board board = new Board(6, 6);
        board.generateSquaredBoard();
        Position pos01 = new Position(0, 1);
        Position pos02 = new Position(0, 2);
        Position pos03 = new Position(0, 3);
        Position pos04 = new Position(1, 2);

        Block block = new Block(1, Arrays.asList(pos01, pos02, pos03, pos04));
        boardOperations.addBlock(board, block);
        assertFalse(board.getCells().get(6).isEmpty());
        assertFalse(board.getCells().get(12).isEmpty());
        assertFalse(board.getCells().get(13).isEmpty());
        assertFalse(board.getCells().get(18).isEmpty());

        Set<Position> symmetricPositions = Operators.DOUBLE_RIGHT.getSymmetricBlockPositions(block, board, boardOperations);
        Position expectedPos01 = new Position(3, 1);
        Position expectedPos02 = new Position(3, 2);
        Position expectedPos03 = new Position(3, 3);
        Position expectedPos04 = new Position(2, 2);
        assertFalse(symmetricPositions.isEmpty());
        assertTrue(symmetricPositions.contains(expectedPos01));
        assertTrue(symmetricPositions.contains(expectedPos02));
        assertTrue(symmetricPositions.contains(expectedPos03));
        assertTrue(symmetricPositions.contains(expectedPos04));
    }

    @Test
    public void cantBeDoubledRightBecauseSymmetricIsOutsideBoundaries() {
        Board board = new Board(6, 6);
        board.generateSquaredBoard();
        Position pos01 = new Position(3, 1);
        Position pos02 = new Position(3, 2);
        Position pos03 = new Position(3, 3);
        Position pos04 = new Position(4, 2);

        Block block = new Block(1, Arrays.asList(pos01, pos02, pos03, pos04));
        boardOperations.addBlock(board, block);
        assertFalse(board.getCells().get(9).isEmpty());
        assertFalse(board.getCells().get(15).isEmpty());
        assertFalse(board.getCells().get(16).isEmpty());
        assertFalse(board.getCells().get(21).isEmpty());

        Set<Position> symmetricPositions = Operators.DOUBLE_RIGHT.getSymmetricBlockPositions(block, board, boardOperations);
        assertTrue(symmetricPositions.isEmpty());
    }

    @Test
    public void cantBeDoubledRightBecauseSymmetricGoesToFilledPosition() {
        Board board = new Board(6, 6);
        board.generateSquaredBoard();
        Position pos01 = new Position(0, 1);
        Position pos02 = new Position(0, 2);
        Position pos03 = new Position(0, 3);
        Position pos04 = new Position(1, 2);

        Block block = new Block(1, Arrays.asList(pos01, pos02, pos03, pos04));
        Position pos05 = new Position(3, 2);
        Block block1 = new Block(2, Collections.singletonList(pos05));
        boardOperations.addBlock(board, block);
        boardOperations.addBlock(board, block1);
        assertFalse(board.getCells().get(6).isEmpty());
        assertFalse(board.getCells().get(12).isEmpty());
        assertFalse(board.getCells().get(13).isEmpty());
        assertFalse(board.getCells().get(18).isEmpty());
        assertFalse(board.getCells().get(15).isEmpty());

        Set<Position> symmetricPositions = Operators.DOUBLE_RIGHT.getSymmetricBlockPositions(block, board, boardOperations);
        assertTrue(symmetricPositions.isEmpty());
    }
}
