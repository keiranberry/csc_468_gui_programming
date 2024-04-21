package edu.sdsmt.hcats_berry_keiran;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

public class EndedState extends State {

    public EndedState (StateMachine stateMachine, Game game, MainActivity mainActivity) {
        super(stateMachine, game, mainActivity);
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

    @Override
    public void onEntry() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mainActivity);
        if (this.game.isWon()) {
            builder.setTitle("You Win!");
            builder.setMessage("You caught all the cats!");
        } else {
            builder.setTitle("You Lost!");
            builder.setMessage("You ran out of moves...");
        }
        builder.setPositiveButton("Reset", (dialog, which) -> {
            this.game.reset();
            this.mainActivity.updateViews();
            dialog.dismiss();
            checkTransition();
        });
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        //GRADING: DIALOG
        dialog.show();
    }
    private void checkTransition() {
        //GRADING: RESET
        this.stateMachine.setState(new HighCats(this.stateMachine, this.game, this.mainActivity));
    }
}
