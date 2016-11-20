#version 330

in vec3 outColor;
in vec2 outTexCoord;

out vec4 fragColor;

uniform sampler2D texture_sampler;
uniform vec3 colour;
uniform int textured;

void main()
{
	if ( textured == 1 )
    {
        fragColor = texture(texture_sampler, outTexCoord);
    }
    else
    {
        fragColor = vec4(colour, 1);
    }
}