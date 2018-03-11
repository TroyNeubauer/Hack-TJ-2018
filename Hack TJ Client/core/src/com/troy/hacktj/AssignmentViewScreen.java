package com.troy.hacktj;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.troy.hacjtj.base.Course;

import java.util.Arrays;

public class AssignmentViewScreen extends MyScreen {
    private Course course;
    private Table list;

    public AssignmentViewScreen(Course course) {
        TextButton back, upload;
        this.course = course;
        container.align(Align.topLeft);
        back = new TextButton("Back", Settings.skin);
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                HackTJ.getInstance().setScreen(HackTJ.getInstance().getScreens());
            }
        });
        container.add(back);

        container.align(Align.topRight);
        upload = new TextButton("Upload", Settings.skin);
        upload.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                HackTJ.getInstance().getCamera().takePicture(new MyCamera.PictureCallback() {
                    @Override
                    public void pictureTaken(byte[] bytes) {
                        System.out.println("took pic " + Arrays.toString(bytes));
                    }
                });
            }
        });
        container.add(upload);

        initItems();
        init();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    private void initItems() {
        if(list != null) stage.getRoot().removeActor(list);
        list = new Table(Settings.skin);
        list.center();
        list.add("Test1").row();
        list.add("Test2").row();
        list.add("Test3").row();



        stage.addActor(list);
    }


}
