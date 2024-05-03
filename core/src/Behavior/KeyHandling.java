package Behavior;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import java.util.ArrayList;

public class KeyHandling extends InputAdapter {
    int deadLines=0;
    int deadWords=0;
    private ArrayList<Line> leftLine;
    private ArrayList<Line> rightLine;
    private ArrayList<String> wordList;
    private ArrayList<Boolean> isDead;
    Score score;
    float centerX = (float) Gdx.graphics.getWidth() / 2;

    String bad="Img/MapSprites/bad.png";
    String good="Img/MapSprites/bad.png";
    String great="Img/MapSprites/bad.png";
    String perfect="Img/MapSprites/bad.png";

    public KeyHandling(ArrayList<Line> leftLine, ArrayList<Line> rightLine, ArrayList<String> wordList, ArrayList<Boolean> isDead, Score score){
        this.leftLine = leftLine;
        this.rightLine = rightLine;
        this.wordList=wordList;
        this.score=score;
        this.isDead=isDead;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.SPACE) {
            if (isLineCloseToCenter(leftLine.get(0))) {
                if(judgeTiming(leftLine.get(0))==0) {
                    score.resetCombo();
                }
                else {
                    scorePoint(1 + (int) calculateTiming(leftLine.get(0)),leftLine.get(0).getLetter(),'/');
                    return true;
                }
                deleteLines();
            }
        }
        else if(keycode >= Input.Keys.A && keycode <= Input.Keys.Z){
            char keyPressed = (char) (keycode - Input.Keys.A + 'A');
            if (isLineCloseToCenter(leftLine.get(0))) {
                if(judgeTiming(leftLine.get(0))==0) {
                    score.resetCombo();
                }
                else {
                    scorePoint(1 + (int) calculateTiming(leftLine.get(0)),leftLine.get(0).getLetter(),Character.toUpperCase(keyPressed));
                    return true;
                }
                deleteLines();
            }

        }
        return false;
    }

    /*---------------------------FUNCTIONS------------------------------------------*/

    public void scorePoint(float timing, char a, char b) {
        if (checkChar(a, b)) {
            float maxTiming = centerX / 6;
            float relativeTiming = timing / maxTiming;

            float baseScore = 1000;
            float timingScore = baseScore * (1 - relativeTiming);

            score.setScore(timingScore *score.getCombo());
            score.incrementCombo();

            deleteLines();
        } else {
            score.resetCombo();
            deleteLines();
        }
    }

    public void deleteLines() {
        if (leftLine.get(0).getLineType() == 2) {
            for(int i=0; i<wordList.get(0).length(); i++){
                isDead.remove(0);
            }
            deadLines=0;
            wordList.remove(0);
            if (!leftLine.isEmpty()) {
                leftLine.remove(0);
            }
            if (!rightLine.isEmpty()) {
                rightLine.remove(0);
            }
            return;
        }
        if (!leftLine.isEmpty()) {
            leftLine.remove(0);
        }
        if (!rightLine.isEmpty()) {
            rightLine.remove(0);
        }

        isDead.set(deadLines,true);
        deadLines++;
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
        if(a=='*' & b=='/'){
            return true;
        }
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

    public void incrementDeath(){
        isDead.set(deadLines,true);
        deadLines++;
    }

    public int getDeath(){
        return deadLines;
    }

    public void setDeath(int death){
        deadLines=death;
    }

}