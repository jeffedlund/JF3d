'---------------------------------------------------------------------------------------------------
' This program was written with BLIde (www.blide.org)
' Application:
' Author:
' License:
'---------------------------------------------------------------------------------------------------
SuperStrict

Include "include/TAGFX_Wavefront_obj.bmx"

Global InName:String
Global MESHdata:TAGFX_Wavefront_obj

If AppArgs.Length = 2 Then
	InName = AppArgs[1]
	'InName = "test_load"
	MESHdata:TAGFX_Wavefront_obj = New TAGFX_Wavefront_obj
	Print "Start Loading ...."
	MESHdata.LoadFromFile("import/" + InName + ".obj")
	MESHdata.SaveToFile("export/models/" + InName + ".a3da")
	MESHdata.MaterialList.SaveToFile("export/models/" + InName + ".mat")
	Print "... done"
	'MESHdata.Dump()
Else
	Print("********************************************************")
	Print(" Wavefront OBJ file convertor to Android3D Engine 2010 *")
	Print("********************************************************")
	Print(" Execute: convert_obj2a3da.exe <obj filename>")
	Print("********************************************************")
End If





