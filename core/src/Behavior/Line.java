package Behavior;

public class Line {
    private float x;
    private float y;
    private float speed;

    public Line(float x, float y, float speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void update(float delta) {
        x += speed * delta;
    }
}
