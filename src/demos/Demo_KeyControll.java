/**
 * 
 */
package demos;



import org.lwjgl.input.Keyboard;

import AGFX.F3D.F3D;
import AGFX.F3D.AppWrapper.TF3D_AppWrapper;
import AGFX.F3D.Config.TF3D_Config;

/**
 * @author AndyGFX
 * 
 */
public class Demo_KeyControll extends TF3D_AppWrapper
{

	public Demo_KeyControll()
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
			F3D.Config.r_display_title = "jFinal3D Graphics Engine 2010 - KEYBOARD CONTROL";
			
			super.onConfigure();
			

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void onInitialize()
	{
		
	}
	
	@Override
	public void onUpdate3D()
	{
		
		if (F3D.Input.Mouse.IsButtonDown(0))
		{
			F3D.Log.info("BUTTON_0", "Down");
			F3D.Log.info("BUTTON_0", F3D.Input.Mouse.GetButtonEvent(0).toString());
		}
		
		if (F3D.Input.Mouse.IsButtonUp(0))
		{
			F3D.Log.info("BUTTON_0", F3D.Input.Mouse.GetButtonEvent(0).toString());
		}
		
		if (F3D.Input.Key.IsKeyDown(Keyboard.KEY_SPACE))
		{
			F3D.Log.info("", "presss");
		}
		
		
		if (F3D.Input.Key.IsKeyUp(Keyboard.KEY_R))
		{
			F3D.Log.info("", "hold");
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
		
		new Demo_KeyControll().Execute();		
		System.exit(0); 

	}

}
