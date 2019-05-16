package com.mlm09kdev.jump_jump.GameObjects;

import com.mlm09kdev.jump_jump.Framework.GameObject;

/**
 * Created by Manuel Montes de Oca on 5/4/2019.
 */
public class Coin extends GameObject {

    private static final float COIN_WIDTH = 0.5f;
    public static final float COIN_HEIGHT = 0.8f;
    public static final int COIN_SCORE = 10;

   public float stateTime;

    public Coin(float x, float y) {
        super(x, y, COIN_WIDTH, COIN_HEIGHT);
        stateTime = 0;
    }

    public void update(float deltaTime) {
        stateTime += deltaTime;
    }
}
