package berry_keiran.catcafe;

import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;

import java.util.Objects;

public class Controller {

    private CafeSim cafeSim;
    private Tile lastTile;

    Controller(CafeSim cafeSim) {
        this.cafeSim = cafeSim;
    }

    public void setCafeSim(CafeSim cafeSim) {
        this.cafeSim = cafeSim;
    }

    public void nextWeek() {
        this.cafeSim.nextWeek();
        this.setCafeInfo(this.cafeSim);
        this.setTileDetails(this.lastTile);
    }

    public void handleTileViewClick(int row, int col) {
        this.updateTile(row, col);
    }

    public void updateTile(int row, int col) {
        //update the tile which was clicked to be the new value
        //also set the funds of the sim and total cost of the tiles
        String type = ((RadioButton)(Layout.toggleGroup.getSelectedToggle())).getText();

        if (Objects.equals(type, "Table")) {
            cafeSim.getTile(row, col).setFloorArea(new Table());
            cafeSim.setFunds(cafeSim.getFunds() - cafeSim.getTile(row, col).getFloorArea().initialCost);
            cafeSim.getTile(row, col).getFloorArea().totalCost += cafeSim.getTile(row, col).getFloorArea().initialCost;
        }
        else if (Objects.equals(type, "Kitten")) {
            cafeSim.getTile(row, col).setFloorArea(new Kitten());
            cafeSim.setFunds(cafeSim.getFunds() - cafeSim.getTile(row, col).getFloorArea().initialCost);
            cafeSim.getTile(row, col).getFloorArea().totalCost += cafeSim.getTile(row, col).getFloorArea().initialCost;
        }
        else if (Objects.equals(type, "Cat")) {
            cafeSim.getTile(row, col).setFloorArea(new Cat());
            cafeSim.setFunds(cafeSim.getFunds() - cafeSim.getTile(row, col).getFloorArea().initialCost);
            cafeSim.getTile(row, col).getFloorArea().totalCost += cafeSim.getTile(row, col).getFloorArea().initialCost;
        }
        else if (Objects.equals(type, "Empty")){
            FloorArea lastFloorArea = cafeSim.getTile(row, col).getFloorArea();
            cafeSim.getTile(row, col).setFloorArea(new Empty());

            if (!(lastFloorArea instanceof Empty)) {
                cafeSim.setFunds(cafeSim.getFunds() - cafeSim.getTile(row, col).getFloorArea().initialCost);
                cafeSim.getTile(row, col).getFloorArea().totalCost += cafeSim.getTile(row, col).getFloorArea().initialCost;
            }
        }
        else {
            setTileDetails(cafeSim.getTile(row, col));
        }

        //if looking at the same tile that is being displayed, update the display
        if (cafeSim.getTile(row, col) == this.lastTile) {
            setTileDetails(cafeSim.getTile(row, col));
        }
        setCafeInfo(cafeSim);
    }

    public void setTileDetails(Tile tile) {
        //update the display for the tile

        //remove the border from the last tile which was displayed
        if (this.lastTile != null) {
            this.lastTile.removeBorderFromView();
        }
        this.lastTile = tile;
        //add the border to the new tile which is being displayed
        this.lastTile.addBorderToView();

        Layout.tileDetails.getChildren().clear();

        if (tile.getFloorArea() instanceof Empty) {
            Label type = new Label("Empty");
            Label floorChanged = new Label("Floor Changed: " + Integer.toString(tile.timesChanged));
            Label floorAge = new Label("Floor Age: " + Integer.toString(tile.lastChanged));
            Label totalCost = new Label("Total Cost: $" + Integer.toString(tile.getFloorArea().totalCost));
            Layout.tileDetails.getChildren().setAll(type, floorChanged, floorAge, totalCost);
        }
        else if (tile.getFloorArea() instanceof Table) {
            Label type = new Label("Table");
            Label floorChanged = new Label("Floor Changed: " + Integer.toString(tile.timesChanged));
            Label floorAge = new Label("Floor Age: " + Integer.toString(tile.lastChanged));
            Label totalCost = new Label("Total Cost: $" + Integer.toString(tile.getFloorArea().totalCost));
            Label totalRevenue = new Label("Total Revenue: $" + Integer.toString(((Table) tile.getFloorArea()).totalRevenue));
            Layout.tileDetails.getChildren().setAll(type, floorChanged, floorAge, totalCost, totalRevenue);
        }
        else if (tile.getFloorArea() instanceof Cat) {
            Label type = new Label("Cat");
            Label floorChanged = new Label("Floor Changed: " + Integer.toString(tile.timesChanged));
            Label floorAge = new Label("Floor Age: " + Integer.toString(tile.lastChanged));
            Label totalCost = new Label("Total Cost: $" + Integer.toString(tile.getFloorArea().totalCost));
            Label catAge = new Label("Cat Age: " + Integer.toString(((Cat) tile.getFloorArea()).getCatAge()));
            Label weeksUntilAdoption = new Label("Weeks until adoption: " + Integer.toString(((Cat) tile.getFloorArea()).getCountdown()));
            Layout.tileDetails.getChildren().setAll(type, floorChanged, floorAge, totalCost, catAge, weeksUntilAdoption);
        }
        else if (tile.getFloorArea() instanceof Kitten) {
            Label type = new Label("Kitten");
            Label floorChanged = new Label("Floor Changed: " + Integer.toString(tile.timesChanged));
            Label floorAge = new Label("Floor Age: " + Integer.toString(tile.lastChanged));
            Label totalCost = new Label("Total Cost: $" + Integer.toString(tile.getFloorArea().totalCost));
            Label catAge = new Label("Cat Age: " + Integer.toString(((Kitten) tile.getFloorArea()).getKittenAge()));
            Label weeksUntilAdoption = new Label("Weeks until adoption: " + Integer.toString(((Kitten) tile.getFloorArea()).getCountdown()));
            Layout.tileDetails.getChildren().setAll(type, floorChanged, floorAge, totalCost, catAge, weeksUntilAdoption);
        }
    }

    public void setCafeInfo(CafeSim cafeSim) {
        Label week = new Label("Week: " + Integer.toString(cafeSim.getWeek()));
        Label filled = new Label("Filled: " + Integer.toString(cafeSim.filledTiles()));
        Label funds = new Label("Funds: $" + Integer.toString(cafeSim.getFunds()));
        Label adopted = new Label("Adopted: " + Integer.toString(cafeSim.getAdopted()));

        Layout.cafeInfo.getChildren().setAll(week, filled, funds, adopted);
    }
}
