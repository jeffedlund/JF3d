/**
 * 
 */
package demos;

import javax.vecmath.Vector3f;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import com.bulletphysics.dynamics.RigidBody;
import AGFX.F3D.F3D;
import AGFX.F3D.AppWrapper.TF3D_AppWrapper;
import AGFX.F3D.Body.TF3D_Body;
import AGFX.F3D.Camera.TF3D_Camera;
import AGFX.F3D.Config.TF3D_Config;
import AGFX.F3D.Light.TF3D_Light;
import AGFX.F3D.Pivot.TF3D_Pivot;
import AGFX.F3D.Skybox.TF3D_Skybox;

/**
 * @author AndyGFX
 *
 */
public class Demo_PickBody extends TF3D_AppWrapper
{

	public TF3D_Camera Camera;
	public TF3D_Body   pbody1;
	public TF3D_Body   pbody2;
	public TF3D_Pivot  pivot;
	public RigidBody body;

	int odx = 0;
	int ody = 0;
	
	public Demo_PickBody()
	{
		
	}
	
	@Override
	public void onConfigure()
	{
		try
		{
			
			F3D.Config = new TF3D_Config();
			
			F3D.Config.r_display_width = 800;
			F3D.Config.r_display_height = 600;
			F3D.Config.r_fullscreen = false;
			F3D.Config.r_display_vsync = true;
			F3D.Config.r_display_title = "jFinal3D Graphics Engine 2010 - "+ this.getClass().getName();
			
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
		this.Camera.SetPosition(0.0f, 10.0f, -10.0f);
		this.Camera.SetRotation(0, 180, 0);
		this.Camera.ctype = F3D.CAMERA_TYPE_FPS;
		
		this.Camera.Sky = new TF3D_Skybox();
		F3D.Cameras.Add(this.Camera);
		F3D.Worlds.SetCamera(this.Camera);
		
		// Add light to scene
		TF3D_Light light = new TF3D_Light("light_0", 0);
		light.SetPosition(3, 3, 3);
		light.Enable();
		
		
		F3D.Meshes.Add("abstract::Cube.a3da");
	
		//create pivot helper for simple calc ray line direction
		
		this.pivot = new TF3D_Pivot("Pivot");
		
	
		this.pbody1 = new TF3D_Body("PCube1");
		this.pbody1.AssignMesh("abstract::Cube.a3da");
		this.pbody1.Enable();
		//this.pbody1.changeSurface("MATbase");
		this.pbody1.SetPosition(4f, 0f, 0);
		this.pbody1.SetRotation(0f, 0f, 0f);
		this.pbody1.CreateRigidBody(F3D.BULLET_SHAPE_BOX, 0f);
		
		this.pbody2 = new TF3D_Body("PCube2");
		this.pbody2.AssignMesh("abstract::Cube.a3da");
		this.pbody2.Enable();
		//this.pbody2.SetSurface("MATbase");
		this.pbody2.SetPosition(-4f, 0f, 0);
		this.pbody2.SetRotation(0f, 0f, 0f);
		this.pbody2.CreateRigidBody(F3D.BULLET_SHAPE_BOX, 0f);
		
		
		
		
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
		
		this.pivot.Turn(0,2*F3D.Timer.AppSpeed(),0);
		
		
		Vector3f pA = new Vector3f();
		Vector3f pB = new Vector3f();
		
		this.pivot.UpdateAxisDirection();
		
		pA.add(this.pivot.axis._forward);
		pA.scale(2f);
		
		pB.add(this.pivot.axis._forward);
		pB.scale(10f);
		
		// Draw visual raycast line
		F3D.Draw.Line3D(pA, pB);
		F3D.Draw.Axis(2.0f);
		
		/*
		// LINE PICK from POINT A to B
		
		body= F3D.Pick.LineAB(pA, pB);
		if (body != null) 
		{
			TF3D_Body pb = (TF3D_Body) body.getUserPointer();
			F3D.Log.info("LINE PICK", pb.name);
			
		}
	
	
		//CAMERA PICK
		body= F3D.Pick.CameraDirection(1000f);
		if (body != null) 
		{
			TF3D_Body pb = (TF3D_Body) body.getUserPointer();
			F3D.Log.info("CAMERA PICK", pb.name);
			
		}
		*/
		
		//MOUSE PICK 
		body = F3D.Pick.Mouse(Mouse.getX(),Mouse.getY());
		if (body != null) 
		{
			TF3D_Body pb = (TF3D_Body) body.getUserPointer();
			F3D.Log.info("MOUSE PICK", pb.name);
			
		}
		// Draw body axis
		F3D.Draw.Axis(pbody1, 2f);
		F3D.Draw.Axis(pbody2, 2f);
		
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
		new Demo_PickBody().Execute();		
		System.exit(0); 
	}

}
