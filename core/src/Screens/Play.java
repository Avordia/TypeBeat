package Screens;

import Behavior.KeyHandling;
import Behavior.Line;
import Behavior.Score;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.ArrayList;

public class Play extends ScreenAdapter {

    int dead=0;
    int wordCount=0;
    private BitmapFont font;
    private GlyphLayout layout;
    private ShapeRenderer shapeRenderer;
    private Game game;
    private final Texture LineTexture;
    private final SpriteBatch lineBatch;
    private final SpriteBatch textBatch;
    private final ArrayList<Line> leftLine;
    private final ArrayList<Line> rightLine;
    private final ArrayList<Float> beatTimes;
    private ArrayList<Float> spawnTimes;
    private final ArrayList<Character> letterList;
    private ArrayList<String> wordList;
    private float elapsedTime;

    private final float screenWidth;
    private final float screenHeight;
    private float centerX;
    private float centerY;
    private final int noteCount;
    private int movingNotes=0;
    private final KeyHandling keyHandler;
    Score score;

    public Play(Game game) {
        this.game = game;
        LineTexture = new Texture("Img/MapSprites/line1.png");
        lineBatch = new SpriteBatch();
        textBatch = new SpriteBatch();
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
                // Append character to currentString
                currentString.append(ch);
            }
        }

        // Add the last accumulated currentString if not empty
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
        keyHandler = new KeyHandling(leftLine, rightLine,wordList,score);

        float centerY = screenHeight / 2;


        centerX = screenWidth/2;
        centerY = screenHeight/2;

        font = new BitmapFont();
        layout = new GlyphLayout();
        shapeRenderer = new ShapeRenderer();



    }

    @Override
    public void show(){
        for(int i=0; i<noteCount;i++){
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
        Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
                    if (leftLine.get(0).getLineType()==2) {
                        wordList.remove(0);
                    }
                    leftLine.remove(i);
                    i--;
                    movingNotes--;
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
        float rectWidth = 200;
        float rectHeight = 100;
        float rectX = (screenWidth - rectWidth) / 2;
        float rectY = (screenHeight - rectHeight) / 2;
        shapeRenderer.rect(rectX, rectY, rectWidth, rectHeight);

        shapeRenderer.end();

        textBatch.begin();

        font.setColor(Color.RED);
        int drawIndex = Math.min(wordCount, wordList.size() - 1);
        font.draw(textBatch, wordList.get(0), 100, 100);
        font.draw(textBatch, score.getScoreString(), 300, 100);
        font.draw(textBatch, score.getComboString(), 400, 100);

        textBatch.end();
    }

    @Override
    public void dispose() {
        lineBatch.dispose();
        LineTexture.dispose();
    }
}