package com.untilldown.Model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MapActor extends Actor {
    private Texture texture;

    public MapActor(Texture texture) {
        this.texture = texture;
        setSize(texture.getWidth(), texture.getHeight());
        setPosition(100, 100);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

}
