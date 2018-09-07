#version 300 es

precision highp float;
out vec4 fragColor;
uniform vec4 mainColor;

void main() {
	fragColor = mainColor;
}