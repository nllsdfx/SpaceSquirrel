package com.nllsdfx.spacesquirell.world.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nllsdfx.spacesquirell.SpaceSquirrel;
import com.nllsdfx.spacesquirell.actor.*;
import com.nllsdfx.spacesquirell.ui.GameHud;
import com.nllsdfx.spacesquirell.util.AssetsLoader;
import com.nllsdfx.spacesquirell.world.GameRenderer;
import com.nllsdfx.spacesquirell.world.GameWorld;

import java.util.List;
import java.util.Random;

public class GameRendererImpl implements GameRenderer {

    private Viewport viewport;


    private SpriteBatch batch;

    private Hero hero;

    private ShapeRenderer shapeRenderer;

    private List<Bullet> bullets;

    private List<Bullet> alienBullets;

    private List<Alien> aliens;

    private List<Squirrel> squirrels;

    private FirstAidKit kit;

    private Array<Rock> rocks;

    private GameHud hud;

    private Dialog dialog;

    private Stage stage;

    private GameWorld world;

    private Random random = new Random();

    public GameRendererImpl(GameWorld world) {
        viewport = SpaceSquirrel.viewport;
        batch = new SpriteBatch();
        hero = world.getHero();
        bullets = world.getBullets();
        alienBullets = world.getAlienBullets();
        aliens = world.getAliens();
        squirrels = world.getSquirrels();
        kit = world.getKit();
        rocks = world.getRocks();
        shapeRenderer = new ShapeRenderer();
        hud = new GameHud(batch, viewport, world.getHudData());
        stage = new Stage(SpaceSquirrel.viewport);
        dialog = new Dialog("How to play", AssetsLoader.skin);
        this.world = world;
        Gdx.input.setInputProcessor(stage);

        dialog.text("Avoid invulnerable rocks by inclining your phone. Touch to fire laser and destroy aliens.\n" +
                "Pick up squirrels if you can. After killing 10 aliens or saving 20 squirrels\n" +
                "a medicine kit appears. Good luck!");
        dialog.button("OK!");
        stage.addActor(dialog);
        dialog.show(stage);


    }



    @Override
    public void render() {

        batch.setProjectionMatrix(SpaceSquirrel.camera.combined);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (world.getState() == GameWorldImpl.GameState.PAUSED) {
            stage.act();
            stage.draw();
        }

        batch.begin();
        Texture heroSprite = AssetsLoader.heroSprite;
        batch.draw(heroSprite, hero.getX(), hero.getY(), heroSprite.getWidth(), heroSprite.getHeight());
        for (Alien alien : aliens) {
            batch.draw(AssetsLoader.alienSprite, alien.getPosition().x, alien.getPosition().y,
                    alien.getBounds().width, alien.getBounds().height);
        }

        for (Squirrel squirrel : squirrels) {
            Sprite sq = AssetsLoader.squirell;
            sq.rotate(random.nextFloat());
            sq.setPosition(squirrel.getPosition().x, squirrel.getPosition().y);
            sq.draw(batch);
        }

        for (Rock rock : rocks) {
            batch.draw(AssetsLoader.rock, rock.getPosition().x, rock.getPosition().y,
                    rock.getBounds().width, rock.getBounds().height);
        }

        if (kit.isActive()) {
            batch.draw(AssetsLoader.medkit, kit.getPosition().x, kit.getPosition().y, kit.getBounds().width, kit.getBounds().height);
        }

        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);

        for (Bullet bullet : bullets) {
            shapeRenderer.rect(bullet.getX(), bullet.getY(), LaserBullet.W, LaserBullet.H);
        }

        shapeRenderer.setColor(Color.YELLOW);

        for (Bullet alienBullet : alienBullets) {
            shapeRenderer.rect(
                    alienBullet.getX(),
                    alienBullet.getY(),
                    alienBullet.getBounds().width,
                    alienBullet.getBounds().height);
        }

        shapeRenderer.end();
        hud.draw();

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        shapeRenderer.setProjectionMatrix(SpaceSquirrel.camera.combined);
    }

    @Override
    public void dispose() {
        hud.dispose();
        stage.dispose();
    }
}
