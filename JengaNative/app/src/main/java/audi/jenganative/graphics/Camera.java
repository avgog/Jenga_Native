package audi.jenganative.graphics;

import android.opengl.Matrix;
import android.renderscript.Matrix4f;

import audi.jenganative.math.Vector3;

/**
 * Created by audi on 4-8-2018.
 */

public class Camera {
    public float fov = 80;
    public float aspect = 9.0f / 16;
    public float near = 0.01f;
    public float far = 1000.0f;

    protected Vector3 position;
    protected Vector3 target;
    protected Vector3 up = new Vector3(0, 1, 0);

    public Camera(Vector3 position, Vector3 target){
        this.position = position;
        this.target = target;
    }

    public Matrix4f getView(){
        Matrix4f mat = new Matrix4f();
        Matrix.setLookAtM(mat.getArray(), 0,
                position.x, position.y, position.z,
                target.x, target.y, target.z,
                up.x, up.y, up.z);
        return mat;
    }

    public Matrix4f getProjection(){
        Matrix4f mat = new Matrix4f();
        mat.loadPerspective(fov, aspect, near, far);
        return mat;
    }
}
