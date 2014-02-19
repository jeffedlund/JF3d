package AGFX.F3D.Mesh;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import org.lwjgl.BufferUtils;

import AGFX.F3D.F3D;

import static org.lwjgl.opengl.ARBVertexProgram.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.ARBBufferObject.*;
import static org.lwjgl.opengl.ARBMultitexture.*;
import static org.lwjgl.opengl.ARBVertexBufferObject.*;

public class TF3D_VBO
{

	float               matDiffuse[]     = new float[] { 1.0f, 0.0f, 0.0f, 1.0f };

	/** The buffer holding the vertices */
	private FloatBuffer vertexBuffer;
	private boolean     b_vertexBuffer   = false;
	private int         vertex_buffer_id;

	/** The buffer holding the texture coordinates for TMU=0 */
	private FloatBuffer textureBuffer0;
	private boolean     b_textureBuffer0 = false;
	private int         texture0_buffer_id;

	/** The buffer holding the texture coordinates for TMU=1 */
	private FloatBuffer textureBuffer1;
	private boolean     b_textureBuffer1 = false;
	private int         texture1_buffer_id;

	/** The buffer holding the texture coordinates for TMU=2 */
	private FloatBuffer textureBuffer2;
	private boolean     b_textureBuffer2 = false;
	private int         texture2_buffer_id;

	/** The buffer holding the texture coordinates for TMU=3 */
	private FloatBuffer textureBuffer3;
	private boolean     b_textureBuffer3 = false;
	private int         texture3_buffer_id;

	/** The buffer holding the indices */
	private ShortBuffer indexBuffer;
	private boolean     b_indexBuffer    = false;
	private int         indices_length;
	private int         indices_id;

	/** The buffer holding the normals */
	private FloatBuffer normalBuffer;
	private boolean     b_normalBuffer   = false;
	private int         normal_buffer_id;

	/** The buffer holding the binormals */
	private FloatBuffer binormalBuffer;
	private boolean     b_binormalBuffer = false;
	private int         binormal_buffer_id;

	/** The buffer holding the normals */
	private FloatBuffer tangentBuffer;
	private boolean     b_tangentBuffer  = false;
	private int         tangent_buffer_id;

	/** The buffer holding the normals */
	private FloatBuffer colorBuffer;
	private boolean     b_colorBuffer    = false;
	private int         color_buffer_id;

	private Boolean     b_build          = false;

	// -----------------------------------------------------------------------
	// TA3D_VBO:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Constructor <BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	public TF3D_VBO()
	{

	}

	// -----------------------------------------------------------------------
	// TA3D_VBO:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Create vertex array <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param arr
	 *            array of vertices
	 */
	// -----------------------------------------------------------------------
	public void CreateVertexBuffer(float[] arr)
	{
		this.vertexBuffer = BufferUtils.createFloatBuffer(arr.length);
		this.vertexBuffer.put(arr);
		this.vertexBuffer.rewind();
		this.b_vertexBuffer = true;

	}

	// -----------------------------------------------------------------------
	// TA3D_VBO:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * update vertex array <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param arr
	 *            array of vertices
	 */
	// -----------------------------------------------------------------------
	public void UpdateVertexBuffer(float[] arr)
	{
		this.vertexBuffer.position(0);
		this.vertexBuffer.put(arr);
		this.vertexBuffer.rewind();

	}

	// -----------------------------------------------------------------------
	// TA3D_VBO:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * create color buffer <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param arr
	 *            array of vertex colors
	 */
	// -----------------------------------------------------------------------
	public void CreateColorBuffer(float[] arr)
	{

		this.colorBuffer = BufferUtils.createFloatBuffer(arr.length);
		this.colorBuffer.put(arr);
		this.colorBuffer.rewind();
		this.b_colorBuffer = true;
	}

	// -----------------------------------------------------------------------
	// TA3D_VBO:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * update color buffer <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param arr
	 *            array of vertex colors
	 */
	// -----------------------------------------------------------------------
	public void UpdateColorBuffer(float[] arr)
	{
		this.colorBuffer.position(0);
		this.colorBuffer.put(arr);
		this.colorBuffer.rewind();

	}

