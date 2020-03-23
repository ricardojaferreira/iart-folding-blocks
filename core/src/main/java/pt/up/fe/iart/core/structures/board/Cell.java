package pt.up.fe.iart.core.structures.board;

import java.util.Objects;

public class Cell {
    private Position position;
    private boolean belongsToBoard;
    private boolean isEmpty;

    public Cell(Position position, boolean belongsToBoard, boolean isEmpty) {
        this.position = position;
        this.belongsToBoard = belongsToBoard;
        this.isEmpty = isEmpty;
    }

    /**
     *
     * @return
     */
    public Position getPosition() {
        return position;
    }

    /**
     *
     * @param position
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     *
     * @return
     */
    public boolean belongsToBoard() {
        return belongsToBoard;
    }

    /**
     *
     * @param belongsToBoard
     */
    public void setBelongsToBoard(boolean belongsToBoard) {
        this.belongsToBoard = belongsToBoard;
    }

    /**
     *
     * @return
     */
    public boolean isEmpty() {
        return isEmpty;
    }

    /**
     *
     * @param empty
     */
    public void setEmpty(boolean empty) {
        isEmpty = empty;
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
        Cell cell = (Cell) o;
        return position.equals(cell.position);
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(position);
    }
}
