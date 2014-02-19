/**
 * 
 */
package demos;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import AGFX.F3D.F3D;
import AGFX.F3D.AppWrapper.TF3D_AppWrapper;
import AGFX.F3D.Camera.TF3D_Camera;
import AGFX.F3D.Config.TF3D_Config;
import AGFX.F3D.Light.TF3D_Light;

import AGFX.F3D.Model.TF3D_Model;

/**
 * @author AndyGFX
 *
 */
public class Demo_Performance extends TF3D_AppWrapper
{

	public TF3D_Camera Camera;
	
	public int VisCount = 0;
	public int TotalVisCount = 0;
	
	public Demo_Performance()
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
			F3D.Config.r_display_title = "jFinal3D Graphics Engine 2010 - "+ this.getClass().getName();

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
		
		this.Camera = new TF3D_Camera("FPSCamera");
		this.Camera.SetPosition(0.0f, 0.0f, -10.0f);
		this.Camera.SetRotation(0, 180, 0);
		this.Camera.ctype = F3D.CAMERA_TYPE_FPS;
		
		F3D.Cameras.Add(this.Camera);
		F3D.Worlds.SetCamera(this.Camera);
		
		F3D.Meshes.Add("abstract::Cube.a3da");
		
		// Add light to scene
		TF3D_Light light = new TF3D_Light("light_0", 0);
		light.SetPosition(3, 3, 3);
		light.Enable();
		
		TF3D_Model model;
		int hc = 10;
		TotalVisCount = (hc*2) *(hc*2)*(hc*2);
		float offset = 1.2f;
		for(int px=-hc;px<hc;px++)
		{
			for(int py=-hc;py<hc;py++)
			{
				for(int pz=-hc;pz<hc;pz++)
				{		
					model = new TF3D_Model("Model_test");
					model.SetPosition(px*offset, py*offset, pz*offset);
					model.AssignMesh(F3D.Meshes.FindByName("abstract::Cube.a3da"));
					
					
				}
			}
		}
	}
	
	
	@Override
	public void onUpdate3D()
	{
		F3D.Draw.Axis(2.0f);	
		
		
		if (Mouse.isInsideWindow())
		{
    		if (Mouse.isButtonDown(0))
    		{
    			float dx = (float)Mouse.getDX()/10.0f;
    			float dy = (float)Mouse.getDY()/10.0f;
    			
    			this.Camera.Turn( -dy, dx, 0.0f);
    		
    			if (Mouse.getX()<3) {Mouse.setCursorPosition(F3D.Config.r_display_width-4,Mouse.getY());}
        		if (Mouse.getX()>F3D.Config.r_display_width-3) {Mouse.setCursorPosition(4,Mouse.getY());}
        		if (Mouse.getY()<3) {Mouse.setCursorPosition(Mouse.getX(),F3D.Config.r_display_height-4);}
        		if (Mouse.getY()>F3D.Config.r_display_height-3) {Mouse.setCursorPosition(Mouse.getX(),4);}
    		}
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_W))
		{
			this.Camera.Move(0.0f, 0.0f, -0.1f);
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_A))
		{
			this.Camera.Move(-0.1f, 0.0f, 0.0f);
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_S))
		{
			this.Camera.Move(0.0f, 0.0f, 0.1f);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D))
		{
			this.Camera.Move(0.1f, 0.0f, 0.0f);
		}
		
		this.VisCount = 0;
		/*
		for(int m=0;m<F3D.World.entities.size();m++)
		{
			if (F3D.World.entities.get(m).classname==F3D.CLASS_MODEL)
			{
				F3D.World.entities.get(m).Turn(0, 2.0f*F3D.Timer.AppSpeed(), 0);
				if (F3D.World.entities.get(m).visibility)
				{
					this.VisCount++;
				}
			}
		}
		*/
	}
	
	
	@Override
	public void onUpdate2D()
	{
		F3D.Viewport.DrawInfo(0,0);
		Display.setTitle("jFinal3D Graphics Engine 2010 - PERFORMANCE TEST[ ObjectView: "+this.VisCount+"/"+TotalVisCount+"]");
	}
	
	@Override
	public void OnDestroy()
	{
		
	}
	public static void main(String[] args)
	{

		new Demo_Performance().Execute();		
		System.exit(0); 
	}

}
