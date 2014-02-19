package AGFX.F3D.Mesh;

import java.nio.ShortBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

import AGFX.F3D.F3D;

public class TF3D_Indices
{
	public String material_name;	
	public ArrayList<Short> indices_list;
	
	public int material_id=-1;
	private short indices[];
	
	public ShortBuffer	indexBuffer;
	
	// -----------------------------------------------------------------------
	// TF3D_Indices: 
	// -----------------------------------------------------------------------
	/**
	 * <BR>-------------------------------------------------------------------<BR> 
	 *  Create indices for material
	 * <BR>-------------------------------------------------------------------<BR> 
	 * @param mat_name
	 */
	// -----------------------------------------------------------------------
	public TF3D_Indices(String mat_name)
	{
		this.material_id = F3D.Surfaces.FindByName(mat_name);
		this.material_name = mat_name;
		this.indices_list = new ArrayList<Short>();
	}
	
	
	// -----------------------------------------------------------------------
	// TF3D_Indices: 
	// -----------------------------------------------------------------------
	/**
	 * <BR>-------------------------------------------------------------------<BR> 
	 *  Create indices buffer for Vertex Buffer Object rendering method
	 * <BR>-------------------------------------------------------------------<BR> 
	 */
	// -----------------------------------------------------------------------
	public void CreateIndicesBuffer()
	{
		this.indices = new short[this.indices_list.size()];
	
		for(int i=0;i<this.indices_list.size();i++)
		{
			this.indices[i]=this.indices_list.get(i);
		}
		
		
		this.indexBuffer = BufferUtils.createShortBuffer(this.indices.length);
		this.indexBuffer.clear();
		this.indexBuffer.put(this.indices);
		this.indexBuffer.position(0);
	}

}
