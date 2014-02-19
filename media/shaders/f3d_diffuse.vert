
varying vec4 v_light_diffuse;
varying vec4 v_light_ambient;

void main()
{
	
	gl_TexCoord[0]=gl_TextureMatrix[0]* gl_MultiTexCoord0;
	vec3 normal =gl_NormalMatrix *  gl_Normal;
	
	vec4 LightPosition =  gl_LightSource[0].position;	
	vec4 VertexPosition = gl_Vertex;
		
	vec4 lightvector = normalize(LightPosition - VertexPosition);
	
	float NxDir = max(0.0,dot(normal.xyz,lightvector.xyz));
	
	v_light_diffuse =(gl_LightSource[0].diffuse * NxDir);
	v_light_ambient = gl_LightSource[0].ambient;
	
	gl_Position = ftransform();
}
