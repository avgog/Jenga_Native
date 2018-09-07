#version 300 es

layout(location = 0) in vec3 position;
uniform mat4x4 mvp;

void main() {
    gl_Position = mvp * vec4(position.xyz, 1.0f);
}