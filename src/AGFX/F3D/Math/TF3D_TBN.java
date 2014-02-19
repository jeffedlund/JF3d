/**
 * 
 */
package AGFX.F3D.Math;

import javax.vecmath.Vector3f;

/**
 * @author AndyGFX
 * 
 */
public class TF3D_TBN
{
	public Vector3f tangent;
	public Vector3f binormal;
	public Vector3f normal;

	public TF3D_TBN()
	{
		this.tangent = new Vector3f(1, 0, 0);
		this.binormal = new Vector3f(0, 1, 0);
		this.normal = new Vector3f(0, 0, 1);

	}

}
