package com.mlm09kdev.jump_jump.Framework;

/**
 * Created by Manuel Montes de Oca on 5/4/2019.
 */
public abstract class Screen {
    protected final Game game;

    protected Screen(Game game) {
        this.game = game;
    }

    public abstract void update(float deltaTime);

    public abstract void present(float deltaTime);

    public abstract void pause();

    public abstract void resume();

    public abstract void dispose();
}
