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
    private float elapsedTime;
    private ArrayList<Line> leftLine;
    private ArrayList<Line> rightLine;
    private float lineY;
    private ArrayList<Float> beatTimes;
    private int noteCount;

    float screenWidth = Gdx.graphics.getWidth();
    float screenHeight = Gdx.graphics.getHeight();
    float centerY = screenHeight / 2;
    float centerX = screenWidth / 2;

    public Play(Game game) {
        this.game = game;
        LineTexture = new Texture("Img/MapSprites/line.png");
        beatTimes = new ArrayList<>();
        beatTimes.add(1.0f);
        beatTimes.add(2.0f);
        beatTimes.add(3.0f);
        beatTimes.add(3.5f);
        beatTimes.add(4.0f);

        leftLine = new ArrayList<>();
        rightLine = new ArrayList<>();

        batch = new SpriteBatch();

        noteCount = beatTimes.size();

        for (int i = 0; i < noteCount; i++) {
            float beat = beatTimes.get(i);
            leftLine.add(new Line(beat, LineTexture, 0));
            rightLine.add(new Line(beat, LineTexture, screenWidth - LineTexture.getWidth()));
        }

        // Add input processor to listen for key events
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.SPACE) {

                    leftLine.remove(0);
                    rightLine.remove(0);
                    return true; // Input handled
                }
                return false; // Input not handled
            }
        });
    }

    @Override
    public void render(float delta) {
        elapsedTime += delta;

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        for (Line line : leftLine) {
            line.updatePosition(elapsedTime, centerX);
            batch.draw(line.getTexture(), line.getX(), line.getY());
        }
        for (Line line : rightLine) {
            line.updatePosition(elapsedTime, centerX);
            batch.draw(line.getTexture(), line.getX(), line.getY());
        }
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        LineTexture.dispose();
    }
}
