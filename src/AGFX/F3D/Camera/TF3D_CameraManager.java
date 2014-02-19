package AGFX.F3D.Camera;

import java.util.ArrayList;

import javax.vecmath.Vector3f;

import AGFX.F3D.*;

public class TF3D_CameraManager
{
	public ArrayList<TF3D_Camera> items;
	public int                    CurrentCameraID;	
	
	// -----------------------------------------------------------------------
	// TA3D_CameraManager:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Constructor <BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	public TF3D_CameraManager()
	{
		F3D.Log.info("TF3D_CameraManager", "Create - constructor");
		this.items = new ArrayList<TF3D_Camera>();
		F3D.Log.info("TF3D_CameraManager", "Done");
		this.CurrentCameraID = -1;
	}

	// -----------------------------------------------------------------------
	// TA3D_CameraManager:
	// -----------------------------------------------------------------------
	/**
	 * -------------------------------------------------------------------<BR>
	 * Add camera to List<BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param _cam
	 *            Camera
	 */
	// -----------------------------------------------------------------------
	public void Add(TF3D_Camera _cam)
	{
		this.CurrentCameraID = this.items.size();
		this.items.add(_cam);
		F3D.Log.info("TF3D_CameraManager", ": Add() '" + _cam.name + "'");

	}

	// -----------------------------------------------------------------------
	// TA3D_CameraManager:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Find camera by name <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param _name
	 *            searching name
	 * @return camera ID
	 */
	// -----------------------------------------------------------------------
	public int FindByName(String _name)
	{
		int res = -1;
		for (int i = 0; i < this.items.size(); i++)
		{
			if (this.items.get(i).name == _name)
			{
				res = i;
			}
		}

		return res;
	}

	// -----------------------------------------------------------------------
	// TA3D_CameraManager:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Get current used camera <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @return return actual camera
	 */
	// -----------------------------------------------------------------------
	public TF3D_Camera GetCurrentCamera()
	{
		return this.items.get(this.CurrentCameraID);
	}

	// -----------------------------------------------------------------------
	// TA3D_CameraManager:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Activate camera as current <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param id
	 *            camera ID from List
	 */
	// -----------------------------------------------------------------------
	public void Activate(int id)
	{
		this.CurrentCameraID = id;
	}

	// -----------------------------------------------------------------------
	// TA3D_CameraManager:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Update camera<BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	public void Update()
	{
		if (this.CurrentCameraID >= 0)
		{
			
			this.items.get(this.CurrentCameraID).Update();
			
		}

	}

	// -----------------------------------------------------------------------
	// TA3D_CameraManager:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Set camera position X,Y,Z <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param id
	 *            camera ID
	 * @param _x
	 *            X position
	 * @param _y
	 *            Y position
	 * @param _z
	 *            Z position
	 */
	// -----------------------------------------------------------------------
	public void SetPosition(int id, float _x, float _y, float _z)
	{
		this.items.get(id).SetPosition(_x, _y, _z);
	}

	// -----------------------------------------------------------------------
	// TA3D_CameraManager:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Set camera position [vector] <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param id
	 *            camera ID
	 * @param _v
	 *            vector position
	 */
	// -----------------------------------------------------------------------
	public void SetPosition(int id, Vector3f _v)
	{
		this.items.get(id).SetPosition(_v);

	}

	// -----------------------------------------------------------------------
	// TA3D_CameraManager:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Set camera rotation [x,y,x] <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param id
	 *            camera ID
	 * @param _x
	 *            angle in X axis
	 * @param _y
	 *            angle in Y axis
	 * @param _z
	 *            angle in Z axis
	 */
	// -----------------------------------------------------------------------
	public void SetRotation(int id, float _x, float _y, float _z)
	{
		this.items.get(id).SetRotation(_x, _y, _z);

	}

	// -----------------------------------------------------------------------
	// TA3D_CameraManager:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Set camera rotation [vector] <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param id
	 *            camera ID
	 * @param _v
	 *            - angles as vetor
	 */
	// -----------------------------------------------------------------------
	public void SetRotation(int id, Vector3f _v)
	{
		this.items.get(id).SetRotation(_v);

	}

	public void Destroy()
	{
		for (int m = 0; m < this.items.size(); m++)
		{
			this.items.remove(m);
		}
	}

	public Vector3f GetPosition()
	{
		return this.items.get(this.CurrentCameraID).GetPosition();
	}

	public Vector3f GetTargetPosition()
	{
		Vector3f pos = new Vector3f();
		Vector3f dir = new Vector3f();

		pos = (Vector3f) this.items.get(this.CurrentCameraID).GetPosition().clone();
		dir = this.items.get(this.CurrentCameraID).axis._forward;
		dir.scale(1000.0f);
		pos.add(dir);

		return pos;
	}
	
	public TF3D_Camera GetCamera(String name)
	{
		return this.items.get(this.FindByName(name));
	}
}
