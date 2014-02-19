package demos;

import javax.vecmath.*;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import AGFX.F3D.F3D;
import AGFX.F3D.AppWrapper.TF3D_AppWrapper;
import AGFX.F3D.Camera.TF3D_Camera;
import AGFX.F3D.Config.TF3D_Config;
import AGFX.F3D.Light.TF3D_Light;
import AGFX.F3D.Model.TF3D_Model;
import AGFX.F3D.Parser.TF3D_PARSER;
import AGFX.F3D.Texture.TF3D_Texture;

public class Demo_TestVisibilityFromDepth extends TF3D_AppWrapper
{

	public TF3D_Camera  Camera;
	public TF3D_Texture Tex;
	public float        angle = 0;
	public TF3D_PARSER  PARSER;
	public TF3D_Model   model1;
	public TF3D_Model   model2;
	public Vector3f     screen2D1;
	public Vector3f     screen2D2;
	public Boolean      res1;
	public Boolean      res2;
	public int          surface_id;

	public Demo_TestVisibilityFromDepth()

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
			F3D.Config.r_display_title = "jFinal3D Graphics Engine 2010 - " + this.getClass().getName();

			// [1] set resource destination
			F3D.Config.io_preload_source_mode = "PRELOAD_FROM_JAR";

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

		this.Camera = new TF3D_Camera("TargetCamera");
		this.Camera.SetPosition(0f, 2.0f, 7.0f);
		this.Camera.ctype = F3D.CAMERA_TYPE_FPS;

		F3D.Cameras.Add(this.Camera);
		F3D.Worlds.SetCamera(this.Camera);

		// Add light to scene
		TF3D_Light light = new TF3D_Light("light_0", 0);
		light.SetPosition(3, 3, 3);
		light.Enable();

		this.surface_id = F3D.Surfaces.FindByName("MAT_text_a");

		F3D.Meshes.Add("abstract::Cube.a3da");

		this.model1 = new TF3D_Model("Cube1");
		this.model1.AssignMesh(F3D.Meshes.FindByName("abstract::Cube.a3da"));
		this.model1.SetPosition(0, 0, -2);
		this.model1.Enable();

		this.model2 = new TF3D_Model("Cube2");
		this.model2.AssignMesh(F3D.Meshes.FindByName("abstract::Cube.a3da"));
		this.model2.SetPosition(0, 0, 2);
		this.model2.Enable();

		// this.model.ChangeSurface("Cube_MAT_095A","MAT_test_alpha_blend");
		
		

	}

	@Override
	public void onUpdate3D()
	{

		Vector3f test_point = new Vector3f(0,0,0);
		
		screen2D2 = F3D.MathUtils.World3DtoScreen2D(test_point);
		this.res2 = F3D.Viewport.IsPointVisible(test_point);
		

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
			this.Camera.Move(0.0f, 0.0f, -1f*F3D.Timer.AppSpeed());
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_A))
		{
			this.Camera.Move(-1f*F3D.Timer.AppSpeed(), 0.0f, 0.0f);
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_S))
		{
			this.Camera.Move(0.0f, 0.0f, 1f*F3D.Timer.AppSpeed());
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D))
		{
			this.Camera.Move(1f*F3D.Timer.AppSpeed(), 0.0f, 0.0f);
		}

		
		
		
	}

	@Override
	public void onUpdate2D()
	{
		F3D.Textures.DeactivateLayers();
		GL11.glColor4f(1f,0f,0f,0.5f);
		F3D.Draw.Rectangle(screen2D2.x-2, screen2D2.y-2, screen2D2.x+2, screen2D2.y+2, false);
		F3D.Fonts.DrawText(0, screen2D2.x, screen2D2.y, "Visible point: "+this.res2.toString(), 0);
		
	}

	@Override
	public void OnDestroy()
	{

	}

	public static void main(String[] args)
	{
		new Demo_TestVisibilityFromDepth().Execute();
		System.exit(0);
	}

}
