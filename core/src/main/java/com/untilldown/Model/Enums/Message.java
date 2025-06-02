package com.untilldown.Model.Enums;

import com.untilldown.Model.App;

public enum Message {
    USERNAME_IS_IN_USE("Username is already in use!", "¡Nombre de usuario ya en uso!"),
    USERNAME_IS_SHORT("Username is too short!", "¡Nombre de usuario demasiado corto!"),
    PROFILE_MENU_BUTTON("Profile Menu", "Menú de Perfil"),
    PASSWORD_IS_WEAK("Password is weak", "Contraseña débil"),
    CHANGE_USERNAME("Change Username", "Cambiar Nombre de Usuario"),
    CHANGE_PASSWORD("Change Password", "Cambiar Contraseña"),
    CHANGE_AVATAR("Change Avatar", "Cambiar Avatar"),
    YES_BUTTON("Yes", "Sí"),
    NO_BUTTON("No", "No"),
    BACK("Back", "Atrás"),
    ENTER_NEW_PASSWORD("Enter New Password", "Ingresar Nueva Contraseña"),
    ENTER_NEW_USERNAME("Enter New Username", "Ingresar Nuevo Nombre de Usuario"),
    NEW_PASSWORD("New Password", "Nueva Contraseña"),
    NEW_USERNAME("New Username", "Nuevo Nombre de Usuario"),
    SET("Set", "Establecer"),
    PASSWORD_CHANGED_SUCCESSFULLY("Password changed successfully!", "¡Contraseña cambiada con éxito!"),
    USERNAME_CHANGED_SUCCESSFULLY("Username changed successfully!", "¡Nombre de usuario cambiado con éxito!"),
    PLAY("Play", "Jugar"),
    MINUTES(" Minutes", " Minutos"),
    HERO("Hero", "Héroe"),
    HP("HP", "PV"),
    SPEED("Speed", "Velocidad"),
    RESUME("Resume", "Reanudar"),
    GIVE_UP("Give Up", "Rendirse"),
    TIME_LEFT("Time Left:", "Tiempo Restante:"),
    KILLS("Kills: ", "Muertes: "),
    WON("Won!", "¡Ganaste!"),
    DEAD("Dead...", "Muerto..."),
    CONTINUE("Continue", "Continuar"),

    //-------------Talents--------------
    VITALITY("Vitality", "Vitalidad"),
    DAMAGER("Damager", "Daño"),
    PROCREASE("Procrease", "Procreación"),
    AMOCREASE("Amorecase", "Incremento de Munición"),
    SPEEDY("Speedy", "Veloz"),
    VITALITY_INFO("add one health", "añade una vida"),
    DAMAGER_INFO("more damage for 10 seconds", "más daño durante 10 segundos"),
    PROCREASE_INFO("add one projectile", "añade un proyectil"),
    AMOCREASE_INFO("add 5 ammo to MaxAmmo", "añade 5 de munición a la Munición Máxima"),
    SPEEDY_INFO("2X speed for 10 seconds", "2X velocidad durante 10 segundos"),
//-----------------------------------

    CHOOSE_ABILITY("Choose Ability:", "Elegir Habilidad:"),

    //-----------ScoreBoard------------
    SCOREBOARD_TITLE("Scoreboard" , "Marcador"),
    SORT_BY_POINTS("Sort by Points", "Ordenar por Puntos"),
    SORT_BY_USERNAME("Sort by Username", "Ordenar por Nombre de Usuario"),
    SORT_BY_KILLS("Sort by Kills", "Ordenar por Muertes"),
    SORT_BY_LIFETIME("Sort by Lifetime", "Ordenar por Tiempo de Vida"),
    RANK_HEADER("Rank", "Clasificación"),
    USERNAME_HEADER("Username", "Nombre de Usuario"),
    SCORE_HEADER("Score", "Puntuación"),
    KILLS_HEADER("Kills", "Muertes"),
    LIFETIME_HEADER("Lifetime", "Tiempo de Vida"),
//---------------------------------

    //----------Talent--------------
    TALENT_MENU_TITLE("Talent Menu", "Menú de Talentos"),
    SECTION_HEROES("Heroes", "Héroes"),
    SECTION_KEYS("Keys", "Teclas"),
    SECTION_CHEATS("Cheats", "Trucos"),
    SECTION_ABILITIES("Ability", "Habilidad"),
    HERO_NAME_HEADER("Hero Name", "Nombre del Héroe"),
    HERO_DESCRIPTION_HEADER("Description", "Descripción"),
    ACTION_HEADER("Action", "Acción"),
    KEY_HEADER("Key", "Tecla"),
    CHEAT_CODE_HEADER("Cheat Code", "Código de Truco"),
    CHEAT_EFFECT_HEADER("Effect", "Efecto"),
    ABILITY_NAME_HEADER("Ability", "Habilidad"),
    ABILITY_DESCRIPTION_HEADER("Description", "Descripción"),
//---------------------------------

