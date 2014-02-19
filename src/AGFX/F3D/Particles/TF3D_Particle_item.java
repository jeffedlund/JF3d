/**
 * 
 */
package AGFX.F3D.Particles;

import javax.vecmath.Vector3f;

import AGFX.F3D.Material.TF3D_Material;

/**
 * @author AndyGFX
 * 
 */
public class TF3D_Particle_item
{
	TF3D_Particle_Sprite	sprite		= null;
	TF3D_Material			material	= null;

	boolean					active;						// Active (Yes/No)
	float					life;							// Particle Life
	float					inc_life;
	float					r;								// Red Value
	float					g;								// Green Value
	float					b;								// Blue Value
	float					a;								// Blue Value
	Vector3f				position	= new Vector3f();
	Vector3f				direction	= new Vector3f();
	Vector3f				gravity		= new Vector3f();

}
