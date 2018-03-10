package com.troy.hacktj;


import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class AccountSettingsScreen extends MyScreen {
    public AccountSettingsScreen() {
        stage.addActor(new Label("Account", Settings.skin));
    }
}
