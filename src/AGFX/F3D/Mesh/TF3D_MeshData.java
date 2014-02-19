package AGFX.F3D.Mesh;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.GL_TEXTURE2;
import static org.lwjgl.opengl.GL13.GL_TEXTURE3;

import java.io.Serializable;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import AGFX.F3D.F3D;

public class TF3D_MeshData implements Serializable
{

	static final long			serialVersionUID	= 5590845905712907241L;

	public int					material_id;

	public float				vertices[];
	public float				normals[];
	public float				tangents[];
	public float				binormals[];
	public float				colors[];
	public float				uv0[];
	public float				uv1[];
	public float				uv2[];
	public float				uv3[];
	public short				indices[];
	public String				faces_material[];
	public int					facecount;
	public int					v_id				= 0;
	public int					f_id				= 0;
	public int					n_id				= 0;
	public int					tn_id				= 0;
	public int					bn_id				= 0;
	public int					c_id				= 0;
	public int					u0_id				= 0;
	public int					u1_id				= 0;
	public int					u2_id				= 0;
	public int					u3_id				= 0;

	

	public TF3D_MeshData()
	{
		this.material_id = -1;
		
	}

	// -----------------------------------------------------------------------
	// TF3D_MeshData:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Allocate buffer for all elements like vertex/normal/color/uv/indices <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param fc
	 */
	// -----------------------------------------------------------------------
	public void SetFacesCount(int fc)
	{
		this.facecount = fc;

		this.vertices = new float[this.facecount * 3 * 3];
		this.colors = new float[this.facecount * 3 * 4];
		this.normals = new float[this.facecount * 3 * 3];
		this.tangents = new float[this.facecount * 3 * 3];
		this.binormals = new float[this.facecount * 3 * 3];
		this.uv0 = new float[this.facecount * 3 * 2];
		this.uv1 = new float[this.facecount * 3 * 2];
		this.uv2 = new float[this.facecount * 3 * 2];
		this.uv3 = new float[this.facecount * 3 * 2];
		this.indices = new short[this.facecount * 3];
		this.faces_material = new String[this.facecount];

		F3D.Log.info("vertices", String.valueOf(this.vertices.length));
		F3D.Log.info("colors", String.valueOf(this.colors.length));
		F3D.Log.info("normals", String.valueOf(this.normals.length));
		F3D.Log.info("binormals", String.valueOf(this.binormals.length));
		F3D.Log.info("tangents", String.valueOf(this.tangents.length));
		F3D.Log.info("indices", String.valueOf(this.indices.length));
		F3D.Log.info("faces", String.valueOf(this.facecount));

	}

	// -----------------------------------------------------------------------
	// TF3D_MeshData:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Add Vertex to vertex buffer <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param v
	 *            vertex
	 */
	// -----------------------------------------------------------------------
	public void AddVertex(Vector3f v)
	{
		this.vertices[this.v_id + 0] = v.x;
		this.vertices[this.v_id + 1] = v.y;
		this.vertices[this.v_id + 2] = v.z;

		this.v_id += 3;
	}

	// -----------------------------------------------------------------------
	// TF3D_MeshData:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Add Normal to normal buffer <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param v
	 *            normal
	 */
	// -----------------------------------------------------------------------
	public void AddNormal(Vector3f v)
	{
		this.normals[this.n_id + 0] = v.x;
		this.normals[this.n_id + 1] = v.y;
		this.normals[this.n_id + 2] = v.z;

		this.n_id += 3;
	}
	
	public void AddTangent(Vector3f v)
	{
		this.tangents[this.tn_id + 0] = v.x;
		this.tangents[this.tn_id + 1] = v.y;
		this.tangents[this.tn_id + 2] = v.z;

		this.tn_id += 3;
	}

	public void AddBinormal(Vector3f v)
	{
		this.binormals[this.bn_id + 0] = v.x;
		this.binormals[this.bn_id + 1] = v.y;
		this.binormals[this.bn_id + 2] = v.z;

		this.bn_id += 3;
	}
	
