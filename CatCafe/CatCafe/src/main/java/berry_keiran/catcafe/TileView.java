package berry_keiran.catcafe;

import javafx.scene.control.Button;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Objects;

public class TileView extends Button implements PropertyChangeListener {

    private Tile tile;

    public TileView(Tile tile) {
        this.tile = tile;
        this.tile.addObserver(this);
        setProperties(tile);
    }

    private void setProperties(Tile tile) {
        setText(getTileText(tile));
        setTextAlignment(TextAlignment.CENTER);
        setWrapText(true);
        setColor();
    }

    private void setColor() {
        if (this.tile.getFloorArea() instanceof Empty) {
            setStyle("-fx-background-color: #FCF7DE" );
        }
        else if (this.tile.getFloorArea() instanceof Table) {
            setStyle("-fx-background-color: #FDDFDF");
        }
        else if (this.tile.getFloorArea() instanceof Cat) {
            setStyle("-fx-background-color: #DEFDE0");
        }
        else if (this.tile.getFloorArea() instanceof Kitten) {
            setStyle("-fx-background-color: #DEF3FD");
        }

    }

    private String getTileText(Tile tile) {
        FloorArea floorArea = tile.getFloorArea();
        String text = floorArea.name + '\n';

        if (floorArea instanceof Empty) {
            text = text +  "-$" + floorArea.weeklyCost;
        } else if (floorArea instanceof Table) {
            text = text + "-$" + floorArea.weeklyCost + "\n $" + ((Table) floorArea).getWeeklyRevenue();
        } else if (floorArea instanceof Cat) {
            text = text + "-$" + floorArea.weeklyCost + "\n" + ((Cat) floorArea).getCountdown();
        } else if (floorArea instanceof Kitten) {
            text = text + "-$" + floorArea.weeklyCost + "\n" + ((Kitten) floorArea).getCountdown();
        }

        return text;
    }

    //GRADING: OBSERVE
    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        if (Objects.equals(evt.getPropertyName(), "floorArea")) {
            if (this.tile.getFloorArea() instanceof Cat) {
                ((Cat) tile.getFloorArea()).addObserver(this);
            }

            if (this.tile.getFloorArea() instanceof Kitten) {
                ((Kitten) tile.getFloorArea()).addObserver(this);
            }
        }
        else if (Objects.equals(evt.getPropertyName(), "addBorder")) {
            this.setBorder(new Border(new BorderStroke(
                    Color.BLACK,
                    BorderStrokeStyle.SOLID,
                    null,
                    null
            )));
        }
        else if (Objects.equals(evt.getPropertyName(), "removeBorder")) {
            this.setBorder(null);
        }

        setProperties(this.tile);
    }
}