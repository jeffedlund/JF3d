/**
 * 
 */
package AGFX.F3D.Font;

import java.io.IOException;
import java.io.InputStream;


import AGFX.F3D.F3D;

/**
 * @author AndyGFX
 * 
 */
public class TF3D_Font
{
	public TF3D_HUD_TextInfo[]	F;

	public int[]				CharLookup	= new int[F3D.MAX_CHARS];
	public int					NUMFONTS;
	public int					material_id	= -1;
	public int					SpaceWidth;
	public int					MaxHeight;
	public String[]				ini_lines;
	public String				name		= "none";
	

	public class TF3D_HUD_TextInfo
	{

		public int		A;
		public int		C;
		public int		Wid;
		public int		Hgt;
		public String	_char;
		public float	x1;
		public float	y1;
		public float	x2;
		public float	y2;

		// -----------------------------------------------------------------------
		// TA3D_HUD_TextInfo:
		// -----------------------------------------------------------------------
		/**
		 * <BR>
		 * -------------------------------------------------------------------<BR>
		 * record for font chars kerning <BR>
		 * -------------------------------------------------------------------<BR>
		 */
		// -----------------------------------------------------------------------
		public TF3D_HUD_TextInfo()
		{

			
		}

	}

	// -----------------------------------------------------------------------
	// TA3D_HUD_Font:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Create font <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param _name
	 *            font name
	 * @param matname
	 *            font material
	 * @param iname
	 *            font ini file
	 */
	// -----------------------------------------------------------------------
	public TF3D_Font(String _name, String matname, String iname)
	{
		F3D.Log.info("TF3D_Font", "Create - constructor");
		
		this.name = _name;
		this.NUMFONTS = 0;
		this.F = new TF3D_HUD_TextInfo[F3D.MAX_CHARS];
		this.LoadFontIni(iname);

		this.material_id = F3D.Surfaces.FindByName(matname);

		if (this.material_id==-1)
		{
			F3D.Log.error("TA3D_Font","TA3D_Font Create - assigned material name '"+matname+"' not found ! (Surface material you have to load before.)");
		}
	
	}

	private String GetIniLineByKey(String keyword)
	{
		int id;
		String Line;
		for (id = 0; id < this.ini_lines.length; id++)
		{
			Line = this.ini_lines[id];

			if (Line.length() >= keyword.length())
			{
				String sub = Line.substring(0, keyword.length());
				if (sub.equals(keyword))
				{
					String[] spl = Line.split("=");
					return spl[1];
				}
			}
		}

		return null;
	}

	private void LoadFontIni(String fontinifile)
	{
		try
		{
			InputStream is = null;
			
			fontinifile = F3D.AbstractFiles.GetFullPath(fontinifile);
			
			is = F3D.Resource.GetInputStream(fontinifile);
			
			// asset can't be more than 2 gigs.
			int size = is.available();

			// Read the entire asset into a local byte buffer.
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();

			// Convert the buffer into a string.
			this.ini_lines = new String(buffer).split("\r\n");

			this.NUMFONTS = Integer.valueOf(this.GetIniLineByKey("NumFont").trim()).intValue();
			this.SpaceWidth = Integer.valueOf(this.GetIniLineByKey("SpaceWidth").trim()).intValue();

			for (int chidx = 0; chidx < this.NUMFONTS; chidx++)
			{
				this.F[chidx] = new TF3D_HUD_TextInfo();

				this.F[chidx]._char = this.GetIniLineByKey(chidx + "Char=");

				this.CharLookup[Integer.valueOf(this.F[chidx]._char.trim()).intValue()] = chidx;
				this.F[chidx].A = Integer.valueOf(this.GetIniLineByKey(chidx + "A=").trim()).intValue();
				this.F[chidx].C = Integer.valueOf(this.GetIniLineByKey(chidx + "C=").trim()).intValue();

				this.F[chidx].Wid = Integer.valueOf(this.GetIniLineByKey(chidx + "Wid=").trim()).intValue();
				this.F[chidx].Hgt = Integer.valueOf(this.GetIniLineByKey(chidx + "Hgt=").trim()).intValue();

				this.F[chidx].x1 = Float.valueOf(this.GetIniLineByKey(chidx + "X1=").trim()).floatValue();
				this.F[chidx].y1 = Float.valueOf(this.GetIniLineByKey(chidx + "Y1=").trim()).floatValue();
				this.F[chidx].x2 = Float.valueOf(this.GetIniLineByKey(chidx + "X2=").trim()).floatValue();
				this.F[chidx].y2 = Float.valueOf(this.GetIniLineByKey(chidx + "Y2=").trim()).floatValue();

			}

		} catch (IOException e)
		{
			F3D.Log.error("TA3D_Font", "TA3D_HUD_Font.LoadFontIni('" + fontinifile + "') " + e.toString());
		}

	}

	

	// -----------------------------------------------------------------------
	// TA3D_HUD_Font:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Draw text on screen <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param X
	 *            position X
	 * @param Y
	 *            position Y
	 * @param Txt
	 *            text
	 * @param Lev
	 *            depth level
	 */
	// -----------------------------------------------------------------------
	public void DrawText(float X, float Y, String Txt, float Lev)
	{
		float CurX;
		byte Chaar;
		int I;
		int Ind;

		CurX = X;

		F3D.Surfaces.ApplyMaterial(this.material_id);
		
		for (I = 0; I < Txt.length(); I++)
		{

			Chaar = (byte) Txt.charAt(I);

			if (Chaar == 32)
			{
				Ind = -1;
				CurX = CurX + SpaceWidth;
			} else
			{
				Ind = CharLookup[Chaar];
			}

			if (Ind > -1)
			{
				CurX = CurX + F[Ind].A;
				F3D.Draw.QuadBySizeAndUV(CurX, Y, this.F[Ind].Wid, this.F[Ind].Hgt, Lev, this.F[Ind].x1, this.F[Ind].x2, this.F[Ind].y1, this.F[Ind].y2);
				CurX = CurX + F[Ind].C;

			}
		}

	}
}