	// -----------------------------------------------------------------------
	// TA3D_VBO:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * create normal buffer <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param arr
	 *            array of normals
	 */
	// -----------------------------------------------------------------------
	public void CreateNormalBuffer(float[] arr)
	{

		this.normalBuffer = BufferUtils.createFloatBuffer(arr.length);
		this.normalBuffer.put(arr);
		this.normalBuffer.rewind();
		this.b_normalBuffer = true;

	}

	// -----------------------------------------------------------------------
	// TA3D_VBO:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * create binormal buffer <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param arr
	 *            array of normals
	 */
	// -----------------------------------------------------------------------
	public void CreateBinormalBuffer(float[] arr)
	{

		this.binormalBuffer = BufferUtils.createFloatBuffer(arr.length);
		this.binormalBuffer.put(arr);
		this.binormalBuffer.rewind();
		this.b_binormalBuffer = true;

	}

	// -----------------------------------------------------------------------
	// TA3D_VBO:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * create tangent buffer <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param arr
	 *            array of normals
	 */
	// -----------------------------------------------------------------------
	public void CreateTangentBuffer(float[] arr)
	{

		this.tangentBuffer = BufferUtils.createFloatBuffer(arr.length);
		this.tangentBuffer.put(arr);
		this.tangentBuffer.rewind();
		this.b_tangentBuffer = true;

	}

	// -----------------------------------------------------------------------
	// TA3D_VBO:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * update normal buffer <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param arr
	 *            array of normals
	 */
	// -----------------------------------------------------------------------
	public void UpdateNormalBuffer(float[] arr)
	{

		this.normalBuffer.position(0);
		this.normalBuffer.put(arr);
		this.normalBuffer.rewind();

	}

	// -----------------------------------------------------------------------
	// TA3D_VBO:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * update binormal buffer <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param arr
	 *            array of normals
	 */
	// -----------------------------------------------------------------------
	public void UpdateBinormalBuffer(float[] arr)
	{

		this.binormalBuffer.position(0);
		this.binormalBuffer.put(arr);
		this.binormalBuffer.rewind();

	}

	// -----------------------------------------------------------------------
	// TA3D_VBO:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * update tangent buffer <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param arr
	 *            array of normals
	 */
	// -----------------------------------------------------------------------
	public void UpdateTangentBuffer(float[] arr)
	{

		this.tangentBuffer.position(0);
		this.tangentBuffer.put(arr);
		this.tangentBuffer.rewind();

	}

	// -----------------------------------------------------------------------
	// TA3D_VBO:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * create texture UV buffer for TMU=0<BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param arr
	 *            array of UV coordinates
	 */
	// -----------------------------------------------------------------------
	public void CreateTextureBuffer(float[] arr, int tmu)
	{
		if (tmu == GL_TEXTURE0)
		{

			this.textureBuffer0 = BufferUtils.createFloatBuffer(arr.length);
			this.textureBuffer0.put(arr);
			this.textureBuffer0.rewind();
			this.b_textureBuffer0 = true;

		}

		if (tmu == GL_TEXTURE1)
		{

			this.textureBuffer1 = BufferUtils.createFloatBuffer(arr.length);
			this.textureBuffer1.put(arr);
			this.textureBuffer1.rewind();
			this.b_textureBuffer1 = true;

		}

		if (tmu == GL_TEXTURE2)
		{

			this.textureBuffer2 = BufferUtils.createFloatBuffer(arr.length);
			this.textureBuffer2.put(arr);
			this.textureBuffer2.rewind();
			this.b_textureBuffer2 = true;

		}

		if (tmu == GL_TEXTURE3)
		{

			this.textureBuffer3 = BufferUtils.createFloatBuffer(arr.length);
			this.textureBuffer3.put(arr);
			this.textureBuffer3.rewind();
			this.b_textureBuffer3 = true;

		}
	}

