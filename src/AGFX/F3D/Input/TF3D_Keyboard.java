/**
 * 
 */
package AGFX.F3D.Input;

import java.util.HashMap;
import org.fenggui.binding.render.lwjgl.EventHelper;
import org.lwjgl.input.Keyboard;
import AGFX.F3D.F3D;

/**
 * @author AndyGFX
 * 
 */
public class TF3D_Keyboard
{
	private HashMap<String, TF3D_KeyEvent> Keys = new HashMap<String, TF3D_KeyEvent>();
	
	public TF3D_Keyboard()
	{

	}

	public void Update()
	{

		// Keyboard.poll();

		while (Keyboard.next())
		{
			int keyCode = Keyboard.getEventKey();
			char keyChar = Keyboard.getEventCharacter();
			boolean pressed = Keyboard.getEventKeyState();

			if (pressed)
			{
				boolean down = true;
				boolean up = false;
				TF3D_KeyEvent key = new TF3D_KeyEvent(keyCode, keyChar, pressed, down, up, 0);
				this.Keys.put(Keyboard.getKeyName(Keyboard.getEventKey()), key);
			} else
			{
				boolean down = false;
				boolean up = true;
				TF3D_KeyEvent key = new TF3D_KeyEvent(keyCode, keyChar, pressed, down, up, 0);
				this.Keys.put(Keyboard.getKeyName(Keyboard.getEventKey()), key);
			}
			
			if (Keyboard.getEventKeyState())
			{
				F3D.Gui.display.fireKeyPressedEvent(EventHelper.mapKeyChar(), EventHelper.mapEventKey());

				F3D.Gui.display.fireKeyTypedEvent(EventHelper.mapKeyChar());
			} else
			{
				F3D.Gui.display.fireKeyReleasedEvent(EventHelper.mapKeyChar(), EventHelper.mapEventKey());
			}
		}
	}

	public void Test()
	{
		// check keys, buffered
		Keyboard.poll();

		int count = Keyboard.getNumKeyboardEvents();
		while (Keyboard.next())
		{
			int character_code = ((int) Keyboard.getEventCharacter()) & 0xffff;
			System.out.println("Checking key:" + Keyboard.getKeyName(Keyboard.getEventKey()));
			System.out.println("Pressed:" + Keyboard.getEventKeyState());
			System.out.println("Key character code: 0x" + Integer.toHexString(character_code));
			System.out.println("Key character: " + Keyboard.getEventCharacter());
			System.out.println("Repeat event: " + Keyboard.isRepeatEvent());

			if (Keyboard.getEventKey() == Keyboard.KEY_R && Keyboard.getEventKeyState())
			{
				Keyboard.enableRepeatEvents(!Keyboard.areRepeatEventsEnabled());
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE)
			{
				return;
			}
		}
	}

	public Boolean IsKeyDown(int key_code)
	{
		TF3D_KeyEvent key = (TF3D_KeyEvent) this.Keys.get(Keyboard.getKeyName(key_code));

		if (key != null)
		{
			if (key.pressed)
			{
				return true;
			}
		}

		return false;
	}

	public Boolean IsKeyUp(int key_code)
	{
		TF3D_KeyEvent key = (TF3D_KeyEvent) this.Keys.get(Keyboard.getKeyName(key_code));

		if (key != null)
		{
			if (key.up)
			{
				this.Keys.get(Keyboard.getKeyName(key_code)).up = false;
				return true;
			}
		}

		return false;
	}

}
