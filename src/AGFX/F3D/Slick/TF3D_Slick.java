/**
 * 
 */
package AGFX.F3D.Slick;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * @author AndyGFX
 *
 */
public class TF3D_Slick
{
	public GameContainer container;
	public Graphics graphics;
	
	public TF3D_Slick()
	{		
	}
	
	public void Initialize()
	{
		this.graphics = new Graphics();
	}
	
	
	public void Destroy()
	{
		this.graphics.destroy();
	}
}
