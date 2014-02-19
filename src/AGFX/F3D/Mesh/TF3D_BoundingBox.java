/**
 * 
 */
package AGFX.F3D.Mesh;

import javax.vecmath.Vector3f;

import AGFX.F3D.F3D;

/**
 * @author AndyGFX
 *
 */
public class TF3D_BoundingBox
{
	public Vector3f size = new Vector3f();
	public Vector3f center = new Vector3f();
	public Vector3f min = new Vector3f();
	public Vector3f max = new Vector3f();

	public TF3D_BoundingBox()
	{
		
	}
	
	
	public void CalcFromMesh(int mid)
	{

		int v_count = F3D.Meshes.items.get(mid).data.vertices.length / 3;

		Vector3f max = new Vector3f(-9999, -9999, -9999);
		Vector3f min = new Vector3f(9999, 9999, 9999);

		for (int i = 0; i < v_count; i++)
		{
			Vector3f v = F3D.Meshes.items.get(mid).data.GetVertexAsVector(i);

			max.x = v.x >= max.x ? v.x : max.x;
			max.y = v.y >= max.y ? v.y : max.y;
			max.z = v.z >= max.z ? v.z : max.z;

			min.x = v.x <= min.x ? v.x : min.x;
			min.y = v.y <= min.y ? v.y : min.y;
			min.z = v.z <= min.z ? v.z : min.z;

		}

		this.max.set(max);
		this.min.set(min);
		this.size.sub(max, min);
		this.center.add(max, min);
		this.center.scale(2.0f);

	}
	
	public void CalcFromMinMax(Vector3f min,Vector3f max)
	{
		this.size.set(0, 0, 0);
		this.center.set(0, 0, 0);
		this.max.set(max);
		this.min.set(min);
		this.size.sub(max, min);
		//this.center.add(max, min);
		//this.center.scale(2.0f);
	}
}
