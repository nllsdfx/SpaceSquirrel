package com.nllsdfx.spacesquirell;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nllsdfx.spacesquirell.screen.GameScreen;
import com.nllsdfx.spacesquirell.screen.StartScreen;

public class SpaceSquirrel extends Game {


    public static final float WIDTH = 800;

    public static final float HEIGHT = 480;

    public static Camera camera;

    public static Viewport viewport;

    static {
        camera = new OrthographicCamera();
        viewport = new FitViewport(WIDTH, HEIGHT, camera);
    }

    @Override
    public void create() {
        setScreen(new StartScreen(this));
        Gdx.gl.glClearColor(0, 0, 0, 1);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        screen.dispose();
    }

}
