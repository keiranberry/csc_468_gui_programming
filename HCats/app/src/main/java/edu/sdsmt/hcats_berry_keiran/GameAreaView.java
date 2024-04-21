package edu.sdsmt.hcats_berry_keiran;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;

public class GameAreaView extends View {
    private static final int NUM_ROWS = 3;
    private static final int NUM_COLS = 3;

    // Colors for the squares
    private static final int COLOR_WHITE = Color.WHITE;
    private int themeColor = Color.BLACK;

    private Paint borderPaint;
    private Paint fillPaint;
    private Paint textPaint;

    private int squareSize;

    private Game game;

    public GameAreaView(Context context) {
        super(context);
        init();
    }

    public GameAreaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GameAreaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setGame(Game game) {
        this.game = game;
        invalidate();
    }

    public void setThemeColor(int color){
        this.themeColor = color;
        init();
        invalidate();
    }

    private void init() {
        borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setColor(this.themeColor);
        borderPaint.setStrokeWidth(10);

        fillPaint = new Paint();
        fillPaint.setStyle(Paint.Style.FILL);

        textPaint = new Paint();
        textPaint.setColor(this.themeColor);
        textPaint.setTextSize(50);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // Update view dimensions and calculate square size
        // Dimensions of the view and each square
        squareSize = Math.min(w, h) / NUM_ROWS; // Assuming equal width and height
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        if(!isInEditMode()){
            drawGrid(canvas);
        }
    }

    private void drawGrid(Canvas canvas) {
        // Draw squares
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                int left = col * squareSize;
                int top = row * squareSize;
                int right = left + squareSize;
                int bottom = top + squareSize;

                canvas.drawRect(left, top, right, bottom, borderPaint);

                // fill every square with cat number except bottom right
                if (row != NUM_ROWS - 1 || col != NUM_COLS - 1) {
                    int cats = game.getCatsAt(col, row);
                    fillPaint.setColor(COLOR_WHITE);
                    canvas.drawRect(left, top, right, bottom, fillPaint);

                    canvas.drawText(String.valueOf(cats), left + (float) squareSize / 2, top + (float) squareSize / 2, textPaint);
                } else {
                    fillPaint.setColor(this.themeColor);
                    canvas.drawRect(left, top, right, bottom, fillPaint);
                }
            }
        }
    }
}

