package Screens;

import com.badlogic.gdx.Game;

public class TypeBeat extends Game {
    @Override
    public void create() {
        setScreen(new Menu(this));
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
