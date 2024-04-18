package Screens;

import com.badlogic.gdx.Game;

public class Test extends Game {
    @Override
    public void create() {
        setScreen(new Menu(this)); // e setscreen if unsa na screen inyong e test.
        //so example, ga test ka sa shop, setScreen(new Shop(this));
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
