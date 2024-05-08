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
//import com.sun.java.swing.action.StateChangeAction;
import org.w3c.dom.Text;
import javax.swing.*;
import javax.swing.plaf.nimbus.State;
import java.sql.*;

import java.io.FileNotFoundException;
import java.sql.Connection;

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
    private int userID;
    private int mapID;
    private int bestScore;

    String testUserScore;
    String testBestPlayerScore;
    String testBestScore;

    Connection con = null;


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

        connectionDB();

        testUserScore=getUserScoreSQL();
        testBestPlayerScore=getBestPlayerSQL();
        testBestScore=getBestScoreSQL();
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
        lblScore.draw(batch,"Test case: "+testUserScore,100,300); //1
        lblScore.draw(batch,"Test case: "+testBestPlayerScore,100,500); //2
        lblScore.draw(batch,"Test case: "+testBestScore,100, 600); //3
        /*----------------------------------------MGA TEST CASE--------------------------------*/

        batch.end();
    }

    //sql database connection
    public void connectionDB () {
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/typebeat_db"; // database declaration
            String username = "root";
            String password = "";

            con = DriverManager.getConnection(url, username, password);

            //extra checker if connected naba sa database
            if (con != null && !con.isClosed()) {
                System.out.println("Connected to the database successfully!");
            } else {
                System.out.println("Failed to connect to the database.");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }



    // sa Map na table, e add si Idol. Iyang MusicID kay 1.
    public void insertScore(){
        connectionDB();

        try {
            Statement st = con.createStatement();

            String insertQuery = "INSERT INTO highscores (userID, mapID, scores) VALUES (" + userID + ", " + mapID + ", " + score + ")";
            int rows = st.executeUpdate(insertQuery);

            //checker if na insert ang data
            if (rows > 0) {
                System.out.println("Records inserted Successfully!");
            } else {
                System.out.println("Failed to insert records");
            }


            System.out.println("Score inserted Successfully!");

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        //Goal ani kay mu insert iyang score sa Score table.
        //Basta foreign key ang ID, ug Username.
        //naa String musicTitle sa taas ug Int musicID.
        //Dapat ang goal kay ma insert ang score. Ang placeholder score ani kay 198762.
        //so sample output inig tan.aw sa SQL kay
        // Music ID    Title      Username                          Score
        //   1          Idol       (inyong sample username)         198762
    }

    public String getUserScoreSQL(){
        connectionDB();

        String scoreString = "";
        try {
            Statement st = con.createStatement();

            String selectScore = "SELECT scores FROM highscores WHERE userID = " + userID;
            ResultSet rs = st.executeQuery(selectScore);


            if (rs.next()) {
                score = rs.getInt(1); //changed to column index instead of column name
                scoreString = String.valueOf(score);

            }

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        //insert logic sa pagkuha sa score
        //e return ang score as string. ""+score

        return scoreString;
    }

    public String getBestScoreSQL(){
        connectionDB();

        String bestScoreString = "";

        try{
            Statement st = con.createStatement();

            String selectBestScore = "SELECT MAX(scores) FROM highscores WHERE mapID = " + mapID;
            ResultSet rs = st.executeQuery(selectBestScore);

            if(rs.next()){
                bestScore = rs.getInt(1); //changed to column index instead of column name
                bestScoreString = String.valueOf(bestScore);
            }

        }catch (Exception e){
            System.out.println("Error: " + e);
        }

        //goal ninyo ani kay e return ang highest score sa kana na map.
        //pwede ra mo mag add ug lain row sa SQL para testing basta e remove basta succesful na ang testing.
        //e return ang score as string. ""+score

        return bestScoreString;
    }

    public String getBestPlayerSQL(){
        connectionDB();
        String bestPlayer = "";


        try {
            Statement st = con.createStatement();

            String selectBestPlayer = "SELECT l.firstname " +
                    "FROM highscores AS s " +
                    "INNER JOIN login AS l ON s.userID = l.userID " +
                    "WHERE s.mapID = " + mapID + " " +
                    "ORDER BY s.scores DESC " +
                    "LIMIT 1";
            ResultSet rs = st.executeQuery(selectBestPlayer);

            if (rs.next()) {
                bestPlayer = rs.getString("firstname");

            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        //goal ninyo ani kay e return ang player na naay highest score sa kana na map.
        //pwede ra mo mag add ug lain row sa SQL para testing basta e remove basta succesful na ang testing.
        //e return ang best player as string. return player;

        return bestPlayer;
    }

    //getters for userID
    public int getUserID (String username) {
        connectionDB();

        userID = -1;

        try {
            Statement st = con.createStatement();

            String selectUserID = "SELECT userID FROM login WHERE username = " + username;
            ResultSet rs = st.executeQuery(selectUserID);

            if (rs.next()) {
                userID = rs.getInt("userID");

            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        return userID;
    }



    @Override
    public void hide() {
        batch.dispose();
        font.dispose();
    }
}
