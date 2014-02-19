Const MAX_MATERIAL_COUNT:Int = 10

Type TAGFX_Material

	Field Name:String = "Default"
	Field Kd:TAGFX_Vector3
	Field Ks:TAGFX_Vector3
	Field Ka:TAGFX_Vector3
	Field Kb:TAGFX_Vector3
	
	Field map_Kd:String = "none"
	Field map_Ks:String = "none"
	Field map_Ka:String = "none"
	Field map_Kb:String = "none"
	
	Method New()
		Self.Kd = New TAGFX_Vector3
		Self.Ks = New TAGFX_Vector3
		Self.Ka = New TAGFX_Vector3
		Self.Kb = New TAGFX_Vector3
	End Method

End Type


Type TAGFX_MaterialList

	Field Materials:TAGFX_Material[MAX_MATERIAL_COUNT]
	Field count:Int = 0
	
	Method LoadFromFile(_filename:String)
	Local mname:String
	Local file:TStream = OpenStream(_filename, True, False)
		Local mtlid:Int
		
		If file
			While Not Eof(file)

				Local Line:String = ReadLine(file)
				Local pline:String[] = Line.Split(" ")
				
				If pline[0] = "newmtl" Then				
					mname = pline[1]
					mtlid = Self.count
					Self.Materials[mtlid] = New TAGFX_Material
					Self.Materials[mtlid].Name = mname
					Self.count:+1
				End If
				
				If pline[0] = "map_Kd" Then
					Self.Materials[mtlid].map_Kd = pline[1]
				End If
				
				If pline[0] = "map_Ka" Then
					Self.Materials[mtlid].map_Ka = pline[1]
				End If
				
				If pline[0] = "map_Kb" Then
					Self.Materials[mtlid].map_Kb = pline[1]
				End If
				
				If pline[0] = "map_Ks" Then
					Self.Materials[mtlid].map_Ks = pline[1]
				End If
				
				If pline[0] = "Kd" Then
					Local r:Float = pline[1].ToFloat()
					Local g:Float = pline[2].ToFloat()
					Local b:Float = pline[3].ToFloat()
					
					Self.Materials[mtlid].Kd.x = r
					Self.Materials[mtlid].Kd.y = g
					Self.Materials[mtlid].Kd.z = b
				End If
				
				If pline[0] = "Ka" Then
					Local r:Float = pline[1].ToFloat()
					Local g:Float = pline[2].ToFloat()
					Local b:Float = pline[3].ToFloat()
					
					Self.Materials[mtlid].Ka.x = r
					Self.Materials[mtlid].Ka.y = g
					Self.Materials[mtlid].Ka.z = b
				End If
				
				If pline[0] = "Kb" Then
					Local r:Float = pline[1].ToFloat()
					Local g:Float = pline[2].ToFloat()
					Local b:Float = pline[3].ToFloat()
					
					Self.Materials[mtlid].Kb.x = r
					Self.Materials[mtlid].Kb.y = g
					Self.Materials[mtlid].Kb.z = b
				End If
				
				If pline[0] = "Ks" Then
					Local r:Float = pline[1].ToFloat()
					Local g:Float = pline[2].ToFloat()
					Local b:Float = pline[3].ToFloat()
					
					Self.Materials[mtlid].Ks.x = r
					Self.Materials[mtlid].Ks.y = g
					Self.Materials[mtlid].Ks.z = b
				End If
				
				Wend
		End If
		
	End Method
	
	
	Method SaveToFile(_filename:String)
		Local _out:TStream = WriteStream(_filename)
		Local m_id:Int

		WriteLine _out, "[MATERIALS]"
		For m_id = 0 To Self.count - 1
	
			
			WriteLine _out, "{"
			WriteLine _out, "         type = MAT_TEXTURE"
			WriteLine _out, "         name = " + Self.Materials[m_id].Name
			WriteLine _out, "      diffuse = 1, 1, 1, 1"
			WriteLine _out, "      ambient = 0.1,0.1,0.1,1.0"
			WriteLine _out, "     emissive = 0,0,0,1"
			WriteLine _out, "     specular = 0,0,0,1"
			WriteLine _out, "    shinisess = 0"			
			WriteLine _out, " vertex_color = false"
			WriteLine _out, "    texture_0 = " + StripDir(Self.Materials[m_id].map_Kd)
			WriteLine _out, "    texture_1 = " + StripDir(Self.Materials[m_id].map_Kb)
			WriteLine _out, "    texture_2 = " + StripDir(Self.Materials[m_id].map_Ks)
			WriteLine _out, "    texture_3 = none"
			WriteLine _out, "      event_0 = none"
			WriteLine _out, "      event_1 = none"
			WriteLine _out, "      event_2 = none"
			WriteLine _out, "      event_3 = none"
			WriteLine _out, "    depthtest = true"
			WriteLine _out, "    alphatest = false"
			WriteLine _out, "  faceculling = true"
			WriteLine _out, "}"
		Next
		CloseStream _out
	End Method
	
End Type