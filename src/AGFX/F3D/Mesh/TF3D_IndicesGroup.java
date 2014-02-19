package AGFX.F3D.Mesh;

import java.util.ArrayList;


public class TF3D_IndicesGroup
{
	public ArrayList<TF3D_Indices> items;
    
	public TF3D_IndicesGroup()
	{
		this.items = new ArrayList<TF3D_Indices>();
	}

	// -----------------------------------------------------------------------
	// TF3D_IndicesGroup: 
	// -----------------------------------------------------------------------
	/**
	 * <BR>-------------------------------------------------------------------<BR> 
	 *  Add Group name to list 
	 * <BR>-------------------------------------------------------------------<BR> 
	 * @param mat_name - group name = material name
	 */
	// -----------------------------------------------------------------------
	private void AddGroup(String mat_name)
	{
		TF3D_Indices idx = new TF3D_Indices(mat_name);
		this.items.add(idx);
	}

	// -----------------------------------------------------------------------
	// TF3D_IndicesGroup: 
	// -----------------------------------------------------------------------
	/**
	 * <BR>-------------------------------------------------------------------<BR> 
	 *  Find ID for Group name
	 * <BR>-------------------------------------------------------------------<BR> 
	 * @param gname - group name = material name
	 * @return
	 */
	// -----------------------------------------------------------------------
	private int FindGroupByName(String gname)
	{
		for (int i = 0; i < this.items.size(); i++)
		{
			if (this.items.get(i).material_name.equalsIgnoreCase(gname))
			{
				return i;
			}
		}

		return -1;
	}

	// -----------------------------------------------------------------------
	// TF3D_IndicesGroup:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Add face index with assigned material name <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param gname
	 *            - group name = material name
	 * @param iA
	 *            - index to vertex id A
	 * @param iB
	 *            - index to vertex id B
	 * @param iC
	 *            - index to vertex id C
	 */
	// -----------------------------------------------------------------------
	public void AddIndexToGroup(String gname, Short iA, Short iB, Short iC)
	{
		int g_id = this.FindGroupByName(gname);

		if (g_id == -1)
		{
			// when missing - create new indices group
			this.AddGroup(gname);
			g_id = this.FindGroupByName(gname);
			this.items.get(g_id).indices_list.add(iA);
			this.items.get(g_id).indices_list.add(iB);
			this.items.get(g_id).indices_list.add(iC);
		} else
		{
			// add new indices to existing group
			this.items.get(g_id).indices_list.add(iA);
			this.items.get(g_id).indices_list.add(iB);
			this.items.get(g_id).indices_list.add(iC);
		}
	}

	public void AddIndexToGroup(String names[])
	{
		for(int i=0;i<names.length;i++)
		{
			this.AddIndexToGroup(names[i], i);
		}
		
	}
	// -----------------------------------------------------------------------
	// TF3D_IndicesGroup:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Add face index with assigned material name <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param gname
	 *            - groum name = material name
	 * @param idx
	 *            - face id
	 */
	// -----------------------------------------------------------------------
	public void AddIndexToGroup(String gname, int idx)
	{
		int g_id = this.FindGroupByName(gname);

		
		
		if (g_id == -1)
		{
			// when missing - create new indices group
			this.AddGroup(gname);
			g_id = this.FindGroupByName(gname);
			this.items.get(g_id).indices_list.add((short) (idx*3 + 0));
			this.items.get(g_id).indices_list.add((short) (idx*3 + 1));
			this.items.get(g_id).indices_list.add((short) (idx*3 + 2));
						
		} else
		{
			// add new indices to existing group
			this.items.get(g_id).indices_list.add((short) (idx*3 + 0));
			this.items.get(g_id).indices_list.add((short) (idx*3 + 1));
			this.items.get(g_id).indices_list.add((short) (idx*3 + 2));
						
		}
	}

	// -----------------------------------------------------------------------
	// TF3D_IndicesGroup:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Generate indices group by materials <BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	public void Generate()
	{
		for(int i=0;i<this.items.size();i++)
		{
			this.items.get(i).CreateIndicesBuffer();
		}

	}
}
