package AGFX.F3D.Math;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.vecmath.*;

import com.bulletphysics.linearmath.QuaternionUtil;

import org.lwjgl.BufferUtils;

import org.lwjgl.util.glu.GLU;

import static org.lwjgl.opengl.GL11.*;

import AGFX.F3D.F3D;

public class TF3D_MathUtils
{
	public TF3D_MathUtils()
	{

	}

	public Vector3f Matrix2Angles(Matrix4f m)
	{

		/**
		 * this conversion uses conventions as described on page:
		 * http://www.euclideanspace
		 * .com/maths/geometry/rotations/euler/index.htm Coordinate System:
		 * right hand Positive angle: right hand Order of euler angles: heading
		 * first, then attitude, then bank matrix row column ordering: [m00 m01
		 * m02] [m10 m11 m12] [m20 m21 m22]
		 */

		// Assuming the angles are in radians.
		if (m.m10 > 0.998)
		{ // singularity at north pole
			float heading = (float) Math.atan2(m.m02, m.m22);
			float attitude = (float) (Math.PI / 2f);
			float bank = 0f;
			return new Vector3f(bank * F3D.RADTODEG, heading * F3D.RADTODEG, attitude * F3D.RADTODEG);
		}
		if (m.m10 < -0.998)
		{ // singularity at south pole
			float heading = (float) Math.atan2(m.m02, m.m22);
			float attitude = (float) (-Math.PI / 2f);
			float bank = 0;
			return new Vector3f(bank * F3D.RADTODEG, heading * F3D.RADTODEG, attitude * F3D.RADTODEG);
		}
		float heading = (float) Math.atan2(-m.m20, m.m00);
		float bank = (float) Math.atan2(m.m12, m.m11);
		float attitude = (float) Math.asin(m.m10);

		return new Vector3f(bank * F3D.RADTODEG, heading * F3D.RADTODEG, attitude * F3D.RADTODEG);
	}

	public Vector3f Quad2Angles(Quat4f q)
	{
		/*
		 * Heading = rotation about y axis Attitude = rotation about z axis Bank
		 * = rotation about x axis
		 */

		float angles[] = new float[3];

		float sqw = q.w * q.w;
		float sqx = q.x * q.x;
		float sqy = q.y * q.y;
		float sqz = q.z * q.z;
		float unit = sqx + sqy + sqz + sqw; // if normalized is one, otherwise
		                                    // is correction factor
		float test = q.x * q.y + q.z * q.w;
		if (test > 0.499 * unit)
		{ // singularity at north pole
			angles[1] = (float) (2f * Math.atan2(q.x, q.w));
			angles[2] = (float) (Math.PI * 0.5f);
			angles[0] = 0;
		} else if (test < -0.499 * unit)
		{ // singularity at south pole
			angles[1] = (float) (-2f * Math.atan2(q.x, q.w));
			angles[2] = (float) (-Math.PI * 0.5f);
			angles[0] = 0;
		} else
		{
			// X
			angles[0] = (float) Math.atan2(2 * q.x * q.w - 2 * q.y * q.z, -sqx + sqy - sqz + sqw); // yaw
			                                                                                       // or
			                                                                                       // bank
			// Y axis
			angles[1] = (float) Math.atan2(2 * q.y * q.w - 2 * q.x * q.z, sqx - sqy - sqz + sqw); // roll
			                                                                                      // or
			                                                                                      // heading
			// Z axis
			angles[2] = (float) Math.asin(2 * test / unit); // pitch or attitude

		}

		return new Vector3f(360f - angles[0] * F3D.RADTODEG, 360f - angles[1] * F3D.RADTODEG, 360f - angles[2] * F3D.RADTODEG);

	}

	// -----------------------------------------------------------------------
	// TF3D_MathUtils:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Create Quat3f vector f rom angles <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param yaw
	 *            - Y angle
	 * @param roll
	 *            - X angle
	 * @param pitch
	 *            - Z angle
	 * @return
	 */
	// -----------------------------------------------------------------------
	public Quat4f AnglesToQuat4f(float yaw, float roll, float pitch)
	{

		Quat4f q = new Quat4f();
		QuaternionUtil.setEuler(q, roll * F3D.DEGTORAD, yaw * F3D.DEGTORAD, pitch * F3D.DEGTORAD);
		return q;
	}

