package AGFX.F3D.Input;


public class TF3D_MouseEvent
{

	public int mx;
	public int my;
	
	public Boolean button_press = false;
	public Boolean button_up = false;
	
	public TF3D_MouseEvent(int id, boolean pressed, boolean up, int _mx, int _my)
	{
		this.mx = _mx;
		this.my = _my;
		this.button_press=pressed;
		this.button_up=up;
	}

	@Override
	public String toString()
	{
		return "TF3D_MouseEvent [mx=" + mx + ", my=" + my + ", button_press=" + button_press + ", button_up=" + button_up + "]";
	}
	
	
}
