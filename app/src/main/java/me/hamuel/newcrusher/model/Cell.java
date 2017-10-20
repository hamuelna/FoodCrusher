package me.hamuel.newcrusher.model;

public class Cell {
    private int row;
    private int col;
    private Coordinate coordinate;
    private CellType type;

    public Cell(int row, int col, Coordinate coordinate, CellType type) {
        this.row = row;
        this.col = col;
        this.coordinate = coordinate;
        this.type = type;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public CellType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cell cell = (Cell) o;

        if (row != cell.row) return false;
        if (col != cell.col) return false;
        return type == cell.type;
    }

    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + col;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
