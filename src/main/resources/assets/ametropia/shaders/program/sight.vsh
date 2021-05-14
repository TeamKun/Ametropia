#version 130
attribute vec4 Position;

uniform vec2 InSize;

out vec2 texCoord;
out vec2 oneTexel;

void main() {
    texCoord = gl_MultiTexCoord0.st;
    oneTexel = 1.0 / InSize;
    gl_Position = ftransform();
}