package berry_keiran.catcafe;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


public class Cat extends FloorArea {

    private int catAge = 52;
    private int countdown = 8;
    private PropertyChangeSupport subject;

    public Cat() {
        this.weeklyCost = 30;
        this.initialCost = 200;
        this.name = "-C-";
        this.subject = new PropertyChangeSupport(this);
    }

    public int getCatAge() {
        return this.catAge;
    }

    public int getCountdown() {
        return this.countdown;
    }

    public void addObserver(PropertyChangeListener listener) {
        //GRADING: 1.B SUBJECT- MODEL
        subject.addPropertyChangeListener(listener);
    }

    @Override
    public void nextWeek() {
        //increment everything
        this.totalCost += this.weeklyCost;
        this.age++;
        this.catAge++;
        this.countdown -= 1;

        //adopt a cat and add a new one
        if (this.countdown == 0) {
            this.countdown = 8;
            this.catAge = 52;
            //GRADING: 1.B TRIGGER-MODEL
            this.subject.firePropertyChange("adopted", 0, 1);
        }

        //update countdown on the tile
        this.subject.firePropertyChange("countdown", null, this.countdown);

    }

}
