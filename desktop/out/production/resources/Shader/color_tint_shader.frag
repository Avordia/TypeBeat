#ifdef GL_ES
precision mediump float;
#endif

uniform sampler2D u_texture;
uniform vec4 u_tint; // Color tint

varying vec2 v_texCoord0;

void main() {
    vec4 texColor = texture2D(u_texture, v_texCoord0);
    texColor *= u_tint; // Apply color tint
    gl_FragColor = texColor;
}
