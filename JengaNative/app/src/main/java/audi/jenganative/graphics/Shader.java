package audi.jenganative.graphics;

/**
 * Created by audi on 30-7-2018.
 */
import android.opengl.GLES30;
import android.renderscript.Matrix4f;

import audi.jenganative.graphics.shaderProperties.Matrix4Property;
import audi.jenganative.graphics.shaderProperties.Vector4Property;

public class Shader {
    protected int program;
    protected String message = "";

    public Shader(String vertexShaderCode, String fragmentShaderCode){
        int vertexShader = createVertexShader(vertexShaderCode);
        int fragmentShader = createFragmentShader(fragmentShaderCode);

        //create program
        program = GLES30.glCreateProgram();
        GLES30.glAttachShader(program, vertexShader);
        GLES30.glAttachShader(program, fragmentShader);
        GLES30.glLinkProgram(program);

        int linked[] = new int[1];
        GLES30.glGetProgramiv(program, GLES30.GL_LINK_STATUS, linked, 0);
        if(linked[0] == 0){
            message += "[Shader Program Link Error]\nmsg: \n" + GLES30.glGetProgramInfoLog(program) + "\n";
            delete();
        }

        GLES30.glValidateProgram(program);

        int isValid[] = new int[1];
        GLES30.glGetProgramiv(program, GLES30.GL_VALIDATE_STATUS, isValid, 0);
        if(isValid[0] == 0){
            message += "[Shader Program Validation Error]\nmsg: \n" + GLES30.glGetProgramInfoLog(program) + "\n";
            delete();
        }

        //delete shaders
        GLES30.glDeleteShader(vertexShader);
        GLES30.glDeleteShader(fragmentShader);
        //--
    }

    public void delete(){
        GLES30.glDeleteProgram(program);
    }

    private int createShader(int type, String shaderCode){
        int shader = GLES30.glCreateShader(type);
        GLES30.glShaderSource(shader, shaderCode);
        GLES30.glCompileShader(shader);

        int compiled[] = new int[1];
        GLES30.glGetShaderiv(shader, GLES30.GL_COMPILE_STATUS, compiled, 0);
        if(compiled[0] == 0){
            String typeString = (type == GLES30.GL_VERTEX_SHADER)? "Vertex" : "Fragment";
            message += "[Shader Compile Error]\nshader type: " + typeString + "Shader\nmsg: \n" + GLES30.glGetShaderInfoLog(shader) + "\n";
        }

        return shader;
    }

    private int createVertexShader(String shaderCode){
        return createShader(GLES30.GL_VERTEX_SHADER, shaderCode);
    }

    private int createFragmentShader(String shaderCode){
        return createShader(GLES30.GL_FRAGMENT_SHADER, shaderCode);
    }

    public void bind(){
        GLES30.glUseProgram(program);
    }

    public void unbind(){
        GLES30.glUseProgram(0);
    }

    public String getMessage(){
        if(message == null || message.isEmpty()){
            return null;
        }
        return message;
    }
}
