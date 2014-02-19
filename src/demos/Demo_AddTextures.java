package demos;

import static org.lwjgl.opengl.GL11.*;


import javax.vecmath.*;

import AGFX.F3D.F3D;
import AGFX.F3D.AppWrapper.TF3D_AppWrapper;
import AGFX.F3D.Camera.TF3D_Camera;
import AGFX.F3D.Config.TF3D_Config;
import AGFX.F3D.Helpers.TF3D_Helpers;
import AGFX.F3D.Parser.TF3D_PARSER;

public class Demo_AddTextures extends TF3D_AppWrapper
{

	public TF3D_Camera Camera;
	public TF3D_Helpers HLP;
	public float angle = 0;
	public TF3D_PARSER PARSER;
	public int id;
	
	public Demo_AddTextures()
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
			F3D.Config.r_display_title = "jFinal3D Graphics Engine 2010 - RENDER VBO CUBE";
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
		this.Camera.SetPosition(10.0f, 10.0f, -10.0f);
		this.Camera.TargetPoint = new Vector3f(0,0,0);
		this.Camera.ctype = F3D.CAMERA_TYPE_TARGET;
		
		F3D.Cameras.Add(this.Camera);
		
		F3D.Textures.Load("abstract::text_d.texture");
		
		
		F3D.Meshes.Add("abstract::Cube.a3da");
		id = F3D.Meshes.FindByName("abstract::Cube.a3da");
		
		this.HLP = new TF3D_Helpers();
		
		this.PARSER = new TF3D_PARSER();
		this.PARSER.ParseFile("media/surfaces/materials/tahoma_8_bold.mat");
		
		
		
	}
	
	
	@Override
	public void onUpdate3D()
	{
		
		this.HLP.Axis(2.0f);
		glEnable(GL_TEXTURE_2D);
		F3D.Textures.ActivateLayer(0);
		F3D.Textures.Bind("text_d");
		
		F3D.Meshes.items.get(id).Render();
		
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
		new Demo_AddTextures().Execute();		
		System.exit(0); 
	}



}
