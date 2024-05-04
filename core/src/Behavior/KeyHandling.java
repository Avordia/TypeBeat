package Behavior;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.audio.Sound;

import java.util.ArrayList;

public class KeyHandling extends InputAdapter {
    int deadLines=0;
    Sound kick;
    Sound snare;
    private ArrayList<Line> leftLine;
    private ArrayList<Line> rightLine;
    private ArrayList<String> wordList;
    private ArrayList<Integer> isDead;
    Score score;
    float centerX = (float) Gdx.graphics.getWidth() / 2;

    String bad="Img/MapSprites/bad.png";
    String good="Img/MapSprites/bad.png";
    String great="Img/MapSprites/bad.png";
    String perfect="Img/MapSprites/bad.png";

    public KeyHandling(ArrayList<Line> leftLine, ArrayList<Line> rightLine, ArrayList<String> wordList, ArrayList<Integer> isDead,
                       Score score, Sound kick, Sound snare){
        this.leftLine = leftLine;
        this.rightLine = rightLine;
        this.wordList=wordList;
        this.score=score;
        this.isDead=isDead;
        this.kick=kick;
        this.snare=snare;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (leftLine.isEmpty() || rightLine.isEmpty()) {
            return false;
        }
        int judge=judgeTiming(leftLine.get(0));
        if (keycode == Input.Keys.SPACE) {
            if (isLineCloseToCenter(leftLine.get(0))) {
                if(judge==0) {
                    score.resetCombo();
                }
                else if(judge==1) {
                    scorePoint(1 + (int) calculateTiming(leftLine.get(0)),leftLine.get(0).getLetter(),'/',judge);
                    return true;
                }
                else if(judge==2) {
                    scorePoint(1 + (int) calculateTiming(leftLine.get(0)),leftLine.get(0).getLetter(),'/',judge);
                    return true;
                }
                else if(judge==3) {
                    scorePoint(1 + (int) calculateTiming(leftLine.get(0)),leftLine.get(0).getLetter(),'/',judge);
                    return true;
                }
                deleteLines(0);
            }
        }
        else if(keycode >= Input.Keys.A && keycode <= Input.Keys.Z){
            char keyPressed = (char) (keycode - Input.Keys.A + 'A');
            if (isLineCloseToCenter(leftLine.get(0))) {
                if(judge==0) {
                    score.resetCombo();
                }
                else if(judge==1) {
                    scorePoint(1 + (int) calculateTiming(leftLine.get(0)),leftLine.get(0).getLetter(),'/',judge);
                    return true;
                }
                else if(judge==2) {
                    scorePoint(1 + (int) calculateTiming(leftLine.get(0)),leftLine.get(0).getLetter(),'/',judge);
                    return true;
                }
                else if(judge==3) {
                    scorePoint(1 + (int) calculateTiming(leftLine.get(0)),leftLine.get(0).getLetter(),'/',judge);
                    return true;
                }
                deleteLines(0);
            }
        }
        return false;
    }

    /*---------------------------FUNCTIONS------------------------------------------*/

    public void scorePoint(float timing, char a, char b, int judge) {
        if (checkChar(a, b)) {
            float maxTiming = centerX / 6;
            float relativeTiming = timing / maxTiming;

            float baseScore = 1000;
            float timingScore = baseScore * (1 - relativeTiming);

            score.setScore(timingScore *score.getCombo());
            score.incrementCombo();

            deleteLines(judge);
        } else {
            score.resetCombo();
            deleteLines(judge);
        }
    }

    public void deleteLines(int judge) {
        if (leftLine.get(0).getLineType() == 2) {
            snare.play();
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
        else {
            if (!leftLine.isEmpty()) {
                leftLine.remove(0);
            }
            if (!rightLine.isEmpty()) {
                rightLine.remove(0);
            }
            kick.play();
        }

        isDead.set(deadLines,judge);
        deadLines++;
    }

    private boolean isLineCloseToCenter(Line line) {
        return calculateTiming(line) <= centerX / 2.5;
    }

    private float calculateTiming(Line line) {
        return  Math.abs(line.getX() - centerX);
    }

    public int judgeTiming(Line line) {
        float timing = calculateTiming(line);

        float perfectThreshold = centerX / 10;
        float greatThreshold = centerX / 6;
        float goodThreshold = centerX / 4.2f;

        // Determine the judgment based on the calculated timing
        if (timing <= perfectThreshold) {
            return 3;
        } else if (timing <= greatThreshold) {
            return 2;
        } else if (timing <= goodThreshold) {
            return 1;
        } else {
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

    public String TextureJudge(){
        if(judgeTiming(leftLine.get(0))==3){
            return perfect;
        }
        if(judgeTiming(leftLine.get(0))==2){
            return great;
        }
        if(judgeTiming(leftLine.get(0))==1){
            return good;
        }
        return bad;
    }

    public void incrementDeath(){
        isDead.set(deadLines,0);
        deadLines++;
    }

    public int getDeath(){
        return deadLines;
    }

    public void setDeath(int death){
        deadLines=death;
    }

}