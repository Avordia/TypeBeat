package Behavior;

import com.badlogic.gdx.Gdx;
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
            if(isLineCloseToCenter(leftLine.get(0))) {
                deleteLines();
                return true;
            }
        }
        return false;
    }

    public void deleteLines() {
        if (!leftLine.isEmpty()) {
            leftLine.remove(0);
        }
        if (!rightLine.isEmpty()) {
            rightLine.remove(0);
        }
    }

    private boolean isLineCloseToCenter(Line line) {
        float centerX = (float) Gdx.graphics.getWidth() / 2;
        float distanceToCenter = Math.abs(line.getX() - centerX);
        float spawnToCenterDistance = centerX;
        return distanceToCenter <= spawnToCenterDistance / 2.2;
    }


}