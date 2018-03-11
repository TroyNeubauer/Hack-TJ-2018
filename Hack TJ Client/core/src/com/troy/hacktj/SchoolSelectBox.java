package com.troy.hacktj;

import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.troy.hacjtj.base.School;

public class SchoolSelectBox extends SelectBox<School> {
    public SchoolSelectBox() {
        super(Settings.skin);
        setItems(new School(), new School(), new School());
    }

}
