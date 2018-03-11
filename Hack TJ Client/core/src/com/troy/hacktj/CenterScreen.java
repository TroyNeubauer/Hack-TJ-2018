package com.troy.hacktj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.troy.hacjtj.base.Course;
import com.troy.hacjtj.base.Account;

public class CenterScreen extends MyScreen {

    private Table courseList;

    public CenterScreen() {
        container.center();
        container.add("Center!");
        initTable();
        init();
    }

    @Override
    public void show() {
        initTable();
    }

    private void initTable() {
        if(courseList != null) stage.getRoot().removeActor(courseList);
        courseList = new Table(Settings.skin);
        courseList.center();
        courseList.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        for(final Course course : HackTJ.getInstance().getAccount().courses) {
            TextButton button = new TextButton(course.getName(), Settings.skin);
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    HackTJ.getInstance().setScreen(new AssignmentViewScreen(course));
                }
            });
            courseList.add(button).width(400).height(80).row();
        }

        stage.addActor(courseList);


    }
}
