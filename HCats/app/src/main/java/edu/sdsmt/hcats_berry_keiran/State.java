package edu.sdsmt.hcats_berry_keiran;

public abstract class State {
    protected StateMachine stateMachine;
    protected Game game;

    public State(StateMachine stateMachine, Game game) {
        this.stateMachine = stateMachine;
        this.game = game;
    }

    public abstract void sweepRight();
    public abstract void sweepDown();
    public abstract void useTreat();
}
