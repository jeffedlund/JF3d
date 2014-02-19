package AGFX.F3D.Shader;

import javax.vecmath.Vector3f;

import AGFX.F3D.F3D;

public class TF3D_ShaderAttribData
{

	public String  name;
	public int     type = -1;
	public int[]   i;
	public float[] f;
	public int     e;

	public TF3D_ShaderAttribData(String name)
	{
		this.name = name;
	}


	public void SetAsFloat(float[] _f)
	{
		this.type = F3D.SHADER_ATTRIB_FLOAT;
		this.f = new float[_f.length];
		this.f = _f;
	}


	public void Set(int shd_id)
	{
		// FLOAT UNIFORMS
		if (this.type == F3D.SHADER_ATTRIB_FLOAT)
		{
			if (this.f.length == 1)
				TF3D_GLSL_Shader.sendAttrib1f(shd_id, this.name, this.f[0]);
			if (this.f.length == 2)
				TF3D_GLSL_Shader.sendAttrib2f(shd_id, this.name, this.f[0], this.f[1]);
			if (this.f.length == 3)
				TF3D_GLSL_Shader.sendAttrib3f(shd_id, this.name, this.f[0], this.f[1], this.f[2]);
			if (this.f.length == 4)
				TF3D_GLSL_Shader.sendAttrib4f(shd_id, this.name, this.f[0], this.f[1], this.f[2], this.f[3]);
		}
		
	}
}
