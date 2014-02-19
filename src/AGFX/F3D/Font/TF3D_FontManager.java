/**
 * 
 */
package AGFX.F3D.Font;

import java.util.ArrayList;

import AGFX.F3D.F3D;
import AGFX.F3D.Parser.TF3D_PARSER;

/**
 * @author AndyGFX
 * 
 */
public class TF3D_FontManager
{
	public ArrayList<TF3D_Font> items;

	// -----------------------------------------------------------------------
	// TA3D_FontManager:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Constructor <BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	public TF3D_FontManager()
	{
		F3D.Log.info("TA3D_FontManager", "TA3D_FontManager: constructor");
		this.items = new ArrayList<TF3D_Font>();
		F3D.Log.info("TA3D_FontManager", "TA3D_FontManager: ... done");
	}

	// -----------------------------------------------------------------------
	// TA3D_FontManager:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Add font to list <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param font
	 *            TA3D_Font
	 * @return assigned ID from list
	 */
	// -----------------------------------------------------------------------
	public int Add(TF3D_Font font)
	{
		if (this.Exist(font.name))
		{
			F3D.Log.info("TA3D_FontManager", "TA3D_FontManager: Add() '" + font.name + "' wasn't added - exist !");
			return this.FindByName(font.name);

		} else
		{
			int res = this.items.size();
			this.items.add(font);
			F3D.Log.info("TA3D_FontManager", "TA3D_FontManager: Add() '" + font.name + "'");
			return res;
		}
	}

	// -----------------------------------------------------------------------
	// TA3D_FontManager:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Load font/fonts from file <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param assetname
	 *            font file *.font
	 */
	// -----------------------------------------------------------------------
	public void Load(String assetname)
	{
		TF3D_PARSER PARSER = new TF3D_PARSER();
		TF3D_Font _font;

		PARSER.ParseFile(assetname);

		for (int i = 0; i < PARSER.GetBlocksCount(); i++)
		{
			PARSER.SetBlock(i);

			String _name = PARSER.GetAs_STRING("name");
			String _material = PARSER.GetAs_STRING("material");
			String _kerning = PARSER.GetAs_STRING("kerning");

			_font = new TF3D_Font(_name, _material, _kerning);
			this.Add(_font);

		}
	}

	// -----------------------------------------------------------------------
	// TA3D_FontManager:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Find font by name <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param name
	 *            font name
	 * @return - font ID
	 */
	// -----------------------------------------------------------------------
	public int FindByName(String name)
	{
		int ret = -1;

		for (int i = 0; i < this.items.size(); i++)
		{
			if (this.items.get(i).name.equalsIgnoreCase(name))
			{
				ret = i;
			}
		}
		return ret;
	}

	// -----------------------------------------------------------------------
	// TA3D_FontManager:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * check if font exist in list <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param name
	 * @return return true when font exist
	 */
	// -----------------------------------------------------------------------
	public boolean Exist(String name)
	{
		boolean res = false;

		int f = this.FindByName(name);

		if (f == -1)
		{
			res = false;
		} else
		{
			res = true;
		}

		return res;
	}

	public void DrawText(String fontname, float X, float Y, String Txt, float Lev)
	{
		int id = this.FindByName(fontname);
		if (id < 0)
		{
			F3D.Log.warning("TF3D_FontManager", "Font name: '" + fontname + "' doesn't exist !");
		} else
		{
			this.items.get(id).DrawText(X, Y, Txt, Lev);
		}
	}

	public void DrawText(int fontid, float X, float Y, String Txt, float Lev)
	{
		
		if ((fontid < 0) | fontid>this.items.size())
		{
			F3D.Log.error("TF3D_FontManager", "Font ID: '" + fontid + "' doesn't exist !");
		} else
		{
			this.items.get(fontid).DrawText(X, Y, Txt, Lev);
		}
	}

	public void Destroy()
	{
		for(int m=0;m<this.items.size();m++)
		{
			this.items.remove(m);
		}
	}
}
