package edu.sdsmt.hcats_berry_keiran;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Game implements Parcelable {
    private final int GRID_SIZE = 3;
    private final int STARTING_CATS = 40;
    private int moves = 15;
    private int catsCaught = 0;
    private int totalCatsLeft = 40;
    private int treats = 3;
    private Boolean treatActive = false;
    private int gameColor = Color.BLACK;

    private int[][] grid = {
            {5, 5, 5},
            {5, 5, 5},
            {5, 5, 0}
    };

    public Game(){
        //initialize with default values
    }

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

    public int getGameColor(){
        return this.gameColor;
    }

    public void setGameColor(int color) {
        this.gameColor = color;
    }
    public boolean getTreatActive(){
        return this.treatActive;
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
        //start at the right col and work left
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
    }

    public void sweepDown(int numberToMove, int percentToMove){
        double percentNumber = (double) percentToMove / 100;
        //start at the bottom row and work up
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

    public static final Parcelable.Creator<Game> CREATOR = new Parcelable.Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel source) {
            return new Game(source);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };

    public Game(Parcel source) {
        this.moves = source.readInt();
        this.catsCaught = source.readInt();
        this.totalCatsLeft = source.readInt();
        this.treats = source.readInt();
        this.treatActive = source.readInt() == 1;
        this.gameColor = source.readInt();

        this.grid = new int[GRID_SIZE][GRID_SIZE];
        for (int i = 0; i < GRID_SIZE; i++) {
            source.readIntArray(grid[i]);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(this.moves);
        dest.writeInt(this.catsCaught);
        dest.writeInt(this.totalCatsLeft);
        dest.writeInt(this.treats);
        dest.writeInt(this.treatActive ? 1 : 0);
        dest.writeInt(this.gameColor);

        for (int i = 0; i < GRID_SIZE; i++) {
            dest.writeIntArray(this.grid[i]);
        }
    }
}