	// -----------------------------------------------------------------------
	// TA3D_VBO:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * update texture UV buffer for TMU=0<BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param arr
	 *            array of UV coordinates
	 */
	// -----------------------------------------------------------------------
	public void UpdateTextureBuffer(float[] arr, int tmu)
	{
		if (tmu == GL_TEXTURE0)
		{
			this.textureBuffer0.position(0);
			this.textureBuffer0.put(arr);
			this.textureBuffer0.rewind();

		}

		if (tmu == GL_TEXTURE1)
		{
			this.textureBuffer1.position(0);
			this.textureBuffer1.put(arr);
			this.textureBuffer1.rewind();

		}

		if (tmu == GL_TEXTURE2)
		{
			this.textureBuffer2.position(0);
			this.textureBuffer2.put(arr);
			this.textureBuffer2.rewind();

		}

		if (tmu == GL_TEXTURE3)
		{
			this.textureBuffer3.position(0);
			this.textureBuffer3.put(arr);
			this.textureBuffer3.rewind();

		}
	}

	// -----------------------------------------------------------------------
	// TA3D_VBO:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * create indices buffer <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param indices
	 *            array of faces indices
	 */
	// -----------------------------------------------------------------------
	public void CreateIndicesBuffer(short[] indices)
	{
		this.indexBuffer = BufferUtils.createShortBuffer(indices.length);
		this.indexBuffer.clear();
		this.indexBuffer.put(indices);
		this.indexBuffer.position(0);
		this.b_indexBuffer = true;
		this.indices_length = indices.length;
	}

	public void Build()
	{
		// AS VBO
		// F3D.Extensions.VertexBufferObject = false;

		if (F3D.Extensions.VertexBufferObject)
		{
			if (this.b_vertexBuffer)
			{
				this.vertex_buffer_id = glGenBuffersARB();
				glBindBufferARB(GL_ARRAY_BUFFER_ARB, this.vertex_buffer_id);
				this.vertexBuffer.rewind();
				glBufferDataARB(GL_ARRAY_BUFFER_ARB, this.vertexBuffer, GL_STATIC_DRAW_ARB);
			}

			if (this.b_colorBuffer)
			{
				this.color_buffer_id = glGenBuffersARB();
				glBindBufferARB(GL_ARRAY_BUFFER_ARB, this.color_buffer_id);
				this.colorBuffer.rewind();
				glBufferDataARB(GL_ARRAY_BUFFER_ARB, this.colorBuffer, GL_STATIC_DRAW_ARB);

			}

			if (this.b_normalBuffer)
			{
				this.normal_buffer_id = glGenBuffersARB();
				glBindBufferARB(GL_ARRAY_BUFFER_ARB, this.normal_buffer_id);
				this.normalBuffer.rewind();
				glBufferDataARB(GL_ARRAY_BUFFER_ARB, this.normalBuffer, GL_STATIC_DRAW_ARB);

			}

			if (this.b_binormalBuffer)
			{
				this.binormal_buffer_id = glGenBuffersARB();
				glBindBufferARB(GL_ARRAY_BUFFER_ARB, this.binormal_buffer_id);
				this.binormalBuffer.rewind();
				glBufferDataARB(GL_ARRAY_BUFFER_ARB, this.binormalBuffer, GL_STATIC_DRAW_ARB);

			}

			if (this.b_tangentBuffer)
			{
				this.tangent_buffer_id = glGenBuffersARB();
				glBindBufferARB(GL_ARRAY_BUFFER_ARB, this.tangent_buffer_id);
				this.tangentBuffer.rewind();
				glBufferDataARB(GL_ARRAY_BUFFER_ARB, this.tangentBuffer, GL_STATIC_DRAW_ARB);

			}

			if (this.b_textureBuffer0)
			{
				this.texture0_buffer_id = glGenBuffersARB();
				glBindBufferARB(GL_ARRAY_BUFFER_ARB, this.texture0_buffer_id);
				this.textureBuffer0.rewind();
				glBufferDataARB(GL_ARRAY_BUFFER_ARB, this.textureBuffer0, GL_STATIC_DRAW_ARB);
			}
			
			if (this.b_textureBuffer1)
			{
				this.texture1_buffer_id = glGenBuffersARB();
				glBindBufferARB(GL_ARRAY_BUFFER_ARB, this.texture1_buffer_id);
				this.textureBuffer1.rewind();
				glBufferDataARB(GL_ARRAY_BUFFER_ARB, this.textureBuffer1, GL_STATIC_DRAW_ARB);
			}
			
			if (this.b_textureBuffer2)
			{
				this.texture2_buffer_id = glGenBuffersARB();
				glBindBufferARB(GL_ARRAY_BUFFER_ARB, this.texture2_buffer_id);
				this.textureBuffer2.rewind();
				glBufferDataARB(GL_ARRAY_BUFFER_ARB, this.textureBuffer2, GL_STATIC_DRAW_ARB);
			}
			
			if (this.b_textureBuffer3)
			{
				this.texture3_buffer_id = glGenBuffersARB();
				glBindBufferARB(GL_ARRAY_BUFFER_ARB, this.texture3_buffer_id);
				this.textureBuffer3.rewind();
				glBufferDataARB(GL_ARRAY_BUFFER_ARB, this.textureBuffer3, GL_STATIC_DRAW_ARB);
			}

		}

		this.b_build = true;

	}

