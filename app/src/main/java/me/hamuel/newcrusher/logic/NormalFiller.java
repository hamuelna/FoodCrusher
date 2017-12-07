package me.hamuel.newcrusher.logic;

import me.hamuel.newcrusher.model.Board;
import me.hamuel.newcrusher.model.Cell;
import me.hamuel.newcrusher.model.CellType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Fill the board on all blank cell with a random cell with other type
 */
public class NormalFiller implements Creatable {
    private Random random = new Random();

    @Override
    public List<Cell> fillBoard(Board board) {
        List<Cell> newCells = new ArrayList<>();
        Cell[][] cells = board.getBoard();
        for (int row = 0; row < board.getDim(); row++) {
            for (int col = 0; col < board.getDim(); col++) {
                Cell currentCell = cells[row][col];
                if (currentCell.getType() == CellType.BLANK){
                    randomType(currentCell);
                    newCells.add(currentCell);
                }
            }
        }
        return newCells;
    }

    private void randomType(Cell cell){
        cell.setType(getAllValidTypes().get(random.nextInt(getAllValidTypes().size())));
    }

    private List<CellType> getAllValidTypes(){
        List<CellType> cellTypes = new ArrayList<>();
        Set<CellType> invalidType = new HashSet<>();
        invalidType.add(CellType.BLANK);
        invalidType.add(CellType.BBQ);
        for(CellType cellType: CellType.values()){
            if(cellType != CellType.BLANK && cellType != CellType.BBQ){
                cellTypes.add(cellType);
            }
        }
        return cellTypes;
    }


}
