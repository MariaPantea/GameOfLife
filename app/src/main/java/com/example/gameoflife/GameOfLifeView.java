package com.example.gameoflife;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Display;
import android.view.SurfaceView;
import android.view.WindowManager;

public class GameOfLifeView extends SurfaceView implements Runnable {

    public static final int DEFAULT_CELL_SIZE = 50;
    public static final int DEFAULT_ALIVE_COLOR = Color.BLACK;
    public static final int DEFAULT_DEAD_COLOR = Color.WHITE;
    private boolean isRunning = false;
    private int nColumns;
    private int nRows;
    private World world;
    private Rect r = new Rect();
    private Paint p = new Paint();

    public GameOfLifeView(Context context) {
        super(context);
        initWorld();
    }

    public GameOfLifeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWorld();
    }

    @Override
    public void run() {
        while (isRunning) {
            if (!getHolder().getSurface().isValid())
                continue;
            // Pause of 100 ms to better visualize the evolution of the world
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }

            Canvas canvas = getHolder().lockCanvas();
            world.nextGeneration();
            drawCells(canvas);
            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    public void start() {
        isRunning = true;
        Thread thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        isRunning = false;
    }

    private void initWorld() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point displaySize = new Point();
        display.getSize(displaySize);
        nColumns = displaySize.x / DEFAULT_CELL_SIZE - 1;
        nRows = displaySize.y / DEFAULT_CELL_SIZE - 10;
        world = new World(nColumns, nRows);
    }

    private void drawCells(Canvas canvas) {
        for (int i = 0; i < nColumns; i++) {
            for (int j = 0; j < nRows; j++) {
                Cell cell = world.get(i, j);
                r.set(((cell.x + 1) * DEFAULT_CELL_SIZE) - 1,
                        ((cell.y + 1) * DEFAULT_CELL_SIZE) - 1,
                        ((cell.x + 1) * DEFAULT_CELL_SIZE + DEFAULT_CELL_SIZE) - 1,
                        ((cell.y + 1) * DEFAULT_CELL_SIZE + DEFAULT_CELL_SIZE) - 1);

                p.setColor(cell.alive ? DEFAULT_ALIVE_COLOR : DEFAULT_DEAD_COLOR);
                canvas.drawRect(r, p);
            }
        }
    }

    public void restart() {
        world = new World(nColumns, nRows);
    }
}
