package audi.jenganative.constants;

/**
 * Created by audi on 5-8-2018.
 */

public final class Constants {
    //type constants
    public static final int FLOAT_SIZE = 4;
    public static final int SHORT_SIZE = 2;

    //background
    public static final float BG_R = 0.8962264f;
    public static final float BG_G = 0.5634807f;
    public static final float BG_B = 0.06341223f;

    //mesh paths
    public static final String MESH_BLOCK_PATH = "meshes/JengaBlock.obj";
    public static final String MESH_CUBE_PATH = "meshes/Cube.obj";

    //Shader paths
    public static final String COLOR_VERTEX_PATH = "shaders/color.vertex.glsl";
    public static final String COLOR_FRAGMENT_PATH = "shaders/color.fragment.glsl";

    public static final String DIFFUSE_VERTEX_PATH = "shaders/diffuse.vertex.glsl";
    public static final String DIFFUSE_FRAGMENT_PATH = "shaders/diffuse.fragment.glsl";

    //Tower size
    public static final int TOWER_WIDTH = 3;
    public static final int TOWER_HEIGHT = 54;

    //Block size
    public static final float BLOCK_LENGTH = 1.5f;
    public static final float BLOCK_HEIGHT = 0.3f;
    public static final float BLOCK_WIDTH = 0.5f;

}
