package com.nllsdfx.spacesquirell.actor;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Hero {

    private float speed = 460f;

    private Vector2 position;

    private Rectangle bounds;

    private Gun gun;

    private int HP = 100;

    public Hero(float x, float y, float w, float h) {
        position = new Vector2(x, y);
        bounds = new Rectangle(x, y, w, h);
        gun = new Gun(x + w, y + h / 2);
    }

    public Bullet fire() {
        return gun.fire(Gun.BulletType.LASER);
    }


    public void move(Direction direction, float delta) {

        switch (direction) {
            case UP:
                position.y += speed * delta;
                break;
            case DOWN:
                position.y -= speed * delta;
                break;
            case LEFT:
                position.x -= speed * delta;
                break;
            case RIGHT:
                position.x += speed * delta;
                break;

        }

        gun.setPosition(position.x + bounds.width, position.y + bounds.height / 2);
        bounds.setPosition(position.x, position.y);
    }

    public float getY() {
        return position.y;
    }

    public float getX() {
        return position.x;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public enum Direction {
        RIGHT, LEFT, DOWN, UP
    }


}
