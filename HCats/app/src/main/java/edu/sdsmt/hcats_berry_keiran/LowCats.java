package edu.sdsmt.hcats_berry_keiran;

public class LowCats extends State {
    private static final int MOVE_AMOUNT = 1;
    private static final int MOVE_PERCENT = 25;

    public LowCats(StateMachine stateMachine, Game game, MainActivity mainActivity) {
        super(stateMachine, game, mainActivity);
    }

    @Override
    public void sweepRight() {
        this.game.sweepRight(MOVE_AMOUNT, MOVE_PERCENT);
        checkTransition();
    }

    @Override
    public void sweepDown() {
        //GRADING: SWEEP
        this.game.sweepDown(MOVE_AMOUNT, MOVE_PERCENT);
        checkTransition();
    }

    @Override
    public void useTreat() {
        if(this.game.getTreats() > 0){
            this.game.useTreat();
            //GRADING: TO_TREAT
            this.stateMachine.setState(new TreatActive(this.stateMachine, this.game, this.mainActivity));
        }
    }

    @Override
    public void onEntry(){
        //does nothing in this state
    }

    private void checkTransition() {
        if (this.game.getMoves() < 0 || this.game.getCatsCaught() >= 40){
            this.stateMachine.setState(new EndedState(this.stateMachine, this.game, this.mainActivity));
        }
    }
}
