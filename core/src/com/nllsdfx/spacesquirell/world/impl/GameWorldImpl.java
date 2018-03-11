package com.nllsdfx.spacesquirell.world.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.nllsdfx.spacesquirell.SpaceSquirrel;
import com.nllsdfx.spacesquirell.actor.*;
import com.nllsdfx.spacesquirell.ui.GameHud;
import com.nllsdfx.spacesquirell.util.AssetsLoader;
import com.nllsdfx.spacesquirell.util.CollisionHelper;
import com.nllsdfx.spacesquirell.world.GameWorld;

import java.util.*;

public class GameWorldImpl implements GameWorld {

    private Hero hero;

    private List<Alien> aliens;

    private List<Bullet> bullets;

    private List<Bullet> alienBullets;

    private List<Squirrel> squirrels;

    private Array<Rock> rocks;

    private Random random;

    private GameHud.HudData hudData;

    private FirstAidKit kit;

    private boolean wasKitUsed = false;

    private Preferences preferences;

    private GameState gameState;

    public enum GameState {
        RUNNING, PAUSED
    }

    public GameWorldImpl() {
        hero = new Hero(50, 50, 50, 46);
        bullets = new ArrayList<>(100);
        alienBullets = new ArrayList<>();
        aliens = new LinkedList<>();
        squirrels = new LinkedList<>();
        random = new Random();
        hudData = new GameHud.HudData();
        kit = new FirstAidKit(SpaceSquirrel.WIDTH + 100, random.nextInt((int) (SpaceSquirrel.HEIGHT - 30)), 15, 15);
        rocks = new Array<>();
        preferences = Gdx.app.getPreferences("space_squirrel_game");
        init();
    }

    private void init() {

        for (int i = 0; i < 7; i++) {
            float x = SpaceSquirrel.WIDTH + random.nextInt(2000 - 50) + 1;
            float y = random.nextInt((int) ((SpaceSquirrel.HEIGHT) + 1 - 25));
            aliens.add(new Alien(x, y, 50, 24));

            squirrels.add(new Squirrel(x + 100, random.nextInt((int) SpaceSquirrel.HEIGHT + 1 - (29 * 2)),
                    25f * 1.5f, 29 * 1.5f));

            rocks.add(new Rock(x, y, 35, 35));
        }

        if (preferences.getBoolean("showTip")) {
            gameState = GameState.RUNNING;
        } else {
            gameState = GameState.PAUSED;
        }

    }

    @Override
    public void update(float delta) {

        switch (gameState) {
            case PAUSED:
                updatePause();
                break;
            case RUNNING:
                updateGame(delta);
                break;
        }


    }

    private void updatePause() {
        if (Gdx.input.justTouched()) {
            gameState = GameState.RUNNING;
            preferences.putBoolean("showTip", true);
            preferences.flush();
        }
    }

