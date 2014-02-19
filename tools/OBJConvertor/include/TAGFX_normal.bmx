Const MAX_NORMAL_COUNT:Int = 200000


Type TAGFX_NormalList

	Field Normal:TAGFX_Vector3[MAX_NORMAL_COUNT]
	Field NormalCount:Int

	Method New()
		Self.NormalCount = 0
	End Method
	
	Method Add(_x:Float, _y:Float, _z:Float)
		
		Self.Normal[Self.NormalCount] = New TAGFX_Vector3
		Self.Normal[Self.NormalCount].x = _x
		Self.Normal[Self.NormalCount].y = _y
		Self.Normal[Self.NormalCount].z = _z
		Self.NormalCount:+1
		
	End Method
	
	Method Get:TAGFX_Vector3(_id:Int)
		Return Self.Normal[_id]
	End Method
	
End Type
