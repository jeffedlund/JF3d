/**
 * 
 */
package AGFX.F3D.Shader;

import java.util.ArrayList;

import org.lwjgl.opengl.GL20;

import AGFX.F3D.F3D;

/**
 * @author AndyGFX
 * 
 */
public class TF3D_Shader
{
	public String                            name;
	public int                               id         = -1;
	public ArrayList<TF3D_ShaderUniformData> Uniforms;
	public ArrayList<TF3D_ShaderAttribData>  Attribs;
	public int                               loc_tangent;
	public Boolean                           b_tangent  = false;
	public int                               loc_binormal;
	public Boolean                           b_binormal = false;

	// -----------------------------------------------------------------------
	// TF3D_Shader:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Create Shader <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param name
	 *            - user defiend shader name
	 */
	// -----------------------------------------------------------------------
	public TF3D_Shader(String name)
	{
		this.name = name;
		this.id = -1;
		this.Uniforms = new ArrayList<TF3D_ShaderUniformData>();
		this.Attribs = new ArrayList<TF3D_ShaderAttribData>();
	}

	public void AddUniformEvent(String var_name, int _event)
	{
		TF3D_ShaderUniformData un = new TF3D_ShaderUniformData(var_name);
		un.SetAsEvent(_event);
		this.Uniforms.add(un);
	}

	public void AddUniform1i(String var_name, int _i1)
	{
		TF3D_ShaderUniformData un = new TF3D_ShaderUniformData(var_name);
		int[] ii = new int[1];
		ii[0] = _i1;
		un.SetAsInteger(ii);
		this.Uniforms.add(un);
	}

	public void AddUniform2i(String var_name, int _i1, int _i2)
	{
		TF3D_ShaderUniformData un = new TF3D_ShaderUniformData(var_name);
		int[] ii = new int[2];
		ii[0] = _i1;
		ii[1] = _i2;
		un.SetAsInteger(ii);
		this.Uniforms.add(un);
	}

	public void AddUniform3i(String var_name, int _i1, int _i2, int _i3)
	{
		TF3D_ShaderUniformData un = new TF3D_ShaderUniformData(var_name);
		int[] ii = new int[3];
		ii[0] = _i1;
		ii[1] = _i2;
		ii[2] = _i3;
		un.SetAsInteger(ii);
		this.Uniforms.add(un);
	}

	public void AddUniform4i(String var_name, int _i1, int _i2, int _i3, int _i4)
	{
		TF3D_ShaderUniformData un = new TF3D_ShaderUniformData(var_name);
		int[] ii = new int[3];
		ii[0] = _i1;
		ii[1] = _i2;
		ii[2] = _i3;
		ii[3] = _i4;
		un.SetAsInteger(ii);
		this.Uniforms.add(un);
	}

	public void AddUniform1f(String var_name, float _f1)
	{
		TF3D_ShaderUniformData un = new TF3D_ShaderUniformData(var_name);
		float[] ff = new float[1];
		ff[0] = _f1;
		un.SetAsFloat(ff);
		this.Uniforms.add(un);
	}

	public void AddUniform2f(String var_name, float _f1, float _f2)
	{
		TF3D_ShaderUniformData un = new TF3D_ShaderUniformData(var_name);
		float[] ff = new float[2];
		ff[0] = _f1;
		ff[1] = _f2;
		un.SetAsFloat(ff);
		this.Uniforms.add(un);
	}

	public void AddUniform3f(String var_name, float _f1, float _f2, float _f3)
	{
		TF3D_ShaderUniformData un = new TF3D_ShaderUniformData(var_name);
		float[] ff = new float[3];
		ff[0] = _f1;
		ff[1] = _f2;
		ff[2] = _f3;
		un.SetAsFloat(ff);
		this.Uniforms.add(un);
	}

	public void AddUniform4f(String var_name, float _f1, float _f2, float _f3, float _f4)
	{
		TF3D_ShaderUniformData un = new TF3D_ShaderUniformData(var_name);
		float[] ff = new float[4];
		ff[0] = _f1;
		ff[1] = _f2;
		ff[2] = _f3;
		ff[3] = _f4;
		un.SetAsFloat(ff);
		this.Uniforms.add(un);
	}

	public void AddAttrib1f(String var_name, float _f1)
	{
		TF3D_ShaderAttribData un = new TF3D_ShaderAttribData(var_name);
		float[] ff = new float[1];
		ff[0] = _f1;
		un.SetAsFloat(ff);
		this.Attribs.add(un);
	}

	public void AddAttrib2f(String var_name, float _f1, float _f2)
	{
		TF3D_ShaderAttribData un = new TF3D_ShaderAttribData(var_name);
		float[] ff = new float[2];
		ff[0] = _f1;
		ff[1] = _f2;
		un.SetAsFloat(ff);
		this.Attribs.add(un);
	}

	public void AddAttrib3f(String var_name, float _f1, float _f2, float _f3)
	{
		TF3D_ShaderAttribData un = new TF3D_ShaderAttribData(var_name);
		float[] ff = new float[3];
		ff[0] = _f1;
		ff[1] = _f2;
		ff[2] = _f3;
		un.SetAsFloat(ff);
		this.Attribs.add(un);
	}

	public void AddAttrib3f(String var_name, float _f1, float _f2, float _f3, float _f4)
	{
		TF3D_ShaderAttribData un = new TF3D_ShaderAttribData(var_name);
		float[] ff = new float[4];
		ff[0] = _f1;
		ff[1] = _f2;
		ff[2] = _f3;
		ff[3] = _f4;
		un.SetAsFloat(ff);
		this.Attribs.add(un);
	}

	public void SetAttribTangent(String attr_name)
	{
		this.loc_tangent =  TF3D_GLSL_Shader.GetAttribLocation(this.id, attr_name);
		if (this.loc_tangent!=-1)
		{
			this.b_tangent = true;
		}
		
	}
	
	
	public void SetAttribBinormal(String attr_name)
	{
		this.loc_binormal =  TF3D_GLSL_Shader.GetAttribLocation(this.id, attr_name);
		if (this.loc_binormal!=-1)
		{
			this.b_binormal = true;
		}
		
	}
	
	// -----------------------------------------------------------------------
	// TF3D_Shader:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Load vertex/fragmet shader file <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param vertexShaderFile
	 * @param fragmentShaderFile
	 */
	// -----------------------------------------------------------------------
	public void Load(String vertexShaderFile, String fragmentShaderFile)
	{

		this.id = TF3D_GLSL_Shader.loadShadersCode(F3D.AbstractFiles.GetFullPath(vertexShaderFile), F3D.AbstractFiles.GetFullPath(fragmentShaderFile));
	}

	public int FindUniformByName(String _name)
	{
		int res = -1;

		for (int i = 0; i < this.Uniforms.size(); i++)
		{
			if (this.Uniforms.get(i).name.equalsIgnoreCase(_name))
			{
				res = i;
			}
		}

		return res;
	}
	// -----------------------------------------------------------------------
	// TF3D_Shader:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Execute GLSL priogram <BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	public void UseProgram()
	{
		GL20.glUseProgram(this.id);

		// set uniforms
		for (int i = 0; i < this.Uniforms.size(); i++)
		{
			this.Uniforms.get(i).Set(this.id);
		}

		// set attribs
		for (int i = 0; i < this.Attribs.size(); i++)
		{
			this.Attribs.get(i).Set(this.id);
		}

	}

	public void StopProgram()
	{
		GL20.glUseProgram(0);
	}
}
