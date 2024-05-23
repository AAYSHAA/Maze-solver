import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner path = new Scanner(System.in);
        System.out.println("Enter the path for file: ");
        try {
            // Specify the file path for the maze
            String filePath = path.nextLine();

            // Create a Parser instance
            Parser parser = new Parser(filePath);

            // Get the maze information from the Parser
            int gridHeight = parser.getGridHeight();
            int gridWidth = parser.getGridWidth();
            char[][] grid = parser.getGrid();
            int startRow = parser.getStartRow();
            int startCol = parser.getStartCol();
            int endRow = parser.getEndRow();
            int endCol = parser.getEndCol();

            // Print the maze (optional)
            for (char[] rowData : grid) {
                for (char cell : rowData) {
                    System.out.print(cell);
                }
                System.out.println();
            }

            // Create an instance of AStar and find the shortest path
            ShortestPathSolver aStar = new ShortestPathSolver(gridWidth, gridHeight, startRow, startCol, endRow, endCol, grid);
            long startTime = System.nanoTime();
            aStar.findShortestPath();
            long endTime = System.nanoTime();

            // Display solution
            aStar.displaySolution(grid); // Pass the grid array to the displaySolution method

            // Calculate and display elapsed time
            long duration = (endTime - startTime);
            System.out.println("Elapsed time = " + (double) duration / 1000000 + " milliseconds");
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
