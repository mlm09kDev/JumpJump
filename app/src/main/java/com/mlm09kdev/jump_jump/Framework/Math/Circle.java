package com.mlm09kdev.jump_jump.Framework.Math;

/**
 * Created by Manuel Montes de Oca on 5/4/2019.
 */
public class Circle {
    public final Vector2 center = new Vector2();
    public float radius;

    public Circle(float x, float y, float radius) {
        this.center.set(x,y);
        this.radius = radius;
    }
}
