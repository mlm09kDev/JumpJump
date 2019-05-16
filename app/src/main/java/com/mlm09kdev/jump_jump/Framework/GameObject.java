package com.mlm09kdev.jump_jump.Framework;


import com.mlm09kdev.jump_jump.Framework.Math.Rectangle;
import com.mlm09kdev.jump_jump.Framework.Math.Vector2;

/**
 * Created by Manuel Montes de Oca on 5/4/2019.
 */
public class GameObject {
    public final Vector2 position;
    public final Rectangle bounds;
    
    protected GameObject(float x, float y, float width, float height) {
        this.position = new Vector2(x,y);
        this.bounds = new Rectangle(x-width/2, y-height/2, width, height);
    }
}
