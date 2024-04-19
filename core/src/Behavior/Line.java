package Behavior;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class Line {

    private float x; // X-coordinate of the line
    private float y; // Y-coordinate of the line
    private boolean fromLeft; // Indicates whether the line is spawned from the left side
    private boolean isHit; // Indicates whether the line has been hit by the player

    public Line(boolean fromLeft) {
        this.fromLeft = fromLeft;
        this.isHit = false;
        resetPosition();
    }

    public void resetPosition() {
        if (fromLeft) {
            x = 0;
        } else {
            x = Gdx.graphics.getWidth();
        }
        y = MathUtils.random(0, Gdx.graphics.getHeight());
    }

    public void updatePosition(float delta, float speed){
        if (fromLeft) {
            x += speed * delta;
        } else {
            x -= speed * delta;
        }
    }

    public boolean isPassedCenter() {
        if (fromLeft) {
            return x > (float) Gdx.graphics.getWidth() / 2;
        } else {
            return x < (float) Gdx.graphics.getHeight() / 2;
        }
    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.rectLine(x, y - 20, x, y + 20, 4); // Example drawing of the line
    }

    public boolean isHit() {
        return isHit;
    }

    public void setHit(boolean hit) {
        isHit = hit;
    }
}
