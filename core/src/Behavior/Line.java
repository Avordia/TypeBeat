package Behavior;

public class Line {
    private float x; // Left position of the line
    private float speed; // Speed of the line

    public Line(float x, float speed) {
        this.x = x;
        this.speed = speed;
    }

    public void update(float delta) {
        x += speed * delta;
    }

    public float getX() {
        return x;
    }

}