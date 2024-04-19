package Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import GameDat.Track;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

public class TrackSelection extends ScreenAdapter {

    private Game game;
    private Stage stage;
    private Texture background;
    private SpriteBatch batch;
    private ArrayList<Track> trackList;
    Button btnPrev;
    Button btnNext;
    int l;
    int c=0;
    int r=1;
    private final int trackCount;
    private Music currentMusic;

    public TrackSelection(Game game) { //Add tracks here
        this.game=game;
        trackList = new ArrayList<Track>();
        trackList.add(new Track("Song1", "Artist1", 3, "bm/DragonBall.png", "Sound/bm/Dragonball.mp3",143));
        trackList.add(new Track("Song2", "Artist2", 1, "bm/Naruto.png", "Sound/bm/Naruto.mp3",143));
        trackList.add(new Track("Song3", "Artist3", 2, "bm/Nora.png", "Sound/bm/Nora.mp3",143));
        trackList.add(new Track("Song4", "Artist4", 2, "bm/Oshi.png", "Sound/bm/Oshi.mp3",143));
        trackCount=trackList.size()-1;
        l=trackCount;
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
        if(l+1>trackCount){
            l=0;
        }
        else{
            l++;
        }

        if(c+1>trackCount){
            c=0;
        }
        else{
            c++;
        }

        if(r+1>trackCount){
            r=0;
        }
        else{
            r++;
        }
    }

    public void traverseLeft() {
        if(l-1<0){
            l=trackCount;
        }
        else{
            l--;
        }

        if(c-1<0){
            c=trackCount;
        }
        else{
            c--;
        }

        if(r-1<0){
            r=trackCount;
        }
        else{
            r--;
        }

    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        stage = new Stage();

        background = new Texture("Img/background2.png");

        btnPrev = new Button(new TextureRegionDrawable(new TextureRegion(new Texture("Img/Buttons/prev.png"))));
        btnPrev.setSize(100,200);

        btnNext = new Button(new TextureRegionDrawable(new TextureRegion(new Texture("Img/Buttons/next.png"))));
        btnNext.setSize(100,200);

        btnPrev.setPosition(35, Gdx.graphics.getHeight() / 2 - btnPrev.getHeight() / 2);
        btnNext.setPosition(Gdx.graphics.getWidth() - (btnNext.getWidth()+35), Gdx.graphics.getHeight() / 2 - btnPrev.getHeight() / 2);

        stage.addActor(btnPrev);
        stage.addActor(btnNext);
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

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        float coverWidthH = 400;
        float coverHeightH = 300;

        float coverWidthS = 250;
        float coverHeightS = 187.5f;

        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(new Texture(trackList.get(l).getCoverImagePath()),200, (float) Gdx.graphics.getHeight() /2 - coverHeightS/2,coverWidthS,coverHeightS);
        batch.draw(new Texture(trackList.get(c).getCoverImagePath()),(float)Gdx.graphics.getWidth()/2 - coverWidthH/2, (float) Gdx.graphics.getHeight() /2 - coverHeightH/2,coverWidthH,coverHeightH);
        batch.draw(new Texture(trackList.get(r).getCoverImagePath()),(float)Gdx.graphics.getWidth()-(coverWidthS+200),(float) Gdx.graphics.getHeight() /2 - coverHeightS/2,coverWidthS,coverHeightS);

        batch.end();

        stage.act(delta);
        stage.draw();
    }

}


