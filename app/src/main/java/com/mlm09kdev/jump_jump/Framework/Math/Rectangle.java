package com.mlm09kdev.jump_jump.Framework.Math;

/**
 * Created by Manuel Montes de Oca on 5/4/2019.
 */
public class Rectangle {
    public final Vector2 lowerLeft;
    public float width, height;
    
    public Rectangle(float x, float y, float width, float height) {
        this.lowerLeft = new Vector2(x,y);
        this.width = width;
        this.height = height;
    }	
}
