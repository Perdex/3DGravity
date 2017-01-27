#version 130

varying vec3 pos;

uniform float zoom;

void main (void)
{

    float real = pos.x * zoom;
    float imag = pos.y * zoom;
    float Creal = real;
    float Cimag = imag;

    float r2 = 0.0;
    int iter;

    for (iter = 0; iter < 200 && r2 < 4.0; ++iter) {
        float tempreal = real;

        real = (tempreal * tempreal) - (imag * imag) + Creal;
        imag = 2.0 * tempreal * imag + Cimag;
        r2 = (real * real) + (imag * imag);
    }

    // Base the color on the number of iterations
    vec3 color;

    if (r2 < 4.0) {
        color = vec3(0.0, 0.0, 0.0);
    } else {
        float g = iter * 0.03;
        g = fract(g / 2.0) * 2.0;
        if(g > 1.0)
            g = 2.0 - g;

        g = clamp(g, 0.0, 1.0);

        float b = iter * 0.03 - 1.0;
        b = fract(b / 2.0) * 2.0;
        if(b > 1.0)
            b = 2.0 - b;
        
        b = clamp(b, 0.0, 1.0);

        color = vec3(0, g, b);
    }


    gl_FragColor = vec4(color, 1.0);
}
