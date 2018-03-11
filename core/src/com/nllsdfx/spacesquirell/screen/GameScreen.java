package com.nllsdfx.spacesquirell.screen;

import com.badlogic.gdx.Screen;
import com.nllsdfx.spacesquirell.SpaceSquirrel;
import com.nllsdfx.spacesquirell.util.AssetsLoader;
import com.nllsdfx.spacesquirell.util.InputHandler;
import com.nllsdfx.spacesquirell.world.GameRenderer;
import com.nllsdfx.spacesquirell.world.GameWorld;
import com.nllsdfx.spacesquirell.world.impl.GameRendererImpl;
import com.nllsdfx.spacesquirell.world.impl.GameWorldImpl;

public class GameScreen implements Screen {

    private GameWorld gameWorld;

    private GameRenderer renderer;

    private InputHandler inputHandler;

    private SpaceSquirrel game;

    public GameScreen(SpaceSquirrel game) {
        this.game = game;
        AssetsLoader.load();
        gameWorld = new GameWorldImpl();
        renderer = new GameRendererImpl(gameWorld);
        inputHandler = new InputHandler(gameWorld, gameWorld.getBullets());
    }

    @Override
    public void show() {
        AssetsLoader.gameMusic.setLooping(true);
        AssetsLoader.gameMusic.setVolume(0.8f);
        AssetsLoader.gameMusic.play();

    }

    @Override
    public void render(float delta) {
        inputHandler.handle(delta);
        gameWorld.update(delta);
        renderer.render();

        if (gameWorld.getHudData().getHP() <= 0) {
            game.setScreen(new EndScreen(game, gameWorld));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        renderer.resize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        AssetsLoader.dispose();
        renderer.dispose();
    }
}
