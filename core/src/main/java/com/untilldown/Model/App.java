package com.untilldown.Model;

import com.untilldown.Controller.GameController;
import com.untilldown.Model.Enums.Language;

import java.util.ArrayList;

public class App {
    private static ArrayList<User> users = new ArrayList<>();
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
        return users;
    }

    public static void setUsers(ArrayList<User> users) {
        App.users = users;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        App.currentUser = currentUser;
    }

    public static User findUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public static Language getCurrentLanguage() {
        return currentLanguage;
    }
}
