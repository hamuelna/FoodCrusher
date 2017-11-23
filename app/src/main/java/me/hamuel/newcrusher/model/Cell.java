package me.hamuel.newcrusher.model;

public class Cell {
    private int row;
    private int col;
    private Coordinate coordinate;
    private CellType type;

    public void setType(CellType type) {
        this.type = type;
    }

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

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cell cell = (Cell) o;

        if (row != cell.row) return false;
        if (col != cell.col) return false;
        return coordinate != null ? coordinate.equals(cell.coordinate) : cell.coordinate == null;
    }

    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + col;
        result = 31 * result + (coordinate != null ? coordinate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "row=" + row +
                ", col=" + col +
                ", coordinate=" + coordinate +
                ", type=" + type +
                '}';
    }

    public Cell clone() {
        return new Cell(this.row, this.col, this.coordinate, this.type);
    }

}
