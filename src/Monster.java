import java.awt.Color;

public class Monster extends GameCharacter {
    private Color color;

    public Monster(int initialX, int initialY, Color color) {
        super(initialX, initialY);
        this.color = color;
    }

    public void move(int[][] environment, int difficultyLevel, String direction) {
        // Implement movement logic based on direction and environment
        switch (direction) {
            case "UP":
                if (isValidMove(environment, positionX, positionY + 1)) {
                    moveUp(environment);
                }
                break;
            case "DOWN":
                if (isValidMove(environment, positionX, positionY - 1)) {
                    moveDown(environment);
                }
                break;
            case "LEFT":
                if (isValidMove(environment, positionX - 1, positionY)) {
                    moveLeft(environment);
                }
                break;
            case "RIGHT":
                if (isValidMove(environment, positionX + 1, positionY)) {
                    moveRight(environment);
                }
                break;
        }
    }

    private boolean isValidMove(int[][] environment, int x, int y) {
        // Check if the move is within bounds and not colliding with walls
        return x >= 0 && x < environment[0].length && y >= 0 && y < environment.length && environment[y][x] != 1;
    }

    private void moveUp(int[][] environment) {
        if (positionY < environment.length - 1 && environment[positionY + 1][positionX] != 1) {
            positionY++;
        }
    }

    private void moveDown(int[][] environment) {
        if (positionY > 0 && environment[positionY - 1][positionX] != 1) {
            positionY--;
        }
    }

    private void moveLeft(int[][] environment) {
        if (positionX > 0 && environment[positionY][positionX - 1] != 1) {
            positionX--;
        }
    }

    private void moveRight(int[][] environment) {
        if (positionX < environment[0].length - 1 && environment[positionY][positionX + 1] != 1) {
            positionX++;
        }
    }

    public Color getColor() {
        return color;
    }
}