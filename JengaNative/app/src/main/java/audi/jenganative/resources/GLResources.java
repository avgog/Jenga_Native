package audi.jenganative.resources;

import android.content.Context;

import audi.jenganative.constants.Constants;

/**
 * Created by audi on 30-7-2018.
 */

public class GLResources {
    public String vertexShaderCode;
    public String fragmentShaderCode;
    public MeshData blockMeshData;

    public GLResources(){

    }

    public static GLResources CreateDefault(Context context){
        GLResources resources = new GLResources();
        resources.vertexShaderCode = FileUtil.loadTextFile(context, Constants.DIFFUSE_VERTEX_PATH);
        resources.fragmentShaderCode = FileUtil.loadTextFile(context, Constants.DIFFUSE_FRAGMENT_PATH);

        resources.blockMeshData = FileUtil.loadMesh(context, Constants.MESH_BLOCK_PATH);

        return resources;
    }
}
