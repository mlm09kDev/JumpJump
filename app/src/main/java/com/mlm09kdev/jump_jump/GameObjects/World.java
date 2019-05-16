package com.mlm09kdev.jump_jump.GameObjects;

import com.mlm09kdev.jump_jump.Framework.Math.OverlapTester;
import com.mlm09kdev.jump_jump.Framework.Math.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Manuel Montes de Oca on 5/4/2019.
 */
public class World {
    public interface WorldListener {
        void jump();

        void highJump();

        void hit();

        void coin();
    }

    public static final float WORLD_WIDTH = 10;
    public static final float WORLD_HEIGHT = 150;
    public static final int WORLD_STATE_RUNNING = 0;
    public static final int WORLD_STATE_NEXT_LEVEL = 1;
    public static final int WORLD_STATE_GAME_OVER = 2;
    public static final Vector2 gravity = new Vector2(0, -12);

    public static int level = 1;

    public final Player player;
    public final List<Platform> platforms;
    public final List<Spring> springs;
    public final List<Squirrel> squirrels;
    public final List<Coin> coins;
    public Castle castle;
    public final WorldListener listener;
    public final Random rand;

    private boolean isSpringLoaded;
    public float heightSoFar;
    public static int score = 0;
    public int levelScore;
    public int state;

    public World(WorldListener listener) {
        rand = new Random();
        this.player = new Player(rand.nextInt(8) + 1, 1);
        this.platforms = new ArrayList<>();
        this.springs = new ArrayList<>();
        this.squirrels = new ArrayList<>();
        this.coins = new ArrayList<>();
        this.listener = listener;
        this.levelScore =0;
        generateLevel();

        this.heightSoFar = 0;
        this.state = WORLD_STATE_RUNNING;
    }

    private void generateLevel() {
        float y = Platform.PLATFORM_HEIGHT / 2;
        float maxJumpHeight = Player.PLAYER_JUMP_VELOCITY * Player.PLAYER_JUMP_VELOCITY
                / (2 * -gravity.y);

        float newPlatformY = (maxJumpHeight - 0.5f) / 2;
        while (newPlatformY < WORLD_HEIGHT - WORLD_WIDTH / 2) {
            int type = 0;
            if (level >= 15) {
                type = rand.nextFloat() > level / 50f ? Platform.PLATFORM_TYPE_STATIC
                        : Platform.PLATFORM_TYPE_MOVING;
            }
            float platformX = rand.nextFloat()
                    * (WORLD_WIDTH - Platform.PLATFORM_WIDTH)
                    + Platform.PLATFORM_WIDTH / 2;
            if (level < 15 && rand.nextFloat() > 0.9f
                    && type != Platform.PLATFORM_TYPE_MOVING) {
                Spring spring = new Spring(platformX,
                        newPlatformY + Platform.PLATFORM_HEIGHT / 2
                                + Spring.SPRING_HEIGHT / 2);
                springs.add(spring);
                isSpringLoaded = true;
            } else
                isSpringLoaded = false;

            Platform platform = new Platform(type, platformX, newPlatformY, isSpringLoaded);
            platform.setPlatformVelocity(level / 25f < 5f ? level / 25f : 5f);
            platforms.add(platform);


            y += (maxJumpHeight - 0.5f);
            y -= rand.nextFloat() * (maxJumpHeight / 3);

            newPlatformY += 1f + level / 10;
            if (newPlatformY > y + 1) {
                newPlatformY = y;
            }
        }
/*
        if (isEnemyReadyToBeGenerated && level >= 15 && y > WORLD_HEIGHT / 8 && rand.nextFloat() > 0.9f - ((level / 25f) - .35)) {
            Squirrel squirrel = new Squirrel(platform.position.x
                    + rand.nextFloat(), platform.position.y
                    + Squirrel.SQUIRREL_HEIGHT + rand.nextFloat() * 2);
            squirrel.setSQUIRREL_VELOCITY(level/7.5f < 5f ? level/7.5f : 5f);
            squirrels.add(squirrel);
            isEnemyReadyToBeGenerated = false;
        } else {
            isEnemyReadyToBeGenerated = true;
        }*/
        if (level >= 10) {
            int enemyQuotient = (platforms.size() - 1) / (level - 10 - 1);
            int enemyRemainder = (platforms.size() - 1) % (level - 10 - 1);

            int enemyIndex = 10;
            do {

                Squirrel squirrel = new Squirrel(platforms.get(enemyIndex).position.x
                        + rand.nextFloat(), platforms.get(enemyIndex).position.y
                        + Squirrel.SQUIRREL_HEIGHT + rand.nextFloat() * 2);
                squirrel.setSQUIRREL_VELOCITY(level / 7.5f < 5f ? level / 7.5f : 5f);
                squirrels.add(squirrel);

            } while ((enemyIndex += enemyQuotient + (enemyRemainder-- > 0 ? 1 : 0)) < platforms.size());
        }

        int quotient = (platforms.size() - 1) / (10 - 1);
        int remainder = (platforms.size() - 1) % (10 - 1);

        int index = 0;
        do {

            Coin coin = new Coin(platforms.get(index).position.x, platforms.get(index).position.y + Coin.COIN_HEIGHT);
            coins.add(coin);

        } while ((index += quotient + (remainder-- > 0 ? 1 : 0)) < platforms.size());


        castle = new Castle(WORLD_WIDTH / 2, newPlatformY + 2.5f);
    }

