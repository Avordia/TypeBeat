package Behavior;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import java.util.ArrayList;

public class KeyHandling extends InputAdapter {
    private ArrayList<Line> leftLine;
    private ArrayList<Line> rightLine;

    public KeyHandling(ArrayList<Line> leftLine, ArrayList<Line> rightLine) {
        this.leftLine = leftLine;
        this.rightLine = rightLine;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.SPACE) {
            if (!leftLine.isEmpty()) {
                leftLine.remove(0);
            }
            if (!rightLine.isEmpty()) {
                rightLine.remove(0);
            }
            return true;
        }
        return false;
    }
}
