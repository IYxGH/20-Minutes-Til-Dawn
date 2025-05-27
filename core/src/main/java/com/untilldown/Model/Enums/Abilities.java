package com.untilldown.Model.Enums;

import com.untilldown.Model.Game;
import com.untilldown.Model.Player;

public enum Abilities {
    VITALITY("", Message.VITALITY){
        @Override
        void useAbility(Game game, Player player) {

        }
    },
    DAMAGER("", Message.DAMAGER){
        @Override
        void useAbility(Game game, Player player) {

        }
    },
    PROCREASE("", Message.PROCREASE){
        @Override
        void useAbility(Game game, Player player) {

        }
    },
    AMOCREASE("", Message.AMOCREASE){
        @Override
        void useAbility(Game game, Player player) {

        }
    },
    SPEEDY("", Message.SPEEDY){
        @Override
        void useAbility(Game game, Player player) {

        }
    }
    ;

    private final String info;
    private final Message message;
    abstract void useAbility(Game game, Player player);

    Abilities(String info, Message message) {
        this.info = info;
        this.message = message;
    }

    public String getMessage() {
        return message.getMessage();
    }
}
