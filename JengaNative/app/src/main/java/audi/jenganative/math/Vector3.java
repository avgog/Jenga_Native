package audi.jenganative.math;

/**
 * Created by audi on 1-8-2018.
 */

public class Vector3 {
    public static final int FLOAT_COUNT = 3;

    public float x;
    public float y;
    public float z;

    public Vector3(){

    }

    public Vector3(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(Vector3 v){
        x = v.x;
        y = v.y;
        z = v.z;
    }

    public Vector3 copy(){
        return new Vector3(x, y, z);
    }

    public Vector3 add(Vector3 v){
        return new Vector3(
                this.x + v.x,
                this.y + v.y,
                this.z + v.z
        );
    }

    public Vector3 min(Vector3 v){
        return new Vector3(
                this.x - v.x,
                this.y - v.y,
                this.z - v.z
        );
    }
    public Vector3 mul(Vector3 v){
        return new Vector3(
                this.x * v.x,
                this.y * v.y,
                this.z * v.z
        );
    }

    public Vector3 div(Vector3 v){
        return new Vector3(
                this.x / v.x,
                this.y / v.y,
                this.z / v.z
        );
    }



    public Vector3 add(float x, float y, float z){
        return new Vector3(
                this.x + x,
                this.y + y,
                this.z + z
        );
    }

    public Vector3 min(float x, float y, float z){
        return new Vector3(
                this.x - x,
                this.y - y,
                this.z - z
        );
    }
    public Vector3 mul(float x, float y, float z){
        return new Vector3(
                this.x * x,
                this.y * y,
                this.z * z
        );
    }

    public Vector3 div(float x, float y, float z){
        return new Vector3(
                this.x / x,
                this.y / y,
                this.z / z
        );
    }



    public Vector3 mul(float multiplier){
        return new Vector3(
                this.x * multiplier,
                this.y * multiplier,
                this.z * multiplier
        );
    }

    public Vector3 div(float divider){
        return new Vector3(
                this.x / divider,
                this.y / divider,
                this.z / divider
        );
    }


    @Override
    public String toString() {
        return "["+x+", "+y+", "+z+"]";
    }
}
