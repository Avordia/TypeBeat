package Screens;

import com.badlogic.gdx.Game;

public class Test extends Game {
    @Override
    public void create() {
        setScreen(new TrackSelection(this, "test")); // e setscreen if unsa na screen inyong e test.
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
