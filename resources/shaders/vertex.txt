#version 110

attribute vec3 position;
attribute vec4 color;
attribute vec2 uv;

varying vec3 ourColor;
varying vec2 ourUV;

uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

uniform vec3 skyColor;
uniform float brightness;

void main()
{
    vec4 rPos = viewMatrix * modelMatrix * vec4(position.xyz, 1.0);
    gl_Position = projectionMatrix * rPos;

    vec3 colorLight = color.rgb;
    vec3 skyLight = skyColor.rgb * color.a;
    ourColor =  max(colorLight, skyLight);
    ourColor = vec3(brightness) + ourColor * (1.0 - brightness);

    ourUV = uv;
}