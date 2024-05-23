import
        java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;

public class ShortestPathSolver {
    public static final int V_H_COST = 10;
    private final GridCell[][] grid;
    private final PriorityQueue<GridCell> openCells;
    private final boolean[][] visitedCells;
    private int startRow, startCol;
    private int endRow, endCol;

    // Constructor initializes the maze, visitedCells, and openCells.
    // It also sets the start and end points and calculates the heuristic cost for each cell.

    public ShortestPathSolver(int width, int height, int startRow, int startCol, int endRow, int endCol, char[][] map) {
        this.grid = new GridCell[height][width];
        this.visitedCells = new boolean[height][width];
        this.openCells = new PriorityQueue<>((c1, c2) -> {
            return c1.finalCost < c2.finalCost ? -1 : (c1.finalCost > c2.finalCost ? 1 : 0);
        });
        this.setStartCell(startRow, startCol);
        this.setEndCell(endRow, endCol);

        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                if (map[i][j] != '0') {
                    this.grid[i][j] = new GridCell(i, j);
                    this.grid[i][j].heuristicCost = Math.abs(i - this.endRow) + Math.abs(j - this.endCol);
                    this.grid[i][j].Solution = false;
                }
            }
        }

        this.grid[this.startRow][this.startCol].finalCost = 0;
    }

    // Sets the starting point in the maze.
    public void setStartCell(int row, int col) {
        this.startRow = row;
        this.startCol = col;
    }

    // Sets the ending point in the maze.
    public void setEndCell(int row, int col) {
        this.endRow = row;
        this.endCol = col;
    }

    // Updates the total cost of a neighboring cell if it's a better path than the current one.
    public void updateCostIfNeeded(GridCell currentCell, GridCell neighborCell, int cost) {
        if (neighborCell != null && !this.visitedCells[neighborCell.row][neighborCell.col]) {
            int neighborTotalCost = neighborCell.heuristicCost + cost;
            boolean isOpen = this.openCells.contains(neighborCell);
            if (!isOpen || neighborTotalCost < neighborCell.finalCost) {
                neighborCell.finalCost = neighborTotalCost;
                neighborCell.parent = currentCell;
                if (!isOpen) {
                    this.openCells.add(neighborCell);
                }
            }
        }
    }

    // Main method that implements the A* algorithm to find the shortest path.
    public void findShortestPath() {
        this.openCells.add(this.grid[this.startRow][this.startCol]);

        while (!this.openCells.isEmpty()) {
            GridCell currentCell = this.openCells.poll();
            this.visitedCells[currentCell.row][currentCell.col] = true;

            if (currentCell.equals(this.grid[this.endRow][this.endCol])) {
                break;
            }

            // Explore neighboring cells in all four directions.
            GridCell neighborCell;
            int slideCount = this.numUpSlider(currentCell);
            if (slideCount > 0) {
                neighborCell = this.grid[currentCell.row - slideCount][currentCell.col];
                this.updateCostIfNeeded(currentCell, neighborCell, currentCell.finalCost + V_H_COST * slideCount);
            }

            slideCount = this.numLeftSlider(currentCell);
            if (slideCount > 0) {
                neighborCell = this.grid[currentCell.row][currentCell.col - slideCount];
                this.updateCostIfNeeded(currentCell, neighborCell, currentCell.finalCost + V_H_COST * slideCount);
            }

            slideCount = this.numRightSlider(currentCell);
            if (slideCount > 0) {
                neighborCell = this.grid[currentCell.row][currentCell.col + slideCount];
                this.updateCostIfNeeded(currentCell, neighborCell, currentCell.finalCost + V_H_COST * slideCount);
            }

            slideCount = this.numDownSlider(currentCell);
            if (slideCount > 0) {
                neighborCell = this.grid[currentCell.row + slideCount][currentCell.col];
                this.updateCostIfNeeded(currentCell, neighborCell, currentCell.finalCost + V_H_COST * slideCount);
            }
        }
    }


    // Calculates the number of steps a cell can slide upwards.
    public int numUpSlider(GridCell currentCell) {
        int row = currentCell.row;
        int col = currentCell.col;
        int count = 0;

        while (row - 1 >= 0 && this.grid[row - 1][col] != null) {
            --row;
            ++count;
            if (row == this.endRow && col == this.endCol) {
                return count;
            }
        }

        return count;
    }

    // Calculates the number of steps a cell can slide leftwards.
    public int numLeftSlider(GridCell currentCell) {
        int row = currentCell.row;
        int col = currentCell.col;
        int count = 0;

        while (col - 1 >= 0 && this.grid[row][col - 1] != null) {
            --col;
            ++count;
            if (row == this.endRow && col == this.endCol) {
                return count;
            }
        }

        return count;
    }

    // Calculates the number of steps a cell can slide downwards.
    public int numDownSlider(GridCell currentCell) {
        int row = currentCell.row;
        int col = currentCell.col;
        int count = 0;

        while (row + 1 < this.grid.length && this.grid[row + 1][col] != null) {
            ++row;
            ++count;
            if (row == this.endRow && col == this.endCol) {
                return count;
            }
        }

        return count;
    }

    // Calculates the number of steps a cell can slide rightwards.
    public int numRightSlider(GridCell currentCell) {
        int row = currentCell.row;
        int col = currentCell.col;
        int count = 0;

        while (col + 1 < this.grid[0].length && this.grid[row][col + 1] != null) {
            ++col;
            ++count;
            if (row == this.endRow && col == this.endCol) {
                return count;
            }
        }

        return count;
    }

    // Displays the shortest path found by the algorithm.
    public void displaySolution(char[][] map) {
        ArrayList<GridCell> solutionPath = new ArrayList<>();
        if (this.visitedCells[this.endRow][this.endCol]) {
            System.out.println();
            System.out.println("Puzzle solving path  :\n");
            GridCell currentCell = this.grid[this.endRow][this.endCol];
            solutionPath.add(0, currentCell);

            for (this.grid[currentCell.row][currentCell.col].Solution = true; currentCell.parent != null; currentCell = currentCell.parent) {
                solutionPath.add(0, currentCell.parent);
                this.grid[currentCell.parent.row][currentCell.parent.col].Solution = true;
            }

            for (GridCell cell : solutionPath) {
                map[cell.row][cell.col] = '@';
            }

            // Print the updated maze with '@' symbols for the path
            for (char[] row : map) {
                for (char c : row) {
                    System.out.print(c);
                }
                System.out.println();
            }

            System.out.println();
            System.out.println("Solution Path: \n");

            GridCell previousCell = null;
            int stepCount = 0;
            GridCell cell = null;

            for (Iterator<GridCell> iterator = solutionPath.iterator(); iterator.hasNext(); previousCell = cell) {
                cell = iterator.next();
                if (previousCell == null) {
                    ++stepCount;
                    System.out.println("" + stepCount + ". Start at      (" + (cell.col + 1) + "," + (cell.row + 1) + ")");
                } else if (previousCell.row > cell.row) {
                    ++stepCount;
                    System.out.println("" + stepCount + ". Move up to    (" + (cell.col + 1) + "," + (cell.row + 1) + ")");
                } else if (previousCell.row < cell.row) {
                    ++stepCount;
                    System.out.println("" + stepCount + ". Move down to  (" + (cell.col + 1) + "," + (cell.row + 1) + ")");
                } else if (previousCell.col > cell.col) {
                    ++stepCount;
                    System.out.println("" + stepCount + ". Move left to  (" + (cell.col + 1) + "," + (cell.row + 1) + ")");
                } else if (previousCell.col < cell.col) {
                    ++stepCount;
                    System.out.println("" + stepCount + ". Move right to (" + (cell.col + 1) + "," + (cell.row + 1) + ")");
                }
            }

            ++stepCount;
            System.out.println("" + stepCount + ". Done!\n");
            System.out.println();
        } else {
            System.out.println("No possible path");
        }
    }
}