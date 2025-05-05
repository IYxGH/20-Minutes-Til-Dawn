package com.untilldown.Controller;

import com.untilldown.Model.Pregame;
import com.untilldown.View.PreGameMenuView;

public class PreGameMenuController {
    private PreGameMenuView view;
    private Pregame pregame;


    public void setView(PreGameMenuView view) {
        this.view = view;
        this.pregame = new Pregame();
    }

    public void handlePreGameMenuButtons() {
        if (view != null) {

        }
    }

}
