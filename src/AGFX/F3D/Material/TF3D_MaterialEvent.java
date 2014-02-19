/**
 * 
 */
package AGFX.F3D.Material;

import javax.vecmath.*;

import AGFX.F3D.F3D;

import static org.lwjgl.opengl.GL11.*;

/**
 * @author AndyGFX
 * 
 */

public class TF3D_MaterialEvent
{
	public String   name         = "none";
	public Vector2f tmp_position = new Vector2f();
	public Vector2f position     = new Vector2f();
	public Vector2f scroll       = new Vector2f();
	public Vector2f offset       = new Vector2f();
	public float    angle        = 0.0f;
	public float    tmp_angle    = 0.0f;
	public float    rotate       = 0.0f;
	public Vector2f scale        = new Vector2f(1.0f, 1.0f); ;
	public boolean  bAlphaMask   = false;
	public boolean  bAlphaBlend  = false;
	public boolean  bDepthMask   = true;
	public int      Blend_SRC    = GL_ONE;
	public int      Blend_DST    = GL_ONE;
	public int      uv_mode      = F3D.UV_MODE_UV_MAP;

	// -----------------------------------------------------------------------
	// TA3D_MaterialEvent:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Create copy of Material Event <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @return Event
	 */
	// -----------------------------------------------------------------------
	public TF3D_MaterialEvent Copy()
	{
		TF3D_MaterialEvent e = new TF3D_MaterialEvent();

		try
		{
			e = (TF3D_MaterialEvent) this.clone();
		} catch (CloneNotSupportedException e1)
		{
			e1.printStackTrace();
		}

		return e;
	}

}
