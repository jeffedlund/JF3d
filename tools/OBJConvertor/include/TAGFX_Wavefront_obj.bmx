
Include "TAGFX_vector3.bmx"
Include "TAGFX_vector2.bmx"
Include "TAGFX_vertex.bmx"
Include "TAGFX_normal.bmx"
Include "TAGFX_uv.bmx"
Include "TAGFX_face.bmx"
Include "TAGFX_object.bmx"
Include "TAGFX_Material.bmx"

Const MAX_OBJECT_COUNT:Int = 1000

Type TAGFX_Wavefront_obj

	Field singlemesh:Int
	Field loaded:Int = False
	Field MaterialFileName:String
	Field UseMtl:String = "Default"
	Field ObjectCount:Int = 0
	Field ObjectList:TAGFX_object[MAX_OBJECT_COUNT]
	Field MaterialList:TAGFX_MaterialList
	
	Method New()		
		Self.ObjectList[0] = New TAGFX_object
		Self.ObjectList[0].Name = "singlemesh"		
		Self.MaterialList = New TAGFX_MaterialList
		Self.ObjectCount = 1
		Self.singlemesh = True
	End Method
	
	Method LoadFromFile(_filename:String)
		Local file:TStream = OpenStream(_filename, True, False)
		Local objid:Int
		
		If file
			Self.loaded = True
			objid = Self.ObjectCount - 1
			
			While Not Eof(file)

				Local Line:String = ReadLine(file)
				Local pline:String[] = Line.Split(" ")
				
				
				' get/set OBJECT
				If pline[0] = "o" Then
					
					Self.singlemesh = False
					objid = Self.ObjectCount - 1
					Self.ObjectList[objid] = New TAGFX_object
					Self.ObjectList[objid].Name = pline[1]
					Self.ObjectCount:+1
					
				End If
				
				' set material filename
				If pline[0] = "mtllib" Then

					Self.MaterialFileName = "import/" + pline[1]
				End If
		
				' read vertex list
				If pline[0] = "v" Then
					Local x:Float = pline[1].ToFloat()
					Local y:Float = pline[2].ToFloat()
					Local z:Float = pline[3].ToFloat()
					Self.ObjectList[objid].VertexList.Add(x, y, z)

				End If

				' read normal list
				If pline[0] = "vn" Then
					Local x:Float = pline[1].ToFloat()
					Local y:Float = pline[2].ToFloat()
					Local z:Float = pline[3].ToFloat()
					Self.ObjectList[objid].NormalList.Add(x, y, z)

				End If

				' read UV list
				If pline[0] = "vt" Then
					Local x:Float = pline[1].ToFloat()
					Local y:Float = -pline[2].ToFloat()					
					Self.ObjectList[objid].UVList.Add(x, y)

				End If
				
				' set used material name for next face list
				If pline[0] = "usemtl" Then
					Self.UseMtl = pline[1]

				End If
				
				' read face idx
				If pline[0] = "f" Then
					
					Local vA:String[] = pline[1].Split("/")
					Local vB:String[] = pline[2].Split("/")
					Local vC:String[] = pline[3].Split("/")
					
					If vA.Length <= 2 Or vB.Length <= 2 Or vC.Length <= 2 Then
						Print "WARNNING: missing normal or UV !!!!"
					End If

					Local avi:Int = -1
					Local bvi:Int = -1
					Local cvi:Int = -1
					Local auvi:Int = -1
					Local buvi:Int = -1
					Local cuvi:Int = -1
					Local nai:Int = -1
					Local nbi:Int = -1
					Local nci:Int = -1
					
					If vA.Length = 1 Then
						avi = vA[0].ToInt()
						bvi = vB[0].ToInt()
						cvi = vC[0].ToInt()
					End If
					
					If vA.Length = 2 Then
						avi = vA[0].ToInt()
						bvi = vB[0].ToInt()
						cvi = vC[0].ToInt()
						
						
						auvi = vA[1].ToInt()
						buvi = vB[1].ToInt()
						cuvi = vC[1].ToInt()
					End If
					
					If vA.Length = 3 Then
						avi = vA[0].ToInt()
						bvi = vB[0].ToInt()
						cvi = vC[0].ToInt()
						
						
						auvi = vA[1].ToInt()
						buvi = vB[1].ToInt()
						cuvi = vC[1].ToInt()
						
						nai = vA[2].ToInt()
						nbi = vB[2].ToInt()
						nci = vC[2].ToInt()
					End If
					
					Self.ObjectList[objid].AddFace(avi, bvi, cvi, nai, nbi, nci, auvi, buvi, cuvi, Self.UseMtl)
					
				End If
			Wend
			CloseStream(file)
		End If
	
		 If Self.singlemesh = False Then
		 	Self.ObjectCount:-1
		 End If
		' load material
		
		Self.MaterialList.LoadFromFile(Self.MaterialFileName)
	End Method
	
	
	Method Dump()
		If Self.loaded Then
			For Local o:Int = 0 To Self.ObjectCount - 1

				Print "Object name: " + Self.ObjectList[o].Name
				
				For Local i:Int = 0 To Self.ObjectList[o].VertexList.VertexCount - 1
					Print "   vertex[" + i + "]: " + Self.ObjectList[o].VertexList.Vertex[i].x + "," + Self.ObjectList[o].VertexList.Vertex[i].y + "," + Self.ObjectList[o].VertexList.Vertex[i].z
				Next
				
				For Local i:Int = 0 To Self.ObjectList[o].NormalList.NormalCount - 1
					Print "   normal: " + Self.ObjectList[o].NormalList.Normal[i].x + "," + Self.ObjectList[o].NormalList.Normal[i].y + "," + Self.ObjectList[o].NormalList.Normal[i].z
				Next
				
				For Local i:Int = 0 To Self.ObjectList[o].UVList.UVCount - 1
					Print "   uv    : " + Self.ObjectList[o].UVList.UV[i].x + "," + Self.ObjectList[o].UVList.UV[i].y
				Next
				
				For Local i:Int = 0 To Self.ObjectList[o].facecount - 1
					    Print "   face    : " + i + " MAT: " + Self.ObjectList[o].FaceList[i].material_name
					For Local f:Int = 0 To 2
						Print "       V[" + f + "] : " + Self.ObjectList[o].FaceList[i].vertexID[f]
						Print "       N[" + f + "] : " + Self.ObjectList[o].FaceList[i].normalID[f]
						Print "      UV[" + f + "] : " + Self.ObjectList[o].FaceList[i].uvID[f]
						
					Next
				Next
			Next
		End If
	End Method
	
	
	Method SaveToFile(_filename:String)
		Local o_id:Int = 0
		Local f_id:Int = 0
		Local _out:TStream = WriteStream(_filename)
		For o_id = 0 To Self.ObjectCount - 1
			
			For f_id = 0 To Self.ObjectList[o_id].facecount - 1
			
				Local vA:TAGFX_Vector3
				Local vB:TAGFX_Vector3
				Local vC:TAGFX_Vector3
				
				Local uA:TAGFX_Vector2
				Local uB:TAGFX_Vector2
				Local uC:TAGFX_Vector2
				
				Local Ai:Int
				Local Bi:Int
				Local Ci:Int
				
				' VERTEX OUT				
				vA = New TAGFX_Vector3
				vB = New TAGFX_Vector3
				vC = New TAGFX_Vector3
				
				Ai = Self.ObjectList[o_id].FaceList[f_id].vertexID[0] - 1
				Bi = Self.ObjectList[o_id].FaceList[f_id].vertexID[1] - 1
				Ci = Self.ObjectList[o_id].FaceList[f_id].vertexID[2] - 1
								
				vA = Self.ObjectList[o_id].VertexList.Get(Ai)
				vB = Self.ObjectList[o_id].VertexList.Get(Bi)
				vC = Self.ObjectList[o_id].VertexList.Get(Ci)
				WriteLine _out, "{"
				WriteLine _out, "  vA = " + vA.X + "," + vA.Y + "," + vA.Z
				WriteLine _out, "  vB = " + vB.X + "," + vB.Y + "," + vB.Z
				WriteLine _out, "  vC = " + vC.X + "," + vC.Y + "," + vC.Z
				
				' NORMAL OUT
				
				Ai = Self.ObjectList[o_id].FaceList[f_id].normalID[0] - 1
				Bi = Self.ObjectList[o_id].FaceList[f_id].normalID[1] - 1
				Ci = Self.ObjectList[o_id].FaceList[f_id].normalID[2] - 1
								
				vA = Self.ObjectList[o_id].NormalList.Get(Ai)
				vB = Self.ObjectList[o_id].NormalList.Get(Bi)
				vC = Self.ObjectList[o_id].NormalList.Get(Ci)
				
				WriteLine _out, "  nA = " + vA.X + "," + vA.Y + "," + vA.Z
				WriteLine _out, "  nB = " + vB.X + "," + vB.Y + "," + vB.Z
				WriteLine _out, "  nC = " + vC.X + "," + vC.Y + "," + vC.Z
				
				' DUMMY COLOR OUT
				
				WriteLine _out, "  cA = 1.0 , 1.0 , 1.0 , 1.0 "
				WriteLine _out, "  cB = 1.0 , 1.0 , 1.0 , 1.0 "
				WriteLine _out, "  cC = 1.0 , 1.0 , 1.0 , 1.0 "
				
				' UV OUT
				
				uA = New TAGFX_Vector2
				uB = New TAGFX_Vector2
				uC = New TAGFX_Vector2
				
				Ai = Self.ObjectList[o_id].FaceList[f_id].UVID[0] - 1
				Bi = Self.ObjectList[o_id].FaceList[f_id].UVID[1] - 1
				Ci = Self.ObjectList[o_id].FaceList[f_id].UVID[2] - 1
								
				uA = Self.ObjectList[o_id].UVList.Get(Ai)
				uB = Self.ObjectList[o_id].UVList.Get(Bi)
				uC = Self.ObjectList[o_id].UVList.Get(Ci)
				
				
				WriteLine _out, " uv0A = " + uA.X + "," + uA.Y
				WriteLine _out, " uv0B = " + uB.X + "," + uB.Y
				WriteLine _out, " uv0C = " + uC.X + "," + uC.Y
				
				WriteLine _out, " mat = " + Self.ObjectList[o_id].FaceList[f_id].material_name
				WriteLine _out, "}"
			Next
			
		Next
		
		CloseStream _out
	End Method

	
	Method SaveToJava(path:String, _filename:String)
		Local id:Int = 0
		Local o_id:Int = 0
		Local f_id:Int = 0
		Local _out:TStream = WriteStream(path + "TF3D_" + _fileName + ".java")
		
		
		WriteLine _out, "package agfx.F3D.MESH;"
		WriteLine _out, ""
		WriteLine _out, ""
		WriteLine _out, ""
		WriteLine _out, "import AGFX.F3D.F3D;"
		WriteLine _out, "import AGFX.F3D.Entity.TF3D_Entity;"
		WriteLine _out, "import static org.lwjgl.opengl.GL11.*;"
		WriteLine _out, "import static org.lwjgl.opengl.GL13.*;"
		
		
		
		WriteLine _out, ""
		WriteLine _out, "public class TA3D_" + _filename + " extends TA3D_Entity"
		WriteLine _out, "{"
		WriteLine _out, "    private float vertices[] = {"
		
		For o_id = 0 To Self.ObjectCount - 1
			For f_id = 0 To Self.ObjectList[o_id].facecount - 1
			
				Local vA:TAGFX_Vector3
				Local vB:TAGFX_Vector3
				Local vC:TAGFX_Vector3

				
				Local Ai:Int
				Local Bi:Int
				Local Ci:Int
				
				' VERTEX OUT				
				vA = New TAGFX_Vector3
				vB = New TAGFX_Vector3
				vC = New TAGFX_Vector3
				
				Ai = Self.ObjectList[o_id].FaceList[f_id].vertexID[0] - 1
				Bi = Self.ObjectList[o_id].FaceList[f_id].vertexID[1] - 1
				Ci = Self.ObjectList[o_id].FaceList[f_id].vertexID[2] - 1
								
				vA = Self.ObjectList[o_id].VertexList.Get(Ai)
				vB = Self.ObjectList[o_id].VertexList.Get(Bi)
				vC = Self.ObjectList[o_id].VertexList.Get(Ci)
				
				WriteLine _out, "                               " + vA.x + "f, " + vA.y + "f, " + vA.z + "f,"
				WriteLine _out, "                               " + vB.x + "f, " + vB.y + "f, " + vB.z + "f,"
				WriteLine _out, "                               " + vC.x + "f, " + vC.y + "f, " + vC.z + "f,"
				
				
				
			Next
		Next
		WriteLine _out, "                               };"
		
		WriteLine _out, "    private float normals[] = {"
		
		For o_id = 0 To Self.ObjectCount - 1
			For f_id = 0 To Self.ObjectList[o_id].facecount - 1
			
				Local vA:TAGFX_Vector3
				Local vB:TAGFX_Vector3
				Local vC:TAGFX_Vector3
				
				Local Ai:Int
				Local Bi:Int
				Local Ci:Int
				
				' NORMALS OUT				
				vA = New TAGFX_Vector3
				vB = New TAGFX_Vector3
				vC = New TAGFX_Vector3
				
				Ai = Self.ObjectList[o_id].FaceList[f_id].normalID[0] - 1
				Bi = Self.ObjectList[o_id].FaceList[f_id].normalID[1] - 1
				Ci = Self.ObjectList[o_id].FaceList[f_id].normalID[2] - 1
								
				vA = Self.ObjectList[o_id].NormalList.Get(Ai)
				vB = Self.ObjectList[o_id].NormalList.Get(Bi)
				vC = Self.ObjectList[o_id].NormalList.Get(Ci)
				
				WriteLine _out, "                               " + vA.x + "f, " + vA.y + "f, " + vA.z + "f,"
				WriteLine _out, "                               " + vB.x + "f, " + vB.y + "f, " + vB.z + "f,"
				WriteLine _out, "                               " + vC.x + "f, " + vC.y + "f, " + vC.z + "f,"
				
				
				
			Next
		Next
		WriteLine _out, "                               };"
		
		
		' TEXTURE UV OUT
		WriteLine _out, "    private float texture[] = {"
		
		For o_id = 0 To Self.ObjectCount - 1
			For f_id = 0 To Self.ObjectList[o_id].facecount - 1

				
				Local uA:TAGFX_Vector2
				Local uB:TAGFX_Vector2
				Local uC:TAGFX_Vector2
				
				Local Ai:Int
				Local Bi:Int
				Local Ci:Int
				
				' UV OUT
				
				uA = New TAGFX_Vector2
				uB = New TAGFX_Vector2
				uC = New TAGFX_Vector2
				
				Ai = Self.ObjectList[o_id].FaceList[f_id].UVID[0] - 1
				Bi = Self.ObjectList[o_id].FaceList[f_id].UVID[1] - 1
				Ci = Self.ObjectList[o_id].FaceList[f_id].UVID[2] - 1
								
				uA = Self.ObjectList[o_id].UVList.Get(Ai)
				uB = Self.ObjectList[o_id].UVList.Get(Bi)
				uC = Self.ObjectList[o_id].UVList.Get(Ci)
				
				WriteLine _out, "                               " + uA.x + "f, " + uA.y + "f,"
				WriteLine _out, "                               " + uB.x + "f, " + uB.y + "f,"
				WriteLine _out, "                               " + uC.x + "f, " + uC.y + "f,"
				
				
			
			Next
		Next
		WriteLine _out, "                               };"
		' INDICES OUT
		WriteLine _out, "    private int indices[] = {"
		
		
		
		For o_id = 0 To Self.ObjectCount - 1
			For f_id = 0 To Self.ObjectList[o_id].facecount - 1
				
				Local Ai:Int
				Local Bi:Int
				Local Ci:Int
				
				Ai = f_id * 3 + 0
				Bi = f_id * 3 + 1
				Ci = f_id * 3 + 2
				
				WriteLine _out, "                               " + Ai + ", " + Bi + "," + Ci + ","
				
				
			
			Next
		Next
		WriteLine _out, "                               };"
		
		WriteLine _out, ""
		WriteLine _out, "public int material_id;"
		WriteLine _out, "public TF3D_VBO vbo;"
		WriteLine _out, ""
