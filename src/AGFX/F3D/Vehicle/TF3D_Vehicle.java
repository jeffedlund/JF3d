/**
 * 
 */
package AGFX.F3D.Vehicle;

import static org.lwjgl.opengl.GL11.glMultMatrix;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glScalef;
import java.nio.FloatBuffer;
import javax.vecmath.Vector3f;
import org.lwjgl.BufferUtils;
import com.bulletphysics.collision.dispatch.CollisionObject;
import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.collision.shapes.CompoundShape;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.vehicle.DefaultVehicleRaycaster;
import com.bulletphysics.dynamics.vehicle.RaycastVehicle;
import com.bulletphysics.dynamics.vehicle.VehicleRaycaster;
import com.bulletphysics.dynamics.vehicle.VehicleTuning;
import com.bulletphysics.dynamics.vehicle.WheelInfo;
import com.bulletphysics.linearmath.Transform;
import AGFX.F3D.F3D;
import AGFX.F3D.Entity.TF3D_Entity;
import AGFX.F3D.Mesh.TF3D_BoundingBox;

/**
 * @author AndyGFX
 * 
 */
public class TF3D_Vehicle extends TF3D_Entity
{

	public float			gEngineForce					= 0.f;
	public float			gBreakingForce					= 0.f;
	public float			maxEngineForce					= 300.f;
	public float			maxBreakingForce				= 100.f;
	public float			gVehicleSteering				= 0.f;
	public float			steeringIncrement				= 0.04f;
	public float			steeringClamp					= 0.5f;
	private float			wheelRadius						= 0.5f;
	// private float wheelWidth = 0.4f;
	private float			wheelFriction					= 100;
	private float			suspensionStiffness				= 20.f;
	private float			suspensionDamping				= 2.3f;
	private float			suspensionCompression			= 4.4f;
	private float			rollInfluence					= 0.1f;

	private float			suspensionRestLength			= 0.7f;

	// assigned vehicle models
	public int				model_wheel_FL					= -1;
	public int				material_wheel_FL				= -1;
	public float[]			wheel_FL_transformMatrix		= new float[16];
	public FloatBuffer		wheel_FL_transformMatrixBuffer	= BufferUtils.createFloatBuffer(16);

	public int				model_wheel_FR					= -1;
	public int				material_wheel_FR				= -1;
	public float[]			wheel_FR_transformMatrix		= new float[16];
	public FloatBuffer		wheel_FR_transformMatrixBuffer	= BufferUtils.createFloatBuffer(16);

	public int				model_wheel_BL					= -1;
	public int				material_wheel_BL				= -1;
	public float[]			wheel_BL_transformMatrix		= new float[16];
	public FloatBuffer		wheel_BL_transformMatrixBuffer	= BufferUtils.createFloatBuffer(16);

	public int				model_wheel_BR					= -1;
	public int				material_wheel_BR				= -1;
	public float[]			wheel_BR_transformMatrix		= new float[16];
	public FloatBuffer		wheel_BR_transformMatrixBuffer	= BufferUtils.createFloatBuffer(16);

	public int				model_chassis					= -1;
	public int				material_chassis				= -1;
	public float[]			chassis_transformMatrix			= new float[16];
	public FloatBuffer		chassis_transformMatrixBuffer	= BufferUtils.createFloatBuffer(16);

	// Physics vehicle definition
	public RigidBody		RB_carChassis;
	public Transform		RigidBody_tr					= new Transform();
	public VehicleTuning	tuning							= new VehicleTuning();
	public VehicleRaycaster	vehicleRayCaster;
	public RaycastVehicle	vehicle;

	// -----------------------------------------------------------------------
	// TF3D_Vehicle:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Constructor <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param name
	 *            - name of vehicle
	 */
	// -----------------------------------------------------------------------
	public TF3D_Vehicle(String name)
	{
		this.classname = F3D.CLASS_VEHICLE;
		F3D.Log.info("TF3D_Vehicle", "TF3D_Vehicle: constructor");
		this.name = name;
		F3D.Log.info("TF3D_Vehicle", "TF3D_Vehicle: done");
	}

