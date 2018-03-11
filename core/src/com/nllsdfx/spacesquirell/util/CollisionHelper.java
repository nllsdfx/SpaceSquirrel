package com.nllsdfx.spacesquirell.util;

import com.badlogic.gdx.math.Rectangle;

import java.util.List;

public class CollisionHelper {

    private CollisionHelper() {}

    public static boolean collides(Rectangle r1, Rectangle r2) {
        return r1.overlaps(r2);
    }

    public static Collideable collides(Rectangle r1, List<? extends Collideable> objects) {
        for (Collideable object : objects) {
            if (object.getBounds().overlaps(r1)) {
                return object;
            }
        }

        return null;
    }
}
