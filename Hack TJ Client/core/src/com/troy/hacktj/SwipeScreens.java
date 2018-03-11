package com.troy.hacktj;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

public class SwipeScreens implements Screen {
    private MyScreen[] screens;
    private int index;

    private GestureDetector detector = new GestureDetector(new GestureDetector.GestureAdapter(){
        public boolean fling(float velocityX, float velocityY, int button) {
            System.out.println("fling");
            if(Math.abs(velocityX) > 1000.0f) {
                moveScreen(velocityX > 0.0f);
            }
            return true;
        }
    }) {


        @Override
        public boolean keyDown(int keycode) {
            screens[index].stage.keyDown(keycode);
            return super.keyDown(keycode);
        }

        @Override
        public boolean keyUp(int keycode) {
            screens[index].stage.keyUp(keycode);
            return super.keyUp(keycode);
        }

        @Override
        public boolean keyTyped(char character) {
            screens[index].stage.keyTyped(character);
            return super.keyTyped(character);
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            System.out.println("touch down");
            screens[index].stage.touchDown(screenX, screenY, pointer, button);
            return super.touchDown(screenX, screenY, pointer, button);
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            screens[index].stage.touchUp(screenX, screenY, pointer, button);
            return super.touchUp(screenX, screenY, pointer, button);
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            screens[index].stage.touchDragged(screenX, screenY, pointer);
            return super.touchDragged(screenX, screenY, pointer);
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            screens[index].stage.mouseMoved(screenX, screenY);
            return super.mouseMoved(screenX, screenY);
        }

        @Override
        public boolean scrolled(int amount) {
            screens[index].stage.scrolled(amount);
            return super.scrolled(amount);
        }

    };

    public SwipeScreens(int index, final MyScreen... screens) {
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
        screens[index].resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        screens[index].show();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(detector);
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

    }

    @Override
    public void dispose() {
        for(MyScreen screen : screens) {
            screen.dispose();
        }
    }
}
