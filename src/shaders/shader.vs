#version 120

attribute vec3 vertices;
attribute vec2 textures;

varying float lightIntensity;
varying vec3 Position;
varying vec3 Texture;


uniform vec3 LightPosition;

uniform mat4 projection;

void main(){

    Position = vertices;
    Texture = vec3(textures, 0);

    const float specularContribution = 0.2;
    const float diffuseContribution = (1.0 - specularContribution);

    vec3 tnorm = normalize(gl_NormalMatrix * gl_Normal);
    vec3 lightVec = normalize(LightPosition - Position);
    vec3 reflectVec = reflect(lightVec, tnorm);
    vec3 viewVec = normalize(Position);

    float spec = clamp(dot(reflectVec, viewVec), 0.0, 1.0);
    spec = spec * spec;
    spec = spec * spec;
    spec = spec * spec;
    spec = spec * spec;

    lightIntensity = abs(diffuseContribution * dot(lightVec, tnorm) +
    specularContribution * spec) + 0.1;

    gl_Position = projection * vec4(vertices, 1);
}