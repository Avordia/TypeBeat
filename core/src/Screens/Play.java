package Screens;

import Behavior.Line;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class Play extends ScreenAdapter {
    private Game game;
    private Texture LineTexture;
    private SpriteBatch batch;
    private ArrayList<Line> leftLine;
    private ArrayList<Line> rightLine;
    private ArrayList<Float> beatTimes;
    private ArrayList<Float> spawnTimes;
    private float elapsedTime;

    private float screenWidth;
    private float screenHeight;
    private float targetX;
    private int noteCount;

    private int movingNotes=0;

    public Play(Game game) {
        this.game = game;
        LineTexture = new Texture("Img/MapSprites/line.png");
        batch = new SpriteBatch();
        beatTimes = new ArrayList<>();
        beatTimes.add(1.0f);
        beatTimes.add(2.0f);
        beatTimes.add(3.0f);
        beatTimes.add(3.5f);
        beatTimes.add(7.0f);

        spawnTimes=new ArrayList<>();
        spawnTimes.add(0.0f);
        spawnTimes.add(0.0f);
        spawnTimes.add(0.0f);
        spawnTimes.add(1.0f);
        spawnTimes.add(5.0f);

        noteCount= beatTimes.size();


        leftLine = new ArrayList<>();
        rightLine = new ArrayList<>();

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        float centerY = screenHeight / 2;

        for(int i=0; i<noteCount;i++){
            float spawnLocationL = -LineTexture.getWidth();
            float spawnLocationR = screenWidth;
            leftLine.add(new Line(spawnTimes.get(i),beatTimes.get(i), LineTexture, spawnLocationL));
            rightLine.add(new Line(spawnTimes.get(i),beatTimes.get(i), LineTexture, spawnLocationR));
        }

        targetX = screenWidth / 2;

    }

    @Override
    public void render(float delta) {
        elapsedTime += delta;

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.SPACE) {
                    // Remove the first line from both lists when spacebar is pressed
                    if (!leftLine.isEmpty()) {
                        leftLine.remove(0);
                    }
                    if (!rightLine.isEmpty()) {
                        rightLine.remove(0);
                    }
                    return true;
                }
                return false;
            }
        });
        /* ------------------------------------------ LEFT SIDE ----------------------------------------- */
        for (int i = 0; i < leftLine.size(); i++) {
                Line line = leftLine.get(i);
            if (elapsedTime >= line.getSpawnTime()) {
                float distanceToTravel = targetX - line.getX();

                float remainingTime = line.getBeatTime() - elapsedTime;
                float speed = distanceToTravel / remainingTime;

                batch.draw(line.getTexture(), line.getX(), line.getY());
                line.setX(line.getX() + speed * delta);
                movingNotes++;

                if (line.getX() >= targetX) {
                    leftLine.remove(i);
                    i--;
                    movingNotes--;
                }
            }
        }

        /* ------------------------------------------ RIGHT SIDE ----------------------------------------- */

        // Update and render right lines
        for (int i = 0; i < rightLine.size(); i++) {
            Line line = rightLine.get(i);
            if (elapsedTime >= line.getSpawnTime()) {
                float distanceToTravel = line.getX() - targetX; // Distance to center

                // Calculate speed based on the time remaining until beatTime
                float remainingTime = line.getBeatTime() - elapsedTime;
                float speed = distanceToTravel / remainingTime;

                batch.draw(line.getTexture(), line.getX(), line.getY());
                line.setX(line.getX() - speed * delta); // Move towards the center


                // Check if line has reached the center
                if (line.getX() <= targetX) {
                    rightLine.remove(i);
                    i--; // Decrement i to account for removed element
                }
            }
        }

        batch.end();
    }


    @Override
    public void dispose() {
        batch.dispose();
        LineTexture.dispose();
    }
}
