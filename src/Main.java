import java.io.IOException;
import java.awt.Color;

public class Main {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private static final int CELL_SIZE = 40;
    private static final int DIFFICULTY_LEVEL = 1;
    private final GameEnvironment gameEnvironment;
    private final PacMan pacMan;
    private final Monster[] monsters;
    private static final String sceneFilePath = "src/input/scene.txt";

    public Main() throws IOException {
        gameEnvironment = new GameEnvironment(sceneFilePath);
        pacMan = new PacMan(1, 1); // Initial position for Pac-Man
        monsters = new Monster[]{
                new Monster(10, 8, Color.RED),
                new Monster(11, 9, Color.BLUE),
                new Monster(12, 8, Color.ORANGE),
                new Monster(11, 7, Color.PINK),
        };
    }

    public void runGame() {
        StdDraw.setCanvasSize(WIDTH, HEIGHT);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);

        // Enable double buffering
        StdDraw.enableDoubleBuffering();

        while (true) {
            handleInput(); // Handle user input for Pac-Man movement
            updateGame();  // Update game state (e.g., move monsters)
            drawGame();    // Draw the updated game state

            // Show the completed frame by swapping buffers
            StdDraw.show();

            // Pause for a short time to control game speed
            StdDraw.pause(100); // Adjust this value based on desired frame rate
        }
    }

    private void drawGame() {
        StdDraw.clear(StdDraw.BLACK); // Set canvas background to black

        // Draw game environment
        int[][] environment = gameEnvironment.getEnvironment();
        for (int i = 0; i < environment.length; i++) {
            for (int j = 0; j < environment[i].length; j++) {
                double x = j * CELL_SIZE + CELL_SIZE / 2.0;
                double y = HEIGHT - i * CELL_SIZE - CELL_SIZE / 2.0;

                switch (environment[i][j]) {
                    case 0: // Empty cell (background)
                        // No need to draw anything for empty cells, as the canvas background is already black
                        break;
                    case 1: // Wall
                        StdDraw.setPenColor(StdDraw.BLUE); // Dark blue for walls
                        StdDraw.filledRectangle(x, y, CELL_SIZE / 2.0, CELL_SIZE / 2.0);
                        break;
                    case 2: // Regular food
                        StdDraw.setPenColor(StdDraw.YELLOW);
                        StdDraw.filledCircle(x, y, CELL_SIZE / 8.0);
                        break;
                    case 3: // Big food
                        StdDraw.setPenColor(StdDraw.YELLOW);
                        StdDraw.filledCircle(x, y, CELL_SIZE / 4.0);
                        break;
                }
            }
        }

        // Draw Pac-Man with correct mouth position based on direction
        StdDraw.setPenColor(StdDraw.YELLOW);
        double pacmanX = pacMan.getPositionX() * CELL_SIZE + CELL_SIZE / 2.0;
        double pacmanY = HEIGHT - pacMan.getPositionY() * CELL_SIZE - CELL_SIZE / 2.0;
        drawPacMan(pacmanX, pacmanY, pacMan.getDirection());

        // Draw Monsters
        for (Monster monster : monsters) {
            StdDraw.setPenColor(monster.getColor());
            StdDraw.filledCircle(monster.getPositionX() * CELL_SIZE + CELL_SIZE / 2.0, HEIGHT - monster.getPositionY() * CELL_SIZE - CELL_SIZE / 2.0, CELL_SIZE / 3.0);
        }

        // Display total points earned
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(50, HEIGHT - 20, "Points: " + pacMan.getTotalPoints());

        StdDraw.show();
    }

    private void drawPacMan(double x, double y, String direction) {
        double mouthSize = CELL_SIZE / 3.0;
        double[] xPoints = new double[3];
        double[] yPoints = new double[3];

        switch (direction) {
            case "UP":
                xPoints = new double[]{x - mouthSize, x + mouthSize, x};
                yPoints = new double[]{y, y, y + mouthSize};
                break;
            case "DOWN":
                xPoints = new double[]{x - mouthSize, x + mouthSize, x};
                yPoints = new double[]{y, y, y - mouthSize};
                break;
            case "LEFT":
                xPoints = new double[]{x, x, x - mouthSize};
                yPoints = new double[]{y - mouthSize, y + mouthSize, y};
                break;
            case "RIGHT":
                xPoints = new double[]{x, x, x + mouthSize};
                yPoints = new double[]{y - mouthSize, y + mouthSize, y};
                break;
        }

        StdDraw.setPenColor(StdDraw.YELLOW);
        StdDraw.filledPolygon(xPoints, yPoints);
    }

    private void handleInput() {
        if (StdDraw.hasNextKeyTyped()) {
            char typedKey = StdDraw.nextKeyTyped();
            switch (typedKey) {
                case 'w':
                case 'W':
                    pacMan.move(gameEnvironment.getEnvironment(), DIFFICULTY_LEVEL, "UP");
                    break;
                case 's':
                case 'S':
                    pacMan.move(gameEnvironment.getEnvironment(), DIFFICULTY_LEVEL, "DOWN");
                    break;
                case 'a':
                case 'A':
                    pacMan.move(gameEnvironment.getEnvironment(), DIFFICULTY_LEVEL, "LEFT");
                    break;
                case 'd':
                case 'D':
                    pacMan.move(gameEnvironment.getEnvironment(), DIFFICULTY_LEVEL, "RIGHT");
                    break;
            }
        }
    }

    private void updateGame() {
        // Update monster positions based on difficulty level
        int monsterMoveSpeed = 1; // Default speed
        if (DIFFICULTY_LEVEL == 2) {
            monsterMoveSpeed = 2; // Faster speed for difficulty level 2
        } else if (DIFFICULTY_LEVEL == 3) {
            monsterMoveSpeed = 3; // Fastest speed for difficulty level 3
        }

        for (int i = 0; i < monsterMoveSpeed; i++) {
            for (Monster monster : monsters) {
                // Generate a random movement direction for each monster
                String[] directions = {"UP", "DOWN", "LEFT", "RIGHT"};
                int randomIndex = (int) (Math.random() * directions.length);
                String randomDirection = directions[randomIndex];

                monster.move(gameEnvironment.getEnvironment(), DIFFICULTY_LEVEL, randomDirection);
            }
        }

        // Check collisions
        for (Monster monster : monsters) {
            if (pacMan.checkCollision(monster)) {
                // Game over
                System.out.println("Game over! Total Points: " + pacMan.getTotalPoints());
                System.exit(0);
            }
        }
    }



    public static void main(String[] args) throws IOException {
        Main game = new Main();
        game.runGame();
    }
}
