package edu.sdsmt.hcats_berry_keiran;

import android.content.Context;

public abstract class State {
    protected StateMachine stateMachine;
    protected Game game;
    protected MainActivity mainActivity;

    public State(StateMachine stateMachine, Game game, MainActivity mainActivity) {
        this.stateMachine = stateMachine;
        this.game = game;
        this.mainActivity = mainActivity;
    }

    public abstract void sweepRight();
    public abstract void sweepDown();
    public abstract void useTreat();
    public abstract void onEntry();
    public abstract void onExit();
}
