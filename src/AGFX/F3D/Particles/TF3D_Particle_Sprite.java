/**
 * 
 */
package AGFX.F3D.Particles;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glMultMatrix;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex3f;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.vecmath.Vector3f;

import AGFX.F3D.F3D;
import AGFX.F3D.Entity.TF3D_Entity;
import AGFX.F3D.Material.TF3D_Material;
import AGFX.F3D.Math.TF3D_Axis3f;

/**
 * @author AndyGFX
 * 
 */
public class TF3D_Particle_Sprite extends TF3D_Entity
{

	/**
	 * Billboard rendering mode (set one from BILLBOARD: CONST included in A3D as const
	 */
	public int           mode;

	/** Target point for Directional billboard type */
	public Vector3f      Dir;
	/** material assigned to surface */
	public TF3D_Material material;
	// ** help var for alpha fading */
	public float         Alpha = 1f;
	/** depth sort true/false */
	public boolean       bDepthSort;
	/** alpha fade true/false */
	public boolean       bFadeAlpha;
	public float         alpha_fade_speed;
	/** enable rendering true/false */
	public boolean       enable;

	// -----------------------------------------------------------------------
	// TA3D_Billboard:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Constructor <BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	public TF3D_Particle_Sprite()
	{
		this.classname = F3D.CLASS_PARTICLE_SPRITE;
		this.Dir = new Vector3f(0, -1f, 0);
		this.mode = F3D.BM_SPRITE;
		this.enable = true;
		this.Alpha = 1.0f;
		this.alpha_fade_speed = 1f;
		this.bDepthSort = true;
		this.bFadeAlpha = false;
		this.material = null;
		this.name = "none";
	}

	// -----------------------------------------------------------------------
	// TA3D_Billboard:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param right
	 * @param up
	 * @param look
	 * @param pos
	 * @return
	 */
	// -----------------------------------------------------------------------
	private float[] _CreateBillboardMatrix(Vector3f right, Vector3f up, Vector3f look, Vector3f pos)
	{
		float bbmat[] = new float[16];

		bbmat[0] = right.x;
		bbmat[1] = right.y;
		bbmat[2] = right.z;
		bbmat[3] = 0f;
		bbmat[4] = up.x;
		bbmat[5] = up.y;
		bbmat[6] = up.z;
		bbmat[7] = 0f;
		bbmat[8] = look.x;
		bbmat[9] = look.y;
		bbmat[10] = look.z;
		bbmat[11] = 0f;
		// Add the translation in as well.
		bbmat[12] = pos.x;
		bbmat[13] = pos.y;
		bbmat[14] = pos.z;
		bbmat[15] = 1f;

		return bbmat;

	}

