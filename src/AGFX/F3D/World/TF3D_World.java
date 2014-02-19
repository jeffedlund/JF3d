/**
 * 
 */
package AGFX.F3D.World;

import java.util.ArrayList;
import AGFX.F3D.F3D;
import AGFX.F3D.Entity.TF3D_Entity;

/**
 * @author AndyGFX
 * 
 */
public class TF3D_World
{
	public String                 name;

	public ArrayList<TF3D_Entity> entities;

	// -----------------------------------------------------------------------
	// TF3D_World:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * COnstructor <BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	public TF3D_World(String world_name)
	{
		F3D.Log.info("TF3D_World", "TF3D_World: constructor");
		this.entities = new ArrayList<TF3D_Entity>();
		F3D.Log.info("TF3D_World", "TF3D_World: ... done");
		this.name = world_name;
	}

	// -----------------------------------------------------------------------
	// TF3D_World:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Update world. Call Update in all entities before Render <BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	public void Update()
	{
		for (int i = 0; i < this.entities.size(); i++)
		{
			if (this.entities.get(i).parent == null)
			{
				switch (this.entities.get(i).classname)
				{
				case F3D.CLASS_BODY:
					this.entities.get(i).Update();
					break;
				case F3D.CLASS_CAMERA:
					// nothing
					break;
				case F3D.CLASS_LIGHT:
					this.entities.get(i).Update();
					break;
				case F3D.CLASS_MESH:
					this.entities.get(i).Update();
					break;
				case F3D.CLASS_MODEL:
					this.entities.get(i).Update();
					break;
				case F3D.CLASS_PIVOT:
					this.entities.get(i).Update();
					break;
				case F3D.CLASS_SPRITE:
					this.entities.get(i).Update();
					break;
				case F3D.CLASS_VEHICLE:
					this.entities.get(i).Update();
				case F3D.CLASS_PARTICLE_EMITTER:
					this.entities.get(i).Update();
				case F3D.CLASS_PARTICLE_SPRITE:
					this.entities.get(i).Update();
					break;
				}

			}
		}

	}

	// -----------------------------------------------------------------------
	// TF3D_World:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Render all entities <BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	public void Render()
	{

		// TODO REWRITE SKYBOX RENDERING !!!
		// F3D.Cameras.RenderSky();

		for (int i = 0; i < this.entities.size(); i++)
		{
			if (this.entities.get(i).parent == null)
			{
				switch (this.entities.get(i).classname)
				{
				case F3D.CLASS_BODY:
					this.entities.get(i).Render();
					break;
				case F3D.CLASS_CAMERA:
					// nothing
					break;
				case F3D.CLASS_LIGHT:
					this.entities.get(i).Render();
					break;
				case F3D.CLASS_MESH:
					this.entities.get(i).Render();
					break;
				case F3D.CLASS_MODEL:
					this.entities.get(i).Render();
					break;
				case F3D.CLASS_PIVOT:
					this.entities.get(i).Render();
					break;
				case F3D.CLASS_SPRITE:
					this.entities.get(i).Render();
					break;
				case F3D.CLASS_VEHICLE:
					this.entities.get(i).Render();
				case F3D.CLASS_PARTICLE_EMITTER:
					this.entities.get(i).Render();
				case F3D.CLASS_PARTICLE_SPRITE:
					this.entities.get(i).Render();
					break;
				}
			}
		}

	}

	// -----------------------------------------------------------------------
	// TF3D_World:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Add Entity to world <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param e
	 *            = entity
	 */
	// -----------------------------------------------------------------------
	public void Add(TF3D_Entity e)
	{
		this.entities.add(e);
	}

	// -----------------------------------------------------------------------
	// TF3D_World:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Remove entity from world by object<BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param e
	 *            = entity
	 */
	// -----------------------------------------------------------------------
	public void RemoveEntity(TF3D_Entity e)
	{
		if (e.childs.size() > 0)
		{
			e.ClearChild();
		}
		this.entities.remove(e);
	}

	// -----------------------------------------------------------------------
	// TF3D_World:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Remove entity from world by name <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param name
	 *            - entity name
	 */
	// -----------------------------------------------------------------------
	public void RemoveEntiy(String name)
	{
		int id = this.FindEntityByName(name);
		if (id >= 0)
		{
			this.entities.remove(id);
			F3D.Log.info("TF3D_World", "entity '" + name + "' removed");
		}
	}

	// -----------------------------------------------------------------------
	// TF3D_World:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Remove entity from world by ID <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param id
	 *            - entity ID
	 */
	// -----------------------------------------------------------------------
	public void RemoveEntiy(int id)
	{

		if (id >= 0)
		{
			this.entities.remove(id);
			F3D.Log.info("TF3D_World", "entity '" + name + "' removed");
		}
	}

	// -----------------------------------------------------------------------
	// TF3D_World:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * FInd entity ID by name <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param entity_name
	 *            - entity name
	 * @return
	 */
	// -----------------------------------------------------------------------
	public int FindEntityByName(String entity_name)
	{
		for (int i = 0; i < this.entities.size(); i++)
		{
			if (this.entities.get(i).name.equals(entity_name))
			{
				return i;
			}

		}

		return -1;
	}

	// -----------------------------------------------------------------------
	// TF3D_World:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Destroy entities list and clear list <BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	public void Destroy()
	{
		for (int i = 0; i < this.entities.size(); i++)
		{
			this.entities.get(i).Destroy();
		}

		this.entities.clear();
	}
}
