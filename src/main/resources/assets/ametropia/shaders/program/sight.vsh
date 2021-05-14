#version 130
attribute vec4 Position;

uniform vec2 InSize;

out vec2 texCoord;

void main() {
    texCoord = gl_MultiTexCoord0.st;
    gl_Position = ftransform();
}