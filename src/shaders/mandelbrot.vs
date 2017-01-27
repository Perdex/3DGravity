#version 130

attribute vec3 vertices;
attribute vec2 textures;
varying vec3 pos;
uniform mat4 projection;

void main(void) {
    pos = vec3(textures, 0);

    gl_Position = projection * vec4(vertices, 1);
}

