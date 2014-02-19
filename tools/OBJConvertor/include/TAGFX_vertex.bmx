Const MAX_VERTEX_COUNT:Int = 200000


Type TAGFX_VertexList

	Field Vertex:TAGFX_Vector3[MAX_VERTEX_COUNT]
	Field VertexCount:Int

	Method New()
		Self.VertexCount = 0
	End Method
	
	Method Add(_x:Float, _y:Float, _z:Float)
		
		Self.Vertex[Self.VertexCount] = New TAGFX_Vector3
		Self.Vertex[Self.VertexCount].x = _x
		Self.Vertex[Self.VertexCount].y = _y
		Self.Vertex[Self.VertexCount].z = _z
		Self.VertexCount:+1
		
	End Method
	
	Method Get:TAGFX_Vector3(_id:Int)
		Return Self.Vertex[_id]
	End Method
	
End Type
