/**
 * 
 */
package AGFX.F3D.Math;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.BufferUtils;
import javax.vecmath.Vector3f;



/**
 * @author AndyGFX
 * 
 */
public class TF3D_Frustum
{
	private float[][]   FFrustum;
	private float[]     Clip;
	private float[]     Proj;
	private float[]     Modl;

	// projection matrix
	//private ByteBuffer  projBuffer;
	public FloatBuffer projMatrix;

	// modelview matrix
	//private ByteBuffer  modelBuffer;
	public FloatBuffer modelMatrix;

	public TF3D_Frustum()
	{
		this.Clip = new float[16];
		this.Proj = new float[16];
		this.Modl = new float[16];
		this.FFrustum = new float[6][4];

	//	this.projBuffer = ByteBuffer.allocateDirect(64);
	//	this.modelBuffer = ByteBuffer.allocateDirect(64);
		
		this.modelMatrix = BufferUtils.createFloatBuffer(16);
		this.projMatrix = BufferUtils.createFloatBuffer(16);
	}

	// -----------------------------------------------------------------------
	// TA3D_Frustum:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Calculate frustum clling - is called automaticaly from A3D package, when
	 * camera is created and updated in main loop <BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	public void Update()
	{
		// Get the current PROJECTION matrix from OpenGL
		  glGetFloat( GL_PROJECTION_MATRIX, this.projMatrix );

		  for (int m = 0; m < 16; m++)
			{
				this.Proj[m] = this.projMatrix.get(m);
			}
		  
		// Get the current MODELVIEW matrix from OpenGL
		  glGetFloat( GL_MODELVIEW_MATRIX, this.modelMatrix );

		  for (int m = 0; m < 16; m++)
			{
				this.Modl[m] = this.modelMatrix.get(m);

			}
		
		  
		// Combine the two matrices (multiply Projection by modelview) */
		Clip[0] = Modl[0] * Proj[0] + Modl[1] * Proj[4] + Modl[2] * Proj[8] + Modl[3] * Proj[12];
		Clip[1] = Modl[0] * Proj[1] + Modl[1] * Proj[5] + Modl[2] * Proj[9] + Modl[3] * Proj[13];
		Clip[2] = Modl[0] * Proj[2] + Modl[1] * Proj[6] + Modl[2] * Proj[10] + Modl[3] * Proj[14];
		Clip[3] = Modl[0] * Proj[3] + Modl[1] * Proj[7] + Modl[2] * Proj[11] + Modl[3] * Proj[15];

		Clip[4] = Modl[4] * Proj[0] + Modl[5] * Proj[4] + Modl[6] * Proj[8] + Modl[7] * Proj[12];
		Clip[5] = Modl[4] * Proj[1] + Modl[5] * Proj[5] + Modl[6] * Proj[9] + Modl[7] * Proj[13];
		Clip[6] = Modl[4] * Proj[2] + Modl[5] * Proj[6] + Modl[6] * Proj[10] + Modl[7] * Proj[14];
		Clip[7] = Modl[4] * Proj[3] + Modl[5] * Proj[7] + Modl[6] * Proj[11] + Modl[7] * Proj[15];

		Clip[8] = Modl[8] * Proj[0] + Modl[9] * Proj[4] + Modl[10] * Proj[8] + Modl[11] * Proj[12];
		Clip[9] = Modl[8] * Proj[1] + Modl[9] * Proj[5] + Modl[10] * Proj[9] + Modl[11] * Proj[13];
		Clip[10] = Modl[8] * Proj[2] + Modl[9] * Proj[6] + Modl[10] * Proj[10] + Modl[11] * Proj[14];
		Clip[11] = Modl[8] * Proj[3] + Modl[9] * Proj[7] + Modl[10] * Proj[11] + Modl[11] * Proj[15];

		Clip[12] = Modl[12] * Proj[0] + Modl[13] * Proj[4] + Modl[14] * Proj[8] + Modl[15] * Proj[12];
		Clip[13] = Modl[12] * Proj[1] + Modl[13] * Proj[5] + Modl[14] * Proj[9] + Modl[15] * Proj[13];
		Clip[14] = Modl[12] * Proj[2] + Modl[13] * Proj[6] + Modl[14] * Proj[10] + Modl[15] * Proj[14];
		Clip[15] = Modl[12] * Proj[3] + Modl[13] * Proj[7] + Modl[14] * Proj[11] + Modl[15] * Proj[15];

		float t;

		// Extract the numbers for the RIGHT plane
		FFrustum[0][0] = Clip[3] - Clip[0];
		FFrustum[0][1] = Clip[7] - Clip[4];
		FFrustum[0][2] = Clip[11] - Clip[8];
		FFrustum[0][3] = Clip[15] - Clip[12];

		// Normalize the result
		t = (float) Math.sqrt(FFrustum[0][0] * FFrustum[0][0] + FFrustum[0][1] * FFrustum[0][1] + FFrustum[0][2] * FFrustum[0][2]);
		FFrustum[0][0] = FFrustum[0][0] / t;
		FFrustum[0][1] = FFrustum[0][1] / t;
		FFrustum[0][2] = FFrustum[0][2] / t;
		FFrustum[0][3] = FFrustum[0][3] / t;

		// Extract the numbers for the LEFT plane
		FFrustum[1][0] = Clip[3] + Clip[0];
		FFrustum[1][1] = Clip[7] + Clip[4];
		FFrustum[1][2] = Clip[11] + Clip[8];
		FFrustum[1][3] = Clip[15] + Clip[12];

		// Normalize the result
		t = (float) Math.sqrt(FFrustum[1][0] * FFrustum[1][0] + FFrustum[1][1] * FFrustum[1][1] + FFrustum[1][2] * FFrustum[1][2]);
		FFrustum[1][0] = FFrustum[1][0] / t;
		FFrustum[1][1] = FFrustum[1][1] / t;
		FFrustum[1][2] = FFrustum[1][2] / t;
		FFrustum[1][3] = FFrustum[1][3] / t;

		// Extract the BOTTOM plane
		FFrustum[2][0] = Clip[3] + Clip[1];
		FFrustum[2][1] = Clip[7] + Clip[5];
		FFrustum[2][2] = Clip[11] + Clip[9];
		FFrustum[2][3] = Clip[15] + Clip[13];

		// Normalize the result
		t = (float) Math.sqrt(FFrustum[2][0] * FFrustum[2][0] + FFrustum[2][1] * FFrustum[2][1] + FFrustum[2][2] * FFrustum[2][2]);
		FFrustum[2][0] = FFrustum[2][0] / t;
		FFrustum[2][1] = FFrustum[2][1] / t;
		FFrustum[2][2] = FFrustum[2][2] / t;
		FFrustum[2][3] = FFrustum[2][3] / t;

		// Extract the TOP plane
		FFrustum[3][0] = Clip[3] - Clip[1];
		FFrustum[3][1] = Clip[7] - Clip[5];
		FFrustum[3][2] = Clip[11] - Clip[9];
		FFrustum[3][3] = Clip[15] - Clip[13];

		// Normalize the result
		t = (float) Math.sqrt(FFrustum[3][0] * FFrustum[3][0] + FFrustum[3][1] * FFrustum[3][1] + FFrustum[3][2] * FFrustum[3][2]);
		FFrustum[3][0] = FFrustum[3][0] / t;
		FFrustum[3][1] = FFrustum[3][1] / t;
		FFrustum[3][2] = FFrustum[3][2] / t;
		FFrustum[3][3] = FFrustum[3][3] / t;

		// Extract the FAR plane
		FFrustum[4][0] = Clip[3] - Clip[2];
		FFrustum[4][1] = Clip[7] - Clip[6];
		FFrustum[4][2] = Clip[11] - Clip[10];
		FFrustum[4][3] = Clip[15] - Clip[14];

		// Normalize the result
		t = (float) Math.sqrt(FFrustum[4][0] * FFrustum[4][0] + FFrustum[4][1] * FFrustum[4][1] + FFrustum[4][2] * FFrustum[4][2]);
		FFrustum[4][0] = FFrustum[4][0] / t;
		FFrustum[4][1] = FFrustum[4][1] / t;
		FFrustum[4][2] = FFrustum[4][2] / t;
		FFrustum[4][3] = FFrustum[4][3] / t;

		// Extract the NEAR plane
		FFrustum[5][0] = Clip[3] + Clip[2];
		FFrustum[5][1] = Clip[7] + Clip[6];
		FFrustum[5][2] = Clip[11] + Clip[10];
		FFrustum[5][3] = Clip[15] + Clip[14];

		// Normalize the result
		t = (float) Math.sqrt(FFrustum[5][0] * FFrustum[5][0] + FFrustum[5][1] * FFrustum[5][1] + FFrustum[5][2] * FFrustum[5][2]);
		FFrustum[5][0] = FFrustum[5][0] / t;
		FFrustum[5][1] = FFrustum[5][1] / t;
		FFrustum[5][2] = FFrustum[5][2] / t;
		FFrustum[5][3] = FFrustum[5][3] / t;

	}

