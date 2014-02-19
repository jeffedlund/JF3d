/**
 * 
 */
package AGFX.F3D.Particles;

import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import static org.lwjgl.opengl.GL11.*;

import AGFX.F3D.F3D;
import AGFX.F3D.Billboard.TF3D_Billboard;
import AGFX.F3D.Entity.TF3D_Entity;

/**
 * @author AndyGFX
 * 
 */
public class TF3D_Particles extends TF3D_Entity
{

	private int                  PARTICLES_COUNT = 500;
	private TF3D_Particle_item[] particles;
	private TF3D_Particle_Sprite sprite;

	private float                lifetime        = 1000f;
	private Vector3f             gravity;
	private Vector3f             direction;
	private Vector4f             ColorStart;
	private Vector4f             ColorEnd;
	private Vector3f             ScaleStart;
	private Vector3f             ScaleEnd;

	// -------------------------------------------------------

	private boolean              doBurst;

	// -----------------------------------------------------------------------
	// TF3D_Particles:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Create base particle emiter <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param name
	 *            - name
	 * @param count
	 *            - particle lements count
	 * @param sprite
	 *            - sprite matrix
	 * @param life
	 *            - lifetime
	 */
	// -----------------------------------------------------------------------
	public TF3D_Particles(String name, int count, TF3D_Particle_Sprite sprite, float life)
	{
		this.PARTICLES_COUNT = count;

		if (count > 500)
		{
			this.PARTICLES_COUNT = 500;
		}

		this.particles = new TF3D_Particle_item[this.PARTICLES_COUNT];
		this.sprite = sprite;
		this.lifetime = life;
		this.classname = F3D.CLASS_PARTICLE_EMITTER;
		this.name = name;
		this.gravity = new Vector3f(0, 0, 0);
		this.direction = new Vector3f(0, 0, 0);

		F3D.Worlds.RemoveEntity(F3D.Worlds.GetCurrentName(), sprite.name);
	}

	// -----------------------------------------------------------------------
	// TF3D_Particles:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Initialize particles <BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	public void Init()
	{
		// Initials All The Textures
		for (int loop = 0; loop < this.PARTICLES_COUNT; loop++)
		{
			particles[loop] = new TF3D_Particle_item();
			particles[loop].sprite = this.sprite.Clone();
			particles[loop].sprite.name = this.sprite.name + "_" + String.valueOf(loop);

			this.ResetParticleItem(loop);

		}

		this.Update();
	}

	// -----------------------------------------------------------------------
	// TF3D_Particles:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Update particles <BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	public void Update()
	{

		for (int loop = 0; loop < this.PARTICLES_COUNT; loop++)
		{
			if (particles[loop].active) // If The Particle Is Active
			{

				// EXPLOSION FX - sample ------------------------------------------
				if (doBurst)
				{
					particles[loop].position.set(this.GetPosition());

					particles[loop].direction.x = (float) ((50 * Math.random()) - 26.0f) * 10.0f;
					particles[loop].direction.y = (float) ((50 * Math.random()) - 25.0f) * 10.0f;
					particles[loop].direction.z = (float) ((50 * Math.random()) - 25.0f) * 10.0f;
				}

				// STANDART PARTICLE ITEM UPDATE ----------------------------------

				// set sprite position

				// TODO scale from - to size
				// particles[loop].sprite.SetScale(zoomRate, zoomRate,
				// zoomRate);

				particles[loop].sprite.SetPosition(particles[loop].position);

				// Draw The Particle Using Our RGB Values, Fade The Particle
				// Based On It's Life

				// POS = POS + ((appApeed)*(DIR/(2 * this.lifetime)

				Vector3f delta_dir = new Vector3f(particles[loop].direction);

				delta_dir.scale(F3D.Timer.AppSpeed() * (1f / (2.0f * this.lifetime)));
				particles[loop].position.add(delta_dir);

				// ADD GRAVITY
				particles[loop].direction.add(particles[loop].gravity);

				particles[loop].life += particles[loop].inc_life;

				// SET COLOR

				this.SetActualColor(loop);
				particles[loop].sprite.material.colors.diffuse[0] = particles[loop].r;
				particles[loop].sprite.material.colors.diffuse[1] = particles[loop].g;
				particles[loop].sprite.material.colors.diffuse[2] = particles[loop].b;
				particles[loop].sprite.material.colors.diffuse[3] = particles[loop].a;

				if (particles[loop].life > this.lifetime)
				{
					particles[loop].active = false;
				}

			} else
			{
				this.ResetParticleItem(loop);
			}
		}

		doBurst = false;
	}

