package com.untilldown.Controller.ModelControllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.untilldown.Model.MapActor;

public class WorldController {
    private Texture mapTexture;
    private MapActor mapActor;

    public void initWorld(Stage stage) {
        // Load your map texture
        mapTexture = new Texture(Gdx.files.internal("Map/map2.png"));

        // Create an actor for the map
        mapActor = new MapActor(mapTexture);

        // Add the map to the stage
        stage.addActor(mapActor);
        mapActor.setPosition(0,0);
    }

    public Texture getMapTexture() {
        return mapTexture;
    }

    public MapActor getMapActor() {
        return mapActor;
    }
}
