package Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.audio.Sound;

public class LoadingScreen extends ScreenAdapter {
    private final Game game;
    private SpriteBatch batch;
    private BitmapFont font;
    private AssetManager assetManager;

    public LoadingScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.WHITE);

        // Initialize AssetManager
        assetManager = new AssetManager();
        // Load your assets here
        assetManager.load("assets/Beatmap/Idol/audio.ogg", Music.class);
        assetManager.load("assets/Sound/type1.wav", Sound.class);
        assetManager.load("assets/Sound/type2.wav", Sound.class);
        assetManager.load("Img/MapSprites/line1.png", Texture.class);
        // Load more assets as needed

        // Block until assets are loaded (optional)
        assetManager.finishLoading();
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Show loading message or animation
        batch.begin();
        font.draw(batch, "Loading...", 100, 100);
        batch.end();

        // Check if all assets are loaded
        if (assetManager.update()) {
            // All assets are loaded, switch to the Play screen
            game.setScreen(new Play(game, assetManager));
        }
    }

    @Override
    public void hide() {
        // Clean up resources
        batch.dispose();
        font.dispose();
    }
}
