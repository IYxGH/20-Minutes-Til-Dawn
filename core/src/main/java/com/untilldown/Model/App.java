package com.untilldown.Model;

import com.untilldown.Controller.GameController;
import com.untilldown.DataBase.UserDataManager;
import com.untilldown.Model.Enums.Language;

import java.util.ArrayList;

public class App {
    private static final UserDataManager userDataManager = new UserDataManager();
    private static User currentUser;
    private static Language currentLanguage = Language.ENGLISH;
    private static ArrayList<Game> games = new ArrayList<>();
    private static Game activeGame;
    public static GameControls gameControls = new GameControls();

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
}
