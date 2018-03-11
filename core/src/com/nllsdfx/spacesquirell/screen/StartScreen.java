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

public class StartScreen extends ScreenAdapter {

    private Stage stage;

    private Label.LabelStyle style;

    private SpaceSquirrel game;

    public StartScreen(SpaceSquirrel game) {
        this.game = game;
        stage = new Stage(SpaceSquirrel.viewport);
        Gdx.input.setInputProcessor(stage);
        style = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        init();
    }

    private void init() {


        Table table = new Table();
        table.top();
        table.setFillParent(true);

        Label title = new Label("Space Squirrel", style);
        title.setFontScale(2);
        table.add(title).pad(100);
        table.row();

        Label start = new Label("START", style);
        start.setFontScale(1.5f);
        table.add(start);
        table.row();

        stage.addActor(table);

    }

    @Override
    public void resize(int width, int height) {
        SpaceSquirrel.viewport.update(width, height);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.justTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }

        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
