/**
 * 
 */
package AGFX.F3D.Model;

import java.util.ArrayList;
import AGFX.F3D.F3D;
import AGFX.F3D.Entity.TF3D_Entity;
import AGFX.F3D.Mesh.TF3D_Mesh;
import AGFX.F3D.Surface.TF3D_SurfaceSubstItem;
import static org.lwjgl.opengl.GL11.*;

/**
 * @author AndyGFX
 * 
 */

public class TF3D_Model extends TF3D_Entity
{

	private int                              mesh_id = -1;
	private ArrayList<TF3D_SurfaceSubstItem> surfaces;

	public TF3D_Model(String _name)
	{
		this.classname = F3D.CLASS_MODEL;
		this.name = _name;
		this.surfaces = null;

		F3D.Log.info("TF3D_Model", "Create model '" + name + "'");
	}

	public void AssignMesh(int id)
	{
		this.mesh_id = id;
		this.BBOX.CalcFromMesh(this.mesh_id);
		F3D.Log.info("TF3D_Model", "Assigned mesh = '" + F3D.Meshes.Get(this.mesh_id).name + "'");
		this.ReadAssignedSurfaces();
	}

	public void AssignMesh(String name)
	{
		this.mesh_id = F3D.Meshes.FindByName(name);
		this.BBOX.CalcFromMesh(this.mesh_id);

		F3D.Log.info("TF3D_Model", "Assigned mesh = '" + name + "'");

		this.ReadAssignedSurfaces();

	}

	private void ReadAssignedSurfaces()
	{
		this.surfaces = new ArrayList<TF3D_SurfaceSubstItem>();

		TF3D_Mesh mesh = F3D.Meshes.items.get(this.mesh_id);

		for (int i = 0; i < mesh.IndicesGroup.items.size(); i++)
		{
			String name = mesh.IndicesGroup.items.get(i).material_name;
			int id = mesh.IndicesGroup.items.get(i).material_id;

			TF3D_SurfaceSubstItem sitem = new TF3D_SurfaceSubstItem(name, id);
			this.surfaces.add(sitem);

			F3D.Log.info("TF3D_Model", "Assigned surface = '" + name + "'");
		}
	}

	public void ChangeSurface(String old_surface, String new_surface)
	{
		Boolean done = false;
		for (int i = 0; i < this.surfaces.size(); i++)
		{
			if (this.surfaces.get(i).name.equals(old_surface))
			{
				int res = this.surfaces.get(i).ChangeTo(new_surface);
				
				if (res == -1)
				{
					done = false;
				} else
				{
					done = true;
				}
			}

		}

		if (!done)
			F3D.Log.error("TF3D_Model", ".ChangeSurface(...)" + "Material name: '" + new_surface + "' doesn't exist");
	}

	public void SetRenderSurface(String sname, Boolean state)
	{
		for (int i = 0; i < this.surfaces.size(); i++)
		{
			if (this.surfaces.get(i).name.equals(sname))
			{
				this.surfaces.get(i).SetEnable(state);
			}
		}
	}

	public void Render()
	{
		int mid;

		if (this.IsEnabled())
		{
			if (this.IsVisible())
			{
				TF3D_Mesh mesh = F3D.Meshes.items.get(this.mesh_id);

				mesh.vbo.Bind();

				for (int i = 0; i < this.surfaces.size(); i++)
				{
					if (this.surfaces.get(i).isEnabled())
					{
						mid = this.surfaces.get(i).id;

						if (mid >= 0)
						{
							F3D.Surfaces.ApplyMaterial(mid);
							int shd_id = F3D.Surfaces.materials.get(mid).shader_id;
							// TODO add bind TBN when is in shader
							if (shd_id >= 0)
							{

								if (F3D.Shaders.items.get(shd_id).Attribs.size() > 0)
								{
									mesh.vbo.Bind_TBN_Attributs();
								}
							}
						}

						glPushMatrix();

						glTranslatef(this.GetPosition().x, this.GetPosition().y, this.GetPosition().z);

						if (this.rotation_seq == F3D.ROTATE_IN_SEQ_XYZ)
						{
							glRotatef(this.GetRotation().x, 1.0f, 0.0f, 0.0f);
							glRotatef(this.GetRotation().y, 0.0f, 1.0f, 0.0f);
							glRotatef(this.GetRotation().z, 0.0f, 0.0f, 1.0f);
						}

						if (this.rotation_seq == F3D.ROTATE_IN_SEQ_YXZ)
						{
							glRotatef(this.GetRotation().y, 0.0f, 1.0f, 0.0f);
							glRotatef(this.GetRotation().x, 1.0f, 0.0f, 0.0f);
							glRotatef(this.GetRotation().z, 0.0f, 0.0f, 1.0f);
						}

						glScalef(this.GetScale().x, this.GetScale().y, this.GetScale().z);

						mesh.Render(i);

						if (F3D.Surfaces.materials.get(mid).use_shader)
							F3D.Shaders.StopProgram();

						// render childs
						for (int ci = 0; ci < this.childs.size(); ci++)
						{
							this.childs.get(ci).Render();
						}
						glScalef(1, 1, 1);

						glPopMatrix();

					}
				}

				mesh.vbo.UnBind();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see AGFX.F3D.Entity.TF3D_Entity#Update()
	 */
	@Override
	public void Update()
	{

	}

	@Override
	public void Destroy()
	{

	}

	public void DrawAxis()
	{

	}

}
