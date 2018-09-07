#version 300 es
precision highp float;
in vec2 uv;
out vec4 fragColor;
in vec3 v_normal;
uniform sampler2D mainTexture;

void main() {
    vec4 color = vec4(texture(mainTexture, uv));
    fragColor = color;
}
