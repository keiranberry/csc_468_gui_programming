package edu.sdsmt.hcats_berry_keiran;

public class StateMachine {
    private State currentState;
    private Game game;

    public StateMachine(Game game) {
        this.game = game;
        this.currentState = new HighCats(this, this.game, null);
    }

    public void setState(State state) {
        this.currentState = state;
        this.currentState.onEntry();
    }

    public String getCurrentStateName(){
        return this.currentState.getClass().getName();
    }

    public void useTreat(){
        this.currentState.useTreat();
    }

    public void sweepRight(){
        System.out.println("------current state: " + this.currentState.getClass().getSimpleName());
        System.out.println("cats caught: " + this.game.getCatsCaught());
        this.currentState.sweepRight();
    }

    public void sweepDown(){
        this.currentState.sweepDown();
    }

}
