/**
 * 
 */
package AGFX.F3D.Preloader;

import AGFX.F3D.F3D;

/**
 * @author AndyGFX
 * 
 */
public class TF3D_Preloader
{

	public Boolean	b_preload_textures	= true;
	public Boolean	b_preload_events	= true;
	public Boolean	b_preload_materials	= true;
	public Boolean	b_preload_shaders	= true;
	public Boolean	b_preload_fonts		= true;
	

	// -----------------------------------------------------------------------
	// TF3D_Preloader:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Cnstructor <BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	public TF3D_Preloader()
	{
		F3D.Log.info("TF3D_Preloader", "Create - constructor");

	}

	// -----------------------------------------------------------------------
	// TF3D_Preloader:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Preloading enabled media files <BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	public void PreloadData()
	{
		
		this.b_preload_textures = F3D.Config.io_preload_textures;
		this.b_preload_events = F3D.Config.io_preload_events;
		this.b_preload_fonts = F3D.Config.io_preload_fonts;
		this.b_preload_materials = F3D.Config.io_preload_materials;
		this.b_preload_shaders = F3D.Config.io_preload_shaders;
		

		// .frag /. vert <-----------------------
		if (this.b_preload_shaders)
		{
			F3D.Shaders.InitPresets();
		}

		// .event <-----------------------------
		if (this.b_preload_events)
		{
			this.Preload_Events();
		}

		// .texture <----------------------------
		if (this.b_preload_textures)
		{
			this.Preload_Textures();
		}

		// .mat <--------------------------------
		if (this.b_preload_materials)
		{
			this.Preload_Materials();
		}

		// .font <--------------------------------
		if (this.b_preload_fonts)
		{
			this.Preload_Fonts();
		}
		
		this.List();
	}

	// -----------------------------------------------------------------------
	// TF3D_Preloader:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Preload TEXTURES from .texture files in media folder <BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	private void Preload_Textures()
	{

		for (int i = 0; i < F3D.AbstractFiles.Dir.size(); i++)
		{
			String pFile = F3D.AbstractFiles.Dir.get(i);

			if (pFile.contains(".texture"))
			{
				F3D.Textures.Load(pFile);
			}
		}

	}

	// -----------------------------------------------------------------------
	// TF3D_Preloader:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Preload EVENTS from .event files in media folder <BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	private void Preload_Events()
	{

		for (int i = 0; i < F3D.AbstractFiles.Dir.size(); i++)
		{
			String pFile = F3D.AbstractFiles.Dir.get(i);

			if (pFile.contains(".event"))
			{
				F3D.MaterialEvents.Load(pFile);
			}
		}

	}

	// -----------------------------------------------------------------------
	// TF3D_Preloader:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Preload MATERIALS from .mat files in media folder <BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	private void Preload_Materials()
	{

		for (int i = 0; i < F3D.AbstractFiles.Dir.size(); i++)
		{
			String pFile = F3D.AbstractFiles.Dir.get(i);

			if (pFile.contains(".mat"))
			{
				F3D.Surfaces.Load(pFile);
			}
		}

	}

	
	private void Preload_Fonts()
	{
		F3D.Fonts.Load("abstract::system.font");
	}
	
	// -----------------------------------------------------------------------
	// TF3D_Preloader:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Dump list of preloaded files <BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	public void List()
	{
		F3D.Log.info("Preloader", "-------------- LIST ---------------");

		for (int i = 0; i < F3D.Shaders.items.size(); i++)
		{
			String name = F3D.Shaders.items.get(i).name;

			F3D.Log.info("Shader", name);

		}

		for (int i = 0; i < F3D.MaterialEvents.items.size(); i++)
		{
			String name = F3D.MaterialEvents.items.get(i).name;

			F3D.Log.info("Events", name);

		}

		for (int i = 0; i < F3D.Textures.items.size(); i++)
		{
			String name = F3D.Textures.items.get(i).name;

			F3D.Log.info("Texture", name);

		}

		for (int i = 0; i < F3D.Surfaces.materials.size(); i++)
		{
			String name = F3D.Surfaces.materials.get(i).name;

			F3D.Log.info("Material", name);

		}

		F3D.Log.info("Preloader", "--------------  END ---------------");
	}
}
