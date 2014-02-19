/**
 * 
 */
package AGFX.F3D.Gui;

import java.io.IOException;
import org.fenggui.Container;
import org.fenggui.FengGUI;
import org.fenggui.binding.render.lwjgl.LWJGLBinding;
import org.fenggui.theme.ITheme;
import org.fenggui.theme.XMLTheme;
import org.fenggui.theme.xml.IXMLStreamableException;

import AGFX.F3D.F3D;

/**
 * @author AndyGFX
 * 
 */
public class TF3D_FengGUI
{
	public org.fenggui.Display display        = null;
	private ITheme             theme;

	public TF3D_FengGUI()
	{

	}
	
	public void Initialize()
	{
		LWJGLBinding binding = new LWJGLBinding();
		this.display = new org.fenggui.Display(binding);
		
	}
	
	
	public void SetTheme(String themename)
	{
		try
		{
			theme = new XMLTheme(themename);
		} catch (IOException e)
		{
			e.printStackTrace();
		} catch (IXMLStreamableException e)
		{
			e.printStackTrace();
		}
		FengGUI.setTheme(this.theme);
	}
	public void Render()
	{
		F3D.Textures.DeactivateLayers();
		F3D.Textures.ActivateLayer(0);
		this.display.display();
	}
}
