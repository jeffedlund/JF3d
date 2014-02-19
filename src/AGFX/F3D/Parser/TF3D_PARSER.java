/**
 * 
 */
package AGFX.F3D.Parser;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import org.lwjgl.BufferUtils;

import AGFX.F3D.F3D;

/**
 * @author AndyGFX
 * 
 */
public class TF3D_PARSER
{

	public class TA3D_ParserBlock
	{
		public ArrayList<String>	lines;

		public TA3D_ParserBlock()
		{
			this.lines = new ArrayList<String>();
		}

		public void Add(String line)
		{
			this.lines.add(line);
		}
	}

	public ArrayList<TA3D_ParserBlock>	Blocks;
	public String						filename		= "";
	public String						NL				= "\n";
	public String						VAR_SEPARATOR	= "=";
	public String						VALUE_SEPARATOR	= ",";
	public String						BEGIN_SEPARATOR	= "{";
	public String						END_SEPARATOR	= "}";

	public TA3D_ParserBlock				CurrentBlock;

	// -----------------------------------------------------------------------
	// TA3D_PARSER:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * constructor <BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	public TF3D_PARSER()
	{
		System.out.print("TF3D_PARSER   Create File Parser class");
		this.Blocks = new ArrayList<TA3D_ParserBlock>();

	}

	// -----------------------------------------------------------------------
	// TA3D_PARSER:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Parse file and generate block segments <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param filename
	 *            input filename
	 */
	// -----------------------------------------------------------------------
	public void ParseFile(String filename)
	{

		TA3D_ParserBlock _block = new TA3D_ParserBlock();
		InputStream is = null;

		try
		{
			Boolean Exist = F3D.AbstractFiles.ExistFile(filename);

			if (!Exist)
			{
				F3D.Log.error("F3D_PARSER", "Can't load file:" + filename);
			}

			F3D.Log.info("F3D_PARSER", "Parsing file:" + filename);

			filename = F3D.AbstractFiles.GetFullPath(filename);
			
			is = F3D.Resource.GetInputStream(filename);
			
			// get size.
			int size = is.available();
			int rsize = 0;
			// Read the entire asset into a local byte buffer.
			byte[] buffer = new byte[size];

			DataInputStream in = new DataInputStream(is);
			ByteBuffer bb = BufferUtils.createByteBuffer(size);

			while (in.available() != 0)
			{
				bb.put(in.readByte());
				rsize++;
			}

			bb.rewind();
			bb.get(buffer);

			F3D.Log.info("F3D_PARSER", "   file size:" + String.valueOf(size));
			F3D.Log.info("F3D_PARSER", "   read size:" + String.valueOf(rsize));

			// Convert the buffer into a string.
			String[] text = new String(buffer).split("\n");

			for (int i = 0; i < text.length; i++)
			{
				String line = text[i];

				// skip empty lines
				if (line.length() == 0)
				{
					continue;
				}

				int idx_begin = line.indexOf(this.BEGIN_SEPARATOR);
				int idx_end = line.indexOf(this.END_SEPARATOR);

				if ((idx_begin >= 0) && (idx_end == -1))
				{
					_block = new TA3D_ParserBlock();
				}

				if ((idx_begin == -1) && (idx_end >= 0))
				{
					this.Blocks.add(_block);
				}

				if ((idx_begin == -1) && (idx_end == -1))
				{

					line = line.replaceAll("\t", "");
					line = line.replaceAll(" ", "");
					line = line.replaceAll("\r", "");
					line = line.replaceAll("\n", "");

					if (line.toCharArray()[0] != 0x23)
					{
						_block.Add(line);
					}
				}

			}
			is.close();
			this.Dump();
		} catch (IOException e)
		{
			F3D.Log.error("TF3D_PARSER", "TA3D_PARSER.ParseFile('" + filename
					+ "') " + e.toString());
		}
	}

	// -----------------------------------------------------------------------
	// TA3D_PARSER:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * get loaded blocks count <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @return block count
	 */
	// -----------------------------------------------------------------------
	public int GetBlocksCount()
	{
		return this.Blocks.size();
	}

	// -----------------------------------------------------------------------
	// TA3D_PARSER:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * read block from list <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param id
	 *            block id
	 * @return block
	 */
	// -----------------------------------------------------------------------
	public TA3D_ParserBlock GetBlockAt(int id)
	{
		return this.Blocks.get(id);
	}

	// -----------------------------------------------------------------------
	// TA3D_PARSER:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Set CurrentBlock to block from blocks list <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param id
	 *            block ID
	 */
	// -----------------------------------------------------------------------
	public void SetBlock(int id)
	{
		this.CurrentBlock = new TA3D_ParserBlock();
		this.CurrentBlock = this.Blocks.get(id);
	}

	// -----------------------------------------------------------------------
	// TA3D_PARSER:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * generate dump of parsed file to LOG console [Debug/DDMS] <BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	public void Dump()
	{
		if (F3D.PARSER_LOG)
		{
			for (int i = 0; i < this.Blocks.size(); i++)
			{
				F3D.Log.info("TF3D_PARSER", "PARSER: ------------ BLOCK ["
						+ String.valueOf(i) + "]-------------");
				this.SetBlock(i);

				for (int l = 0; l < this.CurrentBlock.lines.size(); l++)
				{
					F3D.Log.info("TF3D_PARSER", "PARSER: "
							+ this.CurrentBlock.lines.get(l));
				}
				F3D.Log.info("TF3D_PARSER", "PARSER: --------------------------------");
			}
		}
	}

