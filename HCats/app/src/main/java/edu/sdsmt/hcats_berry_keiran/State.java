package edu.sdsmt.hcats_berry_keiran;

public abstract class State {
    protected Game game;

    public State(Game game) {
        this.game = game;
    }

    public abstract void enterState();
    public abstract void exitState();
    public abstract void handleButtonPress();
    public abstract void doMaintenanceTask(double delta);
}
