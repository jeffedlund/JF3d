/**
 * 
 */
package AGFX.F3D.Pivot;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;

import AGFX.F3D.F3D;
import AGFX.F3D.Entity.TF3D_Entity;

/**
 * @author AndyGFX
 * 
 */
public class TF3D_Pivot extends TF3D_Entity
{

	public TF3D_Pivot(String _name)
	{
		this.classname = F3D.CLASS_PIVOT;
		this.name = _name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see AGFX.F3D.Entity.TF3D_Entity#Render()
	 */
	@Override
	public void Render()
	{
		if (this.IsEnabled())
		{
			glPushMatrix();

			//glScalef(this.GetScale().x, this.GetScale().y, this.GetScale().z);
			glTranslatef(this.GetPosition().x, this.GetPosition().y, this.GetPosition().z);

			glRotatef(this.GetRotation().x, 1.0f, 0.0f, 0.0f);
			glRotatef(this.GetRotation().y, 0.0f, 1.0f, 0.0f);
			glRotatef(this.GetRotation().z, 0.0f, 0.0f, 1.0f);

			// render childs
			for (int i = 0; i < this.childs.size(); i++)
			{
				this.childs.get(i).Render();
			}

			//glScalef(1, 1, 1);
			glPopMatrix();
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

}
