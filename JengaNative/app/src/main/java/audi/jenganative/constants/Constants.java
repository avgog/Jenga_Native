package audi.jenganative.constants;

import audi.jenganative.math.Vector3;
import audi.jenganative.math.Vector4;

/**
 * Created by audi on 5-8-2018.
 */

public final class Constants {
    //type constants
    public static final int FLOAT_SIZE = 4;
    public static final int SHORT_SIZE = 2;

    //background color
    public static final float BG_R = 0.8962264f;
    public static final float BG_G = 0.5634807f;
    public static final float BG_B = 0.06341223f;

    //Ground material color
    public static final float GR_R = 0.2f;
    public static final float GR_G = 0.2f;
    public static final float GR_B = 0.2f;

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
    public static final int TOWER_HEIGHT = 18;

    //Block size
    public static final float BLOCK_LENGTH = 1.5f;
    public static final float BLOCK_HEIGHT = 0.3f;
    public static final float BLOCK_WIDTH = 0.5f;

    //Ground size;
    public static final Vector3 GROUND_SIZE = new Vector3(10, 10, 1);
    //Input
    public static final float TOUCH_SENSITIVITY = 0.5f;

    //Camera
    public static final Vector3 CAM_START_POS = new Vector3(4,3,3);
    public static final Vector3 CAM_TARGET_POS = new Vector3(0, 2, 0);
    public static final float CAM_MIN_ANGLE_Y = -70;
    public static final float CAM_MAX_ANGLE_Y = 45;
    public static final boolean CAM_MOVE_INVERT_X = true;
    public static final boolean CAM_MOVE_INVERT_Y = true;

    //outline
    public static final Vector4 OUTLINE_COLOR = new Vector4(0,0,0,0);
    public static final float OUTLINE_THICKNESS = 0.01f;
    //public static final float OUTLINE_SIZE_MULT = 1.2f;

}
