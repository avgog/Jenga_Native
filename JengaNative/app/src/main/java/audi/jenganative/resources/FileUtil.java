package audi.jenganative.resources;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import audi.jenganative.graphics.Mesh;
import audi.jenganative.graphics.Vertex;
import audi.jenganative.math.Vector2;
import audi.jenganative.math.Vector3;
import audi.jenganative.memory.BufferUtil;

/**
 * Created by audi on 30-7-2018.
 */

public class FileUtil {
    public static String loadTextFile(Context context, String path){
        try{
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open(path))
            );

            String line = reader.readLine();
            StringBuilder content = new StringBuilder();

            while(line != null){
                content.append(line + "\n");
                line = reader.readLine();
            }

            return content.toString();
        }
        catch (IOException e){
            Log.e("Jenga IO", "error msg: " + e.getMessage());
        }

        return "";
    }


    //supported formats: .obj
    public static MeshData loadMesh(Context context, String path){
        /*try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open(path))
            );

            String line = reader.readLine();

            //List<Vertex> vertices = new ArrayList<>();
            List<Vector3> positions = new ArrayList<>();
            List<Vector2> uvs = new ArrayList<>();
            List<Vector3> normals = new ArrayList<>();

            List<Short> indices = new ArrayList<>();
            List<Short> uvIndices = new ArrayList<>();
            List<Short> normalIndices = new ArrayList<>();

            while(line != null){
                String parts[] = line.split("[\\s]");

                String type = parts[0];

                try{
                    switch (type){
                        case "v": //vertex position
                            float x = Float.parseFloat(parts[1]);
                            float y = Float.parseFloat(parts[2]);
                            float z = Float.parseFloat(parts[3]);

                            Vector3 pos = new Vector3(x, y, z);
                            positions.add(pos);
                            break;
                        case "vt": //texture uv
                            float u = Float.parseFloat(parts[1]);
                            float v = Float.parseFloat(parts[2]);
                            v = 1 - v;
                            //u = 1 - u;

                            Vector2 uv = new Vector2(u, v);
                            uvs.add(uv);
                            break;
                        case "vn": //vertex normal
                            float xn = Float.parseFloat(parts[1]);
                            float yn = Float.parseFloat(parts[2]);
                            float zn = Float.parseFloat(parts[3]);

                            Vector3 normal = new Vector3(xn, yn, zn);
                            normals.add(normal);
                            break;
                        case "f": //index
                            for(int i = 1; i < parts.length; i++){
                                String part = parts[i];
                                String indexParts[] = part.replaceAll("/", " ").split(" ");

                                for(int p = 0; p < 3; p++){
                                    if(!indexParts[p].trim().isEmpty()){
                                        short index = Short.parseShort(indexParts[p]);
                                        index -= 1; //index in de .obj bestand begint met 1
                                        switch (p){
                                            case 0: indices.add(index); break;
                                            case 1: uvIndices.add(index); break;
                                            case 2: normalIndices.add(index); break;
                                        }
                                    }
                                    else{
                                        Log.e("Jenga IO", "empty string error");
                                    }
                                }

                            }
                            break;
                    }
                }
                catch (Exception e){
                    Log.e("Jenga Mesh IO Error", "msg: " + e.getMessage());
                }

                line = reader.readLine();
            }

            return new MeshData(
                    BufferUtil.vec3ListToArray(positions),
                    BufferUtil.vec2ListToArray(uvs),
                    BufferUtil.vec3ListToArray(normals),
                    BufferUtil.shortListToArray(indices),
                    BufferUtil.shortListToArray(uvIndices),
                    BufferUtil.shortListToArray(normalIndices)
            );
        }
        catch (IOException e){
            Log.e("Jenga IO", "error msg: " + e.getMessage());
        }*/


        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open(path))
            );

            String line = reader.readLine();

            //List<Vertex> vertices = new ArrayList<>();
            List<Vector3> positions = new ArrayList<>();
            List<Vector2> uvs = new ArrayList<>();
            List<Vector3> normals = new ArrayList<>();

            List<Short> indices = new ArrayList<>();
            List<Short> uvIndices = new ArrayList<>();
            List<Short> normalIndices = new ArrayList<>();

            String tempText = "new short[]{";

            while (line != null) {
                if (line == null || line.equals("") || line.startsWith("#")) {
                } else {
                    String[] split = line.split(" ");
                    switch (split[0]) {
                        case "v":
                            positions.add(new Vector3(
                                    Float.parseFloat(split[1]),
                                    Float.parseFloat(split[2]),
                                    Float.parseFloat(split[3])
                            ));
                            break;
                        case "vn":
                            normals.add(new Vector3(
                                    Float.parseFloat(split[1]),
                                    Float.parseFloat(split[2]),
                                    Float.parseFloat(split[3])
                            ));
                            break;
                        case "vt":
                            uvs.add(new Vector2(
                                    Float.parseFloat(split[1]),
                                    1-Float.parseFloat(split[2])
                            ));
                            break;
                        case "f":
                                    Short[] pIndices = new Short[]{
                                            Short.parseShort(split[1].split("/")[0]),
                                            Short.parseShort(split[2].split("/")[0]),
                                            Short.parseShort(split[3].split("/")[0])
                                    };
                                    Short[] uIndices = new Short[]{
                                            Short.parseShort(split[1].split("/")[1]),
                                            Short.parseShort(split[2].split("/")[1]),
                                            Short.parseShort(split[3].split("/")[1])
                                    };
                                    Short[] nIndices = new Short[]{
                                            Short.parseShort(split[1].split("/")[2]),
                                            Short.parseShort(split[2].split("/")[2]),
                                            Short.parseShort(split[3].split("/")[2])
                                    };

                                    //Short[] test = new Short[2];
                                    indices.addAll(Arrays.asList(BufferUtil.incrementAllShorts(pIndices, (short)-1)));
                                    uvIndices.addAll(Arrays.asList(BufferUtil.incrementAllShorts(uIndices, (short)-1)));
                                    normalIndices.addAll(Arrays.asList(BufferUtil.incrementAllShorts(nIndices, (short)-1)));

                                    for(int i = 0; i < 3; i++){
                                        tempText += uIndices[i] + ", ";
                                    }

                                    tempText += "\n";
                            break;
                        default:
                            //System.err.println("[OBJ] Unknown Line: " + line);
                    }
                }
                line = reader.readLine();
            }

            tempText += "};";
            //Log.e("test", "test");
            //Log.e("Error", tempText);


            //uvIndices = BufferUtil.createFlippedList(uvIndices);

            return new MeshData(
                    BufferUtil.vec3ListToArray(positions),
                    BufferUtil.vec2ListToArray(uvs),
                    //tempUVArr(),
                    BufferUtil.vec3ListToArray(normals),
                    BufferUtil.shortListToArray(indices),
                    (BufferUtil.shortListToArray(uvIndices)),
                    //tempUIndicesArr(),
                    BufferUtil.shortListToArray(normalIndices)
            );


        }
        catch (Exception e){
            Log.e("Obj. Loading", e.getMessage());
        }

        return null;
    }

    public static short[] tempUIndicesArr(){
        return new short[]{0, 1, 2,
                3, 4, 5,
                6, 7, 8,
                9, 10, 11,
                12, 13, 14,
                15, 16, 17,
                18, 19, 20,
                21, 22, 23,
                24, 25, 26,
                27, 28, 29,
                30, 31, 32,
                33, 34, 35,
                36, 37, 38,
                39, 40, 41,
                42, 43, 44,
                45, 46, 47,
                48, 49, 50,
                51, 52, 53,
                0, 2, 54,
                3, 5, 55,
                6, 8, 56,
                9, 11, 57,
                12, 14, 58,
                15, 17, 59,
                18, 20, 60,
                21, 23, 61,
                24, 26, 62,
                27, 29, 63,
                30, 32, 64,
                33, 35, 65,
                36, 38, 66,
                39, 41, 67,
                42, 44, 68,
                45, 47, 69,
                49, 70, 71,
                72, 73, 74,
                73, 70, 75,
                76, 75, 77,
                70, 49, 48,
                78, 50, 79,
                50, 49, 80,
                81, 80, 82,
                71, 73, 72,
                75, 70, 77,
                48, 50, 78,
                80, 49, 82,
                71, 70, 73,
                83, 84, 85,
                52, 86, 87,
                88, 89, 90,
                89, 86, 91,
                92, 91, 93,
                86, 52, 51,
                94, 53, 95,
                53, 52, 84,
                85, 84, 52,
                88, 87, 89,
                93, 91, 86,
                94, 51, 53,
                87, 86, 89,
        };

    }
    public static Vector2[] tempUVArr(){

        return new Vector2[]{new Vector2(0.3273f, 0.6587f),
                new Vector2(0.6304f, 0.3413f),
                new Vector2(0.3273f, 0.3413f),
                new Vector2(0.6304f, 0.6587f),
                new Vector2(0.3112f, 0.6587f),
                new Vector2(0.0081f, 0.3413f),
                new Vector2(0.0081f, 0.6587f),
                new Vector2(0.3112f, 0.3413f),
                new Vector2(0.9497f, 0.3413f),
                new Vector2(0.6466f, 0.6587f),
                new Vector2(0.9497f, 0.6587f),
                new Vector2(0.6466f, 0.3413f),
                new Vector2(0.1187f, 0.9921f),
                new Vector2(0.1137f, 0.6746f),
                new Vector2(0.1137f, 0.9921f),
                new Vector2(0.1187f, 0.6746f),
                new Vector2(0.9658f, 0.3413f),
                new Vector2(0.9708f, 0.6587f),
                new Vector2(0.9708f, 0.3413f),
                new Vector2(0.9658f, 0.6587f),
                new Vector2(0.0081f, 0.6746f),
                new Vector2(0.0131f, 0.9921f),
                new Vector2(0.0131f, 0.6746f),
                new Vector2(0.0081f, 0.9921f),
                new Vector2(0.9869f, 0.3413f),
                new Vector2(0.9919f, 0.6587f),
                new Vector2(0.9919f, 0.3413f),
                new Vector2(0.9869f, 0.6587f),
                new Vector2(0.0765f, 0.9921f),
                new Vector2(0.0715f, 0.6746f),
                new Vector2(0.0715f, 0.9921f),
                new Vector2(0.0765f, 0.6746f),
                new Vector2(0.0976f, 0.9921f),
                new Vector2(0.0926f, 0.6746f),
                new Vector2(0.0926f, 0.9921f),
                new Vector2(0.0976f, 0.6746f),
                new Vector2(0.1771f, 0.6746f),
                new Vector2(0.1821f, 0.9921f),
                new Vector2(0.1821f, 0.6746f),
                new Vector2(0.1771f, 0.9921f),
                new Vector2(0.1348f, 0.6746f),
                new Vector2(0.1399f, 0.9921f),
                new Vector2(0.1399f, 0.6746f),
                new Vector2(0.1348f, 0.9921f),
                new Vector2(0.161f, 0.9921f),
                new Vector2(0.156f, 0.6746f),
                new Vector2(0.156f, 0.9921f),
                new Vector2(0.161f, 0.6746f),
                new Vector2(0.2033f, 0.9921f),
                new Vector2(0.1982f, 0.6746f),
                new Vector2(0.1982f, 0.9921f),
                new Vector2(0.2033f, 0.6746f),
                new Vector2(0.0342f, 0.9921f),
                new Vector2(0.0292f, 0.6746f),
                new Vector2(0.0292f, 0.9921f),
                new Vector2(0.0342f, 0.6746f),
                new Vector2(0.0503f, 0.6746f),
                new Vector2(0.0553f, 0.9921f),
                new Vector2(0.0553f, 0.6746f),
                new Vector2(0.0503f, 0.9921f),
                new Vector2(0.6852f, 0.3254f),
                new Vector2(0.9884f, 0.0079f),
                new Vector2(0.6852f, 0.0079f),
                new Vector2(0.9884f, 0.3254f),
                new Vector2(0.6691f, 0.0175f),
                new Vector2(0.6595f, 0.0079f),
                new Vector2(0.3467f, 0.0175f),
                new Vector2(0.6691f, 0.3159f),
                new Vector2(0.3467f, 0.3159f),
                new Vector2(0.3563f, 0.0079f),
                new Vector2(0.6678f, 0.0127f),
                new Vector2(0.6643f, 0.0092f),
                new Vector2(0.348f, 0.0127f),
                new Vector2(0.3515f, 0.0092f),
                new Vector2(0.3563f, 0.3254f),
                new Vector2(0.6595f, 0.3254f),
                new Vector2(0.348f, 0.3206f),
                new Vector2(0.3515f, 0.3241f),
                new Vector2(0.6678f, 0.3206f),
                new Vector2(0.6643f, 0.3241f),
                new Vector2(0.3209f, 0.3254f),
                new Vector2(0.3305f, 0.3159f),
                new Vector2(0.3209f, 0.0079f),
                new Vector2(0.0177f, 0.3254f),
                new Vector2(0.0177f, 0.0079f),
                new Vector2(0.3305f, 0.0175f),
                new Vector2(0.3257f, 0.0092f),
                new Vector2(0.3292f, 0.0127f),
                new Vector2(0.3257f, 0.3241f),
                new Vector2(0.3292f, 0.3206f),
                new Vector2(0.0081f, 0.0175f),
                new Vector2(0.0081f, 0.3159f),
                new Vector2(0.0129f, 0.0092f),
                new Vector2(0.0094f, 0.0127f),
                new Vector2(0.0129f, 0.3241f),
                new Vector2(0.0094f, 0.3206f),
        };
    }

}
