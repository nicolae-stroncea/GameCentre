package fall2018.csc2017.GameCentre;

import java.io.Serializable;
import java.util.List;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
class SlidingBoardManager extends BoardManager<SlidingTilesBoard> implements Serializable, Undoable {

    /**
     * The stack which remembers the moves on the board.
     */
    private UndoStack stack;
    /**
     * Manage a board that has been pre-populated.
     *
     */
//    SlidingBoardManager(SlidingTilesBoard board) {
//        this.board = board;
//        this.stack = new UndoStack(3);
//    }
    public UndoStack getStack(){
        return stack;
    }

    /**
     * Manage a new shuffled board.
     */
    //TODO Notice how  the board is created from the tiles created in Super.
    SlidingBoardManager(int undoMax, SlidingTilesBoard board ) {
        super(board);
        this.stack = new UndoStack(undoMax);
    }

    /**
     * Undo the most recent move. If undo is pressed again, undo the next most recent move.
     * Continue as long as moves are recorded.
     * @return whether a move has been undone
     */
    public boolean undo() {
        if (this.stack.getSize() == 0) {
            return false;
        } else {
            int[] lst = (int[]) this.stack.pop();
            getBoard().swapTiles(lst[0], lst[1], lst[2], lst[3]);
            return true;
        }
    }


    /**
     * Returns score based on dimensions of puzzle
     *
     * @param moves number of moves
     * @return total score
     */
    @Override
    int calculateScore(int moves) {
        int dimensions = getBoard().getDimension();
        return dimensions * 500 - (moves * 5);
    }




}
