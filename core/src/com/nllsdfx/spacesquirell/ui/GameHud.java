package com.nllsdfx.spacesquirell.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nllsdfx.spacesquirell.SpaceSquirrel;

import java.util.Locale;
import java.util.Random;

public class GameHud {

    private Label savedLabel;
    private Label sqKilledLabel;
    private Label aliensKilledLabel;
    private Label hpLabel;

    private Stage stage;

    private HudData data;

    private Locale l = new Locale("UTF-8");

    private static Random random = new Random();

    public GameHud(SpriteBatch batch, Viewport viewport, HudData data) {
        Table table = new Table();
        table.top();
        table.setFillParent(true);
        Label.LabelStyle style = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        savedLabel = new Label(String.format(l, "Squirrels saved: %d", data.squirrelsSaved), style);
        sqKilledLabel = new Label(String.format(l, "Squirrels killed: %d", data.squirrelsKilled),
                style);
        hpLabel = new Label(String.format(l, "Health: %d", data.HP),
                style);
        aliensKilledLabel = new Label(String.format(l, "Aliens killed: %d", data.aliensKilled),
                style);
        table.add(hpLabel).expand().padTop(10);
        table.add(savedLabel).expand().padTop(10);
        table.add(sqKilledLabel).expand().padTop(10);
        table.add(aliensKilledLabel).expand().padTop(10);
        table.setY(SpaceSquirrel.HEIGHT / 2 - 15);

        stage = new Stage(viewport, batch);
        stage.addActor(table);
        this.data = data;
    }


    public void draw() {
        sqKilledLabel.setText(String.format(l, "Squirrels killed: %d", data.squirrelsKilled));
        savedLabel.setText(String.format(l, "Squirrels saved: %d", data.squirrelsSaved));
        hpLabel.setText(String.format(l, "Health: %d", data.HP));
        aliensKilledLabel.setText(String.format(l, "Aliens killed: %d", data.aliensKilled));

        if (data.HP >= 100) {
            hpLabel.setColor(Color.GREEN);
        } else if (data.HP <= 30) {
            hpLabel.setColor(Color.RED);
        } else {
            hpLabel.setColor(Color.YELLOW);
        }

        stage.draw();
    }

    public void dispose() {
        stage.dispose();
    }


    public enum HudAction {
        SQUIRREL_SAVED,
        SQUIRREL_KILLED,
        HERO_HIT,
        HERO_HEALED, ALIEN_KILLED
    }

    public static class HudData {
        private int squirrelsSaved;
        private int squirrelsKilled;
        private int aliensKilled;
        private int HP;

        public HudData() {
            squirrelsSaved = 0;
            aliensKilled = 0;
            squirrelsKilled = 0;
            HP = 100;
        }

        public void update(HudAction action) {
            switch (action) {
                case ALIEN_KILLED:
                    ++aliensKilled;
                    break;
                case SQUIRREL_SAVED:
                    ++squirrelsSaved;
                    break;
                case SQUIRREL_KILLED:
                    ++squirrelsKilled;
                    break;
                case HERO_HIT:
                    HP -= GameHud.random.nextInt(11) + 5;
                    break;
                case HERO_HEALED:
                    HP = 100;
                    break;
            }
        }

        public int getAliensKilled() {
            return aliensKilled;
        }

        public int getHP() {
            return HP;
        }

        public int getSquirrelsSaved() {
            return squirrelsSaved;
        }

        public int getSquirrelsKilled() {
            return squirrelsKilled;
        }
    }


}
