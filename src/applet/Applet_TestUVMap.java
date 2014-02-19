/**
 * 
 */
package applet;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Canvas;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import demos.Demo_TestUVMap;




/**
 * @author AndyGFX
 *
 */
public class Applet_TestUVMap extends Applet
{
	/**  */
    private static final long serialVersionUID = 1L;

	/** The Canvas where the LWJGL Display is added */
	Canvas          display_parent;

	/** Thread which runs the main game loop */
	Thread          gameThread;

	/** The Game instance */
	Demo_TestUVMap APP;


	public void startLWJGL()
	{
		gameThread = new Thread()
		{
			public void run()
			{

				try
				{
					Display.setParent(display_parent);

				} catch (LWJGLException e)
				{
					e.printStackTrace();
				}
				// start game
				APP = new Demo_TestUVMap();
				
				APP.Execute();
			}
		};
		gameThread.start();
	}

	/**
	 * Tell game loop to stop running, after which the LWJGL Display will be
	 * destoryed. The main thread will wait for the Display.destroy() to
	 * complete
	 */
	private void stopLWJGL()
	{
		// APP.gameRunning = false;
		try
		{
			gameThread.join();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public void start()
	{

	}

	public void stop()
	{

	}

	/**
	 * Applet Destroy method will remove the canvas, before canvas is destroyed
	 * it will notify stopLWJGL() to stop main game loop and to destroy the
	 * Display
	 */
	public void destroy()
	{
		remove(display_parent);
		super.destroy();
		System.out.println("Clear up");
	}

	/**
	 * initialise applet by adding a canvas to it, this canvas will start the
	 * LWJGL Display and game loop in another thread. It will also stop the game
	 * loop and destroy the display on canvas removal when applet is destroyed.
	 */
	public void init()
	{
		setLayout(new BorderLayout());
		try
		{
			display_parent = new Canvas()
			{
				/**  */
                private static final long serialVersionUID = 202517488977311139L;

				public void addNotify()
				{
					super.addNotify();
					startLWJGL();
				}

				public void removeNotify()
				{
					stopLWJGL();
					super.removeNotify();
				}
			};
			display_parent.setSize(getWidth(), getHeight());
			add(display_parent);
			display_parent.setFocusable(true);
			display_parent.requestFocus();
			display_parent.setIgnoreRepaint(true);
			setVisible(true);
		} catch (Exception e)
		{
			System.err.println(e);
			throw new RuntimeException("Unable to create display");
		}
	}
}