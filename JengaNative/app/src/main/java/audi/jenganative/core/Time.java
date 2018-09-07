package audi.jenganative.core;

/**
 * Created by audi on 5-8-2018.
 */


public class Time {
    private static long prevTime;
    private static double deltaTime;
    private static long frameCount = 0;

    private static final double NS_IN_SECONDS = 1000000000.0;
    private static final long NS_IN_MS = 1000000;

    private Time(){

    }

    public static long getSystemTime(){
        return System.nanoTime();
    }

    public static void start(){
        prevTime = getSystemTime();
    }

    public static void update(){
        long time = getSystemTime();
        deltaTime = ((time - prevTime) / NS_IN_SECONDS);
        prevTime = time;
        ++frameCount;
    }

    public static float getDeltatime(){
        return (float)deltaTime;
    }

    public static long getCurrentDeltaInMS(){
        long time = getSystemTime();
        return (time - prevTime) / NS_IN_MS;
    }

    public long getFrameCount(){
        return frameCount;
    }
}