	// -----------------------------------------------------------------------
	// TA3D_Frustum:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * check if point is in frustum <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param Point
	 *            point position
	 * @return true/false
	 */
	// -----------------------------------------------------------------------
	public boolean PointInFrustum(Vector3f Point)
	{
		for (int i = 0; i < 6; i++)
		{
			if (FFrustum[i][0] * Point.x + FFrustum[i][1] * Point.y + FFrustum[i][2] * Point.z + FFrustum[i][3] <= 0)
			{
				return false;
			}
		}
		return true;
	}

	// -----------------------------------------------------------------------
	// TA3D_Frustum:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * check if sphere is in frustum <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param Center
	 *            cemter od sphere
	 * @param Radius
	 *            radius of sphere
	 * @return true/false
	 */
	// -----------------------------------------------------------------------
	public boolean SphereInFrustum(Vector3f Center, float Radius)
	{
		for (int i = 0; i < 6; i++)
		{
			if (FFrustum[i][0] * Center.x + FFrustum[i][1] * Center.y + FFrustum[i][2] * Center.z + FFrustum[i][3] <= -Radius)
			{
				return false;
			}
		}
		return true;
	}

	// -----------------------------------------------------------------------
	// TA3D_Frustum:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Check if Box defined by size and position is in frustum <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param Center
	 *            center of BBOX
	 * @param Size
	 *            - size sx/sy/sz
	 * @return true/false
	 */
	// -----------------------------------------------------------------------
	public boolean BoxInFrustum(Vector3f Center, Vector3f Size)
	{
		float x1 = 0, y1 = 0, x2 = 0, y2 = 0, z1 = 0, z2 = 0;

		  x1= Center.x - Size.x;
		  x2= Center.x + Size.x;
		  y1= Center.y - Size.y;
		  y2= Center.y + Size.y;
		  z1= Center.z - Size.z;
		  z2= Center.z + Size.z;
		
		for (int i = 0; i < 6; i++)
		{
			if (FFrustum[i][0] * x1 + FFrustum[i][1] * y1 + FFrustum[i][2] * z1 + FFrustum[i][3] > 0)
			{
				continue;
			}
			if (FFrustum[i][0] * x1 + FFrustum[i][1] * y1 + FFrustum[i][2] * z2 + FFrustum[i][3] > 0)
			{
				continue;
			}
			if (FFrustum[i][0] * x1 + FFrustum[i][1] * y2 + FFrustum[i][2] * z2 + FFrustum[i][3] > 0)
			{
				continue;
			}
			if (FFrustum[i][0] * x2 + FFrustum[i][1] * y2 + FFrustum[i][2] * z2 + FFrustum[i][3] > 0)
			{
				continue;
			}
			if (FFrustum[i][0] * x2 + FFrustum[i][1] * y2 + FFrustum[i][2] * z1 + FFrustum[i][3] > 0)
			{
				continue;
			}
			if (FFrustum[i][0] * x2 + FFrustum[i][1] * y1 + FFrustum[i][2] * z1 + FFrustum[i][3] > 0)
			{
				continue;
			}
			if (FFrustum[i][0] * x2 + FFrustum[i][1] * y1 + FFrustum[i][2] * z2 + FFrustum[i][3] > 0)
			{
				continue;
			}
			if (FFrustum[i][0] * x1 + FFrustum[i][1] * y2 + FFrustum[i][2] * z1 + FFrustum[i][3] > 0)
			{
				continue;
			}
			return false;
		}
		return true;
	}

}
