package audi.jenganative.components;

/**
 * Created by audi on 1-8-2018.
 */
import android.renderscript.Matrix4f;

import java.util.ArrayList;

import audi.jenganative.math.Quaternion;
import audi.jenganative.math.Vector3;

public class Transform {
    private Vector3 position;
    private Quaternion rotation;
    private Vector3 scale;
    private Matrix4f matrix = new Matrix4f();

    private static ArrayList<Transform> recalcTransformList = new ArrayList<>(); //recalculate matrices
    private boolean queuedForMatrixRecalculation = false;

    public Transform(){
        position = new Vector3(0, 0, 0);
        rotation = Quaternion.identity();
        scale = new Vector3(1, 1, 1);
        matrix.loadScale(1, 1, 1);
    }

    public Transform(Vector3 pos, Quaternion rot, Vector3 scale){
        if(pos == null){
            pos = new Vector3();
        }
        if(rot == null){
            rot = Quaternion.identity();
        }
        if(scale == null){
            scale = new Vector3(1, 1, 1);
        }

        this.position = pos.copy();
        this.rotation = rot.copy();
        this.scale = scale.copy();

        Matrix4f temp = new Matrix4f();
        recalculateMatrix(temp);
    }

    public Vector3 getPosition(){
        return position.copy();
    }

    public Quaternion getRotation(){
        return rotation.copy();
    }

    public Vector3 getScale() {
        return scale.copy();
    }

    public void setPosition(float x, float y, float z){
        position.x = x;
        position.y = y;
        position.z = z;
        queueForRecalculation();
    }

    public void setRotation(float x, float y, float z, float w){
        rotation.x = x;
        rotation.y = y;
        rotation.z = z;
        rotation.w = w;
        queueForRecalculation();
    }

    public void setScale(float x, float y, float z){
        scale.x = x;
        scale.y = y;
        scale.z = z;
        queueForRecalculation();
    }

    public void move(Vector3 movement){
        if(movement != null){
            Vector3 pos = getPosition().add(movement);
            setPosition(pos);
        }
    }

    public void setPosition(Vector3 pos){
        if(pos != null){
            setPosition(pos.x, pos.y, pos.z);
        }
    }

    public void setRotation(Quaternion rot){
        if(rot != null){
            setRotation(rot.x, rot.y, rot.z, rot.w);
        }
    }

    public void setScale(Vector3 scale){
        if(scale != null){
            setScale(scale.x, scale.y, scale.z);
        }
    }

    public void setEulers(float x, float y, float z){
        this.rotation.setEulerAngles(x, y, z);
        this.queueForRecalculation();
    }




    private void recalculateMatrix(Matrix4f temp){
        /*
        //Scale
        matrix.loadScale(scale.x, scale.y, scale.z);

        //Rotation
        rotation.toMatrix(temp);
        matrix.multiply(temp);

        //Position
        temp.loadTranslate(position.x, position.y, position.z);
        matrix.multiply(temp);
        */

        matrix.loadTranslate(position.x, position.y, position.z);
        rotation.toMatrix(temp);
        matrix.multiply(temp);

        temp.loadScale(scale.x, scale.y, scale.z);
        matrix.multiply(temp);
    }

    public static void recalculateMatrices(){
        int length = recalcTransformList.size();

        Matrix4f temp = new Matrix4f();

        for(int i = 0; i < length; i++){
            Transform tr = recalcTransformList.get(i);

            if(tr != null){

                tr.recalculateMatrix(temp);
                tr.queuedForMatrixRecalculation = false;
            }

        }

        recalcTransformList.clear();
    }

    public Matrix4f getMatrix(){
        return matrix;
    }

    private void queueForRecalculation(){
        if(!queuedForMatrixRecalculation){
            queuedForMatrixRecalculation = true;
            recalcTransformList.add(this);
        }
    }
}