	public void Bind_TBN_Attributs()
	{
		// http://www.fabiensanglard.net/bumpMapping/index.php
		
		if (F3D.Extensions.VertexBufferObject)
		{

			if (this.b_binormalBuffer)
			{
				glEnableClientState(GL_NORMAL_ARRAY);
				glBindBufferARB(GL_ARRAY_BUFFER_ARB, this.binormal_buffer_id);
				glNormalPointer(GL_FLOAT, 0, 0);
				
				// glEnableVertexAttribArrayARB(F3D.Shaders.shader[shd_id].loc_Tangent);
				 /*
                 glBindBufferARB(GL_ARRAY_BUFFER_ARB, VBOTangents);
                 glVertexAttribPointerARB(F3D.Shaders.shader[shd_id].loc_Tangent, 3, GL_FLOAT, false, 0, nil);
                 */

				
			}

			if (this.b_tangentBuffer)
			{
				glEnableClientState(GL_NORMAL_ARRAY);
				glBindBufferARB(GL_ARRAY_BUFFER_ARB, this.tangent_buffer_id);
				glNormalPointer(GL_FLOAT, 0, 0);

				/*
				 glEnableVertexAttribArrayARB(F3D.Shaders.shader[shd_id].loc_Tangent);
                 glBindBufferARB(GL_ARRAY_BUFFER_ARB, VBOTangents);
                 glVertexAttribPointerARB(F3D.Shaders.shader[shd_id].loc_Tangent, 3, GL_FLOAT, false, 0, nil);
                 */

			}

		} else
		{

			if (this.b_binormalBuffer)
			{
				glEnableClientState(GL_NORMAL_ARRAY);
				glNormalPointer(3, this.binormalBuffer);
			}

			if (this.b_tangentBuffer)
			{
				glEnableClientState(GL_NORMAL_ARRAY);
				glNormalPointer(3, this.tangentBuffer);
			}

		}
	}

