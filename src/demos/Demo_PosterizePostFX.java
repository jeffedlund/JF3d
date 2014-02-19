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
import AGFX.F3D.Model.TF3D_Model;
import AGFX.F3D.Skybox.TF3D_Skybox;

/**
 * @author AndyGFX
 * 
 */
public class Demo_PosterizePostFX extends TF3D_AppWrapper
{

	/**
	 * 
	 */
	public TF3D_Camera Camera;
	/**
	 * 
	 */
	public TF3D_Body   PCube;
	public TF3D_Body   PSphere;
	public TF3D_Body   PCylinder;
	public TF3D_Body   PCapsule;
	public TF3D_Body   PCone;
	public TF3D_Body   PPlane;
	public TF3D_Body   PCubePlaneL;
	public TF3D_Body   PCubePlaneR;

	public TF3D_Body   zeton;
	public TF3D_Model  mzeton;

	public int         world_id;
	public int         frame_0;
	public int         frame_1;
	public int         FX = 0;

	public Demo_PosterizePostFX()
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
		this.Camera.SetPosition(0.0f, 10.0f, -30.0f);
		this.Camera.SetRotation(0, 180, 0);
		this.Camera.ctype = F3D.CAMERA_TYPE_FPS;

		F3D.Cameras.Add(this.Camera);
		F3D.Worlds.SetCamera(this.Camera);

		this.Camera.Sky = new TF3D_Skybox();

		F3D.Meshes.Add("abstract::Cube.a3da");
		F3D.Meshes.Add("abstract::Sphere.a3da");
		F3D.Meshes.Add("abstract::Cone.a3da");
		F3D.Meshes.Add("abstract::Cylinder.a3da");
		F3D.Meshes.Add("abstract::Capsule.a3da");
		F3D.Meshes.Add("abstract::Plane.a3da");
		F3D.Meshes.Add("abstract::MultiSurfCube.a3da");
		F3D.Meshes.Add("abstract::zeton.a3da");

		// Add light to scene
		TF3D_Light light = new TF3D_Light("light_0", 0);
		light.SetPosition(3, 3, 3);
		light.Enable();

		this.PCubePlaneL = new TF3D_Body("PCubePlane");
		this.PCubePlaneL.AssignMesh("abstract::Cube.a3da");
		this.PCubePlaneL.Enable();
		this.PCubePlaneL.SetPosition(2.5f, 14f, 0f);
		this.PCubePlaneL.SetScale(5f, 0.15f, 5f);
		this.PCubePlaneL.SetRotation(0f, 0f, 45f);
		this.PCubePlaneL.CreateRigidBody(F3D.BULLET_SHAPE_BOX, 0.0f);

		this.PCubePlaneR = new TF3D_Body("PCubePlane");
		this.PCubePlaneR.AssignMesh("abstract::Cube.a3da");
		this.PCubePlaneR.Enable();
		this.PCubePlaneR.SetPosition(-2.5f, 8f, 0f);
		this.PCubePlaneR.SetScale(5f, 0.15f, 5f);
		this.PCubePlaneR.SetRotation(0f, 0f, -45f);
		this.PCubePlaneR.CreateRigidBody(F3D.BULLET_SHAPE_BOX, 0.0f);

		this.PPlane = new TF3D_Body("PPlane");
		this.PPlane.AssignMesh("abstract::Plane.a3da");
		this.PPlane.Enable();
		this.PPlane.SetPosition(0f, 0f, 0f);
		this.PPlane.SetRotation(0f, 0f, 0f);
		this.PPlane.CreateRigidBody(F3D.BULLET_SHAPE_PLANE, 0.0f);

		this.PCube = new TF3D_Body("PCube");
		this.PCube.AssignMesh("abstract::MultiSurfCube.a3da");
		this.PCube.Enable();
		this.PCube.SetPosition(0, 20f, 0);
		this.PCube.SetRotation(0f, 0f, 0f);
		this.PCube.CreateRigidBody(F3D.BULLET_SHAPE_BOX, 1.0f);

		this.PCube.PhysicObject.SetFriction(0.2f);

		this.PSphere = new TF3D_Body("PSphere");
		this.PSphere.AssignMesh("abstract::Sphere.a3da");
		this.PSphere.Enable();
		this.PSphere.SetPosition(0.5f, 21f, 0);
		this.PSphere.SetRotation(0f, 0f, 0f);
		this.PSphere.CreateRigidBody(F3D.BULLET_SHAPE_SPHERE, 1.0f);
		// this.PSphere.CreateRigidBody(F3D.BULLET_SHAPE_CONVEXHULL, 1.0f);

		this.PCapsule = new TF3D_Body("PCapsule");
		this.PCapsule.AssignMesh("abstract::Capsule.a3da");
		this.PCapsule.Enable();
		this.PCapsule.SetPosition(-0.5f, 21f, 0);
		this.PCapsule.SetRotation(0f, 0f, 0f);
		this.PCapsule.CreateRigidBody(F3D.BULLET_SHAPE_CAPSULE, 1.0f);

		this.PCylinder = new TF3D_Body("PCylinder");
		this.PCylinder.AssignMesh("abstract::Cylinder.a3da");
		this.PCylinder.Enable();
		this.PCylinder.SetPosition(0.5f, 22f, 0);
		this.PCylinder.SetRotation(0f, 0f, 0f);
		this.PCylinder.CreateRigidBody(F3D.BULLET_SHAPE_CYLINDER, 1.0f);

		this.PCone = new TF3D_Body("PCone");
		this.PCone.AssignMesh("abstract::Cone.a3da");
		this.PCone.Enable();
		this.PCone.SetPosition(-0.5f, 22f, 0);
		this.PCone.SetRotation(0f, 0f, 0f);
		this.PCone.CreateRigidBody(F3D.BULLET_SHAPE_CONE, 1.0f);

