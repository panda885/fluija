#version 330 core

out vec4 FragColor;

in vec2 uv;

uniform vec3 color;
uniform float radius;

void main()
{
    float alpha = 1.0f;
    float dst = distance(uv * radius * 2.0f, vec2(radius, radius));
    if (dst > radius) {
        discard;
    } else if (dst > radius - 1) {
        alpha = radius - dst;
    }
    FragColor = vec4(color, alpha);
}
