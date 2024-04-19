package Screens;

import Behavior.Line;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class Play extends ScreenAdapter {

    private Game game;
    private SpriteBatch batch;
    private Texture lineTexture;
    private ArrayList<Line> lines = new ArrayList<>();
    private float lineSpeed = 200; // Initial line speed
    private float lineInterval = 0.5f; // Interval between line spawns
    private float lineTimer = 0;

    private float[] beatTimings = { 1.0f, 2.0f, 3.0f, 4.0f, 5.0f };

    private int currentBeatIndex = 0;

    public Play(Game game) {
        this.game = game;
        batch = new SpriteBatch();
        lineTexture = new Texture("Img/MapSprites/line.png");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        // Render and update existing lines
        for (Line line : lines) {
            batch.draw(lineTexture, line.getX(), line.getY());
            line.update(delta);
        }

        // Check for beat spawn based on current timing
        float currentTime = getCurrentTime();
        if (currentTime >= beatTimings[currentBeatIndex]) {
            spawnLine();
            currentBeatIndex++; // Move to the next beat timing
        }

        batch.end();

        // Handle input (spacebar for hits)
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            checkHits();
        }
    }

    private void spawnLine() {
        Line newLine = new Line(0, Gdx.graphics.getHeight(), lineSpeed);
        lines.add(newLine);
    }

    private void checkHits() {
        if (!lines.isEmpty()) {
            Line line = lines.get(0); // Check the first line (oldest line)
            float targetY = Gdx.graphics.getHeight() / 2; // Height of the target area
            float hitRange = 20; // Acceptable range around the target area for a hit

            // Check if the line's Y position is within the hit range
            if (Math.abs(line.getY() - targetY) <= hitRange) {
                System.out.println("Hit!");
            } else {
                System.out.println("Miss!");
            }

            // Remove the hit line from the list
            lines.remove(0);
        }
    }

    private float getCurrentTime() {
        return lineTimer; // Using lineTimer as current time for simplicity
    }

    @Override
    public void dispose() {
        batch.dispose();
        lineTexture.dispose();
    }
}
