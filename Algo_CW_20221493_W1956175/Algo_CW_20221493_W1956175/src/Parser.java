import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Parser {
    private int gridHeight;
    private int gridWidth;
    private char[][] grid;
    private int startRow, startCol;
    private int endRow, endCol;

    public Parser(String filePath) throws IOException {
        parseMapFile(filePath);
    }

    private void parseMapFile(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;

        // Determine the width and height of the maze
        while ((line = reader.readLine()) != null) {
            gridHeight++;
            gridWidth = Math.max(gridWidth, line.length());
        }
        reader.close();

        // Create a 2D character array to store the maze
        grid = new char[gridHeight][gridWidth];

        // Reset the reader and read the lines again
        reader = new BufferedReader(new FileReader(filePath));
        int currentRow = 0;
        startRow = -1;
        startCol = -1;
        endRow = -1;
        endCol = -1;
        while ((line = reader.readLine()) != null) {
            for (int col = 0; col < line.length(); col++) {
                char cell = line.charAt(col);
                grid[currentRow][col] = cell;
                if (cell == 'S') {
                    startRow = currentRow;
                    startCol = col;
                } else if (cell == 'F') {
                    endRow = currentRow;
                    endCol = col;
                }
            }
            currentRow++;
        }
        reader.close();
    }

    public int getGridHeight() {
        return gridHeight;
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public char[][] getGrid() {
        return grid;
    }

    public int getStartRow() {
        return startRow;
    }

    public int getStartCol() {
        return startCol;
    }

    public int getEndRow() {
        return endRow;
    }

    public int getEndCol() {
        return endCol;
    }
}