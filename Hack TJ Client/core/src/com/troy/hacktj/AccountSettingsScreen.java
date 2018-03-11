package com.troy.hacktj;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.troy.hacjtj.base.School;

public class AccountSettingsScreen extends MyScreen {

    public AccountSettingsScreen() {
        container.center();
        container.add("Account Info: ").center().height(100).row();
        container.add("Username: " + HackTJ.getInstance().getAccount().getUsername()).center().height(100).row();
        container.add("Email: " + HackTJ.getInstance().getAccount().getEmail()).center().height(100).row();
        container.add(HackTJ.getInstance().getSchoolSelectBox()).center().height(100).row();
        init();
    }
}
