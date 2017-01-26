#version 130

varying vec3 pos;

void main(void) {
    pos = vec3(gl_Vertex);

    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
}

