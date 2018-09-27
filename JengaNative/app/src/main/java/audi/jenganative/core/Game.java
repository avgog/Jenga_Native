package audi.jenganative.core;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import audi.jenganative.components.Transform;
import audi.jenganative.constants.Constants;
import audi.jenganative.math.Quaternion;
import audi.jenganative.math.Vector3;

/**
 * Created by audi on 5-8-2018.
 */

public class Game {
    public List<Transform> blocks = new ArrayList<>();
    public Transform ground = new Transform(new Vector3(0,-Constants.BLOCK_LENGTH / 2,0), Quaternion.eulerAngles(0,90, 0), new Vector3(5, 5, 1));

    private int count = 0;
    public Game(){
        Vector3 blockSize = new Vector3(
                Constants.BLOCK_LENGTH / 2,
                Constants.BLOCK_HEIGHT / 2,
                Constants.BLOCK_WIDTH / 2
        );

        for(int y = 0; y < Constants.TOWER_HEIGHT; y++){
            for(int x = 0; x < Constants.TOWER_WIDTH; x++){
                Vector3 pos = new Vector3(0, y * blockSize.y, x * blockSize.z);
                Transform transform = new Transform(pos, Quaternion.identity(), new Vector3(1, 1, 1));
                blocks.add(transform);
            }
        }
    }

    float eulerX = 0;

    public void update(){
        eulerX += Time.getDeltatime() * 20;
        blocks.get(0).setEulers(eulerX, 0, 0);
        blocks.get(0).setPosition(2, 0, 1);
    }
}
