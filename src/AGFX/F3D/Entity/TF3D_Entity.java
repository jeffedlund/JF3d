// -----------------------------------------------------------------------
// A3D_Entity:
// -----------------------------------------------------------------------

package AGFX.F3D.Entity;

import java.util.ArrayList;
import javax.vecmath.*;

import AGFX.F3D.F3D;
import AGFX.F3D.Math.TF3D_Axis3f;

import AGFX.F3D.Mesh.TF3D_BoundingBox;

/**
 * @author AndyGFX
 * 
 */
public abstract class TF3D_Entity
{

	/** Entity start position - used for physics restart */
	public Vector3f               start_position;
	/** Entity rotation - used for physics restart */
	public Vector3f               start_rotation;

	/** Entity position */
	private Vector3f              position;
	/** Entity rotation */
	private Vector3f              rotation;
	/** Entity scale */
	private Vector3f              scale;
	/** Entity axis vectors */
	public TF3D_Axis3f            axis;
	/** class name of entity */
	public int                    classname;
	/** name of entity */
	public String                 name;
	public Boolean                visibility        = false;
	private Boolean               enable            = true;

	public ArrayList<TF3D_Entity> childs;
	public TF3D_Entity            parent;
	public Boolean                is_child          = false;

	public TF3D_BoundingBox       BBOX;
	public Boolean                enableFrustumTest = true;
	public int                    rotation_seq      = F3D.ROTATE_IN_SEQ_XYZ;

	// -----------------------------------------------------------------------
	// TA3D_Entity:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Constructor <BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	public TF3D_Entity()
	{
		F3D.Log.info("TF3D_Entity", "Create - constructor");

		this.start_position = new Vector3f();
		this.start_rotation = new Vector3f();

		this.position = new Vector3f();
		this.rotation = new Vector3f();
		this.scale = new Vector3f();
		this.scale.set(1.0f, 1.0f, 1.0f);
		this.name = "noname";
		this.axis = new TF3D_Axis3f();

		this.BBOX = new TF3D_BoundingBox();
		this.childs = new ArrayList<TF3D_Entity>();

		if (F3D.Config.e_world_autoassign)
		{
			F3D.Worlds.Add(this);
		}

	}

	// -----------------------------------------------------------------------
	// TA3D_Entity:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * set Entity position <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param _x
	 *            X position
	 * @param _y
	 *            Y position
	 * @param _z
	 *            Z position
	 */
	// -----------------------------------------------------------------------
	public void SetPosition(float _x, float _y, float _z)
	{
		this.position.set(_x, _y, _z);
	}

	// -----------------------------------------------------------------------
	// TF3D_Entity:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Set entity position with hold start position <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param _x
	 *            - X position
	 * @param _y
	 *            - Y position
	 * @param _z
	 *            - Z position
	 * @param store
	 *            - true save pos to start_position
	 */
	// -----------------------------------------------------------------------
	public void SetPosition(float _x, float _y, float _z, Boolean store)
	{
		if (store)
			this.start_position.set(_x, _y, _z);

		this.position.set(_x, _y, _z);
	}

	// -----------------------------------------------------------------------
	// TA3D_Entity:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Set Entity position <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param p
	 *            vector3f
	 */
	// -----------------------------------------------------------------------
	public void SetPosition(Vector3f p)
	{
		this.position = p;
	}

	// -----------------------------------------------------------------------
	// TF3D_Entity:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Set entity position with hold start position <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param p
	 *            - position vector
	 * @param store
	 *            - true save pos to start_position
	 */
	// -----------------------------------------------------------------------
	public void SetPosition(Vector3f p, Boolean store)
	{
		if (store)
			this.start_position.set(p);
		this.position.set(p);
	}

	// -----------------------------------------------------------------------
	// TF3D_Entity:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * return position vector <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @return
	 */
	// -----------------------------------------------------------------------
	public Vector3f GetPosition()
	{
		return this.position;
	}

	// -----------------------------------------------------------------------
	// TF3D_Entity:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * return start position vector <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @return
	 */
	// -----------------------------------------------------------------------
	public Vector3f GetStartPosition()
	{
		return this.start_position;
	}

	// -----------------------------------------------------------------------
	// TA3D_Entity:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Set entity rotation and recalculate calculate Axis orientation <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param _x
	 *            X axis angle
	 * @param _y
	 *            Y axis angle
	 * @param _z
	 *            Z axis angle
	 */
	// -----------------------------------------------------------------------
	public void SetRotation(float _x, float _y, float _z)
	{
		_x = _x % 360f;
		_y = _y % 360f;
		_z = _z % 360f;

		this.rotation.set(_x, _y, _z);
	}

	// -----------------------------------------------------------------------
	// TA3D_Entity:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Set entity rotation and recalculate calculate Axis orientation <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param r
	 *            rotation angles as vector
	 */
	// -----------------------------------------------------------------------
	public void SetRotation(Vector3f r)
	{
		this.SetRotation(r.x, r.y, r.z);
	}

