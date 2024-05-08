package Screens;

import com.badlogic.gdx.Game;

public class Test extends Game {
    @Override
    public void create() {
        setScreen(new ScoreScreen(this, 69, 254, "jared123", 1)); // e setscreen if unsa na screen inyong e test.
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
