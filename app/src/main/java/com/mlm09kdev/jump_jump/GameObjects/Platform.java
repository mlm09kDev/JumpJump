package com.mlm09kdev.jump_jump.GameObjects;

import com.mlm09kdev.jump_jump.Framework.DynamicGameObject;

/**
 * Created by Manuel Montes de Oca on 5/4/2019.
 */
public class Platform extends DynamicGameObject {
    static final float PLATFORM_WIDTH = 2;
    static final float PLATFORM_HEIGHT = 0.5f;
    static final int PLATFORM_TYPE_STATIC = 0;
    static final int PLATFORM_TYPE_MOVING = 1;
    private static final int PLATFORM_STATE_NORMAL = 0;
    public static final int PLATFORM_STATE_PULVERIZING = 1;
    static final float PLATFORM_PULVERIZE_TIME = 0.2f * 4;



    private float platformVelocity = 2;

    final Boolean isPlatformSpringLoaded;
    private final int type;
    public int state;
    public float stateTime;

    Platform(int type, float x, float y, boolean springLoaded) {
        super(x, y, PLATFORM_WIDTH, PLATFORM_HEIGHT);
        this.type = type;
        this.state = PLATFORM_STATE_NORMAL;
        this.stateTime = 0;
        if (type == PLATFORM_TYPE_MOVING) {
            velocity.x = platformVelocity;
        }
        this.isPlatformSpringLoaded = springLoaded;
    }

    public void update(float deltaTime) {
        if (type == PLATFORM_TYPE_MOVING) {
            position.add(velocity.x * deltaTime, 0);
            bounds.lowerLeft.set(position).sub(PLATFORM_WIDTH / 2, PLATFORM_HEIGHT / 2);

            if (position.x < PLATFORM_WIDTH / 2) {
                velocity.x = -velocity.x;
                position.x = PLATFORM_WIDTH / 2;
            }
            if (position.x > World.WORLD_WIDTH - PLATFORM_WIDTH / 2) {
                velocity.x = -velocity.x;
                position.x = World.WORLD_WIDTH - PLATFORM_WIDTH / 2;
            }
        }

        stateTime += deltaTime;
    }

    void pulverize() {
        state = PLATFORM_STATE_PULVERIZING;
        stateTime = 0;
        velocity.x = 0;
    }

    void setPlatformVelocity(float platformVelocity) {
        this.platformVelocity = platformVelocity;
    }
}
