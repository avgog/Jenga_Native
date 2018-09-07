package audi.jenganative.math;

import android.renderscript.Matrix4f;

/**
 * Created by audi on 1-8-2018.
 */

public class MathUtil {
    public static final float degreesToRadians = 0.0174532925f;

    public static void calculateMVP(Matrix4f out_mvp, Matrix4f model, Matrix4f view, Matrix4f projection){
        out_mvp.load(projection);
        out_mvp.multiply(view);
        out_mvp.multiply(model);
    }
}