    private void updateGame(float delta) {
        if (hero.getX() < 0) {
            hero.move(Hero.Direction.RIGHT, delta);
        }

        if (hero.getX() + hero.getBounds().width > SpaceSquirrel.WIDTH) {
            hero.move(Hero.Direction.LEFT, delta);
        }

        if (hero.getY() + hero.getBounds().height > SpaceSquirrel.HEIGHT) {
            hero.move(Hero.Direction.DOWN, delta);
        }

        if (hero.getY() < 0) {
            hero.move(Hero.Direction.UP, delta);
        }

        if (!wasKitUsed && !kit.isActive()
                && ((hudData.getAliensKilled() > 0 && hudData.getAliensKilled() % 10 == 0) ||
                (hudData.getSquirrelsSaved() > 0 && hudData.getSquirrelsSaved() % 20 == 0))) {
            randomizePosition(kit.getPosition(), kit.getBounds());
            kit.setActive(true);
        }

        if (kit.isActive()) {

            kit.setPosition(kit.getPosition().x - kit.getSpeed() * delta);

            if (CollisionHelper.collides(hero.getBounds(), kit.getBounds())) {
                hudData.update(GameHud.HudAction.HERO_HEALED);
                kit.setActive(false);
                wasKitUsed = true;
            }

            if (kit.getPosition().x < -25) {
                kit.setActive(false);
            }
        }

        if (wasKitUsed && hudData.getAliensKilled() % 10 != 0) {
            wasKitUsed = false;
        }

        Iterator<Bullet> iterator = bullets.iterator();

        while (iterator.hasNext()) {

            Bullet bullet = iterator.next();
            bullet.setPosition(bullet.getX() + bullet.getSpeed() * delta);

            Alien alien = (Alien) CollisionHelper.collides(bullet.getBounds(), aliens);

            Squirrel squirrel;

            if (alien != null) {
                hudData.update(GameHud.HudAction.ALIEN_KILLED);
                randomizePosition(alien.getPosition(), alien.getBounds());
                iterator.remove();
            } else if ((squirrel = (Squirrel) CollisionHelper.collides(bullet.getBounds(), squirrels)) != null) {
                hudData.update(GameHud.HudAction.SQUIRREL_KILLED);
                randomizePosition(squirrel.getPosition(), squirrel.getBounds());
                iterator.remove();
            } else if (bullet.getX() > SpaceSquirrel.WIDTH) {
                iterator.remove();
            }

        }

        Iterator<Bullet> iter = alienBullets.iterator();

        while (iter.hasNext()) {

            Bullet bullet = iter.next();
            bullet.setPosition(bullet.getX() - bullet.getSpeed() * delta);

            if (CollisionHelper.collides(bullet.getBounds(), hero.getBounds())) {
                hudData.update(GameHud.HudAction.HERO_HIT);
                iter.remove();
            } else if (bullet.getX() < 0) {
                iter.remove();
            }

        }

        for (Alien alien : aliens) {

            alien.move(delta);

            float x = alien.getPosition().x;

            if (CollisionHelper.collides(alien.getBounds(), hero.getBounds())) {
                hudData.update(GameHud.HudAction.ALIEN_KILLED);
                hudData.update(GameHud.HudAction.HERO_HIT);
                randomizePosition(alien.getPosition(), alien.getBounds());
            }

            if (x < -25) {
                randomizePosition(alien.getPosition(), alien.getBounds());
            }

            if (x < SpaceSquirrel.WIDTH && x > 300 && alien.canFire()) {
                alienBullets.add(alien.fire());
                AssetsLoader.alienLaserSound.play(0.4f);
            }
        }

        for (Squirrel squirrel : squirrels) {
            squirrel.move(delta);

            float x = squirrel.getPosition().x;

            if (x < -25) {
                randomizePosition(squirrel.getPosition(), squirrel.getBounds());
            }

            if (CollisionHelper.collides(squirrel.getBounds(), hero.getBounds())) {
                randomizePosition(squirrel.getPosition(), squirrel.getBounds());
                hudData.update(GameHud.HudAction.SQUIRREL_SAVED);
            }
        }

        for (Rock rock : rocks) {

            rock.setPosition(rock.getPosition().x -= rock.getSpeed() * delta);

            if (CollisionHelper.collides(rock.getBounds(), hero.getBounds())) {
                randomizePosition(rock.getPosition(), rock.getBounds());
                hudData.update(GameHud.HudAction.HERO_HIT);
            }

            if (rock.getPosition().x < -25) {
                randomizePosition(rock.getPosition(), rock.getBounds());
            }


        }
    }


    private void randomizePosition(Vector2 position, Rectangle bounds) {
        position.x = SpaceSquirrel.WIDTH + random.nextInt(1000) + 300;
        position.y = random.nextInt((int) (SpaceSquirrel.HEIGHT - 29));
        bounds.x = position.x;
        bounds.y = position.y;

    }

    @Override
    public Hero getHero() {
        return hero;
    }

    @Override
    public List<Bullet> getBullets() {
        return bullets;
    }

    @Override
    public List<Bullet> getAlienBullets() {
        return alienBullets;
    }

    @Override
    public List<Alien> getAliens() {
        return aliens;
    }

    @Override
    public List<Squirrel> getSquirrels() {
        return squirrels;
    }

    @Override
    public GameHud.HudData getHudData() {
        return hudData;
    }

    @Override
    public FirstAidKit getKit() {
        return kit;
    }

    @Override
    public Array<Rock> getRocks() {
        return rocks;
    }

    @Override
    public GameState getState() {
        return gameState;
    }
}
