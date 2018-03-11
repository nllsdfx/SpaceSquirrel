package com.nllsdfx.spacesquirell.util;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.nllsdfx.spacesquirell.actor.Bullet;
import com.nllsdfx.spacesquirell.actor.Hero;
import com.nllsdfx.spacesquirell.world.GameWorld;
import com.nllsdfx.spacesquirell.world.impl.GameWorldImpl;

import java.util.List;

public class InputHandler {

    private Hero hero;

    private List<Bullet> bullets;

    private Sound laser;

    private GameWorld world;

    public InputHandler(GameWorld world, List<Bullet> bullets) {
        this.hero = world.getHero();
        this.bullets = bullets;
        laser = AssetsLoader.laser_sound;
        this.world = world;
    }

    public void handle(float delta) {

        if (world.getState() == GameWorldImpl.GameState.PAUSED)
            return;


        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.justTouched()) {
            bullets.add(hero.fire());
            laser.play(0.5f);
        }

        if (Gdx.app.getType() == Application.ApplicationType.Android) {

            float xAcc = Gdx.input.getAccelerometerX();
            float yAcc = Gdx.input.getAccelerometerY();

            if (xAcc < 5f) {
                hero.move(Hero.Direction.UP, delta);
            }

            if (xAcc > 7f) {
                hero.move(Hero.Direction.DOWN, delta);
            }

            if (yAcc <= -2f) {
                hero.move(Hero.Direction.LEFT, delta);
            }

            if (yAcc >= 2f) {
                hero.move(Hero.Direction.RIGHT, delta);
            }

        } else {

            if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
                hero.move(Hero.Direction.UP, delta);
            }

            if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
                hero.move(Hero.Direction.DOWN, delta);
            }

            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
                hero.move(Hero.Direction.RIGHT, delta);
            }

            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
                hero.move(Hero.Direction.LEFT, delta);
            }
        }
    }

}
