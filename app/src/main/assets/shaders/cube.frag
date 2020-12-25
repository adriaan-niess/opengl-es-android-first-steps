#version 300 es

precision mediump float;

uniform sampler2D tex;

in vec4 color;
in vec4 normal;
in vec2 texCoords;

out vec4 fragColor;

void main()
{
    fragColor = texture(tex, texCoords);
}
