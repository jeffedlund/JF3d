package AGFX.F3D.Engine;

import static org.lwjgl.opengl.GL11.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import AGFX.F3D.F3D;

public class TF3D_RenderToTexture
{
	private int			texID;
	private int			size;
	private ByteBuffer	pixels;

	public TF3D_RenderToTexture(int size)
	{

		this.size = size;

		this.pixels = BufferUtils.createByteBuffer(size * size * 4);

		// Create shared texture
		IntBuffer texture_buffer = ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder()).asIntBuffer();
		glGenTextures(texture_buffer);
		this.texID = texture_buffer.get(0);

		glBindTexture(GL_TEXTURE_2D, this.texID);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, size, size, 0, GL_RGBA, GL_UNSIGNED_BYTE, (IntBuffer) null);
		// glTexImage2D(GL_TEXTURE_2D, 0, GL_INTENSITY, width, height, 0,
		// GL_LUMINANCE, GL_UNSIGNED_BYTE, BufferUtils.createIntBuffer(width *
		// height * 4));

		// glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP);
		// glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
	}

	public void BeginRender()
	{
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
		glViewport(0, 0, this.size, this.size);

	}

	public void EndRender()
	{
		glBindTexture(GL_TEXTURE_2D, this.texID);
		glReadPixels(0, 0, this.size, this.size, GL_RGB, GL_BYTE, this.pixels);

		//glTexImage2D(GL_TEXTURE_2D, 0, 3, this.size, this.size, 0, GL_LUMINANCE, GL_UNSIGNED_BYTE, pixels);
		glTexImage2D(GL_TEXTURE_2D, 0, 3, this.size, this.size, 0, GL_RGB, GL_UNSIGNED_BYTE, pixels);
		
		glViewport(0, 0, F3D.Config.r_display_width, F3D.Config.r_display_height);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
	    glColor4f(0, 0, 0, 1);
	}
	
	
	public void Bind()
	{
		F3D.Textures.DeactivateLayers();
		F3D.Textures.ActivateLayer(0);
        
		glEnable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D,this.texID);
		
		
	}
}
