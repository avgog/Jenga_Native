package audi.jenganative.graphics.shaders;

import audi.jenganative.graphics.Shader;
import audi.jenganative.graphics.Texture;
import audi.jenganative.graphics.shaderProperties.Matrix4Property;
import audi.jenganative.graphics.shaderProperties.TextureProperty;
import audi.jenganative.graphics.shaderProperties.Vector4Property;

/**
 * Created by audi on 6-8-2018.
 */

public class DiffuseShader extends Shader{
    private Matrix4Property mvp; //model view projection matrix
    public TextureProperty mainTexture;

    public DiffuseShader(String vertexCode, String fragmentCode, Texture texture){
        super(vertexCode, fragmentCode);

        bind();

        mvp = new Matrix4Property(program, "mvp");
        mainTexture = new TextureProperty(0);

        this.mainTexture.set(texture);
        unbind();
    }

    //returns mvp matrix property
    public Matrix4Property getMVP(){
        return mvp;
    }

    @Override
    public void bind(){
        super.bind();

        if(mainTexture != null){
            mainTexture.bind();
        }
    }

    @Override
    public void unbind(){
        super.unbind();

        if(mainTexture != null){
            mainTexture.unbind();
        }
    }
}