	// -----------------------------------------------------------------------
	// TF3D_Vehicle:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Create complex vehicle <BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	public void Create()
	{

		RigidBody_tr.setIdentity();

		// Shape of Chassis

		TF3D_BoundingBox chassis_bbox = new TF3D_BoundingBox();
		chassis_bbox.CalcFromMesh(this.model_chassis);
		chassis_bbox.size.scale(0.5f);

		CollisionShape chassisShape = new BoxShape(chassis_bbox.size);

		CompoundShape compound = new CompoundShape();

		Transform localTrans = new Transform();
		localTrans.setIdentity();

		compound.addChildShape(localTrans, chassisShape);

		RigidBody_tr.origin.set(this.GetPosition());
		RigidBody_tr.setRotation(F3D.MathUtils.AnglesToQuat4f(this.GetRotation().x, this.GetRotation().y, this.GetRotation().z));

		RB_carChassis = F3D.Physic.localCreateRigidBody(1200, RigidBody_tr, compound);
		F3D.Physic.AddBody(RB_carChassis);

		// create vehicle
		{
			this.vehicleRayCaster = new DefaultVehicleRaycaster(F3D.Physic.dynamicsWorld);
			this.vehicle = new RaycastVehicle(tuning, RB_carChassis, vehicleRayCaster);

			// never deactivate the vehicle
			RB_carChassis.setActivationState(CollisionObject.DISABLE_DEACTIVATION);

			F3D.Physic.dynamicsWorld.addVehicle(vehicle);

			float connectionWidth = 1.15f;
			float connectionHeight = -0.15f;
			float connectionLength = 1.40f;

			boolean isFrontWheel = true;

			// choose coordinate system
			vehicle.setCoordinateSystem(0, 1, 2);

			Vector3f wheelDirectionCS0 = new Vector3f(0, -1, 0);
			Vector3f wheelAxleCS = new Vector3f(-1, 0, 0);

			// WHEEL FL
			Vector3f connectionPointCS0 = new Vector3f(-connectionWidth, connectionHeight, connectionLength);
			vehicle.addWheel(connectionPointCS0, wheelDirectionCS0, wheelAxleCS, suspensionRestLength, wheelRadius, tuning, isFrontWheel);

			// WHEEL FR
			connectionPointCS0 = new Vector3f(connectionWidth, connectionHeight, connectionLength);
			vehicle.addWheel(connectionPointCS0, wheelDirectionCS0, wheelAxleCS, suspensionRestLength, wheelRadius, tuning, isFrontWheel);

			// WHEEL BL
			connectionPointCS0 = new Vector3f(-connectionWidth, connectionHeight, -connectionLength);
			vehicle.addWheel(connectionPointCS0, wheelDirectionCS0, wheelAxleCS, suspensionRestLength, wheelRadius, tuning, isFrontWheel);

			// WHEEL BR
			connectionPointCS0 = new Vector3f(connectionWidth, connectionHeight, -connectionLength);
			vehicle.addWheel(connectionPointCS0, wheelDirectionCS0, wheelAxleCS, suspensionRestLength, wheelRadius, tuning, isFrontWheel);

			for (int i = 0; i < vehicle.getNumWheels(); i++)
			{
				WheelInfo wheel = vehicle.getWheelInfo(i);
				wheel.suspensionStiffness = suspensionStiffness;
				wheel.wheelsDampingRelaxation = suspensionDamping;
				wheel.wheelsDampingCompression = suspensionCompression;
				wheel.frictionSlip = wheelFriction;
				wheel.rollInfluence = rollInfluence;
			}

		}
	}

	// -----------------------------------------------------------------------
	// TF3D_Vehicle:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Destroy vehicle rigidbody <BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	@Override
	public void Destroy()
	{
		this.RB_carChassis.destroy();
	}

