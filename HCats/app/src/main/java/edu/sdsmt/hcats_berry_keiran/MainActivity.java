/*
____ 	Pulled the most recent unit tests at submission time, and ensure they still pass
____	    All grading tags added if the tier was reached


Tier 1: Model		42
_X__	Unit Tests all pass

Tier 2: Connect Views		16
_X__	Unit Tests all pass

Tier 3a: State Machine/Event Rules *	36
_X__	Framework there
_X__	Unit Tests all pass

Tier 3b: Floating Action		18
_X__	All buttons there
_X__	Icons set and distinguishable
_X__	Opens/closes properly
_X__	Player color updated.

Tier 3c: Layout **	(-50% each line if fails in on orientation)	26
_X__	Custom’s View’s aspect ratio constant
_X__	Relative size of objects in view maintained
_X__	Works in required screen sizes

Tier 3d: Rotation		20
_X__	Required state saved on rotation

Tier 3e: Unit Test		10
_X__	Test there
_X__	Updated all values to support check
_X__	Triggered rotation
_X__	Checked all values after rotation (does NOT require passing since those points are in 3d)

Tier 4: Extensions		30
Extension 1: 1k 5pt Red border around gameArea if a treat is active:
    Whenever a treat is active, the borders around the play area (usually black) turn red. To test,
    just use a treat in a state which allows treats! This is done with the entry and exit functions
    of the treatActive state. This persists between orientations.
Extension 2: 1d 5pt Add another player appearance option: I added a fourth color, blue, to the
    player appearance options. It can be found in the floating menu the same as the others, and also
    persists between portrait and landscape as it should.
Extension 3: 5a 5pt Disable treat button if there are none available: the treat button disables
    if the number of treats left is 0 or less. This is done in the state machine classes.
Extension 4: 4a 10pt Control the activation and deactivation of the treat button with the state machine:
    In the TreatActive state's entry and exit, the button for treats is disabled or enabled accordingly.
    This can be tested by using a treat. Also, the button is disabled in HighCats.
Extension 5: 1a 5pt Improve the background of the app: I created a gradient for the background of the
    application, which can be seen from both orientations. The gradient turns with the screen, so that
    the text is always contrasting the lighter gradient behind it.
*/

