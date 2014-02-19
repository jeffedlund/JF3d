package AGFX.F3D.Hud;

/**
 * @author AndyGFX
 *
 */
public class TF3D_HUD_GadgetProperties 
{
	
	/** enable texture blend [true/false]*/
	public boolean Blend;
	
	/**  enable texture [true/false]*/
	public boolean Texture;
	
	/** not implemented yet */
	public boolean Scissor;
	
	/** set texture size from texture (user defined size is ignored)*/
	public boolean Autosize;
	
	/** clamp texture to edge [true/false] */
	public boolean Clamp;
	
	public TF3D_HUD_GadgetProperties()
	{
		this.Blend = false;
		this.Texture = false;
		this.Scissor = false;
		this.Autosize = true;
		this.Clamp = false;
	}

}
