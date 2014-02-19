/**
 * 
 */
package demos;

import javax.vecmath.Vector3f;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import AGFX.F3D.F3D;
import AGFX.F3D.AppWrapper.TF3D_AppWrapper;
import AGFX.F3D.Body.TF3D_Body;
import AGFX.F3D.Camera.TF3D_Camera;
import AGFX.F3D.Config.TF3D_Config;
import AGFX.F3D.Light.TF3D_Light;
import AGFX.F3D.Math.TF3D_Matrix;
import AGFX.F3D.Model.TF3D_Model;

/**
 * @author AndyGFX
 * 
 */
public class Demo_AxisTest extends TF3D_AppWrapper
{

	public TF3D_Camera	Camera;
	public TF3D_Model	model;
	public TF3D_Body	body;
	public Vector3f		pos2D;
	public TF3D_Matrix	mat;
	public TF3D_Matrix	tmat;
	public Vector3f		vec;

	int					odx	= 0;
	int					ody	= 0;

	public Demo_AxisTest()
	{

	}

	@Override
	public void onConfigure()
	{
		try
		{

			// Redefine Config
			F3D.Config = new TF3D_Config();

			F3D.Config.r_display_width = 800;
			F3D.Config.r_display_height = 600;
			F3D.Config.r_fullscreen = false;
			F3D.Config.r_display_vsync = false;
			F3D.Config.r_display_title = "jFinal3D Graphics Engine 2010 - CAMERA CONTROL";
			F3D.Config.use_physics_debug = false;

			super.onConfigure();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void onInitialize()
	{

		F3D.Worlds.CreateWorld("MAIN_WORLD");

		this.Camera = new TF3D_Camera("FPSCamera");
		this.Camera.SetPosition(0.0f, .0f, 10.0f);
		this.Camera.SetRotation(0, 0f, 0);
		this.Camera.ctype = F3D.CAMERA_TYPE_FPS;

		F3D.Cameras.Add(this.Camera);
		F3D.Worlds.SetCamera(this.Camera);

		// Add light to scene
		TF3D_Light light = new TF3D_Light("light_0", 0);
		light.SetPosition(3, 3, 3);
		light.Enable();

		F3D.Meshes.Add("abstract::jeep.a3da");
		F3D.Meshes.Add("abstract::Cube.a3da");

		this.model = new TF3D_Model("AUTO");
		this.model.AssignMesh("abstract::jeep.a3da");
		this.model.SetPosition(3, 0, 0);
		this.model.SetRotation(0, 90, 0);
		
		
		
		this.body = new TF3D_Body("PCube");
		this.body.AssignMesh("abstract::Cube.a3da");
		this.body.Enable();
		this.body.SetPosition(-3, 0f, 0);
		this.body.SetRotation(0f, 15f, 0f);
		this.body.CreateRigidBody(F3D.BULLET_SHAPE_BOX, 0.0f);
		
		
		this.mat = new TF3D_Matrix();
		this.vec = new Vector3f(0,0,1);
		
		this.tmat = new TF3D_Matrix();
		
		tmat.CreateTranslationMatrix(0,0,0);
		
		mat.CreateRotationMatrix(0,90,0);
		this.vec = mat.RotateVector(0, 0, 1);
		tmat.Multiply(mat);
		
		F3D.Log.info("ROT", mat.toString());
		
		Vector3f res = new Vector3f(mat.RotateVector(vec));
		
		F3D.Log.info("POS", vec.toString());
		F3D.Log.info("ROT", res.toString());
		F3D.Log.info("ROT", this.model.axis._forward.toString());
		
		

		
	}

	@Override
	public void onUpdate3D()
	{

		if (Mouse.isInsideWindow())
		{
    		if (Mouse.isButtonDown(0))
    		{
    			float dx = (float)Mouse.getDX()/10.0f;
    			float dy = (float)Mouse.getDY()/10.0f;
    			
    			this.Camera.Turn( -dy, dx, 0.0f);
    		
    			if (Mouse.getX()<3) {Mouse.setCursorPosition(F3D.Config.r_display_width-4,Mouse.getY());}
        		if (Mouse.getX()>F3D.Config.r_display_width-3) {Mouse.setCursorPosition(4,Mouse.getY());}
        		if (Mouse.getY()<3) {Mouse.setCursorPosition(Mouse.getX(),F3D.Config.r_display_height-4);}
        		if (Mouse.getY()>F3D.Config.r_display_height-3) {Mouse.setCursorPosition(Mouse.getX(),4);}
    		}
		}
		
		
		if (Keyboard.isKeyDown(Keyboard.KEY_W))
		{
			this.Camera.Move(0.0f, 0.0f, -0.05f);
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_A))
		{
			this.Camera.Move(-0.05f, 0.0f, 0.0f);
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_S))
		{
			this.Camera.Move(0.0f, 0.0f, 0.05f);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D))
		{
			this.Camera.Move(0.05f, 0.0f, 0.0f);
		}
	
		//this.model.Turn(1f * F3D.Timer.AppSpeed(), 1f * F3D.Timer.AppSpeed(),0);
		F3D.Draw.Axis(1f);
		F3D.Draw.Axis(this.model, 3);
		
		F3D.Draw.Axis(this.body, 3);

		
	}

	@Override
	public void onUpdate2D()
	{

		F3D.Viewport.DrawInfo(0, 0);
		this.model.DrawInfoAt(0, 100);
		this.Camera.DrawInfoAt(0, 220);
	}

	@Override
	public void OnDestroy()
	{

	}

	public static void main(String[] args)
	{
		new Demo_AxisTest().Execute();
		System.exit(0);
	}

}
