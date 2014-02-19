package AGFX.F3D.Utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;

import AGFX.F3D.F3D;

public class TF3D_AbstractFiles
{
	public ArrayList<String> Dir;
	static final int         MAX_DEPTH = 20;

	// -----------------------------------------------------------------------
	// TF3D_AbstractFiles:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Constructor <BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	public TF3D_AbstractFiles()
	{

		this.Dir = new ArrayList<String>();

		if (F3D.Config == null)
		{
			this.GererateFilesList("config");

		} else
		{
			this.GererateFilesList(F3D.Config.io_preload_folder);
		}

	}

	// -----------------------------------------------------------------------
	// TF3D_AbstractFiles:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Generate files list <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param media_folder
	 *            - root folder name
	 */
	// -----------------------------------------------------------------------
	private void GererateFilesList(String media_folder)
	{
		File root;

		root = new File(media_folder);
		if (root != null && root.isDirectory())
		{
			listRecursively(root, 0);
		} else
		{
			System.out.println("Not a directory: " + root);
		}
	}

	// -----------------------------------------------------------------------
	// TF3D_AbstractFiles:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * List of all files from defined folder <BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	public void Dump()
	{
		for (int i = 0; i < this.Dir.size(); i++)
		{
			String relative_path = this.Dir.get(i);
			System.out.println(relative_path);

		}
	}

	// ==========================================================
	// listRecursively
	public void listRecursively(File fdir, int depth)
	{

		String replaced = TF3D_AbstractFiles.backlashReplace(fdir.getAbsolutePath());
		// String replaced = fdir.getAbsolutePath();

		if (replaced.contains(".svn") | replaced.contains(".bak"))
		{
			// IGNORE
		} else
		{
			this.Dir.add(replaced);

		}

		if (fdir.isDirectory() && depth < MAX_DEPTH)
		{
			for (File f : fdir.listFiles())
			{
				listRecursively(f, depth + 1);
			}
		}
	}

	public static String backlashReplace(String myStr)
	{
		final StringBuilder result = new StringBuilder();
		final StringCharacterIterator iterator = new StringCharacterIterator(myStr);
		char character = iterator.current();
		while (character != CharacterIterator.DONE)
		{

			if (character == '\\')
			{
				result.append("/");
			} else
			{
				result.append(character);
			}

			character = iterator.next();
		}
		return result.toString();
	}

	public boolean ExistFile(String file)
	{
		boolean res = false;

		file = file.replace("abstract::", "");
		for (int i = 0; i < this.Dir.size(); i++)
		{
			if (this.Dir.get(i).contains(file))
			{
				res = true;
			}
		}

		if (res==false)
		{
			F3D.Log.warning("AbstarctFile", "File :"+file+"doesn't exist in medial list file !!!");
		}
		return res;
	}

	public String GetFullPath(String file)
	{
		String res = null;

		if (file.contains("abstract::"))
		{
			file = file.replace("abstract::", "");

			for (int i = 0; i < this.Dir.size(); i++)
			{
				if (this.Dir.get(i).contains(file))
				{
					res = this.Dir.get(i);
				}
			}
		} else
		{
			res = file;
		}

		return res;
	}

	public void Save(String name)
	{
		FileWriter f;
		PrintWriter dos = null;

		try
		{
			f = new FileWriter(name);
			dos = new PrintWriter(f);
			for (int i = 0; i < this.Dir.size(); i++)
			{
				String relative_path = this.Dir.get(i);
				int id = relative_path.indexOf(F3D.Config.io_preload_folder);
				relative_path = relative_path.substring(id, relative_path.length());
				dos.println(relative_path);
			}

			dos.close();
		} catch (IOException e1)
		{

			e1.printStackTrace();
		}

	}

	// -----------------------------------------------------------------------
	// TF3D_AbstractFiles:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Load File list from file <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param is
	 */
	// -----------------------------------------------------------------------
	public void Load(String filename)
	{

		InputStream is = null;

		is = F3D.Resource.GetInputStream(filename);

		// asset can't be more than 2 gigs.
		int size;
		try
		{
			size = is.available();
			// Read the entire asset into a local byte buffer.
			byte[] buffer = new byte[size];
			is.read(buffer);

			// Convert the buffer into a string.
			String[] text = new String(buffer).split("\n");

			this.Dir = new ArrayList<String>();

			for (int i = 0; i < text.length; i++)
			{
				text[i] = text[i].replaceAll("\n", "");
				text[i] = text[i].replaceAll("\r", "");
				this.Dir.add(text[i]);
				F3D.Log.info("Manulay load Abstract list", text[i]);
			}

			is.close();
		} catch (IOException e)
		{
			e.printStackTrace();
			F3D.Log.error("TF3D_AbstractFiles", "Missing file ");
		}

	}
}