		this.zeton = new TF3D_Body("zeton");
		this.zeton.AssignMesh("abstract::zeton.a3da");
		this.zeton.Enable();
		// this.zeton.SetScale(0.25f, 0.25f, 0.25f);
		this.zeton.SetPosition(0.5f, 22f, -2);
		this.zeton.SetRotation(0f, 0f, 0f);
		this.zeton.CreateRigidBody(F3D.BULLET_SHAPE_CYLINDER_Z, 1.0f);

		this.mzeton = new TF3D_Model("zeton");
		this.mzeton.AssignMesh("abstract::zeton.a3da");
		this.mzeton.Enable();

		// F3D.Worlds.RenderManualy();
		world_id = F3D.Worlds.FindByName("MAIN_WORLD");

		// use this line when your GPU has RenderBufffer supported
		frame_0 = F3D.FrameBuffers.Add("POSTFX", F3D.Config.r_display_width, F3D.Config.r_display_height,true,1);
		frame_1 = F3D.FrameBuffers.Add("POSTFX0", F3D.Config.r_display_width, F3D.Config.r_display_height,false,1);
				
		F3D.Textures.Add("POSTFX_TETXURE", F3D.FrameBuffers.Get("POSTFX"), false);
		F3D.Textures.Add("POSTFX_TETXURE0", F3D.FrameBuffers.Get("POSTFX0"), false);
		
		

	}

	@Override
	public void onUpdate3D()
	{
		// F3D.Draw.Axis(2.0f);

		F3D.Worlds.SetCamera(this.Camera);
		F3D.Worlds.UpdateWorld(world_id);

		F3D.FrameBuffers.Get("POSTFX").BeginRender();
		F3D.Worlds.RenderWorld(world_id);
		F3D.FrameBuffers.Get("POSTFX").EndRender(true, true);

		if (Mouse.isInsideWindow())
		{
			if (Mouse.isButtonDown(0))
			{
				float dx = (float) Mouse.getDX() / 10.0f;
				float dy = (float) Mouse.getDY() / 10.0f;

				this.Camera.Turn(-dy, dx, 0.0f);

				if (Mouse.getX() < 3)
				{
					Mouse.setCursorPosition(F3D.Config.r_display_width - 4, Mouse.getY());
				}
				if (Mouse.getX() > F3D.Config.r_display_width - 3)
				{
					Mouse.setCursorPosition(4, Mouse.getY());
				}
				if (Mouse.getY() < 3)
				{
					Mouse.setCursorPosition(Mouse.getX(), F3D.Config.r_display_height - 4);
				}
				if (Mouse.getY() > F3D.Config.r_display_height - 3)
				{
					Mouse.setCursorPosition(Mouse.getX(), 4);
				}
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

		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE))
		{
			Vector3f dir = new Vector3f(F3D.Cameras.items.get(F3D.Cameras.CurrentCameraID).axis._forward);
			dir.scale(-0.25f);
			this.PCube.PhysicObject.RigidBody.applyCentralImpulse(dir);
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_1))
		{
			this.FX = 0;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_2))
		{
			this.FX = 1;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_3))
		{
			this.FX = 2;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_4))
		{
			this.FX = 3;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_5))
		{
			this.FX = 4;
		}

	}

	@Override
	public void onUpdate2D()
	{

		if (FX==0)
		{
			F3D.Shaders.UseProgram("DREAM");
			F3D.Textures.Bind("POSTFX_TETXURE");
			
		}
		if (FX==1)
		{
			F3D.Shaders.UseProgram("POSTERIZE");
			F3D.Textures.Bind("POSTFX_TETXURE");
		}
		
		if (FX==2)
		{
			
			F3D.Shaders.UseProgram("WARP");
			F3D.Textures.ActivateLayer(0);
			F3D.Textures.Bind("POSTFX_TETXURE");
			F3D.Textures.ActivateLayer(1);
			F3D.Textures.Bind("perlin_noise");
		}
		
		if (FX==3)
		{
			F3D.Shaders.UseProgram("BLUR_H");
			F3D.Textures.Bind("POSTFX_TETXURE");
			F3D.FrameBuffers.BeginRender(this.frame_1);
			F3D.Draw.Rectangle(0, 0, F3D.Config.r_display_width, F3D.Config.r_display_height, true);
			F3D.FrameBuffers.EndRender(this.frame_1,true,true);
			F3D.Shaders.UseProgram("BLUR_V");
			F3D.Textures.Bind("POSTFX_TETXURE0");
		}
		
		if (FX==4)
		{
			
										
						
			F3D.Shaders.UseProgram("GAUSSIAN_V");
			F3D.Textures.Bind("POSTFX_TETXURE");
			F3D.FrameBuffers.BeginRender(this.frame_1);
			F3D.Draw.Rectangle(0, 0, F3D.Config.r_display_width, F3D.Config.r_display_height, true);
			F3D.FrameBuffers.EndRender(this.frame_1,true,true);
			F3D.Shaders.UseProgram("GAUSSIAN_H");
			F3D.Textures.Bind("POSTFX_TETXURE0");
			
			
			
		}
		
		
		F3D.Draw.Rectangle(0, 0, F3D.Config.r_display_width, F3D.Config.r_display_height, true);
		//F3D.Draw.Rectangle(0, 0, 400,300,true);
		F3D.Shaders.StopProgram();
		F3D.Viewport.DrawInfo(0, 0);

	}

	@Override
	public void OnDestroy()
	{

	}

	public static void main(String[] args)
	{
		new Demo_PosterizePostFX().Execute();
		System.exit(0);
	}

}
