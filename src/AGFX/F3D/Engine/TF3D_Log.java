/**
 * 
 */
package AGFX.F3D.Engine;


/**
 * @author AndyGFX
 *
 */
public class TF3D_Log
{
	public TF3D_Log()
	{
		
	}
	
	public void info(String class_name,String text)
	{
		System.out.println(String.format("INFO  : %25s :   %s", class_name,text)); 
	}
	
	public void warning(String class_name,String text)
	{
		System.out.println(String.format("WARNING  : %25s :   %s", class_name,text)); 
	}
	
	public void error(String class_name,String text)
	{
		System.out.println(String.format("ERROR : %25s :   %s", class_name,text));
		System.exit(1);
	}
	
}
