package Behavior;

public class Score {
    float score=0;
    int combo=1;
    int comboDisplay;


    public int getCombo() {
        return combo;
    }

    public String getScoreString(){
        return String.format("%.0f", this.score);
    }

    public String getComboString(){
        comboDisplay=combo-1;
        return ""+comboDisplay;
    }

    public void resetCombo(){
        combo=1;
    }

    public void setScore(float score) {
        this.score=this.score+score;
    }

    public void incrementCombo(){
        combo++;
    }
}
