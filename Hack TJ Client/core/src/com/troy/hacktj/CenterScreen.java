package com.troy.hacktj;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class CenterScreen extends MyScreen {

    public CenterScreen() {
        stage.addActor(new Label("Center!", Settings.skin));
    }
}