	// -----------------------------------------------------------------------
	// TF3D_Particles:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Render particles <BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	public void Render()
	{

	}

	// -----------------------------------------------------------------------
	// TF3D_Particles:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Reset particle element <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param ID
	 *            - particle element ID
	 */
	// -----------------------------------------------------------------------
	public void ResetParticleItem(int ID)
	{

		// set direction
		// TODO - create various direction
		particles[ID].direction.set(this.direction);

		// set lifetime
		particles[ID].life = 0f;

		// Reset color
		// TODO color modification
		particles[ID].r = this.ColorStart.x;
		particles[ID].g = this.ColorStart.y;
		particles[ID].b = this.ColorStart.z;
		particles[ID].a = this.ColorStart.w;

		// Random life increment
		particles[ID].inc_life = this.GetLifeIncrement();

		// Random DIrection*Speed
		// TODO write emitter types [point, line, plane, area, mesh]

		particles[ID].direction.set(this.direction);

		// set position from EMITTER
		particles[ID].position.set(this.GetPosition());

		// set SPRITE position from EMITTER
		particles[ID].sprite.SetPosition(this.GetPosition());

		// Set GRAVITY
		particles[ID].gravity.set(this.gravity);

		// Make Particle Active
		particles[ID].active = true;

	}

	public void SetGravity(float gx, float gy, float gz)
	{
		this.gravity = new Vector3f(gx, gy, gz);
	}

	public void SetGravity(Vector3f g)
	{
		this.gravity = new Vector3f(g);
	}

	public void SetDirection(float gx, float gy, float gz)
	{
		this.direction = new Vector3f(gx, gy, gz);
	}

	public void SetDirection(Vector3f g)
	{
		this.direction = new Vector3f(g);
	}

	public Vector4f getColorStart()
	{
		return ColorStart;
	}

	public void setColorStart(Vector4f colorStart)
	{
		ColorStart = colorStart;
	}

	public Vector4f getColorEnd()
	{
		return ColorEnd;
	}

	public void setColorEnd(Vector4f colorEnd)
	{
		ColorEnd = colorEnd;
	}

	public Vector3f getScaleStart()
	{
		return ScaleStart;
	}

	public void setScaleStart(Vector3f scaleStart)
	{
		ScaleStart = scaleStart;
	}

	public Vector3f getScaleEnd()
	{
		return ScaleEnd;
	}

	public void setScaleEnd(Vector3f scaleEnd)
	{
		ScaleEnd = scaleEnd;
	}

	public float GetLifeIncrement()
	{
		return (float) (100f * Math.random()) / this.lifetime + 0.003f;
	}

	private void SetActualColor(int ID)
	{
		Vector4f res = new Vector4f();

		float perc = this.particles[ID].life * (1f / this.lifetime);

		res.interpolate(this.ColorStart, this.ColorEnd, perc);
		/*
		 * this.particles[ID].r = F3D.MathUtils.Interpolate(this.ColorStart.x, this.ColorEnd.x, perc, 1f / this.lifetime); this.particles[ID].g = F3D.MathUtils.Interpolate(this.ColorStart.y,
		 * this.ColorEnd.y, perc, 1f / this.lifetime); this.particles[ID].b = F3D.MathUtils.Interpolate(this.ColorStart.z, this.ColorEnd.z, perc, 1f / this.lifetime); this.particles[ID].a =
		 * F3D.MathUtils.Interpolate(this.ColorStart.w, this.ColorEnd.w, perc, 1f / this.lifetime);
		 */

		this.particles[ID].r = res.x;
		this.particles[ID].g = res.y;
		this.particles[ID].b = res.z;
		this.particles[ID].a = res.w;

		// if (ID==0) F3D.Log.info("R", String.valueOf(perc));
	}

	public void createBurst()
	{
		this.doBurst = true;
	}

	@Override
	public void Destroy()
	{

	}

}
