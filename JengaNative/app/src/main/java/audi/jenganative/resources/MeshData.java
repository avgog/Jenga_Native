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
    private Vertex[] vertices;

    private MeshData(Vertex[] vertices){
        this.vertices = vertices;
    }

    //create a meshdata instance (which ccntains vertices) with vector and index arrays
    public static MeshData createFromArrays(Vector3[] positions, Vector2[] uvs, Vector3[] normals, short[] indices, short[] uvIndices, short[] normalIndices){
        final int vertexSize = indices.length;
        Vertex[] vertices = new Vertex[vertexSize];

        //create (full) vertices
        for(int i = 0; i < vertexSize; i++){
            short posIndex = indices[i];
            short uvIndex = uvIndices[i];
            short normalIndex = normalIndices[i];

            vertices[i] = new Vertex(
                    positions[posIndex],
                    uvs[uvIndex],
                    normals[normalIndex]
            );
        }

        return new MeshData(vertices);
    }

    //get te vertices (contains position, uv and normal)
    public Vertex[] getVertices(){
        return vertices;
    }

    public static MeshData createQuad(){
        Vector3 f = new Vector3(0, 1, 0); //forward
        Vertex[] vertices = {
                new Vertex(new Vector3(-0.5f,0.5f,0), new Vector2(0, 0), f.copy()), //Top left
                new Vertex(new Vector3(-0.5f,-0.5f,0), new Vector2(0, 1), f.copy()), //Bottom left
                new Vertex(new Vector3(0.5f,0.5f,0), new Vector2(1, 0), f.copy()), //Top Right
                new Vertex(new Vector3(0.5f,0.5f,0), new Vector2(1, 0), f.copy()), //Top right
                new Vertex(new Vector3(-0.5f,-0.5f,0), new Vector2(0, 1), f.copy()), //Bottom left
                new Vertex(new Vector3(0.5f,-0.5f,0), new Vector2(1, 1), f.copy()) //Bottom right
        };

        MeshData data = new MeshData(vertices);
        return data;
    }
}
