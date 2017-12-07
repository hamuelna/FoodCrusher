package me.hamuel.newcrusher.model;

import android.support.v4.app.DialogFragment;

import me.hamuel.newcrusher.event.*;
import me.hamuel.newcrusher.logic.*;
import me.hamuel.newcrusher.utils.BoardUtils;
import me.hamuel.newcrusher.view.GameOverDialog;

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
    public final int FILL_ANIMATE_OFFSET = 1000;
    private List<Destroyable> destroyables = new ArrayList<>();
    private List<Fallable> fallables = new ArrayList<>();
    private List<Swappable> swappables = new ArrayList<>();
    private List<Creatable> creatables = new ArrayList<>();
    private List<GameOverable> gameOverables = new ArrayList<>();
    private int combo = 1;

    public Board(int dim) {
        this.dim = dim;
        board = new Cell[dim][dim];
        destroyables.addAll(Arrays.asList(
                new BBQDestroyer(),
                new DefaultDestroyer()
        ));
        fallables.addAll(
                Arrays.asList(
                        new DefaultFaller()
                )
        );
        swappables.addAll(Arrays.asList(
                new DefaultSwapper(destroyables)
        ));
        creatables.addAll(Arrays.asList(
                new BBQFiller(),
                new NormalFiller()
        ));
        gameOverables.addAll(Arrays.asList(
                new DefaultGameOver(swappables)
        ));
    }

    public void initBoard() {
        InitFiller initFiller = new InitFiller();
        //send message to frontend what to initialize
        List<Cell> cells = initFiller.fillBoard(this);
        EventBus.getDefault().post(new FillCellEvent(cells));
    }


    public Cell[][] getBoard() {
        return board;
    }

    public void setBoard(Cell[][] board) {
        this.board = board;
    }

    public List<CellPair> swap(Cell a, Cell b){
        List<CellPair> movedPosition = new ArrayList<>();
        for(Swappable swapper: swappables){
            if (swapper.isSwappable(a,b,this)){
                movedPosition.addAll(swapper.swap(a,b,this));
            }
        }
        return movedPosition;

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
        int totalScore = 0;
        for(Destroyable destroyer: destroyables){
            if(destroyer.isDestroyable(this)){
                totalScore += destroyer.increaseScore(this);
                destroyedCell.addAll(destroyer.destroy(this));
                break;
            }
        }
        EventBus.getDefault().post(new ScoringEvent(totalScore, combo));
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

    //refill the damn board after some cell are destroy
    public void refill(){
        List<Cell> cellsToBeFill = new ArrayList<>();
        for (Creatable creatable: creatables){
            cellsToBeFill.addAll(creatable.fillBoard(this));
        }
        List<CellPair> cellsToBeAnimated = new ArrayList<>();
        List<Cell> cellsBeforeAnimated = new ArrayList<>();
        for (Cell cell: cellsToBeFill){
            Cell beforeFall = cell.clone();
            Coordinate c = beforeFall.getCoordinate();
            beforeFall.setCoordinate(new Coordinate(c.getLeft(), c.getTop() - FILL_ANIMATE_OFFSET, c.getRight(), c.getBottom() - FILL_ANIMATE_OFFSET));
            cellsBeforeAnimated.add(beforeFall);
            cellsToBeAnimated.add(new CellPair(
                    beforeFall,
                    cell
            ));
        }

        EventBus.getDefault().post(new PartialFillCellEvent(cellsToBeAnimated, cellsBeforeAnimated));
    }

    public List<CellPair> collapse(){
        List<CellPair> movedPosition = new ArrayList<>();
        for(Fallable faller: fallables){
            movedPosition.addAll(faller.collapse(this));
        }
        return movedPosition;
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
        return null;
    }

    @Subscribe
    public void onMoveCellEvent(MoveCellEvent moveCellEvent){
        Cell a = findCell(moveCellEvent.getCellA());
        Cell b = findCell(moveCellEvent.getCellB());
        if(isSwappable(a, b)){
            EventBus.getDefault().post(new AnimateCellEvent(swap(a,b), "swap"));
        }else{
            EventBus.getDefault().post(new ToggleBlockingEvent());
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
            System.out.println("after collapse");
        }else if(msg.equals("end collapse")){
            System.out.println("trigger end collapse");
            refill();
        }else if(msg.equals("end refill")){
            if(isDestroyable()){
                combo++;
                cellRemovalProcess();
            }else{
                combo = 1;
                //tell front that we can now accept input again
                EventBus.getDefault().post(new ToggleBlockingEvent());
                EventBus.getDefault().post(new UnRegisterEvent("all"));
            }
        }
    }

}
