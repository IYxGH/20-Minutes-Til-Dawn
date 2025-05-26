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
        map.put(Action.PAUSE, Input.Keys.ESCAPE);
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

}