	// -----------------------------------------------------------------------
	// TF3D_MathUtils:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * convert world 3D point to screen space 2D <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param pos
	 * @return
	 */
	// -----------------------------------------------------------------------
	public Vector3f World3DtoScreen2D(Vector3f pos)
	{
		Vector3f res = new Vector3f();
		FloatBuffer modelMatrix = BufferUtils.createFloatBuffer(16);
		FloatBuffer projMatrix = BufferUtils.createFloatBuffer(16);
		IntBuffer viewport = BufferUtils.createIntBuffer(16);
		FloatBuffer win_pos = BufferUtils.createFloatBuffer(16);

		glGetFloat(GL_MODELVIEW_MATRIX, modelMatrix);
		glGetFloat(GL_PROJECTION_MATRIX, projMatrix);
		glGetInteger(GL_VIEWPORT, viewport);

		GLU.gluProject(pos.x, pos.y, pos.z, modelMatrix, projMatrix, viewport, win_pos);

		res.x = win_pos.get(0);
		res.y = F3D.Config.r_display_height - win_pos.get(1);
		res.z = win_pos.get(2);

		return res;
	}

	// -----------------------------------------------------------------------
	// TF3D_MathUtils:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Calc angle between 2 vectors [0-PI/2] <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param a
	 *            - vector A
	 * @param b
	 *            - vector B
	 * @return
	 */
	// -----------------------------------------------------------------------
	public float VectorAngle(Vector3f a, Vector3f b)
	{
		float res = 0;

		Vector3f c = new Vector3f();

		c.cross(a, b);
		res = (float) Math.atan2(c.length(), a.dot(b));

		return res;
	}

	// -----------------------------------------------------------------------
	// TF3D_MathUtils:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Calc angle between 2 vectors [-PI/2,PI/2] <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param a
	 *            - vector A
	 * @param b
	 *            - vector B
	 * @param reference
	 *            - ref. vector to determine +/- angle
	 * @return
	 */
	// -----------------------------------------------------------------------
	public float VectorAngle(Vector3f a, Vector3f b, Vector3f reference)
	{
		float res = 0;

		Vector3f c = new Vector3f();

		c.cross(a, b);
		res = (float) Math.atan2(c.length(), a.dot(b));
		return a.dot(reference) < 0.f ? -res : res;
	}

	public Vector3f RotatePoint(Vector3f angle, Vector3f point)
	{
		Matrix4f rot = new Matrix4f();
		Matrix4f rot_X = new Matrix4f();
		Matrix4f rot_Y = new Matrix4f();
		Matrix4f rot_Z = new Matrix4f();

		Vector3f new_position = new Vector3f();

		rot.setIdentity();
		rot_X.setIdentity();
		rot_Y.setIdentity();
		rot_Z.setIdentity();

		rot_X.rotX(angle.x * F3D.DEGTORAD);
		rot_Y.rotY(angle.y * F3D.DEGTORAD);
		rot_Z.rotZ(angle.z * F3D.DEGTORAD);

		rot.mul(rot_Y);
		rot.mul(rot_X);
		rot.mul(rot_Z);

		rot.transform(point, new_position);
		return new_position;
	}

	public Vector3f RotatePoint(Vector3f angle, float _x, float _y, float _z)
	{
		Matrix4f rot = new Matrix4f();
		Matrix4f rot_X = new Matrix4f();
		Matrix4f rot_Y = new Matrix4f();
		Matrix4f rot_Z = new Matrix4f();

		Vector3f new_position = new Vector3f();

		rot.setIdentity();
		rot_X.setIdentity();
		rot_Y.setIdentity();
		rot_Z.setIdentity();

		rot_X.rotX(angle.x * F3D.DEGTORAD);
		rot_Y.rotY(angle.y * F3D.DEGTORAD);
		rot_Z.rotZ(angle.z * F3D.DEGTORAD);

		rot.mul(rot_Y);
		rot.mul(rot_X);
		rot.mul(rot_Z);

		rot.transform(new Vector3f(_x, _y, _z), new_position);
		return new_position;
	}

	public float UpdateValue(float current, float destination, float rate)
	{

		float res = current + ((destination - current) * rate);

		return res;
	}


}