	// -----------------------------------------------------------------------
	// TA3D_Billboard:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param pos
	 */
	// -----------------------------------------------------------------------
	private void _BillboardPoint(Vector3f pos)
	{
		Vector3f look = new Vector3f(0, 0, 0);
		TF3D_Axis3f Axis = new TF3D_Axis3f();
		float[] M = new float[16];
		FloatBuffer fM;

		look.sub(F3D.Cameras.GetCurrentCamera().GetPosition(), pos);

		F3D.Cameras.GetCurrentCamera().UpdateAxisDirection();
		Axis = F3D.Cameras.GetCurrentCamera().axis;

		look.normalize();

		M = this._CreateBillboardMatrix(Axis._right, Axis._up, look, pos);

		ByteBuffer byteBuf = ByteBuffer.allocateDirect(M.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		fM = byteBuf.asFloatBuffer();
		fM.put(M).rewind();

		glMultMatrix(fM);

	}

	// -----------------------------------------------------------------------
	// TA3D_Billboard:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param pos
	 */
	// -----------------------------------------------------------------------
	private void _BillboardAxisX(Vector3f pos)
	{
		Vector3f up = new Vector3f(1.0f, 0.0f, 0.0f);
		Vector3f right = new Vector3f(0.0f, 0.0f, 0.0f);
		Vector3f look = new Vector3f();

		float[] M = new float[16];
		FloatBuffer fM;

		look.sub(F3D.Cameras.GetCurrentCamera().GetPosition(), pos);

		look.x = 0.0f;
		look.normalize();

		right.cross(up, look);
		M = this._CreateBillboardMatrix(right, up, look, pos);

		ByteBuffer byteBuf = ByteBuffer.allocateDirect(M.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		fM = byteBuf.asFloatBuffer();
		fM.put(M).rewind();

		glMultMatrix(fM);
	}

	// -----------------------------------------------------------------------
	// TA3D_Billboard:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param pos
	 */
	// -----------------------------------------------------------------------
	private void _BillboardAxisY(Vector3f pos)
	{
		Vector3f up = new Vector3f(0.0f, 1.0f, 0.0f);
		Vector3f right = new Vector3f();
		Vector3f look = new Vector3f();

		float[] M = new float[16];
		FloatBuffer fM;

		look.sub(F3D.Cameras.GetCurrentCamera().GetPosition(), pos);

		look.y = 0.0f;
		look.normalize();

		right.cross(up, look);
		M = this._CreateBillboardMatrix(right, up, look, pos);

		ByteBuffer byteBuf = ByteBuffer.allocateDirect(M.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		fM = byteBuf.asFloatBuffer();
		fM.put(M).rewind();

		glMultMatrix(fM);
	}

	// -----------------------------------------------------------------------
	// TA3D_Billboard:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param pos
	 */
	// -----------------------------------------------------------------------
	private void _BillboardAxisZ(Vector3f pos)
	{
		Vector3f up = new Vector3f(0, 0, 1);
		Vector3f right = new Vector3f();
		Vector3f look = new Vector3f();

		float[] M = new float[16];
		FloatBuffer fM;

		look.sub(F3D.Cameras.GetCurrentCamera().GetPosition(), pos);

		look.z = 0.0f;
		look.normalize();

		right.cross(up, look);
		M = this._CreateBillboardMatrix(right, up, look, pos);

		ByteBuffer byteBuf = ByteBuffer.allocateDirect(M.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		fM = byteBuf.asFloatBuffer();
		fM.put(M).rewind();

		glMultMatrix(fM);
	}

	// -----------------------------------------------------------------------
	// TA3D_Billboard:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param pos
	 * @param dir
	 */
	// -----------------------------------------------------------------------
	private void _BillboardAxisDir(Vector3f pos, Vector3f dir)
	{
		Vector3f up = new Vector3f(0, 0, 1);
		Vector3f right = new Vector3f();
		Vector3f look = new Vector3f();

		float[] M = new float[16];
		FloatBuffer fM;

		look.sub(F3D.Cameras.GetCurrentCamera().GetPosition(), pos);
		look.normalize();

		up = dir;
		right.cross(up, look);
		right.normalize();
		look.cross(right, up);

		M = this._CreateBillboardMatrix(right, up, look, pos);

		ByteBuffer byteBuf = ByteBuffer.allocateDirect(M.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		fM = byteBuf.asFloatBuffer();
		fM.put(M);
		fM.position(0);

		glMultMatrix(fM);
	}

	private void _DrawPlane(float w, float h)
	{
		glBegin(GL_QUADS);
		// [0]
		glTexCoord2f(0.0f, 1.0f);
		glVertex3f(-(w / 2f), -(h / 2f), 0);
		// [1]
		glTexCoord2f(1.0f, 1.0f);
		glVertex3f(+(w / 2f), -(h / 2f), 0);
		// [2]
		glTexCoord2f(1.0f, 0.0f);
		glVertex3f(+(w / 2f), +(h / 2f), 0);
		// [3]
		glTexCoord2f(0.0f, 0.0f);
		glVertex3f(-(w / 2f), +(h / 2f), 0);
		glEnd();
	}

	private void _DrawDirPlane(float w, float h)
	{
		glBegin(GL_QUADS);
		// 0
		glTexCoord2f(0.0f, 1.0f);
		glVertex3f(-(w / 2f), 0, 0);
		// 1
		glTexCoord2f(1.0f, 1.0f);
		glVertex3f(+(w / 2f), 0, 0);
		// 2
		glTexCoord2f(1.0f, 0.0f);
		glVertex3f(+(w / 2f), h, 0);
		// 3
		glTexCoord2f(0.0f, 0.0f);
		glVertex3f(-(w / 2f), h, 0);
		glEnd();
	}

	// -----------------------------------------------------------------------
	// TA3D_Billboard:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Render billboard <BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	public void Render()
	{

		if (this.IsEnabled())
		{
			if (this.IsVisible())
			{
				if (this.bFadeAlpha)
				{
					glEnable(GL_BLEND);
					if (F3D.Viewport.IsPointVisible(this.GetPosition()))
					{
						this.Alpha = this.Alpha + this.alpha_fade_speed * F3D.Timer.AppSpeed();
						if (this.Alpha > 1f)
							this.Alpha = 1f;
					} else
					{
						this.Alpha = this.Alpha - this.alpha_fade_speed * F3D.Timer.AppSpeed();
						if (this.Alpha < 0f)
							this.Alpha = 0f;
					}

				} else
				{
					this.Alpha = 1f;
				}

				if (this.material != null)
				{
					this.material.colors.diffuse[3] = this.Alpha;
					F3D.Surfaces.ApplyMaterial(this.material);
				}

				glPushMatrix();

				switch (this.mode)
				{
				case F3D.BM_SPRITE:
					this._BillboardPoint(this.GetPosition());
					this._DrawPlane(this.GetScale().x, this.GetScale().y);
					break;
				case F3D.BM_AXIS_X:
					this._BillboardAxisX(this.GetPosition());
					this._DrawPlane(this.GetScale().x, this.GetScale().y);
					break;
				case F3D.BM_AXIS_Y:
					this._BillboardAxisY(this.GetPosition());
					this._DrawPlane(this.GetScale().x, this.GetScale().y);
					break;
				case F3D.BM_AXIS_Z:
					this._BillboardAxisZ(this.GetPosition());
					this._DrawPlane(this.GetScale().x, this.GetScale().y);
					break;
				case F3D.BM_DIRECTIONAL:
					this._BillboardAxisDir(this.GetPosition(), this.Dir);
					this._DrawDirPlane(this.GetScale().x, this.GetScale().y);
					break;
				default:
					break;
				}

				glPopMatrix();
				glDisable(GL_BLEND);
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
		this.material = null;
	}

	// -----------------------------------------------------------------------
	// TA3D_Entity:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Check if Entity is visible in Frustum <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @return
	 */
	// -----------------------------------------------------------------------
	public boolean IsVisible()
	{
		if (this.enableFrustumTest)
		{
			this.visibility = F3D.Frustum.PointInFrustum(this.GetPosition());
		} else
		{
			this.visibility = true;
		}
		return this.visibility;

	}

	// -----------------------------------------------------------------------
	// TF3D_Particle_Sprite:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Create particle SPRITE [graphics] element <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param _name
	 *            - name
	 * @param pos
	 *            - position
	 * @param dir
	 *            - direction
	 * @param sx
	 *            - width
	 * @param sy
	 *            - height
	 * @param mat
	 *            - material name
	 * @param type
	 *            - type
	 * @return - TF3D_Particle_Sprite instance
	 */
	// -----------------------------------------------------------------------
	public static TF3D_Particle_Sprite CreateSprite(String _name, Vector3f pos, Vector3f dir, float sx, float sy, String mat, int type)
	{
		TF3D_Particle_Sprite sprite = new TF3D_Particle_Sprite();

		sprite.mode = type;
		sprite.name = _name;
		sprite.enable = true;
		sprite.SetScale(sx, sy, 0.0f);
		int material_id = F3D.Surfaces.FindByName(mat);
		sprite.material = F3D.Surfaces.materials.get(material_id).Clone();
		sprite.SetPosition(pos);
		sprite.Dir.set(dir);

		return sprite;

	}

	// -----------------------------------------------------------------------
	// TF3D_Particle_Sprite:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Clone particle SPRITE [graphics] element <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @return
	 */
	// -----------------------------------------------------------------------
	public TF3D_Particle_Sprite Clone()
	{
		TF3D_Particle_Sprite res = new TF3D_Particle_Sprite();

		res.mode = this.mode;
		res.name = this.name;
		res.enable = this.enable;
		res.SetScale(this.GetScale());
		res.bFadeAlpha = this.bFadeAlpha;
		res.bDepthSort = this.bDepthSort;
		res.material = this.material.Clone();
		res.SetScale(this.GetScale());
		res.Dir.set(this.Dir);
		res.classname = this.classname;
		res.Alpha = this.Alpha;
		res.alpha_fade_speed = this.alpha_fade_speed;

		return res;
	}
}
