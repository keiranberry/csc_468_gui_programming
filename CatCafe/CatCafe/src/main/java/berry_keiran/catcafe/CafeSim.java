package berry_keiran.catcafe;

import javafx.scene.layout.GridPane;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import java.beans.PropertyChangeListener;
import java.util.Objects;

public class CafeSim extends GridPane implements PropertyChangeListener {

    private ArrayList<ArrayList<Tile>> tiles;
    private int week = 0;
    private int adopted = 0;
    private int funds = 0;


    public CafeSim(int size) {
        this.fillTiles(size);
    }

    public int getWeek() {
        return this.week;
    }

    public int getAdopted() {
        return this.adopted;
    }

    public int getFunds() {
        return this.funds;
    }

    public void setFunds(int funds) {
        this.funds = funds;
    }

    private void fillTiles(int size) {
        this.tiles = new ArrayList<>();
        ArrayList<Tile> row;
        for (int i = 0; i < size; i++) {
            row = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                row.add(new Tile(new Empty()));
            }
            this.tiles.add(row);
        }
    }

    public Tile getTile(int row, int col) {
        return this.tiles.get(row).get(col);
    }

    public int filledTiles() {
        int count = 0;

        for (int i = 0; i < this.tiles.size(); i++) {
            for (int j = 0; j < this.tiles.size(); j++) {
                if (!(this.tiles.get(i).get(j).getFloorArea() instanceof Empty)) {
                    count++;
                }
            }
        }

        return count;
    }



    public void nextWeek() {

        for (List<Tile> tileRow : tiles) {
            for (Tile tile : tileRow) {
                //if the floor area is a cat or kitten, add an observer to listen for adoption
                if (tile.getFloorArea() instanceof Cat && tile.getFloorArea().age == 0) {
                    ((Cat) tile.getFloorArea()).addObserver(this);
                }
                if (tile.getFloorArea() instanceof Kitten && tile.getFloorArea().age == 0) {
                    ((Kitten) tile.getFloorArea()).addObserver(this);
                }

                //if its a table we need to ad the revenue
                if (tile.getFloorArea() instanceof Table) {
                    this.funds += ((Table) tile.getFloorArea()).getWeeklyRevenue();
                }

                //subtract the weekly cost
                this.funds -= tile.getFloorArea().weeklyCost;
                //update each of the tiles to next week
                tile.nextWeek();
            }
        }
        this.week++;
    }

    //GRADING: 1.B OBSERVER-MODEL
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (Objects.equals(evt.getPropertyName(), "adopted")) {
            this.adopted++;
        }
    }
}

