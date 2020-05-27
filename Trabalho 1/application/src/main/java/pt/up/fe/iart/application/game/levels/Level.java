package pt.up.fe.iart.application.game.levels;

import pt.up.fe.iart.core.structures.board.Board;

public interface Level {
    Board bootstrapLevel();
    Board getBoard();
}
