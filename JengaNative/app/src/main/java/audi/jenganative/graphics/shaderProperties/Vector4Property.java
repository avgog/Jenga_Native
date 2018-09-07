package audi.jenganative.graphics.shaderProperties;

import android.opengl.GLES30;

import audi.jenganative.graphics.ShaderProperty;
import audi.jenganative.math.Vector4;

/**
 * Created by audi on 1-8-2018.
 */

public class Vector4Property extends ShaderProperty<Vector4> {
    public Vector4Property(int program, String name){
        super(program, name);
    }
    @Override
    protected void setUniform(int location, Vector4 value) {
        GLES30.glUniform4f(location, value.x, value.y, value.z, value.w);
    }
}
