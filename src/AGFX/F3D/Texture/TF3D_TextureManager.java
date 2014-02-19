package AGFX.F3D.Texture;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

import AGFX.F3D.F3D;
import AGFX.F3D.FrameBufferObject.TF3D_FrameBufferObject;
import AGFX.F3D.FrameBufferObject.TF3D_FrameBufferObject;
import AGFX.F3D.Parser.TF3D_PARSER;

public class TF3D_TextureManager
{
	public ArrayList<TF3D_Texture> items;

	// -----------------------------------------------------------------------
	// TA3D_TextureManager: constructor
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Create texture manager for handling textures <BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------

	public TF3D_TextureManager()
	{
		F3D.Log.info("TF3D_TextureManager", "Create TF3D_TextureManager class");
		this.items = new ArrayList<TF3D_Texture>();
		F3D.Log.info("TF3D_TextureManager", "Created");
	}

	// -----------------------------------------------------------------------
	// TA3D_TextureManager:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Add texture from assets folder <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param _name
	 *            texture name assigned to list
	 * @param _assetpath
	 *            path to texture
	 * @param mipmap
	 *            generate mipmaps true/false
	 */
	// -----------------------------------------------------------------------
	public void Add(String _name, String _assetpath, Boolean mipmap)
	{

		if (this.Exist(_name))
		{
			F3D.Log.warning("TA3D_TextureManager", ": TA3D_TextureManager.Add() '" + this.items.get(this.items.size() - 1).name + " EXIST !'");
		} else
		{

			TF3D_Texture tex = new TF3D_Texture(_name);
			tex.Load(_assetpath, mipmap);
			this.items.add(tex);

			F3D.Log.info("TA3D_TextureManager", ": TA3D_TextureManager.Add() '" + this.items.get(this.items.size() - 1).name + "'");
		}

	}

	// -----------------------------------------------------------------------
	// TA3D_TextureManager:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Add texture from assets folder <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param _name
	 *            texture name assigned to list
	 * @param _assetpath
	 *            path to texture
	 * @param mipmap
	 *            generate mipmaps true/false
	 */
	// -----------------------------------------------------------------------
	public void Add(String _name, TF3D_FrameBufferObject _fbo, Boolean mipmap)
	{

		if (this.Exist(_name))
		{
			F3D.Log.warning("TA3D_TextureManager", ": TA3D_TextureManager.Add() '" + this.items.get(this.items.size() - 1).name + " EXIST !'");
		} else
		{

			TF3D_Texture tex = new TF3D_Texture(_name);
			tex.CreateFromFBO(_fbo);
			this.items.add(tex);

			F3D.Log.info("TA3D_TextureManager", ": TA3D_TextureManager.Add() '" + this.items.get(this.items.size() - 1).name + "'");
		}

	}

	public void Add(String _name, int fbo_id, Boolean mipmap)
	{

		if (this.Exist(_name))
		{
			F3D.Log.warning("TA3D_TextureManager", ": TA3D_TextureManager.Add() '" + this.items.get(this.items.size() - 1).name + " EXIST !'");
		} else
		{

			TF3D_Texture tex = new TF3D_Texture(_name);
			tex.CreateFromFBO(F3D.FrameBuffers.Get(fbo_id));
			this.items.add(tex);

			F3D.Log.info("TA3D_TextureManager", ": TA3D_TextureManager.Add() '" + this.items.get(this.items.size() - 1).name + "'");
		}

	}

	public void ReplaceWithFBO(String text_name, TF3D_FrameBufferObject fbo)
	{
		int text_id = this.FindByName(text_name);
		this.items.get(text_id).CreateFromFBO(fbo);
	}

	public void ReplaceWithFBO(String text_name, int fbo_id)
	{
		int text_id = this.FindByName(text_name);
		this.items.get(text_id).CreateFromFBO(F3D.FrameBuffers.Get(fbo_id));
	}

	// -----------------------------------------------------------------------
	// TA3D_TextureManager: GetID
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Get ID by texture name <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param _name
	 *            search texture name
	 * @return texture ID from List
	 */
	// -----------------------------------------------------------------------
	public int FindByName(String _name)
	{
		int res = -1;

		for (int i = 0; i < this.items.size(); i++)
		{
			if (this.items.get(i).name.equalsIgnoreCase(_name))
			{
				res = i;
			}
		}

		return res;
	}

	// -----------------------------------------------------------------------
	// TA3D_TextureManager:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Bind texture by name <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param _name
	 *            texture name
	 */
	// -----------------------------------------------------------------------

	public void Bind(String _name)
	{
		int id = this.FindByName(_name);
		if (id != -1)
		{
			this.Bind(id);
		} else
		{
			F3D.Log.error("TF3D_TextureManager", "missing texture " + _name);
		}

	}

	// -----------------------------------------------------------------------
	// TA3D_TextureManager: Bind
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Bind texture by ID (best way when is changed during render loop) <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param id
	 *            texture ID
	 */
	// -----------------------------------------------------------------------

	public void Bind(int id)
	{
		this.items.get(id).Bind();
	}

