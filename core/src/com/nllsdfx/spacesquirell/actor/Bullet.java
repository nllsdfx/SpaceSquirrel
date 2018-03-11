package com.nllsdfx.spacesquirell.actor;

import com.badlogic.gdx.math.Rectangle;
import com.nllsdfx.spacesquirell.util.Collideable;

public abstract class Bullet implements Collideable{

    private float x, y;
    private Rectangle bounds;

    float speed = 800;


    public Bullet(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        bounds = new Rectangle(x, y, w, h);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setPosition(float x) {
        this.x = x;
        bounds.setPosition(x, y);
    }

    public float getSpeed() {
        return speed;
    }
}
