package AGFX.F3D.Timer;


import sun.misc.Perf;



public class TF3D_Timer
{

	// the hight-resolution timer object we are using to work
	private Perf    perf             = sun.misc.Perf.getPerf();

	// the number of ticks per second
	private long    ticksPerSecond   = perf.highResFrequency();
	
	
	protected float minimumtimeLapse = 1.0f / 30f;
	// temporary variables used to calculate the framerate
	private long    lastSecondTicks  = 0;
	private long    newFrameTicks    = 0;
	private long    oldFrameTicks    = 0;
	private long    frameTicks       = 0;
	private int     frameCounter     = 0;
	// private int temp = 0;
	private int     secondFPS        = 0;
	private float   averageFPS       = 0;
	private float   realFPS          = 0;
	private float   mTimeLapse       = 0.0f;

	// private double realFPS = 0.0;
	private int     numberFPS        = 15;
	private float   FPS[];
	
	

	public TF3D_Timer()
	{
		FPS = new float[numberFPS];
		
	}

	void calculateRealFPS()
	{
		int i = 0;
		realFPS = FPS[0];
		for (i = 1; i < numberFPS; i++)
		{
			FPS[i - 1] = FPS[i];
			realFPS += FPS[i];
		}
		;
		FPS[i - 1] = mTimeLapse;
		realFPS = (i - 1) / realFPS;

	};

	public void Update()
	{
		if (newFrameTicks == 0)
		{
			// start time measurement
			lastSecondTicks = perf.highResCounter();
			
			newFrameTicks = lastSecondTicks;
			frameCounter = 0;
			oldFrameTicks = lastSecondTicks;
		} else
		{
			// update number of frames and ticks
			frameCounter++;
			newFrameTicks = perf.highResCounter();
			
			frameTicks = (newFrameTicks - oldFrameTicks);
			mTimeLapse = (float) frameTicks / ticksPerSecond;
			// to ensure a minimum time granularity for coehsive movement even
			// in low frame rate
			// conditions
			if (mTimeLapse > minimumtimeLapse)
				mTimeLapse = minimumtimeLapse;
			// update the counter every second
			calculateRealFPS(); // DISABLED -> BUGGY
			if (newFrameTicks - lastSecondTicks > ticksPerSecond)
			{
				// display the new framerate
				secondFPS = frameCounter;
				averageFPS = (frameCounter + averageFPS) * 0.5f;

				// set the frame counter back
				frameCounter = 0;

				// add a "second"
				lastSecondTicks += ticksPerSecond;
			}

			oldFrameTicks = newFrameTicks;
			// newFrameTicks = 0;
			// frameTicks = 0;
		}

	}

	public float AppSpeed()
	{

		return mTimeLapse;
	}

	public int GetFPS()
	{
		return (int) realFPS;
	}

	public int getAverageFPS()
	{
		return (int) averageFPS;
	}

	public int getLastSecondFPS()
	{
		return secondFPS;
	}

	public String getTimingString()
	{
		return new String("Current FPS : " + realFPS + " Last Second FPS : " + secondFPS + " Average FPS : " + averageFPS + "");
	}

	public String getShortTimingString()
	{
		return new String("FPS : " + realFPS + "");
	}

	public float getMinimumtimeLapse()
	{
		return minimumtimeLapse;
	}
	
	public int GetTickCount()
	{
		return (int) this.newFrameTicks;
	}
	

}