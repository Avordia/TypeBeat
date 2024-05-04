package Screens;

import Behavior.KeyHandling;
import Behavior.Line;
import Behavior.Score;
import Parser.TBPFileReader;
import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;


public class Play extends ScreenAdapter {
    private Sound kick;
    private Sound snare;
    private Music backgroundMusic;
    private BitmapFont lblText;
    private BitmapFont lblScore;
    private BitmapFont lblCombo;
    private GlyphLayout layout;
    private ShapeRenderer shapeRenderer;
    private ShapeRenderer shapeAccentColor;
    private ShapeRenderer background;
    private Game game;
    private final Texture lineTexture;
    private Texture backgroundPicture;
    private SpriteBatch lineBatch;
    private SpriteBatch textBatch;
    private SpriteBatch backgroundBatch;
    Animation<TextureRegion> animation;
    private final ArrayList<Line> leftLine;
    private final ArrayList<Line> rightLine;
    private final ArrayList<Float> beatTimes;
    private ArrayList<Float> spawnTimes;
    private final ArrayList<Character> letterList;
    private ArrayList<String> wordList;
    private ArrayList<Integer> isDead;
    private float elapsedTime;

    private final float screenWidth;
    private final float screenHeight;
    private float centerX;
    private float centerY;
    private final int noteCount;
    private final KeyHandling keyHandler;
    Score score;
    Sound sound;
    private AssetManager assetManager;

