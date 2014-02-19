package AGFX.F3D.Texture;

import static org.lwjgl.opengl.GL11.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.fenggui.binding.render.ITexture;
import org.fenggui.util.SVGImageFactory;
import org.lwjgl.opengl.EXTTextureFilterAnisotropic;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.lwjgl.util.glu.MipMap;
import AGFX.F3D.F3D;
import AGFX.F3D.FrameBufferObject.TF3D_FrameBufferObject;

public class TF3D_Texture
{

	private Texture                texture;
	private TF3D_FrameBufferObject fbo_texture;
	public String                  name          = "noname";
	public String                  filename      = "";
	public Boolean                 bmipmap       = false;
	public int                     width         = 0;
	public int                     height        = 0;
	public Boolean                 b_texture_svg = false;
	public ITexture                texture_svg;
	public BufferedImage           buffer_svg;

	public TF3D_Texture(String _name)
	{
		F3D.Log.info("TF3D_Texture", "Create - constructor");
		this.name = _name;

		this.texture_svg = null;
		this.texture = null;
		this.fbo_texture = null;
	}

	public void Load(String filename, Boolean mipmap)
	{
		this.filename = filename;
		this.bmipmap = mipmap;
		F3D.Log.info("TF3D_Texture", "Loading ... " + filename);
		filename = F3D.AbstractFiles.GetFullPath(filename);
		String FMT = "PNG";

		try
		{
			if (filename.contains(".png"))
			{
				FMT = "PNG";
			}
			if (filename.contains(".jpg"))
			{
				FMT = "JPG";
			}

			if (filename.contains(".gif"))
			{
				FMT = "TGA";
			}
			if (filename.contains(".tga"))
			{
				FMT = "TGA";
			}

			if (filename.contains(".svg"))
			{
				FMT = "SVG";
				this.b_texture_svg = true;
			}

			if (this.b_texture_svg)
			{
				try
				{
					this.buffer_svg = SVGImageFactory.createSVGImage(filename, 512, 512);
					this.texture_svg = org.fenggui.binding.render.Binding.getInstance().getTexture(this.buffer_svg);
					this.width = this.texture_svg.getTextureWidth();
					this.height = this.texture_svg.getTextureHeight();
					

				} catch (Exception e)
				{
					
					F3D.Log.error("TF3D_Texture.Load(...)", "File: " + filename+" error message: "+e.toString());
					
				}
			} else
			{
				this.texture = this.LoadMipmap(FMT, filename, mipmap);

				this.width = this.texture.getImageWidth();
				this.height = this.texture.getImageHeight();
			}

		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
			F3D.Log.error("TF3D_Texture.Load(...)", "Missing file: " + filename);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	// -----------------------------------------------------------------------
	// TF3D_Texture:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Reload texture from file <BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	public void Reload()
	{
		if (this.b_texture_svg)
		{

		} else
		{
			if (this.texture != null)
			{
				this.texture.release();
				this.Load(this.filename, this.bmipmap);
			}
		}

	}

	// -----------------------------------------------------------------------
	// TF3D_Texture:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Generate MIPMAP <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param FMT
	 * @param filename
	 * @param mipmap
	 * @return
	 * @throws IOException
	 */
	// -----------------------------------------------------------------------
	private Texture LoadMipmap(String FMT, String filename, Boolean mipmap) throws IOException
	{

		Texture texture = null;
		InputStream is = null;

		is = F3D.Resource.GetInputStream(filename);

		texture = TextureLoader.getTexture(FMT, is);

		texture.bind();
		int width = (int) texture.getImageWidth();
		int height = (int) texture.getImageHeight();

		byte[] texbytes = texture.getTextureData();
		int components = texbytes.length / (width * height);

		if (mipmap)
		{
			MipMap.gluBuild2DMipmaps(GL11.GL_TEXTURE_2D, components, width, height, components == 3 ? GL11.GL_RGB : GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, F3D.GetBuffer.Byte(texbytes));
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
		} else
		{
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		}

		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);

		if (F3D.Config.r_anisotropy_filtering > 0)
		{
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT, F3D.Config.r_anisotropy_filtering);
		}

		return texture;
	}

	// -----------------------------------------------------------------------
	// TA3D_Texture:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Standart OpenGL texture bind<BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	public void Bind()
	{
		if (this.b_texture_svg)
		{
			
			glBindTexture(GL_TEXTURE_2D, this.texture_svg.getID());
		} else
		{
			if (this.texture != null)
			{
				this.texture.bind();
			}

			if (this.fbo_texture != null)
			{
				this.fbo_texture.BindTexture();
			}
		}
	}

	// -----------------------------------------------------------------------
	// TF3D_Texture:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * bind fbo texure from attachment id <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param fbo_attachment_id
	 *            - FBO attachment id
	 */
	// -----------------------------------------------------------------------
	public void Bind(int fbo_attachment_id)
	{
		if (this.fbo_texture != null)
		{
			this.fbo_texture.BindTexture(fbo_attachment_id);
		}
	}

	// -----------------------------------------------------------------------
	// TA3D_Texture:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Get OpenGL tetxure <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @return OGL texture ID
	 */
	// -----------------------------------------------------------------------
	public Texture Get()
	{
		return this.texture;
	}

	// -----------------------------------------------------------------------
	// TF3D_Texture:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Create texture from FBO with attachments <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param fbo
	 */
	// -----------------------------------------------------------------------
	public void CreateFromFBO(TF3D_FrameBufferObject fbo)
	{
		if (this.texture != null)
			this.texture.release();
		this.texture = null;
		this.fbo_texture = fbo;
	}
}
