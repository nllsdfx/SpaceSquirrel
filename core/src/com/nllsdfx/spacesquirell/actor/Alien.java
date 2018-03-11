package com.nllsdfx.spacesquirell.actor;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.nllsdfx.spacesquirell.util.Collideable;

public class Alien implements Collideable {

    private Vector2 position;

    private Rectangle bounds;

    private Gun gun;

    private boolean canFire = true;

    public Alien(float x, float y, float w, float h) {
        position = new Vector2(x, y);
        bounds = new Rectangle(x, y, w, h);
        gun = new Gun(x , y + h / 2);
        Timer timer = new Timer();
        timer.scheduleTask(new FireTask(), 0, 5);
    }

    public void move(float delta) {
        float speed = 230f;
        position.x -= speed * delta;
        bounds.x = position.x;
    }

    public void setPosition(float x) {
        position.x = x;
        bounds.setPosition(position.x, position.y);
    }

    public Bullet fire() {
        canFire = false;
        gun.setPosition(position.x, position.y + bounds.height / 2);
        return gun.fire(Gun.BulletType.ALIEN);
    }

    public Vector2 getPosition() {
        return position;
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    public boolean canFire() {
        return canFire;
    }

    private class FireTask extends Timer.Task {
        @Override
        public void run() {
            Alien.this.canFire = true;
        }
    }
}
