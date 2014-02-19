/**
 * 
 */
package AGFX.F3D.ResourceLoader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import AGFX.F3D.F3D;

/**
 * @author AndyGFX
 * 
 */
public class TF3D_ResourceLoader
{

	public TF3D_ResourceLoader()
	{

	}

	public InputStream GetInputStream(String filename)
	{
		InputStream is = null;

		if (F3D.Config.io_preload_source_mode.equals("PRELOAD_FROM_JAR"))
		{
			is = getClass().getClassLoader().getResourceAsStream(filename);
		}

		if (F3D.Config.io_preload_source_mode.equals("PRELOAD_FROM_FOLDER"))
		{
			try
			{
				is = new FileInputStream(filename);
			} catch (FileNotFoundException e)
			{

				e.printStackTrace();
			}
		}

		return is;
	}

	public InputStream FromJAR(String filename)
	{
		InputStream is = null;

		is = getClass().getClassLoader().getResourceAsStream(filename);
		return is;
	}
	
	public InputStream FromFOLDER(String filename)
	{
		InputStream is = null;

		try
		{
			is = new FileInputStream(filename);
		} catch (FileNotFoundException e)
		{

			e.printStackTrace();
		}
		return is;
	}
}