	public void Bind()
	{

		if (F3D.Extensions.VertexBufferObject)
		{
			if (this.b_vertexBuffer)
			{
				glEnableClientState(GL_VERTEX_ARRAY);
				glBindBufferARB(GL_ARRAY_BUFFER_ARB, this.vertex_buffer_id);
				glVertexPointer(3, GL_FLOAT, 0, 0);
			}

			if (this.b_colorBuffer)
			{
				glEnableClientState(GL_COLOR_ARRAY);
				glBindBufferARB(GL_ARRAY_BUFFER_ARB, this.color_buffer_id);
				glColorPointer(4, GL_FLOAT, 0, 0);

			}

			if (this.b_normalBuffer)
			{
				glEnableClientState(GL_NORMAL_ARRAY);
				glBindBufferARB(GL_ARRAY_BUFFER_ARB, this.normal_buffer_id);
				glNormalPointer(GL_FLOAT, 0, 0);
			}

			if (this.b_textureBuffer0)
			{
				glClientActiveTextureARB(GL_TEXTURE0);
				glEnableClientState(GL_TEXTURE_COORD_ARRAY);
				glBindBufferARB(GL_ARRAY_BUFFER_ARB, this.texture0_buffer_id);
				glTexCoordPointer(2, GL_FLOAT, 0, 0);
			}
			if (this.b_textureBuffer1)
			{
				glClientActiveTextureARB(GL_TEXTURE1);
				glEnableClientState(GL_TEXTURE_COORD_ARRAY);
				glBindBufferARB(GL_ARRAY_BUFFER_ARB, this.texture1_buffer_id);
				glTexCoordPointer(2, GL_FLOAT, 0, 0);
			}
			if (this.b_textureBuffer2)
			{
				glClientActiveTextureARB(GL_TEXTURE2);
				glEnableClientState(GL_TEXTURE_COORD_ARRAY);
				glBindBufferARB(GL_ARRAY_BUFFER_ARB, this.texture2_buffer_id);
				glTexCoordPointer(2, GL_FLOAT, 0, 0);
			}
			if (this.b_textureBuffer3)
			{
				glClientActiveTextureARB(GL_TEXTURE3);
				glEnableClientState(GL_TEXTURE_COORD_ARRAY);
				glBindBufferARB(GL_ARRAY_BUFFER_ARB, this.texture3_buffer_id);
				glTexCoordPointer(2, GL_FLOAT, 0, 0);
			}

		} else
		{
			if (this.b_vertexBuffer)
			{
				glEnableClientState(GL_VERTEX_ARRAY);
				glVertexPointer(3, 0, this.vertexBuffer);
			}

			if (this.b_colorBuffer)
			{
				glEnableClientState(GL_COLOR_ARRAY);
				glColorPointer(4, 0, this.colorBuffer);

			}

			if (this.b_normalBuffer)
			{
				glEnableClientState(GL_NORMAL_ARRAY);
				glNormalPointer(3, this.normalBuffer);
			}

			if (this.b_textureBuffer0)
			{
				glClientActiveTextureARB(GL_TEXTURE0);
				glEnableClientState(GL_TEXTURE_COORD_ARRAY);
				glTexCoordPointer(2, 0, this.textureBuffer0);
			}
			if (this.b_textureBuffer1)
			{
				glClientActiveTextureARB(GL_TEXTURE1);
				glEnableClientState(GL_TEXTURE_COORD_ARRAY);
				glTexCoordPointer(2, 0, this.textureBuffer1);
			}
			if (this.b_textureBuffer2)
			{
				glClientActiveTextureARB(GL_TEXTURE2);
				glEnableClientState(GL_TEXTURE_COORD_ARRAY);
				glTexCoordPointer(2, 0, this.textureBuffer2);
			}
			if (this.b_textureBuffer3)
			{
				glClientActiveTextureARB(GL_TEXTURE3);
				glEnableClientState(GL_TEXTURE_COORD_ARRAY);
				glTexCoordPointer(2, 0, this.textureBuffer3);
			}
		}

	}

	public void UnBind()
	{
		if (F3D.Extensions.VertexBufferObject)
		{
			glBindBufferARB(GL_ARRAY_BUFFER_ARB, 0);
		}

		glDisableClientState(GL_VERTEX_ARRAY);
		glDisableClientState(GL_COLOR_ARRAY);
		glDisableClientState(GL_TEXTURE_COORD_ARRAY);
		glDisableClientState(GL_NORMAL_ARRAY);
	}

	// -----------------------------------------------------------------------
	// TA3D_VBO:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * draw assigned Elements <BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	public void DrawVertexBuffer(ShortBuffer gindices)
	{
		if (this.b_build)
		{
			if (F3D.Extensions.VertexBufferObject)
			{
				glDrawElements(GL_TRIANGLES, gindices);
			} else
			{
				glDrawArrays(GL_TRIANGLES, 0, this.indices_length);
			}
		} else
		{
			F3D.Log.error("TF3D_VBO", "DrawVertexBuffer() : You have to call Build method when you have finished VBO data creation");
		}

	}
}
