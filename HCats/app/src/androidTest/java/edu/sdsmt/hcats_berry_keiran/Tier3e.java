package edu.sdsmt.hcats_berry_keiran;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.widget.Button;
import android.widget.TextView;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import androidx.test.platform.app.InstrumentationRegistry;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class Tier3e {

    public static final int MOVE_START = 15;
    public static final int TREAT_START = 3;
    public static final int CAT_START = 40;
    public static final int CAT_START_PER_CELL = 5;
    public static final int GRID_SIZE = 3;

    public static final int CAT_INITIAL_MOVE = 3;
    public static final int  CAT_INITIAL_PERCENT = 60;

    public static final int CAT_MID_MOVE = 2;
    public static final int  CAT_MID_PERCENT =50;

    public static final int CAT_LOW_MOVE = 1;
    public static final int  CAT_LOW_PERCENT =25;

    public static final int CAT_TREAT_MOVE = 5;
    public static final int  CAT_TREAT_PERCENT = 75;

    //thresholds

    @Rule
    public ActivityScenarioRule<MainActivity> act = new ActivityScenarioRule<>(MainActivity.class);

    private Game g;
    private StateMachine sm;
    private MainActivity mainAct;
    private Button treatBtn;
    private Button resetBtn;
    private Button downBtn;
    private Button rightBtn;
    private TextView treatView;
    private TextView moveView;
    private TextView caughtView;

    private FloatingActionButton colorBtn;
    private FloatingActionButton redBtn;

    //Initialize to have access to underlying game, state machine, and buttons
    private void init(){
        AtomicReference<Game> gameAtom = new AtomicReference<>();
        AtomicReference<StateMachine> smAtom = new AtomicReference<>();
        AtomicReference<Button> treatBtnAtom = new AtomicReference<>();
        AtomicReference<Button> resetBtnAtom = new AtomicReference<>();
        AtomicReference<Button> downBtnAtom = new AtomicReference<>();
        AtomicReference<Button> rightBtnAtom = new AtomicReference<>();
        AtomicReference<TextView> treatAtom = new AtomicReference<>();
        AtomicReference<TextView> moveAtom = new AtomicReference<>();
        AtomicReference<TextView> caughtAtom = new AtomicReference<>();
        AtomicReference<FloatingActionButton> colorAtom = new AtomicReference<>();
        AtomicReference<FloatingActionButton> redAtom = new AtomicReference<>();

        act.getScenario().onActivity(act -> {
            mainAct = act;
            gameAtom.set(act.getGame());
            smAtom.set(act.getStateMachine());

            treatBtnAtom.set(act.findViewById(R.id.treatBtn));
            resetBtnAtom.set(act.findViewById(R.id.resetBtn));
            downBtnAtom.set(act.findViewById(R.id.downBtn));
            rightBtnAtom.set(act.findViewById(R.id.rightBtn));

            treatAtom.set(act.findViewById(R.id.treats));
            moveAtom.set(act.findViewById(R.id.moves));
            caughtAtom.set(act.findViewById(R.id.caught));

            colorAtom.set(act.findViewById(R.id.editColors));
            redAtom.set(act.findViewById(R.id.redFloatingButton));
        });

        g = gameAtom.get();
        sm = smAtom.get();
        treatBtn=treatBtnAtom.get();
        resetBtn=resetBtnAtom.get();
        downBtn=downBtnAtom.get();
        rightBtn=rightBtnAtom.get();

        treatView = treatAtom.get();
        moveView = moveAtom.get();
        caughtView = caughtAtom.get();

        colorBtn = colorAtom.get();
        redBtn = redAtom.get();
    }

    @Test
    public void a_checkInitialState() {
        init();
        //check portrait values
        assertEquals(HighCats.class.getName(), sm.getCurrentStateName());
        helperCheckStartingGameValues();
        checkNumberMatch();
        //switch to landscape
        mainAct.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();
        init();
        //check landscape values
        assertEquals(HighCats.class.getName(), sm.getCurrentStateName());
        helperCheckStartingGameValues();
        checkNumberMatch();
    }

    public void checkNumberMatch(){
        assertEquals(g.getTreats(), Integer.parseInt(treatView.getText().toString()));
        assertEquals(g.getMoves(), Integer.parseInt(moveView.getText().toString()));
        assertEquals(g.getCatsCaught(), Integer.parseInt(caughtView.getText().toString()));
    }


    @Test
    public void b_checkColorPersists(){
        init();
        onView(withId(R.id.editColors)).perform(click());
        onView(withId(R.id.redFloatingButton)).perform(click());
        assertEquals(g.getGameColor(), Color.RED);
        //switch to landscape
        mainAct.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();
        init();
        assertEquals(g.getGameColor(), Color.RED);
    }

    @Test
    public void c_checkAllValuesPersist(){
        init();
        onView(withId(R.id.treatBtn)).perform(click());
        //make sure treat doesn't do anything yet
        assertEquals(TREAT_START, Integer.parseInt(treatView.getText().toString()));
        onView(withId(R.id.downBtn)).perform(click());
        onView(withId(R.id.downBtn)).perform(click());
        onView(withId(R.id.rightBtn)).perform(click());
        onView(withId(R.id.rightBtn)).perform(click());
        onView(withId(R.id.treatBtn)).perform(click());
        onView(withId(R.id.downBtn)).perform(click());
        onView(withId(R.id.rightBtn)).perform(click());
        helperCheckGridValuesForC();
        assertEquals(LowCats.class.getName(), sm.getCurrentStateName());
        assertEquals(TREAT_START - 1, Integer.parseInt(treatView.getText().toString()));
        assertEquals(MOVE_START - 7, Integer.parseInt(moveView.getText().toString()));
        assertEquals(25, Integer.parseInt(caughtView.getText().toString()));

        //switch to landscape
        mainAct.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();
        init();
        helperCheckGridValuesForC();
        assertEquals(LowCats.class.getName(), sm.getCurrentStateName());
        assertEquals(TREAT_START - 1, Integer.parseInt(treatView.getText().toString()));
        assertEquals(MOVE_START - 7, Integer.parseInt(moveView.getText().toString()));
        assertEquals(25, Integer.parseInt(caughtView.getText().toString()));
    }

    private void helperCheckGridValuesForC(){
        assertEquals( 0, g.getCatsAt(0,0));
        assertEquals( 0, g.getCatsAt(0,1));
        assertEquals( 2, g.getCatsAt(0,2));
        assertEquals( 0, g.getCatsAt(1,0));
        assertEquals( 0, g.getCatsAt(1,1));
        assertEquals( 10, g.getCatsAt(1,2));
        assertEquals( 0, g.getCatsAt(2,0));
        assertEquals( 3, g.getCatsAt(2,1));
    }

    private void helperCheckStartingGameValues(){
        assertEquals(MOVE_START, g.getMoves());
        assertEquals(TREAT_START, g.getTreats());
        assertEquals(CAT_START, g.getTotalCatsLeft());
        assertEquals(0, g.getCatsCaught());
        assertEquals(MOVE_START, g.getMoves());
        assertEquals(GRID_SIZE, g.getWidth());
        assertEquals(GRID_SIZE, g.getHeight());
    }

}