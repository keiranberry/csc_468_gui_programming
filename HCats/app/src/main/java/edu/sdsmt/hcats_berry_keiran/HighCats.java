package edu.sdsmt.hcats_berry_keiran;

import android.widget.Button;

public class HighCats extends State {
    private static final int MOVE_AMOUNT = 3;
    private static final int MOVE_PERCENT = 50;

    public HighCats(StateMachine stateMachine, Game game, MainActivity mainActivity) {
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
        //does nothing in this state
    }

    @Override
    public void onEntry(){
        Button treatButton = this.mainActivity.findViewById(R.id.treatBtn);
        if(treatButton != null){
            treatButton.setEnabled(false);
        }
        checkTransition();
    }

    @Override
    public void onExit(){
        //does nothing in this state
    }

    private void checkTransition() {
        if (this.game.getMoves() < 0 || this.game.getCatsCaught() >= 40) {
            this.stateMachine.setState(new EndedState(this.stateMachine, this.game, this.mainActivity));
        } else if (this.game.getTreatActive()){
            this.stateMachine.setState(new TreatActive(this.stateMachine, this.game, this.mainActivity));
        } else if (this.game.getCatsCaught() > 20) {
            this.stateMachine.setState(new LowCats(this.stateMachine, this.game, this.mainActivity));
        } else if (this.game.getCatsCaught() > 10) {
            this.stateMachine.setState(new MidCats(this.stateMachine, this.game, this.mainActivity));
        }
    }
}
