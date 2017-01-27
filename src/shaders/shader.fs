#version 120

uniform sampler2D sampler;

varying vec3 Position;
varying vec2 Texture;
//varying float lightIntensity;

uniform int shaded;


void main(){
	
	if(shaded == 1){
		gl_FragColor = texture2D(sampler, Texture);
		return;
	}
	
    float pscale = 20.0;
    float r = clamp(Position.x / pscale + 0.5, 0.1, 1);
    float g = clamp(Position.y / pscale + 0.5, 0.1, 1);
    float b = clamp(Position.z / pscale + 0.5, 0.1, 1);


    float wireframeScale = 0.02;

    float k = clamp(1 / (1-Position.z/2), 0.1, 1);

    if(Texture.x < wireframeScale)
        k *= Texture.x / wireframeScale;
    if(Texture.y < wireframeScale)
        k *= Texture.y / wireframeScale;

    if(Texture.x > 1 - wireframeScale)
        k *= (1 - Texture.x) / wireframeScale;
    if(Texture.y > 1 - wireframeScale)
        k *= (1 - Texture.y) / wireframeScale;

    if(abs(Texture.x - Texture.y - 0.25) < wireframeScale)
        k *= 0.25;
    

    r *= k;
    g *= k;
    b *= k;

    if(Texture.x > 1 || Texture.y > 1){
        r = 1;
        g = 1;
        b = 1;
    }
/*
    r = clamp(r * lightIntensity, 0.0, 1.0);
    g = clamp(g * lightIntensity, 0.0, 1.0);
    b = clamp(b * lightIntensity, 0.0, 1.0);
*/


    gl_FragColor = vec4(r, g, b, 1);
}
