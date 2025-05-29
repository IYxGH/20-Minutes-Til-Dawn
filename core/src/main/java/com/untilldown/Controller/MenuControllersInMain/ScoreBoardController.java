package com.untilldown.Controller.MenuControllersInMain;

import com.untilldown.Controller.MainMenuController;
import com.untilldown.Main;
import com.untilldown.Model.App;
import com.untilldown.Model.GameAssetManager;
import com.untilldown.Model.User;
import com.untilldown.View.MainMenuView;
import com.untilldown.View.MenusViewInMain.ScoreBoardView;

import java.util.*;

/**
 * Controller for the ScoreBoardView.
 * Handles the logic for sorting and retrieving user data for the scoreboard.
 */
public class ScoreBoardController {
    private ScoreBoardView view;

    public ScoreBoardController() {
    }

    /**
     * Sets the view for this controller.
     *
     * @param view The ScoreBoardView instance to set.
     */
    public void setView(ScoreBoardView view) {
        this.view = view;
    }

    /**
     * Retrieves a sorted list of all users by their total points in descending order.
     *
     * @return An ArrayList of User objects, sorted by total points.
     */
    public ArrayList<User> getSortedUsersByPoints() {
        ArrayList<User> users = new ArrayList<>(App.getUsers()); // Create a mutable copy
        Collections.sort(users, new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                // Sort in descending order of total points
                return Integer.compare(u2.getTotalPoints(), u1.getTotalPoints());
            }
        });
        return users;
    }

    /**
     * Retrieves a sorted list of all users by their username in ascending alphabetical order.
     *
     * @return An ArrayList of User objects, sorted by username.
     */
    public ArrayList<User> getSortedUsersByUsername() {
        ArrayList<User> users = new ArrayList<>(App.getUsers()); // Create a mutable copy
        Collections.sort(users, new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                // Sort in ascending alphabetical order of username
                return u1.getUsername().compareToIgnoreCase(u2.getUsername());
            }
        });
        return users;
    }

    /**
     * Retrieves a sorted list of all users by their total kills in descending order.
     *
     * @return An ArrayList of User objects, sorted by total kills.
     */
    public ArrayList<User> getSortedUsersByKills() {
        ArrayList<User> users = new ArrayList<>(App.getUsers()); // Create a mutable copy
        Collections.sort(users, new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                // Sort in descending order of total kills
                return Integer.compare(u2.getTotalKills(), u1.getTotalKills());
            }
        });
        return users;
    }

    /**
     * Retrieves a sorted list of all users by their maximum life time in descending order.
     *
     * @return An ArrayList of User objects, sorted by maximum life time.
     */
    public ArrayList<User> getSortedUsersByLifeTime() {
        ArrayList<User> users = new ArrayList<>(App.getUsers()); // Create a mutable copy
        Collections.sort(users, new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                // Sort in descending order of max life time
                return Float.compare(u2.getMaxLifeTime(), u1.getMaxLifeTime());
            }
        });
        return users;
    }

    /**
     * Retrieves the top N users from a given sorted list.
     *
     * @param sortedUsers The list of users already sorted by the desired criteria.
     * @param count The number of top users to retrieve.
     * @return A List containing the top N users.
     */
    public List<User> getTopUsers(ArrayList<User> sortedUsers, int count) {
        if (sortedUsers == null || sortedUsers.isEmpty()) {
            return new ArrayList<>();
        }
        // Return a sublist up to 'count' or the total number of users if less than 'count'
        return sortedUsers.subList(0, Math.min(sortedUsers.size(), count));
    }

    /**
     * Returns the currently logged-in user.
     *
     * @return The current User object.
     */
    public User getCurrentUser() {
        return App.getCurrentUser();
    }

    public void goBack() {
        Main.getMain().setScreen(new MainMenuView(new MainMenuController(),
            GameAssetManager.getGameAssetManager().getSkin()));
    }

}