	// -----------------------------------------------------------------------
	// TF3D_MeshData:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Add Color to color buffer <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param v
	 *            color
	 */
	// -----------------------------------------------------------------------
	public void AddColor(Vector4f v)
	{
		this.colors[this.c_id + 0] = v.x;
		this.colors[this.c_id + 1] = v.y;
		this.colors[this.c_id + 2] = v.z;
		this.colors[this.c_id + 3] = v.w;

		this.c_id += 4;

	}

	// -----------------------------------------------------------------------
	// TF3D_MeshData:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Add UV to UV buffer for defined texture memory unit <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param v
	 *            vector2f
	 * @param tmu
	 *            texture memory unit
	 */
	// -----------------------------------------------------------------------
	public void AddUV(Vector2f v, int tmu)
	{
		if (tmu == 0)
		{
			this.uv0[this.u0_id + 0] = v.x;
			this.uv0[this.u0_id + 1] = v.y;
			this.u0_id += 2;
		}

		if (tmu == 1)
		{
			this.uv1[this.u1_id + 0] = v.x;
			this.uv1[this.u1_id + 1] = v.y;
			this.u1_id += 2;
		}

		if (tmu == 2)
		{
			this.uv2[this.u2_id + 0] = v.x;
			this.uv2[this.u2_id + 1] = v.y;
			this.u2_id += 2;
		}

		if (tmu == 3)
		{
			this.uv3[this.u3_id + 0] = v.x;
			this.uv3[this.u3_id + 1] = v.y;
			this.u3_id += 2;
		}

	}

	// -----------------------------------------------------------------------
	// TF3D_MeshData:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Add face to list - indices are created automaticaly for last 3 insertec
	 * value vertex/uv/color/normal <BR>
	 * -------------------------------------------------------------------<BR>
	 */
	// -----------------------------------------------------------------------
	public void AddFace(String mat_name)
	{
		short iA = (short) (f_id + 0);
		short iB = (short) (f_id + 1);
		short iC = (short) (f_id + 2);

		this.indices[f_id + 0] = iA;
		this.indices[f_id + 1] = iB;
		this.indices[f_id + 2] = iC;
		this.faces_material[this.f_id / 3] = mat_name;

		this.f_id += 3;

	}

	// -----------------------------------------------------------------------
	// TF3D_MeshData:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * get vertex[i] as array <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param id
	 *            vertex index
	 * @return float[3]
	 */
	// -----------------------------------------------------------------------
	public float[] GetVertexAsArray(int id)
	{
		float res[] = new float[3];

		res[0] = this.vertices[id * 3 + 0];
		res[1] = this.vertices[id * 3 + 1];
		res[2] = this.vertices[id * 3 + 2];
		return res;
	}

	// -----------------------------------------------------------------------
	// TF3D_MeshData:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * get vertex[i] as vector <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param id
	 *            vertex index
	 * @return vector
	 */
	// -----------------------------------------------------------------------
	public Vector3f GetVertexAsVector(int id)
	{
		Vector3f res = new Vector3f();

		res.x = this.vertices[id * 3 + 0];
		res.y = this.vertices[id * 3 + 1];
		res.z = this.vertices[id * 3 + 2];

		return res;
	}

	// -----------------------------------------------------------------------
	// TF3D_MeshData:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * get normal[i] as array <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param id
	 *            normal index
	 * @return float[3]
	 */
	// -----------------------------------------------------------------------
	public float[] GetNormalAsArray(int id)
	{
		float res[] = new float[3];

		res[0] = this.normals[id * 3 + 0];
		res[1] = this.normals[id * 3 + 1];
		res[2] = this.normals[id * 3 + 2];
		return res;
	}

	// -----------------------------------------------------------------------
	// TF3D_MeshData:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * get normal[i] as vector <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param id
	 *            normal index
	 * @return vector
	 */
	// -----------------------------------------------------------------------
	public Vector3f GetNormalAsVector(int id)
	{
		Vector3f res = new Vector3f();

		res.x = this.normals[id * 3 + 0];
		res.y = this.normals[id * 3 + 1];
		res.z = this.normals[id * 3 + 2];

		return res;
	}

