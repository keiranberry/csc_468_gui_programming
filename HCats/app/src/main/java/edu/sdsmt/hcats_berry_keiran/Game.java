package edu.sdsmt.hcats_berry_keiran;

public class Game {
    private final int GRID_SIZE = 3;
    private final int STARTING_CATS = 40;
    private int moves = 15;
    private int catsCaught = 0;
    private int totalCatsLeft = 40;
    private int treats = 3;
    private Boolean treatActive = false;

    private int[][] grid = {
            {5, 5, 5},
            {5, 5, 5},
            {5, 5, 0}
    };

    public int getMoves() {
        return this.moves;
    }

    public int getCatsCaught() {
        return this.catsCaught;
    }

    public int getTotalCatsLeft() {
        return this.totalCatsLeft;
    }

    public int getTreats() {
        return this.treats;
    }

    public int getWidth(){
        return this.grid.length;
    }

    public int getHeight(){
        return this.grid[0].length;
    }

    public int getCatsAt(int row, int col) {
        if(row < GRID_SIZE && row >= 0 && col < GRID_SIZE && col >= 0) {
            return this.grid[row][col];
        }
        else {
            return -1;
        }
    }

    public void sweepRight(int numberToMove, int percentToMove){
        double percentNumber = (double) percentToMove / 100;
        for(int i = GRID_SIZE - 2; i >= 0; i--){
            for(int j = 0; j < GRID_SIZE; j++){
                int numberFromPercent = (int) Math.floor(percentNumber * this.grid[i][j]);
                int finalNumber = Math.max(numberToMove, numberFromPercent);
                finalNumber = Math.min(finalNumber, this.grid[i][j]);
                this.grid[i][j] -= finalNumber;
                this.grid[i + 1][j] += finalNumber;
            }
        }
        this.totalCatsLeft = STARTING_CATS - this.grid[GRID_SIZE - 1][GRID_SIZE - 1];
        this.catsCaught = this.grid[GRID_SIZE - 1][GRID_SIZE - 1];
        this.moves--;
        this.treatActive = false;
        System.out.println("number: " + numberToMove);
        System.out.println("percent: " + percentToMove);
    }

    public void sweepDown(int numberToMove, int percentToMove){
        double percentNumber = (double) percentToMove / 100;
        for(int i = 0; i < GRID_SIZE; i++){
            for(int j = GRID_SIZE - 2; j >= 0; j--){
                int numberFromPercent = (int) Math.floor(percentNumber * this.grid[i][j]);
                int finalNumber = Math.max(numberToMove, numberFromPercent);
                finalNumber = Math.min(finalNumber, this.grid[i][j]);
                this.grid[i][j] -= finalNumber;
                this.grid[i][j + 1] += finalNumber;
            }
        }
        this.totalCatsLeft = STARTING_CATS - this.grid[GRID_SIZE - 1][GRID_SIZE - 1];
        this.catsCaught = this.grid[GRID_SIZE - 1][GRID_SIZE - 1];
        this.moves--;
        this.treatActive = false;
    }

    public void useTreat(){
        if(this.treats > 0){
            this.treats--;
            this.moves--;
            this.treatActive = true;
        }
    }

    public Boolean isWon(){
        return this.catsCaught >= STARTING_CATS;
    }

    public Boolean isLost(){
        return this.moves < 0;
    }

    public void reset(){
        this.moves = 15;
        this.catsCaught = 0;
        this.totalCatsLeft = 40;
        this.treats = 3;
        this.treatActive = false;

        this.grid = new int[][]{
                {5, 5, 5},
                {5, 5, 5},
                {5, 5, 0}
        };
    }
}
