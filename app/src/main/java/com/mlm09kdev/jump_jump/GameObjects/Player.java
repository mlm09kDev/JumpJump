package com.mlm09kdev.jump_jump.GameObjects;

import com.mlm09kdev.jump_jump.Framework.DynamicGameObject;

/**
 * Created by Manuel Montes de Oca on 5/4/2019.
 */
public class Player extends DynamicGameObject {
    public static final int PLAYER_STATE_JUMP = 0;
    public static final int PLAYER_STATE_FALL = 1;
    public static final int PLAYER_STATE_HIT = 2;
    public static final float PLAYER_JUMP_VELOCITY = 11;
    public static final float PLAYER_MOVE_VELOCITY = 20;
    public static final float PLAYER_WIDTH = 0.8f;
    public static final float PLAYER_HEIGHT = 0.8f;

    public int state;
    public float stateTime;

    public Player(float x, float y) {
        super(x, y, PLAYER_WIDTH, PLAYER_HEIGHT);
        state = PLAYER_STATE_FALL;
        stateTime = 0;
    }

    public void update(float deltaTime) {
        velocity.add(World.gravity.x * deltaTime, World.gravity.y * deltaTime);
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
        bounds.lowerLeft.set(position).sub(bounds.width / 2, bounds.height / 2);

        if(velocity.y > 0 && state != PLAYER_STATE_HIT) {
            if(state != PLAYER_STATE_JUMP) {
                state = PLAYER_STATE_JUMP;
                stateTime = 0;
            }
        }

        if(velocity.y < 0 && state != PLAYER_STATE_HIT) {
            if(state != PLAYER_STATE_FALL) {
                state = PLAYER_STATE_FALL;
                stateTime = 0;
            }
        }

        if(position.x < 0)
            position.x = World.WORLD_WIDTH;
        if(position.x > World.WORLD_WIDTH)
            position.x = 0;

        stateTime += deltaTime;
    }

    public void hitSquirrel() {
        velocity.set(0,0);
        state = PLAYER_STATE_HIT;
        stateTime = 0;
    }

    public void hitPlatform() {
        velocity.y = PLAYER_JUMP_VELOCITY;
        state = PLAYER_STATE_JUMP;
        stateTime = 0;
    }

    public void hitSpring() {
        velocity.y = PLAYER_JUMP_VELOCITY * 1.5f;
        state = PLAYER_STATE_JUMP;
        stateTime = 0;
    }
}
