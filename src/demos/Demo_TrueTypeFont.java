/**
 * 
 */
package demos;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.lwjgl.opengl.GL11;
import AGFX.F3D.F3D;
import AGFX.F3D.AppWrapper.TF3D_AppWrapper;
import AGFX.F3D.Config.TF3D_Config;
import AGFX.F3D.Font.TF3D_Font_TTF;

/**
 * @author AndyGFX
 * 
 */
public class Demo_TrueTypeFont extends TF3D_AppWrapper
{

	TF3D_Font_TTF ttf;
	Font          font;

	TF3D_Font_TTF ttf_bumbazoo;
	Font          font_bumbazoo;

	public Demo_TrueTypeFont()
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

			
			super.onConfigure();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void onInitialize()
	{
		// load system TTF font
		Font font = new Font("Verdana", Font.BOLD, 32);
		this.ttf = new TF3D_Font_TTF(font, true);

		// load user defined font

		

		try
		{
			File f = new File("media/ttf/BUMBAZO.TTF");
			FileInputStream in = new FileInputStream(f);
			font_bumbazoo = Font.createFont(Font.TRUETYPE_FONT, in);
			font_bumbazoo = font_bumbazoo.deriveFont(24f);
			
		} catch (FontFormatException e)
		{
			
			e.printStackTrace();
		} catch (IOException e)
		{
			
			e.printStackTrace();
		}

		this.ttf_bumbazoo = new TF3D_Font_TTF(font_bumbazoo, true);
		
	}

	@Override
	public void onUpdate3D()
	{
	}

	@Override
	public void onUpdate2D()
	{
	
		
		F3D.Textures.DeactivateLayers();
		F3D.Textures.ActivateLayer(0);
		GL11.glColor4f(1,1,1,1);		
		this.ttf.drawString(50, 50, "Demo of Tryetype font", 1f, 1f);
		
		// create shadow
		GL11.glColor4f(0,0,0,0.5f);
		this.ttf_bumbazoo.drawString(50, 152, "Demo of User Tryetype font", 1f, 1f);
		// draw font
		GL11.glColor4f(1,1,0,1);
		
		
		int size = this.ttf_bumbazoo.getWidth("Demo of User Tryetype font");
		this.ttf_bumbazoo.drawString(50, 150, "Demo of User Tryetype font", 1f, 1f);
		
		F3D.Log.info("SIZE", String.valueOf(size));
	}

	@Override
	public void OnDestroy()
	{

	}

	public static void main(String[] args)
	{

		new Demo_TrueTypeFont().Execute();
		System.exit(0);

	}

}
