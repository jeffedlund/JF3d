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
public class Demo_CreateAppFrom_CFG extends TF3D_AppWrapper
{

	public Demo_CreateAppFrom_CFG()
	{		
	}
	
	
	@Override
	public void onConfigure()
	{
		try
		{
			// Creaet and load config file
			F3D.Config = new TF3D_Config();
			F3D.Config.Load("config/default.cfg");
			F3D.Config.r_display_title = F3D.Config.r_display_title + "   [ "+this.getClass().getName()+"]"; 
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
		
		new Demo_CreateAppFrom_CFG().Execute();		
		System.exit(0); 

	}

}
