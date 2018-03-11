package com.nllsdfx.spacesquirell.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.nllsdfx.spacesquirell.SpaceSquirrel;
import com.nllsdfx.spacesquirell.ui.GameHud;
import com.nllsdfx.spacesquirell.util.AssetsLoader;
import com.nllsdfx.spacesquirell.world.GameWorld;

import java.util.Locale;

public class EndScreen extends ScreenAdapter {

    private GameWorld world;

    private SpaceSquirrel game;

    private Locale l = new Locale("UTF-8");

    private Stage stage;


    EndScreen(SpaceSquirrel game, GameWorld world) {
        this.game = game;
        this.world = world;
        stage = new Stage(SpaceSquirrel.viewport);
        Gdx.input.setInputProcessor(stage);
        init();

    }

    @Override
    public void show() {
        AssetsLoader.errorSound.play();
    }

    private void init() {
        GameHud.HudData data = world.getHudData();
        Table table = new Table();
        table.center();
        table.setFillParent(true);
        Label.LabelStyle style = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Label gameOver = new Label("GAME OVER", style);
        gameOver.setFontScale(2);
        table.add(gameOver).colspan(3).pad(50);
        table.row();
        Label savedLabel = new Label(String.format(l, "Squirrels saved: %d", data.getSquirrelsSaved()), style);
        Label sqKilledLabel = new Label(String.format(l, "Squirrels killed: %d", data.getSquirrelsKilled()),
                style);
        Label aliensKilledLabel = new Label(String.format(l, "Aliens killed: %d", data.getAliensKilled()),
                style);
        table.add(savedLabel).pad(10);
        table.add(sqKilledLabel).pad(10);
        table.add(aliensKilledLabel).pad(10);
        table.row();

        Label menu = new Label("MENU", style);
        menu.setFontScale(1.5f);
        table.add(menu).colspan(3).pad(50);
        table.row();
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.justTouched()) {
            game.setScreen(new StartScreen(game));
            dispose();
        }

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        SpaceSquirrel.viewport.update(width, height);
    }

    @Override
    public void dispose() {
        stage.dispose();
        AssetsLoader.errorSound.dispose();
    }
}
