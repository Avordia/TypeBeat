package Behavior;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import java.util.ArrayList;

public class KeyHandling extends InputAdapter {
    private ArrayList<Line> leftLine;
    private ArrayList<Line> rightLine;
    ArrayList<String> wordList;
    Score score;
    float centerX = (float) Gdx.graphics.getWidth() / 2;

    String bad="Img/MapSprites/bad.png";
    String good="Img/MapSprites/bad.png";
    String great="Img/MapSprites/bad.png";
    String perfect="Img/MapSprites/bad.png";

    public KeyHandling(ArrayList<Line> leftLine, ArrayList<Line> rightLine, ArrayList<String> wordList, Score score){
        this.leftLine = leftLine;
        this.rightLine = rightLine;
        this.wordList=wordList;
        this.score=score;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.SPACE) {
            if (isLineCloseToCenter(leftLine.get(0))) {
                if(judgeTiming(leftLine.get(0))==0) {
                    score.resetCombo();
                    deleteLines();
                }
                else {
                    scorePoint(1 + (int) calculateTiming(leftLine.get(0)),'/','*');
                    return true;
                }
            }
        }
        return false;
    }
    /*---------------------------FUNCTIONS------------------------------------------*/


    public void scorePoint(float timing, char a, char b){
        if(checkChar('a','a')) {
            score.setScore((2 * ((centerX / 4)) * (float) score.getCombo()));
            score.incrementCombo();
            deleteLines();
        }
        else{
            score.resetCombo();
            deleteLines();
        }
    }
    /*
    public void scorePoint(float timing){
        score.setScore((2*((centerX / 4))* (float)score.getCombo()));
        score.incrementCombo();
        deleteLines();
    }
     */

    public void deleteLines() {
        if (leftLine.get(0).getLineType() == 2) {
                wordList.remove(0);
        }
        if (!leftLine.isEmpty()) {
                leftLine.remove(0);
        }
        if (!rightLine.isEmpty()) {
                rightLine.remove(0);
        }
    }

    private boolean isLineCloseToCenter(Line line) {
        return calculateTiming(line) <= centerX / 2.5;
    }
    private float calculateTiming(Line line) {
        return  Math.abs(line.getX() - centerX);
    }

    private int judgeTiming(Line line){
        if(calculateTiming(line)<=centerX/6){
            return 3;
        }
        else if(calculateTiming(line)<=centerX/5){
            return 2;
        }
        else if(calculateTiming(line)<=centerX/4){
            return 1;
        }
        else{
            score.resetCombo();
            return 0;
        }
    }


    private boolean checkChar(char a, char b){
        return a==b;
    }

    public String TextureJudge(Line line){
        if(judgeTiming(line)==3){
            return perfect;
        }
        if(judgeTiming(line)==2){
            return great;
        }
        if(judgeTiming(line)==1){
            return good;
        }
        return bad;
    }

}
