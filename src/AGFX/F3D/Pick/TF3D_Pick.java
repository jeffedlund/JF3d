/**
 * 
 */
package AGFX.F3D.Pick;

import javax.vecmath.Vector3f;


import AGFX.F3D.F3D;

import com.bulletphysics.collision.dispatch.CollisionWorld;
import com.bulletphysics.dynamics.RigidBody;

/**
 * @author AndyGFX
 * 
 */
public class TF3D_Pick
{
	public CollisionWorld.ClosestRayResultCallback rayCallback;

	public TF3D_Pick()
	{
		F3D.Log.info("TF3D_Pick", "TF3D_Physics: constructor");
		F3D.Log.info("TF3D_Pick", "TF3D_Physics: done");
	}

	// -----------------------------------------------------------------------
	// TF3D_Pick:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Raycollision hit test with line defined with two points <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param fromA
	 *            = position from
	 * @param toB
	 *            = position to
	 * @return
	 */
	// -----------------------------------------------------------------------
	public RigidBody LineAB(Vector3f fromA, Vector3f toB)
	{

		this.rayCallback = new CollisionWorld.ClosestRayResultCallback(fromA, toB);
		F3D.Physic.dynamicsWorld.rayTest(fromA, toB, this.rayCallback);

		// when exist HIT
		if (rayCallback.hasHit())
		{
			// get what was touched
			RigidBody body = RigidBody.upcast(this.rayCallback.collisionObject);

			// when it's body, return pointer to TF3D_MODEL instance
			if (body != null)
			{
				return body;
			}
		} else
		{
			return null;
		}

		return null;
	}

	// -----------------------------------------------------------------------
	// TF3D_Pick:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Pick Body by camera center direction of view <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param length
	 *            - distance do raycast from actual position
	 * @return
	 */
	// -----------------------------------------------------------------------
	public RigidBody CameraDirection(float length)
	{

		Vector3f fromA = new Vector3f(F3D.Cameras.GetPosition());
		F3D.Cameras.items.get(F3D.Cameras.CurrentCameraID).UpdateAxisDirection();
		Vector3f toB = new Vector3f(F3D.Cameras.items.get(F3D.Cameras.CurrentCameraID).axis._forward);
		
		toB.scale(-length);
		toB.add(fromA);

		this.rayCallback = new CollisionWorld.ClosestRayResultCallback(fromA, toB);
		F3D.Physic.dynamicsWorld.rayTest(fromA, toB, this.rayCallback);

		// when exist HIT
		if (rayCallback.hasHit())
		{
			// get what was touched
			RigidBody body = RigidBody.upcast(this.rayCallback.collisionObject);

			// when it's body, return pointer to TF3D_MODEL instance
			if (body != null)
			{
				return body;
			}
		} else
		{
			return null;
		}

		return null;
	}

	
	public Vector3f getRayTo(int x, int y)
	{
		
		float top = 1f;
		float bottom = -1f;
		float nearPlane = 1f;
		float tanFov = (top - bottom) * 0.5f / nearPlane;
		float fov = (float) Math.atan(tanFov);
		
		fov = F3D.Config.r_display_fov * F3D.DEGTORAD;
		
		//F3D.Log.info("",String.valueOf(fov*F3D.RADTODEG));
		
		F3D.Cameras.items.get(F3D.Cameras.CurrentCameraID).UpdateAxisDirection();
		
		Vector3f rayFrom = new Vector3f(F3D.Cameras.GetPosition());
		Vector3f rayForward = new Vector3f(F3D.Cameras.items.get(F3D.Cameras.CurrentCameraID).axis._forward);
		
		
		float farPlane = 10000f;
		rayForward.scale(-farPlane);
		
		Vector3f vertical = new Vector3f(F3D.Cameras.items.get(F3D.Cameras.CurrentCameraID).axis._up);
		

		Vector3f hor = new Vector3f();
	
		hor.cross(rayForward, vertical);
		hor.normalize();
	
		vertical.cross(hor, rayForward);
		vertical.normalize();

		float tanfov = (float) Math.tan(0.5f * fov);
		
		float aspect = F3D.Config.r_display_height / (float)F3D.Config.r_display_width;
		
		hor.scale(2f * farPlane * tanfov);
		vertical.scale(2f * farPlane * tanfov);
		
		if (aspect < 1f) {
			hor.scale(1f / aspect);
		}
		else {
			vertical.scale(aspect);
		}
		
		Vector3f rayToCenter = new Vector3f();
		rayToCenter.add(rayFrom, rayForward);
		Vector3f dHor = new Vector3f(hor);
		dHor.scale(1f / (float) F3D.Config.r_display_width);
		Vector3f dVert = new Vector3f(vertical);
		dVert.scale(1.f / (float) F3D.Config.r_display_height);

		Vector3f tmp1 = new Vector3f();
		Vector3f tmp2 = new Vector3f();
		tmp1.scale(0.5f, hor);
		tmp2.scale(0.5f, vertical);

		Vector3f rayTo = new Vector3f();
		rayTo.sub(rayToCenter, tmp1);
		rayTo.add(tmp2);

		tmp1.scale(x, dHor);
		tmp2.scale((F3D.Config.r_display_height-y), dVert);

		rayTo.add(tmp1);
		rayTo.sub(tmp2);
		return rayTo; 
		
		
	}

	

	// -----------------------------------------------------------------------
	// TF3D_Pick:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Pick Body from mouse click position <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param mx
	 * @param my
	 * @return
	 */
	// -----------------------------------------------------------------------
	public RigidBody Mouse(int mx, int my)
	{

		
		Vector3f fromA = new Vector3f(F3D.Cameras.GetPosition());		
		Vector3f toB = new Vector3f(this.getRayTo(mx, my));

		
		
		F3D.Draw.Line3D(fromA, toB);

		this.rayCallback = new CollisionWorld.ClosestRayResultCallback(fromA, toB);
		F3D.Physic.dynamicsWorld.rayTest(fromA, toB, this.rayCallback);

		// when exist HIT
		if (rayCallback.hasHit())
		{
			// get what was touched
			RigidBody body = RigidBody.upcast(this.rayCallback.collisionObject);

			// when it's body, return pointer to TF3D_MODEL instance
			if (body != null)
			{
				return body;
			}
		} else
		{
			return null;
		}

		return null;
	}
}