package edu.sdsmt.hcats_berry_keiran;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.widget.TextView;
import android.widget.Button;
import android.content.res.Configuration;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity {
    private StateMachine stateMachine;
    private Game game;
    private GameAreaView gameAreaView;

    private TextView movesTextView;
    private TextView caughtTextView;
    private TextView treatsTextView;
    private boolean isFABOpen = false;
    private FloatingActionButton redBtn;
    private FloatingActionButton purpleBtn;
    private FloatingActionButton blackBtn;
    private FloatingActionButton blueBtn;
    private static final String GAME_STATE = "game_state";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            this.game = savedInstanceState.getParcelable(GAME_STATE);
            if (this.game != null) {
                this.stateMachine = new StateMachine(this.game);
                this.stateMachine.setState(new HighCats(this.stateMachine, this.game, this));
            }
        } else {
            this.game = new Game();
            this.stateMachine = new StateMachine(this.game);
            this.stateMachine.setState(new HighCats(this.stateMachine, this.game, this));
        }
        this.gameAreaView = findViewById(R.id.gameArea);
        this.gameAreaView.setGame(this.game);
        setupViews();
        updateViews();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setupViews() {
        movesTextView = findViewById(R.id.moves);
        caughtTextView = findViewById(R.id.caught);
        treatsTextView = findViewById(R.id.treats);

        redBtn = findViewById(R.id.redFloatingButton);
        purpleBtn = findViewById(R.id.purpleFloatingButton);
        blackBtn = findViewById(R.id.blackFloatingButton);
        blueBtn = findViewById(R.id.blueFloatingButton);
        FloatingActionButton colorBtn = findViewById(R.id.editColors);
        colorBtn.bringToFront();

        Button resetBtn = findViewById(R.id.resetBtn);
        Button downBtn = findViewById(R.id.downBtn);
        Button rightBtn = findViewById(R.id.rightBtn);
        Button treatBtn = findViewById(R.id.treatBtn);

        resetBtn.setOnClickListener(v -> {
            game.reset();
            this.stateMachine.setState(new HighCats(this.stateMachine, this.game, this));
            updateViews();
        });

        treatBtn.setOnClickListener(v -> {
            this.stateMachine.useTreat();
            updateViews();
        });

        downBtn.setOnClickListener(v -> {
            this.stateMachine.sweepDown();
            updateViews();
        });

        rightBtn.setOnClickListener(v -> {
            this.stateMachine.sweepRight();
            updateViews();
        });

        colorBtn.setOnClickListener(v -> onBurst());


        redBtn.setOnClickListener(v -> {
            this.gameAreaView.setThemeColor(Color.RED);
            this.game.setGameColor(Color.RED);
        });

        purpleBtn.setOnClickListener(v -> {
            this.gameAreaView.setThemeColor(Color.parseColor("#AA66CC"));
            this.game.setGameColor(Color.parseColor("#AA66CC"));;
        });

        blackBtn.setOnClickListener(v -> {
            this.gameAreaView.setThemeColor(Color.BLACK);
            this.game.setGameColor(Color.BLACK);
        });

        blueBtn.setOnClickListener(v -> {
            this.gameAreaView.setThemeColor(Color.BLUE);
            this.game.setGameColor(Color.BLUE);
        });
    }

    public void updateViews() {
        movesTextView.setText(String.valueOf(game.getMoves()));
        caughtTextView.setText(String.valueOf(game.getCatsCaught()));
        treatsTextView.setText(String.valueOf(game.getTreats()));
        this.gameAreaView.invalidate();
        this.gameAreaView.setThemeColor(this.game.getGameColor());
        if(this.game.getTreatActive()){
            this.gameAreaView.setBorderColor(Color.RED);
        }
    }

    public StateMachine getStateMachine() {
        return this.stateMachine;
    }

    public Game getGame() {
        return this.game;
    }

    public float dpToPixels(float dp){
        float pxPerDp = (float) getResources().getDisplayMetrics().densityDpi;
        pxPerDp = pxPerDp / DisplayMetrics.DENSITY_DEFAULT;
        return dp * pxPerDp;
    }

    public void onBurst(){
        if(!this.isFABOpen){
            showFABMenu();
        }
        else{
            closeFABMenu();
        }
    }

    public void showFABMenu(){
        this.isFABOpen = true;

        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            redBtn.animate().translationX(-dpToPixels(60));
            purpleBtn.animate().translationX(-dpToPixels(110));
            blackBtn.animate().translationX(-dpToPixels(160));
            blueBtn.animate().translationX(-dpToPixels(210));
        } else {
            redBtn.animate().translationY(-dpToPixels(60));
            purpleBtn.animate().translationY(-dpToPixels(110));
            blackBtn.animate().translationY(-dpToPixels(160));
            blueBtn.animate().translationY(-dpToPixels(210));
        }
        redBtn.animate().alpha(1f);
        purpleBtn.animate().alpha(1f);
        blackBtn.animate().alpha(1f);
        blueBtn.animate().alpha(1f);
    }

    public void closeFABMenu(){
        this.isFABOpen = false;
        redBtn.animate().translationX(0);
        purpleBtn.animate().translationX(0);
        blackBtn.animate().translationX(0);
        blueBtn.animate().translationX(0);
        redBtn.animate().translationY(0);
        purpleBtn.animate().translationY(0);
        blackBtn.animate().translationY(0);
        blueBtn.animate().translationY(0);
        redBtn.animate().alpha(0);
        purpleBtn.animate().alpha(0);
        blackBtn.animate().alpha(0);
        blueBtn.animate().alpha(0);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);

        bundle.putParcelable(GAME_STATE, this.game);
    }
}
