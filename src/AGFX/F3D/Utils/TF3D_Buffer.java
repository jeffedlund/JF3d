package AGFX.F3D.Utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import org.lwjgl.BufferUtils;

public class TF3D_Buffer
{
	public static FloatBuffer fB = BufferUtils.createFloatBuffer(16);
	public static IntBuffer iB = BufferUtils.createIntBuffer(16);
	public static ByteBuffer bB = BufferUtils.createByteBuffer(16);
	
	
	public FloatBuffer Float(float[] f)
	{
		TF3D_Buffer.fB.clear();
		TF3D_Buffer.fB.put(f).flip();
		
		return TF3D_Buffer.fB; 
		
	}
	
	public FloatBuffer Float(Vector2f v)
	{
		TF3D_Buffer.fB.clear();
		TF3D_Buffer.fB.put(v.x);
		TF3D_Buffer.fB.put(v.y);
		TF3D_Buffer.fB.flip();
		
		return TF3D_Buffer.fB;
		
	}
	
	public FloatBuffer Float(Vector3f v)
	{
		TF3D_Buffer.fB.clear();
		TF3D_Buffer.fB.put(v.x);
		TF3D_Buffer.fB.put(v.y);
		TF3D_Buffer.fB.put(v.z);
		TF3D_Buffer.fB.flip();
		
		return TF3D_Buffer.fB;
		
	}
	
	public FloatBuffer Float(Vector4f v)
	{
		TF3D_Buffer.fB.clear();
		TF3D_Buffer.fB.put(v.x);
		TF3D_Buffer.fB.put(v.y);
		TF3D_Buffer.fB.put(v.z);
		TF3D_Buffer.fB.put(v.w);
		TF3D_Buffer.fB.flip();
		
		return TF3D_Buffer.fB;
		
	}
	
	public IntBuffer Int(int[] i)
	{
		TF3D_Buffer.iB.clear();
		TF3D_Buffer.iB.put(i).flip();
		
		return TF3D_Buffer.iB ;
	}
	
	public ByteBuffer Byte(byte[] b)
	{
		TF3D_Buffer.bB = BufferUtils.createByteBuffer(b.length);
		TF3D_Buffer.bB.clear();
		TF3D_Buffer.bB.put(b);
		TF3D_Buffer.bB.flip();
		
		return TF3D_Buffer.bB;
	}

}
