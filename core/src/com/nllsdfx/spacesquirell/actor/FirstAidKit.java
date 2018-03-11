package com.nllsdfx.spacesquirell.actor;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.nllsdfx.spacesquirell.util.Collideable;

public class FirstAidKit implements Collideable {

    private Vector2 position;

    private Rectangle bounds;

    private boolean active;

    private float speed;

    public FirstAidKit(float x, float y, float w, float h) {
        position = new Vector2(x, y);
        bounds = new Rectangle(x, y, w, h);
        active = false;
        speed = 300f;
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    public void setPosition(float x) {
        this.position.x = x;
        this.bounds.setPosition(position.x, position.y);
    }

    public Vector2 getPosition() {
        return position;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public float getSpeed() {
        return speed;
    }
}
