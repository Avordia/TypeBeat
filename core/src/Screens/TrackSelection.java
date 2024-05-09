package Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import GameDat.Track;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.video.VideoPlayer;
import com.badlogic.gdx.video.VideoPlayerCreator;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class TrackSelection extends ScreenAdapter {
    VideoPlayer videoPlayer;
    FileHandle backgroundVid;
    ShaderProgram shaderProgram = new ShaderProgram(Gdx.files.internal("Shader/default.vert"), Gdx.files.internal("Shader/color_tint_shader.frag"));
    SpriteBatch background = new SpriteBatch();
    Texture logo;
    Connection con=null;
    SpriteBatch text;
    BitmapFont lblTitle;
    private final float screenWidth=Gdx.graphics.getWidth();
    private final float screenHeight=Gdx.graphics.getHeight();
    private ShapeRenderer shapeRenderer;
    private Game game;
    private Stage stage;
    private Texture buttonPlyS;
    private Texture buttonPlyH;
    private BitmapFont lblScore;
    private GlyphLayout layout;
    private SpriteBatch batch;
    private ArrayList<Track> trackList;
    private ArrayList<String> tbpPath;
    private ArrayList<String> title;
    ArrayList<Integer> userScore;
    ArrayList<Integer> bestScore;
    ArrayList<String> bestUser;
    Button btnPrev;
    Button btnNext;
    int l;
    int c=0;
    int r=1;
    private final int trackCount;
    private Music currentMusic;
    int colorCount=0;

    Button btnPlay;
    String username;
    int userID;

    public TrackSelection(Game game, String username) { //Add tracks here
        this.game=game;
        this.username=username;
        logo = new Texture("Img/logo.png");
        layout=new GlyphLayout();
        trackList = new ArrayList<Track>();
        trackList.add(new Track("Song1", "Artist1", 3, "Beatmap/Idol/background.png", "Beatmap/Idol/audio.ogg",143));
        trackList.add(new Track("Song2", "Artist2", 1, "Beatmap/Hey Kids/background.png", "Beatmap/Hey Kids/audio.ogg",143));
        trackList.add(new Track("Song3", "Artist3", 2, "Beatmap/Silhouette/background.png", "Beatmap/Silhouette/audio.ogg",143));
        trackList.add(new Track("Song4", "Artist4", 2, "Beatmap/lebron/background.png", "Beatmap/lebron/audio.ogg",143));
        trackCount=trackList.size()-1;
        l=trackCount;

        videoPlayer= new VideoPlayerCreator().createVideoPlayer();
        backgroundVid=Gdx.files.internal("Video/bg1.webm");
        try {
            videoPlayer.play(backgroundVid);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        videoPlayer.setLooping(true);

        title=new ArrayList<>();
        title.add("Idol");
        title.add("Hey Kids");
        title.add("Silhouette");
        title.add("lebron");

        tbpPath=new ArrayList<>();
        tbpPath.add("Beatmap/"+title.get(0)+"/dragon.tbp");
        tbpPath.add("Beatmap/"+title.get(1)+"/dragon.tbp");
        tbpPath.add("Beatmap/"+title.get(2)+"/dragon.tbp");
        tbpPath.add("Beatmap/"+title.get(3)+"/dragon.tbp");


        bestUser=new ArrayList<>();
        bestUser.add(getBestPlayerNameSQL(1));
        bestUser.add(getBestPlayerNameSQL(2));
        bestUser.add(getBestPlayerNameSQL(3));
        bestUser.add(getBestPlayerNameSQL(4));

        bestScore = new ArrayList<>();
        bestScore.add(getBestScoreSQL(1));
        bestScore.add(getBestScoreSQL(2));
        bestScore.add(getBestScoreSQL(3));
        bestScore.add(getBestScoreSQL(4));

        FreeTypeFontGenerator scoreFont = new FreeTypeFontGenerator(Gdx.files.internal("Franklin Gothic Heavy Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameterScore= new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameterScore.size=15;

        FreeTypeFontGenerator.FreeTypeFontParameter parameterTitle= new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameterTitle.size=80;

        lblScore = scoreFont.generateFont(parameterScore);
        lblTitle= scoreFont.generateFont(parameterTitle);


        buttonPlyS=new Texture("Img/box3.png");
        buttonPlyH=new Texture("Img/box4.png");
        userID=getUserID();
        userScore = new ArrayList<>();
        userScore.add(getUserScoreSQL(1));
        userScore.add(getUserScoreSQL(2));
        userScore.add(getUserScoreSQL(3));
        userScore.add(getUserScoreSQL(4));

        text=new SpriteBatch();
    }
    private void playCenterMusic() {
        Music musicToPlay = Gdx.audio.newMusic(Gdx.files.internal(trackList.get(c).getSongFilePath()));
        if (currentMusic != null) {
            currentMusic.stop();
            currentMusic.dispose();
        }
        currentMusic = musicToPlay;
        currentMusic.play();
    }
    public void traverseRight() {
        l = (l + 1) % (trackCount + 1);
        c = (c + 1) % (trackCount + 1);
        r = (r + 1) % (trackCount + 1);
    }
    public void traverseLeft() {
        l = (l - 1 + trackCount + 1) % (trackCount + 1);
        c = (c - 1 + trackCount + 1) % (trackCount + 1);
        r = (r - 1 + trackCount + 1) % (trackCount + 1);
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        stage = new Stage();
        shapeRenderer = new ShapeRenderer();
        System.out.println(userID);
        float oW = 80;
        float oH = 34;

        btnPrev = new Button(new TextureRegionDrawable(new TextureRegion(new Texture("Img/Buttons/prev.png"))));
        btnPrev.setSize(100,200);

        btnNext = new Button(new TextureRegionDrawable(new TextureRegion(new Texture("Img/Buttons/next.png"))));
        btnNext.setSize(100,200);

        btnPrev.setPosition(35, Gdx.graphics.getHeight() / 2 - btnPrev.getHeight() / 2);
        btnNext.setPosition(Gdx.graphics.getWidth() - (btnNext.getWidth()+35), Gdx.graphics.getHeight() / 2 - btnPrev.getHeight() / 2);

        btnPlay = new Button(new TextureRegionDrawable(new TextureRegion(new Texture("Img/Buttons/Play0.png"))));
        btnPlay.setSize(oW,oH);
        TextureRegionDrawable btnPlayN = new TextureRegionDrawable(new TextureRegion(buttonPlyS));
        TextureRegionDrawable btnPlayH = new TextureRegionDrawable(new TextureRegion(buttonPlyH));
        btnPlay.getStyle().up=btnPlayN;
        btnPlay.getStyle().down=btnPlayH;
        btnPlay.setPosition(screenWidth/2 - oW/2, 10);

        btnPlay.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor fromActor) {
                // Change button texture on mouse hover
                btnPlay.getStyle().up = btnPlayH;
                btnPlay.addAction(Actions.sizeTo(oW * 1.2f, oH * 1.2f, 0.2f, Interpolation.smooth));
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor toActor) {
                // Revert button texture when mouse exits
                btnPlay.getStyle().up = btnPlayN;
                btnPlay.addAction(Actions.sizeTo(oW, oH, 0.2f, Interpolation.smooth));
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LoadingScreen(game,tbpPath.get(c),title.get(c),username));
                dispose();
            }
        });

        stage.addActor(btnPrev);
        stage.addActor(btnNext);
        stage.addActor(btnPlay);
        currentMusic=Gdx.audio.newMusic(Gdx.files.internal(trackList.get(c).getSongFilePath()));
        currentMusic.setVolume(0.5f);
        currentMusic.setLooping(true);
        currentMusic.play();

        btnNext.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                SequenceAction scaleAnim = Actions.sequence();

                scaleAnim.addAction(Actions.sizeTo(100*1.15f,200*1.15f,0.1f,Interpolation.smooth));
                traverseRight();
                scaleAnim.addAction(Actions.sizeTo(100,200,0.1f,Interpolation.smooth));
                btnNext.addAction(scaleAnim);
                playCenterMusic();

            }
        });

        btnPrev.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SequenceAction scaleAnim = Actions.sequence();

                scaleAnim.addAction(Actions.sizeTo(100*1.15f,200*1.15f,0.1f,Interpolation.smooth));
                traverseLeft();
                scaleAnim.addAction(Actions.sizeTo(100,200,0.1f,Interpolation.smooth));
                btnPrev.addAction(scaleAnim);
                playCenterMusic();
            }
        });
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 0, 0, 1);

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

        float coverWidthH = 400;
        float coverHeightH = 300;

        float coverWidthS = 250;
        float coverHeightS = 187.5f;

        batch.draw(new Texture(trackList.get(l).getCoverImagePath()),200, (float) Gdx.graphics.getHeight() /2 - coverHeightS/2,coverWidthS,coverHeightS);
        batch.draw(new Texture(trackList.get(c).getCoverImagePath()),(float)Gdx.graphics.getWidth()/2 - coverWidthH/2, (float) Gdx.graphics.getHeight() /2 - coverHeightH/2,coverWidthH,coverHeightH);
        batch.draw(new Texture(trackList.get(r).getCoverImagePath()),(float)Gdx.graphics.getWidth()-(coverWidthS+200),(float) Gdx.graphics.getHeight() /2 - coverHeightS/2,coverWidthS,coverHeightS);

        batch.end();
        Button btnPlay;
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);

        shapeRenderer.rect(0,0,screenWidth,200);
        shapeRenderer.rect(0,screenHeight-200,screenWidth,200);
        shapeRenderer.end();

        stage.act(delta);
        stage.draw();

        text.begin();
        lblScore.draw(text,"Best Player: "+bestUser.get(c)+" ["+bestScore.get(c)+"]",150,50);
        lblScore.draw(text,"Your Score: "+userScore.get(c),150,90);
        layout.setText(lblTitle, title.get(c));
        float titleX = (Gdx.graphics.getWidth() - layout.width) / 2;
        lblTitle.draw(text, layout, titleX, 160);
        float logoWidth = logo.getWidth() * 0.35f;
        float logoHeight = logo.getHeight() * 0.35f;
        float logoX = (screenWidth - logoWidth) / 2;
        float logoY = screenHeight-240;
        text.draw(logo, logoX, logoY, logoWidth, logoHeight);
        text.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        shapeRenderer.dispose();
        stage.dispose();
        batch.dispose();

        buttonPlyS.dispose();
        buttonPlyH.dispose();
        logo.dispose();

        videoPlayer.dispose();

        lblScore.dispose();
        lblTitle.dispose();
        if (currentMusic != null) {
            currentMusic.stop();
            currentMusic.dispose();
        }

        if (shaderProgram != null) {
            shaderProgram.dispose();
        }

    }


    @Override
    public void hide() {
        currentMusic.stop();
    }

    public void incrementCount(){
        colorCount++;
        if(colorCount==3){
            colorCount=0;
        }
    }

    public void connectionDB () {
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/typebeat_db"; // database declaration
            String username = "root";
            String password = "";

            con = DriverManager.getConnection(url, username, password);

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public int getUserScoreSQL(int mapID) {
        connectionDB();
        int userScore = 0;
        String scoreString = "";
        try {
            Statement st = con.createStatement();

            String selectScore = "SELECT scores FROM highscores WHERE userID = " + userID + " AND mapID = " + mapID;
            ResultSet rs = st.executeQuery(selectScore);

            if (rs.next()) {
                userScore = rs.getInt("scores");
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return userScore;
    }

    public int getBestScoreSQL(int mapID){  // WORKS
        connectionDB();
        int userBestScore = 0;
        String bestScoreString = "";

        try{
            Statement st = con.createStatement();

            String selectBestScore = "SELECT MAX(scores) FROM highscores WHERE mapID = " + mapID;
            ResultSet rs = st.executeQuery(selectBestScore);

            if(rs.next()){
                userBestScore = rs.getInt(1);
            }

        }catch (Exception e){
            System.out.println(e);
        }

        return userBestScore;
    }

    public String getBestPlayerNameSQL(int mapID){  // WORKS
        connectionDB();
        String bestPlayer = "";

        try {
            Statement st = con.createStatement();

            String selectBestPlayer = "SELECT l.firstname " +
                    "FROM highscores AS s " +
                    "INNER JOIN login AS l ON s.userID = l.userID " +
                    "WHERE s.mapID = " + mapID + " " +
                    "ORDER BY s.scores DESC " +
                    "LIMIT 1;";
            ResultSet rs = st.executeQuery(selectBestPlayer);

            if (rs.next()) {
                bestPlayer = rs.getString(1);

            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return bestPlayer;
    }

    public int getUserID() {  // WORKS
        connectionDB();

        try {
            Statement st = con.createStatement();

            String selectUserID = "SELECT userID FROM login WHERE username = '" + username + "'";
            ResultSet rs = st.executeQuery(selectUserID);

            if (rs.next()) {
                userID = rs.getInt(1);

            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        return userID;
    }

}

