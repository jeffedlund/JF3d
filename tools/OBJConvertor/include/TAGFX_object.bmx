Const MAX_FACE_COUNT:Int = 120000

Type TAGFX_object

	Field Name:String
	Field VertexList:TAGFX_VertexList
	Field NormalList:TAGFX_NormalList
	Field UVList:TAGFX_UVList
	Field FaceList:TAGFX_Face[MAX_FACE_COUNT]
	Field facecount:Int
	
	Method New()
		Self.facecount = 0
		Self.VertexList = New TAGFX_VertexList
		Self.NormalList = New TAGFX_NormalList
		self.UVList = new TAGFX_UVList
	End Method
	
	Method AddFace(avi:Int, bvi:Int, cvi:Int, nai:Int, nbi:Int, nci:Int, auvi:Int, buvi:Int, cuvi:Int, mtl_name:String)
	
		Self.FaceList[Self.facecount] = New TAGFX_Face
		
		' vertices idx
		Self.FaceList[Self.facecount].vertexID[0] = avi
		Self.FaceList[Self.facecount].vertexID[1] = bvi
		Self.FaceList[Self.facecount].vertexID[2] = cvi
		
		'normals idx	
		Self.FaceList[Self.facecount].normalID[0] = nai
		Self.FaceList[Self.facecount].normalID[1] = nbi
		Self.FaceList[Self.facecount].normalID[2] = nci
		
		' uv idx
		Self.FaceList[Self.facecount].uvID[0] = auvi
		Self.FaceList[Self.facecount].uvID[1] = buvi
		Self.FaceList[Self.facecount].uvID[2] = cuvi
		
		Self.FaceList[Self.facecount].material_name = mtl_name
		
		Self.facecount:+1
		
	End Method
	
End Type
