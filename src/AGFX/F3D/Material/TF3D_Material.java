/**
 * 
 */
package AGFX.F3D.Material;

import AGFX.F3D.F3D;

/**
 * @author AndyGFX
 * 
 */
public class TF3D_Material
{

	public String					name			= "none";
	public int						typ				= F3D.MAT_TYPE_TEXTURE;
	// public Vector4f color = new Vector4f(1, 1, 1, 1);

	public TF3D_MaterialColor		colors			= new TF3D_MaterialColor();

	// front material color definition
	// public float diffuse[] = new float[] { 1.0f, 1.0f, 1.0f, 1.0f };

	public TF3D_MaterialTextureUnit	texture_unit[]	= new TF3D_MaterialTextureUnit[4];

	public Boolean					bAlphaTest		= false;
	public Boolean					bDepthTest		= false;
	public Boolean					bFaceCulling	= false;

	public String					shader_name		= "none";
	public int						shader_id		= -1;
	public Boolean					use_shader		= false;

	// -----------------------------------------------------------------------
	// TA3D_Material:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * constructor <BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	public TF3D_Material()
	{
		F3D.Log.info("TF3D_Material", "Create - constructor");

		this.texture_unit[0] = new TF3D_MaterialTextureUnit();
		this.texture_unit[1] = new TF3D_MaterialTextureUnit();
		this.texture_unit[2] = new TF3D_MaterialTextureUnit();
		this.texture_unit[3] = new TF3D_MaterialTextureUnit();
	}

	// -----------------------------------------------------------------------
	// TA3D_Material:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Create clone of material <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @return material
	 */
	// -----------------------------------------------------------------------
	public TF3D_Material Clone()
	{
		TF3D_Material mat = new TF3D_Material();

		mat.bAlphaTest = this.bAlphaTest;
		mat.bDepthTest = this.bDepthTest;
		mat.bFaceCulling = this.bFaceCulling;

		mat.colors.ambient = this.colors.ambient.clone();
		mat.colors.diffuse = this.colors.diffuse.clone();
		mat.colors.emissive = this.colors.emissive.clone();
		mat.colors.shinisess = this.colors.shinisess;
		mat.colors.specular = this.colors.specular.clone();

		mat.name = this.name;

		mat.shader_id = this.shader_id;
		mat.shader_name = this.shader_name;

		for (int i = 0; i < 4; i++)
		{

			mat.texture_unit[i] = new TF3D_MaterialTextureUnit();
			mat.texture_unit[i].bEvent = this.texture_unit[i].bEvent;
			mat.texture_unit[i].bTexture = this.texture_unit[i].bTexture;
			mat.texture_unit[i].event_id = this.texture_unit[i].event_id;
			mat.texture_unit[i].event_name = this.texture_unit[i].event_name;
			mat.texture_unit[i].texture_id = this.texture_unit[i].texture_id;
			mat.texture_unit[i].texture_name = this.texture_unit[i].texture_name;
		}

		mat.typ = this.typ;
		mat.use_shader = this.use_shader;
		
		return mat;

	}

}
