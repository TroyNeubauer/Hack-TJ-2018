package com.troy.hacktj;


import com.badlogic.gdx.scenes.scene2d.ui.Label;


public class RecentUploadsScreen extends MyScreen {
    public RecentUploadsScreen() {
        stage.addActor(new Label("Uploads", Settings.skin));
    }
}
