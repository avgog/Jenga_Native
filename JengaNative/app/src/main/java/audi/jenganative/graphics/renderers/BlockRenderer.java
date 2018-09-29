package audi.jenganative.graphics.renderers;

import android.opengl.GLES30;
import android.renderscript.Matrix4f;

import audi.jenganative.constants.Constants;
import audi.jenganative.graphics.Camera;
import audi.jenganative.graphics.Mesh;
import audi.jenganative.graphics.Vertex;
import audi.jenganative.graphics.shaders.ColorShader;
import audi.jenganative.graphics.shaders.DiffuseShader;
import audi.jenganative.math.MathUtil;
import audi.jenganative.resources.GLGameData;
import audi.jenganative.resources.MeshData;

/**
 * Created by audi on 1-8-2018.
 */

public class BlockRenderer {
    private DiffuseShader diffuseShader = null;
    private ColorShader outlineShader = null;
    private Mesh mesh = null;
    private Mesh outlineMesh = null;

    public BlockRenderer(DiffuseShader diffuseShader, ColorShader outlineShader, Mesh mainMesh, Mesh outlineMesh){
        this.diffuseShader = diffuseShader;
        this.outlineShader = outlineShader;

        this.mesh = mainMesh;
        this.outlineMesh = outlineMesh;
    }

    public void render(GLGameData data, Camera camera) {
        if(diffuseShader == null){
            //Log.e("BlockRenderer", "Shader is null");
            return;
        }
        if(mesh == null){
            //Log.e("BlockRenderer", "Mesh is null");
            return;
        }


        Matrix4f projection = camera.getProjection();
        Matrix4f view = camera.getView();

        Matrix4f mvp = new Matrix4f();


        int length = data.getBlockMatrixLength();


        diffuseShader.bind();
        mesh.bind();
        mesh.enableAttributes();;

        for(int i = 0; i < length; i++){
            Matrix4f model = data.getBlockMatrix(i);

            if(model != null){
                MathUtil.calculateMVP(mvp, model, view, projection);

                diffuseShader.getMVP().set(mvp);

                mesh.drawInstance();
            }
        }

        mesh.disableAttributes();
        mesh.unbind();
        diffuseShader.unbind();

        //draw outline mesh
        outlineShader.bind();
        outlineMesh.bind();
        outlineMesh.enableAttributes();;
        GLES30.glCullFace(GLES30.GL_FRONT);

        outlineShader.getColor().set(Constants.OUTLINE_COLOR);

        for(int i = 0; i < length; i++){
            Matrix4f model = data.getBlockMatrix(i);

            if(model != null){
                MathUtil.calculateMVP(mvp, model, view, projection);

                outlineShader.getMVP().set(mvp);

                outlineMesh.drawInstance();
            }
        }
        //-----------------

        GLES30.glCullFace(GLES30.GL_BACK);
        outlineMesh.disableAttributes();
        outlineMesh.unbind();
        outlineShader.unbind();
    }
}
