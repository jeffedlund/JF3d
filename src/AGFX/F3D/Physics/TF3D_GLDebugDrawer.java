package AGFX.F3D.Physics;

import AGFX.F3D.F3D;

import com.bulletphysics.linearmath.DebugDrawModes;
import com.bulletphysics.linearmath.IDebugDraw;
import javax.vecmath.Vector3f;

import org.lwjgl.opengl.GL11;

/**
 * 
 * @author jezek2
 */
public class TF3D_GLDebugDrawer extends IDebugDraw
{

	// JAVA NOTE: added
	private static final boolean DEBUG_NORMALS = false;

	private int                  debugMode;

	private final Vector3f       tmpVec        = new Vector3f();

	public TF3D_GLDebugDrawer()
	{
	}

	@Override
	public void draw3dText(Vector3f arg0, String arg1)
	{
	}

	@Override
	public void drawContactPoint(Vector3f pointOnB, Vector3f normalOnB, float distance, int lifeTime, Vector3f color)
	{
		if ((debugMode & DebugDrawModes.DRAW_CONTACT_POINTS) != 0)
		{
			Vector3f to = tmpVec;
			to.scaleAdd(distance * 100f, normalOnB, pointOnB);
			Vector3f from = pointOnB;

			// JAVA NOTE: added
			if (DEBUG_NORMALS)
			{
				to.normalize(normalOnB);
				to.scale(10f);
				to.add(pointOnB);
				GL11.glLineWidth(3f);
				GL11.glPointSize(6f);
				GL11.glBegin(GL11.GL_POINTS);
				GL11.glColor3f(color.x, color.y, color.z);
				GL11.glVertex3f(from.x, from.y, from.z);
				GL11.glEnd();
			}

			GL11.glBegin(GL11.GL_LINES);
			GL11.glColor3f(color.x, color.y, color.z);
			GL11.glVertex3f(from.x, from.y, from.z);
			GL11.glVertex3f(to.x, to.y, to.z);
			GL11.glEnd();

			// JAVA NOTE: added
			if (DEBUG_NORMALS)
			{
				GL11.glLineWidth(1f);
				GL11.glPointSize(1f);
			}

		}

	}

	
	public void drawLine(Vector3f from, Vector3f to, Vector3f color)
	{

		if (F3D.Config.use_gl_light)
		{
			GL11.glDisable(GL11.GL_LIGHTING);
		}
		
		if (this.debugMode > 0)
		{
			//GL11.glPushMatrix();
			GL11.glBegin(GL11.GL_LINES);
			GL11.glColor4f(color.x, color.y, color.z,1f);
			GL11.glVertex3f(from.x, from.y, from.z);
			GL11.glVertex3f(to.x, to.y, to.z);
			GL11.glEnd();
			
			/*
			GL11.glBegin(GL11.GL_POINTS);
			GL11.glPointSize( 5.0f );
			GL11.glColor4f(color.x, color.y, color.z,1f);
			GL11.glVertex3f(from.x, from.y, from.z);
			GL11.glEnd();
			*/
			//GL11.glPopMatrix();  
		}
		
		
		if (F3D.Config.use_gl_light)
		{
			GL11.glEnable(GL11.GL_LIGHTING);
		}
		
	}


	@Override
	public int getDebugMode()
	{
		return debugMode;
	}

	@Override
	public void reportErrorWarning(String arg0)
	{
		F3D.Log.warning("BULLET", arg0);
	}

	@Override
	public void setDebugMode(int arg0)
	{
		debugMode = arg0;
	}

}
