/**
 * 
 */
package AGFX.F3D.Skybox;


import static org.lwjgl.opengl.GL11.*;
import javax.vecmath.Vector3f;
import AGFX.F3D.F3D;
import AGFX.F3D.Model.TF3D_Model;


/**
 * @author AndyGFX
 *
 */
public class TF3D_Skybox
{	
	private TF3D_Model 	SkyGradient;

	public TF3D_Skybox()
	{
		F3D.Meshes.Add("abstract::Sky_sphere.a3da");
		this.SkyGradient = new TF3D_Model("SKyGradient");
		this.SkyGradient.AssignMesh("abstract::Sky_sphere.a3da");
	}
	
	public void Render(Vector3f pos)
	{
		glPushMatrix();		
		this.SkyGradient.SetPosition(pos);
		this.SkyGradient.Render();
		glEnable(GL_DEPTH_TEST);
		glPopMatrix();

	}
}
