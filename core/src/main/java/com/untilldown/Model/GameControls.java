package com.untilldown.Model;

import com.badlogic.gdx.Input;
import com.untilldown.Model.Enums.Action;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GameControls {

    private static final Map<Action, Integer> DEFAULT_CONTROLS;
    static {
        Map<Action, Integer> map = new HashMap<>();
        map.put(Action.MOVE_UP, Input.Keys.W);
        map.put(Action.MOVE_DOWN, Input.Keys.S);
        map.put(Action.MOVE_LEFT, Input.Keys.A);
        map.put(Action.MOVE_RIGHT, Input.Keys.D);
        map.put(Action.TOGGLE_AIM, Input.Keys.SPACE);
        map.put(Action.ATTACK, Input.Keys.ENTER);
        map.put(Action.RELOAD, Input.Keys.R);
        map.put(Action.CHEAT_ADD_XP, Input.Keys.X);
        map.put(Action.CHEAT_ADD_LEVEL, Input.Keys.Z);
        map.put(Action.CHEAT_REDUCE_TIME, Input.Keys.T);
        map.put(Action.CHEAT_KILL_ALL_ENEMIES, Input.Keys.F);
        DEFAULT_CONTROLS = Collections.unmodifiableMap(map);
    }

    private final Map<Action, Integer> currentControls;

    public GameControls() {
        this.currentControls = new HashMap<>(DEFAULT_CONTROLS);
    }

    public int getKey(Action action) {
        return currentControls.getOrDefault(action, -1);
    }

    public void setKey(Action action, int newKeyCode) {
        currentControls.put(action, newKeyCode);
    }

    public Map<Action, Integer> getCurrentControls() {
        return currentControls;
    }

    public Map<String, String> getKeyBindings() {
        Map<String, String> map = new HashMap<>();
        for (Map.Entry<Action, Integer> entry : currentControls.entrySet()) {
            char a = Character.highSurrogate(entry.getValue());
            map.put(entry.getKey().getInfo(), String.valueOf(a));
        }

        return new HashMap<>();
    }



}
