package com.untilldown.Controller.ModelControllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.untilldown.Model.App;
import com.untilldown.Model.EnemyClasses.Tree;
import com.untilldown.Model.Game;
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

        initEnemies(stage);
    }

    public void initEnemies(Stage stage) {
        //put trees
        Game game = App.getActiveGame();
        for (int i = 0; i < 30 + (Math.random() * 50); i++) {
            //TODO: make them less random if have time
            Tree tree = new Tree();
            float x = (float) (Math.random() * 2800 + 100);
            float y = (float) (Math.random() * 1500 + 100);
            tree.setPosition(x, y);
            stage.addActor(tree);
            game.getEnemies().add(tree);
        }
    }

    public Texture getMapTexture() {
        return mapTexture;
    }

    public MapActor getMapActor() {
        return mapActor;
    }
}
