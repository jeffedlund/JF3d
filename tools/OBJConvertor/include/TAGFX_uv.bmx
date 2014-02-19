Const MAX_UV_COUNT:Int = 200000


Type TAGFX_UVList

	Field UV:TAGFX_Vector2[MAX_UV_COUNT]
	Field UVCount:Int

	Method New()
		Self.UVCount = 0
	End Method
	
	Method Add(_x:Float, _y:Float)
		
		Self.UV[Self.UVCount] = New TAGFX_Vector2
		Self.UV[Self.UVCount].x = _x
		Self.UV[Self.UVCount].y = _y
		Self.UVCount:+1
		
	End Method
	
	Method Get:TAGFX_Vector2(_id:Int)
		Return Self.UV[_id]
	End Method
	
End Type