    //-----------Hero Infoes----------
    SHANA_INFO("Brave and Warrior! SHANA can kill all the MONSTERS!", "¡Valiente y Guerrera! ¡SHANA puede matar a todos los MONSTRUOS!"),
    DIAMOND_INFO("No one can kill DIAMOND! don't try!", "¡Nadie puede matar a DIAMOND! ¡No lo intentes!"),
    SCARLET_INFO("Fast, Powerful, Clever! SCARLET is here! ", "¡Rápida, Poderosa, Inteligente! ¡SCARLET está aquí!"),
    LILITH_INFO("Lilith was born to do it!", "¡Lilith nació para hacerlo!"),
    DASHER_INFO("The fastest hero ever!", "¡El héroe más rápido de todos los tiempos!"),
//----------------

    MAIN_MENU_TITLE("MAIN MENU", "MENÚ PRINCIPAL"),
    CONTINUE_SAVED_GAME_BUTTON("Continue Saved Game", "Continuar Partida Guardada"),
    PRE_GAME_BUTTON("Pre-Game", "Pre-Juego"),
    SETTINGS_BUTTON("Settings", "Configuración"),
    PROFILE_BUTTON("Profile", "Perfil"),
    SCOREBOARD_BUTTON("Scoreboard", "Marcador"),
    TALENTS_MENU_BUTTON("Talents", "Talentos"),
    LOGOUT_BUTTON("Logout", "Cerrar Sesión"),

    //---------- Action Info-----------
    MOVE_UP_INFO("Move Up", "Mover Arriba"),
    MOVE_DOWN_INFO("Move Down", "Mover Abajo"),
    MOVE_LEFT_INFO("Move Left", "Mover Izquierda"),
    MOVE_RIGHT_INFO("Move Right", "Mover Derecha"),
    TOGGLE_AIM_INFO("Toggle Auto Aim", "Alternar Apuntar Automáticamente"),
    ATTACK_INFO("Attack", "Atacar"),
    PAUSE_INFO("Pause Game", "Pausar Juego"),
    RELOAD_INFO("Reload Weapon", "Recargar Arma"),
    CHEAT_ADD_XP_INFO("Add XP Cheat", "Truco: Añadir XP"),
    CHEAT_ADD_LEVEL_INFO("Add Level Cheat", "Truco: Añadir Nivel"),
    CHEAT_REDUCE_TIME_INFO("Reduce Time Cheat", "Truco: Reducir Tiempo"),
    CHEAT_KILL_ALL_ENEMIES("Kill All Enemies", "Matar a Todos los Enemigos"),
    CHEAT_GO_TO_BOSS_INFO("Go To Boss", "Ir al Jefe"),
//----------------------------------

    //-----------Delete account
    DELETE_ACCOUNT("Delete Account", "Eliminar Cuenta"),
    ARE_YOU_SURE_DELETE_ACCOUNT("Are you sure you want to delete your account? This action cannot be undone.",
                                    "¿Estás seguro de que quieres eliminar tu cuenta? Esta acción no se puede deshacer."),
//-----------------------------

    //----------Profile menu
    CHOOSE_AVATAR_FILE("Choose Custom Avatar", "Elegir Avatar Personalizado"),
    USE_DEFAULT_AVATAR("Use Default Avatar", "Usar Avatar Predeterminado"),
    AVATAR_UPLOAD_SUCCESS("Avatar uploaded successfully!", "¡Avatar subido con éxito!"),
    AVATAR_UPLOAD_FAILED("Failed to upload avatar.", "Error al subir el avatar."),
    FILE_NOT_FOUND_ERROR("File not found. Please check path.", "Archivo no encontrado. Por favor, verifica la ruta."),
    CUSTOM_AVATAR_NOT_FOUND("Custom avatar file not found on disk. Setting default.", "Archivo de avatar personalizado no encontrado en disco. Estableciendo predeterminado."),
    ERROR_LOADING_AVATAR("Error loading avatar image. Setting default.", "Error al cargar la imagen del avatar. Estableciendo predeterminado."),
    DEFAULT_AVATAR_SET("Default avatar set.", "Avatar predeterminado establecido."),
    LOGIN_REQUIRED_AVATAR("Login required to set avatar.", "Se requiere iniciar sesión para establecer el avatar."),
    MUST_BE_IMAGE_FILE("Please select an image file (PNG/JPG).", "Por favor, selecciona un archivo de imagen (PNG/JPG)."),
    INVALID_IMAGE_FORMAT("Only PNG and JPG images are supported.", "Solo se admiten imágenes PNG y JPG."),
    SELECT_PREDEFINED_OR_CUSTOM("Select a predefined avatar or upload your own:", "Selecciona un avatar predefinido o sube el tuyo:"),
    AVATAR_CHANGED_SUCCESSFULLY("Avatar changed successfully!", "¡Avatar cambiado con éxito!"),
//------------

    //---------------Setting
    VOLUME("Volume", "Volumen"),
    MUSIC("Music", "Música"),
    SFX("SFX", "SFX"),
    CHANGE_ACTION_BUTTON("Chnage action button", "Cambiar botón de acción"),
    PRESS_ANY_KEY_TO_BIND("Press any key to bind…", "Presiona cualquier tecla para asignar…"),
    PRESS_NEW_KEY("Press new key...", "Presiona la nueva tecla..."),
    CONTROLLER_CHANGED_SUCCESSFULLY("Controller changed successfully", "Controlador cambiado con éxito"),
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
