package Launcher;

import Screens.Test;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;

public class DevTest { //Gamita ni pang Testing. Adtu mu sa Test class sa Screens folder under src[main] para e change ang screen.

    public static void main (String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.setTitle("TypeBeat");
        config.setInitialBackgroundColor(Color.BLACK);
        config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
        new Lwjgl3Application(new Test(), config);
    }
}