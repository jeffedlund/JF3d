/**
 * 
 */
package AGFX.F3D.Engine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import AGFX.F3D.F3D;

/**
 * @author AndyGFX
 * 
 */
public class TF3D_Display
{
	
	public DisplayMode mode;

	public TF3D_Display()
	{
		F3D.Log.info("TF3D_Display","Display class created");
	}

	// -----------------------------------------------------------------------
	// TF3D_Display:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param width 
	 * @param height
	 * @param bpp
	 * @return
	 * @throws LWJGLException
	 */
	// -----------------------------------------------------------------------
	private DisplayMode findDisplayMode(int width, int height, int bpp) throws LWJGLException
	{
		DisplayMode[] modes = Display.getAvailableDisplayModes();
		for (DisplayMode mode : modes)
		{
			if (mode.getWidth() == width && mode.getHeight() == height && mode.getBitsPerPixel() >= bpp && mode.getFrequency() <= 75)
			{
				return mode;
			}
		}
		return Display.getDesktopDisplayMode();
	}

	// -----------------------------------------------------------------------
	// TF3D_Display: 
	// -----------------------------------------------------------------------
	/**
	 * <BR>-------------------------------------------------------------------<BR> 
	 *  Create GL Window display inherited from Confing definition
	 * <BR>-------------------------------------------------------------------<BR> 
	 */
	// -----------------------------------------------------------------------
	public void Create()
	{
		try
		{
			this.mode = findDisplayMode(F3D.Config.r_display_width, F3D.Config.r_display_height, Display.getDisplayMode().getBitsPerPixel());
			Display.setDisplayMode(this.mode);
			Display.setFullscreen(F3D.Config.r_fullscreen);			
			Display.setTitle(F3D.Config.r_display_title);
			Display.create();
			Display.setVSyncEnabled(F3D.Config.r_display_vsync);
			
			F3D.Log.info("TF3D_Display", "Display : title      = "+F3D.Config.r_display_title);
			F3D.Log.info("TF3D_Display", "Display : width      = "+F3D.Config.r_display_width);
			F3D.Log.info("TF3D_Display", "Display : height     = "+F3D.Config.r_display_height);
			F3D.Log.info("TF3D_Display", "Display : fullscreen = "+F3D.Config.r_fullscreen.toString());
			F3D.Log.info("TF3D_Display", "Display : VSync      = "+F3D.Config.r_display_vsync.toString());

		} catch (LWJGLException e)
		{
			e.printStackTrace();
			F3D.Log.error("TF3D_Display","Unsupported display resolution");
			
		}
	}

	// -----------------------------------------------------------------------
	// TF3D_Display: 
	// -----------------------------------------------------------------------
	/**
	 * <BR>-------------------------------------------------------------------<BR> 
	 *  Switch to Fullscreen mode
	 * <BR>-------------------------------------------------------------------<BR> 
	 * @throws LWJGLException
	 */
	// -----------------------------------------------------------------------
	private void switchToFullscreenMode() throws LWJGLException
	{

		F3D.Config.r_fullscreen = true;
		Display.setFullscreen(F3D.Config.r_fullscreen);

	}

	
	// -----------------------------------------------------------------------
	// TF3D_Display: 
	// -----------------------------------------------------------------------
	/**
	 * <BR>-------------------------------------------------------------------<BR> 
	 *  Switch to Window screen mode
	 * <BR>-------------------------------------------------------------------<BR> 
	 * @throws LWJGLException
	 */
	// -----------------------------------------------------------------------
	private void switchToWindowMode() throws LWJGLException
	{
		F3D.Config.r_fullscreen = false;
		Display.setFullscreen(F3D.Config.r_fullscreen);
	}

	// -----------------------------------------------------------------------
	// TF3D_Display: 
	// -----------------------------------------------------------------------
	/**
	 * <BR>-------------------------------------------------------------------<BR> 
	 *  Switch screen mode between fullscreen/window<BR>
	 * <BR>-------------------------------------------------------------------<BR> 
	 * @throws LWJGLException
	 */
	// -----------------------------------------------------------------------
	public void SwitchSceenMode() throws LWJGLException
	{
		if (F3D.Config.r_fullscreen) 
		{
			this.switchToWindowMode();
			F3D.Log.info("TF3D_Display", "Display mode = WINDOW MODE");
		}
		else
		{
			this.switchToFullscreenMode();
			F3D.Log.info("TF3D_Display", "Display mode = FULLSCREEN MODE");
		}
	}
	
	public void Destroy()
	{
		Display.destroy(); 
	}
	
	public Boolean isVisible()
	{
		return Display.isVisible();
	}

	public Boolean isDirty()
	{
		return Display.isDirty();
	}

	public void Update()
	{
		Display.update();
	}
}
