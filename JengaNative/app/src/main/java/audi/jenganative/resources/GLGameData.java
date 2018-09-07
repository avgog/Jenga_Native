package audi.jenganative.resources;

import android.renderscript.Matrix4f;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by audi on 5-8-2018.
 */

public class GLGameData {
    private List<Matrix4f> blockMatrices = new ArrayList<>();

    public GLGameData(){

    }

    public void setBlockMatrix(int index, Matrix4f mat){
        if(mat == null){
            return;
        }

        while(blockMatrices.size() <= index){
            blockMatrices.add(new Matrix4f());
        }

        blockMatrices.get(index).load(mat);
    }

    public Matrix4f getBlockMatrix(int index){
        return blockMatrices.get(index);
    }

    public int getBlockMatrixLength(){
        return blockMatrices.size();
    }

}