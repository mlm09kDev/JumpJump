package com.mlm09kdev.jump_jump.GameObjects;

import com.mlm09kdev.jump_jump.Framework.GameObject;

/**
 * Created by Manuel Montes de Oca on 5/4/2019.
 */
public class Spring extends GameObject {
    private static final float SPRING_WIDTH = .3f;
    public static final float SPRING_HEIGHT = .3f;
    public float stateTime;
    public int state;
    public static final int SPRING_STATE_NORMAL = 0;
    public static final int SPRING_STATE_ANIMATE = 1;

    public Spring(float x, float y) {
        super(x, y, SPRING_WIDTH, SPRING_HEIGHT);
        this.state = SPRING_STATE_NORMAL;
        stateTime = 0;
    }
    public void update(float deltaTime){
        stateTime += deltaTime;
    }

    public void animate(){
        this.state =  SPRING_STATE_ANIMATE;
        stateTime = 0;
    }
}
