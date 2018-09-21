package audi.jenganative.graphics;

import audi.jenganative.math.Vector2;
import audi.jenganative.math.Vector3;

/**
 * Created by audi on 30-7-2018.
 */


public class Vertex {
    public static final int FLOAT_COUNT = 8;

    public Vector3 position;
    public Vector2 uv;
    public Vector3 normal;

    public Vertex(Vector3 position, Vector2 uv, Vector3 normal){
        this.position = position;
        this.uv = uv;
        this.normal = normal;
    }

    public Vertex(Vector3 position){
        this.position = position;
    }

    public static float[] ToFloatArray(Vertex[] vertices){
        int vertCount = vertices.length;
        int floatCount = vertCount * FLOAT_COUNT;
        float array[] = new float[floatCount];

        for(int i = 0; i < vertCount; i++){
            int arrIndex = i * FLOAT_COUNT;
            Vertex vert = vertices[i];
            array[arrIndex+0] = vert.position.x;
            array[arrIndex+1] = vert.position.y;
            array[arrIndex+2] = vert.position.z;
            array[arrIndex+3] = vert.uv.x;
            array[arrIndex+4] = vert.uv.y;
            array[arrIndex+5] = vert.normal.x;
            array[arrIndex+6] = vert.normal.y;
            array[arrIndex+7] = vert.normal.z;
        }

        return array;
    }

    public String toString(){
        return "["+position.toString() + ", " + uv.toString() + ", " + normal.toString() +"]";
    }
}
