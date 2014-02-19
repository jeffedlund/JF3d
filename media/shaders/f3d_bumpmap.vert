
//uniform float finvRad;

varying vec2 Texcoord;
varying vec3 ViewDirection;
varying vec3 LightDirection;

   
//attribute vec3 binormal;
//attribute vec3 tangent;
   
vec3 tangent; 
vec3 binormal;
   
void main()
{
	gl_Position = ftransform();
	
	gl_TexCoord[0] = gl_MultiTexCoord0;
	Texcoord    = gl_MultiTexCoord0.xy;

    // vypocitame tangent & binormal z vertex normal
	vec3 c1 = cross( gl_Normal, vec3(0.0, 0.0, 1.0) ); 
	vec3 c2 = cross( gl_Normal, vec3(0.0, -1.0, 0.0) ); 
	
	if( length(c1)>length(c2) )
	{
		tangent = c1; 
	}
	else
	{
		tangent = c2; 
	}
	
	tangent = normalize(tangent);
	
	binormal = cross(gl_Normal, tangent); 
	binormal = normalize(binormal);

	// vypocitame TBN na zaklade face normal
	mat3 TBN_Matrix = gl_NormalMatrix * mat3(tangent, binormal, gl_Normal);
	
	// skonvertujeme vertex do MODELVIEW matrix a prenesieme do fragmetu
	vec4 mv_Vertex = gl_ModelViewMatrix * gl_Vertex;	
	ViewDirection = vec3(-mv_Vertex) * TBN_Matrix ;	
	
	// skonvertujeme lightposition do MODELVIEW matrix a prenesieme do fragmetu
	//vec4 lightEye = gl_ModelViewMatrix *  vec4(gl_LightSource[0].position.xyz,1.0);
	
	vec3 lightEye = normalize(gl_LightSource[0].position.xyz - mv_Vertex.xyz);
	vec3 lightVec =(lightEye.xyz - mv_Vertex.xyz);				
	LightDirection = lightVec * TBN_Matrix; 
}