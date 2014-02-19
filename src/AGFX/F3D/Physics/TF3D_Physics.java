package AGFX.F3D.Physics;

import javax.vecmath.Vector3f;

import AGFX.F3D.F3D;

import com.bulletphysics.BulletStats;
import com.bulletphysics.collision.broadphase.BroadphaseInterface;
import com.bulletphysics.collision.broadphase.DbvtBroadphase;
import com.bulletphysics.collision.dispatch.CollisionConfiguration;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.CollisionObject;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.DynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;
import com.bulletphysics.linearmath.DebugDrawModes;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.Transform;

public class TF3D_Physics
{

	// this is the most important class
	public DynamicsWorld                     dynamicsWorld = null;
	public BroadphaseInterface               broadphase;
	public CollisionConfiguration            collisionConfiguration;
	public CollisionDispatcher               dispatcher;
	public SequentialImpulseConstraintSolver solver;

	public CollisionShape                    groundShape;
	public RigidBody                         groundBody;
	public DefaultMotionState                groundMotionState;
	public Transform                         groundTransform;
	public TF3D_GLDebugDrawer                Debug;

	public TF3D_Physics()
	{
		F3D.Log.info("TF3D_Physics", "TF3D_Physics: constructor");
		F3D.Log.info("TF3D_Physics", "TF3D_Physics: done");
	}

	// -----------------------------------------------------------------------
	// TF3D_Physics:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Create Global Dynamic World <BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	public void Initialize()
	{
		// Build the broadphase
		this.broadphase = new DbvtBroadphase();

		// Set up the collision configuration and dispatcher
		this.collisionConfiguration = new DefaultCollisionConfiguration();
		this.dispatcher = new CollisionDispatcher(collisionConfiguration);

		// The actual physics solver
		this.solver = new SequentialImpulseConstraintSolver();

		// The world.
		this.dynamicsWorld = new DiscreteDynamicsWorld(this.dispatcher, this.broadphase, this.solver, this.collisionConfiguration);

		this.dynamicsWorld.setGravity(new Vector3f(0, -10, 0));

		// create VISUAL PHYSICS DEBUG RENDERER
		this.Debug = new TF3D_GLDebugDrawer();
		
		this.Debug.setDebugMode(DebugDrawModes.DRAW_WIREFRAME | DebugDrawModes.DRAW_AABB | DebugDrawModes.DRAW_CONTACT_POINTS);
		this.dynamicsWorld.setDebugDrawer(this.Debug);

		this.Reset();
	}

	// -----------------------------------------------------------------------
	// TF3D_Physics:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Help function to create rigidbody <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param mass
	 *            = Body mass
	 * @param startTransform
	 *            = stratup transformation
	 * @param shape
	 *            = collision shape
	 * @return RigidBody
	 */
	// -----------------------------------------------------------------------
	public RigidBody localCreateRigidBody(float mass, Transform startTransform, CollisionShape shape)
	{

		boolean isDynamic = (mass != 0f);

		Vector3f localInertia = new Vector3f(0f, 0f, 0f);
		if (isDynamic)
		{
			shape.calculateLocalInertia(mass, localInertia);
		}
		DefaultMotionState myMotionState = new DefaultMotionState(startTransform);

		RigidBodyConstructionInfo cInfo = new RigidBodyConstructionInfo(mass, myMotionState, shape, localInertia);

		RigidBody body = new RigidBody(cInfo);

		return body;
	}

	// -----------------------------------------------------------------------
	// TF3D_Physics:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Add rigiBody to dynamic world <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param body
	 */
	// -----------------------------------------------------------------------
	public void AddBody(RigidBody body)
	{
		dynamicsWorld.addRigidBody(body);
	}

	// -----------------------------------------------------------------------
	// TF3D_Physics:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Reset dynamicworld with all objects <BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	public void Reset()
	{
		// #ifdef SHOW_NUM_DEEP_PENETRATIONS
		BulletStats.gNumDeepPenetrationChecks = 0;
		BulletStats.gNumGjkChecks = 0;
		// #endif //SHOW_NUM_DEEP_PENETRATIONS

		int numObjects = 0;
		if (F3D.Physic.dynamicsWorld != null)
		{
			F3D.Physic.dynamicsWorld.stepSimulation(1f / 60f, 0);
			numObjects = F3D.Physic.dynamicsWorld.getNumCollisionObjects();
		}

		for (int i = 0; i < numObjects; i++)
		{
			CollisionObject colObj = dynamicsWorld.getCollisionObjectArray().getQuick(i);
			RigidBody body = RigidBody.upcast(colObj);
			if (body != null)
			{
				if (body.getMotionState() != null)
				{
					DefaultMotionState myMotionState = (DefaultMotionState) body.getMotionState();
					myMotionState.graphicsWorldTrans.set(myMotionState.startWorldTrans);
					colObj.setWorldTransform(myMotionState.graphicsWorldTrans);
					colObj.setInterpolationWorldTransform(myMotionState.startWorldTrans);
					colObj.activate();
				}
				// removed cached contact points
				dynamicsWorld.getBroadphase().getOverlappingPairCache().cleanProxyFromPairs(colObj.getBroadphaseHandle(), getDynamicsWorld().getDispatcher());

				body = RigidBody.upcast(colObj);
				if (body != null && !body.isStaticObject())
				{
					RigidBody.upcast(colObj).setLinearVelocity(new Vector3f(0f, 0f, 0f));
					RigidBody.upcast(colObj).setAngularVelocity(new Vector3f(0f, 0f, 0f));
				}
			}
		}

	}

	// -----------------------------------------------------------------------
	// TF3D_Physics:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Return current dynamicWorld <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @return = DynamicsWorld
	 */
	// -----------------------------------------------------------------------
	public DynamicsWorld getDynamicsWorld()
	{
		return dynamicsWorld;
	}

	// -----------------------------------------------------------------------
	// TF3D_Physics:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Update Simulation. <BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	public void Update()
	{
		// this.dynamicsWorld.stepSimulation(1/30.f,10);
		if (F3D.Timer.GetFPS()>0)
		{
			this.dynamicsWorld.stepSimulation(1.0f / (float)F3D.Timer.GetFPS(), 2);
		}
		//this.dynamicsWorld.stepSimulation(1.0f / 100f, 2);
	}

}
