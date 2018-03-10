package com.troy.hacktj;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

public class SwipeScreens implements Screen {
    private MyScreen[] screens;
    private int index;
    private GestureDetector detector = new GestureDetector(new GestureDetector.GestureAdapter(){
        @Override
        public boolean fling(float velocityX, float velocityY, int button) {
            if(Math.abs(velocityX) > 1000.0f) {
                moveScreen(velocityX > 0.0f);
            }
            return true;
        }
    });

    public SwipeScreens(int index, MyScreen... screens) {
        Gdx.input.setInputProcessor(detector);
        this.screens = screens;
        this.index = index;
    }

    private void moveScreen(boolean right) {
        int newIndex = index + (right ? -1 : +1);
        if(newIndex < 0 || newIndex >= screens.length) {
            return;
        }
        index = newIndex;
        screens[index].show();
    }

    @Override
    public void show() {
        screens[index].show();
    }

    @Override
    public void render(float delta) {
        screens[index].render(delta);
    }

    @Override
    public void resize(int width, int height) {
        screens[index].getStage().getViewport().setScreenSize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        for(MyScreen screen : screens) {
            screen.dispose();
        }
    }
}
