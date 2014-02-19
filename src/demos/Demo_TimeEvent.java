/**
 * 
 */
package demos;

import javax.vecmath.Vector3f;
import AGFX.F3D.F3D;
import AGFX.F3D.AppWrapper.TF3D_AppWrapper;
import AGFX.F3D.Callback.TF3D_TimeEventCallBack;
import AGFX.F3D.Config.TF3D_Config;
import AGFX.F3D.ObjectEvent.TF3D_ObjectTimeEvent;

/**
 * @author AndyGFX
 * 
 */
public class Demo_TimeEvent extends TF3D_AppWrapper
{

	public TF3D_ObjectTimeEvent event;

	public Demo_TimeEvent()
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
			F3D.Config.r_display_title = "jFinal3D Graphics Engine 2010 - app";

			super.onConfigure();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void onInitialize()
	{

		Vector3f my_object = new Vector3f(0,0,0);
		
		this.event = new TF3D_ObjectTimeEvent("EVENT1");
		
		// DEFINE INIT SUB-EVENT
		this.event.onInit = new TF3D_TimeEventCallBack()
		{

			Vector3f v;

			@Override
			public void Call(Object obj)
			{
				this.v = (Vector3f) obj;

				F3D.Log.info("INIT", v.toString());
			}

		};
		
		// DEFINE PLAY SUB-EVENT
		
		this.event.time_to_start = 1000;
		this.event.repeat_onPlay = false;
		this.event.onPlay = new TF3D_TimeEventCallBack()
		{

			Vector3f v;

			@Override
			public void Call(Object obj)
			{
				this.v = (Vector3f) obj;
				this.v.add(new Vector3f(1,0,0));

				F3D.Log.info("PLAY", v.toString());
			}

		};

		// DEFINE END SUB-EVENT
		
		this.event.time_to_end = 2000;		
		this.event.repeat_onEnd = false;
		this.event.onEnd = new TF3D_TimeEventCallBack()
		{

			Vector3f v;

			@Override
			public void Call(Object obj)
			{
				this.v = (Vector3f) obj;
				this.v.add(new Vector3f(0,-1,0));

				F3D.Log.info("END", v.toString());
			}

		};
		
		
		// DEFINE FINISH SUB-EVENT
		this.event.time_to_finish = 3000;
		this.event.onFinish = new TF3D_TimeEventCallBack()
		{

			Vector3f v;

			@Override
			public void Call(Object obj)
			{
				this.v = (Vector3f) obj;
				v.set(0,0,0);
				F3D.Log.info("FINISH", v.toString());
			}

		};
		this.event.loop = true;
		this.event.object = my_object;
		this.event.Execute();
	}

	@Override
	public void onUpdate3D()
	{
		
		this.event.Update();

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

		new Demo_TimeEvent().Execute();
		System.exit(0);

	}

}
