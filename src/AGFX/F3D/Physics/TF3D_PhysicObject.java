/**
 * 
 */
package AGFX.F3D.Physics;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import org.lwjgl.BufferUtils;

import AGFX.F3D.F3D;
import AGFX.F3D.Mesh.TF3D_Mesh;

import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.collision.shapes.BvhTriangleMeshShape;
import com.bulletphysics.collision.shapes.CapsuleShape;
import com.bulletphysics.collision.shapes.CapsuleShapeX;
import com.bulletphysics.collision.shapes.CapsuleShapeZ;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.collision.shapes.ConeShape;
import com.bulletphysics.collision.shapes.ConvexHullShape;
import com.bulletphysics.collision.shapes.StaticPlaneShape;
import com.bulletphysics.collision.shapes.ConeShapeX;
import com.bulletphysics.collision.shapes.ConeShapeZ;
import com.bulletphysics.collision.shapes.CylinderShape;
import com.bulletphysics.collision.shapes.CylinderShapeX;
import com.bulletphysics.collision.shapes.CylinderShapeZ;
import com.bulletphysics.collision.shapes.SphereShape;
import com.bulletphysics.collision.shapes.TriangleIndexVertexArray;

import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.Transform;
import com.bulletphysics.util.ObjectArrayList;

/**
 * @author AndyGFX
 * 
 */
public class TF3D_PhysicObject
{

	public CollisionShape     Shape;
	public RigidBody          RigidBody;
	public DefaultMotionState MotionState;
	public Transform          Transform;

	public float              mass      = 0.1f;
	public boolean            isDynamic = true;

	public float[]            transformMatrix;
	public FloatBuffer        transformMatrixBuffer;

	public TF3D_PhysicObject()
	{
		this.transformMatrix = new float[16];
		this.transformMatrixBuffer = BufferUtils.createFloatBuffer(16);
	}

	// -----------------------------------------------------------------------
	// TF3D_PhysicObject:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param shapemode
	 * @param mass
	 * @param pos
	 * @param rot
	 * @param size
	 */
	// -----------------------------------------------------------------------
	public void Create(int shapemode, float mass, Vector3f pos, Vector3f rot, Vector3f size)
	{
		this.mass = mass;

		if (shapemode == F3D.BULLET_SHAPE_PLANE)
		{

			size.scale(0.5f);
			this.Shape = new StaticPlaneShape(new Vector3f(0, 1, 0), 0.01f);
		}

		if (shapemode == F3D.BULLET_SHAPE_BOX)
		{
			size.scale(0.5f);
			this.Shape = new BoxShape(size);
		}

		if (shapemode == F3D.BULLET_SHAPE_SPHERE)
		{
			size.scale(0.5f);
			this.Shape = new SphereShape(size.x);
		}

		if (shapemode == F3D.BULLET_SHAPE_CAPSULE)
		{
			size.scale(0.5f);
			this.Shape = new CapsuleShape(size.x, size.y);
		}

		if (shapemode == F3D.BULLET_SHAPE_CAPSULE_X)
		{
			size.scale(0.5f);
			this.Shape = new CapsuleShapeX(size.x, size.y);
		}

		if (shapemode == F3D.BULLET_SHAPE_CAPSULE_Z)
		{
			size.scale(0.5f);
			this.Shape = new CapsuleShapeZ(size.x, size.y);
		}

		if (shapemode == F3D.BULLET_SHAPE_CYLINDER)
		{
			size.scale(0.5f);
			this.Shape = new CylinderShape(size);
		}
		if (shapemode == F3D.BULLET_SHAPE_CYLINDER_X)
		{
			size.scale(0.5f);
			this.Shape = new CylinderShapeX(size);
		}
		if (shapemode == F3D.BULLET_SHAPE_CYLINDER_Z)
		{
			size.scale(0.5f);
			this.Shape = new CylinderShapeZ(size);
		}
		if (shapemode == F3D.BULLET_SHAPE_CONE)
		{
			size.scale(0.5f);
			this.Shape = new ConeShape(size.x, size.y * 2.0f);
		}

		if (shapemode == F3D.BULLET_SHAPE_CONE_X)
		{
			size.scale(0.5f);
			this.Shape = new ConeShapeX(size.x, size.y * 2.0f);
		}

		if (shapemode == F3D.BULLET_SHAPE_CONE_Z)
		{
			size.scale(0.5f);
			this.Shape = new ConeShapeZ(size.x, size.y * 2.0f);
		}

		// Shape RigidBody

		this.Transform = new Transform();
		this.Transform.setIdentity();

		this.Transform.origin.set(pos);
		Quat4f qrot = new Quat4f();

		qrot.x = rot.x * F3D.DEGTORAD;
		qrot.y = rot.y * F3D.DEGTORAD;
		qrot.z = rot.z * F3D.DEGTORAD;
		qrot.w = 2.0f;

		this.Transform.setRotation(F3D.MathUtils.AnglesToQuat4f(rot.x, rot.y, rot.z));

		/*
		 * 
		 * this.MotionState = new DefaultMotionState(this.Transform);
		 * 
		 * Vector3f localInertia = new Vector3f(0, 0, 0); if (this.mass>0) {
		 * this.Shape.calculateLocalInertia(this.mass, localInertia);
		 * this.isDynamic = true; }
		 * 
		 * RigidBodyConstructionInfo rbInfo = new
		 * RigidBodyConstructionInfo(this.mass, this.MotionState, this.Shape,
		 * localInertia); this.RigidBody = new RigidBody(rbInfo);
		 */

		this.RigidBody = F3D.Physic.localCreateRigidBody(this.mass, this.Transform, this.Shape);

		this.RigidBody.setRestitution(0.1f);
		this.RigidBody.setFriction(0.5f);
		this.RigidBody.setDamping(0, 0.5f);

		F3D.Physic.AddBody(this.RigidBody);
	}

