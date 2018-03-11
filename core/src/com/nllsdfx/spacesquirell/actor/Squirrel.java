package com.nllsdfx.spacesquirell.actor;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.nllsdfx.spacesquirell.util.Collideable;

public class Squirrel implements Collideable {

    private float speed = 300f;

    private Vector2 position;

    private Rectangle bounds;

    public Squirrel(float x, float y, float w, float h) {
        position = new Vector2(x, y);
        bounds = new Rectangle(x, y, w, h);
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    public void move(float delta) {
        setPosition(position.x -= speed * delta);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(float position) {
        this.position.x = position;
        this.bounds.x = position;
    }
}
