/**
 * 
 */
package AGFX.F3D.Helpers;

import javax.vecmath.Vector3f;

import AGFX.F3D.F3D;
import AGFX.F3D.Entity.TF3D_Entity;

import static org.lwjgl.opengl.GL11.*;

/**
 * @author AndyGFX
 * 
 */
public class TF3D_Helpers
{

	public TF3D_Helpers()
	{
		F3D.Log.info("TF3D_Helpers", "Create - constructor");
	}

	private void _light_off()
	{
		if (F3D.Config.use_gl_light)
		{
			glDisable(GL_LIGHTING);
		}
	}

	private void _light_on()
	{
		if (F3D.Config.use_gl_light)
		{
			glEnable(GL_LIGHTING);
		}
	}

	public void Rectangle(float X1, float Y1, float X2, float Y2, Boolean y_flip)
	{
		this._light_off();

		if (y_flip)
		{
			glBegin(GL_QUADS);

			glColor4f(1, 1, 1, 1);
			glTexCoord2f(0.0f, 0.0f);
			glVertex3f(X1, Y2, 0.0f);

			glColor4f(1, 1, 1, 1);
			glTexCoord2f(1.0f, 0.0f);
			glVertex3f(X2, Y2, 0.0f);

			glColor4f(1, 1, 1, 1);
			glTexCoord2f(1.0f, 1.0f);
			glVertex3f(X2, Y1, 0.0f);

			glColor4f(1, 1, 1, 1);
			glTexCoord2f(0.0f, 1.0f);
			glVertex3f(X1, Y1, 0.0f);

			glEnd();
		} else
		{
			glBegin(GL_QUADS);

			glColor4f(1, 1, 1, 1);
			glTexCoord2f(0.0f, 1.0f);
			glVertex3f(X1, Y2, 0.0f);

			glColor4f(1, 1, 1, 1);
			glTexCoord2f(1.0f, 1.0f);
			glVertex3f(X2, Y2, 0.0f);

			glColor4f(1, 1, 1, 1);
			glTexCoord2f(1.0f, 0.0f);
			glVertex3f(X2, Y1, 0.0f);

			glColor4f(1, 1, 1, 1);
			glTexCoord2f(0.0f, 0.0f);
			glVertex3f(X1, Y1, 0.0f);

			glEnd();
		}
		this._light_on();

	}

	public void QuadBySize(float X, float Y, float Wid, float Hgt, float Lev)
	{
		this._light_off();
		glBegin(GL_QUADS);
		glTexCoord2f(0.0f, 0.0f);
		glVertex3f(X, Y, -Lev);
		glTexCoord2f(1.0f, 0.0f);
		glVertex3f(X + Wid, Y, -Lev);
		glTexCoord2f(1.0f, 1.0f);
		glVertex3f(X + Wid, Y + Hgt, -Lev);
		glTexCoord2f(0.0f, 1.0f);
		glVertex3f(X, Y + Hgt, -Lev);
		glEnd();
		this._light_on();

	}

	public void QuadBySize(float X, float Y, float Wid, float Hgt, float offx, float offy, float Lev)
	{
		this._light_off();
		glBegin(GL_QUADS);
		glTexCoord2f(0.0f, 0.0f);
		glVertex3f(X + offx, Y + offy, -Lev);
		glTexCoord2f(1.0f, 0.0f);
		glVertex3f(X + offx + Wid, Y + offy, -Lev);
		glTexCoord2f(1.0f, 1.0f);
		glVertex3f(X + offx + Wid, Y + offy + Hgt, -Lev);
		glTexCoord2f(0.0f, 1.0f);
		glVertex3f(X + offx, Y + offy + Hgt, -Lev);
		glEnd();
		this._light_on();

	}

	public void QuadBySizeAndUV(float X, float Y, float Wid, float Hgt, float Lev, float Tu, float Tu2, float Tv, float Tv2)
	{
		this._light_off();
		glBegin(GL_QUADS);
		glTexCoord2f(Tu, Tv);
		glVertex3f(X, Y, -Lev);
		glTexCoord2f(Tu2, Tv);
		glVertex3f(X + Wid, Y, -Lev);
		glTexCoord2f(Tu2, Tv2);
		glVertex3f(X + Wid, Y + Hgt, -Lev);
		glTexCoord2f(Tu, Tv2);
		glVertex3f(X, Y + Hgt, -Lev);
		glEnd();
		this._light_on();

	}

	public void Line3D(Vector3f A, Vector3f B)
	{
		this._light_off();

		F3D.Textures.DeactivateLayers();
		glEnable(GL_DEPTH_TEST);
		glDisable(GL_BLEND);
		glPushMatrix();
		// glLoadIdentity();
		glBegin(GL_LINES);
		glVertex3f(A.x, A.y, A.z);
		glVertex3f(B.x, B.y, B.z);
		glEnd();
		glPopMatrix();

		this._light_on();
	}

	public void Line3D(float Ax, float Ay, float Az, float Bx, float By, float Bz)
	{
		this._light_off();
		glBegin(GL_LINES);
		glVertex3f(Ax, Ay, Az);
		glVertex3f(Bx, By, Bz);
		glEnd();
		this._light_on();

	}

	public void Axis(float size)
	{
		F3D.Textures.DeactivateLayers();
		// this._light_off();
		glPushMatrix();
		// X
		glColor4f(1, 0, 0, 1);
		this.Line3D(0, 0, 0, size, 0, 0);
		// Y
		glColor4f(0, 1, 0, 1);
		this.Line3D(0, 0, 0, 0, size, 0);
		// Z
		glColor4f(0, 0, 1, 1);
		this.Line3D(0, 0, 0, 0, 0, size);
		glPopMatrix();

		glColor3f(1, 1, 1);
	}

	public void Axis(TF3D_Entity ent, float length)
	{
		Vector3f a = new Vector3f(ent.GetPosition());

		ent.UpdateAxisDirection();
		
		glPushMatrix();
		// X
		glColor3f(1, 0, 0);
		Vector3f X = new Vector3f(ent.axis._right);
		X.scale(length);
		X.add(a);
		this.Line3D(a, X);
		// Y
		glColor3f(0, 1, 0);
		Vector3f Y = new Vector3f(ent.axis._up);
		Y.scale(length);
		Y.add(a);
		this.Line3D(a, Y);
		// Z
		glColor3f(0, 0, 1);
		Vector3f Z = new Vector3f(ent.axis._forward);
		Z.scale(length);
		Z.add(a);
		this.Line3D(a, Z);
		glPopMatrix();

		glColor3f(1, 1, 1);
	}
	
	public void Axis(Vector3f a, float length)
	{
		
		
		glPushMatrix();
		// X
		glColor3f(1, 0, 0);
		Vector3f X = new Vector3f(1,0,0);
		X.scale(length);
		X.add(a);
		this.Line3D(a, X);
		// Y
		glColor3f(0, 1, 0);
		Vector3f Y = new Vector3f(0,1,0);
		Y.scale(length);
		Y.add(a);
		this.Line3D(a, Y);
		// Z
		glColor3f(0, 0, 1);
		Vector3f Z = new Vector3f(0,0,1);
		Z.scale(length);
		Z.add(a);
		this.Line3D(a, Z);
		glPopMatrix();

		glColor3f(1, 1, 1);
	}
}
