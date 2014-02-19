// -----------------------------------------------------------------------
// A3D_Axis3f:
// -----------------------------------------------------------------------
package AGFX.F3D.Math;

import javax.vecmath.Vector3f;

public class TF3D_Axis3f
{
	public Vector3f	_forward;
	public Vector3f	_right;
	public Vector3f	_up;

	// -----------------------------------------------------------------------
	// A3D_Axis3f: constructor
	// -----------------------------------------------------------------------
	public TF3D_Axis3f()
	{
		this._forward = new Vector3f(0.0f, 0.0f, 1.0f);
		this._right = new Vector3f(1.0f, 0.0f, 0.0f);
		this._up = new Vector3f(0.0f, 1.0f, 0.0f);

	}

	// -----------------------------------------------------------------------
	// A3D_Axis3f: calc direction vectors from angles
	// -----------------------------------------------------------------------
	public void GetFromEuler(Vector3f angles)
	{
		
		this.GetFromEuler(angles.x, angles.y, angles.z);
		
	}

	// -----------------------------------------------------------------------
	// A3D_Axis3f: calc dir. vector wrom angles
	// -----------------------------------------------------------------------
	public void GetFromEuler(float _x, float _y, float _z)
	{
		
		
		TF3D_Matrix mat = new TF3D_Matrix();
		
		mat.LoadIdentity();
		
		mat.CreateRotationMatrix(new Vector3f(_x,_y,_z));
		
		this._right.x = mat.grid[0][0];
		this._right.y = mat.grid[1][0];
		this._right.z = mat.grid[2][0];
		
		this._up.x = mat.grid[0][1];
		this._up.y = mat.grid[1][1];
		this._up.z = mat.grid[2][1];
		
		this._forward.x = mat.grid[0][2];
		this._forward.y = mat.grid[1][2];
		this._forward.z = mat.grid[2][2];
		
		
	}
	
}
