package com.untilldown.Model.Enums;

import com.untilldown.Model.App;

public enum Message {
    PROFILE_MENU_BUTTON("Profile Menu", "Menú de Perfil"),
    PASSWORD_IS_WEAK("Password is weak", ""),
    CHANGE_USERNAME("Change Username", ""),
    CHANGE_PASSWORD("Change Password", ""),
    CHANGE_AVATAR("Change Avatar", ""),
    DELETE_ACCOUNT("Delete Account", ""),
    BACK("Back", ""),
    ENTER_NEW_PASSWORD("Enter New Password", ""),
    NEW_PASSWORD("New Password", ""),
    SET("Set", ""),
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
