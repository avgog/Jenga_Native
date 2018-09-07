package audi.jenganative;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.app.ActivityManager;
import android.content.pm.ConfigurationInfo;
import android.util.Log;

import audi.jenganative.constants.CLog;
import audi.jenganative.constants.Constants;
import audi.jenganative.core.Engine;
import audi.jenganative.resources.FileUtil;
import audi.jenganative.resources.GLResources;

public class GameActivity extends Activity {

    private Engine engine;

    Thread mainThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(detectOpenGLES30()){
            engine = new Engine(getBaseContext(), this);
            mainThread = createMainThread();
            mainThread.start();
        }
        else{
            Log.e(CLog.GRAPHICS, CLog.OPENGL_NOT_SUPPORTED);
            finish();
        }
    }

    private Thread createMainThread(){
        return new Thread(new Runnable() {
            @Override
            public void run() {
                engine.run();
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        engine.getView().onPause();
        engine.runEngine = false;

        try{
            mainThread.join();
            Log.i("GameActivity", "Paused main thread.");
        }
        catch (Exception e){
            Log.e("GameActivity", e.getMessage());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        engine.getView().onResume();

        if(engine.runEngine == false){
            engine.runEngine = true;
            mainThread = createMainThread();
            mainThread.start();
        }
    }

    private boolean detectOpenGLES30() {
        ActivityManager am =
                ( ActivityManager ) getSystemService ( Context.ACTIVITY_SERVICE );
        ConfigurationInfo info = am.getDeviceConfigurationInfo();
        return ( info.reqGlEsVersion >= 0x30000 );
    }


}
