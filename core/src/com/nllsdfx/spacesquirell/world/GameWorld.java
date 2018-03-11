package com.nllsdfx.spacesquirell.world;

import com.badlogic.gdx.utils.Array;
import com.nllsdfx.spacesquirell.actor.*;
import com.nllsdfx.spacesquirell.ui.GameHud;
import com.nllsdfx.spacesquirell.world.impl.GameWorldImpl;

import java.util.List;

public interface GameWorld {

    void update(float delta);

    Hero getHero();

    List<Bullet> getBullets();

    List<Bullet> getAlienBullets();

    List<Alien> getAliens();

    List<Squirrel> getSquirrels();

    GameHud.HudData getHudData();

    FirstAidKit getKit();

    Array<Rock> getRocks();

    GameWorldImpl.GameState getState();
}
