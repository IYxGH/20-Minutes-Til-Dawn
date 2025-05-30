package com.untilldown.Controller.MenuControllersInMain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.untilldown.Model.App;
import com.untilldown.Model.Enums.Action;
import com.untilldown.Model.Enums.Message;
import com.untilldown.Model.Enums.MusicName;
import com.untilldown.View.MenusViewInMain.SettingsView;

public class SettingsController {
    private SettingsView view;

    public void setView(SettingsView view) {
        this.view = view;
    }

    public void changeMusic(MusicName musicName) {
        Music music= App.getCurrentMusic();
        if (App.getCurrentMusicName().equals(musicName)) return;

        App.setCurrentMusicName(musicName);
        Music newMusic = Gdx.audio.newMusic(Gdx.files.internal(musicName.getPath()));
        newMusic.setLooping(true);
        if (music.isPlaying()) newMusic.play();
        else newMusic.stop();
        music.dispose();
        App.setCurrentMusic(newMusic);
    }

    public void changeButton(Action action, int index) {
        for (Action action1 : Action.values()) {
            if (App.gameControls.getKey(action1) == index) {
                view.showTemporaryMessage(Message.PRESS_NEW_KEY.getMessage(), Color.RED);
                return;
            }
        }

        App.gameControls.setKey(action, index);
        view.showTemporaryMessage(Message.CONTROLLER_CHANGED_SUCCESSFULLY.getMessage(), Color.GREEN);

    }
}
