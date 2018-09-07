package audi.jenganative.graphics.shaders;

import audi.jenganative.graphics.Shader;
import audi.jenganative.graphics.shaderProperties.Matrix4Property;
import audi.jenganative.graphics.shaderProperties.Vector4Property;

/**
 * Created by audi on 6-8-2018.
 */

public class DiffuseShader extends Shader{
    private Matrix4Property mvp; //model view projection matrix


    public DiffuseShader(String vertexCode, String fragmentCode){
        super(vertexCode, fragmentCode);

        bind();

        mvp = new Matrix4Property(program, "mvp");

        unbind();
    }

    //returns mvp matrix property
    public Matrix4Property getMVP(){
        return mvp;
    }
}