	public Vector3f GetRotation()
	{
		return this.rotation;

	}

	// -----------------------------------------------------------------------
	// TA3D_Entity:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Set Entity scale <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param _x
	 *            X scale size [1.0f]
	 * @param _y
	 *            Y scale size [1.0f]
	 * @param _z
	 *            Z scale size [1.0f]
	 */
	// -----------------------------------------------------------------------
	public void SetScale(float _x, float _y, float _z)
	{
		this.scale.set(_x, _y, _z);
	}

	public void SetName(String n)
	{
		this.name = n;
	}

	public String GetName()
	{
		return this.name;
	}

	// -----------------------------------------------------------------------
	// TA3D_Entity:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Set Entity scale <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param s
	 *            scale vector [1.0f,1.0f,1.0f]
	 */
	// -----------------------------------------------------------------------
	public void SetScale(Vector3f s)
	{
		this.scale = s;
	}

	// -----------------------------------------------------------------------
	// TF3D_Entity:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Get entity scale <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @return
	 */
	// -----------------------------------------------------------------------
	public Vector3f GetScale()
	{
		return this.scale;
	}

	// -----------------------------------------------------------------------
	// TF3D_Entity:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Convert angles to AXIS direction vectors <BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	public void UpdateAxisDirection()
	{
		this.axis.GetFromEuler(this.rotation);
	}

	// -----------------------------------------------------------------------
	// TF3D_Entity:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Turn entity around position on radius <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param angle
	 *            - delta axis angles as vector3f
	 * @param radius
	 *            - distance to position
	 */
	// -----------------------------------------------------------------------
	public void Turn(Vector3f angle, float radius)
	{
		this.Turn(angle.x, angle.y, angle.z, radius);
	}

	// -----------------------------------------------------------------------
	// TF3D_Entity:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Turn entity around position on radius <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param dx
	 *            - delta of X angle
	 * @param dy
	 *            - delta of Y angle
	 * @param dz
	 *            - delta of Z angle
	 * @param radius
	 *            - distance to position
	 */
	// -----------------------------------------------------------------------
	public void Turn(float dx, float dy, float dz, float radius)
	{

		Vector3f res = new Vector3f(0, 0, 0);
		res.add(this.rotation, new Vector3f(dx, dy, dz));

		this.SetRotation(res);

		Vector3f new_position = new Vector3f();

		new_position = F3D.MathUtils.RotatePoint(this.GetRotation(), new Vector3f(0, 0, radius));
		new_position.add(this.start_position);
		this.SetPosition(new_position);

	}

	// -----------------------------------------------------------------------
	// TF3D_Entity:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Turn entity around center<BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param dx
	 *            - delta of X angle
	 * @param dy
	 *            - delta of Y angle
	 * @param dz
	 *            - delta of Z angle
	 */
	// -----------------------------------------------------------------------
	public void Turn(float dx, float dy, float dz)
	{
		Vector3f res = new Vector3f(0, 0, 0);

		res.add(this.rotation, new Vector3f(dx, dy, dz));

		this.SetRotation(res);
	}

	// -----------------------------------------------------------------------
	// TF3D_Entity:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Turn entity around center <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param ta
	 *            - delta angles as vector
	 */
	// -----------------------------------------------------------------------
	public void Turn(Vector3f ta)
	{
		this.Turn(ta.x, ta.y, ta.z);
	}

