/**
 * 
 */
package demos;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_STENCIL_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import java.io.IOException;

import org.fenggui.FengGUI;
import org.fenggui.actor.ScreenshotActor;

import org.fenggui.binding.render.lwjgl.EventHelper;
import org.fenggui.binding.render.lwjgl.LWJGLBinding;
import org.fenggui.example.Everything;
import org.fenggui.theme.ITheme;
import org.fenggui.theme.XMLTheme;
import org.fenggui.theme.xml.IXMLStreamableException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import AGFX.F3D.F3D;
import AGFX.F3D.AppWrapper.TF3D_AppWrapper;
import AGFX.F3D.Camera.TF3D_Camera;
import AGFX.F3D.Config.TF3D_Config;
import AGFX.F3D.Hud.TF3D_HUD_Image;
import AGFX.F3D.Light.TF3D_Light;
import AGFX.F3D.Material.TF3D_Material;
import AGFX.F3D.Model.TF3D_Model;
import AGFX.F3D.Skybox.TF3D_Skybox;

/**
 * @author AndyGFX
 * 
 */
public class Demo_FengGUIasFBO extends TF3D_AppWrapper
{

	org.fenggui.Display     desk           = null;
	private int             lastButtonDown = -1;
	private ScreenshotActor screenshotActor;
	private ITheme          theme;
	public int              fbo_id;
	public TF3D_Camera      Camera;
	public TF3D_Model       model;
	public TF3D_HUD_Image   mouse_cur;

	public Demo_FengGUIasFBO()
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
			F3D.Config.use_gl_light = false;
			
			super.onConfigure();
			
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void onInitialize()
	{

		// build the gui
		buildGUI();

		screenshotActor = new ScreenshotActor();
		screenshotActor.hook(desk);

		F3D.Worlds.CreateWorld("MAIN_WORLD");

		this.Camera = new TF3D_Camera("FPSCamera");
		this.Camera.SetPosition(0.0f, 0.0f, 10.0f);
		this.Camera.Sky = new TF3D_Skybox();
		
		this.Camera.ctype = F3D.CAMERA_TYPE_FPS;

		F3D.Cameras.Add(this.Camera);
		F3D.Worlds.SetCamera(this.Camera);

		// Add light to scene
		TF3D_Light light = new TF3D_Light("light_0", 0);
		light.SetPosition(0, 0, 3);		
		light.Enable();

		F3D.Meshes.Add("abstract::test_uv_map.a3da");

		this.model = new TF3D_Model("UVPLANE");
		this.model.AssignMesh("abstract::test_uv_map.a3da");		

		this.model.SetScale(3f, 2.5f, 1f);
		this.model.SetRotation(180, 0, 0);
		
		fbo_id = F3D.FrameBuffers.Add("3DGUI_FBO", 800,600,false,1);
		F3D.Textures.Add("3DGUI_TEXTURE", fbo_id, false);		
		
		
		//F3D.Textures.ReplaceWithFBO("UV_MAP", fbo_id);
		
		
		TF3D_Material mat = new TF3D_Material();
		mat.name = "MAT_3DGUI";
		mat.texture_unit[0].bTexture = true;
		mat.texture_unit[0].texture_name = "3DGUI_TEXTURE";
		mat.texture_unit[0].texture_id = F3D.Textures.FindByName("3DGUI_TEXTURE");
			
		F3D.Surfaces.Add(mat);
		
		
		this.model.ChangeSurface("MATuvmap", "MAT_3DGUI");
		
		// TODO fix flip FBO buffer !!!
		F3D.Viewport.DoubleFace(true);
		
		
		// create cursor
		
		this.mouse_cur = new TF3D_HUD_Image();
		this.mouse_cur.texture_id0 =  F3D.Textures.FindByName("mouse");
		// Add image FX
		this.mouse_cur.size.set(64, 64);
		this.mouse_cur.property.Autosize = false;
		this.mouse_cur.property.Texture = true;
		this.mouse_cur.property.Blend = true;
		this.mouse_cur.color.set(1.0f, 1.0f, 1.0f, 1.0f);
		this.mouse_cur.transform.scroll.x = 0.0f;
		this.mouse_cur.transform.scroll.y = 0.0f;
		this.mouse_cur.transform.rotate = 0.0f;
		this.mouse_cur.scale.set(1.0f, 1.0f);
		this.mouse_cur.shape_angle = 0.0f;
		this.mouse_cur.shape_origin.set(0, 0);
		
		this.mouse_cur.Initialize();
		
		// Hide cursor
		Mouse.setGrabbed(true);
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
	}

