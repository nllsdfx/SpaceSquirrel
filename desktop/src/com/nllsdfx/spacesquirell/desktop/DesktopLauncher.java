package com.nllsdfx.spacesquirell.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.nllsdfx.spacesquirell.SpaceSquirrel;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = (int) SpaceSquirrel.WIDTH;
        config.height = (int) SpaceSquirrel.HEIGHT;
        config.title = "Space Squirrel";
        new LwjglApplication(new SpaceSquirrel(), config);
    }
}
