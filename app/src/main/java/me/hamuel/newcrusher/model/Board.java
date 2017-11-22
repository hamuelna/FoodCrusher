package me.hamuel.newcrusher.model;

import me.hamuel.newcrusher.event.*;
import me.hamuel.newcrusher.logic.*;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
    private Cell[][] board;
    private int dim;
    public final int VERTICAL_OFFSET = 100;
    public final int HORIZONTAL_OFFSET = 100;
    public final int SIDE_LENGTH = 100;
    public final int GAP = 10;
    private List<Destroyable> destroyables = new ArrayList<>();
    private List<Fallable> fallables = new ArrayList<>();
    private List<Swappable> swappables = new ArrayList<>();

    public Board(int dim) {
        this.dim = dim;
        board = new Cell[dim][dim];
        destroyables.addAll(Arrays.asList(
                new DefaultDestroyer()
        ));
        fallables.addAll(
                Arrays.asList(
                        new DefaultFaller()
                )
        );
        swappables.addAll(Arrays.asList(
                new DefaultSwapper()
        ));
    }

    public void initBoard() {
        InitBoard initBoard = new InitBoard();
        //send message to frontend what to initialize
        List<Cell> cells = initBoard.fillBoard(this);
        EventBus.getDefault().post(new FillCellEvent(cells));
    }


    public Cell[][] getBoard() {
        return board;
    }

    public List<CellPair> swap(Cell a, Cell b){
        List<CellPair> movedPostion = new ArrayList<>();
        for(Swappable swapper: swappables){
            if (swapper.isSwappable(a,b,this)){
                movedPostion.addAll(swapper.swap(a,b,this));
            }
        }
        return movedPostion;

    }

    public boolean isSwappable(Cell a, Cell b){
        for(Swappable swapper: swappables){
            if (swapper.isSwappable(a,b,this)){
                return true;
            }
        }
        return false;
    }

    public List<Cell> destroy(){
        List<Cell> destroyedCell = new ArrayList<>();
        for(Destroyable destroyer: destroyables){
            if(destroyer.isDestroyable(this)){
                destroyedCell.addAll(destroyer.destroy(this));
            }
        }
        return destroyedCell;
    }

    public boolean isDestroyable(){
        for(Destroyable destroyer: destroyables){
            if(destroyer.isDestroyable(this)){
                return true;
            }
        }
        return false;
    }

    public List<CellPair> collapse(){
        List<CellPair> movedPostion = new ArrayList<>();
        for(Fallable faller: fallables){
            movedPostion.addAll(faller.collapse(this));
        }
        return movedPostion;
    }

    public int getDim() {
        return dim;
    }

    private Cell findCell(Coordinate coordinate){
        for(Cell[] cellRow: board){
            for(Cell cell: cellRow){
                if(cell.getCoordinate().equals(coordinate)){
                    return cell;
                }
            }
        }
        System.out.println("Unable to find cell");
        return null;
    }

    @Subscribe
    public void onMoveCellEvent(MoveCellEvent moveCellEvent){
        Cell a = findCell(moveCellEvent.getCellA());
        Cell b = findCell(moveCellEvent.getCellB());
        if(isSwappable(a, b)){
            EventBus.getDefault().post(new AnimateCellEvent(swap(a,b), "swap"));
        }

    }

    private void cellRemovalProcess(){
        List<Coordinate> coordinates = new ArrayList<>();
        for(Cell cell: destroy()){
            coordinates.add(cell.getCoordinate());
        }
        EventBus.getDefault().post(new RemoveCellEvent(coordinates));
    }

    @Subscribe
    public void onAnimationEnd(AnimationEndEvent animationEndEvent){
        String msg = animationEndEvent.getMessage();
        if(msg.equals("end swap")){
            cellRemovalProcess();
        }else if(msg.equals("end destroy")){
            //start collaspsing
            EventBus.getDefault().post(new AnimateCellEvent(collapse(), "collapse"));
        }else if(msg.equals("end collapse")){
            //check whether there are still some cell left to destroy after collapse
            if(isDestroyable()){
                cellRemovalProcess();
            }else{
                //tell front that we can now accept input again
            }
        }
    }
}
