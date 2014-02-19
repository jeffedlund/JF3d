/**
 * 
 */
package demos;


import AGFX.F3D.F3D;
import AGFX.F3D.AppWrapper.TF3D_AppWrapper;
import AGFX.F3D.Config.TF3D_Config;

/**
 * @author AndyGFX
 *
 */
public class Demo_AbstractFileSystem extends TF3D_AppWrapper
{

	@Override
	public void onConfigure()
	{
		try
		{
			
			
			F3D.Config = new TF3D_Config();
			
			/*
			F3D.Config.r_display_width = 800;
			F3D.Config.r_display_height = 600;
			F3D.Config.r_fullscreen = false;
			F3D.Config.r_display_vsync = true;
			F3D.Config.r_display_title = "jFinal3D Graphics Engine 2010 - APP";
			*/
			
			F3D.Config.Load("abstract::default.cfg");
			F3D.Config.r_display_title = F3D.Config.r_display_title + this.getClass().getName();
			super.onConfigure();
			

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void onInitialize()
	{
		String filename = "abstract::rotate_billboard.event";
		String fullfilenamepath = F3D.AbstractFiles.GetFullPath(filename);
		F3D.Log.info("MAIN", fullfilenamepath);
		
	
		
	}
	
	@Override
	public void onUpdate3D()
	{
		
		
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
		
		new Demo_AbstractFileSystem().Execute();		
		System.exit(0); 

	}


}
