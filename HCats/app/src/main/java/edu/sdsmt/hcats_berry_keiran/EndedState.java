package edu.sdsmt.hcats_berry_keiran;

public class EndedState extends State {

    public EndedState (StateMachine stateMachine, Game game) {
        super(stateMachine, game);
    }

    @Override
    public void sweepRight() {
        //does nothing in this state
    }

    @Override
    public void sweepDown() {
        //does nothing in this state
    }

    @Override
    public void useTreat() {
        //does nothing in this state
    }

    private void checkTransition() {
        this.stateMachine.setState(new HighCats(this.stateMachine, this.game));
    }
}
