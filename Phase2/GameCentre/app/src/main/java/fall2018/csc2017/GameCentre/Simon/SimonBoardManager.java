package fall2018.csc2017.GameCentre.Simon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import fall2018.csc2017.GameCentre.BoardManager;

public class SimonBoardManager extends BoardManager<SimonTilesBoard>{
    /**
     * How many undos a user has in a game
     */
    private int undo;
    /**
     * The gameQueue for this instance of the game
     */
    private GameQueue<SimonTile> gameQueue;

    /**
     * Instantiates a SimonBoardManager object
     * @param board - the board for the game
     * @param undo - number of undos user has per game
     */
    public SimonBoardManager(SimonTilesBoard board, int undo) {
        super(board);
        this.undo = undo;
        this.gameQueue = new GameQueue<>();
    }

    public SimonBoardManager(int dimension, int undo) {
        List<SimonTile> tilesList = new ArrayList<>();
        final int numTiles = dimension * dimension;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tilesList.add(new SimonTile(tileNum + 1));
        }
        SimonTilesBoard simonTilesBoard = new SimonTilesBoard(dimension, tilesList);
        setBoard(simonTilesBoard);
        //TODO: Add something to do with the undo. create a stack?
    }


    /**
     * Returns the game queue for this object
     * @return
     */
    public GameQueue<SimonTile> getGameQueue(){
        return this.gameQueue;
    }

    @Override
    public int calculateScore(int moves) {
        return moves * 10;
    }

    /**
     * Returns a random tile from all tiles in order to display it
     * @return
     */
    SimonTile randomizer() {
        ArrayList<ArrayList<SimonTile>> simonList = this.getBoard().getAllTiles();
        Random rand = new Random();
        int num = 0;
        for (ArrayList<SimonTile> tiles : simonList) {
            num = num + tiles.size();
        }
        int randNum = rand.nextInt(num);
        int index = 0;
        while (randNum >= simonList.get(index).size() ){
            randNum = randNum - simonList.get(index).size();
            index++;
        }
        return simonList.get(index).get(randNum);
    }
}

