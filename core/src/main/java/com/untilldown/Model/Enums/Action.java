package com.untilldown.Model.Enums;

public enum Action {
    MOVE_UP(false, Message.MOVE_UP_INFO),
    MOVE_DOWN(false, Message.MOVE_DOWN_INFO),
    MOVE_LEFT(false, Message.MOVE_LEFT_INFO),
    MOVE_RIGHT(false, Message.MOVE_RIGHT_INFO),
    TOGGLE_AIM(false, Message.TOGGLE_AIM_INFO),
    ATTACK(false, Message.ATTACK_INFO),
    PAUSE(false, Message.PAUSE_INFO),
    RELOAD(false, Message.RELOAD_INFO),
    CHEAT_ADD_XP(true, Message.CHEAT_ADD_XP_INFO),
    CHEAT_ADD_LEVEL(true, Message.CHEAT_ADD_LEVEL_INFO),
    CHEAT_REDUCE_TIME(true, Message.CHEAT_REDUCE_TIME_INFO),
    CHEAT_KILL_ALL_ENEMIES(true, Message.CHEAT_KILL_ALL_ENEMIES)

    ;

    private final boolean isCheat;
    private final Message info;

    Action(boolean isCheat, Message info) {
        this.isCheat = isCheat;
        this.info = info;
    }

    public String getInfo() {
        return info.getMessage();
    }

    public boolean isCheat() {
        return isCheat;
    }
}
