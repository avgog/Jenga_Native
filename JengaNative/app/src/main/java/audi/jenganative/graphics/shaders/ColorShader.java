package audi.jenganative.graphics.shaders;

import audi.jenganative.graphics.Shader;
import audi.jenganative.graphics.shaderProperties.Matrix4Property;
import audi.jenganative.graphics.shaderProperties.Vector4Property;

/**
 * Created by audi on 5-8-2018.
 */

public class ColorShader extends Shader {
    private Vector4Property color;
    private Matrix4Property mvp; //model view projection matrix

    public ColorShader(String vertexCode, String fragmentCode){
        super(vertexCode, fragmentCode);

        bind();

        mvp = new Matrix4Property(program, "mvp");
        color = new Vector4Property(program, "mainColor");

        unbind();
    }

    public Vector4Property getColor(){
        return color;
    }

    //returns mvp matrix property
    public Matrix4Property getMVP(){
        return mvp;
    }
}