WriteLine _out, " public TF3D_" + _filename + "()"
WriteLine _out, " {"
WriteLine _out, "  this.classname = " + Chr(34) + "CLASS_MESH" + Chr(34) + ";"
WriteLine _out, "  this.vbo = new TF3D_VBO();"
WriteLine _out, "  this.vbo.CreateVertexBuffer(this.vertices);"
WriteLine _out, "  this.vbo.CreateNormalBuffer(this.normals);"
WriteLine _out, "  this.vbo.CreateTextureBuffer(this.texture,GL_TEXTURE0);"
WriteLine _out, "  this.vbo.CreateTextureBuffer(this.texture,GL_TEXTURE1);"
WriteLine _out, "  this.vbo.CreateIndicesBuffer(this.indices);"
WriteLine _out, "  this.vbo.Build();"
WriteLine _out, " }"
WriteLine _out, ""
WriteLine _out, " public void draw()"
WriteLine _out, " {"
WriteLine _out, ""
WriteLine _out, "  if (this.material_id != -1)"
WriteLine _out, "  {"
WriteLine _out, "      F3D.Surfaces.ApplyMaterial(this.material_id);"
WriteLine _out, "  }"
WriteLine _out, ""
WriteLine _out, "  glPushMatrix();"
WriteLine _out, ""
WriteLine _out, "  glScalef(this.scale.x, this.scale.y, this.scale.z);"
WriteLine _out, "  glTranslatef(this.position.x, this.position.y, this.position.z);"
WriteLine _out, ""
WriteLine _out, "  glRotatef(this.rotation.x, 1.0f, 0.0f, 0.0f);"
WriteLine _out, "  glRotatef(this.rotation.y, 0.0f, 1.0f, 0.0f);"
WriteLine _out, "  glRotatef(this.rotation.z, 0.0f, 0.0f, 1.0f);"
WriteLine _out, ""
WriteLine _out, "  this.vbo.DrawVertexBuffer();"
WriteLine _out, ""
WriteLine _out, "  glScalef(1, 1, 1);"
WriteLine _out, "  glPopMatrix();"
WriteLine _out, " }"
WriteLine _out, ""
WriteLine _out, "}"
		
		CloseStream _out
	End Method
End Type


' FORMAT STRING
	' -------------------------------------------------------------------------
	Function Format:String(number:Double, decimal:Int = 4, comma:Int = 0, padleft:Int = 0)
		Assert decimal > -1 And comma > -1 And padleft > -1, "Negative numbers not allowed in Format()"
	
		Local str:String = number
		Local dl:Int = str.Find(".")
		If decimal = 0 Then decimal = -1
		str = str[..dl+decimal+1]
		If comma
			While dl>comma
				str = str[..dl-comma] + "," + str[dl-comma..]
				dl :- comma
			Wend
		EndIf
		If padleft
			Local paddedLength:Int = padleft+decimal+1
			If paddedLength < str.Length Then str = "Error"
			str = RSet(str,paddedLength)
		EndIf
		Return str
	End Function