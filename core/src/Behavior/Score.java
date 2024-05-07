package Behavior;

public class Score {
    int score=0;
    int combo=1;
    int comboDisplay;
    int highestCombo=0;

    int great=0;
    int perfect=0;
    int good=0;
    int missed=0;



    public int getCombo() {
        return combo;
    }

    public String getScoreString(){
        return String.format(""+this.score);
    }

    public String getComboString(){
        comboDisplay=combo-1;
        return ""+comboDisplay;
    }

    public void resetCombo(){
        if(combo>highestCombo){
            highestCombo=combo;
        }
        combo=1;
    }

    public void setScore(int score) {
        this.score=this.score+score;
    }

    public void incrementCombo(){
        combo++;
    }
    public void incrementPerfect(){
        perfect++;
    }
    public void incrementGreat(){
        great++;
    }
    public void incrementGood(){
        good++;
    }
    public void incrementMissed(){
        missed++;
    }
    public int getScore(){
        return score;
    }
}
