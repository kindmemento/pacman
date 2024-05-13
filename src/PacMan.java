public class PacMan extends GameCharacter {
    private int totalPoints;
    private String direction;

    public PacMan(int initialX, int initialY) {
        super(initialX, initialY);
        this.totalPoints = 0;
        this.direction = "RIGHT";
    }

    public void move(int[][] environment, int difficultyLevel, String direction) {
        switch (direction) {
            case "UP":
                moveUp(environment);
                this.direction = "UP";
                break;
            case "DOWN":
                moveDown(environment);
                this.direction = "DOWN";
                break;
            case "LEFT":
                moveLeft(environment);
                this.direction = "LEFT";
                break;
            case "RIGHT":
                moveRight(environment);
                this.direction = "RIGHT";
                break;
        }

        handleHorizontalWraparound(environment[0].length);

        // Check if Pac-Man eats food after moving
        int currentCell = environment[positionY][positionX];
        if (currentCell == 2 || currentCell == 3) { // Pac-Man encounters food
            eatFood(currentCell);
            environment[positionY][positionX] = 0; // Set the cell to empty
        }
    }

    private void handleHorizontalWraparound(int numCols) {
        // Wrap around horizontally (left to right)
        if (positionX < 0) {
            positionX = numCols - 1; // Wrap to the rightmost column
        } else if (positionX >= numCols) {
            positionX = 0; // Wrap to the leftmost column
        }
    }

    private void eatFood(int foodType) {
        if (foodType == 2) {
            // Regular food
            totalPoints += 1;
        } else if (foodType == 3) {
            // Big food
            totalPoints += 10;
        }
    }

    private void moveUp(int[][] environment) {
        if (positionY > 0 && environment[positionY - 1][positionX] != 1) {
            positionY--; // Move up (decrease row index)
        }
    }

    private void moveDown(int[][] environment) {
        if (positionY < environment.length - 1 && environment[positionY + 1][positionX] != 1) {
            positionY++; // Move down (increase row index)
        }
    }

    private void moveLeft(int[][] environment) {
        // Check if Pac-Man can move left without hitting a wall or going out of bounds
        if (positionX > 0 && environment[positionY].length > positionX - 1 && environment[positionY][positionX - 1] != 1) {
            positionX--; // Move left (decrease column index)
        }
    }


    private void moveRight(int[][] environment) {
        if (positionX < environment[0].length - 1 && environment[positionY][positionX + 1] != 1) {
            positionX++; // Move right (increase column index)
        }
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public String getDirection() {
        return this.direction;
    }
}
