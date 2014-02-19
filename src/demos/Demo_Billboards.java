/**
 * 
 */
package demos;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import javax.vecmath.*;

import AGFX.F3D.F3D;
import AGFX.F3D.AppWrapper.TF3D_AppWrapper;
import AGFX.F3D.Billboard.TF3D_Billboard;
import AGFX.F3D.Camera.TF3D_Camera;
import AGFX.F3D.Config.TF3D_Config;
import AGFX.F3D.Light.TF3D_Light;
import AGFX.F3D.Model.TF3D_Model;

/**
 * @author AndyGFX
 * 
 */
public class Demo_Billboards extends TF3D_AppWrapper
{

	public TF3D_Camera     Camera;

	private TF3D_Billboard sprite0;
	private TF3D_Billboard sprite1;
	private TF3D_Billboard sprite2;
	private TF3D_Billboard sprite3;
	private float          cam_speed = 0.5f;

	private TF3D_Model     model;

	public Demo_Billboards()
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
			F3D.Config.r_display_vsync = true;
			F3D.Config.r_display_title = "jFinal3D Graphics Engine 2010 - SPRITES and BILLBOARDS";

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
		this.Camera.SetPosition(0.0f, 0.0f, -10.0f);
		this.Camera.SetRotation(0, 180, 0);
		this.Camera.ctype = F3D.CAMERA_TYPE_FPS;

		F3D.Cameras.Add(this.Camera);
		F3D.Worlds.SetCamera(this.Camera);

		// Add light to scene
		TF3D_Light light = new TF3D_Light("light_0", 0);
		light.SetPosition(3, 3, 3);
		light.Enable();

		// ADD TEST MODEL

		F3D.Meshes.Add("abstract::Cube.a3da");

		this.model = new TF3D_Model("Cube1");
		this.model.AssignMesh(F3D.Meshes.FindByName("abstract::Cube.a3da"));
		this.model.SetPosition(3, 0, 0);
		this.model.Enable();

		// ADD sprites

		this.sprite0 = new TF3D_Billboard();

		this.sprite0.mode = F3D.BM_SPRITE;
		this.sprite0.name = "BM_sprite_no_alpha";
		this.sprite0.enable = true;
		this.sprite0.SetScale(1.0f, 1.0f, 0.0f);
		this.sprite0.bFadeAlpha = true;
		this.sprite0.bDepthSort = false;
		this.sprite0.material_id = F3D.Surfaces.FindByName("MAT_SPRITE_NO_DEPTH");
		this.sprite0.SetPosition(3, 0, 2);
		this.sprite0.Dir.set(0, 0, 0);

		this.sprite1 = new TF3D_Billboard();

		this.sprite1.mode = F3D.BM_SPRITE;
		this.sprite1.name = "BM_sprite";
		this.sprite1.enable = true;
		this.sprite1.SetScale(1.0f, 1.0f, 0.0f);
		this.sprite1.bFadeAlpha = false;
		this.sprite1.bDepthSort = true;
		this.sprite1.material_id = F3D.Surfaces.FindByName("MAT_BEAM_3");
		this.sprite1.SetPosition(0, 0, 0);
		this.sprite1.Dir.set(0, 0, 0);

		this.sprite2 = new TF3D_Billboard();

		this.sprite2.mode = F3D.BM_SPRITE;
		this.sprite2.name = "BM_sprite";
		this.sprite2.enable = true;
		this.sprite2.SetScale(1.0f, 1.0f, 0.0f);
		this.sprite2.bFadeAlpha = false;
		this.sprite2.bDepthSort = true;
		this.sprite2.material_id = F3D.Surfaces.FindByName("MAT_BEAM_3");
		this.sprite2.SetPosition(1, 0, 0);
		this.sprite2.Dir.set(0, 0, 0);

		this.sprite3 = new TF3D_Billboard();

		this.sprite3.mode = F3D.BM_SPRITE;
		this.sprite3.name = "BM_sprite";
		this.sprite3.enable = true;
		this.sprite3.SetScale(1.0f, 1.0f, 0.0f);
		this.sprite3.bFadeAlpha = false;
		this.sprite3.bDepthSort = true;
		this.sprite3.material_id = F3D.Surfaces.FindByName("MAT_BEAM_3");
		this.sprite3.SetPosition(-1, 0, 0);
		this.sprite3.Dir.set(0, 0, 0);

		TF3D_Billboard.CreateSprite("Sprite_1_y", new Vector3f(-1, 0, 0), new Vector3f(0, 0, 0), 1, 4, "MAT_BEAM_2", F3D.BM_AXIS_Y);
		TF3D_Billboard.CreateSprite("Sprite_2_y", new Vector3f(1, 0, 0), new Vector3f(0, 0, 0), 1, 4, "MAT_BEAM_2", F3D.BM_AXIS_Y);

		TF3D_Billboard.CreateSprite("Sprite_1_dir", new Vector3f(0, 1, 0), new Vector3f(1, 2, 1), 1, 4, "MAT_BEAM_DIR", F3D.BM_DIRECTIONAL);
		TF3D_Billboard.CreateSprite("Sprite_2_dir", new Vector3f(0, 1, 0), new Vector3f(-1, 2, 1), 1, 4, "MAT_BEAM_DIR", F3D.BM_DIRECTIONAL);
		TF3D_Billboard.CreateSprite("Sprite_3_dir", new Vector3f(0, 1, 0), new Vector3f(1, 2, -1), 1, 4, "MAT_BEAM_DIR", F3D.BM_DIRECTIONAL);
		TF3D_Billboard.CreateSprite("Sprite_4_dir", new Vector3f(0, 1, 0), new Vector3f(-1, 2, -1), 1, 4, "MAT_BEAM_DIR", F3D.BM_DIRECTIONAL);

		TF3D_Billboard.CreateSprite("Sprite_5_dir", new Vector3f(0, -1, 0), new Vector3f(1, -2, 1), 1, 4, "MAT_BEAM_DIR", F3D.BM_DIRECTIONAL);
		TF3D_Billboard.CreateSprite("Sprite_6_dir", new Vector3f(0, -1, 0), new Vector3f(-1, -2, 1), 1, 4, "MAT_BEAM_DIR", F3D.BM_DIRECTIONAL);
		TF3D_Billboard.CreateSprite("Sprite_7_dir", new Vector3f(0, -1, 0), new Vector3f(1, -2, -1), 1, 4, "MAT_BEAM_DIR", F3D.BM_DIRECTIONAL);
		TF3D_Billboard.CreateSprite("Sprite_8_dir", new Vector3f(0, -1, 0), new Vector3f(-1, -2, -1), 1, 4, "MAT_BEAM_DIR", F3D.BM_DIRECTIONAL);

	}

	@Override
	public void onUpdate3D()
	{

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
			this.Camera.Move(0.0f, 0.0f, -cam_speed * F3D.Timer.AppSpeed());
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_A))
		{
			this.Camera.Move(-cam_speed * F3D.Timer.AppSpeed(), 0.0f, 0.0f);
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_S))
		{
			this.Camera.Move(0.0f, 0.0f, cam_speed * F3D.Timer.AppSpeed());
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D))
		{
			this.Camera.Move(cam_speed * F3D.Timer.AppSpeed(), 0.0f, 0.0f);
		}

	}

	@Override
	public void onUpdate2D()
	{
		F3D.Viewport.DrawInfo(0, 0);

	}

	@Override
	public void OnDestroy()
	{

	}

	public static void main(String[] args)
	{
		new Demo_Billboards().Execute();
		System.exit(0);
	}

}
