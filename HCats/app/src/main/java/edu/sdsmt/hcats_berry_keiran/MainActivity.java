package edu.sdsmt.hcats_berry_keiran;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.TextView;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    private StateMachine stateMachine;
    private Game game;
    private GameAreaView gameAreaView;

    private TextView movesTextView;
    private TextView caughtTextView;
    private TextView treatsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.game = new Game();
        this.stateMachine = new StateMachine(this.game);
        this.stateMachine.setState(new HighCats(this.stateMachine, this.game, this));
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
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

        Button resetBtn = findViewById(R.id.resetBtn);
        Button treatBtn = findViewById(R.id.treatBtn);
        Button downBtn = findViewById(R.id.downBtn);
        Button rightBtn = findViewById(R.id.rightBtn);

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
    }

    public void updateViews() {
        movesTextView.setText(String.valueOf(game.getMoves()));
        caughtTextView.setText(String.valueOf(game.getCatsCaught()));
        treatsTextView.setText(String.valueOf(game.getTreats()));
        this.gameAreaView.invalidate();
    }

    public StateMachine getStateMachine() {
        return this.stateMachine;
    }

    public Game getGame() {
        return this.game;
    }
}
