package demos;

import javax.vecmath.*;

import AGFX.F3D.F3D;
import AGFX.F3D.AppWrapper.TF3D_AppWrapper;
import AGFX.F3D.Camera.TF3D_Camera;
import AGFX.F3D.Config.TF3D_Config;
import AGFX.F3D.Light.TF3D_Light;
import AGFX.F3D.Model.TF3D_Model;
import AGFX.F3D.Parser.TF3D_PARSER;
import AGFX.F3D.Texture.TF3D_Texture;

public class Demo_RenderModel extends TF3D_AppWrapper
{

	public TF3D_Camera Camera;
	public TF3D_Texture Tex;
	public float angle = 0;
	public TF3D_PARSER PARSER;
	public TF3D_Model model;
	public int surface_id;
	
	public Demo_RenderModel()
	
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
		this.Camera.SetPosition(2.0f, 2.0f, -5.0f);
		this.Camera.TargetPoint = new Vector3f(0,2,0);
		this.Camera.ctype = F3D.CAMERA_TYPE_TARGET;
		
		F3D.Cameras.Add(this.Camera);
		F3D.Worlds.SetCamera(this.Camera);
		
		// Add light to scene
		TF3D_Light light = new TF3D_Light("light_0", 0);
		light.SetPosition(3, 3, 3);
		light.Enable();
				
		this.surface_id = F3D.Surfaces.FindByName("MAT_text_a"); 

		F3D.Meshes.Add("abstract::Cube.a3da");
		
		this.model = new TF3D_Model("Cube");
		this.model.AssignMesh(F3D.Meshes.FindByName("abstract::Cube.a3da"));
		this.model.SetPosition(0, 2, 0);
		this.model.Enable();
		this.model.ChangeSurface("Cube_MAT_095A","MAT_test_alpha_blend");
		
	}
	
	
	@Override
	public void onUpdate3D()
	{
		
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
		new Demo_RenderModel().Execute();		
		System.exit(0); 
	}



}