    public Play(Game game, AssetManager assetManager) {

        this.assetManager = assetManager;

        backgroundMusic = assetManager.get("assets/Beatmap/Idol/audio.ogg", Music.class);
        kick = assetManager.get("assets/Sound/type1.wav", Sound.class);
        snare = assetManager.get("assets/Sound/type2.wav", Sound.class);
        lineTexture = assetManager.get("Img/MapSprites/line1.png", Texture.class);

        this.game = game;
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        float centerY = screenHeight / 2;
        centerX = screenWidth/2;

        String tbpFilePath = "assets/Beatmap/Idol/dragon.tbp";
        TBPFileReader.BeatmapData beatmapData = TBPFileReader.readTBPFile(tbpFilePath);

        String audioPath = beatmapData.getAudioPath();
        String backgroundPath = beatmapData.getBackgroundPath();
        List<TBPFileReader.BeatData> beatDataList = beatmapData.getBeatDataList();

        beatTimes = new ArrayList<>();
        spawnTimes=new ArrayList<>();
        letterList=new ArrayList<>();

        for (TBPFileReader.BeatData beatData : beatDataList) {
            float spawnTime = beatData.getSpawnTime()+2.378f; //Callibrators
            float beatTime = beatData.getBeatTime()+2.378f; //Callibrators
            char letter = beatData.getLetter();

            beatTimes.add(beatTime);
            spawnTimes.add(spawnTime);
            letterList.add(letter);
        }
        spawnTimes.set(0,1.4f);

        wordList=new ArrayList<>();
        /*-------------------STRING BUILDER------------------------*/

        StringBuilder currentString = new StringBuilder();

        for (int i = 0; i < letterList.size(); i++) {
            char ch = letterList.get(i);

            if (ch == '/') {
                wordList.add(currentString.toString());
                currentString.setLength(0);
            }
            else if(ch=='*'){
                wordList.add("*");
            }
            else {
                currentString.append(ch);
            }
        }

        if (currentString.length() > 0) {
            wordList.add(currentString.toString());
        }

        /*---------------------------------------------------------------*/

        noteCount= beatTimes.size();

        leftLine = new ArrayList<>();
        rightLine = new ArrayList<>();


        score = new Score();
        elapsedTime = 0f;

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Beatmap/Idol/audio.ogg"));
        backgroundMusic.setVolume(0.5f);
        backgroundMusic.setLooping(false);

        this.kick = Gdx.audio.newSound(Gdx.files.internal("assets/Sound/type1.wav"));
        this.snare = Gdx.audio.newSound(Gdx.files.internal("assets/Sound/type2.wav"));

        FreeTypeFontGenerator wordFont = new FreeTypeFontGenerator(Gdx.files.internal("BADABB__.TTF"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameterWord= new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameterWord.size=40;

        FreeTypeFontGenerator scoreFont = new FreeTypeFontGenerator(Gdx.files.internal("BADABB__.TTF"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameterScore= new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameterScore.size=20;

        lblText = wordFont.generateFont(parameterWord);
        lblScore = scoreFont.generateFont(parameterScore);
        lblCombo = new BitmapFont();
        layout = new GlyphLayout();
        shapeRenderer = new ShapeRenderer();
        shapeAccentColor = new ShapeRenderer();

        isDead = new ArrayList<>();
        for (int i = 0; i < noteCount; i++) {
            isDead.add(4);
        }

        keyHandler = new KeyHandling(leftLine, rightLine, wordList, isDead, score, kick, snare);
        lineBatch = new SpriteBatch();
        textBatch = new SpriteBatch();
        backgroundBatch = new SpriteBatch();

    }

    @Override
    public void show(){
        for(int i=0; i<noteCount;i++){
            float spawnLocationL = -lineTexture.getWidth();
            float spawnLocationR = screenWidth;
            leftLine.add(new Line(spawnTimes.get(i),beatTimes.get(i), letterList.get(i), spawnLocationL));
            rightLine.add(new Line(spawnTimes.get(i),beatTimes.get(i), letterList.get(i), spawnLocationR));
        }
    }

    @Override
    public void render(float delta) {
        Gdx.input.setInputProcessor(keyHandler);
        elapsedTime += delta;
        Gdx.gl.glClearColor(0.5f, 0.2f, 0.6f, 0.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(elapsedTime>=2.2f){
            backgroundMusic.play();
        }
        backgroundBatch.begin();
        backgroundBatch.end();

        lineBatch.begin();
        /* ------------------------------------------ LEFT SIDE ----------------------------------------- */
        for (int i = 0; i < leftLine.size(); i++) {
            Line line = leftLine.get(i);
            if (elapsedTime >= line.getSpawnTime()) {
                float distanceToTravel = centerX - line.getX();
                float remainingTime = line.getBeatTime() - elapsedTime;
                float speed = distanceToTravel / remainingTime;

                lineBatch.draw(line.getTexture(), line.getX(), line.getY());
                line.setX(line.getX() + speed * delta);

                if (line.getX() >= centerX) {
                    if (leftLine.get(0).getLineType() == 2) {
                        for(int j=0; j<wordList.get(0).length(); j++){
                            isDead.remove(0);
                        }
                        keyHandler.setDeath(0);
                        wordList.remove(0);
                        if (!leftLine.isEmpty()) {
                            leftLine.remove(0);
                        }
                    }
                    else {
                        leftLine.remove(i);
                        i--;
                        keyHandler.incrementDeath();
                    }
                }
            }
        }
        /* ------------------------------------------ RIGHT SIDE ----------------------------------------- */
        for (int i = 0; i < rightLine.size(); i++) {
            Line line = rightLine.get(i);
            if (elapsedTime >= line.getSpawnTime()) {
                float distanceToTravel = line.getX() - centerX;

                float remainingTime = line.getBeatTime() - elapsedTime;
                float speed = distanceToTravel / remainingTime;

                lineBatch.draw(line.getTexture(), line.getX(), line.getY());
                line.setX(line.getX() - speed * delta);

                if (line.getX() <= centerX) {
                    rightLine.remove(i);
                    i--;
                    score.resetCombo();
                }
            }
        }

        lineBatch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        float rectWidth = 250;
        float rectHeight = 100;
        float rectX = (screenWidth - rectWidth) / 2;
        float rectY = (screenHeight - rectHeight) / 2;
        shapeRenderer.rect(rectX, rectY, rectWidth, rectHeight);
        shapeRenderer.rect(0,0,screenWidth,250);
        shapeRenderer.rect(0,screenHeight-250,screenWidth,250);
        shapeRenderer.rect(0,0,40,screenHeight);
        shapeRenderer.rect(screenWidth-40,0,40,screenHeight);

        float centerRectWidth=10;
        float centerRectHeight=1000;
        shapeRenderer.rect((screenWidth/2)-centerRectWidth,0,centerRectWidth,centerRectHeight);

        shapeRenderer.end();

        textBatch.begin();

        lblText.setColor(Color.RED);

        String word = wordList.get(0);

        layout.setText(lblText, word);
        float textWidth = layout.width;
        float textHeight = layout.height;

        float textX = (screenWidth - textWidth) / 2;
        float textY = (screenHeight + textHeight) / 2;

        for (int i = 0; i < word.length(); i++) {
            char letter = word.charAt(i);
            Color color;

            switch (isDead.get(i)) {
                case 0:
                    color = Color.RED; // Bad
                    break;
                case 1:
                    color = Color.YELLOW; // Good
                    break;
                case 2:
                    color = Color.BLUE; // Great
                    break;
                case 3:
                    color = Color.GREEN; // Perfect
                    break;
                default:
                    color = Color.GRAY; // Default or unrecognized
                    break;
            }

            lblText.setColor(color);

            float characterX = textX + (textWidth - layout.width) / 2 + layout.runs.get(0).x + i * layout.width / word.length();
            float characterY = textY;

            if (!String.valueOf(letter).equals("*")) {
                lblText.draw(textBatch, String.valueOf(letter), characterX, characterY);
            }
        }
        lblScore.setColor(Color.WHITE);
        lblScore.draw(textBatch, score.getScoreString(), 100,screenHeight-50);

        lblCombo.setColor(Color.RED);
        lblCombo.draw(textBatch, score.getComboString(),500,100);
        textBatch.end();
    }

    @Override
    public void dispose() {
        lblText.dispose();
        lblScore.dispose();
        lblCombo.dispose();
        shapeRenderer.dispose();
        shapeAccentColor.dispose();
    }
}