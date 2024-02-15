package berry_keiran.catcafe;

import javafx.geometry.*;
import javafx.scene.control.Button;
import javafx.scene.layout.*;

public class CafeSimView extends GridPane {

    private CafeSim cafeSim;
    private Controller controller;

    public CafeSimView(CafeSim cafeSim, Controller controller) {
        this.controller = controller;
        this.controller.setCafeSim(cafeSim);
        this.resize(3);
    }

    private void fillTileViews(int size) {
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++) {
                TileView tileView = new TileView(this.cafeSim.getTile(i, j));
                int row = i;
                int col = j;
                //add controller function to handle tile view being clicked
                //and assign the location, so it knows what called it
                tileView.setOnAction(event -> this.controller.handleTileViewClick(row, col));

                tileView.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                setHalignment(tileView, HPos.CENTER);
                setValignment(tileView, VPos.CENTER);

                this.add(tileView, i, j);
            }
        }



        for (int i = 0; i < size; i++) {
            //set the row and column constraints so that the sim will take up the remaining space
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setHgrow(Priority.ALWAYS);
            getColumnConstraints().add(columnConstraints);

            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setVgrow(Priority.ALWAYS);
            getRowConstraints().add(rowConstraints);
        }
    }

    public void resize(int size) {
        this.getChildren().clear();
        this.getRowConstraints().clear();
        this.getColumnConstraints().clear();
        this.cafeSim = new CafeSim(size);

        //tie the controller in with the new cafe sim that was made
        this.controller.setCafeSim(this.cafeSim);
        this.controller.setTileDetails(new Tile(new Empty()));
        this.controller.setCafeInfo(this.cafeSim);

        this.fillTileViews(size);
    }

    public void nextWeek() {
        this.controller.nextWeek();
    }
}

