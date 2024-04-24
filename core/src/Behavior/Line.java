package Behavior;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;

public class Line extends InputAdapter {
    private Texture texture;
    private float beatTime;
    private float startX;
    private float x;

    private float y;

    public Line(float beatTime, Texture texture, float startX) {
        this.beatTime = beatTime;
        this.texture = texture;
        this.startX = startX;
        this.x = startX;
        this.y = Gdx.graphics.getHeight() * 0.1f;
    }

    public void updatePosition(float elapsedTime, float targetX) {
        float duration = targetX - startX;
        if (elapsedTime >= beatTime) {
            x = targetX;
            if (x >= targetX) {
                x = -texture.getWidth();
            }
        } else {
            float progress = elapsedTime / beatTime;
            x = startX + progress * duration;
        }
    }

    public Texture getTexture() {
        return texture;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

}