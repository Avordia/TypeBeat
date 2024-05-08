package Screens;

import com.badlogic.gdx.Game;

public class TypeBeat extends Game {
    public String username;
    public TypeBeat(String username){
        this.username=username;
    }
    public String getUsername() {
        return username;
    }
    @Override
    public void create() {
        setScreen(new OpeningScreen(this,username));
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
