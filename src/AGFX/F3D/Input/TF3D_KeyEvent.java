/**
 * 
 */
package AGFX.F3D.Input;

import org.lwjgl.input.Keyboard;

import AGFX.F3D.F3D;

/**
 * @author AndyGFX
 * 
 */
public class TF3D_KeyEvent
{
	public String              name;

	public int                 keyCode;
	public char                keyChar;
	public Boolean             pressed;
	public Boolean             down;
	public Boolean             up;
	public long                time;
	public long                delay;

	public TF3D_KeyEvent(int keyCode, char keyChar, Boolean pressed, Boolean down,Boolean up, long delay)
	{
		this.keyCode = keyCode;
		this.keyChar = keyChar;
		this.pressed = pressed;
		this.down = down;
		this.up = up;
		this.time = F3D.Timer.GetTickCount();
		this.delay = delay;
	}
	
	public void PrintInfo()
	{
		F3D.Log.info("TF3D_KeyEvent", "key name   : "+Keyboard.getKeyName(this.keyCode));
		F3D.Log.info("TF3D_KeyEvent", "key code   : 0x"+Integer.toHexString(this.keyCode));		
		F3D.Log.info("TF3D_KeyEvent", "key pressed: "+this.pressed.toString());
		F3D.Log.info("TF3D_KeyEvent", "key down   : "+this.down.toString());
		F3D.Log.info("TF3D_KeyEvent", "key up     : "+this.up.toString());
		F3D.Log.info("", "--------------------------------------");
	}
}
