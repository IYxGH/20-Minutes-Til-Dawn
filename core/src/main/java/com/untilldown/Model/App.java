package com.untilldown.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.untilldown.Controller.GameController;
import com.untilldown.DataBase.UserDataManager;
import com.untilldown.Model.Enums.Language;
import com.untilldown.Model.Enums.MusicName;

import java.util.ArrayList;

public class App {
    private static final UserDataManager userDataManager = new UserDataManager();
    private static User currentUser;
    private static Language currentLanguage = Language.ENGLISH;
    private static ArrayList<Game> games = new ArrayList<>();
    private static Game activeGame;
    public static GameControls gameControls = new GameControls();

    // Music and sounds
    private static MusicName currentMusicName = MusicName.MUSIC4;
    private static Music currentMusic = Gdx.audio.newMusic(Gdx.files.internal(MusicName.MUSIC4.getPath()));
    private static boolean SFXon = true;

    static {
        Pixmap pixmap = new Pixmap(Gdx.files.internal("T_Cursor.png"));
        Cursor cursor = Gdx.graphics.newCursor(pixmap, 0, 0); // hotspot at top-left (0, 0)
        Gdx.graphics.setCursor(cursor);
        pixmap.dispose();
        currentMusic.setLooping(true);
        currentMusic.play();
    }

    public static ArrayList<Game> getGames() {
        return games;
    }


    public static Game getActiveGame() {
        return activeGame;
    }

    public static void setActiveGame(Game activeGame) {
        App.activeGame = activeGame;
    }

    public static ArrayList<User> getUsers() {
        return userDataManager.getAllUsers();
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        App.currentUser = currentUser;
    }

    public static MusicName getCurrentMusicName() {
        return currentMusicName;
    }

    public static void setCurrentMusicName(MusicName currentMusicName) {
        App.currentMusicName = currentMusicName;
    }

    public static boolean isSFXon() {
        return SFXon;
    }

    public static void setSFXon(boolean SFXon) {
        App.SFXon = SFXon;
    }

    public static void toggleSFXon() {
        SFXon = !SFXon;
    }

    public static User findUser(String username) {
        return userDataManager.findUser(username);
    }

    public static boolean updateUserInDatabase(User user) {
        return userDataManager.updateUser(user);
    }

    public static boolean updateUserInDatabase(String oldUsername, User user) {
        return userDataManager.updateUser(oldUsername, user);
    }

    public static boolean removeUserFromDatabase(String username) {
        return userDataManager.removeUser(username);
    }

    public static boolean registerUser(User user) {
        return userDataManager.addUser(user);
    }

    public static Language getCurrentLanguage() {
        return currentLanguage;
    }

    public static void setCurrentLanguage(Language currentLanguage) {
        App.currentLanguage = currentLanguage;
    }

    public static void setGames(ArrayList<Game> games) {
        App.games = games;
    }

    public static Music getCurrentMusic() {
        return currentMusic;
    }

    public static void setCurrentMusic(Music currentMusic) {
        App.currentMusic = currentMusic;
    }

    public static void setCurrentMusic(String path) {
        currentMusic = Gdx.audio.newMusic(Gdx.files.internal(path));
    }

    public static void toggleMusic() {
        if (currentMusic.isPlaying()) {
            currentMusic.stop();
        } else {
            currentMusic.play();
        }
    }

    public static void setVolume(float volume) {
        currentMusic.setVolume(volume);
    }
}
