/**
 * 
 */
package demos;


import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;



import AGFX.F3D.F3D;
import AGFX.F3D.AppWrapper.TF3D_AppWrapper;
import AGFX.F3D.Body.TF3D_Body;
import AGFX.F3D.Camera.TF3D_Camera;
import AGFX.F3D.Config.TF3D_Config;
import AGFX.F3D.Light.TF3D_Light;
import AGFX.F3D.Model.TF3D_Model;



/**
 * @author AndyGFX
 *
 */
public class Demo_BasicPhysic extends TF3D_AppWrapper
{

	public TF3D_Camera Camera;
	public TF3D_Body	Pbody1;
	public TF3D_Body	Pbody2;
	public TF3D_Body	Pbody3;
	public TF3D_Body	PPlane;
	public TF3D_Model	Model;
	
	
	public Demo_BasicPhysic()
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
			F3D.Config.r_display_title = "jFinal3D Graphics Engine 2010 - PHYSICS TEST";

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
		this.Camera.SetPosition(0.0f, 10.0f, -50.0f);
		this.Camera.SetRotation(0, 180, 0);
		this.Camera.ctype = F3D.CAMERA_TYPE_FPS;
		
		F3D.Cameras.Add(this.Camera);
		F3D.Worlds.SetCamera(this.Camera);
		
		F3D.Meshes.Add("abstract::Cube.a3da");
		F3D.Meshes.Add("abstract::Plane.a3da");
		
		// Add light to scene
		TF3D_Light light = new TF3D_Light("light_0", 0);
		light.SetPosition(3, 3, 3);
		light.Enable();
		
		
		this.PPlane = new TF3D_Body("PPlane");
		this.PPlane.AssignMesh("abstract::Plane.a3da");
		this.PPlane.Enable();
		this.PPlane.SetPosition(0f, 0f, 0f);
		this.PPlane.SetRotation(0f, 0f, 0f);		
		this.PPlane.CreateRigidBody(F3D.BULLET_SHAPE_PLANE, 0.0f);
		
		this.Pbody1 = new TF3D_Body("Body_0");
		this.Pbody1.AssignMesh(F3D.Meshes.FindByName("abstract::Cube.a3da"));
		this.Pbody1.Enable();
		this.Pbody1.SetPosition(0, 20f, 0);
		this.Pbody1.SetRotation(0f, 0f, 45f);		
		this.Pbody1.CreateRigidBody(F3D.BULLET_SHAPE_BOX,1.0f);
		

	
		this.Pbody2 = new TF3D_Body("Body_1");
		this.Pbody2.AssignMesh(F3D.Meshes.FindByName("abstract::Cube.a3da"));
		this.Pbody2.SetPosition(0.7f, 22f, 0);
		this.Pbody2.Enable();
		this.Pbody2.CreateRigidBody(F3D.BULLET_SHAPE_BOX,0.5f);
		
		this.Pbody2.PhysicObject.SetRestitution(0.1f);
		this.Pbody2.PhysicObject.SetFriction(0.5f);
		this.Pbody2.PhysicObject.SetDamping(0, 0);

		this.Pbody3 = new TF3D_Body("Body_2");
		this.Pbody3.AssignMesh(F3D.Meshes.FindByName("abstract::Cube.a3da"));
		this.Pbody3.SetPosition(-0.7f, 22f, 0);
		this.Pbody3.Enable();
		this.Pbody3.ChangeSurface("MATbase","MAT_text_d");
		this.Pbody3.CreateRigidBody(F3D.BULLET_SHAPE_BOX,0.5f);
		
		this.Pbody3.PhysicObject.SetRestitution(0.1f);
		this.Pbody3.PhysicObject.SetFriction(0.1f);
		this.Pbody3.PhysicObject.SetDamping(0, 0);

	}
	
	
	@Override
	public void onUpdate3D()
	{
		F3D.Draw.Axis(2.0f);	
	
		
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
			this.Camera.Move(0.0f, 0.0f, -0.1f);
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_A))
		{
			this.Camera.Move(-0.1f, 0.0f, 0.0f);
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_S))
		{
			this.Camera.Move(0.0f, 0.0f, 0.1f);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D))
		{
			this.Camera.Move(0.1f, 0.0f, 0.0f);
		}
		
	
		
	}
	
	
	@Override
	public void onUpdate2D()
	{
		F3D.Viewport.DrawInfo(0,0);
		
		
	}
	
	@Override
	public void OnDestroy()
	{
		
	}
	public static void main(String[] args)
	{
		new Demo_BasicPhysic().Execute();		
		System.exit(0); 
	}

}
