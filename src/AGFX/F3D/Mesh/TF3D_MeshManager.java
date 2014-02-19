/**
 * 
 */
package AGFX.F3D.Mesh;

import java.util.ArrayList;

/**
 * @author AndyGFX
 *
 */
public class TF3D_MeshManager
{
	public ArrayList<TF3D_Mesh>	items;
	
	public TF3D_MeshManager()
	{
		this.items = new ArrayList<TF3D_Mesh>();
	}

	public void Add(String name)
	{
		this.Add(name, false);
	}
	
	public void Add(String name,boolean flip)
	{
		TF3D_Mesh m = new TF3D_Mesh();
		m.Load(name,flip);
		
		this.items.add(m);
		m = null;
	}
	
	public void Save(String name)
	{
		TF3D_Mesh m = new TF3D_Mesh();			
		m = this.items.get(this.FindByName(name));
		m.Save_A3DB(name);
		m = null;
	}
	
	public int FindByName(String name)
	{
		int ret = -1;

		for (int i = 0; i < this.items.size(); i++)
		{
			if (this.items.get(i).name.equalsIgnoreCase(name))
			{
				ret = i;
			}
		}
		return ret;
	}
	
	public TF3D_Mesh Get(String name)
	{		
		return this.items.get(this.FindByName(name));
	}
	
	public TF3D_Mesh Get(int ID)
	{		
		return this.items.get(ID);
	}
	
	public void Remove(TF3D_Mesh m)
	{
		this.items.remove(m);
	}
	
	public void Remove(int ID)
	{
		this.items.remove(ID);
	}
	
	public void Destroy()
	{
		for (int i = 0; i < this.items.size(); i++)
		{
			this.items.remove(i);
		}
	}
}
