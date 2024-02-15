package berry_keiran.catcafe;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


public class Kitten extends FloorArea {

    private int kittenAge = 10;
    private int countdown = 4;
    private PropertyChangeSupport subject;

    public Kitten() {
        this.weeklyCost = 20;
        this.initialCost = 200;
        this.name = "-K-";
        this.subject = new PropertyChangeSupport(this);
    }

    public int getKittenAge() {
        return this.kittenAge;
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
        this.totalCost += this.weeklyCost;
        this.age++;
        this.kittenAge++;
        this.countdown -= 1;

        //adoption, put in a new cat
        if (this.countdown == 0) {
            this.countdown = 4;
            this.kittenAge = 10;
            //GRADING: 1.B TRIGGER-MODEL
            this.subject.firePropertyChange("adopted", 0, 1);
        }

        //update the countdown on the tileview
        this.subject.firePropertyChange("countdown", null, this.countdown);
    }

}
