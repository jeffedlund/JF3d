/**
 * 
 */
package AGFX.F3D.Material;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

import AGFX.F3D.F3D;
import AGFX.F3D.Parser.TF3D_PARSER;

/**
 * @author AndyGFX
 * 
 */
// -----------------------------------------------------------------------
// TA3D_MaterialEventManager: CLASS
// -----------------------------------------------------------------------
public class TF3D_MaterialEventManager
{
	public ArrayList<TF3D_MaterialEvent> items;

	// -----------------------------------------------------------------------
	// TA3D_MaterialEventManager:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Constructor <BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	public TF3D_MaterialEventManager()
	{
		F3D.Log.info("TF3D_MaterialEventManager", "TA3D_MaterialEventManager: constructor");
		this.items = new ArrayList<TF3D_MaterialEvent>();

	}

	// -----------------------------------------------------------------------
	// TA3D_MaterialEventManager:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Add material event to Array list <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param event
	 *            material event
	 * @return material event ID
	 */
	// -----------------------------------------------------------------------
	public int Add(TF3D_MaterialEvent event)
	{
		if (this.Exist(event.name))
		{
			F3D.Log.info("TF3D_MaterialEventManager", "Add() '" + event.name + "' wasn't added - exist !");
			return this.FindByName(event.name);

		} else
		{
			int res = this.items.size();
			this.items.add(event);
			F3D.Log.info("TF3D_MaterialEventManager", "TA3D_MaterialEventManager: Add() '" + event.name + "'");
			return res;
		}
	}

	// -----------------------------------------------------------------------
	// TA3D_MaterialEventManager:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Get Material Event by name <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param name
	 *            material event name
	 * @return - material event
	 */
	// -----------------------------------------------------------------------
	public TF3D_MaterialEvent Get(String name)
	{
		TF3D_MaterialEvent ret = null;

		for (int i = 0; i < this.items.size(); i++)
		{
			if (this.items.get(i).name == name)
			{
				ret = this.items.get(i);
			}
		}
		return ret;
	}

	// -----------------------------------------------------------------------
	// TA3D_MaterialEventManager:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Find Material Event by name <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param name
	 *            material event name
	 * @return - material event ID
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
	// TA3D_MaterialEventManager:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Apply Material Event on defined Texture Memory Unit <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param tmu
	 *            texture unit [0..3]
	 * @param id
	 *            event ID
	 */
	// -----------------------------------------------------------------------
	public void Apply(int tmu, int id)
	{

		if (this.items.get(id).bAlphaBlend)
		{
			glEnable(GL_BLEND);
			glBlendFunc(this.items.get(id).Blend_SRC, this.items.get(id).Blend_DST);

		} else
		{
			glDisable(GL_BLEND);
			glBlendFunc(GL_ONE, GL_ONE);
		}

		// depth mask
		if (this.items.get(id).bDepthMask)
		{
			glDepthMask(true);
		} else
		{
			glDepthMask(false);
		}

		// alpha mask
		if (this.items.get(id).bAlphaMask)
		{
			glAlphaFunc(GL_GREATER, 0);
		}

		

		this.items.get(id).offset.x = this.items.get(id).offset.x + (this.items.get(id).scroll.x * F3D.Timer.AppSpeed());
		this.items.get(id).offset.y = this.items.get(id).offset.y + (-this.items.get(id).scroll.y * F3D.Timer.AppSpeed());

		this.items.get(id).angle = this.items.get(id).angle + this.items.get(id).rotate * F3D.Timer.AppSpeed();

		F3D.Textures.Begin_TranslateLayer(tmu, this.items.get(id).position.x, this.items.get(id).position.y, this.items.get(id).offset.x, this.items.get(id).offset.y, this.items.get(id).scale.x, this.items.get(id).scale.y, this.items.get(id).angle);

		// uv mode

		if (this.items.get(id).uv_mode == F3D.UV_MODE_UV_MAP)
		{
			glDisable(GL_TEXTURE_GEN_S);
			glDisable(GL_TEXTURE_GEN_T);
			glDisable(GL_TEXTURE_GEN_R);
		}

		if (this.items.get(id).uv_mode == F3D.UV_MODE_SPHERE_MAP)
		{
			glEnable(GL_TEXTURE_GEN_S);
			glEnable(GL_TEXTURE_GEN_T);
			glTexGeni(GL_S, GL_TEXTURE_GEN_MODE, GL_SPHERE_MAP);
			glTexGeni(GL_T, GL_TEXTURE_GEN_MODE, GL_SPHERE_MAP);
		}
		
		if (this.items.get(id).uv_mode == F3D.UV_MODE_REFLECTION_MAP)
		{
			glEnable(GL_TEXTURE_GEN_S);
			glEnable(GL_TEXTURE_GEN_T);
			glEnable(GL_TEXTURE_GEN_R);
			glTexGeni(GL_S, GL_TEXTURE_GEN_MODE, GL_REFLECTION_MAP);
			glTexGeni(GL_T, GL_TEXTURE_GEN_MODE, GL_REFLECTION_MAP);
			glTexGeni(GL_R, GL_TEXTURE_GEN_MODE, GL_REFLECTION_MAP);
		}
		
		if (this.items.get(id).uv_mode == F3D.UV_MODE_NORMAL_MAP)
		{
			glEnable(GL_TEXTURE_GEN_S);
			glEnable(GL_TEXTURE_GEN_T);
			glEnable(GL_TEXTURE_GEN_R);
			glTexGeni(GL_S, GL_TEXTURE_GEN_MODE, GL_NORMAL_MAP);
			glTexGeni(GL_T, GL_TEXTURE_GEN_MODE, GL_NORMAL_MAP);
			glTexGeni(GL_R, GL_TEXTURE_GEN_MODE, GL_NORMAL_MAP);
		}
		
	}

