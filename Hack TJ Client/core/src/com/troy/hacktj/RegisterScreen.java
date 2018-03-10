package com.troy.hacktj;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.troy.hacjtj.base.packet.RegisterData;

import de.tomgrill.gdxdialogs.core.dialogs.GDXButtonDialog;
import de.tomgrill.gdxdialogs.core.listener.ButtonClickListener;

public class RegisterScreen extends MyScreen {
    private TextField usernameField, passwordField, emailField;
    private TextButton createBtn, backBtn;
    private Label label;

    public RegisterScreen(final String username) {

        this.usernameField = new TextField(username, Settings.skin);
        usernameField.setMessageText("Username");

        this.passwordField = new TextField("", Settings.skin);
        passwordField.setMessageText("Password");
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');

        this.emailField = new TextField("", Settings.skin);
        emailField.setMessageText("Email");

        this.createBtn = new TextButton("Create!", Settings.skin);

        this.backBtn = new TextButton("Back", Settings.skin);

        this.label = new Label("Create New Account", Settings.skin);
        label.setAlignment(Align.center);

        container.setSkin(Settings.skin);
        container.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        container.add(label).row();
        container.add(usernameField).width(400).height(65).row();
        container.add(passwordField).width(400).height(65).row();
        container.add(emailField).width(400).height(65).row();
        container.add(createBtn).width(200).height(60).row();
        container.add(backBtn).width(150).height(60).row();
        container.add(" ").row();
        container.add(" ").row();
        container.add(" ").row();
        container.add(" ").row();

        init();

        Gdx.input.setInputProcessor(stage);

        createBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            HackTJNet net = HackTJ.getInstance().getNet();
            if (net.isConnected()) {
                net.write(new RegisterData(usernameField.getText().toCharArray(), passwordField.getText().toCharArray(), emailField.getText().toCharArray()));
                System.out.println("sent register to server " + usernameField.getText() + ", " + passwordField.getText());
            } else {
                HackTJ.showInfoMessage("No connection!", "Check your internet connection or try again later!");
            }

            }
        });

        backBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                HackTJ.getInstance().setScreen(new LoginScreen());
            }
        });
    }
}
