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
        trackList.add(new Track("Song2", "Artist2", 3, "bm/DragonBall.png", "Sound/bm/Track2.mp3"));
        trackList.add(new Track("Song3", "Artist3", 3, "bm/DragonBall.png", "Sound/bm/Track3.mp3"));
        trackCount=trackList.size();
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        stage = new Stage();

        background = new Texture("bm/DragonBall.png");

        Stage stage = new Stage();
        btnPrev = new Button(new TextureRegionDrawable(new TextureRegion(new Texture("Img/Buttons/next.png"))));
        btnPrev.setPosition(10, Gdx.graphics.getHeight() / 2 - btnPrev.getHeight() / 2);

        btnNext = new Button(new TextureRegionDrawable(new TextureRegion(new Texture("Img/Buttons/prev.png"))));
        btnNext.setPosition(Gdx.graphics.getWidth() - 10, Gdx.graphics.getHeight() / 2 - btnPrev.getHeight() / 2);


        stage.addActor(btnPrev);
        stage.addActor(btnNext);

        btnNext.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor fromActor) {
                btnNext.addAction(Actions.sizeTo(100 * 1.2f, 200 * 1.2f, 0.2f, Interpolation.smooth));
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor toActor) {
                btnNext.addAction(Actions.sizeTo(100, 200, 0.2f, Interpolation.smooth));
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectedIndex=(selectedIndex+1)%trackCount;
            }
        });

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 0, 0, 1);

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        batch.end();

        stage.act((Gdx.graphics.getDeltaTime()));
        stage.draw();
    }
}


