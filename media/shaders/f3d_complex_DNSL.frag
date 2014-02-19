

uniform sampler2D baseMap;
uniform sampler2D bumpMap;
uniform sampler2D glossMap;
uniform sampler2D lightMap;
uniform float glow_intesity;

varying vec2 Texcoord;
varying vec3 ViewDirection;
varying vec3 LightDirection;	

void main( void )
{
   vec3 ct,cf,c;
	vec4 texel;
	float intensity,at,af,a;

 
   // vypocitame uhol odrazu svetla
   vec3  fvLightDirection = normalize( LightDirection );
   //vec3  fvNormal         = normalize( ( texture2D( bumpMap, Texcoord ).xyz * 2.0 ) - 1.0 );
   vec3  fvNormal         =  2.0 * (texture2D(bumpMap, Texcoord ).rgb - 0.5);
   float fNDotL           = dot( fvNormal, fvLightDirection ); 
   
   // vypocitame uhola odrazu pre Specular
   vec3  fvReflection     = normalize( ( ( 4.0 * fvNormal ) * fNDotL ) - fvLightDirection ); 
   vec3  fvViewDirection  = normalize( ViewDirection );
   float fRDotV           = max( 0.0, dot( fvReflection, fvViewDirection ) );
   
   // nacitame si RGB z textur ktore farbia povrch
   vec4  fvBaseColor      = texture2D( baseMap, Texcoord );
   vec4  fvLightMapColor  = texture2D( lightMap, Texcoord );
   
   
   // zmena intezity LM
   fvLightMapColor =  fvLightMapColor + 0.25;
    
   // nacita texel z diffuse
   
   texel = texture2D(baseMap,gl_TexCoord[0].st);
		
	ct = texel.rgb;
    at = texel.a;
	
	c = cf * ct;
	a = af * at;
		
//	float coef = smoothstep(1.0,0.2,intensity);
	c += vec3(texture2D(glossMap,gl_TexCoord[0].st));

    
   // zistime si uroven odrazu svetla a prevediem na REF faktor (FIX IT)
   float gloss_color = texture2D(glossMap, gl_TexCoord[0].st).a;
   vec3 glow_color = texture2D(glossMap, gl_TexCoord[0].st).rgb;
   vec4 reflectivity = length(gloss_color) * fRDotV * gl_FrontLightProduct[0].ambient;
   
   float ref = length(reflectivity)*0.05;
			   
   // scitame farby textur a user defined surface properties
   vec4  fvTotalAmbient   = gl_FrontLightProduct[0].ambient * fvBaseColor +( vec4(c,0.5)*glow_intesity); 
   vec4  fvTotalDiffuse   = gl_FrontMaterial.diffuse * gl_FrontLightProduct[0].diffuse * fNDotL * fvBaseColor; 
   vec4  fvTotalSpecular  = gl_FrontLightProduct[0].specular * ( pow( fRDotV-ref, gl_FrontMaterial.shininess ) );
   		
   gl_FragColor = (( fvTotalAmbient*fvLightMapColor) + (fvTotalDiffuse*fvLightMapColor) + (fvTotalSpecular*fvLightMapColor));
       
}