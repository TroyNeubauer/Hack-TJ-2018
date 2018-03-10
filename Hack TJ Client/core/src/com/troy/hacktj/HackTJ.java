package com.troy.hacktj;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.kryo.Kryo;
import com.troy.hacjtj.base.account.Account;

import java.util.ArrayList;
import java.util.List;

import de.tomgrill.gdxdialogs.core.GDXDialogs;
import de.tomgrill.gdxdialogs.core.GDXDialogsSystem;
import de.tomgrill.gdxdialogs.core.dialogs.GDXButtonDialog;
import de.tomgrill.gdxdialogs.core.listener.ButtonClickListener;

public class HackTJ extends Game {

    private GDXDialogs dialogs;
	private HackTJNet net;
	private static HackTJ instance;
    private List<Runnable> actions = new ArrayList<Runnable>();
    private Account account = null;
    private ThreadLocal<Kryo> kryo = new ThreadLocal<Kryo>() {
        @Override
        protected Kryo initialValue() {
            return new Kryo();
        }
    };
	
	@Override
	public void create () {
        Settings.init();
        dialogs = GDXDialogsSystem.install();
	    instance = this;
        setScreen(new LoginScreen());
        this.net = new HackTJNet();

	}

    @Override
    public void render() {
        synchronized (actions) {
            for(Runnable action : actions) {
                action.run();
            }
            actions.clear();
        }
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
	    super.render();
    }

    public static void showInfoMessage(String title, String message) {
        final GDXButtonDialog warningDialog = instance.dialogs.newDialog(GDXButtonDialog.class);
        warningDialog.setTitle(title).setMessage(message);
        warningDialog.addButton("Ok");
        warningDialog.setClickListener(new ButtonClickListener() {
            @Override
            public void click(int button) {
                warningDialog.dismiss();
            }
        });
        warningDialog.build().show();
    }

    public HackTJNet getNet() {
        return net;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public GDXDialogs getDialogs() {
        return dialogs;
    }

    public void addMainThreadAction(Runnable action) {
        synchronized (actions) {
            actions.add(action);
        }
    }

    public Kryo getKryo() {
        return kryo.get();
    }

    public static HackTJ getInstance() {
        return instance;
    }
}
