package audi.jenganative.graphics;

import android.graphics.Bitmap;
import android.opengl.GLES30;
import android.opengl.GLUtils;
import android.util.Log;

import java.nio.Buffer;

/**
 * Created by audi on 4-8-2018.
 */

public class Texture {
    private int textureId[] = {0};

    /*public Texture(int width, int height, int internalFormat, int dataFormat, int dataType, Buffer buffer){
        try{
            GLES30.glGenTextures(GLES30.GL_TEXTURE_2D, textureId, 0);

            bind();

            GLES30.glTexImage2D(
                    GLES30.GL_TEXTURE_2D,
                    0, //mipmap level
                    internalFormat,
                    width,
                    height,
                    0, //border
                    dataFormat,
                    dataType,
                    buffer
            );

            GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR);
            GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_LINEAR_MIPMAP_LINEAR);
            GLES30.glGenerateMipmap(GLES30.GL_TEXTURE_2D);
        }
        catch (Exception e){
            Log.i("texture", e.getMessage());
        }
    }*/

    public Texture(Bitmap bitmap){
        GLES30.glGenTextures(1, textureId, 0);

        bind();

        //texture wrapping
        //GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_S, GLES30.GL_REPEAT);
        //GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_T, GLES30.GL_REPEAT);

        //texture filtering
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_NEAREST);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR);

        //mipmaps
        //GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_LINEAR_MIPMAP_LINEAR);
        //GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR);

        GLUtils.texImage2D(GLES30.GL_TEXTURE_2D, 0, bitmap, 0);
        //GLES30.glGenerateMipmap(GLES30.GL_TEXTURE_2D);
        unbind();
    }

    public void bind(){
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureId[0]);
    }

    public void unbind(){
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, 0);
    }
}
