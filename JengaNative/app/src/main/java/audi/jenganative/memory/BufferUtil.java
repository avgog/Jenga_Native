package audi.jenganative.memory;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.List;

import audi.jenganative.math.Vector2;
import audi.jenganative.math.Vector3;

/**
 * Created by audi on 30-7-2018.
 */

public class BufferUtil {
    private static final int FLOAT_SIZE = 4;
    private static final int SHORT_SIZE = 2;

    public static FloatBuffer createNativeFloatBuffer(float array[]){
        ByteBuffer bb = ByteBuffer.allocateDirect(array.length * FLOAT_SIZE);
        bb.order(ByteOrder.nativeOrder());

        FloatBuffer buffer = bb.asFloatBuffer();
        buffer.put(array);
        buffer.position(0);
        return buffer;
    }

    public static ShortBuffer createNativeShortBuffer(short array[]){
        ByteBuffer bb = ByteBuffer.allocateDirect(array.length * SHORT_SIZE);
        bb.order(ByteOrder.nativeOrder());

        ShortBuffer buffer = bb.asShortBuffer();
        buffer.put(array);
        buffer.position(0);
        return buffer;
    }

    public static short[] shortListToArray(List<Short> list){
        int size = list.size();
        short[] array = new short[size];

        for(int i = 0; i < size; i++){
            array[i] = list.get(i).shortValue();
        }

        return array;
    }

    public static Vector2[] vec2ListToArray(List<Vector2> list){
        int size = list.size();
        Vector2[] array = new Vector2[size];

        for(int i = 0; i < size; i++){
            array[i] = list.get(i);
        }

        return array;
    }

    public static Vector3[] vec3ListToArray(List<Vector3> list){
        int size = list.size();
        Vector3[] array = new Vector3[size];

        for(int i = 0; i < size; i++){
            array[i] = list.get(i);
        }

        return array;
    }

    public static<T> String arrayToString(T[] arr){
        String content = "{";

        for(int i = 0; i < arr.length; i++){
            if(i == arr.length - 1){
                content += arr[i].toString() + "\n";
            }
            else{
                content += arr[i].toString() + ",\n";
            }
        }

        content += "}";

        return content;
    }

    public static String shortArrayToString(short[] arr){
        String content = "{";

        for(int i = 0; i < arr.length; i++){
            if(i == arr.length - 1){
                content += arr[i] + "\n";
            }
            else{
                content += arr[i] + ",\n";
            }
        }

        content += "}";

        return content;
    }
}
