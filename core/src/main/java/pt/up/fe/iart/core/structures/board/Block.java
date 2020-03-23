package pt.up.fe.iart.core.structures.board;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Block {
    private final int id;
    private Set<Position> occupiedPositions;

    public Block(int id) {
        this.id = id;
        occupiedPositions = new HashSet<>();
    }

    public Block(int id, List<Position> positions) {
        this(id);
        occupiedPositions.addAll(positions);
    }

    /**
     *
     * @return
     */
    public int getBlockId() {
        return this.id;
    }

    /**
     *
     * @return
     */
    public Set<Position> getOccupiedPositions() {
        return occupiedPositions;
    }

    /**
     *
     * @param position
     */
    public void addPosition(Position position) {
        this.occupiedPositions.add(position);
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
        Block block = (Block) o;
        return id == block.id;
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
