/**
 * 
 */
package demos;



import javax.vecmath.Vector3f;
import org.fenggui.FengGUI;
import org.fenggui.TextEditor;
import org.fenggui.composite.Window;
import org.fenggui.util.Point;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import AGFX.F3D.F3D;
import AGFX.F3D.AppWrapper.TF3D_AppWrapper;
import AGFX.F3D.Camera.TF3D_Camera;
import AGFX.F3D.Config.TF3D_Config;
import AGFX.F3D.Light.TF3D_Light;

import AGFX.F3D.Math.TF3D_Matrix;
import AGFX.F3D.Model.TF3D_Model;

/**
 * @author AndyGFX
 * 
 */
public class Demo_FengGUI_UserDefined extends TF3D_AppWrapper
{

	public TF3D_Camera Camera;
	public TF3D_Model  model;
	public Vector3f    pos2D;
	public TF3D_Matrix mat;

	int                odx = 0;
	int                ody = 0;

	public Window      window1;

	public Demo_FengGUI_UserDefined()
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
			F3D.Config.r_display_title = "jFinal3D Graphics Engine 2010 - CAMERA CONTROL";
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
		this.Camera.SetPosition(0.0f, 3.0f, 10.0f);
		this.Camera.SetRotation(0, 0, 0);
		this.Camera.ctype = F3D.CAMERA_TYPE_FPS;

		F3D.Cameras.Add(this.Camera);
		F3D.Worlds.SetCamera(this.Camera);

		// Add light to scene
		TF3D_Light light = new TF3D_Light("light_0", 0);
		light.SetPosition(3, 3, 3);
		light.Enable();

		F3D.Meshes.Add("abstract::karoseria.a3da");

		this.model = new TF3D_Model("AUTO");
		this.model.AssignMesh("abstract::karoseria.a3da");

		this.mat = new TF3D_Matrix();
		this.mat.LoadIdentity();
		F3D.Log.info("MAIN", mat.toString());

		this.Initialize_FengGUI();
	}

	@Override
	public void onUpdate3D()
	{

		if (Mouse.isInsideWindow())
		{
			if (Mouse.isButtonDown(0))
			{
				float dx = (float) Mouse.getDX() / 10.0f;
				float dy = (float) Mouse.getDY() / 10.0f;

				this.Camera.Turn(-dy, dx, 0.0f);

				if (Mouse.getX() < 3)
				{
					Mouse.setCursorPosition(F3D.Config.r_display_width - 4, Mouse.getY());
				}
				if (Mouse.getX() > F3D.Config.r_display_width - 3)
				{
					Mouse.setCursorPosition(4, Mouse.getY());
				}
				if (Mouse.getY() < 3)
				{
					Mouse.setCursorPosition(Mouse.getX(), F3D.Config.r_display_height - 4);
				}
				if (Mouse.getY() > F3D.Config.r_display_height - 3)
				{
					Mouse.setCursorPosition(Mouse.getX(), 4);
				}
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

		pos2D = F3D.MathUtils.World3DtoScreen2D(this.model.GetPosition());

	}

	@Override
	public void onUpdate2D()
	{

		
		F3D.Gui.Render();

	}

	@Override
	public void OnDestroy()
	{

	}

	public static void main(String[] args)
	{
		new Demo_FengGUI_UserDefined().Execute();
		System.exit(0);
	}

	public void Initialize_FengGUI()
	{

		F3D.Gui.SetTheme("data/themes/jF3D/jF3D_theme.xml");

	
		this.window1 = FengGUI.createWindow(true, true, false, true);
		window1.setTitle("TEST WIndow");
		window1.setSize(320, 200);
		window1.setPosition(new Point(50, 200));

		final TextEditor text = FengGUI.createWidget(TextEditor.class);
		text.setMultiline(false);
		text.setText("text ...");
		this.window1.addWidget(text);
		
		text.setEnabled(true);		
		F3D.Gui.display.addWidget(this.window1);
		
		F3D.Gui.display.addWidget(text);
		
	
		
		
	}

	

	
}
