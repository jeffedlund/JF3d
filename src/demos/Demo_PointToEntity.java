/**
 * 
 */
package demos;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import AGFX.F3D.F3D;
import AGFX.F3D.AppWrapper.TF3D_AppWrapper;
import AGFX.F3D.Camera.TF3D_Camera;
import AGFX.F3D.Config.TF3D_Config;
import AGFX.F3D.Light.TF3D_Light;
//
import AGFX.F3D.Model.TF3D_Model;

/**
 * @author AndyGFX
 *
 */
public class Demo_PointToEntity extends TF3D_AppWrapper
{

	public TF3D_Camera Camera;
	public TF3D_Model modelA;
	public TF3D_Model modelB;
	public float speed = 0.5f;
	public float ang = 0;

	int odx = 0;
	int ody = 0;
	
	public Demo_PointToEntity()
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
		this.Camera.SetPosition(0.0f, 3.0f,10.0f);
		this.Camera.SetRotation(0, 0, 0);
		this.Camera.ctype = F3D.CAMERA_TYPE_FPS;
		
		F3D.Cameras.Add(this.Camera);
		F3D.Worlds.SetCamera(this.Camera);
	
		// Add light to scene
		TF3D_Light light = new TF3D_Light("light_0", 0);
		light.SetPosition(3, 3, 3);
		light.Enable();
		
		
		F3D.Meshes.Add("abstract::Cube.a3da");
		
		this.modelA = new TF3D_Model("CUBE1");
		this.modelA.AssignMesh("abstract::Cube.a3da");
		this.modelA.SetPosition(1,-2,1);
		//this.modelA.SetScale(0.1f,0.1f,1);
	
		
		this.modelB = new TF3D_Model("CUBE2");
		this.modelB.AssignMesh("abstract::Cube.a3da");
		this.modelB.SetPosition(4,0,0, true);

	}
	
	
	@Override
	public void onUpdate3D()
	{
		
		
		this.modelB.Turn(0,this.speed*F3D.Timer.AppSpeed()*10f,0,4f);
		
		this.modelA.PointTo(this.modelB);

		//F3D.Log.info("ROT Y", this.modelA.GetRotation().toString());
		
		
		ang=ang+0.05f;
		if (ang>360f) ang=0f;
		
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
			this.Camera.Move(0.0f, 0.0f, -this.speed*F3D.Timer.AppSpeed());
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_A))
		{
			this.Camera.Move(-this.speed*F3D.Timer.AppSpeed(), 0.0f, 0.0f);
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_S))
		{
			this.Camera.Move(0.0f, 0.0f, this.speed*F3D.Timer.AppSpeed());
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D))
		{
			this.Camera.Move(this.speed*F3D.Timer.AppSpeed(), 0.0f, 0.0f);
		}
		
		
		
		//this.modelA.PointTo(this.modelB);
		F3D.Draw.Axis(2.0f);	
	
	}
	
	
	@Override
	public void onUpdate2D()
	{
				
	}
	
	@Override
	public void OnDestroy()
	{
		
	}
	public static void main(String[] args)
	{
		new Demo_PointToEntity().Execute();		
		System.exit(0); 
	}

}
