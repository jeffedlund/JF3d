package AGFX.F3D.Timer;

import org.lwjgl.util.Timer;


public class TF3D_Timer2
{

	
	private Timer timer;
    private int m_lastTick;
    private int m_deltaTick;
	private float m_fpsDuration;
	private int m_fpsFrames;
	private int m_fps;
	private int tick;
	private float ElapsedTime;	

	public TF3D_Timer2()
	{
	
		this.timer = new Timer();
		
	}

	
	public void Update()
	{
		Timer.tick();
		  // get the current tick value
		  tick = (int) (this.timer.getTime()*1000);
		  // calculate the amount of elapsed seconds
		  ElapsedTime = ( tick - m_lastTick ) / 1000.0f;
		  if (ElapsedTime != 0)
		  {
		  // adjust fps counter
		  m_fpsDuration = m_fpsDuration + ElapsedTime;
		  if ( m_fpsDuration >= 1.0 ) 
		  {
		    m_fps = Math.round( m_fpsFrames / m_fpsDuration );
		    m_fpsDuration = 0;
		    m_fpsFrames= 0;
		  }
		  m_deltaTick = tick - m_lastTick;
		  m_lastTick = tick;
		  m_fpsFrames++;
		//  glFlush();
		  }
	}

	public float AppSpeed()
	{

		return this.ElapsedTime*10.0f;
	}

	public int GetFPS()
	{
		return (int) this.m_fps;
	}

	
	public int GetDelta()
	{
		return this.m_deltaTick;
	}
	
	public int GetTickCount()
	{
		return (int) this.m_lastTick;
	}
	

}