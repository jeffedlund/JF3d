package AGFX.F3D.Hud;

import javax.vecmath.Vector2f;




public class TF3D_HUD_GadgetTransform 
{
	/**  hud Gadget texture scroll speed  */
	public Vector2f scroll;
	
	/**  hud Gadget texture rotation speed  */
	public float rotate;
	
	public TF3D_HUD_GadgetTransform()
	{
		this.scroll = new Vector2f();
		this.rotate = 0.0f;
		
	}

}
