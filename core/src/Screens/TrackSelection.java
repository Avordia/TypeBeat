package Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.AddAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

import GameDat.Track;
import GameDat.TrackContainer;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

public class TrackSelection extends ScreenAdapter {

    private Game game;
    private Stage stage;
    private Texture background;

    private Texture coverLeft;
    private Texture coverCenter;
    private Texture coverRight;
    private SpriteBatch batch;
    private ArrayList<Track> trackList;
    private int selectedIndex=0;
    private Music currentMusic;
    Button btnPrev;
    Button btnNext;
    private final int trackCount;
    public TrackSelection(Game game) { //Add tracks here
        this.game=game;
        trackList = new ArrayList<Track>();
        trackList.add(new Track("Song1", "Artist1", 3, "bm/DragonBall.png", "Sound/bm/Track1.mp3"));
        trackList.add(new Track("Song2", "Artist2", 1, "bm/Naruto.png", "Sound/bm/Track2.mp3"));
        trackList.add(new Track("Song3", "Artist3", 2, "bm/Frieren.png", "Sound/bm/Track3.mp3"));
        trackCount=trackList.size();
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        stage = new Stage();

        background = new Texture("Img/background2.png");
        coverLeft = new Texture("");
        coverCenter = new Texture("");
        coverRight = new Texture("");

        btnPrev = new Button(new TextureRegionDrawable(new TextureRegion(new Texture("Img/Buttons/prev.png"))));
        btnPrev.setSize(100,200);

        btnNext = new Button(new TextureRegionDrawable(new TextureRegion(new Texture("Img/Buttons/next.png"))));
        btnNext.setSize(100,200);

        btnPrev.setPosition(35, Gdx.graphics.getHeight() / 2 - btnPrev.getHeight() / 2);
        btnNext.setPosition(Gdx.graphics.getWidth() - (btnNext.getWidth()+35), Gdx.graphics.getHeight() / 2 - btnPrev.getHeight() / 2);



        stage.addActor(btnPrev);
        stage.addActor(btnNext);

        btnNext.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                SequenceAction scaleAnim = Actions.sequence();

                scaleAnim.addAction(Actions.sizeTo(100*1.15f,200*1.15f,0.1f,Interpolation.smooth));
                scaleAnim.addAction(Actions.sizeTo(100,200,0.1f,Interpolation.smooth));
                btnNext.addAction(scaleAnim);
                selectedIndex=(selectedIndex+1)%trackCount;
            }
        });

        btnPrev.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SequenceAction scaleAnim = Actions.sequence();

                scaleAnim.addAction(Actions.sizeTo(100*1.15f,200*1.15f,0.1f,Interpolation.smooth));
                scaleAnim.addAction(Actions.sizeTo(100,200,0.1f,Interpolation.smooth));
                btnPrev.addAction(scaleAnim);
                selectedIndex=(selectedIndex+1)%trackCount;
            }
        });

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 0, 0, 1);
        float coverWidthH=400;
        float coverHeightH=300;
        float coverWidthS=250;
        float coverHeightS=187.5f;
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(coverLeft,200, (float) Gdx.graphics.getHeight() /2 - coverHeightS/2,coverWidthS,coverHeightS);
        batch.draw(coverCenter,(float)Gdx.graphics.getWidth()/2 - coverWidthH/2, (float) Gdx.graphics.getHeight() /2 - coverHeightH/2,coverWidthH,coverHeightH);
        batch.draw(coverRight,(float)Gdx.graphics.getWidth()-(coverWidthS+200),(float) Gdx.graphics.getHeight() /2 - coverHeightS/2,coverWidthS,coverHeightS);


        batch.end();

        stage.act((Gdx.graphics.getDeltaTime()));
        stage.draw();
    }
}