	// -----------------------------------------------------------------------
	// TF3D_MeshData:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * get color[i] as array <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param id
	 *            color index
	 * @return float[3]
	 */
	// -----------------------------------------------------------------------
	public float[] GetColorAsArray(int id)
	{
		float res[] = new float[4];

		res[0] = this.colors[id * 4 + 0];
		res[1] = this.colors[id * 4 + 1];
		res[2] = this.colors[id * 4 + 2];
		res[3] = this.colors[id * 4 + 3];

		return res;
	}

	// -----------------------------------------------------------------------
	// TF3D_MeshData:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * get color[i] as vector <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param id
	 *            color index
	 * @return vector
	 */
	// -----------------------------------------------------------------------
	public Vector4f GetColorAsVector(int id)
	{
		Vector4f res = new Vector4f();

		res.x = this.colors[id * 4 + 0];
		res.y = this.colors[id * 4 + 1];
		res.z = this.colors[id * 4 + 2];
		res.w = this.colors[id * 4 + 3];

		return res;
	}

	// -----------------------------------------------------------------------
	// TF3D_MeshData:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Get UV[id] <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param id
	 *            uv index
	 * @param tmu
	 *            texture memory unit
	 * @return float[2]
	 */
	// -----------------------------------------------------------------------
	public float[] GetUVAsArray(int id, int tmu)
	{
		float res[] = new float[2];

		if (tmu == GL_TEXTURE0)
		{
			res[0] = this.uv0[id * 2 + 0];
			res[1] = this.uv0[id * 2 + 1];
		}

		if (tmu == GL_TEXTURE1)
		{
			res[0] = this.uv1[id * 2 + 0];
			res[1] = this.uv1[id * 2 + 1];
		}

		if (tmu == GL_TEXTURE2)
		{
			res[0] = this.uv2[id * 2 + 0];
			res[1] = this.uv2[id * 2 + 1];
		}

		if (tmu == GL_TEXTURE3)
		{
			res[0] = this.uv3[id * 2 + 0];
			res[1] = this.uv3[id * 2 + 1];
		}

		return res;
	}

	// -----------------------------------------------------------------------
	// TF3D_MeshData:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Get UV[id] <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param id
	 *            uv index
	 * @param tmu
	 *            texture memory unit
	 * @return vector2f
	 */
	// -----------------------------------------------------------------------
	public Vector2f GetUVAsVector(int id, int tmu)
	{
		Vector2f res = new Vector2f();

		if (tmu == GL_TEXTURE0)
		{
			res.x = this.uv0[id * 2 + 0];
			res.y = this.uv0[id * 2 + 1];
		}

		if (tmu == GL_TEXTURE1)
		{
			res.x = this.uv1[id * 2 + 0];
			res.y = this.uv1[id * 2 + 1];
		}

		if (tmu == GL_TEXTURE2)
		{
			res.x = this.uv2[id * 2 + 0];
			res.y = this.uv2[id * 2 + 1];
		}

		if (tmu == GL_TEXTURE3)
		{
			res.x = this.uv3[id * 2 + 0];
			res.y = this.uv3[id * 2 + 1];
		}

		return res;
	}

	// -----------------------------------------------------------------------
	// TF3D_MeshData:
	// -----------------------------------------------------------------------
	/**
	 * <BR>
	 * -------------------------------------------------------------------<BR>
	 * Get indices for Face[id] <BR>
	 * -------------------------------------------------------------------<BR>
	 * 
	 * @param id
	 *            face id
	 * @return int array of indices
	 */
	// -----------------------------------------------------------------------
	public short[] GetFaceIndices(int id)
	{
		short[] res = new short[3];
		res[0] = (short) (id * 3 + 0);
		res[1] = (short) (id * 3 + 1);
		res[2] = (short) (id * 3 + 2);

		return res;
	}
	
	
}
