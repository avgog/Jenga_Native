#version 300 es

layout(location = 0) in vec4 position;
layout(location = 1) in vec2 texture_uv;
layout(location = 2) in vec3 normal;
uniform mat4x4 mvp;
out vec2 uv;
out vec3 v_normal;

void main() {
    uv = texture_uv;
    v_normal = normal;
    gl_Position = mvp * vec4(position.xyz, 1.0f);
}
