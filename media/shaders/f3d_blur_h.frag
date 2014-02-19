uniform sampler2D sceneTex; // 0

uniform float rt_w; // render target width


float offset[3];
float weight[3];

void main()
{

 offset[0] = 0.0;
 offset[1] = 1.3846153846 ;
 offset[2] = 3.2307692308 ;
 
 weight[0] = 0.2270270270;
 weight[1] =  0.3162162162;
 weight[2] = 0.0702702703;
 

  vec3 tc = vec3(1.0, 0.0, 0.0);

	vec2 uv = gl_TexCoord[0].xy;
    tc = texture2D(sceneTex, uv).rgb * weight[0];
    for (int i=1; i<3; i++)
    {
      tc += texture2D(sceneTex, uv + vec2(offset[i])/rt_w, 0.0).rgb * weight[i];
      tc += texture2D(sceneTex, uv - vec2(offset[i])/rt_w, 0.0).rgb * weight[i];
    }
  
  gl_FragColor = vec4(tc, 1.0);
}