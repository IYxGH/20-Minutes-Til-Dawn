package com.untilldown.Model.Enums;

import com.untilldown.Model.App;

public enum Message {
    USERNAME_IS_IN_USE("Username is already in use!", ""),
    USERNAME_IS_SHORT("Username is too short!", ""),
    PROFILE_MENU_BUTTON("Profile Menu", "Menú de Perfil"),
    PASSWORD_IS_WEAK("Password is weak", ""),
    CHANGE_USERNAME("Change Username", ""),
    CHANGE_PASSWORD("Change Password", ""),
    CHANGE_AVATAR("Change Avatar", ""),
    DELETE_ACCOUNT("Delete Account", ""),
    BACK("Back", ""),
    ENTER_NEW_PASSWORD("Enter New Password", ""),
    ENTER_NEW_USERNAME("Enter New Username", ""),
    NEW_PASSWORD("New Password", ""),
    NEW_USERNAME("New Username", ""),
    SET("Set", ""),
    PASSWORD_CHANGED_SUCCESSFULLY("Password changed successfully!", ""),
    USERNAME_CHANGED_SUCCESSFULLY("Username changed successfully!", ""),
    PLAY("Play", ""),
    MINUTES(" Minutes", ""),
    HERO("Hero", ""),
    HP("HP", ""),
    SPEED("Speed", ""),
    RESUME("Resume", ""),
    GIVE_UP("Give Up", ""),

    VITALITY("Vitality", ""),
    DAMAGER("Damager", ""),
    PROCREASE("Procrease", ""),
    AMOCREASE("Amorecase", ""),
    SPEEDY("Speedy", ""),

    ;

    private final String english;
    private final String spanish;

    Message(String english, String spanish) {
        this.english = english;
        this.spanish = spanish;
    }

    public String getMessage() {
        Language language = App.getCurrentLanguage();
        switch (language) {
            case ENGLISH:
                return english;

            case SPANISH:
                return spanish;

            default:
                return null;
        }
    }
}
