package com.troy.hacktj;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.kryo.Kryo;
import com.troy.hacjtj.base.Account;
import com.troy.hacjtj.base.Course;
import com.troy.hacjtj.base.packet.Disconnect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.tomgrill.gdxdialogs.core.GDXDialogs;
import de.tomgrill.gdxdialogs.core.GDXDialogsSystem;
import de.tomgrill.gdxdialogs.core.dialogs.GDXButtonDialog;
import de.tomgrill.gdxdialogs.core.listener.ButtonClickListener;

public class HackTJ extends Game {

    private GDXDialogs dialogs;
	private HackTJNet net;
	private static HackTJ instance;
    private final List<Runnable> actions = new ArrayList<Runnable>();
    private Account account = new Account(0, "Test-Username", "testemail@gmail.com");
    private ThreadLocal<Kryo> kryo = new ThreadLocal<Kryo>() {
        @Override
        protected Kryo initialValue() {
            return new Kryo();
        }
    };
    private SchoolSelectBox box;
    private MyCamera camera;
    private SwipeScreens screens;

    public HackTJ(MyCamera camera) {
        this.camera = camera;
        account.courses.add(new Course("English 10 Honors"));
        account.courses.add(new Course("AP World"));
        account.courses.add(new Course("AP CS AB"));
    }
	
	@Override
	public void create () {
        Settings.init();
        box = new SchoolSelectBox();
        dialogs = GDXDialogsSystem.install();
	    instance = this;
        screens = new SwipeScreens(1, new AccountSettingsScreen(), new CenterScreen(), new RecentUploadsScreen());
        setScreen(new LoginScreen());
        this.net = new HackTJNet();
        /*camera.takePicture(new MyCamera.PictureCallback() {
            @Override
            public void pictureTaken(byte[] bytes) {
                System.out.println("Callback called! " + Arrays.toString(bytes));
            }
        });*/
	}

    @Override
    public void render() {
        synchronized (actions) {
            for(Runnable action : actions) {
                action.run();
            }
            actions.clear();
        }
        Gdx.gl.glClearColor(1, 0, 1, 1);
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

    @Override
    public void dispose() {
        net.write(new Disconnect());
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

    public Account getAccount() {
        return account;
    }

    public static HackTJ getInstance() {
        return instance;
    }

    public SchoolSelectBox getSchoolSelectBox() {
        return box;
    }

    public MyCamera getCamera() {
        return camera;
    }

    public SwipeScreens getScreens() {
        return screens;
    }
}
