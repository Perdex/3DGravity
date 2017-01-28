#version 120

attribute vec3 vertices;
attribute vec2 textures;

varying float lightIntensity;
varying vec3 Position;
varying vec2 Texture;


uniform vec3 LightPosition;

uniform mat4 projection;

void main(){

    Position = vertices;
    Texture = textures;


    /*const float specularContribution = 0.2;
    const float diffuseContribution = (1.0 - specularContribution);
*/
    vec3 tnorm = normalize(vertices);
    vec3 lightVec = normalize(LightPosition);
    /*vec3 reflectVec = reflect(lightVec, tnorm);
    vec3 viewVec = normalize(vertices);

    float spec = clamp(dot(reflectVec, viewVec), 0.0, 1.0);
    spec = spec * spec;
    spec = spec * spec;
    spec = spec * spec;
    spec = spec * spec;
*/
    lightIntensity = max(dot(lightVec, tnorm) + 0.2, 0) + 0.02;//abs(diffuseContribution * dot(lightVec, tnorm) +
    //specularContribution * spec) + 0.5;


    gl_Position = projection * vec4(vertices, 1);
}