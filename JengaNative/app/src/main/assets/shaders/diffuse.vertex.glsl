#version 300 es

layout(location = 0) in vec4 position;
layout(location = 1) in vec2 texture_uv;
layout(location = 2) in vec3 normal;
uniform mat4x4 mvp;
uniform mat4x4 mv;
out vec2 uv;
out vec3 v_normal;
out float lightMult;

void main() {
    uv = texture_uv;
    v_normal = normal;
    gl_Position = mvp * vec4(position.xyz, 1.0f);
    vec3 worldNormal = normalize(mv * vec4(v_normal, 0)).xyz;
    lightMult = dot(worldNormal, normalize(vec3(1, 0.4, 1))) * 0.5f + 0.5f;
}
