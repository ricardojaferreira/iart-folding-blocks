package pt.up.fe.iart.core.structures.board;

import java.util.Objects;

public class Position {
    private int xCoord;
    private int yCoord;

    public Position(int xCoord, int yCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    /**
     *
     * @return
     */
    public int getxCoord() {
        return xCoord;
    }

    /**
     *
     * @param xCoord
     */
    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    /**
     *
     * @return
     */
    public int getyCoord() {
        return yCoord;
    }

    /**
     *
     * @param yCoord
     */
    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
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
        Position position = (Position) o;
        return xCoord == position.xCoord && yCoord == position.yCoord;
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(xCoord, yCoord);
    }
}