	@Override
	public void onGUI()
	{

	}

	@Override
	public void onUpdate2D()
	{
		
		F3D.Viewport.DrawInfo(0,0);
		
		// render gui
		readBufferedKeyboard();
		readBufferedMouse();

		F3D.FrameBuffers.BeginRender(this.fbo_id);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
		// clean
		/*
		F3D.Surfaces.ApplyMaterial(F3D.Surfaces.FindByName("MATuvmap"));
		F3D.Draw.Rectangle(0, 0, 800, 600,true);
		*/
		F3D.Textures.DeactivateLayers();
		F3D.Textures.ActivateLayer(0);
		
		
		
		desk.display();
		screenshotActor.renderToDos(desk.getBinding().getOpenGL(), 800, 600);
		
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		this.mouse_cur.DrawAt((float)Mouse.getX()/2f, (float) (600-Mouse.getY())/2f);
		
		
		F3D.Textures.DeactivateLayers();
		F3D.Textures.ActivateLayer(0);
		
		
		
		
		F3D.FrameBuffers.EndRender(this.fbo_id,true,true);
		
		
		/*
		F3D.Textures.Bind("UV_MAP");		
		//F3D.Surfaces.ApplyMaterial(F3D.Surfaces.FindByName("MATuvmap"));
		F3D.Draw.Rectangle(0, 0, 400, 300,true);
		*/
		
	}

	@Override
	public void OnDestroy()
	{

	}

	public void buildGUI()
	{
		// init. the LWJGL Binding
		LWJGLBinding binding = new LWJGLBinding();

		// init the root Widget, that spans the whole
		// screen (i.e. the OpenGL context within the
		// Microsoft XP Window)
		desk = new org.fenggui.Display(binding);
		// build a simple test FengGUI-Window
		Everything everything = new Everything();
		everything.buildGUI(desk);

		try
		{
			theme = new XMLTheme("data/themes/QtCurve/QtCurve.xml");
		} catch (IOException e)
		{
			e.printStackTrace();
		} catch (IXMLStreamableException e)
		{
			e.printStackTrace();
		}
		FengGUI.setTheme(theme);

	}

	private void readBufferedKeyboard()
	{

		// check keys, buffered
		Keyboard.poll();

		while (Keyboard.next())
		{
			if (Keyboard.getEventKeyState()) // if pressed
			{
				desk.fireKeyPressedEvent(EventHelper.mapKeyChar(), EventHelper.mapEventKey());

				desk.fireKeyTypedEvent(EventHelper.mapKeyChar());
			} else
			{
				desk.fireKeyReleasedEvent(EventHelper.mapKeyChar(), EventHelper.mapEventKey());
			}
		}

	}

	/**
	 * reads a mouse in buffered mode
	 */
	private void readBufferedMouse()
	{
		int x = Mouse.getX();
		int y = Mouse.getY();

		boolean hitGUI = false;

		// @todo the click count is not considered in LWJGL! #

		if (lastButtonDown != -1 && Mouse.isButtonDown(lastButtonDown))
		{
			hitGUI |= desk.fireMouseDraggedEvent(x, y, EventHelper.getMouseButton(lastButtonDown), 1);
		} else
		{
			if (Mouse.getDX() != 0 || Mouse.getDY() != 0)
				hitGUI |= desk.fireMouseMovedEvent(x, y, EventHelper.getMouseButton(lastButtonDown), 1);

			if (lastButtonDown != -1)
			{
				desk.fireMouseClickEvent(x, y, EventHelper.getMouseButton(lastButtonDown), 1);
				hitGUI |= desk.fireMouseReleasedEvent(x, y, EventHelper.getMouseButton(lastButtonDown), 1);
				lastButtonDown = -1;
			}
			while (Mouse.next())
			{
				if (Mouse.getEventButton() != -1 && Mouse.getEventButtonState())
				{
					lastButtonDown = Mouse.getEventButton();
					hitGUI |= desk.fireMousePressedEvent(x, y, EventHelper.getMouseButton(lastButtonDown), 1);
				}
				int wheel = Mouse.getEventDWheel();
				if (wheel != 0)
				{
					hitGUI |= desk.fireMouseWheel(x, y, wheel > 0, 1, 1);
				}
			}
		}
	}

	public static void main(String[] args)
	{

		new Demo_FengGUIasFBO().Execute();
		System.exit(0);

	}

}