	// -----------------------------------------------------------------------
	// TF3D_TextureManager:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Bind FBO texture from assigned FBO attachment id <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param _name
	 *            - texture name
	 * @param attachment_id
	 *            - attachment id
	 */
	// -----------------------------------------------------------------------
	public void Bind(String _name, int fbo_attachment_id)
	{
		int id = this.FindByName(_name);
		if (id != -1)
		{
			this.Bind(id, fbo_attachment_id);
		} else
		{
			F3D.Log.error("TF3D_TextureManager", "missing texture " + _name);
		}

	}

	// -----------------------------------------------------------------------
	// TF3D_TextureManager:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Bind FBO texture from assigned FBO attachment id <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param _name
	 *            - texture id
	 * @param attachment_id
	 *            - attachment id
	 */
	// -----------------------------------------------------------------------
	public void Bind(int id, int fbo_attachment_id)
	{
		this.items.get(id).Bind(fbo_attachment_id);
	}

	// -----------------------------------------------------------------------
	// TA3D_TextureManager:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * activate texture layer<BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param target
	 *            texture unit id [0..3]
	 */
	// -----------------------------------------------------------------------

	public void ActivateLayer(int target)
	{
		glClientActiveTexture(GL_TEXTURE0 + target);
		glActiveTexture(GL_TEXTURE0 + target);
		glEnable(GL_TEXTURE_2D);
	}

	// -----------------------------------------------------------------------
	// TA3D_TextureManager:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * deactivate texture layer<BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param target
	 *            texture unit id [0..3]
	 */
	// -----------------------------------------------------------------------

	public void DeactivateLayer(int target)
	{
		glActiveTexture(GL_TEXTURE0 + target);
		glDisable(GL_TEXTURE_2D);
	}

	public void DeactivateLayers()
	{
		this.DeactivateLayer(0);
		this.DeactivateLayer(1);
		this.DeactivateLayer(2);
		this.DeactivateLayer(3);
	}

	// -----------------------------------------------------------------------
	// TA3D_TextureManager: BeginTranslate
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Begin Translate texture mapping by OpenES <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param tmu
	 *            texture TMU id [0..3]
	 * @param px
	 *            x texture position
	 * @param py
	 *            y texture position
	 * @param dx
	 *            x texture offset
	 * @param dy
	 *            y texture offset
	 * @param scalex
	 *            x texture scale
	 * @param scaley
	 *            y texture scale
	 * @param angle
	 *            rotate texture [-360 ... 360]
	 */
	// -----------------------------------------------------------------------

	public void Begin_TranslateLayer(int tmu, float px, float py, float dx, float dy, float scalex, float scaley, float angle)
	{
		this.ActivateLayer(tmu);

		glMatrixMode(GL_TEXTURE);
		glLoadIdentity();
		glTranslatef(-px + dx, -px + dy, 0);
		glRotatef(angle, 0, 0, 1);
		glTranslatef(px, py, 0);
		glScalef(scalex, scaley, 1);

		glMatrixMode(GL_MODELVIEW);

	}

	// -----------------------------------------------------------------------
	// TA3D_TextureManager:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * reset texture transformation <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param tmu
	 *            texture TMU id [0..3]
	 */
	// -----------------------------------------------------------------------
	public void End_TranslateLayer(int tmu)
	{
		this.ActivateLayer(tmu);

		glMatrixMode(GL_TEXTURE);
		glLoadIdentity();
		glMatrixMode(GL_MODELVIEW);
	}

	// -----------------------------------------------------------------------
	// TA3D_TextureManager:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Get texture width <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param id
	 *            texture ID
	 * @return width
	 */
	// -----------------------------------------------------------------------
	public int GetWidth(int id)
	{
		return this.items.get(id).width;
	}

	// -----------------------------------------------------------------------
	// TA3D_TextureManager:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Get texture height <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param id
	 *            texture ID
	 * @return height
	 */
	// -----------------------------------------------------------------------
	public int GetHeight(int id)
	{
		return this.items.get(id).height;
	}

	// -----------------------------------------------------------------------
	// TA3D_TextureManager:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * check if texture exist in texture list <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param name
	 *            texture name
	 * @return return true when texture exist in list
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

	// -----------------------------------------------------------------------
	// TA3D_TextureManager:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Load/Add texture from definition file *.texture <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param assetname
	 *            texture filename *.texture
	 */
	// -----------------------------------------------------------------------
	public void Load(String assetname)
	{
		TF3D_PARSER PARSER = new TF3D_PARSER();

		PARSER.ParseFile(assetname);

		for (int i = 0; i < PARSER.GetBlocksCount(); i++)
		{
			PARSER.SetBlock(i);

			String _name = PARSER.GetAs_STRING("name");
			String _file = PARSER.GetAs_STRING("file");
			Boolean _mipmap = PARSER.GetAs_BOOLEAN("mipmap");

			this.Add(_name, _file, _mipmap);

		}
	}

	public void Destroy()
	{
		for (int m = 0; m < this.items.size(); m++)
		{
			this.items.remove(m);
		}
	}
}
