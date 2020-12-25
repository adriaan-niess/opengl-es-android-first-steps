#version 300 es

layout (location = 0) in vec3 inPos;
layout (location = 1) in vec3 inColor;
layout (location = 2) in vec2 inTexCoords;
layout (location = 3) in vec3 inNormal;

uniform mat4 mvpMx;
uniform mat4 normalMx;

out vec4 color;
out vec4 normal;
out vec2 texCoords;

void main()
{
    gl_Position = mvpMx * vec4(inPos, 1.0);
    color = vec4(inColor, 1.0);
    texCoords = inTexCoords;
    normal = normalMx * vec4(inNormal, 0.0);
}
