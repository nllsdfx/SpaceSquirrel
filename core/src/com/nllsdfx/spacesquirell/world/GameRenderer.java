package com.nllsdfx.spacesquirell.world;

public interface GameRenderer {
    void render();

    void resize(int width, int height);

    void dispose();
}
