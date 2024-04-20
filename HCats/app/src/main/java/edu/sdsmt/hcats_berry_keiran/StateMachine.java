package edu.sdsmt.hcats_berry_keiran;

public class StateMachine {
    private State currentState;
    private Game game;

    public StateMachine(Game game) {
        this.game = game;
        // Initialize with the HighCats state
        currentState = new HighCatsState(game);
        currentState.enterState();
    }

    public void transitionTo(State nextState) {
        currentState.exitState();
        currentState = nextState;
        currentState.enterState();
    }

    // Add methods to handle button presses and update maintenance tasks
}
