package audi.jenganative.graphics;

/**
 * Created by audi on 30-7-2018.
 */

import android.opengl.GLES30;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import audi.jenganative.constants.Constants;
import audi.jenganative.math.Vector2;
import audi.jenganative.math.Vector3;
import audi.jenganative.memory.BufferUtil;
import audi.jenganative.resources.MeshData;

public class Mesh {
    private int vbo[] = {0}; //vertex buffer object
    private int ibo[] = {0}; //index buffer object
    private int size; //vertex buffer float count
    private int indexCount;



    private void createVBO(int bufferPtr[], int bufferType, Buffer buffer, int bufferSize){
        GLES30.glGenBuffers(1, bufferPtr, 0);
        GLES30.glBindBuffer(bufferType, bufferPtr[0]);
        GLES30.glBufferData(bufferType, bufferSize, buffer, GLES30.GL_STATIC_DRAW);
        GLES30.glBindBuffer(bufferType, 0); //unbind buffer
    }

    public Mesh(MeshData data){
        Vertex[] vertices = data.getVertices();
        //short[] indices = data.getIndices();

        short[] indices = new short[vertices.length];

        for(short i = 0; i < indices.length; i++){
            indices[i] = i;
        }

        size = vertices.length * Vertex.FLOAT_COUNT;
        indexCount = indices.length;

        FloatBuffer vertBuffer = BufferUtil.createNativeFloatBuffer(Vertex.ToFloatArray(vertices));
        ShortBuffer indexBuffer = BufferUtil.createNativeShortBuffer(indices);

        createVBO(vbo, GLES30.GL_ARRAY_BUFFER, vertBuffer, size * Constants.FLOAT_SIZE);
        createVBO(ibo, GLES30.GL_ELEMENT_ARRAY_BUFFER, indexBuffer, indices.length * Constants.SHORT_SIZE);

        vertBuffer.clear();
        indexBuffer.clear();
    }

    private void deleteVBO(int bufferPtr[], int bufferType){
        GLES30.glBindBuffer(bufferType, bufferPtr[0]);
        GLES30.glDeleteBuffers(1, bufferPtr, 0);
    }

    public void delete(){
        deleteVBO(vbo, GLES30.GL_ARRAY_BUFFER);
        deleteVBO(ibo, GLES30.GL_ELEMENT_ARRAY_BUFFER);
    }

    public void bind(){
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, vbo[0]);
        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, ibo[0]);
    }

    public void unbind(){
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 0);
        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public void enableAttributes(){
        final int stride = Vertex.FLOAT_COUNT * Constants.FLOAT_SIZE;
        final int vertStart = 0;
        final int uvStart = 3 * Constants.FLOAT_SIZE;
        final int normalStart = (3 + 2) * Constants.FLOAT_SIZE;

        GLES30.glVertexAttribPointer(
                0,
                Vector3.FLOAT_COUNT,
                GLES30.GL_FLOAT,
                false,
                stride,
                vertStart
        );
        GLES30.glVertexAttribPointer(
                1,
                Vector2.FLOAT_COUNT,
                GLES30.GL_FLOAT,
                false,
                stride,
                uvStart
        );
        GLES30.glVertexAttribPointer(
                2,
                Vector2.FLOAT_COUNT,
                GLES30.GL_FLOAT,
                false,
                stride,
                normalStart
        );

        GLES30.glEnableVertexAttribArray(0);
        GLES30.glEnableVertexAttribArray(1);
        GLES30.glEnableVertexAttribArray(2);

    }

    public void disableAttributes(){
        GLES30.glDisableVertexAttribArray(0);
        GLES30.glDisableVertexAttribArray(1);
        GLES30.glDisableVertexAttribArray(2);
    }


    public void drawInstance(){
        /*if(indexCount > 0){
            GLES30.glDrawElements(GLES30.GL_TRIANGLES, indexCount, GLES30.GL_UNSIGNED_SHORT, 0);
        }
        else{
            GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, size);
        }*/

        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, size);
    }

    public int getVertexCount(){
        return size;
    }

    public int getIndexCount(){
        return indexCount;
    }

}
