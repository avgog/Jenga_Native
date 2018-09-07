package audi.jenganative.graphics.renderers;

import android.renderscript.Matrix4f;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import audi.jenganative.components.Transform;
import audi.jenganative.graphics.Camera;
import audi.jenganative.graphics.Mesh;
import audi.jenganative.graphics.shaders.ColorShader;
import audi.jenganative.graphics.shaders.DiffuseShader;
import audi.jenganative.math.MathUtil;
import audi.jenganative.math.Vector4;
import audi.jenganative.resources.GLGameData;

/**
 * Created by audi on 1-8-2018.
 */

public class BlockRenderer {
    private DiffuseShader shader = null;
    private Mesh mesh = null;
    private Camera camera;

    public BlockRenderer(DiffuseShader shader, Mesh mesh, Camera camera){
        this.shader = shader;
        this.mesh = mesh;
        this.camera = camera;

        shader.bind();
        //shader.getColor().set(new Vector4(1, 1, 1, 1));
        shader.unbind();
    }

    private List<Vector4> colors = new ArrayList<>();

    public void randomizeColor(int index){
        while(index >= colors.size()){
            float r = (float)Math.random();
            float g = (float)Math.random();
            float b = (float)Math.random();
            colors.add(new Vector4(r, g, b, 1));
        }

        //shader.getColor().set(colors.get(index));

    }

    public void render(GLGameData data) {
        if(shader == null){
            Log.e("BlockRenderer", "Shader is null");
            return;
        }
        if(mesh == null){
            Log.e("BlockRenderer", "Mesh is null");
            return;
        }

        shader.bind();
        mesh.bind();
        mesh.enableAttributes();;

        Matrix4f projection = camera.getProjection();
        Matrix4f view = camera.getView();

        Matrix4f mvp = new Matrix4f();


        int length = data.getBlockMatrixLength();

        for(int i = 0; i < length; i++){
            Matrix4f model = data.getBlockMatrix(i);

            if(model != null){

                randomizeColor(i);

                MathUtil.calculateMVP(mvp, model, view, projection);

                shader.getMVP().set(mvp);

                mesh.drawInstance();
            }
        }

        mesh.disableAttributes();
        mesh.unbind();
        shader.unbind();
    }
}
