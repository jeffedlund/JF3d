/**
 * 
 */
package demos;


import javax.vecmath.Vector3f;


import AGFX.F3D.F3D;
import AGFX.F3D.AppWrapper.TF3D_AppWrapper;
import AGFX.F3D.Camera.TF3D_Camera;
import AGFX.F3D.Config.TF3D_Config;
import AGFX.F3D.Light.TF3D_Light;

/**
 * @author AndyGFX
 * 
 */
public class Demo_ResourceFrom_JAR extends TF3D_AppWrapper
{
	public TF3D_Camera Camera;
	public int         id;
	
	public Demo_ResourceFrom_JAR()
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
			F3D.Config.r_display_vsync = false;
			F3D.Config.r_display_title = "jFinal3D Graphics Engine 2010 - Reasurce from JAR";
			
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
		this.Camera.SetPosition(5.0f, 5.0f, -5.0f);
		this.Camera.TargetPoint = new Vector3f(0, 0, 0);
		this.Camera.ctype = F3D.CAMERA_TYPE_TARGET;

		F3D.Cameras.Add(this.Camera);
		F3D.Worlds.SetCamera(this.Camera);

		F3D.Meshes.Add("abstract::Capsule.a3da");

		id = F3D.Meshes.FindByName("abstract::Capsule.a3da");

		// Add light to scene
		TF3D_Light light = new TF3D_Light("light_0", 0);
		light.SetPosition(3, 3, 3);
		light.Enable();

	}

	@Override
	public void onUpdate3D()
	{
		F3D.Meshes.items.get(id).Render();
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

		new Demo_ResourceFrom_JAR().Execute();
		System.exit(0);

	}

}
