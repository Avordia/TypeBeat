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
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.video.VideoPlayer;
import com.badlogic.gdx.video.VideoPlayerCreator;
import org.w3c.dom.Text;

import java.io.FileNotFoundException;

public class ScoreScreen extends ScreenAdapter {
    VideoPlayer videoPlayer= VideoPlayerCreator.createVideoPlayer();
    FileHandle backgroundVid =  Gdx.files.internal("Video/bg1.webm");
    ShaderProgram shaderProgram = new ShaderProgram(Gdx.files.internal("Shader/default.vert"), Gdx.files.internal("Shader/color_tint_shader.frag"));
    Texture logo;
    ShapeRenderer shapeRenderer;

    private BitmapFont lblScore;
    private final Game game;
    private SpriteBatch batch;
    private SpriteBatch background;
    private BitmapFont font;
    private AssetManager assetManager;
    private float elapsedTime;
    private int score;
    private int noteCount;
    private int maximumScore=0;
    private int animatedScore=0;
    private GlyphLayout glyph = new GlyphLayout();
    int id;
    String musicTitle;

    public ScoreScreen(Game game, int score, int noteCount){
        this.game = game;
        this.score = score;
        for(int i=0; i<noteCount;i++){
            maximumScore+=350*noteCount;
        }
        try {
            videoPlayer.play(backgroundVid);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        videoPlayer.setLooping(true);

        FreeTypeFontGenerator scoreFont = new FreeTypeFontGenerator(Gdx.files.internal("Franklin Gothic Heavy Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameterScore= new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameterScore.size=20;

        lblScore=scoreFont.generateFont(parameterScore);
        shapeRenderer = new ShapeRenderer();

        int id=1;
        String musicTitle="Idol";
    }

    @Override
    public void show() {
        background=new SpriteBatch();
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.WHITE);

        assetManager = new AssetManager();
        assetManager.load("assets/Beatmap/Idol/audio.ogg", Music.class);
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
        if(animatedScore<=score){
            lblScore.draw(batch,"SCORE: "+(animatedScore+=(score/200)),100,100);
        }
        else{
            lblScore.draw(batch, "Score: "+score,100,100);
        }
        float size=500;
        batch.draw(logo,((float) Gdx.graphics.getWidth() /2)-((float) size /2), ((float) Gdx.graphics.getHeight() /2)-((float) size /2), size,size);

        /*----------------------------------------MGA TEST CASE--------------------------------*/
        lblScore.draw(batch,"Test case: "+getUserScoreSQL(),100,300); //1
        lblScore.draw(batch,"Test case: "+getBestPlayerSQL(),100,500); //2
        lblScore.draw(batch,"Test case: "+getBestScoreSQL(),100, 600); //3
        /*----------------------------------------MGA TEST CASE--------------------------------*/

        batch.end();
    }
    // sa Map na table, e add si Idol. Iyang MusicID kay 1.
    public void insertScore(){

        //Goal ani kay mu insert iyang score sa Score table.
        //Basta foreign key ang ID, ug Username.
        //naa String musicTitle sa taas ug Int musicID.
        //Dapat ang goal kay ma insert ang score. Ang placeholder score ani kay 198762.
        //so sample output inig tan.aw sa SQL kay
        // Music ID    Title      Username                          Score
        //   1          Idol       (inyong sample username)         198762
    }

    public String getUserScoreSQL(){
        String username="inyong sample username";
        //insert logic sa pagkuha sa score
        //e return ang score as string. ""+score
        return "";
    }

    public String getBestScoreSQL(){
        //goal ninyo ani kay e return ang highest score sa kana na map.
        //pwede ra mo mag add ug lain row sa SQL para testing basta e remove basta succesful na ang testing.
        //e return ang score as string. ""+score
        return "";
    }

    public String getBestPlayerSQL(){
        //goal ninyo ani kay e return ang player na naay highest score sa kana na map.
        //pwede ra mo mag add ug lain row sa SQL para testing basta e remove basta succesful na ang testing.
        //e return ang best player as string. return player;
        return "";
    }


    @Override
    public void hide() {
        batch.dispose();
        font.dispose();
    }
}
