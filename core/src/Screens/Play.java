package Screens;

import Behavior.Line;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class Play extends ScreenAdapter {

    private float lifeSpan= 0;
    float screenWidth=Gdx.graphics.getWidth();

    private Game game;
    private SpriteBatch batch;
    private Texture lineTexture;
    private ArrayList<Float> beatTimings = new ArrayList<>();
    private ArrayList<Line> lines = new ArrayList<>();
    private int currentBeatIndex = 0;
    private float timer;


    public Play(Game game) {
        this.game = game;
        batch = new SpriteBatch();
        lineTexture = new Texture("Img/MapSprites/line.png");

        // Example beat timings (populate this with your actual beat timings)
        beatTimings.add(0.0f);   // 0
        beatTimings.add(1.5f);   // 1
        beatTimings.add(3.0f);   // 2
        beatTimings.add(4.0f);//3
        beatTimings.add(5.0f);//4
        beatTimings.add(5.2f);//5
        beatTimings.add(5.3f);//6

        timer = 0;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.SPACE) {
                    removeCurrentLine();
                    return true;
                }
                return false;
            }
        });
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        timer += delta;

        // Spawn new lines based on beat timings
        lifeSpan += delta;
        // Interval between line spawns (in seconds)
        float spawnInterval = 0.5f;
        if (lifeSpan >= spawnInterval && currentBeatIndex < beatTimings.size()) {
            float nextBeatTime = beatTimings.get(currentBeatIndex);
            if (timer >= nextBeatTime) {
                float speed = screenWidth / (beatTimings.get(currentBeatIndex + 1) - nextBeatTime);
                Line newLine = new Line(0, speed); // Spawn line on the left
                lines.add(newLine);
                Line newRightLine = new Line(screenWidth, -speed); // Spawn line on the right
                lines.add(newRightLine);
                currentBeatIndex++;
                lifeSpan = 0;
            }
        }

        // Update and draw lines
        batch.begin();
        for (Line line : lines) {
            batch.draw(lineTexture, line.getX(), 0);  // Draw line using left position
        }
        batch.end();

        // Update line positions
        for (Line line : lines) {
            line.update(delta);
        }

        // Remove lines that have moved off the screen
        lines.removeIf(line -> line.getX() > screenWidth || line.getX() < -lineTexture.getWidth());

        // Update timer
        timer += delta;
    }

    @Override
    public void dispose() {
        batch.dispose();
        lineTexture.dispose();
    }

    private void removeCurrentLine() {
        if (!lines.isEmpty()) {
            lines.remove(0);
        }
    }
}
