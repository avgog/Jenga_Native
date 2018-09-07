package audi.jenganative.resources;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
        }

        return null;
    }
}
