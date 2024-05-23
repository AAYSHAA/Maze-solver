public class GridCell {
    public int row, col;
    public int heuristicCost;
    public int finalCost;
    public boolean Solution;
    public GridCell parent;

    // Create a new cell with a row and column
    public GridCell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    // Check if two cells are the same
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof GridCell)) {
            return false;
        }
        GridCell other = (GridCell) obj;
        return this.row == other.row && this.col == other.col;
    }
}