package Screens;

import Behavior.RhythmGameLogic;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class Play extends ScreenAdapter {
    private Game game;
    private Texture leftLineTexture;
    private Texture rightLineTexture;
    private SpriteBatch batch;
    private RhythmGameLogic gameLogic;
    private float elapsedTime;
    private float leftLineX;
    private float rightLineX;
    private float lineY;

    public Play(Game game) {
        this.game = game;
        leftLineTexture = new Texture("Img/MapSprites/line.png");
        rightLineTexture = new Texture("Img/MapSprites/line.png");
        batch = new SpriteBatch();
        gameLogic = new RhythmGameLogic();
        elapsedTime = 0f;

        float screenWidth = Gdx.graphics.getWidth();
        leftLineX = screenWidth / 4 - leftLineTexture.getWidth() / 2;
        rightLineX = 3 * screenWidth / 4 - rightLineTexture.getWidth() / 2;
        lineY = Gdx.graphics.getHeight() / 2 - leftLineTexture.getHeight() / 2;
    }

    @Override
    public void render(float delta) {
        elapsedTime += delta;

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float screenWidth = Gdx.graphics.getWidth();
        float centerScreenX = screenWidth / 2;

        float targetLeftLineX = centerScreenX - leftLineTexture.getWidth() / 2;
        float targetRightLineX = centerScreenX - rightLineTexture.getWidth() / 2;

        float movementSpeed = 200f;
        leftLineX = moveTowards(leftLineX, targetLeftLineX, movementSpeed * delta);
        rightLineX = moveTowards(rightLineX, targetRightLineX, movementSpeed * delta);

        batch.begin();
        batch.draw(leftLineTexture, leftLineX, lineY);
        batch.draw(rightLineTexture, rightLineX, lineY);
        batch.end();

        if (Gdx.input.justTouched()) {
            Array<Float> beatTimes = gameLogic.getBeatTimes();
            if (!beatTimes.isEmpty()) {
                float nextBeatTime = beatTimes.first();
                float timeThreshold = 0.2f;

                if (Math.abs(elapsedTime - nextBeatTime) <= timeThreshold) {
                    System.out.println("Perfect!");
                    beatTimes.removeIndex(0);
                } else {
                    System.out.println("Missed!");
                }
            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        leftLineTexture.dispose();
        rightLineTexture.dispose();
    }

    private float moveTowards(float currentPos, float targetPos, float maxDistanceDelta) {
        if (Math.abs(targetPos - currentPos) <= maxDistanceDelta) {
            return targetPos;
        } else {
            float direction = Math.signum(targetPos - currentPos);
            return currentPos + direction * maxDistanceDelta;
        }
    }
}
