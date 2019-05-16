package com.mlm09kdev.jump_jump;

import com.mlm09kdev.jump_jump.Framework.GL.SpriteBatcher;
import com.mlm09kdev.jump_jump.Framework.GL.Texture;
import com.mlm09kdev.jump_jump.Framework.GL.TextureRegion;

/**
 * Created by Manuel Montes de Oca on 5/4/2019.
 */
class Font {
    private final Texture texture;
    private final int glyphWidth;
    private final int glyphHeight;
    private final int totalGlyphs = 96;
    private final TextureRegion[] glyphs = new TextureRegion[totalGlyphs];

    public Font(Texture texture, int offsetX, int offsetY, int glyphPerRow, int glyphWidth, int glyphHeight){
        this.texture = texture;
        this.glyphHeight = glyphHeight;
        this.glyphWidth = glyphWidth;
        int x = offsetX;
        int y = offsetY;

        for (int i = 0; i < totalGlyphs; i++) {
            glyphs[i] = new TextureRegion(texture, x,y, glyphWidth, glyphHeight);
            x += glyphWidth;
            if (x == offsetX + glyphPerRow * glyphWidth) {
                x = offsetX;
                y += glyphHeight;
            }
        }
    }

    public void drawText(SpriteBatcher batcher, String text, float x, float y){
        int textLength = text.length();
        for (int i = 0; i < textLength; i++) {
            int c = text.charAt(i) - ' ';
            if (c < 0 || c > glyphs.length - 1)
                continue;
            TextureRegion glyph = glyphs[c];
            batcher.drawSprite(x, y, glyphWidth, glyphHeight, glyph);
            x += glyphWidth;
        }
    }


}
