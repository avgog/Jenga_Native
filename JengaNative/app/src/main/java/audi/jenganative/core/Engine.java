package audi.jenganative.core;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;

import java.util.List;

import audi.jenganative.OpenGLRenderer;
import audi.jenganative.components.Transform;
import audi.jenganative.resources.GLGameData;
import audi.jenganative.resources.GLResources;

/**
 * Created by audi on 5-8-2018.
 */

public class Engine {
    private Context context;
    private GLSurfaceView glView;
    GLResources resources;
    private OpenGLRenderer gl;
    private Activity activity;
    private Game game;

    public GLGameData glGameData = new GLGameData();

    private long frameCap = 16;

    public volatile boolean runEngine = true;

    public Engine(Context context, Activity activity){
        this.context = context;
        this.activity = activity;
        Time.start();

        resources = GLResources.CreateDefault(context);

        initGraphics(context, resources);

        game = new Game();
    }


    //this function should be called after the opengl thread is done with rendering
    private void manageGLGameData(){
        List<Transform> blocks = game.blocks;

        int length = blocks.size();
        for(int i = 0; i < length; i++){
            glGameData.setBlockMatrix(i, blocks.get(i).getMatrix());
        }

        gl.gameData = glGameData;
    }

    private void sleep(long ms){
        try{
            Thread.sleep(ms);
        }
        catch (Exception e){
            Log.e("Engine Error", e.getMessage());
        }
    }

    public void run(){
        runEngine = true;

        while(runEngine){
            Time.update();
            game.update();
            Transform.recalculateMatrices();

            while(gl.isRendering()){
                sleep(1);
            }

            manageGLGameData();
            glView.requestRender();

            long frameTimeMS = Time.getCurrentDeltaInMS();
            long remainingTime = frameCap - frameTimeMS;

            if(remainingTime > 0){
                sleep(remainingTime);
            }
        }
    }

    private void initGraphics(Context context, GLResources resources){

        glView = new GLSurfaceView(context);
        glView.setEGLContextClientVersion(3);

        gl = new OpenGLRenderer(context, resources);
        glView.setRenderer(gl);

        activity.setContentView(glView);

        glView.setPreserveEGLContextOnPause(true);
        glView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    public GLSurfaceView getView(){
        return glView;
    }
}