	// -----------------------------------------------------------------------
	// TF3D_Vehicle:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * REnder vehicle <BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	@Override
	public void Render()
	{

		if (this.model_wheel_FL >= 0)
		{

			int mid;
			if (this.material_wheel_FL!=-1)
			{
				mid = this.material_wheel_FL;
			}
			else
			{
				mid = F3D.Meshes.items.get(this.model_wheel_FL).data.material_id;
			}
			
			if (mid >= 0)
			{
				F3D.Surfaces.ApplyMaterial(mid);
			}

			glPushMatrix();
			glMultMatrix(this.wheel_FL_transformMatrixBuffer);
			glScalef(this.GetScale().x, this.GetScale().y, this.GetScale().z);
			F3D.Meshes.items.get(this.model_wheel_FL).Render();
			glScalef(1, 1, 1);
			glPopMatrix();
		}

		if (this.model_wheel_FR >= 0)
		{

			int mid;
			if (this.material_wheel_FR!=-1)
			{
				mid = this.material_wheel_FR;
			}
			else
			{
				mid = F3D.Meshes.items.get(this.model_wheel_FR).data.material_id;
			}
			if (mid >= 0)
			{
				F3D.Surfaces.ApplyMaterial(mid);
			}

			glPushMatrix();
			glMultMatrix(this.wheel_FR_transformMatrixBuffer);
			glScalef(this.GetScale().x, this.GetScale().y, this.GetScale().z);
			F3D.Meshes.items.get(this.model_wheel_FR).Render();
			glScalef(1, 1, 1);
			glPopMatrix();
		}

		if (this.model_wheel_BL >= 0)
		{

			int mid;
			if (this.material_wheel_BL!=-1)
			{
				mid = this.material_wheel_BL;
			}
			else
			{
				mid = F3D.Meshes.items.get(this.model_wheel_BL).data.material_id;
			}
			
			if (mid >= 0)
			{
				F3D.Surfaces.ApplyMaterial(mid);
			}

			glPushMatrix();
			glMultMatrix(this.wheel_BL_transformMatrixBuffer);
			glScalef(this.GetScale().x, this.GetScale().y, this.GetScale().z);
			F3D.Meshes.items.get(this.model_wheel_BL).Render();
			glScalef(1, 1, 1);
			glPopMatrix();
		}

		if (this.model_wheel_BR >= 0)
		{

			int mid;
			
			if (this.material_wheel_BR!=-1)
			{
				mid = this.material_wheel_BR;
			}
			else
			{
				mid = F3D.Meshes.items.get(this.model_wheel_BR).data.material_id;
			}
			if (mid >= 0)
			{
				F3D.Surfaces.ApplyMaterial(mid);
			}

			glPushMatrix();
			glMultMatrix(this.wheel_BR_transformMatrixBuffer);
			glScalef(this.GetScale().x, this.GetScale().y, this.GetScale().z);
			F3D.Meshes.items.get(this.model_wheel_BR).Render();
			glScalef(1, 1, 1);
			glPopMatrix();
		}

		if (this.model_chassis >= 0)
		{

			int mid;
			if (this.material_chassis!=-1)
			{
				mid = this.material_chassis;
			}
			else
			{
				mid = F3D.Meshes.items.get(this.model_chassis).data.material_id;
			}
			if (mid >= 0)
			{
				F3D.Surfaces.ApplyMaterial(mid);
			}

			glPushMatrix();
			glMultMatrix(this.chassis_transformMatrixBuffer);
			glScalef(this.GetScale().x, this.GetScale().y, this.GetScale().z);
			F3D.Meshes.items.get(this.model_chassis).Render();
			glScalef(1, 1, 1);
			glPopMatrix();
		}
	}

