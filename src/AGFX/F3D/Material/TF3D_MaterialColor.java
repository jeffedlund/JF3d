/**
 * 
 */
package AGFX.F3D.Material;

import javax.vecmath.Vector4f;

/**
 * @author AndyGFX
 * 
 */
public class TF3D_MaterialColor
{
	public float diffuse[] = new float[] { 1.0f, 1.0f, 1.0f, 1.0f };
	public float ambient[] = new float[] { 0.1f, 0.1f, 0.1f, 1.0f };
	public float emissive[] = new float[] { 0.0f, 0.0f, 0.0f, 1.0f };
	public float specular[] = new float[] { 0.0f, 0.0f, 0.0f, 1.0f };
	public float shinisess = 0f;

	public TF3D_MaterialColor()
	{
		
	}
	
	public void SetDiffuse(Vector4f color)
	{
		this.diffuse[0] = color.x;
		this.diffuse[1] = color.y;
		this.diffuse[2] = color.z;
		this.diffuse[3] = color.w;
	}
	
	public void SetAmbient(Vector4f color)
	{
		this.ambient[0] = color.x;
		this.ambient[1] = color.y;
		this.ambient[2] = color.z;
		this.ambient[3] = color.w;
	}
	
	public void SetEmissive(Vector4f color)
	{
		this.emissive[0] = color.x;
		this.emissive[1] = color.y;
		this.emissive[2] = color.z;
		this.emissive[3] = color.w;
	}
	
	public void SetSpecular(Vector4f color)
	{
		this.specular[0] = color.x;
		this.specular[1] = color.y;
		this.specular[2] = color.z;
		this.specular[3] = color.w;
	}
	
	public void SetShinisess(float intensity)
	{
		this.shinisess = intensity;
	}
}
