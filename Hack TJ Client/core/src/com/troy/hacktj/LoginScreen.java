package com.troy.hacktj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.troy.hacjtj.base.packet.LoginData;

public class LoginScreen extends MyScreen {
    private TextField usernameField, passwordField;
    private TextButton loginButton, registerBtn;
    private Label label;

    public LoginScreen() {

        this.usernameField = new TextField("", Settings.skin);
        usernameField.setMessageText("Username");

        this.passwordField = new TextField("", Settings.skin);
        passwordField.setMessageText("Password");
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');

        this.loginButton = new TextButton("Login", Settings.skin);

        this.registerBtn = new TextButton("Create Account", Settings.skin);

        this.label = new Label("Homework Helper", Settings.skin);
        label.setAlignment(Align.center);

        container.setSkin(Settings.skin);
        container.center();
        container.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        container.add(label).row();
        container.add(usernameField).width(500).height(65).row();

        container.add(passwordField).width(500).height(65).row();
        container.add(loginButton).width(400).height(70).row();
        container.add(registerBtn).width(300).height(55).row();
        container.add("By Troy Neubauer").width(200).height(40).row();
        container.add(" ").row();
        container.add(" ").row();
        container.add(" ").row();
        init();

        Gdx.input.setInputProcessor(stage);

        loginButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                HackTJNet net = HackTJ.getInstance().getNet();
                if (net.isConnected()) {
                    loginButton.setDisabled(true);
                    net.write(new LoginData(usernameField.getText().toCharArray(), passwordField.getText().toCharArray()));
                } else {
                    HackTJ.showInfoMessage("No Connection!", "Check your internet connection or try again later!");
                }
            }
        });

        registerBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                HackTJ.getInstance().setScreen(new RegisterScreen(usernameField.getText()));
            }
        });
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {

        }
    }
}
