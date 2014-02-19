package demos;

import javax.vecmath.*;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import AGFX.F3D.F3D;
import AGFX.F3D.AppWrapper.TF3D_AppWrapper;
import AGFX.F3D.Camera.TF3D_Camera;
import AGFX.F3D.Config.TF3D_Config;
import AGFX.F3D.Light.TF3D_Light;
import AGFX.F3D.Model.TF3D_Model;
import AGFX.F3D.Parser.TF3D_PARSER;
import AGFX.F3D.Shader.TF3D_Shader;
import AGFX.F3D.Texture.TF3D_Texture;

public class Demo_ShaderTest extends TF3D_AppWrapper
{

	public TF3D_Camera	Camera;
	public TF3D_Texture	Tex;
	public float		angle			= 0;
	public TF3D_PARSER	PARSER;
	public TF3D_Model	model;
	public int			surface_id;
	public int			selected_shader	= 1;
	public TF3D_Shader	shader_diffuse;
	public TF3D_Shader	shader_phong;
	public TF3D_Shader	shader_envmap;

	public Demo_ShaderTest()

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

		this.Camera = new TF3D_Camera("TargetCamera");
		this.Camera.SetPosition(0.0f, 2.0f, 2.0f);
		this.Camera.TargetPoint = new Vector3f(0, 0, 0);
		this.Camera.ctype = F3D.CAMERA_TYPE_FPS;

		F3D.Cameras.Add(this.Camera);
		F3D.Worlds.SetCamera(this.Camera);

		// Add light to scene
		TF3D_Light light = new TF3D_Light("light_0", 0);
		light.SetPosition(3, 3, 3);
		light.Enable();

		this.surface_id = F3D.Surfaces.FindByName("MAT_text_a");

		F3D.Meshes.Add("abstract::Sphere.a3da");
		F3D.Meshes.items.get(0).IndicesGroup.items.get(0).material_id = -1;

		// Shader: DIFFUSE

		shader_diffuse = new TF3D_Shader("DIFFUSE");
		shader_diffuse.Load("media/shaders/f3d_diffuse.vert", "media/shaders/f3d_diffuse.frag");
		shader_diffuse.AddUniform1i("BaseMap", 0);
		F3D.Shaders.Add(shader_diffuse);

		// Shader: PHONG

		shader_phong = new TF3D_Shader("PHONG");
		shader_phong.Load("media/shaders/f3d_phong.vert", "media/shaders/f3d_phong.frag");
		shader_phong.AddUniform1i("BaseMap", 0);
		shader_phong.AddUniform4f("fvSpecular", 0.7f, 0.7f, 0.7f, 1f);
		shader_phong.AddUniform4f("fvDiffuse", 0.7f, 0.7f, 0.7f, 1f);
		shader_phong.AddUniform4f("fvAmbient", 0.1f, 0.1f, 0.1f, 1f);
		shader_phong.AddUniform1f("fSpecularPower", 100f);
		shader_phong.AddUniform3f("fvLightPosition", -3f, 3f, 3f);
		shader_phong.AddUniform3f("fvEyePosition", 2f, 2f, 2f);

		F3D.Shaders.Add(shader_phong);

		// Shader: PHONG

		shader_envmap = new TF3D_Shader("ENVMAP");
		shader_envmap.Load("media/shaders/f3d_envmap.vert", "media/shaders/f3d_envmap.frag");
		shader_envmap.AddUniform1i("BaseMap", 0);
		shader_envmap.AddUniform1i("EnvMap", 1);
		shader_envmap.AddUniform3f("BaseColor", 0.7f, 0.7f, 0.7f);
		shader_envmap.AddUniform1f("MixRatio", 0.5f);
		shader_envmap.AddUniform3f("LightPos", 3f, 3f, 3f);

		F3D.Shaders.Add(shader_envmap);

	}

	@Override
	public void onUpdate3D()
	{

		F3D.Draw.Axis(2.0f);

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
		if (selected_shader == 1)
		{
			F3D.Shaders.UseProgram("DIFFUSE");
			F3D.Textures.ActivateLayer(0);
			F3D.Textures.Bind(6);

		}

		if (selected_shader == 2)
		{

			F3D.Shaders.UseProgram("PHONG");
			F3D.Textures.ActivateLayer(0);
			F3D.Textures.Bind(6);

		}

		if (selected_shader == 3)
		{

			F3D.Shaders.UseProgram("ENVMAP");
			F3D.Textures.ActivateLayer(0);
			F3D.Textures.Bind(6);

		}

		if (Keyboard.isKeyDown(Keyboard.KEY_1))
		{
			selected_shader = 1;
			F3D.Log.info("MAIN", "shader_diffuse");
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_2))
		{
			selected_shader = 2;
			F3D.Log.info("MAIN", "shader_phong");
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_3))
		{
			selected_shader = 3;
			F3D.Log.info("MAIN", "ENVMAP");
		}

		F3D.Meshes.items.get(0).Render();

		F3D.Shaders.StopProgram();
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
		new Demo_ShaderTest().Execute();
		System.exit(0);
	}

}
