package AGFX.F3D.Input;

public class TF3D_InputManager
{
	public TF3D_Keyboard	Key;
	public TF3D_Mouse		Mouse;

	public TF3D_InputManager()
	{
		this.Key = new TF3D_Keyboard();
		this.Mouse = new TF3D_Mouse();
	}
}
