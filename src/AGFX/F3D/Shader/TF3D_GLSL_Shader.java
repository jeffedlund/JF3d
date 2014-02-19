package AGFX.F3D.Shader;

/* *Author: Abdul Bezrati *Email: abezrati@hotmail.com */
// http://www.opengl.org/discussion_boards/ubbthreads.php?ubb=showflat&Number=236976
/* Fixed Memory leaks due to too many bytebuffers - TheSmit  email: TheSmit@warmtart.co.uk */

import org.lwjgl.opengl.ARBFragmentProgram;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.ARBVertexProgram;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL20.*;
import java.io.FileInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.lwjgl.BufferUtils;

import AGFX.F3D.F3D;

import java.nio.FloatBuffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class TF3D_GLSL_Shader
{
	static ByteBuffer buffer    = BufferUtils.createByteBuffer(1024); ;
	static IntBuffer  logBuffer = BufferUtils.createIntBuffer(1);

	public static int loadProgramCode(String filename, boolean isFragment)
	{
		if (isFragment && !F3D.Extensions.GLSL_FragmentProgram)
		{
			System.out.println("GL_ARB_fragment_program is not supported, skipping.");
			return 0;
		} else if (!F3D.Extensions.GLSL_VertexProgram)
		{
			System.out.println("GL_ARB_vertex_program is not supported, skipping.");
			return 0;
		}

		ByteBuffer shaderPro = getProgramCode(filename);
		IntBuffer id = BufferUtils.createIntBuffer(1);

		if (isFragment)
		{
			ARBFragmentProgram.glGenProgramsARB(id);
			ARBFragmentProgram.glBindProgramARB(ARBFragmentProgram.GL_FRAGMENT_PROGRAM_ARB, id.get(0));
			ARBFragmentProgram.glProgramStringARB(ARBFragmentProgram.GL_FRAGMENT_PROGRAM_ARB, ARBFragmentProgram.GL_PROGRAM_FORMAT_ASCII_ARB, shaderPro);
			System.out.println(GL11.glGetString(ARBFragmentProgram.GL_PROGRAM_ERROR_STRING_ARB));
		} else
		{
			ARBVertexProgram.glGenProgramsARB(id);
			ARBVertexProgram.glBindProgramARB(ARBVertexProgram.GL_VERTEX_PROGRAM_ARB, id.get(0));
			ARBVertexProgram.glProgramStringARB(ARBVertexProgram.GL_VERTEX_PROGRAM_ARB, ARBVertexProgram.GL_PROGRAM_FORMAT_ASCII_ARB, shaderPro);
			System.out.println(GL11.glGetString(ARBVertexProgram.GL_PROGRAM_ERROR_STRING_ARB));
		}
		return id.get(0);
	}

	public static int loadShadersCode(String vertexShaderFile, String fragmentShaderFile)
	{
		if (!F3D.Extensions.GLSL_FragmenShader || !F3D.Extensions.GLSL_VertexShader)
		{
			F3D.Log.error("TF3D_GLSL_Shader", "GL_ARB_fragment_shader or GL_ARB_vertex_shader is not supported, skipping.");
		}

		ByteBuffer vertexShader = getProgramCode(vertexShaderFile), fragmentShader = getProgramCode(fragmentShaderFile);
		int vertexShaderID = 0, fragmentShaderID = 0, linkedShadersID = 0;
		vertexShaderID = ARBShaderObjects.glCreateShaderObjectARB(ARBVertexShader.GL_VERTEX_SHADER_ARB);
		ARBShaderObjects.glShaderSourceARB(vertexShaderID, vertexShader);
		ARBShaderObjects.glCompileShaderARB(vertexShaderID);
		printShaderObjectInfoLog(vertexShaderFile, vertexShaderID);
		ARBShaderObjects.glGetObjectParameterARB(vertexShaderID, ARBShaderObjects.GL_OBJECT_COMPILE_STATUS_ARB, logBuffer);
		if (logBuffer.get(0) == GL11.GL_FALSE)
		{

			F3D.Log.error("TF3D_GLSL_Shader", "Error in vertex shader");
		}
		fragmentShaderID = ARBShaderObjects.glCreateShaderObjectARB(ARBFragmentShader.GL_FRAGMENT_SHADER_ARB);
		ARBShaderObjects.glShaderSourceARB(fragmentShaderID, fragmentShader);
		ARBShaderObjects.glCompileShaderARB(fragmentShaderID);
		printShaderObjectInfoLog(fragmentShaderFile, fragmentShaderID);
		ARBShaderObjects.glGetObjectParameterARB(fragmentShaderID, ARBShaderObjects.GL_OBJECT_COMPILE_STATUS_ARB, logBuffer);
		if (logBuffer.get(0) == GL11.GL_FALSE)
		{
			F3D.Log.error("TF3D_GLSL_Shader", "Error in fragment shader");
		}
		linkedShadersID = ARBShaderObjects.glCreateProgramObjectARB();
		ARBShaderObjects.glAttachObjectARB(linkedShadersID, vertexShaderID);
		ARBShaderObjects.glAttachObjectARB(linkedShadersID, fragmentShaderID);
		ARBShaderObjects.glLinkProgramARB(linkedShadersID);
		printShaderProgramInfoLog(linkedShadersID);
		ARBShaderObjects.glGetObjectParameterARB(linkedShadersID, ARBShaderObjects.GL_OBJECT_LINK_STATUS_ARB, logBuffer);
		if (logBuffer.get(0) == GL11.GL_FALSE)
		{
			System.out.println("Error in linking shaders");
			F3D.Log.error("TF3D_GLSL_Shader", "Error in linking shaders");
		}
		F3D.Log.info("TF3D_GLSL_Shader", "Loading and Link: ");
		F3D.Log.info("TF3D_GLSL_Shader", "	Vertex shaders   : '" + vertexShaderFile + "' linked OK.");
		F3D.Log.info("TF3D_GLSL_Shader", "	Fragment shaders : '" + fragmentShaderFile + "' linked OK.");
		return linkedShadersID;
	}

	public static void sendAttrib1f(int id, String name, float value)
	{

		int location = ARBVertexShader.glGetAttribLocationARB(id, name);
		if (checkGLError(location, name) == 0)
			return;
		ARBVertexShader.glVertexAttrib1fARB(location, value);
	}

	public static void sendAttrib2f(int id, String name, float value1, float value2)
	{

		int location = ARBVertexShader.glGetAttribLocationARB(id, name);
		if (checkGLError(location, name) == 0)
			return;
		ARBVertexShader.glVertexAttrib2fARB(location, value1, value2);
	}

	public static void sendAttrib3f(int id, String name, float value1, float value2, float value3)
	{

		int location = ARBVertexShader.glGetAttribLocationARB(id, name);
		if (checkGLError(location, name) == 0)
			return;
		ARBVertexShader.glVertexAttrib3fARB(location, value1, value2, value3);
	}

	public static void sendAttrib4f(int id, String name, float value1, float value2, float value3, float value4)
	{

		int location = ARBVertexShader.glGetAttribLocationARB(id, name);
		if (checkGLError(location, name) == 0)
			return;
		ARBVertexShader.glVertexAttrib4fARB(location, value1, value2, value3,value4);
	}
	
	
	public static int GetAttribLocation(int id,String name)
	{
		int res = -1;
		return ARBVertexShader.glGetAttribLocationARB(id, name);
	}
	
	public static void sendUniform1i(int id, String name, int value)
	{
		int location = getUniformLocation(id, name);
		if (checkGLError(location, name) == 0)
			return;
		ARBShaderObjects.glUniform1iARB(location, value);
	}

	public static void sendUniform2i(int id, String name, int value1, int value2)
	{
		int location = getUniformLocation(id, name);
		if (checkGLError(location, name) == 0)
			return;
		ARBShaderObjects.glUniform2iARB(location, value1, value2);
	}

	public static void sendUniform3i(int id, String name, int value1, int value2, int value3)
	{
		int location = getUniformLocation(id, name);
		if (checkGLError(location, name) == 0)
			return;
		ARBShaderObjects.glUniform3iARB(location, value1, value2, value3);
	}

	public static void sendUniform4i(int id, String name, int value1, int value2, int value3, int value4)
	{
		int location = getUniformLocation(id, name);
		if (checkGLError(location, name) == 0)
			return;
		ARBShaderObjects.glUniform4iARB(location, value1, value2, value3, value4);
	}

	public static void sendUniform1f(int id, String name, float value)
	{
		int location = getUniformLocation(id, name);
		if (checkGLError(location, name) == 0)
			return;
		ARBShaderObjects.glUniform1fARB(location, value);
	}

	public static void sendUniform2f(int id, String name, float value1, float value2)
	{
		int location = getUniformLocation(id, name);
		if (checkGLError(location, name) == 0)
			return;
		ARBShaderObjects.glUniform2fARB(location, value1, value2);
	}

	public static void sendUniform3f(int id, String name, float value1, float value2, float value3)
	{
		int location = getUniformLocation(id, name);
		if (checkGLError(location, name) == 0)
			return;
		ARBShaderObjects.glUniform3fARB(location, value1, value2, value3);
	}

	public static void sendUniform4f(int id, String name, float value1, float value2, float value3, float value4)
	{
		int location = getUniformLocation(id, name);
		if (checkGLError(location, name) == 0)
			return;
		ARBShaderObjects.glUniform4fARB(location, value1, value2, value3, value4);
	}

	public static void sendUniform1FB(int id, String name, FloatBuffer values)
	{
		int location = getUniformLocation(id, name);
		if (checkGLError(location, name) == 0)
			return;
		ARBShaderObjects.glUniform1ARB(location, values);
	}

	public static void sendUniform2FB(int id, String name, FloatBuffer values)
	{
		int location = getUniformLocation(id, name);
		if (checkGLError(location, name) == 0)
			return;
		ARBShaderObjects.glUniform2ARB(location, values);
	}

	public static void sendUniform3FB(int id, String name, FloatBuffer values)
	{
		int location = getUniformLocation(id, name);
		if (checkGLError(location, name) == 0)
			return;
		ARBShaderObjects.glUniform3ARB(location, values);
	}

	public static void sendUniform4FB(int id, String name, FloatBuffer values)
	{
		int location = getUniformLocation(id, name);
		if (checkGLError(location, name) == 0)
			return;
		ARBShaderObjects.glUniform4ARB(location, values);
	}

	public static void sendUniform1IB(int id, String name, IntBuffer values)
	{
		int location = getUniformLocation(id, name);
		if (checkGLError(location, name) == 0)
			return;
		ARBShaderObjects.glUniform1ARB(location, values);
	}

	public static void sendUniform2IB(int id, String name, IntBuffer values)
	{
		int location = getUniformLocation(id, name);
		if (checkGLError(location, name) == 0)
			return;
		ARBShaderObjects.glUniform2ARB(location, values);
	}

	public static void sendUniform3IB(int id, String name, IntBuffer values)
	{
		int location = getUniformLocation(id, name);
		if (checkGLError(location, name) == 0)
			return;
		ARBShaderObjects.glUniform3ARB(location, values);
	}

	public static void sendUniform4IB(int id, String name, IntBuffer values)
	{
		int location = getUniformLocation(id, name);
		if (checkGLError(location, name) == 0)
			return;
		ARBShaderObjects.glUniform4ARB(location, values);
	}

	private static int getUniformLocation(int id, String name)
	{
		buffer.clear();
		buffer.put(name.getBytes());
		buffer.put((byte) 0);
		buffer.flip();
		return ARBShaderObjects.glGetUniformLocationARB(id, buffer);
	}

	private static void printShaderObjectInfoLog(String file, int ID)
	{
		ARBShaderObjects.glGetObjectParameterARB(ID, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB, logBuffer);
		final int logLength = logBuffer.get(0);
		if (logLength <= 1)
			return;
		final ByteBuffer log = BufferUtils.createByteBuffer(logLength);
		ARBShaderObjects.glGetInfoLogARB(ID, null, log);
		byte[] byteArray = new byte[logLength];
		log.get(byteArray);
		System.out.println("\nInfo Log of Shader Object: " + file);
		System.out.println("--------------------------");
		System.out.println(new String(byteArray));
	}

	private static void printShaderProgramInfoLog(int ID)
	{
		ARBShaderObjects.glGetObjectParameterARB(ID, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB, logBuffer);
		final int logLength = logBuffer.get(0);
		if (logLength <= 1)
			return;
		final ByteBuffer log = BufferUtils.createByteBuffer(logLength);
		ARBShaderObjects.glGetInfoLogARB(ID, null, log);
		byte[] byteArray = new byte[logLength];
		log.get(byteArray);
		System.out.println("\nShader Program Info Log: ");
		System.out.println("--------------------------");
		System.out.println(new String(byteArray));
	}

	private static ByteBuffer getProgramCode(String filename)
	{
		/*
		ClassLoader fileLoader = TF3D_GLSL_Shader.class.getClassLoader();
		InputStream fileInputStream = fileLoader.getResourceAsStream(filename);
		byte[] shaderCode = null;
		try
		{
			if (fileInputStream == null)
				fileInputStream = new FileInputStream(filename);
			DataInputStream dataStream = new DataInputStream(fileInputStream);
			dataStream.readFully(shaderCode = new byte[fileInputStream.available()]);
			fileInputStream.close();
			dataStream.close();
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		ByteBuffer shaderPro = BufferUtils.createByteBuffer(shaderCode.length).put(shaderCode);
		shaderPro.flip();
		
		return shaderPro;
		
		

		*/
		
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

			bb.clear();
			bb.get(buffer);
			bb.flip();
			return bb;
		
	} catch (IOException e)
	{
		F3D.Log.error("TF3D_PARSER", "TA3D_PARSER.ParseFile('" + filename
				+ "') " + e.toString());
	}
		
	return null;
	
		
	}

	private static int checkGLError(int location, String name)
	{
		if (location == -1)
		{
			System.out.println("Error: couldn't loacte " + name);
			return 0;
		}
		return 1;
	}

}