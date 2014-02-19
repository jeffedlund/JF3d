package demos;

import javax.vecmath.*;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.RoundedRectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;


import AGFX.F3D.F3D;
import AGFX.F3D.AppWrapper.TF3D_AppWrapper;
import AGFX.F3D.Camera.TF3D_Camera;
import AGFX.F3D.Config.TF3D_Config;
import AGFX.F3D.Light.TF3D_Light;
import AGFX.F3D.Model.TF3D_Model;
import AGFX.F3D.Parser.TF3D_PARSER;
import AGFX.F3D.Texture.TF3D_Texture;

public class Demo_SlickGeom extends TF3D_AppWrapper
{

	public TF3D_Camera		Camera;
	public TF3D_Texture		Tex;
	public float			angle	= 0;
	public TF3D_PARSER		PARSER;
	public TF3D_Model		model;
	public int				surface_id;

	/** The rectangle drawn */
	private Shape			rect;
	/** The rectangle drawn */
	private Shape			circle;
	/** The rectangle tested */
	private Shape			rect1;
	/** The rectangle tested */
	private Shape			rect2;
	/** The circle tested */
	private Shape			circle1;
	/** The circle tested */
	private Shape			circle2;
	/** The circle tested */
	private Shape			circle3;
	/** The circle tested */
	private Shape			circle4;
	/** The RoundedRectangle tested */
	private Shape			roundRect;
	/** The RoundedRectangle tested - less cornders */
	private Shape			roundRect2;

	
	public Demo_SlickGeom()

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
			F3D.Config.r_display_title = "jFinal3D Graphics Engine 2010 - " + this.getClass().getName();

			// [1] set resource destination
			//F3D.Config.io_preload_source_mode = "PRELOAD_FROM_JAR";

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
		this.Camera.SetPosition(2.0f, 2.0f, -5.0f);
		this.Camera.TargetPoint = new Vector3f(0, 2, 0);
		this.Camera.ctype = F3D.CAMERA_TYPE_TARGET;

		F3D.Cameras.Add(this.Camera);
		F3D.Worlds.SetCamera(this.Camera);

		// Add light to scene
		TF3D_Light light = new TF3D_Light("light_0", 0);
		light.SetPosition(3, 3, 3);
		light.Enable();

		this.surface_id = F3D.Surfaces.FindByName("MAT_text_a");

		F3D.Meshes.Add("abstract::Cube.a3da");

		this.model = new TF3D_Model("Cube");
		this.model.AssignMesh(F3D.Meshes.FindByName("abstract::Cube.a3da"));
		this.model.SetPosition(0, 2, 0);
		this.model.Enable();
		this.model.ChangeSurface("Cube_MAT_095A", "MAT_test_alpha_blend");

		this.rect = new Rectangle(100, 100, 100, 100);
		this.circle = new Circle(500, 200, 50);
		this.rect1 = new Rectangle(150, 120, 50, 100).transform(Transform.createTranslateTransform(50, 50));
		this.rect2 = new Rectangle(310, 210, 50, 100).transform(Transform.createRotateTransform((float) Math.toRadians(45), 335, 260));
		this.circle1 = new Circle(150, 90, 30);
		this.circle2 = new Circle(310, 110, 70);
		this.circle3 = new Ellipse(510, 150, 70, 70);
		this.circle4 = new Ellipse(510, 350, 30, 30).transform(Transform.createTranslateTransform(-510, -350)).transform(Transform.createScaleTransform(2, 2)).transform(Transform.createTranslateTransform(510, 350));
		this.roundRect = new RoundedRectangle(50, 175, 100, 100, 20);
		this.roundRect2 = new RoundedRectangle(50, 280, 50, 50, 20, 20, RoundedRectangle.TOP_LEFT | RoundedRectangle.BOTTOM_RIGHT);

		
	}

	public void render()
	{

		F3D.Slick.graphics.setDrawMode(Graphics.MODE_NORMAL);

		F3D.Slick.graphics.setAntiAlias(true);
		F3D.Slick.graphics.setColor(Color.white);
			
		F3D.Slick.graphics.pushTransform();
		F3D.Slick.graphics.translate(100, 100);
		F3D.Slick.graphics.pushTransform();
		F3D.Slick.graphics.translate(-50, -50);
		F3D.Slick.graphics.scale(10, 10);
		F3D.Slick.graphics.setColor(Color.red);
		F3D.Slick.graphics.fillRect(0, 0, 5, 5);
		F3D.Slick.graphics.setColor(Color.white);
		F3D.Slick.graphics.drawRect(0, 0, 5, 5);
		F3D.Slick.graphics.popTransform();
		F3D.Slick.graphics.setColor(Color.green);
		F3D.Slick.graphics.fillRect(20, 20, 50, 50);
		F3D.Slick.graphics.popTransform();

		F3D.Slick.graphics.setColor(Color.white);
		F3D.Slick.graphics.draw(rect);
		F3D.Slick.graphics.draw(circle);

		F3D.Slick.graphics.setColor(rect1.intersects(rect) ? Color.red : Color.green);
		F3D.Slick.graphics.draw(rect1);
		F3D.Slick.graphics.setColor(rect2.intersects(rect) ? Color.red : Color.green);
		F3D.Slick.graphics.draw(rect2);
		F3D.Slick.graphics.setColor(roundRect.intersects(rect) ? Color.red : Color.green);
		F3D.Slick.graphics.draw(roundRect);
		F3D.Slick.graphics.setColor(circle1.intersects(rect) ? Color.red : Color.green);
		F3D.Slick.graphics.draw(circle1);
		F3D.Slick.graphics.setColor(circle2.intersects(rect) ? Color.red : Color.green);
		F3D.Slick.graphics.draw(circle2);
		F3D.Slick.graphics.setColor(circle3.intersects(circle) ? Color.red : Color.green);
		F3D.Slick.graphics.fill(circle3);
		F3D.Slick.graphics.setColor(circle4.intersects(circle) ? Color.red : Color.green);
		F3D.Slick.graphics.draw(circle4);

		F3D.Slick.graphics.fill(roundRect2);
		F3D.Slick.graphics.setColor(Color.blue);
		F3D.Slick.graphics.draw(roundRect2);
		F3D.Slick.graphics.setColor(Color.blue);
		F3D.Slick.graphics.draw(new Circle(100, 100, 50));
		F3D.Slick.graphics.drawRect(50, 50, 100, 100);
		
		
		
		
	}
	@Override
	public void onUpdate3D()
	{

		F3D.Draw.Axis(2.0f);

	}

	@Override
	public void onUpdate2D()
	{

		this.render();
	}

	@Override
	public void OnDestroy()
	{
		
	}

	public static void main(String[] args)
	{
		new Demo_SlickGeom().Execute();
		System.exit(0);
	}

}
