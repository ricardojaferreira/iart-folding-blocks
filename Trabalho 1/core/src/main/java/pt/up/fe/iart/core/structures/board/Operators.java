package pt.up.fe.iart.core.structures.board;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public enum Operators {
    DOUBLE_UP("Double Up") {
        @Override
        public Set<Position> getSymmetricBlockPositions(Block block, Board board, BoardOperations operations) {
            Optional<Integer> upperYCoord = block.getOccupiedPositions().stream().map(Position::getyCoord).min(Integer::compareTo);
            if (upperYCoord.isPresent()) {
                Set<Position> symetricBlockPositions = block.getOccupiedPositions().stream().map(p -> {
                    int x = p.getxCoord();
                    int y = (upperYCoord.get() - 1) - (p.getyCoord() - upperYCoord.get());
                    return new Position(x, y);
                }).collect(Collectors.toSet());

                if (operations.checkIfPositionsAreAvailable(symetricBlockPositions, board)) {
                    return symetricBlockPositions;
                }
            }

           return Collections.emptySet();
        }
    },
    DOUBLE_DOWN("Double Down") {
        @Override
        public Set<Position> getSymmetricBlockPositions(Block block, Board board, BoardOperations operations) {
            Optional<Integer> lowerYCoord = block.getOccupiedPositions().stream().map(Position::getyCoord).max(Integer::compareTo);
            if (lowerYCoord.isPresent()) {
                Set<Position> symetricBlockPositions = block.getOccupiedPositions().stream().map(p -> {
                    int x = p.getxCoord();
                    int y = lowerYCoord.get() + 1 + (lowerYCoord.get() - p.getyCoord());
                    return new Position(x, y);
                }).collect(Collectors.toSet());

                if (operations.checkIfPositionsAreAvailable(symetricBlockPositions, board)) {
                    return symetricBlockPositions;
                }
            }

            return Collections.emptySet();
        }
    },
    DOUBLE_LEFT("Double Left") {
        @Override
        public Set<Position> getSymmetricBlockPositions(Block block, Board board, BoardOperations operations) {
            Optional<Integer> lowerXCoord = block.getOccupiedPositions().stream().map(Position::getxCoord).min(Integer::compareTo);
            if (lowerXCoord.isPresent()) {
                Set<Position> symetricBlockPositions = block.getOccupiedPositions().stream().map(p -> {
                    int x = (lowerXCoord.get() - 1) - (p.getxCoord() - lowerXCoord.get());
                    int y = p.getyCoord();
                    return new Position(x, y);
                }).collect(Collectors.toSet());

                if (operations.checkIfPositionsAreAvailable(symetricBlockPositions, board)) {
                    return symetricBlockPositions;
                }
            }

            return Collections.emptySet();
        }
    },
    DOUBLE_RIGHT("Double Right") {
        @Override
        public Set<Position> getSymmetricBlockPositions(Block block, Board board, BoardOperations operations) {
            Optional<Integer> higherXCoord = block.getOccupiedPositions().stream().map(Position::getxCoord).max(Integer::compareTo);
            if (higherXCoord.isPresent()) {
                Set<Position> symetricBlockPositions = block.getOccupiedPositions().stream().map(p -> {
                    int x = higherXCoord.get() + 1 + (higherXCoord.get()  - p.getxCoord());
                    int y = p.getyCoord();
                    return new Position(x, y);
                }).collect(Collectors.toSet());

                if (operations.checkIfPositionsAreAvailable(symetricBlockPositions, board)) {
                    return symetricBlockPositions;
                }
            }

            return Collections.emptySet();
        }
    };

    private String movementName;
    public abstract Set<Position> getSymmetricBlockPositions(Block block, Board board, BoardOperations operations);

    Operators(String movementName) {
        this.movementName = movementName;
    }

    public String getMovementName() {
        return this.movementName;
    }
}
