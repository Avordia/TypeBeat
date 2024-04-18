package Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Play extends ScreenAdapter {

    private Game game;
    private SpriteBatch batch;
    private Texture leftLineTexture;
    private Texture rightLineTexture;
    private Texture targetTexture;
    private float leftLineX;
    private float rightLineX;
    private float targetX;
    private float beatInterval;
    private float timer;

    public Play(Game game){
        this.game=game;
        batch = new SpriteBatch();
        leftLineTexture = new Texture("left_line.png");
        rightLineTexture = new Texture("right_line.png");
        targetTexture = new Texture("target.png");

        leftLineX = 0;
        rightLineX = Gdx.graphics.getWidth() - rightLineTexture.getWidth();
        targetX = (Gdx.graphics.getWidth() - targetTexture.getWidth()) / 2;

        beatInterval = 1.0f; // Example: beat every 1 second
        timer = 0;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        batch.draw(leftLineTexture, leftLineX, 0);
        batch.draw(rightLineTexture, rightLineX, 0);
        batch.draw(targetTexture, targetX, 0);

        batch.end();

        timer += delta;
        if (timer >= beatInterval) {
            timer -= beatInterval;
            handleBeat();
        }
    }

    private void handleBeat() {
        leftLineX += 10; // Move left line towards the center
        rightLineX -= 10; // Move right line towards the center

        if (Gdx.input.justTouched()) {
            float centerTargetX = targetX + targetTexture.getWidth() / 2;
            if (Math.abs(centerTargetX - leftLineX) < 10 || Math.abs(centerTargetX - rightLineX) < 10) {
                System.out.println("Hit!");
                // Handle successful hit
            } else {
                System.out.println("Miss!");
                // Handle miss
            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        leftLineTexture.dispose();
        rightLineTexture.dispose();
        targetTexture.dispose();
    }
}
