package com.nllsdfx.spacesquirell.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class AssetsLoader {

    public static  Sound laser_sound;

    public static Texture heroSprite;

    public static Texture alienSprite;

    public static Sprite squirell;

    private static Texture squirrels;

    public static Texture medkit;

    public static Texture rock;

    public static Sound alienLaserSound;

    public static Sound errorSound;

    public static Music gameMusic;

    public static Skin skin;

    public static void load() {
        heroSprite = load("spaceship.png");
        alienSprite = load("ufo.png");
        squirrels = load("miyamoto_sheet.png");
        squirell = new Sprite(squirrels,25, 68, 25, 29);
        medkit = load("medkit.png");
        rock = load("rock.png");
        laser_sound = Gdx.audio.newSound(Gdx.files.internal("laser.mp3"));
        alienLaserSound = Gdx.audio.newSound(Gdx.files.internal("alien_laser.wav"));
        errorSound = Gdx.audio.newSound(Gdx.files.internal("error.wav"));
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("level1.ogg"));
        skin = new Skin(Gdx.files.internal("uiskin.json"));
    }

    private static Texture load(String path) {
        Texture texture = new Texture(path);
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        return texture;
    }

    public static void dispose() {
        heroSprite.dispose();
        alienSprite.dispose();
        squirrels.dispose();
        medkit.dispose();
        rock.dispose();
        laser_sound.dispose();
        alienLaserSound.dispose();
        gameMusic.dispose();
        skin.dispose();
    }


}
