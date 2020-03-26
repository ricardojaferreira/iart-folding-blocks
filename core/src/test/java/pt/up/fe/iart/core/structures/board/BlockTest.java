package pt.up.fe.iart.core.structures.board;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertEquals;

public class BlockTest {

    @Test
    public void equalBlocks() {
        Block victim = new Block(4, Arrays.asList(new Position(15, 15), new Position(16, 16)));
        Block equalBlock = new Block(4, Arrays.asList(new Position(16, 16), new Position(15, 15)));
        assertEquals(victim, equalBlock);

        victim.addPosition(new Position(18, 18));
        equalBlock.addPosition(new Position(17, 17));
        assertNotEquals(victim, equalBlock);

        victim.addPosition(new Position(17, 17));
        equalBlock.addPosition(new Position(18, 18));
        assertEquals(victim, equalBlock);
    }
}
