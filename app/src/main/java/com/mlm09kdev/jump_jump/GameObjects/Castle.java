package com.mlm09kdev.jump_jump.GameObjects;

import com.mlm09kdev.jump_jump.Framework.GameObject;

/**
 * Created by Manuel Montes de Oca on 5/4/2019.
 */
public class Castle extends GameObject {

    private static final float CASTLE_WIDTH = 1.7f;
    private static final float CASTLE_HEIGHT = 1.7f;

    public Castle(float x, float y) {
        super(x, y, CASTLE_WIDTH, CASTLE_HEIGHT);
    }

}
