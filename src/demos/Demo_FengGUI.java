/**
 * 
 */
package demos;

import org.fenggui.actor.ScreenshotActor;
import org.fenggui.example.Everything;
import AGFX.F3D.F3D;
import AGFX.F3D.AppWrapper.TF3D_AppWrapper;
import AGFX.F3D.Config.TF3D_Config;


/**
 * @author AndyGFX
 * 
 */
public class Demo_FengGUI extends TF3D_AppWrapper
{
	private ScreenshotActor screenshotActor;


	public Demo_FengGUI()
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

			super.onConfigure();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void onInitialize()
	{

		// build the gui
		buildGUI();

		screenshotActor = new ScreenshotActor();
		screenshotActor.hook(F3D.Gui.display);

	}

	@Override
	public void onUpdate3D()
	{

	}

	@Override
	public void onGUI()
	{

	}

	@Override
	public void onUpdate2D()
	{
		
		F3D.Gui.Render();
		screenshotActor.renderToDos(F3D.Gui.display.getBinding().getOpenGL(), 512, 512);

	}

	@Override
	public void OnDestroy()
	{

	}

	public void buildGUI()
	{

		Everything everything = new Everything();
		everything.buildGUI(F3D.Gui.display);

        
	}

	

	public static void main(String[] args)
	{

		new Demo_FengGUI().Execute();
		System.exit(0);

	}

}
