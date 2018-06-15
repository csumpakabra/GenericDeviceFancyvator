precision mediump float;
uniform sampler2D u_TextureUnit;
varying vec2 v_TextureCoordinates;
varying float v_alpha;

void main()
{
    vec4 color = texture2D(u_TextureUnit, v_TextureCoordinates);
    if (color.a > v_alpha) color.a = v_alpha;
    gl_FragColor = color;
}