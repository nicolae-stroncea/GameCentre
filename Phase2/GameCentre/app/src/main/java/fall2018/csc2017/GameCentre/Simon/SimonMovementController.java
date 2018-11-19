package fall2018.csc2017.GameCentre.Simon;

import android.content.Context;

import java.util.Queue;
import fall2018.csc2017.GameCentre.MoveTracker;
import fall2018.csc2017.GameCentre.MovementControllers.MovementControllerSimplePress;
import fall2018.csc2017.GameCentre.Score.ScoreScreenActivity;

public class SimonMovementController extends MovementControllerSimplePress {

    private MoveTracker moves;
    SimonBoardManager simonBoardManager = (SimonBoardManager) getBoardManager();


    SimonMovementController(SimonBoardManager boardManager){
        moves = new MoveTracker(boardManager.getScore());
        setBoardManager(boardManager);
    }

    @Override
    public void processMove(Context context, int position) {
        SimonTile tile = getTileInPosition(position);

        if (isCorrectMove(tile)){
            if (isRoundFinished(simonBoardManager.getGameQueue())) {
                // TODO add instructions to repopulate the stack with more elements than previous round

            }
        }
        else{
            //This means user lost.
            int score = getBoardManager().calculateScore(moves.getMoves());
            moveOnToScoreActivity(context, "Simon.txt", ScoreScreenActivity.class, score);
        }

    }

    //I think I have implemented this correctly
    boolean isCorrectMove(SimonTile userTile){
        Queue<SimonTile> gameQueue = simonBoardManager.getGameQueue();
        SimonTile tileAtFront = gameQueue.remove();
        return tileAtFront.compareTo(userTile) == 0;
    }

    //I think I have also successfully implemented. Have to test.
    boolean isRoundFinished(Queue gameQueue){
        return gameQueue.isEmpty();
    }


    /**
     * Returns the SimonTile object in that position
     * @param position in board
     * @return SimonTile
     */
    private SimonTile getTileInPosition(int position) {
        int row = getBoardManager().getRow(position);
        int col = getBoardManager().getCol(position);
        SimonTile tile = (SimonTile) getBoardManager().getBoard().getTile(row,col);
        return tile;
    }
}
