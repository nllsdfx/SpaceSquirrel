package com.nllsdfx.spacesquirell.actor;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.nllsdfx.spacesquirell.util.Collideable;

public class Rock implements Collideable {

    private Vector2 position;

    private Rectangle bounds;

    public Rock(float x, float y, float w, float h) {
        position = new Vector2(x, y);
        bounds = new Rectangle(x, y, w, h);
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getSpeed() {
        return 200f;
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    public void setPosition(float position) {
        this.position.x = position;
        this.bounds.x = this.position.x;
    }
}
