/**
 * 
 */
package AGFX.F3D.Hud;

import org.lwjgl.input.Mouse;

import AGFX.F3D.F3D;
import AGFX.F3D.Callback.TF3D_HudCallback;

/**
 * @author AndyGFX
 * 
 */

public class TF3D_HUD_ImageButton extends TF3D_HUD_Image
{
	private int				state;
	public TF3D_HudCallback	OnPress;

	public TF3D_HUD_ImageButton()
	{
		this.state = F3D.HUD_STATE_NORMAL;
	}

	private Boolean isMouseInside(float px, float py)
	{
		if (Mouse.isInsideWindow())
		{
			float mx = Mouse.getX();
			float my = F3D.Config.r_display_height - Mouse.getY();

			if ((mx > px) & (mx < (px + this.size.x)) & (my > py) & (my < (py + this.size.y)))
			{
				return true;
			}
		}
		return false;
	}

	public void DrawAt(float _x, float _y)
	{
		if (this.isMouseInside(_x, _y))
		{
			this.state = F3D.HUD_STATE_OVER;

			if (Mouse.isButtonDown(0))
			{
				this.state = F3D.HUD_STATE_PRESSED;

				if (this.OnPress != null)
					this.OnPress.Call();
			}
		} else
		{
			this.state = F3D.HUD_STATE_NORMAL;
		}

		if (this.state == F3D.HUD_STATE_NORMAL)
		{
			this.DrawAt(_x, _y, texture_id0);
		}
		if (this.state == F3D.HUD_STATE_OVER)
		{
			this.DrawAt(_x, _y, texture_id1);
		}
		if (this.state == F3D.HUD_STATE_PRESSED)
		{
			this.DrawAt(_x, _y, texture_id2);
		}
	}

}
