/**
 * 
 */
package AGFX.F3D.AppWrapper;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import AGFX.F3D.F3D;


/**
 * @author AndyGFX
 * 
 */
public class TF3D_AppWrapper
{
	public Boolean	AppTerminate;

	// -----------------------------------------------------------------------
	// TA3D_AppWrapper:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Constructor <BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	public TF3D_AppWrapper()
	{

	}

	// -----------------------------------------------------------------------
	// TA3D_AppWrapper:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Abstract function defined by user<BR>
	 * Is called before F3D engine initialization<BR>
	 * Add here Config setup for create Display<BR>
	 * Note: F3D objects are not now created now.
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	public void onConfigure()
	{
		if (F3D.Config.io_preload_source_mode.equals("PRELOAD_FROM_JAR"))
		{
			// [2] disable auto preloading data like (.event, .texture, .a3da ,
			// .mat )
			F3D.Config.io_preload_data = false;

			// [3] load list of files stored in media folder
			F3D.AbstractFiles.Load("config/" + F3D.Config.io_preload_source_filelist);
		}
		F3D.Create();
		if (F3D.Config.io_preload_source_mode.equals("PRELOAD_FROM_JAR"))
		{
			F3D.Config.io_preload_data = true;
		}

		F3D.Log.info("TF3D_AppWrapper", "Application wrapper created");
		this.AppTerminate = false;
	}

	// -----------------------------------------------------------------------
	// TA3D_AppWrapper:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Abstract function defined by user<BR>
	 * Is called after F3D engine initialization <BR>
	 * Add here code for game/app initialization. OpenGL commands are allowed.<BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	public void onInitialize()
	{

	}

	// -----------------------------------------------------------------------
	// TA3D_AppWrapper:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Abstract function defined by user<BR>
	 * Is called every Main loop after 3D OpenGL render state setup<BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	public void onUpdate3D()
	{

	}

	// -----------------------------------------------------------------------
	// TA3D_AppWrapper:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Abstract function defined by user<BR>
	 * Is called every Main loop after 2D OpenGL render state setup<BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	public void onUpdate2D()
	{
	}

	// -----------------------------------------------------------------------
	// TA3D_AppWrapper:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Abstract function defined by user.<BR>
	 * Is called when main loop is closed<BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	public void OnDestroy()
	{

	}

	private void MouseInput()
	{
		F3D.Input.Mouse.Update();
	}
	// -----------------------------------------------------------------------
	// TF3D_AppWrapper:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Internal KeyPressed event for define KEY event<BR>
	 * [F12] - switch to fullsceen/windowscreen mode [ESCAPE] - terminate
	 * application <BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	private void KeyInput()
	{

		F3D.Input.Key.Update();

		if (F3D.Input.Key.IsKeyUp(Keyboard.KEY_ESCAPE))
			this.AppTerminate = true;
		if (F3D.Input.Key.IsKeyUp(Keyboard.KEY_F10))
			F3D.Config.use_physics_debug = !F3D.Config.use_physics_debug;
		if (F3D.Input.Key.IsKeyUp(Keyboard.KEY_F12))
			try
			{
				F3D.Display.SwitchSceenMode();
			} catch (LWJGLException e)
			{
				F3D.Log.error("TF3D_AppWrapper", "Error during swich display");
			}
			// make screenshot
			if (F3D.Input.Key.IsKeyDown(Keyboard.KEY_LCONTROL) & F3D.Input.Key.IsKeyDown(Keyboard.KEY_LSHIFT) & F3D.Input.Key.IsKeyUp(Keyboard.KEY_P))
			{
				F3D.Viewport.ScreenShot("screenshot.png", F3D.Config.r_display_width, F3D.Config.r_display_height);
			}
	}

	public void onGUI()
	{

	}

	// -----------------------------------------------------------------------
	// TF3D_AppWrapper:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * MAIN Rendering LOOP<BR>
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	private void MainLoop()
	{
		while (!this.AppTerminate && !Display.isCloseRequested())
		{

			F3D.Timer.Update();

			if (F3D.Config.use_physics)
				F3D.Physic.Update();

			if (F3D.Display.isVisible())
			{
				if (F3D.Config.io_keyboard_update) this.KeyInput();
				if (F3D.Config.io_mouse_update) this.MouseInput();

				F3D.Viewport.BeginRender3D();

				F3D.Worlds.UpdateWorld();
				F3D.Worlds.RenderWorld();

				this.onUpdate3D();

				if (F3D.Config.use_physics_debug)
				{
					F3D.Physic.dynamicsWorld.debugDrawWorld();
				}

				F3D.Viewport.EndRender3D();

				F3D.Viewport.BeginRender2D();

				this.onUpdate2D();

				if (F3D.Config.use_slick) F3D.Slick.graphics.resetTransform();
				
				F3D.Viewport.EndRender2D();

			}

			else

			{
				// no need to render/paint if nothing has changed (ie. window
				// dragged over)
				if (F3D.Display.isDirty())
				{

					F3D.Viewport.BeginRender3D();

					F3D.Worlds.UpdateWorld();

					this.onUpdate3D();

					F3D.Viewport.EndRender3D();

					F3D.Viewport.BeginRender2D();

					this.onUpdate2D();

					F3D.Viewport.EndRender2D();

				}
				// don't waste cpu time, sleep more
				try
				{
					Thread.sleep(100);
				} catch (InterruptedException inte)
				{
				}
			}

			F3D.Viewport.Flip();
		}
	}

	private void Destroy()
	{
		this.OnDestroy();
		F3D.Log.info("TF3D_AppWrapper", "Application wrapper destroyed");
		F3D.Destroy();

	}

	// -----------------------------------------------------------------------
	// TF3D_AppWrapper:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Execute user application as combination of F3D and user Overrides method <BR>
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	public void Execute()
	{

		// Initialize Final3D Engine
		try
		{
			this.onConfigure();
			F3D.Initialize();
			this.onInitialize();

		} catch (LWJGLException e)
		{

			e.printStackTrace();
			F3D.Log.error("TF3D_AppWrapper", "Failed to initialize F3D Engine/Application");
		}
		this.MainLoop();
		this.Destroy();

	}

}
