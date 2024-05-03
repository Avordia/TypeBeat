package Screens;

import Behavior.KeyHandling;
import Behavior.Line;
import Behavior.Score;
import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.ArrayList;

public class Play extends ScreenAdapter {
    private Sound kick;
    private Sound snare;
    private BitmapFont lblText;
    private BitmapFont lblScore;
    private BitmapFont lblCombo;
    private GlyphLayout layout;
    private ShapeRenderer shapeRenderer;
    private ShapeRenderer shapeAccentColor;
    private ShapeRenderer background;
    private Game game;
    private final Texture LineTexture;
    private Texture gifTexture;
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
    private ArrayList<Boolean> isDead;
    private float elapsedTime;

    private final float screenWidth;
    private final float screenHeight;
    private float centerX;
    private float centerY;
    private final int noteCount;
    private int movingNotes=0;
    private final KeyHandling keyHandler;
    Score score;
    Sound sound;
    public Play(Game game) {
        this.game = game;
        LineTexture = new Texture("Img/MapSprites/line1.png");
        beatTimes = new ArrayList<>();
        beatTimes.add(1.0f);
        beatTimes.add(2.0f);
        beatTimes.add(3.0f);
        beatTimes.add(4.0f);
        beatTimes.add(5.0f);
        beatTimes.add(6.0f);
        beatTimes.add(7.0f);
        beatTimes.add(8.0f);
        beatTimes.add(9.0f);
        spawnTimes=new ArrayList<>();
        spawnTimes.add(0.5f);
        spawnTimes.add(0.5f);
        spawnTimes.add(0.5f);
        spawnTimes.add(1.0f);
        spawnTimes.add(1.0f);
        spawnTimes.add(5.0f);
        spawnTimes.add(5.0f);
        spawnTimes.add(6.0f);
        spawnTimes.add(6.0f);
        letterList=new ArrayList<>();
        letterList.add('T');
        letterList.add('Y');
        letterList.add('P');
        letterList.add('E');
        letterList.add('/');
        letterList.add('*');
        letterList.add('*');
        letterList.add('A');
        letterList.add('T');
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

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        score= new Score();

        float centerY = screenHeight / 2;

        centerX = screenWidth/2;
        centerY = screenHeight/2;

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
            isDead.add(false);
        }
        keyHandler = new KeyHandling(leftLine, rightLine,wordList,isDead,score);
        sound = Gdx.audio.newSound(Gdx.files.internal("assets/Sound/kick.wav"));
    }

    @Override
    public void show(){
        for(int i=0; i<noteCount;i++){
            lineBatch = new SpriteBatch();
            textBatch = new SpriteBatch();
            backgroundBatch = new SpriteBatch();
            float spawnLocationL = -LineTexture.getWidth();
            float spawnLocationR = screenWidth;
            leftLine.add(new Line(spawnTimes.get(i),beatTimes.get(i), letterList.get(i), spawnLocationL));
            rightLine.add(new Line(spawnTimes.get(i),beatTimes.get(i), letterList.get(i), spawnLocationR));
        }
    }
    @Override
    public void render(float delta) {
        Gdx.input.setInputProcessor(keyHandler);
        elapsedTime += delta;
        Gdx.gl.glClearColor(1.0f, 0.0f, 1.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
                movingNotes++;

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
                        movingNotes--;
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
        shapeRenderer.rect(0,0,screenWidth,100);
        shapeRenderer.rect(0,screenHeight-100,screenWidth,100);
        shapeRenderer.rect(0,0,40,screenHeight);
        shapeRenderer.rect(screenWidth-40,0,40,screenHeight);


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
            if (isDead.get(i)) {
                lblText.setColor(Color.WHITE);
            } else {
                lblText.setColor(Color.RED);
            }

            float characterX = textX + (textWidth - layout.width) / 2 + layout.runs.get(0).x + i * layout.width / word.length();
            float characterY = textY;
            if(!String.valueOf(letter).equals("*")) {
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