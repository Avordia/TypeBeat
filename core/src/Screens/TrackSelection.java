package Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

import GameDat.Track;
import GameDat.TrackContainer;

public class TrackSelection extends ScreenAdapter {

    private Game game;
    private Stage stage;
    private Array<TrackContainer> trackContainers;
    private int selectedIndex;

    public TrackSelection(Game game) {
        this.game = game;
        this.stage = new Stage();
        initializeTrackContainers();

        selectedIndex =0;
        updateTrackDisplay();
    }

    private void initializeTrackContainers() {
        trackContainers = new Array<>();

        trackContainers.add(new TrackContainer(new Track("Song 1", "Artist 1", "bm/DragonBall.png", 1, "Sound/bm/CHA-LA HEAD CHA-LA - Dragonball Z (Opening Theme) [OST Full].mp3")));
        trackContainers.add(new TrackContainer(new Track("Song 2", "Artist 2", "bm/DragonBall.png", 1, "Sound/bm/CHA-LA HEAD CHA-LA - Dragonball Z (Opening Theme) [OST Full].mp3")));
        trackContainers.add(new TrackContainer(new Track("Song 3", "Artist 3", "bm/DragonBall.png", 1, "Sound/bm/CHA-LA HEAD CHA-LA - Dragonball Z (Opening Theme) [OST Full].mp3")));
    }

    private void updateTrackDisplay() {
        stage.clear();

        float screenWidth = Gdx.graphics.getWidth();
        float containerWidth = 200;
        float spacing = 50;
        float startX = (screenWidth - containerWidth) / 2 - (selectedIndex - 1) * (containerWidth + spacing);

        // Display three track containers centered on the screen
        for (int i = 0; i < 3; i++) {
            int index = selectedIndex + i - 1;
            if (index >= 0 && index < trackContainers.size) {
                TrackContainer trackContainer = trackContainers.get(index);
                trackContainer.setPosition(startX + i * (containerWidth + spacing), 100);
                stage.addActor(trackContainer);

                // Enlarge the center track container
               // if (i == 1) {
               //     trackContainer.enlarge();
               // } else {
               //     trackContainer.shrink();
               // }
            }
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}