    public void update(float deltaTime, float accelX) {
        updatePlayer(deltaTime, accelX);
        updatePlatforms(deltaTime);
        updateSquirrels(deltaTime);
        updateCoins(deltaTime);
        if (player.state != Player.PLAYER_STATE_HIT)
            checkCollisions();
        checkGameOver();
    }

    private void updatePlayer(float deltaTime, float accelX) {
        if (player.state != Player.PLAYER_STATE_HIT && player.position.y <= 0.5f)
            player.hitPlatform();
        if (player.state != Player.PLAYER_STATE_HIT)
            player.velocity.x = -accelX / 10 * Player.PLAYER_MOVE_VELOCITY;
        player.update(deltaTime);
        heightSoFar = Math.max(player.position.y, heightSoFar);
    }

    private void updatePlatforms(float deltaTime) {
        int len = platforms.size();
        for (int i = 0; i < len; i++) {
            Platform platform = platforms.get(i);
            platform.update(deltaTime);
            if (platform.state == Platform.PLATFORM_STATE_PULVERIZING
                    && platform.stateTime > Platform.PLATFORM_PULVERIZE_TIME) {
                platforms.remove(platform);
                len = platforms.size();
            }
        }
    }

    //Squirrel movement
    private void updateSquirrels(float deltaTime) {
        int len = squirrels.size();
        for (int i = 0; i < len; i++) {
            Squirrel squirrel = squirrels.get(i);
            squirrel.update(deltaTime);
        }
    }

    private void updateCoins(float deltaTime) {
        int len = coins.size();
        for (int i = 0; i < len; i++) {
            Coin coin = coins.get(i);
            coin.update(deltaTime);
        }
    }

    private void checkCollisions() {
        checkPlatformCollisions();
        checkSquirrelCollisions();
        checkItemCollisions();
        checkCastleCollisions();
    }

    private void checkPlatformCollisions() {
        if (player.velocity.y > 0)
            return;

        int len = platforms.size();
        for (int i = 0; i < len; i++) {
            Platform platform = platforms.get(i);
            if (player.position.y > platform.position.y) {
                if (OverlapTester
                        .overlapRectangles(player.bounds, platform.bounds)) {
                    player.hitPlatform();
                    listener.jump();
                    if (level >= 5 && rand.nextFloat() > 0.9f - (level / 25f) && platform.position.y > 10 && !platform.isPlatformSpringLoaded) {
                        platform.pulverize();
                    }
                    break;
                }
            }
        }
    }

    private void checkSquirrelCollisions() {
        int len = squirrels.size();
        for (int i = 0; i < len; i++) {
            Squirrel squirrel = squirrels.get(i);
            if (OverlapTester.overlapRectangles(squirrel.bounds, player.bounds)) {
                player.hitSquirrel();
                listener.hit();
            }
        }
    }

    private void checkItemCollisions() {
        int len = coins.size();
        for (int i = 0; i < len; i++) {
            Coin coin = coins.get(i);
            if (OverlapTester.overlapRectangles(player.bounds, coin.bounds)) {
                coins.remove(coin);
                len = coins.size();
                listener.coin();
                levelScore += Coin.COIN_SCORE;
            }

        }

        if (player.velocity.y > 0)
            return;

        len = springs.size();
        for (int i = 0; i < len; i++) {
            Spring spring = springs.get(i);
            if (player.position.y > spring.position.y) {
                if (OverlapTester.overlapRectangles(player.bounds, spring.bounds)) {
                    player.hitSpring();
                    listener.highJump();
                }
            }
        }
    }

    private void checkCastleCollisions() {
        if (OverlapTester.overlapRectangles(castle.bounds, player.bounds)) {
            state = WORLD_STATE_NEXT_LEVEL;
            //if(coins.isEmpty())
            score += levelScore;
            levelScore=0;
            level++;
        }
    }

    private void checkGameOver() {
        if (heightSoFar - 7.5f > player.position.y) {
            state = WORLD_STATE_GAME_OVER;
        }
    }

    public int getCoins() {
        return coins.size();
    }
}
