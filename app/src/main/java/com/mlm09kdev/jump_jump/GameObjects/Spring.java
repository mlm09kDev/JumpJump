package com.mlm09kdev.jump_jump.GameObjects;

import com.mlm09kdev.jump_jump.Framework.GameObject;

/**
 * Created by Manuel Montes de Oca on 5/4/2019.
 */
public class Spring extends GameObject {
    private static final float SPRING_WIDTH = .3f;
    public static final float SPRING_HEIGHT = .3f;

    public Spring(float x, float y) {
        super(x, y, SPRING_WIDTH, SPRING_HEIGHT);
    }
}
