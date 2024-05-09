package Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.video.VideoPlayer;
import com.badlogic.gdx.video.VideoPlayerCreator;
import org.w3c.dom.Text;

import java.io.FileNotFoundException;

public class LoadingScreen extends ScreenAdapter {
    VideoPlayer videoPlayer= VideoPlayerCreator.createVideoPlayer();
    FileHandle backgroundVid =  Gdx.files.internal("Video/bg1.webm");
    ShaderProgram shaderProgram = new ShaderProgram(Gdx.files.internal("Shader/default.vert"), Gdx.files.internal("Shader/color_tint_shader.frag"));
    Texture logo;
    private final Game game;
    private SpriteBatch batch;
    private SpriteBatch background;
    private BitmapFont font;
    private AssetManager assetManager;
    float elapsedTime;
    private String tbpPath;
    private String title;
    String username;

    public LoadingScreen(Game game, String tbpPath,String title, String username) {
        this.game = game;
        this.username=username;
        try {
            videoPlayer.play(backgroundVid);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        videoPlayer.setLooping(true);
        this.tbpPath=tbpPath;
        this.title=title;
    }

    @Override
    public void show() {
        background=new SpriteBatch();
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.WHITE);

        assetManager = new AssetManager();
        assetManager.load("assets/Beatmap/"+title+"/audio.ogg", Music.class);
        assetManager.load("assets/Sound/type1.wav", Sound.class);
        assetManager.load("assets/Sound/type2.wav", Sound.class);
        assetManager.load("Img/MapSprites/line1.png", Texture.class);
        assetManager.load("Img/MapSprites/perfect.png", Texture.class);
        assetManager.load("Img/MapSprites/great.png", Texture.class);
        assetManager.load("Img/MapSprites/good.png", Texture.class);
        assetManager.load("Img/MapSprites/bad.png", Texture.class);
        assetManager.finishLoading();

        logo = new Texture("Img/logo3.png");

    }

    @Override
    public void render(float delta) {
        elapsedTime+=delta;

        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        videoPlayer.update();
        background.setShader(shaderProgram);
        if (shaderProgram != null && shaderProgram.isCompiled()) {
            shaderProgram.begin();
            shaderProgram.setUniformf("u_tint", Color.DARK_GRAY);
        }

        background.begin();
        background.draw(videoPlayer.getTexture(),0,0);
        background.end();

        if (shaderProgram != null && shaderProgram.isCompiled()) {
            shaderProgram.end();
        }
        background.setShader(null);

        batch.begin();
        font.draw(batch, "Loading...", 100, 100);
        batch.draw(logo,((float) Gdx.graphics.getWidth() /2)-((float) logo.getWidth() /2), ((float) Gdx.graphics.getHeight() /2)-((float) logo.getHeight() /2) );
        batch.end();

        if (assetManager.update() && elapsedTime>=2) {
            game.setScreen(new Play(game, assetManager,tbpPath, title,username));
        };
    }

    @Override
    public void hide() {
        batch.dispose();
        font.dispose();
    }
}