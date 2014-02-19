//-----------------------------------------------------------------------
// A3D_HUD_Image:
// -----------------------------------------------------------------------
package AGFX.F3D.Hud;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import org.lwjgl.util.vector.Vector2f;
import AGFX.F3D.F3D;
import AGFX.F3D.Parser.TF3D_PARSER;

//-----------------------------------------------------------------------
//A3D_HUD_Image: CLASS 
//-----------------------------------------------------------------------
/**
 * @author AndyGFX
 * 
 */
public class TF3D_HUD_Image extends TF3D_HUD_Gadget
{
	/** HUD image position on screen */
	public Vector2f position;

	/** texture ID from A3D.Textures list */
	public int      texture_id0;
	public int      texture_id1;
	public int      texture_id2;
	public int      texture_id3;
	public int      texture_id4;

	public TF3D_HUD_Image()
	{
		this.hudtype = 0;

	}

	// -----------------------------------------------------------------------
	// TA3D_HUD_Image:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Initialize (call this only once before render loop) <BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	public void Initialize()
	{

		if (this.property.Autosize == true)
		{
			this.size.x = F3D.Textures.GetWidth(this.texture_id0);
			this.size.y = F3D.Textures.GetHeight(this.texture_id0);
		}

	}

	// -----------------------------------------------------------------------
	// TA3D_HUD_Image:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Draw HUD image at positionm (x,y) <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param _x
	 *            x position
	 * @param _y
	 *            y position
	 */
	// -----------------------------------------------------------------------

	public void DrawAt(float _x, float _y)
	{
		this.DrawAt(_x, _y, texture_id0);
	}

	public void DrawAt(float _x, float _y, int tex_id)
	{
		if (this.visible == true)
		{
			// A3D.gl.glFrontFace(GL10.GL_CW);

			if (this.property.Texture == true)
			{
				F3D.Textures.ActivateLayer(0);
				F3D.Textures.Bind(tex_id);
			}

			if (this.property.Blend == true)
			{
				glEnable(GL_BLEND);
				glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
			}

			if (this.property.Clamp == true)
			{
				glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
				glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
			} else
			{
				glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
				glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
			}

			glColor4f(this.color.x, this.color.y, this.color.z, this.color.w);
			this.angle = this.angle + this.transform.rotate * F3D.Timer.AppSpeed();

			this.offset.x = this.offset.x + ((this.transform.scroll.x * F3D.Timer.AppSpeed()));
			this.offset.y = this.offset.y + ((this.transform.scroll.y * F3D.Timer.AppSpeed()));

			F3D.Textures.Begin_TranslateLayer(0, this.origin.x, this.origin.y, this.offset.x, this.offset.y, this.scale.x, this.scale.y, this.angle);

			glPushMatrix();

			glTranslatef(_x, _y, 0.0f);
			glRotatef(this.shape_angle, 0.0f, 0.0f, 1.0f);

			F3D.Draw.QuadBySize(0, 0, this.size.x, this.size.y, this.shape_origin.x, this.shape_origin.y, 0f);

			glPopMatrix();

			F3D.Textures.End_TranslateLayer(0);

			if (this.property.Texture == true)
			{
				F3D.Textures.DeactivateLayer(0);
			}

			if (this.property.Blend == true)
			{
				glDisable(GL_BLEND);
			}

			glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		}
		// A3D.gl.glFrontFace(GL10.GL_CCW);
	}

	// -----------------------------------------------------------------------
	// TA3D_HUD_Image:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Draw HUD image on pre-defined position <BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	public void Draw()
	{
		this.DrawAt(this.position.x, this.position.y);
	}

	public void Load(String filename, Boolean init_after_load)
	{
		TF3D_PARSER PARSER = new TF3D_PARSER();
		int BLOCK_ID;

		System.out.println("Loading config... " + filename);
		Boolean Exist = F3D.AbstractFiles.ExistFile(filename);

		if (!Exist)
		{
			System.out.print("Can't load file:" + filename);
		}

		PARSER.ParseFile(F3D.AbstractFiles.GetFullPath(filename));

		for (BLOCK_ID = 0; BLOCK_ID < PARSER.GetBlocksCount(); BLOCK_ID++)
		{
			PARSER.SetBlock(BLOCK_ID);

			this.angle = PARSER.GetAs_FLOAT("angle");
			this.color.set(PARSER.GetAs_VECTOR4F("color"));

			String type = PARSER.GetAs_STRING("type");
			if (type.equals("HUD_IMAGE"))
				this.hudtype = F3D.HUD_IMAGE;
			if (type.equals("HUD_IMAGEBUTTON"))
				this.hudtype = F3D.HUD_IMAGEBUTTON;

			this.name = PARSER.GetAs_STRING("name");
			this.offset.set(PARSER.GetAs_VECTOR2F("offset"));
			this.origin.set(PARSER.GetAs_VECTOR2F("origin"));
			this.scale.set(PARSER.GetAs_VECTOR2F("scale"));
			this.shape_angle = PARSER.GetAs_FLOAT("shape_angle");
			this.shape_origin.set(PARSER.GetAs_VECTOR2F("shape_origin"));
			this.size.set(PARSER.GetAs_VECTOR2F("size"));

			String texture = PARSER.GetAs_STRING("texture_name");
			this.texture_id0 = F3D.Textures.FindByName(texture);

			if (this.texture_id0 >= 0)
			{
				this.property.Texture = true;
			}
			
			this.texture_id1 = F3D.Textures.FindByName(texture+"_o");
			this.texture_id2 = F3D.Textures.FindByName(texture+"_p");
			this.texture_id3 = F3D.Textures.FindByName(texture+"_c");
			this.texture_id4 = F3D.Textures.FindByName(texture+"_u");
			
			this.visible = true;
			this.property.Autosize = PARSER.GetAs_BOOLEAN("autosize");
			this.property.Blend = PARSER.GetAs_BOOLEAN("blend");
			this.property.Clamp = PARSER.GetAs_BOOLEAN("clamp");
			this.property.Scissor = PARSER.GetAs_BOOLEAN("scissor");
			this.transform.rotate = PARSER.GetAs_FLOAT("rotate");
			this.transform.scroll.set(PARSER.GetAs_VECTOR2F("scroll"));

		}

		if (init_after_load)
		{
			this.Initialize();
		}
	}
}
