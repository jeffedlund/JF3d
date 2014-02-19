/**
 * 
 */
package AGFX.F3D.ObjectEvent;

import AGFX.F3D.Callback.TF3D_TimeEventCallBack;

/**
 * @author AndyGFX
 * 
 */
public class TF3D_ObjectTimeEvent
{
	public String                 name;
	public int                    time_to_start  = 0;
	public int                    time_to_end    = 0;
	public int                    time_to_finish = 0;
	public Boolean                repeat_onPlay  = false;
	public Boolean                repeat_onEnd   = false;
	public TF3D_TimeEventCallBack onInit         = null;
	public TF3D_TimeEventCallBack onPlay         = null;
	public TF3D_TimeEventCallBack onEnd          = null;
	public TF3D_TimeEventCallBack onFinish       = null;
	public Object                 object         = null;
	public Boolean                loop           = false;
	private Boolean               enable         = true;
	private int                   interval       = 0;
	private Boolean               stop_play      = false;
	private Boolean               stop_end       = false;

	public TF3D_ObjectTimeEvent(String name)
	{
		this.name = name;
		this.enable = true;

	}

	public void Execute()
	{

		if (this.onInit != null)
		{
			this.onInit.Call(this.object);
		}

		this.interval = (int) System.currentTimeMillis();
	}

	public void Update()
	{

		int current_time = (int) System.currentTimeMillis() - this.interval;

		// F3D.Log.info("time", String.valueOf(current_time));
		if (this.enable)
		{
			// when exist callback PLAY
			if (this.onPlay != null)
			{
				// OnPLAY
				if ((current_time >= this.time_to_start))
				{
					// execute LOOP
					if ((this.repeat_onPlay == true) & (current_time <= this.time_to_end))
					{
						this.onPlay.Call(this.object);
					}

					// execute ONCE
					if ((this.repeat_onPlay == false) & (this.stop_play == false))
					{

						this.onPlay.Call(this.object);
						this.stop_play = true;
					}

				}

			}

			// when exist callback END
			if (this.onEnd != null)
			{
				// OnEND
				if ((current_time >= this.time_to_end))
				{
					// execute LOOP
					if ((this.repeat_onEnd == true) & (current_time <= this.time_to_finish))
					{
						this.onEnd.Call(this.object);
					}

					// execute ONCE
					if ((this.repeat_onEnd == false) & (this.stop_end == false))
					{

						this.onEnd.Call(this.object);
						this.stop_end = true;
					}

				}

			}

			// when exist callback FINISH
			if (this.onFinish != null)
			{
				// OnEND
				if ((current_time >= this.time_to_finish))
				{
					this.onFinish.Call(this.object);

					if (this.loop)
					{
						this.interval = (int) System.currentTimeMillis();
						this.stop_play = false;
						this.stop_end = false;
					} else
					{
						this.enable = false;
					}

				}
			}
		}
	}
}