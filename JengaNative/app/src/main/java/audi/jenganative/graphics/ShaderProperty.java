package audi.jenganative.graphics;

/**
 * Created by audi on 1-8-2018.
 */
import android.opengl.GLES30;
import android.renderscript.Matrix4f;
import android.util.Log;

import audi.jenganative.math.Vector4;

public abstract class ShaderProperty<T> {
    private int uniformLocation;
    private T currentValue;
    private static final int INVALID_LOCATION = -1;

    public ShaderProperty(int program, String name){
        findUniform(program, name);
    }

    private void findUniform(int program, String name){
        uniformLocation = GLES30.glGetUniformLocation(program, name);

        if(!isValid()){
            Log.e("Shader property", "[Uniform Error] Uniform '"+name+"' does not exist\n");
        }
    }

    public boolean isValid(){
        return uniformLocation != INVALID_LOCATION;
    }

    public T getValue(){
        return currentValue;
    }

    protected abstract void setUniform(int location, T value);

    public void set(T value){
        if(isValid() && value != null){
            setUniform(uniformLocation, value);
            currentValue = value;
        }
    }
}



