package com.mlm09kdev.jump_jump.Framework.GL;

/**
 * Created by Manuel Montes de Oca on 5/4/2019.
 */
public class TextureRegion {    
    final float u1, v1;
    final float u2, v2;
    private final Texture texture;
    
    public TextureRegion(Texture texture, float x, float y, float width, float height) {
        this.u1 = x / texture.width;
        this.v1 = y / texture.height;
        this.u2 = this.u1 + width / texture.width;
        this.v2 = this.v1 + height / texture.height;        
        this.texture = texture;
    }
}
