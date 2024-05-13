import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class GameEnvironment {
    private final int[][] environment;

    public GameEnvironment(String sceneFilePath) throws IOException {
        environment = loadScene(sceneFilePath);
    }

    public static int[][] loadScene(String sceneFilePath) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(sceneFilePath));

        // Parse the first line to get the number of rows and columns
        String[] dimensions = lines.getFirst().split(" ");
        int numRows = Integer.parseInt(dimensions[0]);
        int numCols = Integer.parseInt(dimensions[1]);

        // Create a 2D array to store the game environment
        int[][] environment = new int[numRows][numCols];

        // Populate the environment array based on subsequent lines in the file
        for (int i = 1; i <= numRows; i++) {
            String[] rowValues = lines.get(i).split(" ");
            for (int j = 0; j < numCols; j++) {
                environment[i - 1][j] = Integer.parseInt(rowValues[j]);
            }
        }

        return environment;
    }


    public boolean checkAllFoodEaten() {
        for (int[] row : environment) {
            for (int cell : row) {
                if (cell == 2 || cell == 3) {
                    return false;
                }
            }
        }
        return false;
    }

    public int[][] getEnvironment() {
        return environment;
    }
}