	// -----------------------------------------------------------------------
	// TA3D_MaterialEventManager:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * reset material event on: <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param tmu
	 *            texture memory unit [0..3]
	 */
	// -----------------------------------------------------------------------

	public void ResetEvent(int tmu)
	{
		F3D.Textures.End_TranslateLayer(tmu);
	}

	public int GetAsGLENUM(String _str)
	{
		int en = GL_ONE;

		if (_str.equalsIgnoreCase("GL_ONE"))
		{
			en = GL_ONE;
		}
		if (_str.equalsIgnoreCase("GL_ZERO"))
		{
			en = GL_ZERO;
		}
		if (_str.equalsIgnoreCase("GL_SRC_COLOR"))
		{
			en = GL_SRC_COLOR;
		}
		if (_str.equalsIgnoreCase("GL_ONE_MINUS_SRC_COLOR"))
		{
			en = GL_ONE_MINUS_SRC_COLOR;
		}
		if (_str.equalsIgnoreCase("GL_SRC_ALPHA"))
		{
			en = GL_SRC_ALPHA;
		}
		if (_str.equalsIgnoreCase("GL_ONE_MINUS_SRC_ALPHA"))
		{
			en = GL_ONE_MINUS_SRC_ALPHA;
		}
		if (_str.equalsIgnoreCase("GL_DST_ALPHA"))
		{
			en = GL_DST_ALPHA;
		}
		if (_str.equalsIgnoreCase("GL_ONE_MINUS_DST_ALPHA"))
		{
			en = GL_ONE_MINUS_DST_ALPHA;
		}
		if (_str.equalsIgnoreCase("GL_DST_COLOR"))
		{
			en = GL_DST_COLOR;
		}
		if (_str.equalsIgnoreCase("GL_ONE_MINUS_DST_COLOR"))
		{
			en = GL_ONE_MINUS_DST_COLOR;
		}
		if (_str.equalsIgnoreCase("GL_SRC_ALPHA_SATURATE"))
		{
			en = GL_SRC_ALPHA_SATURATE;
		}

		return en;
	}

	// -----------------------------------------------------------------------
	// TA3D_MaterialEventManager:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Load and create Material Event from file <BR>
	 * 
	 * Example:<BR>
	 * [MATERIAL_EVENTS]<BR>
	 * {<BR>
	 * name : e_default<BR>
	 * position : 0 , 0<BR>
	 * scale : 1 , 1<BR>
	 * scroll : 0 , 0<BR>
	 * angle : 0<BR>
	 * rotate : 0<BR>
	 * depthmask : true<BR>
	 * alphamask : true<BR>
	 * alphablend : false<BR>
	 * blend_src : GL_ONE<BR>
	 * blend_dst : GL_ONE<BR>
	 * }<BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param assetname
	 *            full path to file
	 */
	// -----------------------------------------------------------------------
	public void Load(String assetname)
	{
		TF3D_PARSER PARSER = new TF3D_PARSER();
		TF3D_MaterialEvent _event;

		PARSER.ParseFile(F3D.AbstractFiles.GetFullPath(assetname));

		for (int i = 0; i < PARSER.GetBlocksCount(); i++)
		{
			PARSER.SetBlock(i);

			_event = new TF3D_MaterialEvent();

			_event.name = PARSER.GetAs_STRING("name");
			_event.position = PARSER.GetAs_VECTOR2F("position");
			_event.scroll = PARSER.GetAs_VECTOR2F("scroll");
			_event.offset = PARSER.GetAs_VECTOR2F("offset");
			_event.angle = PARSER.GetAs_FLOAT("angle");
			_event.rotate = PARSER.GetAs_FLOAT("rotate");
			_event.scale = PARSER.GetAs_VECTOR2F("scale");
			_event.bAlphaMask = PARSER.GetAs_BOOLEAN("alphamask");
			_event.bAlphaBlend = PARSER.GetAs_BOOLEAN("alphablend");
			_event.bDepthMask = PARSER.GetAs_BOOLEAN("depthmask");

			String _tmp_str = PARSER.GetAs_STRING("blend_src");
			_event.Blend_SRC = this.GetAsGLENUM(_tmp_str);

			_tmp_str = PARSER.GetAs_STRING("blend_dst");
			_event.Blend_DST = this.GetAsGLENUM(_tmp_str);

			_tmp_str = PARSER.GetAs_STRING("uv_mode");

			if (_tmp_str.equals("UV_MODE_UV_MAP"))
				_event.uv_mode = F3D.UV_MODE_UV_MAP;
			
			if (_tmp_str.equals("UV_MODE_SPHERE_MAP"))
				_event.uv_mode = F3D.UV_MODE_SPHERE_MAP;
			
			if (_tmp_str.equals("UV_MODE_REFLECTION_MAP"))
				_event.uv_mode = F3D.UV_MODE_REFLECTION_MAP;
			
			if (_tmp_str.equals("UV_MODE_NORMAL_MAP"))
				_event.uv_mode = F3D.UV_MODE_NORMAL_MAP;

			this.Add(_event);

		}
	}

	// -----------------------------------------------------------------------
	// TA3D_MaterialEventManager:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * check if event exist in event list <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param name
	 * @return return true when event exist
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

	public void Destroy()
	{
		for (int m = 0; m < this.items.size(); m++)
		{
			this.items.remove(m);
		}
	}
}
