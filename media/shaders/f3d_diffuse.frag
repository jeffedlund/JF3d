uniform sampler2D BaseMap;

varying vec4 v_light_diffuse;
varying vec4 v_light_ambient;

void main()
{
	vec4 tex_color = texture2D(BaseMap,gl_TexCoord[0].st);
	gl_FragColor = v_light_ambient+(v_light_diffuse * tex_color);	
	gl_FragColor.a = tex_color.a;
	
}
