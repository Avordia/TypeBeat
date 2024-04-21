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

        // Initialize initial positions of lines
        float screenWidth = Gdx.graphics.getWidth();
        leftLineX = screenWidth / 4 - leftLineTexture.getWidth() / 2;
        rightLineX = 3 * screenWidth / 4 - rightLineTexture.getWidth() / 2;
        lineY = Gdx.graphics.getHeight() / 2 - leftLineTexture.getHeight() / 2; // Center vertically
    }

    @Override
    public void render(float delta) {
        elapsedTime += delta;

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float screenWidth = Gdx.graphics.getWidth();
        float centerScreenX = screenWidth / 2;

        // Calculate target X positions towards the center of the screen
        float targetLeftLineX = centerScreenX - leftLineTexture.getWidth() / 2;
        float targetRightLineX = centerScreenX - rightLineTexture.getWidth() / 2;

        // Interpolate current positions towards the target positions
        float movementSpeed = 200f; // Adjust movement speed as needed
        leftLineX = moveTowards(leftLineX, targetLeftLineX, movementSpeed * delta);
        rightLineX = moveTowards(rightLineX, targetRightLineX, movementSpeed * delta);

        batch.begin();
        batch.draw(leftLineTexture, leftLineX, lineY);
        batch.draw(rightLineTexture, rightLineX, lineY);
        batch.end();

        // Check for player input
        if (Gdx.input.justTouched()) {
            Array<Float> beatTimes = gameLogic.getBeatTimes();
            if (!beatTimes.isEmpty()) {
                float nextBeatTime = beatTimes.first();
                float timeThreshold = 0.2f; // Adjust threshold for input timing

                // Check if player input matches the beat timing
                if (Math.abs(elapsedTime - nextBeatTime) <= timeThreshold) {
                    // Player input matched the beat
                    System.out.println("Perfect!");
                    beatTimes.removeIndex(0); // Remove the matched beat time
                } else {
                    // Player input missed the beat
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

    // Helper method to interpolate position towards a target position
    private float moveTowards(float currentPos, float targetPos, float maxDistanceDelta) {
        if (Math.abs(targetPos - currentPos) <= maxDistanceDelta) {
            return targetPos;
        } else {
            float direction = Math.signum(targetPos - currentPos);
            return currentPos + direction * maxDistanceDelta;
        }
    }
}
