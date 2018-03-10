package com.troy.hacktj;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Settings {
    public static Skin skin;

    protected static void init() {
        skin = new Skin(Gdx.files.internal("default.json"));
    }

}
