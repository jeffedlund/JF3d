uniform sampler2D SceneMap;
uniform sampler2D NoiseMap;
uniform float TIMER;
uniform float Speed;

void main (void)
{
	vec3 noiseVec;
	vec2 displacement;
	float scaledTimer;

	displacement = gl_TexCoord[0].st;

	scaledTimer = Speed*TIMER;

	displacement.x += scaledTimer;
	displacement.y -= scaledTimer;

	noiseVec = normalize(texture2D(NoiseMap, displacement.xy).xyz);
	noiseVec = (noiseVec * 2.0 - 1.0) * 0.035;
	
	gl_FragColor = texture2D(SceneMap, gl_TexCoord[0].st + noiseVec.xy);
}