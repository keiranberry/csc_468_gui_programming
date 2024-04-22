package edu.sdsmt.hcats_berry_keiran;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.core.content.ContextCompat;
import android.graphics.drawable.LayerDrawable;


public class TreatActive extends State {
    private static final int MOVE_AMOUNT = 5;
    private static final int MOVE_PERCENT = 75;

    private GameAreaView gameAreaView;


    public TreatActive(StateMachine stateMachine, Game game, MainActivity mainActivity) {
        super(stateMachine, game, mainActivity);
        this.gameAreaView = mainActivity.findViewById(R.id.gameArea);
    }

    @Override
    public void sweepRight() {
        //GRADING: SWEEP
        this.game.sweepRight(MOVE_AMOUNT, MOVE_PERCENT);
        checkTransition();
    }

    @Override
    public void sweepDown() {
        this.game.sweepDown(MOVE_AMOUNT, MOVE_PERCENT);
        checkTransition();
    }

    @Override
    public void useTreat() {
        //does nothing in this state
    }

    @Override
    public void onEntry() {
        this.gameAreaView.setBorderColor(Color.RED);
    }

    @Override
    public void onExit() {
        this.gameAreaView.setBorderColor(Color.BLACK);
    }

    private void checkTransition() {
        if (this.game.getMoves() < 0 || this.game.getCatsCaught() >= 40){
            this.stateMachine.setState(new EndedState(this.stateMachine, this.game, this.mainActivity));
        }
        else if (this.game.getCatsCaught() > 20){
            //GRADING: TO_NO_TREAT
            this.stateMachine.setState(new LowCats(this.stateMachine, this.game, this.mainActivity));
        }
        else if (this.game.getCatsCaught() > 10){
            //GRADING: TO_NO_TREAT
            this.stateMachine.setState(new MidCats(this.stateMachine, this.game, this.mainActivity));
        }
    }
}
