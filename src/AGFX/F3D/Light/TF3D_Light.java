// -----------------------------------------------------------------------
// A3D_Light: constructor
// -----------------------------------------------------------------------

package AGFX.F3D.Light;


import AGFX.F3D.F3D;
import AGFX.F3D.Entity.TF3D_Entity;
import static org.lwjgl.opengl.GL11.*;


public class TF3D_Light extends TF3D_Entity
{
	

	
	public static final int LT_SPOT            = 0;
	public static final int LT_POINT           = 1;
	public static final int LT_DIRECTIONAL     = 2;

	public int              ID                 = 0;
	public int              _type              = LT_POINT;
	// Position.
	// - from extends
	// Colors (I'll disregard emission for now).
	public float            Ambient[]          = new float[] { 0.1f, 0.1f, 0.1f, 1.0f };
	public float            Diffuse[]          = new float[] { 1f, 1f, 1f, 1.0f };
	public float            Specular[]         = new float[] { 1f, 1f, 1f, 1.0f };
	
	// spot
	public int              spot_cut_off       = 180;
	public float            spot_target[]      = new float[] { 0f, 0f, 0f };
	public float            spot_direction[]   = new float[] { 0f, -1f, 0f };
	public float            spot_exponent      = 25.0f;

	public float            spot_constant_att  = 1.0f;
	public float            spot_linear_att    = 0.0f;
	public float            spot_quadratic_att = 0.0f;
	// Constant attenuation factor.
	public float            Attenuation        = 1.0f;

	public float            range              = 10.0f;
	public boolean          enable             = true;

	// -----------------------------------------------------------------------
	// A3D_Light: constructor
	// -----------------------------------------------------------------------
	public TF3D_Light(String name, int light_id)
	{
		this.classname = F3D.CLASS_LIGHT;
		this.ID = light_id;
		this.name = name;
	}

	// -----------------------------------------------------------------------
	// A3D_Light: constructor
	// -----------------------------------------------------------------------
	public void Render()
	{
		float pos[] = new float[4];

		if (this.IsEnabled())
		{

			glPushMatrix();
			glLoadIdentity();

			pos[0] = this.GetPosition().x;
			pos[1] = this.GetPosition().y;
			pos[2] = this.GetPosition().z;

			if (this._type == LT_DIRECTIONAL)
			{
				pos[3] = 0.0f;

				glLight(GL_LIGHT0 + this.ID, GL_POSITION, F3D.GetBuffer.Float(pos));
				glLight(GL_LIGHT0 + this.ID, GL_DIFFUSE, F3D.GetBuffer.Float(this.Diffuse));
			}

			if (this._type == LT_POINT)
			{
				pos[3] = 1.0f;

				glLight(GL_LIGHT0 + this.ID, GL_POSITION, F3D.GetBuffer.Float(pos));
				glLight(GL_LIGHT0 + this.ID, GL_AMBIENT, F3D.GetBuffer.Float(this.Ambient));
				glLight(GL_LIGHT0 + this.ID, GL_DIFFUSE, F3D.GetBuffer.Float(this.Diffuse));
				glLightf(GL_LIGHT0 + this.ID, GL_CONSTANT_ATTENUATION, this.spot_constant_att);
				
			}

			if (this._type == LT_SPOT)
			{
				pos[3] = 1.0f;
				glLight(GL_LIGHT0 + this.ID, GL_POSITION, F3D.GetBuffer.Float(pos));
				glLight(GL_LIGHT0 + this.ID, GL_AMBIENT, F3D.GetBuffer.Float(this.Ambient));
				glLight(GL_LIGHT0 + this.ID, GL_DIFFUSE, F3D.GetBuffer.Float(this.Diffuse));
				glLight(GL_LIGHT0 + this.ID, GL_SPECULAR, F3D.GetBuffer.Float(this.Specular));

				glLightf(GL_LIGHT0 + this.ID, GL_CONSTANT_ATTENUATION, this.Attenuation);
				glLightf(GL_LIGHT0 + this.ID, GL_SPOT_CUTOFF, this.spot_cut_off);
				glLightf(GL_LIGHT0 + this.ID, GL_SPOT_EXPONENT, this.spot_exponent);
				//glLightf(GL_LIGHT0 + this.ID, GL_SHININESS, 500f);
				
				glLight(GL_LIGHT0 + this.ID, GL_SPOT_DIRECTION, F3D.GetBuffer.Float(this.spot_direction));

				glLightf(GL_LIGHT0 + this.ID, GL_CONSTANT_ATTENUATION, this.spot_constant_att);
				glLightf(GL_LIGHT0 + this.ID, GL_LINEAR_ATTENUATION, this.spot_linear_att);
				glLightf(GL_LIGHT0 + this.ID, GL_QUADRATIC_ATTENUATION, this.spot_quadratic_att);
				

			}

			// render childs
			for (int i = 0; i < this.childs.size(); i++)
			{
				this.childs.get(i).Render();
			}

			glPopMatrix();

		}
	}

	// -----------------------------------------------------------------------
	// A3D_Light: Enable
	// -----------------------------------------------------------------------
	public void Enable()
	{
		glEnable(GL_LIGHT0 + this.ID);
	}

	// -----------------------------------------------------------------------
	// A3D_Light: Enable
	// -----------------------------------------------------------------------
	public void Disable()
	{
		glDisable(GL_LIGHT0 + this.ID);
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
