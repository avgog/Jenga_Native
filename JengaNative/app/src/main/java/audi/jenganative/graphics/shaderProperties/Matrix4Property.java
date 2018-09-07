package audi.jenganative.graphics.shaderProperties;

import android.opengl.GLES30;
import android.renderscript.Matrix4f;

import audi.jenganative.graphics.ShaderProperty;

/**
 * Created by audi on 1-8-2018.
 */

public class Matrix4Property extends ShaderProperty<Matrix4f> {
    public Matrix4Property(int program, String name){
        super(program, name);
    }
    @Override
    protected void setUniform(int location, Matrix4f value) {
        GLES30.glUniformMatrix4fv(location, 1,false, value.getArray(),0);
    }
}