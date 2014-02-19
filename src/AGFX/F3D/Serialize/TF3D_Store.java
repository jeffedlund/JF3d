package AGFX.F3D.Serialize;

/**
 * @author Baskervil
 *
 */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import AGFX.F3D.F3D;
import AGFX.F3D.Mesh.TF3D_MeshData;

public class TF3D_Store
{

	public TF3D_Store()
	{

	}

	public static int saveObj(TF3D_MeshData obj, String filename)
	{
		filename = F3D.AbstractFiles.GetFullPath(filename);
		filename = filename.replace("a3da", "a3db");
		filename = filename.replace("A3DA", "A3DB");
		try
		{
			FileOutputStream out = new FileOutputStream(filename);
			ObjectOutputStream s = new ObjectOutputStream(out);
			s.writeObject(obj);
			s.flush();
			s.close();
			return 0;
		} catch (Exception e)
		{
			e.printStackTrace();
			return 1;
		}

	}

	public static TF3D_MeshData readObj(InputStream in)
	{
		TF3D_MeshData ret = null;

		try
		{

			ObjectInputStream s = new ObjectInputStream(in);
		
			ret = (TF3D_MeshData) s.readObject();
			return ret;
		} catch (Exception e)
		{
			e.printStackTrace();
			return ret;
		}
	}

	public static TF3D_MeshData readObj(String filename)
	{
		InputStream is = null;
		try
		{
			if (F3D.Config.io_preload_source_mode.equals("PRELOAD_FROM_JAR"))
			{
				
				is = (FileInputStream)ClassLoader.getSystemResourceAsStream(filename);
			}
			if (F3D.Config.io_preload_source_mode.equals("PRELOAD_FROM_FOLDER"))
			{
				is = new FileInputStream(F3D.AbstractFiles.GetFullPath(filename));
			}
			return readObj(is);
		} catch (FileNotFoundException e)
		{			
			e.printStackTrace();
		}

		return null;
	}

}
