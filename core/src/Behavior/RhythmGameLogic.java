package Behavior;

import com.badlogic.gdx.utils.Array;

public class RhythmGameLogic {
    private Array<Float> beatTimes;

    public RhythmGameLogic() {
        beatTimes = new Array<>();
        //Placeholder timing ni naa sa ubos. Measurement is ms so if 1000 kay 1 second/1000ms.
        beatTimes.add(1000f);
        beatTimes.add(2000f);
        beatTimes.add(3000f);
        // Add more beat times as needed
    }

    // Getter for beat times
    public Array<Float> getBeatTimes() {
        return beatTimes;
    }
}