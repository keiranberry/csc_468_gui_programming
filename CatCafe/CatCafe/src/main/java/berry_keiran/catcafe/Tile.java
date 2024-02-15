package berry_keiran.catcafe;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Tile {

    private FloorArea floorArea;
    public int lastChanged = 0;
    public int timesChanged = 0;
    private PropertyChangeSupport subject;

    public Tile(FloorArea floorArea) {
        this.floorArea = floorArea;
        this.subject = new PropertyChangeSupport(this);
    }

    public void addObserver(PropertyChangeListener listener) {
        //GRADING: SUBJECT
        subject.addPropertyChangeListener(listener);
    }

    public FloorArea getFloorArea() {
        return this.floorArea;
    }

    public void setFloorArea(FloorArea floorArea) {
        FloorArea oldFloorArea = this.floorArea;
        this.floorArea = floorArea;
        this.timesChanged += 1;
        this.lastChanged = 0;
        //GRADING: TRIGGER
        this.subject.firePropertyChange("floorArea", oldFloorArea, floorArea);
    }

    public void addBorderToView() {
        this.subject.firePropertyChange("addBorder", 0, 1);
    }

    public void removeBorderFromView() {
        this.subject.firePropertyChange("removeBorder", 0, 1);
    }

    public void nextWeek() {
        this.lastChanged++;
        this.floorArea.nextWeek();
    }
}
