#version 400 core

in vec3 aPosition;
in vec2 aTextureCoords;

out vec2 fTextureCoords;

uniform mat4 uTransformationMatrix;
uniform mat4 uProjectionMatrix;
uniform mat4 uViewMatrix;

void main() {
    gl_Position = uProjectionMatrix * uViewMatrix * uTransformationMatrix * vec4(aPosition, 1.0);
    fTextureCoords = aTextureCoords;
}