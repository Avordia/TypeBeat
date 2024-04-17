package GameDat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class TrackContainer extends Image {

    private Texture coverImage;
    public static final float CONTAINER_WIDTH = 200f;

    public TrackContainer(Track track) {
        super(new Texture(track.getCoverImagePath())); // Set a default black image
        this.coverImage = new Texture(track.getCoverImagePath());
        setSize(200, 200);
        setPosition((float) Gdx.graphics.getWidth() /2, 50);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        // Draw the cover image
        batch.draw(coverImage, getX(), getY(), getWidth(), getHeight());
    }

    public void dispose() {
        coverImage.dispose();
    }
}

