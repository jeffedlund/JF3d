Type TAGFX_Face

	Field vertexID:Int[3]
	Field normalID:Int[3]
	Field uvID:Int[3]
	Field material_name:String
		
	Method New()
		For Local i:Int = 0 To 2
			Self.vertexID[i] = - 1
			Self.normalID[i] = - 1
			Self.uvID[i] = - 1			
		Next
	End Method
End Type
	