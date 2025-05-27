package com.untilldown.Model.Enums;

import com.untilldown.Model.Game;
import com.untilldown.Model.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
    public abstract void useAbility(Game game, Player player);

    Abilities(String info, Message message) {
        this.info = info;
        this.message = message;
    }

    public String getMessage() {
        return message.getMessage();
    }

    public static Abilities[] get3Random() {
        Abilities[] allAbilities = values();
        List<Abilities> abilitiesList = Arrays.asList(allAbilities);

        // Shuffle the list
        Collections.shuffle(abilitiesList);

        // Return first 3 unique abilities
        return new Abilities[] {
            abilitiesList.get(0),
            abilitiesList.get(1),
            abilitiesList.get(2)
        };
    }
}
