package com.untilldown.Model;

import com.untilldown.Model.Enums.Hero;
import com.untilldown.Model.Enums.WeaponType;

public class Player {
    private User user;
    private Hero hero;
    private WeaponType weapon;

    public Player(User user, Hero hero, WeaponType weapon) {
        this.hero = hero;
        this.weapon = weapon;
    }
}
