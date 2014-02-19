package AGFX.F3D.Input;

import java.util.HashMap;

import org.fenggui.binding.render.lwjgl.EventHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import AGFX.F3D.F3D;

public class TF3D_Mouse
{
	private HashMap<String, TF3D_MouseEvent>	mouse	= new HashMap<String, TF3D_MouseEvent>();
	private int                lastButtonDown = -1;
	
	public TF3D_Mouse()
	{

	}

	public void Update()
	{
		int x = Mouse.getX();
		int y = Mouse.getY();

		boolean hitGUI = false;

		// @todo the click count is not considered in LWJGL! #

		if (lastButtonDown != -1 && Mouse.isButtonDown(lastButtonDown))
		{
			hitGUI |= F3D.Gui.display.fireMouseDraggedEvent(x, y, EventHelper.getMouseButton(lastButtonDown), 1);
		} else
		{
			if (Mouse.getDX() != 0 || Mouse.getDY() != 0)
				hitGUI |= F3D.Gui.display.fireMouseMovedEvent(x, y, EventHelper.getMouseButton(lastButtonDown), 1);

			if (lastButtonDown != -1)
			{
				F3D.Gui.display.fireMouseClickEvent(x, y, EventHelper.getMouseButton(lastButtonDown), 1);
				hitGUI |= F3D.Gui.display.fireMouseReleasedEvent(x, y, EventHelper.getMouseButton(lastButtonDown), 1);
				lastButtonDown = -1;
			}
			while (Mouse.next())
			{
				{

					Boolean pressed = Mouse.getEventButtonState();
					int id = Mouse.getEventButton();

					if ((!pressed) & (id >= 0))
					{
						TF3D_MouseEvent m = new TF3D_MouseEvent(id, false, true, Mouse.getEventX(), Mouse.getEventY());
						this.mouse.put(Mouse.getButtonName(id), m);
					}
				}
				
				if (Mouse.getEventButton() != -1 && Mouse.getEventButtonState())
				{
					lastButtonDown = Mouse.getEventButton();
					hitGUI |= F3D.Gui.display.fireMousePressedEvent(x, y, EventHelper.getMouseButton(lastButtonDown), 1);
				}
				int wheel = Mouse.getEventDWheel();
				if (wheel != 0)
				{
					hitGUI |= F3D.Gui.display.fireMouseWheel(x, y, wheel > 0, 1, 1);
				}
			}
		}
		/*
		while (Mouse.next())
		{

			Boolean pressed = Mouse.getEventButtonState();
			int id = Mouse.getEventButton();

			if ((!pressed) & (id >= 0))
			{
				TF3D_MouseEvent m = new TF3D_MouseEvent(id, false, true, Mouse.getEventX(), Mouse.getEventY());
				this.mouse.put(Mouse.getButtonName(id), m);
			}
		}
		*/
	}

	public Boolean IsButtonDown(int id)
	{
		if (Mouse.isButtonDown(id))
		{
			TF3D_MouseEvent m = new TF3D_MouseEvent(id, true, false, Mouse.getEventX(), Mouse.getEventY());
			this.mouse.put(Mouse.getButtonName(id), m);
			return true;
		}

		return false;
	}

	public Boolean IsButtonUp(int id)
	{
		TF3D_MouseEvent m = (TF3D_MouseEvent) this.mouse.get(Mouse.getButtonName(Mouse.getEventButton()));

		if (m != null)
		{
			if ((!m.button_press) & (m.button_up) & (Mouse.getEventButton() >= 0))
			{
				this.mouse.get(Mouse.getButtonName(Mouse.getEventButton())).button_up = false;
				return true;
			}
		}

		return false;
	}

	public Boolean isInsideWindow()
	{
		return Mouse.isInsideWindow();

	}

	public Boolean isGrabbed()
	{
		return Mouse.isGrabbed();
	}

	public void HideCursor()
	{
		Mouse.setGrabbed(true);
	}

	public void ShowCursor()
	{
		Mouse.setGrabbed(false);
	}
	
	public TF3D_MouseEvent GetButtonEvent(int id)
	{
		
		return this.mouse.get(Mouse.getButtonName(id));
	}
}
