/**
 * 
 */
package demos;

import javax.vecmath.*;

import AGFX.F3D.F3D;
import AGFX.F3D.AppWrapper.TF3D_AppWrapper;
import AGFX.F3D.Camera.TF3D_Camera;
import AGFX.F3D.Config.TF3D_Config;

/**
 * @author AndyGFX
 *
 */
public class Demo_CreateCamera extends TF3D_AppWrapper
{

	public TF3D_Camera Camera;

	
	
	public Demo_CreateCamera()
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
		this.Camera.SetPosition(10.0f, 10.0f, -10.0f);
		this.Camera.TargetPoint = new Vector3f(0,0,0);
		this.Camera.ctype = F3D.CAMERA_TYPE_TARGET;
		
		F3D.Cameras.Add(this.Camera);
		F3D.Worlds.SetCamera(this.Camera);
		
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
		new Demo_CreateCamera().Execute();		
		System.exit(0); 
	}

}
