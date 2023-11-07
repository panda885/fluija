#version 330 core

out vec4 FragColor;

in vec2 uv;

uniform vec3 color;
uniform float smoothing;

void main()
{
    float alpha = 1.0f;
    float dst = distance(uv, vec2(0.5f, 0.5f));
    if (dst > 0.5f) {
        discard;
    } else if (dst > smoothing) {
        alpha = 1.0f - (dst - smoothing) * (1.0f / (0.5f - smoothing));
    }
    FragColor = vec4(color, alpha);
}
