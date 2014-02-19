/**
 * 
 */
package AGFX.F3D.FrameBufferObject;

import java.util.ArrayList;

import AGFX.F3D.F3D;

/**
 * @author AndyGFX
 * 
 */
public class TF3D_FBO_manager
{
	public ArrayList<TF3D_FrameBufferObject> items;

	public TF3D_FBO_manager()
	{

		F3D.Log.info("TF3D_FBO_manager", "TF3D_FBO_manager: constructor");
		this.items = new ArrayList<TF3D_FrameBufferObject>();
		F3D.Log.info("TF3D_FBO_manager", "TF3D_FBO_manager: ... done");
	}

	// -----------------------------------------------------------------------
	// TF3D_FBO_manager:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Add FBO to list <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param fbo
	 *            - FrameBufferObject
	 * @return
	 */
	// -----------------------------------------------------------------------
	public int Add(TF3D_FrameBufferObject fbo)
	{
		if (this.Exist(fbo.name))
		{
			F3D.Log.info("TF3D_FBO_manager", "TF3D_FBO_manager: Add() '" + fbo.name + "' wasn't added - exist !");
			return this.FindByName(fbo.name);

		} else
		{
			int res = this.items.size();
			this.items.add(fbo);
			F3D.Log.info("TF3D_FBO_manager", "TF3D_FBO_manager: Add() '" + fbo.name + "'");
			return res;
		}
	}

	
	
	// -----------------------------------------------------------------------
	// TF3D_FBO_manager:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * add and Create FBO defined by <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param _name
	 *            - Frame buffer object name
	 * @param width
	 *            - width
	 * @param height
	 *            - height
	 * @return
	 */
	// -----------------------------------------------------------------------
	public int Add(String _name, int width, int height, Boolean depth, int count)
	{
		if (this.Exist(_name))
		{
			F3D.Log.info("TF3D_FBO_manager", "TF3D_FBO_manager: Add() '" + _name + "' wasn't added - exist !");
			return this.FindByName(_name);

		} else
		{
			int res = this.items.size();
			TF3D_FrameBufferObject fbo = new TF3D_FrameBufferObject(_name, width, height,depth,count);
			this.items.add(fbo);
			F3D.Log.info("TF3D_FBO_manager", "TF3D_FBO_manager: Add() '" + fbo.name + "'");
			return res;
		}
	}

	// -----------------------------------------------------------------------
	// TF3D_FBO_manager:
	// -----------------------------------------------------------------------
	/**
	 * -------------------------------------------------------------------<BR>
	 * get FBO ID from FBO manager manager <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param _name
	 *            fbo name
	 * @return ID
	 */
	// -----------------------------------------------------------------------
	public int FindByName(String _name)
	{
		int res = -1;

		for (int i = 0; i < this.items.size(); i++)
		{
			if (this.items.get(i).name.equalsIgnoreCase(_name))
			{
				res = i;
			}
		}

		return res;
	}

	// -----------------------------------------------------------------------
	// TF3D_FBO_manager:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * check if fbo exist in list <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param name
	 *            fbo name
	 * @return return true when texture exist in list
	 */
	// -----------------------------------------------------------------------
	public Boolean Exist(String name)
	{
		Boolean res = false;

		int f = this.FindByName(name);

		if (f == -1)
		{
			res = false;
		} else
		{
			res = true;
		}

		return res;
	}

	public TF3D_FrameBufferObject Get(String name)
	{
		return this.items.get(this.FindByName(name));
	}
	
	public void BeginRender(String name)
	{
		int id = this.FindByName(name);
		this.items.get(id).BeginRender();
	}
	
	public void BeginRender(int id)
	{		
		this.items.get(id).BeginRender();
	}
	
	
	public void EndRender(String name,Boolean frame_off, Boolean render_off)
	{
		int id = this.FindByName(name);
		this.items.get(id).EndRender(frame_off, render_off);
	}
	
	public void EndRender(int id,Boolean frame_off, Boolean render_off)
	{		
		this.items.get(id).EndRender(frame_off, render_off);
	}
	
	public TF3D_FrameBufferObject Get(int id)
	{
		return this.items.get(id);
	}
	
	// -----------------------------------------------------------------------
	// TF3D_FBO_manager:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Destroy FBO manager <BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	public void Destroy()
	{
		for (int m = 0; m < this.items.size(); m++)
		{
			this.items.remove(m);
		}
	}
}
