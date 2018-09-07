package audi.jenganative.resources;

import android.util.Log;

import audi.jenganative.graphics.Vertex;
import audi.jenganative.math.Vector2;
import audi.jenganative.math.Vector3;
import audi.jenganative.memory.BufferUtil;

/**
 * Created by audi on 5-8-2018.
 */

public class MeshData {
    private Vector3[] positions;
    private Vector2[] uvs;
    private Vector3[] normals;

    private short[] indices;
    private short[] uvIndices;
    private short[] normalIndices;

    private Vertex[] vertices;

    MeshData(Vector3[] positions, Vector2[] uvs, Vector3[] normals, short indices[], short uvIndices[], short normalIndices[]){
        this.positions = positions;
        this.uvs = uvs;
        this.normals = normals;
        this.indices = indices;
        this.uvIndices = uvIndices;
        this.normalIndices = normalIndices;

        //Log.i("MeshData", toString());
    }

    @Override
    public String toString() {
        String content = "";

        content += "positions:\n " + BufferUtil.arrayToString(positions) + "\n";
        content += "uvs:\n " + BufferUtil.arrayToString(uvs) + "\n";
        content += "normals:\n " + BufferUtil.arrayToString(normals) + "\n";

        content += "indices:\n " + BufferUtil.shortArrayToString(indices) + "\n";
        content += "uvIndices:\n " + BufferUtil.shortArrayToString(uvIndices) + "\n";
        content += "normalIndices:\n " + BufferUtil.shortArrayToString(normalIndices) + "\n";

        return content;
    }

    public void createVertices(){
        final int vertexSize = positions.length;
        final int uvIndexCount = uvIndices.length;
        final int normalIndexCount = normalIndices.length;

        vertices = new Vertex[vertexSize];

        //set positions
        for(int i = 0; i < vertexSize; i++){
            vertices[i] = new Vertex(positions[i]);
        }

        //set uvs
        for(int i = 0; i < uvIndexCount; i++){
            short uvIndex = uvIndices[i];
            Vector2 uv = uvs[uvIndex];

            short vertIndex = indices[i];
            vertices[vertIndex].uv = uv;
        }

        //set normals
        for(int i = 0; i < normalIndexCount; i++) {
            short normalIndex = normalIndices[i];
            Vector3 normal = normals[normalIndex];

            short vertIndex = indices[i];
            vertices[vertIndex].normal = normal;
        }

    }

    public Vertex[] getVertices(){
        if(vertices == null){
            createVertices();
        }
        return vertices;
    }

    public short[] getIndices(){
        return indices;
    }


}
