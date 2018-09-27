package audi.jenganative.graphics.shaderProperties;

import android.opengl.GLES30;

import audi.jenganative.graphics.ShaderProperty;
import audi.jenganative.graphics.Texture;

/**
 * Created by audi on 22-8-2018.
 */

public class TextureProperty {
    private int location;
    private Texture texture;

    public TextureProperty(int textureSlot){
        location = GLES30.GL_TEXTURE0 + textureSlot;
        GLES30.glEnable(location);
        GLES30.glActiveTexture(location);
    }

    public void set(Texture texture) {
        this.texture = texture;
    }

    public void bind(){
        if(texture != null){
            texture.bind();
        }
    }

    public void unbind(){
        if(texture != null){
            texture.unbind();
        }
    }

    public Texture get(){
        return texture;
    }
}
