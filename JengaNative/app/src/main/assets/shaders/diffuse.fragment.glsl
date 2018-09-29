#version 300 es
precision highp float;
in vec2 uv;
in float lightMult;
out vec4 fragColor;
in vec3 v_normal;
uniform sampler2D mainTexture;

void main() {
    vec4 color = vec4(texture(mainTexture, uv));
    vec4 ambient = vec4(0.2, 0.2, 0.2, 0.0);
    fragColor = color * lightMult + ambient;
    //fragColor = vec4(v_normal * 0.5 + 0.5, 1);
}
