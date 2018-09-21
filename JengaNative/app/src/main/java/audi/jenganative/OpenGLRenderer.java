package audi.jenganative;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.util.Log;

import javax.microedition.khronos.opengles.GL10;

import audi.jenganative.constants.CLog;
import audi.jenganative.constants.Constants;
import audi.jenganative.graphics.Camera;
import audi.jenganative.graphics.Mesh;
import audi.jenganative.graphics.Texture;
import audi.jenganative.graphics.renderers.BlockRenderer;
import audi.jenganative.graphics.shaders.DiffuseShader;
import audi.jenganative.math.Vector2;
import audi.jenganative.math.Vector3;
import audi.jenganative.resources.GLGameData;
import audi.jenganative.resources.GLResources;
import audi.jenganative.resources.MeshData;

/**
 * Created by audi on 29-7-2018.
 */



public class OpenGLRenderer implements GLSurfaceView.Renderer {
    private Mesh mesh;
    private DiffuseShader shader;
    private Camera camera;
    private BlockRenderer renderer;
    private Texture texture;

    public GLGameData gameData = new GLGameData();

    private int screenWidth;
    private int screenHeight;

    private Context context;
    public GLResources resources;

    private volatile boolean isRendering = false;

    public OpenGLRenderer(Context context, GLResources resources) {
        this.context = context;
        this.resources = resources;
        this.camera = new Camera(new Vector3(4, 3, 3), new Vector3(0, 0, 0));
    }

    public boolean isRendering(){
        return isRendering;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, javax.microedition.khronos.egl.EGLConfig eglConfig) {

        GLES30.glClearColor(Constants.BG_R, Constants.BG_G, Constants.BG_B, 1.0f);
        GLES30.glCullFace(GLES30.GL_BACK);
        GLES30.glFrontFace(GLES30.GL_CCW);
        GLES30.glEnable(GLES30.GL_CULL_FACE);
        GLES30.glEnable(GLES30.GL_BLEND);
        GLES30.glEnable(GLES30.GL_DEPTH_TEST);

        //GLES30.glBlendFunc(GLES30.GL_BLEND_SRC_ALPHA, GLES30.GL_ONE_MINUS_SRC_ALPHA);

        GLES30.glEnable(GLES30.GL_TEXTURE0);

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.block);

        //Matrix mat = new Matrix();
        //mat.postScale(1, -1);
        //mat.postRotate(-180);

        //Bitmap flippedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mat, true);

        texture = new Texture(bitmap);
        GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
        texture.bind();

        resources.blockMeshData.createVertices();
        mesh = new Mesh(resources.blockMeshData);
        String content = resources.blockMeshData.verticesUVToString();
        Log.e("Vertices UV", content);
        //mesh = createQuadMesh(); //temp


        shader = new DiffuseShader(resources.vertexShaderCode, resources.fragmentShaderCode);

        if(shader.getMessage() != null){
            Log.e(CLog.SHADER, shader.getMessage());
        }

        shader.bind();

        renderer = new BlockRenderer(shader, mesh, camera);
    }

    public void draw(){

        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT);

        if(gameData != null){
            renderer.render(gameData);
        }
    }


    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        screenWidth = width;
        screenHeight = height;
        GLES30.glViewport(0, 0, screenWidth, screenHeight);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        isRendering = true;
        draw();
        isRendering = false;
    }

    public Mesh createQuadMesh(){
        Vector3[] positions = {
                new Vector3(0,0,0),
                new Vector3(0, 1, 0),
                new Vector3(1, 0, 0),
                new Vector3(1, 1, 0)
        };

        short[] indices = {
                0, 1, 2,
                2, 1, 3
        };

        Vector3 forward = new Vector3(0, 1, 0);
        Vector3[] normals = {
                forward.copy(),
                forward.copy(),
                forward.copy(),
                forward.copy()
        };

        Vector2[] uvs = {
                new Vector2(0, 0),
                new Vector2(1, 0),
                new Vector2(0,1),
                new Vector2(1, 1)
        };

        short[] uIndices = {0, 1, 2, 3};

        MeshData data = new MeshData(positions, uvs, normals, indices, indices, indices);
        data.createVertices();
        return new Mesh(data);
    }
}
