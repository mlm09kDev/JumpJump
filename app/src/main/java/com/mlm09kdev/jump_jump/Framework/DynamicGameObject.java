package com.mlm09kdev.jump_jump.Framework;


import com.mlm09kdev.jump_jump.Framework.Math.Vector2;

/**
 * Created by Manuel Montes de Oca on 5/4/2019.
 */
public class DynamicGameObject extends GameObject {
    public final Vector2 velocity;
    public final Vector2 accel;
    
    public DynamicGameObject(float x, float y, float width, float height) {
        super(x, y, width, height);
        velocity = new Vector2();
        accel = new Vector2();
    }
}
