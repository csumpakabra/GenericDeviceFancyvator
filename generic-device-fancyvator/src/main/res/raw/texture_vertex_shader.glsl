uniform mat4 u_Matrix;
uniform float u_alpha;

attribute vec4 a_Position;
attribute vec2 a_TextureCoordinates;

varying vec2 v_TextureCoordinates;
varying float v_alpha;

void main()
{
    v_TextureCoordinates = a_TextureCoordinates;
    v_alpha = u_alpha;
    gl_Position = u_Matrix * (a_Position );
}