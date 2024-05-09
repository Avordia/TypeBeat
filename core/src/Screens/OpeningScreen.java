package Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class OpeningScreen extends ScreenAdapter {
    private final Game game;
    private Stage stage;
    private Texture libGDXTexture;
    private Texture javaTexture;
    private Image logoLibGDX;
    private Image logoJava;
    String username;

    public OpeningScreen(Game game, String username) {
        this.game = game;
        this.username=username;
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        libGDXTexture = new Texture("Img/libGDX.png");
        javaTexture = new Texture("Img/javaLogo.png");

        logoLibGDX = new Image(libGDXTexture);
        logoLibGDX.getColor().a = 0f;
        logoLibGDX.setPosition(Gdx.graphics.getWidth() / 2f - logoLibGDX.getWidth() / 2f,
                Gdx.graphics.getHeight() / 2f - logoLibGDX.getHeight() / 2f);
        float javaLogoWidth = Gdx.graphics.getWidth() * 0.5f;
        float aspectRatio = (float) javaTexture.getHeight() / (float) javaTexture.getWidth();
        float javaLogoHeight = javaLogoWidth * aspectRatio;

        logoJava = new Image(javaTexture);
        logoJava.getColor().a = 0f;
        logoJava.setSize(javaLogoWidth, javaLogoHeight);
        logoJava.setPosition(Gdx.graphics.getWidth() / 2f - javaLogoWidth / 2f,
                Gdx.graphics.getHeight() / 2f - javaLogoHeight / 2f);

        float fadeInDuration = 1.0f;
        float holdDuration = 2.0f;
        float fadeOutDuration = 1.0f;

        logoLibGDX.addAction(Actions.sequence(
                Actions.fadeIn(fadeInDuration),
                Actions.delay(holdDuration),
                Actions.fadeOut(fadeOutDuration)
        ));

        logoJava.addAction(Actions.sequence(
                Actions.delay(fadeInDuration + holdDuration+1),
                Actions.fadeIn(fadeInDuration),
                Actions.delay(holdDuration),
                Actions.fadeOut(fadeOutDuration),
                Actions.run(() -> {
                    game.setScreen(new Menu(game,username));
                })
        ));

        stage.addActor(logoLibGDX);
        stage.addActor(logoJava);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        libGDXTexture.dispose();
        javaTexture.dispose();
    }
}
