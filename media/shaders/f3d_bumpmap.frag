

uniform sampler2D baseMap;
uniform sampler2D bumpMap;


varying vec2 Texcoord;
varying vec3 ViewDirection;
varying vec3 LightDirection;	

void main( void )
{
   // vypocitame uhol odrazu svetla
   vec3  fvLightDirection = normalize( LightDirection );
   vec3  fvNormal         =  2.0 * (texture2D(bumpMap, Texcoord ).rgb - 0.5);
   float fNDotL           = dot( fvNormal, fvLightDirection ); 
   
   // vypocitame uhola odrazu pre Specular
   vec3  fvReflection     = normalize( ( ( 2.0 * fvNormal ) * fNDotL ) - fvLightDirection ); 
   vec3  fvViewDirection  = normalize( ViewDirection );
   float fRDotV           = max( 0.0, dot( fvReflection, fvViewDirection ) );
   
   // nacitame si RGB z textur ktore farbia povrch
   vec4  fvBaseColor      = texture2D( baseMap, Texcoord );
   		   
   // scitame farby textur a user defined surafce properties
   vec4  fvTotalAmbient   = gl_FrontMaterial.ambient * gl_FrontLightProduct[0].ambient * fvBaseColor; 
   vec4  fvTotalDiffuse   = gl_FrontMaterial.diffuse * gl_FrontLightProduct[0].diffuse * fNDotL * fvBaseColor; 
   vec4  fvTotalSpecular  = gl_FrontMaterial.specular * gl_FrontLightProduct[0].specular * ( pow( fRDotV, gl_FrontMaterial.shininess ) ) ;
   		
   gl_FragColor =  fvTotalAmbient + fvTotalDiffuse + fvTotalSpecular;
       
}