package Behavior;

import java.util.ArrayList;

public class Thread extends java.lang.Thread {
    ArrayList<Line> leftLine=new ArrayList<>();
    ArrayList<Line> rightLine=new ArrayList<>();

    public Thread(ArrayList<Line> leftLine, ArrayList<Line> rightLine){ //Add music as parameter later
        this.leftLine=leftLine;
        this.rightLine=rightLine;
    }
}
