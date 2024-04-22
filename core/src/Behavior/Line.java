package Behavior;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Line {
    private Texture texture;
    private float beatTime;
    private float startX;
    private float x;
    private float y;

    public Line(float beatTime, Texture texture, float startX) {
        this.beatTime = beatTime;
        this.texture = texture;
        this.startX = startX;
        this.y= Gdx.graphics.getHeight()-texture.getHeight();
    }

    public void updatePosition(float elapsedTime, float targetX) { ///Study this shit
        float duration = targetX - startX;
        if (elapsedTime >= beatTime) {
            x = targetX;
        } else {
            float progress = elapsedTime / beatTime;
            x = startX + progress * duration;
        }
    }

    public float getX() {
        return x;
    }

    public Texture getTexture() {
        return texture;
    }

    public float getY() {
        return y;
    }
}
