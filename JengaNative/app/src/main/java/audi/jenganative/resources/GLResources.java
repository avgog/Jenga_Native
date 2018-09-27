package audi.jenganative.resources;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import audi.jenganative.R;
import audi.jenganative.constants.Constants;

/**
 * Created by audi on 30-7-2018.
 */

public class GLResources {
    public String vertexShaderCode;
    public String fragmentShaderCode;
    public String colorVertexCode;
    public String colorFragmentCode;

    public MeshData blockMeshData;
    public Bitmap blockBitmap;

    public GLResources(){

    }

    public static GLResources CreateDefault(Context context){
        GLResources resources = new GLResources();
        resources.vertexShaderCode = FileUtil.loadTextFile(context, Constants.DIFFUSE_VERTEX_PATH);
        resources.fragmentShaderCode = FileUtil.loadTextFile(context, Constants.DIFFUSE_FRAGMENT_PATH);
        resources.colorVertexCode = FileUtil.loadTextFile(context, Constants.COLOR_VERTEX_PATH);
        resources.colorFragmentCode = FileUtil.loadTextFile(context, Constants.COLOR_FRAGMENT_PATH);

        resources.blockMeshData = FileUtil.loadMesh(context, Constants.MESH_BLOCK_PATH);

        resources.blockBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.block);

        return resources;
    }
}