	// -----------------------------------------------------------------------
	// TF3D_Entity:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Move entity in delta vector <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param dx
	 *            - delta of X direction
	 * @param dy
	 *            - delta of Y direction
	 * @param dz
	 *            - delta of Z direction
	 */
	// -----------------------------------------------------------------------
	public void Move(float dx, float dy, float dz)
	{

		this.UpdateAxisDirection();

		this.axis._right.scale(dx);
		this.axis._up.scale(dy);
		this.axis._forward.scale(dz);

		this.position.add(this.position, this.axis._right);
		this.position.add(this.position, this.axis._up);
		this.position.add(this.position, this.axis._forward);
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
			Vector3f sum = new Vector3f(0, 0, 0);
			sum.add(this.GetPosition(), this.BBOX.center);
			this.visibility = F3D.Frustum.BoxInFrustum(sum, this.BBOX.size);
		} else
		{
			this.visibility = true;
		}
		return this.visibility;

	}

	// -----------------------------------------------------------------------
	// TF3D_Entity:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Point to DST entity <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param e
	 *            - target entity
	 */
	// -----------------------------------------------------------------------
	// TODO - fix rotation angles !!!
	public void PointTo(TF3D_Entity e)
	{
		this.rotation_seq = F3D.ROTATE_IN_SEQ_YXZ;

		Vector3f eye = new Vector3f(this.GetPosition());

		Vector3f forward = new Vector3f(e.GetPosition());
		forward.sub(eye);
		forward.normalize();

		Vector3f target = new Vector3f(forward);
		target.normalize();
		target.y = 0;

		float aY = F3D.MathUtils.VectorAngle(forward, new Vector3f(0, 0, 1), new Vector3f(1, 0, 0)) * F3D.RADTODEG;
		float aX = -F3D.MathUtils.VectorAngle(forward, target, new Vector3f(0, 1, 0)) * F3D.RADTODEG;
		float aZ = 0;

		this.SetRotation(aX, aY, aZ);

	}

	// -----------------------------------------------------------------------
	// TF3D_Entity:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Enable rendering <BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	public void Enable()
	{
		this.enable = true;
	}

	// -----------------------------------------------------------------------
	// TF3D_Entity:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Disable rendering <BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	public void Disable()
	{
		this.enable = false;
	}

	// -----------------------------------------------------------------------
	// TF3D_Entity:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Is rendering enbaled? <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @return
	 */
	// -----------------------------------------------------------------------
	public Boolean IsEnabled()
	{
		return this.enable;
	}

	// -----------------------------------------------------------------------
	// TF3D_Entity:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Add CHILD entity and set parent (owner) <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param m
	 */
	// -----------------------------------------------------------------------
	public void AddChild(TF3D_Entity m)
	{
		m.parent = this;
		this.is_child = true;
		this.childs.add(m);
	}

	// -----------------------------------------------------------------------
	// TF3D_Entity:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Remove CHILD entity from parent child list <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param m
	 */
	// -----------------------------------------------------------------------
	public void RemoveChild(TF3D_Entity m)
	{
		m.is_child = false;
		this.childs.remove(m);
	}

	// -----------------------------------------------------------------------
	// TF3D_Entity:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Remove CHILD entity from parent child list <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param m
	 */
	// -----------------------------------------------------------------------
	public void ClearChild()
	{
		this.parent = null;
		this.childs.clear();
	}

	public abstract void Destroy();

	// -----------------------------------------------------------------------
	// TF3D_Entity:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Abstract method for rendering entity, you need define this method in your extends<BR>
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	public abstract void Render();

	// -----------------------------------------------------------------------
	// TF3D_Entity:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Abstract method for update entity, you need define this method in your extends<BR>
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	public abstract void Update();

	// -----------------------------------------------------------------------
	// TF3D_Entity:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Draw basic infos on 2D screen <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param sx
	 *            - screen X position
	 * @param sy
	 *            - screen Y position
	 */
	// -----------------------------------------------------------------------
	public void DrawInfoAt(float sx, float sy)
	{
		F3D.Textures.DeactivateLayer(0);
		F3D.Textures.DeactivateLayer(1);
		F3D.Textures.DeactivateLayer(2);
		F3D.Textures.DeactivateLayer(3);

		this.UpdateAxisDirection();

		F3D.Fonts.DrawText("system_font", sx, sy + 13 * 0, "Name     : " + this.name, 0);
		F3D.Fonts.DrawText("system_font", sx, sy + 13 * 1, String.format("Rotation : %10.4f , %10.4f , %10.4f", this.position.x, this.position.y, this.position.z), 0);
		F3D.Fonts.DrawText("system_font", sx, sy + 13 * 2, String.format("Rotation : %10.4f , %10.4f , %10.4f", this.rotation.x, this.rotation.y, this.rotation.z), 0);
		F3D.Fonts.DrawText("system_font", sx, sy + 13 * 3, String.format("Scale    : %10.4f , %10.4f , %10.4f", this.scale.x, this.scale.y, this.scale.z), 0);
		F3D.Fonts.DrawText("system_font", sx, sy + 13 * 4, String.format("Axis X   : %10.4f , %10.4f , %10.4f", this.axis._right.x, this.axis._right.y, this.axis._right.z), 0);
		F3D.Fonts.DrawText("system_font", sx, sy + 13 * 5, String.format("Axis Y   : %10.4f , %10.4f , %10.4f", this.axis._up.x, this.axis._up.y, this.axis._up.z), 0);
		F3D.Fonts.DrawText("system_font", sx, sy + 13 * 6, String.format("Axis Z   : %10.4f , %10.4f , %10.4f", this.axis._forward.x, this.axis._forward.y, this.axis._forward.z), 0);

	}

	// -----------------------------------------------------------------------
	// TF3D_Entity:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Draw local axis <BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	public void DrawAxis()
	{
		F3D.Textures.DeactivateLayers();

		Vector3f pos = new Vector3f(this.position);

		Vector3f Ax = new Vector3f(pos);
		Ax.add(this.axis._right);

		Vector3f Ay = new Vector3f(pos);
		Ay.add(this.axis._up);

		Vector3f Az = new Vector3f(pos);
		Az.add(this.axis._forward);

		F3D.Draw.Line3D(pos, Ax);
		F3D.Draw.Line3D(pos, Ay);
		F3D.Draw.Line3D(pos, Az);

	}

}
