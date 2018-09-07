package audi.jenganative.math;

/**
 * Created by audi on 5-8-2018.
 */

public class Vector2 {
    public static final int FLOAT_COUNT = 2;

    public float x;
    public float y;

    public Vector2(float x, float y){
        this.x = x;
        this.y = y;
    }

    public Vector2(){
        this.x = 0;
        this.y = 0;
    }

    public Vector2 copy(){
        return new Vector2(x, y);
    }

    @Override
    public String toString() {
        return "["+x+", "+y+"]";
    }
}
