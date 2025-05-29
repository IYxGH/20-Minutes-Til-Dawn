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

    //-------------Talents--------------
    VITALITY("Vitality", ""),
    DAMAGER("Damager", ""),
    PROCREASE("Procrease", ""),
    AMOCREASE("Amorecase", ""),
    SPEEDY("Speedy", ""),
    //-----------------------------------

    CHOOSE_ABILITY("Choose Ability:", ""),

    //-----------ScoreBoard------------
    SCOREBOARD_TITLE("Scoreboard" , ""),
    SORT_BY_POINTS("Sort by Points", ""),
    SORT_BY_USERNAME("Sort by Username", ""),
    SORT_BY_KILLS("Sort by Kills", ""),
    SORT_BY_LIFETIME("Sort by Lifetime", ""),
    RANK_HEADER("Rank", ""),
    USERNAME_HEADER("Username", ""),
    SCORE_HEADER("Score", ""),
    KILLS_HEADER("Kills", ""),
    LIFETIME_HEADER("Lifetime", ""),
    //---------------------------------

    //----------Talent--------------
    TALENT_MENU_TITLE("Talent Menu", ""),
    SECTION_HEROES("Heroes", ""),
    SECTION_KEYS("Keys", ""),
    SECTION_CHEATS("Cheats", ""),
    SECTION_ABILITIES("Abilities", ""),
    HERO_NAME_HEADER("Hero Name", ""),
    HERO_DESCRIPTION_HEADER("Description", ""),
    ACTION_HEADER("Action", ""),
    KEY_HEADER("Key", ""),
    CHEAT_CODE_HEADER("Cheat Code", ""),
    CHEAT_EFFECT_HEADER("Effect", ""),
    ABILITY_NAME_HEADER("Ability", ""),
    ABILITY_DESCRIPTION_HEADER("Description", ""),
    //---------------------------------

    //-----------Hero Infoes----------
    SHANA_INFO("Brave and Warrior! SHANA can kill all the MONSTERS!", ""),
    DIAMOND_INFO("No one can kill DIAMOND! don't try!", ""),
    SCARLET_INFO("Fast, Powerful, Clever! SCARLET is here! ", ""),
    LILITH_INFO("Lilith was born to do it!", ""),
    DASHER_INFO("The fastest hero ever!", ""),
    //----------------

    //---------- Action Info-----------
    MOVE_UP_INFO("Move Up", "Mover Arriba"),
    MOVE_DOWN_INFO("Move Down", "Mover Abajo"),
    MOVE_LEFT_INFO("Move Left", "Mover Izquierda"),
    MOVE_RIGHT_INFO("Move Right", "Mover Derecha"),
    TOGGLE_AIM_INFO("Toggle Auto Aim", "Alternar Apuntar"),
    ATTACK_INFO("Attack", "Atacar"),
    PAUSE_INFO("Pause Game", "Pausar Juego"),
    RELOAD_INFO("Reload Weapon", "Recargar Arma"),
    CHEAT_ADD_XP_INFO("Add XP Cheat", "Truco: Añadir XP"),
    CHEAT_ADD_LEVEL_INFO("Add Level Cheat", "Truco: Añadir Nivel"),
    CHEAT_REDUCE_TIME_INFO("Reduce Time Cheat", "Truco: Reducir Tiempo");

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
