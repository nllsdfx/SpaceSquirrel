package com.nllsdfx.spacesquirell.actor;

public class Gun {

    private float x;

    private float y;

    public Gun(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Bullet fire(BulletType type) {
        return type == BulletType.LASER ? new LaserBullet(x, y, 15, 3) : new AlienBullet(x, y, 5, 5);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public enum BulletType {
        LASER, ALIEN
    }

}
