package audi.jenganative.math;

/**
 * Created by audi on 1-8-2018.
 */

public class Vector4 {
    public float x;
    public float y;
    public float z;
    public float w;

    public Vector4(float x, float y, float z, float w){
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vector4(){

    }

    public Vector4(Vector3 v, float w){
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = w;
    }
}
