package AGFX.F3D.Shader;

import javax.vecmath.Vector3f;

import AGFX.F3D.F3D;

public class TF3D_ShaderUniformData
{

	public String  name;
	public int     type = -1;
	public int[]   i;
	public float[] f;
	public int     e;

	public TF3D_ShaderUniformData(String name)
	{
		this.name = name;
	}

	public void SetAsInteger(int[] _i)
	{
		this.type = F3D.SHADER_UNIFORM_INT;
		this.i = new int[_i.length];
		this.i = _i;
	}

	public void SetAsFloat(float[] _f)
	{
		this.type = F3D.SHADER_UNIFORM_FLOAT;
		this.f = new float[_f.length];
		this.f = _f;
	}

	public void SetAsEvent(int _event)
	{
		this.type = F3D.SHADER_UNIFORM_EVENT;
		this.e = _event;
	}

	public void Set(int shd_id)
	{
		// INTEGER UNIFORMS
		if (this.type == F3D.SHADER_UNIFORM_INT)
		{
			if (this.i.length == 1)
				TF3D_GLSL_Shader.sendUniform1i(shd_id, this.name, this.i[0]);
			if (this.i.length == 2)
				TF3D_GLSL_Shader.sendUniform2i(shd_id, this.name, this.i[0], this.i[1]);
			if (this.i.length == 3)
				TF3D_GLSL_Shader.sendUniform3i(shd_id, this.name, this.i[0], this.i[1], this.i[2]);
			if (this.i.length == 4)
				TF3D_GLSL_Shader.sendUniform4i(shd_id, this.name, this.i[0], this.i[1], this.i[2], this.i[3]);
		}

		// FLOAT UNIFORMS
		if (this.type == F3D.SHADER_UNIFORM_FLOAT)
		{
			if (this.f.length == 1)
				TF3D_GLSL_Shader.sendUniform1f(shd_id, this.name, this.f[0]);
			if (this.f.length == 2)
				TF3D_GLSL_Shader.sendUniform2f(shd_id, this.name, this.f[0], this.f[1]);
			if (this.f.length == 3)
				TF3D_GLSL_Shader.sendUniform3f(shd_id, this.name, this.f[0], this.f[1], this.f[2]);
			if (this.f.length == 4)
				TF3D_GLSL_Shader.sendUniform4f(shd_id, this.name, this.f[0], this.f[1], this.f[2], this.f[3]);
		}
		
		// EVENT IUNIFORM
		if (this.type == F3D.SHADER_UNIFORM_EVENT)
		{
			if (this.e == F3D.SHADER_EVENT_TIMER)
			{
				float timer = F3D.Timer.GetTickCount()/1000f;
				TF3D_GLSL_Shader.sendUniform1f(shd_id, this.name, timer);
			}
			
			if (this.e == F3D.SHADER_EVENT_CAMERA)
			{
				Vector3f pos = F3D.Worlds.camera.GetPosition();
				TF3D_GLSL_Shader.sendUniform3f(shd_id, this.name, pos.x,pos.y,pos.z);
			}
		}
		
	}
}