	// -----------------------------------------------------------------------
	// TF3D_PhysicObject:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param shapemode
	 * @param mass
	 * @param pos
	 * @param rot
	 * @param size
	 */
	// -----------------------------------------------------------------------
	public void Create(int shapemode, float mass, Vector3f pos, Vector3f rot, Vector3f size, TF3D_Mesh mesh)
	{
		this.mass = mass;

		if (shapemode == F3D.BULLET_SHAPE_CONVEXHULL)
		{
			ObjectArrayList<Vector3f> vertices = new ObjectArrayList<Vector3f>();
			for (int i = 0; i < mesh.data.vertices.length / 3; i++)
			{

				vertices.add(mesh.data.GetVertexAsVector(i));
			}
			this.Shape = new ConvexHullShape(vertices);

		}

		if (shapemode == F3D.BULLET_SHAPE_TRIMESH)
		{
			if (mesh.data.facecount > (32768 / 3))
			{
				F3D.Log.error("TF3D_PhysicObject", "Physics body is out of triangle indices count !!!");
			}

			ByteBuffer gIndices = ByteBuffer.allocateDirect(mesh.data.facecount * 3 * 4).order(ByteOrder.nativeOrder());
			ByteBuffer gVertices = ByteBuffer.allocateDirect(mesh.data.vertices.length * 3 * 4).order(ByteOrder.nativeOrder());

			for (int i = 0; i < mesh.data.indices.length; i++)
			{
				gIndices.putInt(mesh.data.indices[i]);
			}
			gIndices.flip();

			for (int i = 0; i < mesh.data.vertices.length; i++)
			{
				gVertices.putFloat(mesh.data.vertices[i]);
			}
			gVertices.flip();

			int count = mesh.data.facecount;

			/*
			 * if (count > 10000) { count = 10922; }
			 */
			TriangleIndexVertexArray indexVertexArrays = new TriangleIndexVertexArray(count, gIndices, 3 * 4, mesh.data.vertices.length, gVertices, 3 * 4);

			BvhTriangleMeshShape trimeshShape = new BvhTriangleMeshShape(indexVertexArrays, true);

			this.Shape = trimeshShape;

		}

		// Shape RigidBody

		this.Transform = new Transform();
		this.Transform.setIdentity();

		this.Transform.origin.set(pos);

		this.Transform.setRotation(F3D.MathUtils.AnglesToQuat4f(rot.x, rot.y, rot.z));

		this.RigidBody = F3D.Physic.localCreateRigidBody(this.mass, this.Transform, this.Shape);

		this.RigidBody.setRestitution(0.1f);
		this.RigidBody.setFriction(0.5f);
		this.RigidBody.setDamping(0, 0.5f);

		F3D.Physic.AddBody(this.RigidBody);
	}

	// -----------------------------------------------------------------------
	// TF3D_PhysicObject:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param rest
	 */
	// -----------------------------------------------------------------------
	public void SetRestitution(float rest)
	{
		this.RigidBody.setRestitution(rest);
	}

	// -----------------------------------------------------------------------
	// TF3D_PhysicObject:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param fr
	 */
	// -----------------------------------------------------------------------
	public void SetFriction(float fr)
	{
		this.RigidBody.setFriction(fr);
	}

	// -----------------------------------------------------------------------
	// TF3D_PhysicObject:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param dmin
	 * @param dmax
	 */
	// -----------------------------------------------------------------------
	public void SetDamping(float dmin, float dmax)
	{

		this.RigidBody.setDamping(dmin, dmax);

	}

	// -----------------------------------------------------------------------
	// GetPosition:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Get actual position of Rigid body <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @return - position
	 */
	// -----------------------------------------------------------------------
	public Vector3f GetPosition()
	{
		Vector3f pos = new Vector3f();

		// get current position
		pos.set(this.Transform.origin);
		return pos;

	}

	// -----------------------------------------------------------------------
	// GetRotation:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Get Rigid body angles <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @return - rotation
	 */
	// -----------------------------------------------------------------------
	public Vector3f GetRotation()
	{
		Vector3f rot = new Vector3f();
		
		// get current rotation
		Quat4f qm = new Quat4f();
		this.Transform.getRotation(qm);
		rot.set(F3D.MathUtils.Quad2Angles(qm));
		
		Matrix4f mat = new Matrix4f();
		this.Transform.getMatrix(mat);
		rot.set(F3D.MathUtils.Matrix2Angles(mat));
		
		
		return rot;
	}
}