	// -----------------------------------------------------------------------
	// TF3D_Vehicle:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Update vehicle physics <BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	@Override
	public void Update()
	{

		int wheelIndex = 2;
		vehicle.applyEngineForce(gEngineForce, wheelIndex);
		vehicle.setBrake(gBreakingForce, wheelIndex);
		wheelIndex = 3;
		vehicle.applyEngineForce(gEngineForce, wheelIndex);
		vehicle.setBrake(gBreakingForce, wheelIndex);

		wheelIndex = 0;
		vehicle.setSteeringValue(gVehicleSteering, wheelIndex);
		wheelIndex = 1;
		vehicle.setSteeringValue(gVehicleSteering, wheelIndex);

		// update model transformation
		Transform ch_tr = new Transform();

		this.RB_carChassis.getMotionState().getWorldTransform(ch_tr);

		// get real position
		this.SetPosition(ch_tr.origin);

		ch_tr.getOpenGLMatrix(this.chassis_transformMatrix);

		this.chassis_transformMatrixBuffer.put(this.chassis_transformMatrix);
		this.chassis_transformMatrixBuffer.rewind();

		// Wheel FL
		int i = 0;
		vehicle.updateWheelTransform(i, true);
		Transform trans = vehicle.getWheelInfo(i).worldTransform;
		trans.getOpenGLMatrix(this.wheel_FL_transformMatrix);
		this.wheel_FL_transformMatrixBuffer.put(this.wheel_FL_transformMatrix);
		this.wheel_FL_transformMatrixBuffer.rewind();

		// Wheel FR
		i = 1;
		vehicle.updateWheelTransform(i, true);
		trans = vehicle.getWheelInfo(i).worldTransform;
		trans.getOpenGLMatrix(this.wheel_FR_transformMatrix);
		this.wheel_FR_transformMatrixBuffer.put(this.wheel_FR_transformMatrix);
		this.wheel_FR_transformMatrixBuffer.rewind();

		// Wheel BL
		i = 2;
		vehicle.updateWheelTransform(i, true);
		trans = vehicle.getWheelInfo(i).worldTransform;
		trans.getOpenGLMatrix(this.wheel_BL_transformMatrix);
		this.wheel_BL_transformMatrixBuffer.put(this.wheel_BL_transformMatrix);
		this.wheel_BL_transformMatrixBuffer.rewind();

		// Wheel BR
		i = 3;
		vehicle.updateWheelTransform(i, true);
		trans = vehicle.getWheelInfo(i).worldTransform;
		trans.getOpenGLMatrix(this.wheel_BR_transformMatrix);
		this.wheel_BR_transformMatrixBuffer.put(this.wheel_BR_transformMatrix);
		this.wheel_BR_transformMatrixBuffer.rewind();

	}

	@Override
	public void UpdateAxisDirection()
	{
		// update axis by GL Matrix

		this.axis._right.set(this.chassis_transformMatrix[0], this.chassis_transformMatrix[1], this.chassis_transformMatrix[2]);
		this.axis._up.set(this.chassis_transformMatrix[4], this.chassis_transformMatrix[5], this.chassis_transformMatrix[6]);
		this.axis._forward.set(this.chassis_transformMatrix[8], this.chassis_transformMatrix[9], this.chassis_transformMatrix[10]);

	}

	// -----------------------------------------------------------------------
	// TF3D_Vehicle:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Reset vehicle physics <BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	public void Reset()
	{
		gVehicleSteering = 0f;
		Transform tr = new Transform();
		tr.setIdentity();
		tr.origin.set(new Vector3f(0, 0, 5f));

		this.RB_carChassis.setCenterOfMassTransform(tr);
		this.RB_carChassis.setWorldTransform(RigidBody_tr);
		this.RB_carChassis.setLinearVelocity(new Vector3f(0, 0, 0));
		this.RB_carChassis.setAngularVelocity(new Vector3f(0, 0, 0));
		F3D.Physic.dynamicsWorld.getBroadphase().getOverlappingPairCache().cleanProxyFromPairs(this.RB_carChassis.getBroadphaseHandle(), F3D.Physic.getDynamicsWorld().getDispatcher());
		if (vehicle != null)
		{
			vehicle.resetSuspension();
			for (int i = 0; i < vehicle.getNumWheels(); i++)
			{
				vehicle.updateWheelTransform(i, true);
			}
		}
	}
}