	// -----------------------------------------------------------------------
	// TA3D_PARSER:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * get parsed value as string <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param _var
	 *            variable name
	 * @return string
	 */
	// -----------------------------------------------------------------------
	public String GetAs_STRING(String _var)
	{

		for (int i = 0; i < this.CurrentBlock.lines.size(); i++)
		{
			String LINE = this.CurrentBlock.lines.get(i);
			String[] VAR_NAME = LINE.split(this.VAR_SEPARATOR);
			if (_var.equalsIgnoreCase(VAR_NAME[0]))
			{
				return VAR_NAME[1];
			}
		}
		return "none";
	}

	// -----------------------------------------------------------------------
	// TA3D_PARSER:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * get parsed value as vector4f <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param _var
	 *            variable name
	 * @return vector4f
	 */
	// -----------------------------------------------------------------------
	public Vector4f GetAs_VECTOR4F(String _var)
	{
		Vector4f _res = new Vector4f(0, 0, 0, 0);

		for (int i = 0; i < this.CurrentBlock.lines.size(); i++)
		{
			String LINE = this.CurrentBlock.lines.get(i);
			String[] VAR_NAME = LINE.split(this.VAR_SEPARATOR);
			if (_var.equalsIgnoreCase(VAR_NAME[0]))
			{
				String[] VALUE_LIST = VAR_NAME[1].split(this.VALUE_SEPARATOR);
				_res.x = Float.valueOf(VALUE_LIST[0].trim()).floatValue();
				_res.y = Float.valueOf(VALUE_LIST[1].trim()).floatValue();
				_res.z = Float.valueOf(VALUE_LIST[2].trim()).floatValue();
				_res.w = Float.valueOf(VALUE_LIST[3].trim()).floatValue();
			}
		}
		return _res;
	}

	// -----------------------------------------------------------------------
	// TA3D_PARSER:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * get parsed value as vector3f <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param _var
	 *            variable name
	 * @return vector3f
	 */
	// -----------------------------------------------------------------------
	public Vector3f GetAs_VECTOR3F(String _var)
	{
		Vector3f _res = new Vector3f(0, 0, 0);

		for (int i = 0; i < this.CurrentBlock.lines.size(); i++)
		{
			String LINE = this.CurrentBlock.lines.get(i);
			String[] VAR_NAME = LINE.split(this.VAR_SEPARATOR);
			if (_var.equalsIgnoreCase(VAR_NAME[0]))
			{
				String[] VALUE_LIST = VAR_NAME[1].split(this.VALUE_SEPARATOR);
				_res.x = Float.valueOf(VALUE_LIST[0].trim()).floatValue();
				_res.y = Float.valueOf(VALUE_LIST[1].trim()).floatValue();
				_res.z = Float.valueOf(VALUE_LIST[2].trim()).floatValue();
			}
		}
		return _res;
	}

	// -----------------------------------------------------------------------
	// TA3D_PARSER:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * get parsed value as vector2f <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param _var
	 *            variable name
	 * @return vector2f
	 */
	// -----------------------------------------------------------------------
	public Vector2f GetAs_VECTOR2F(String _var)
	{
		Vector2f _res = new Vector2f(0, 0);

		for (int i = 0; i < this.CurrentBlock.lines.size(); i++)
		{
			String LINE = this.CurrentBlock.lines.get(i);
			String[] VAR_NAME = LINE.split(this.VAR_SEPARATOR);
			if (_var.equalsIgnoreCase(VAR_NAME[0]))
			{
				String[] VALUE_LIST = VAR_NAME[1].split(this.VALUE_SEPARATOR);
				_res.x = Float.valueOf(VALUE_LIST[0].trim()).floatValue();
				_res.y = Float.valueOf(VALUE_LIST[1].trim()).floatValue();

			}
		}
		return _res;
	}

	// -----------------------------------------------------------------------
	// TA3D_PARSER:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * get parsed value as boolean <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param _var
	 *            variable name
	 * @return boolena
	 */
	// -----------------------------------------------------------------------
	public Boolean GetAs_BOOLEAN(String _var)
	{

		for (int i = 0; i < this.CurrentBlock.lines.size(); i++)
		{
			String LINE = this.CurrentBlock.lines.get(i);
			String[] VAR_NAME = LINE.split(this.VAR_SEPARATOR);
			if (_var.equalsIgnoreCase(VAR_NAME[0]))
			{
				return Boolean.valueOf(VAR_NAME[1].trim()).booleanValue();
			}
		}
		return false;
	}

	// -----------------------------------------------------------------------
	// TA3D_PARSER:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * get parsed value as integer <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param _var
	 *            variable name
	 * @return integer
	 */
	// -----------------------------------------------------------------------
	public int GetAs_INTEGER(String _var)
	{

		for (int i = 0; i < this.CurrentBlock.lines.size(); i++)
		{
			String LINE = this.CurrentBlock.lines.get(i);
			String[] VAR_NAME = LINE.split(this.VAR_SEPARATOR);
			if (_var.equalsIgnoreCase(VAR_NAME[0]))
			{
				return Integer.valueOf(VAR_NAME[1].trim()).intValue();
			}
		}
		return 0;
	}

	// -----------------------------------------------------------------------
	// TA3D_PARSER:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * get parsed value as float <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param _var
	 *            variable name
	 * @return float
	 */
	// -----------------------------------------------------------------------
	public float GetAs_FLOAT(String _var)
	{

		for (int i = 0; i < this.CurrentBlock.lines.size(); i++)
		{
			String LINE = this.CurrentBlock.lines.get(i);
			String[] VAR_NAME = LINE.split(this.VAR_SEPARATOR);
			if (_var.equalsIgnoreCase(VAR_NAME[0]))
			{
				return Float.valueOf(VAR_NAME[1].trim()).floatValue();
			}
		}
		return 0.0f;
	}
}
