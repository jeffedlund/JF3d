// -----------------------------------------------------------------------
// A3D_Camera:
// -----------------------------------------------------------------------
package AGFX.F3D.Camera;

import org.lwjgl.util.glu.GLU;
import javax.vecmath.Vector3f;

import AGFX.F3D.F3D;
import AGFX.F3D.Entity.TF3D_Entity;
import AGFX.F3D.Skybox.TF3D_Skybox;

import static org.lwjgl.opengl.GL11.*;

public class TF3D_Camera extends TF3D_Entity
{

	public Vector3f		TargetPoint;
	public int			cmode;
	public int			ctype;
	public TF3D_Skybox	Sky;

	// -----------------------------------------------------------------------
	// A3D_Camera: constructor
	// -----------------------------------------------------------------------
	// -----------------------------------------------------------------------
	// TA3D_Camera:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Constructor <BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	public TF3D_Camera(String _name)
	{
		F3D.Log.info("TF3D_Camera", "Create - constructor");
		this.classname = F3D.CLASS_CAMERA;
		this.cmode = F3D.CAMERA_MODE_FREELOOK;
		this.ctype = F3D.CAMERA_TYPE_FPS;
		this.TargetPoint = new Vector3f();
		this.name = _name;

	}

	// -----------------------------------------------------------------------
	// TA3D_Camera: look at
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Set camera view matrix to target point <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param tx
	 *            - X position
	 * @param ty
	 *            - Y position
	 * @param tz
	 *            - Z position
	 */
	// -----------------------------------------------------------------------
	public void LookAt(float tx, float ty, float tz)
	{
		Vector3f pos = new Vector3f();
		pos = this.GetPosition();

		GLU.gluLookAt(pos.x, pos.y, pos.z, tx, ty, tz, 0.0f, 1.0f, 0.0f);
	}

	// -----------------------------------------------------------------------
	// TA3D_Camera: Render
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Update [called every render loop] <BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	public void Render()
	{

	}

	// -----------------------------------------------------------------------
	// TA3D_Camera:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * create copy of Camera <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @return return camera
	 */
	// -----------------------------------------------------------------------
	public TF3D_Camera Copy()
	{
		TF3D_Camera c = new TF3D_Camera(this.name);

		try
		{
			c = (TF3D_Camera) this.clone();
		} catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
		}

		return c;
	}

	// -----------------------------------------------------------------------
	// TA3D_Camera: Update
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Update [called every render loop] <BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	@Override
	public void Update()
	{
		if (this.Sky != null)
		{
			this.Sky.Render(this.GetPosition());
		}

		if (this.ctype == F3D.CAMERA_TYPE_TARGET)
		{
			glLoadIdentity();

			this.LookAt(this.TargetPoint.x, this.TargetPoint.y, this.TargetPoint.z);
		}

		if (this.ctype == F3D.CAMERA_TYPE_FPS)
		{
			glLoadIdentity();

			// rotation
			glRotatef(this.GetRotation().x, 1.0f, 0.0f, 0.0f);
			glRotatef(this.GetRotation().y, 0.0f, 1.0f, 0.0f);
			glRotatef(this.GetRotation().z, 0.0f, 0.0f, 1.0f);

			// inverse translation
			glTranslatef(-this.GetPosition().x, -this.GetPosition().y, -this.GetPosition().z);
		}

		F3D.Frustum.Update();

	}

	@Override
	public void Destroy()
	{

	}

	public void UpdateAxisDirection()
	{
		double a, b, c, d, e, f, ad, bd;

		a = Math.cos(0.0174532925f * this.GetRotation().x);
		b = Math.sin(0.0174532925f * this.GetRotation().x);
		c = Math.cos(0.0174532925f * this.GetRotation().y);
		d = Math.sin(0.0174532925f * this.GetRotation().y);
		e = Math.cos(0.0174532925f * this.GetRotation().z);
		f = Math.sin(0.0174532925f * this.GetRotation().z);

		ad = a * d;
		bd = b * d;

		this.axis._right.x = (float) (c * e);
		this.axis._right.y = (float) (-c * f);
		this.axis._right.z = (float) d;

		this.axis._up.x = (float) (bd * e + a * f);
		this.axis._up.y = (float) (-bd * f + a * e);
		this.axis._up.z = (float) (-b * c);

		this.axis._forward.x = (float) (-ad * e + b * f);
		this.axis._forward.y = (float) (ad * f + b * e);
		this.axis._forward.z = (float) (a * c);
	}
}
