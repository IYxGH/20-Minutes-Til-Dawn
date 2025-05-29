package com.untilldown.Model.Enums;

import com.untilldown.Model.Game;
import com.untilldown.Model.Player;

import java.util.*;

public enum Abilities {
    VITALITY("", Message.VITALITY){
        @Override
        public void useAbility(Game game, Player player) {
            player.addHp(1);
            player.addMaxHp(1);
        }
    },
    DAMAGER("", Message.DAMAGER){
        @Override
        public void useAbility(Game game, Player player) {
            player.setTimerBuffWeapon(10);
        }
    },
    PROCREASE("", Message.PROCREASE){
        @Override
        public void useAbility(Game game, Player player) {
            player.addProjectileEffect(1);
        }
    },
    AMOCREASE("", Message.AMOCREASE){
        @Override
        public void useAbility(Game game, Player player) {
            player.addMaxAmmo(5);
        }
    },
    SPEEDY("", Message.SPEEDY){
        @Override
        public void useAbility(Game game, Player player) {
            player.setTimerSpeedEffect(10);
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
        ArrayList<Abilities> allAbilities = new ArrayList<>();
        for (Abilities ability : Abilities.values()) {
            allAbilities.add(ability);
        }

        Random rand = new Random();
        Set<Integer> result = new HashSet<>();

        while (result.size() < 3) {
            result.add(rand.nextInt(5)); // 0 to 4
        }

        List<Integer> resultList = new ArrayList<>(result);

        // Access elements
        int a = resultList.get(0);
        int b = resultList.get(1);
        int c = resultList.get(2);


        // Return first 3 unique abilities
        return new Abilities[] {
            allAbilities.get(a),
            allAbilities.get(b),
            allAbilities.get(c),
        };
    }

}
