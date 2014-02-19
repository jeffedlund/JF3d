/**
 * 
 */
package demos;

import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import AGFX.F3D.F3D;
import AGFX.F3D.AppWrapper.TF3D_AppWrapper;
import AGFX.F3D.Billboard.TF3D_Billboard;
import AGFX.F3D.Camera.TF3D_Camera;
import AGFX.F3D.Config.TF3D_Config;
import AGFX.F3D.Light.TF3D_Light;
import AGFX.F3D.Material.TF3D_Material;

import AGFX.F3D.Particles.TF3D_Particle_Sprite;
import AGFX.F3D.Particles.TF3D_Particles;


/**
 * @author AndyGFX
 *
 */
public class Demo_Particles extends TF3D_AppWrapper
{

	public TF3D_Camera Camera;
	public TF3D_Particles particles;
	public TF3D_Material mat;

	int odx = 0;
	int ody = 0;
	
	public Demo_Particles()
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
			F3D.Config.r_display_title = "jFinal3D Graphics Engine 2010 - PARTICLE SYSTEM";
			F3D.Config.use_physics_debug = false;

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
		this.Camera.SetPosition(0.0f, 3.0f,10.0f);
		this.Camera.SetRotation(0, 0, 0);
		this.Camera.ctype = F3D.CAMERA_TYPE_FPS;
		
		F3D.Cameras.Add(this.Camera);
		F3D.Worlds.SetCamera(this.Camera);
	
		// Add light to scene
		TF3D_Light light = new TF3D_Light("light_0", 0);
		light.SetPosition(3, 3, 3);
		light.Enable();
	
		// get MATERIAL ID
		int mid=F3D.Surfaces.FindByName("MAT_BEAM_3");
		
		// create sprite for clone particle elements
		TF3D_Particle_Sprite sprite1 = new TF3D_Particle_Sprite();

		// define sprite properties
		sprite1.mode = F3D.BM_SPRITE;
		sprite1.name = "TMP_Particle_sprite";
		sprite1.enable = true;
		sprite1.SetScale(1.0f, 1.0f, 0.0f);
		sprite1.bFadeAlpha = false;
		sprite1.bDepthSort = true;
		int material_id = F3D.Surfaces.FindByName("MAT_BEAM_3");
		sprite1.material = F3D.Surfaces.materials.get(material_id).Clone();		
		sprite1.SetScale(0.5f, 0.5f, 0.5f);
		sprite1.Dir.set(0, 0, 0);
		
		// create particle emitter
		this.particles = new TF3D_Particles("PARTICLE_01",10,sprite1,100f);
		this.particles.SetGravity(0, 0.1f, 0);
		this.particles.SetDirection(0f, 0, 0);
		this.particles.SetPosition(3,0,0);
		this.particles.setColorStart(new Vector4f(0,0,0,1));
		this.particles.setColorEnd(new Vector4f(1,1,1,0));
		this.particles.setScaleStart(new Vector3f(1,1,1));
		this.particles.setScaleEnd(new Vector3f(1,1,1));
		this.particles.Init();
		
		
	}
	
	
	@Override
	public void onUpdate3D()
	{
		
		
		

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
			this.Camera.Move(0.0f, 0.0f, -0.05f);
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_A))
		{
			this.Camera.Move(-0.05f, 0.0f, 0.0f);
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_S))
		{
			this.Camera.Move(0.0f, 0.0f, 0.05f);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D))
		{
			this.Camera.Move(0.05f, 0.0f, 0.0f);
		}
		
		
		
		

		F3D.Draw.Axis(2.0f);	
	
		F3D.Textures.ActivateLayer(0);
		
		if(F3D.Input.Key.IsKeyUp(Keyboard.KEY_TAB)) this.particles.createBurst();
	}
	
	
	@Override
	public void onUpdate2D()
	{
		
		F3D.Viewport.DrawInfo(0,0);
		
	}
	
	@Override
	public void OnDestroy()
	{
		
	}
	public static void main(String[] args)
	{
		new Demo_Particles().Execute();		
		System.exit(0); 
	}

}
