#version 110

uniform sampler2D sampler;


varying vec3 ourColor;
varying vec2 ourUV;


void main()
{
    vec4 tex = texture2D ( sampler, ourUV.xy );

    gl_FragColor = tex * vec4(ourColor, 1.0);
}

