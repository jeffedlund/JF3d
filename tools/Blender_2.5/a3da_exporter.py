import bpy
import os
import string

#######################################
#
#   Exporter for jfinal3d 
#   http://code.google.com/p/jfinal3d/
#   Autor: Baskervil
#   29.10.2010
#   Ver: 0.2
#
#######################################

#########################
#
#   Returns an array of vertices and normal 
#
#########################

def vert(i,j):
    vertices = []
    normals = []
    for k in range(len(bpy.data.meshes[i].faces[j].vertices)):
        vertices.append(bpy.data.meshes[i].vertices[bpy.data.meshes[i].faces[j].vertices[k]].co)
        normals.append(bpy.data.meshes[i].vertices[bpy.data.meshes[i].faces[j].vertices[k]].normal)
    return vertices,normals


##########################
#
#   Returns array vertexcolr
#
##########################

def vert_col(i,j):
    vertexcolor = []
    if (bpy.data.meshes[i].vertex_colors):
        vertexcolor.append(bpy.data.meshes[i].vertex_colors[0].data[j].color1)
        vertexcolor.append(bpy.data.meshes[i].vertex_colors[0].data[j].color2)
        vertexcolor.append(bpy.data.meshes[i].vertex_colors[0].data[j].color3)    
        return vertexcolor
    else :
        return vertexcolor

##########################
#
#   Returns array uvmaps
#
##########################


def uvs(i,j):
    uvmap = []
    if (bpy.data.meshes[i].uv_textures):
        uvmap.append(bpy.data.meshes[i].uv_textures[0].data[j].uv1)
        uvmap.append(bpy.data.meshes[i].uv_textures[0].data[j].uv2)
        uvmap.append(bpy.data.meshes[i].uv_textures[0].data[j].uv3)
        return uvmap
    else :
        return uvmap 

##########################
#
#   Writes data to material file
#
##########################


def write_matfile(materialName,txt_col):
    mat_file = open(materialName+".mat","w")
    mat_file.write("""[MATERIALS]
{
         type = MAT_TEXTURE
         name = %(mat)s
      diffuse = 1,1,1,1
      ambient = 0.1,0.1,0.1,1.0
     emissive = 0,0,0,1
     specular = 0,0,0,1
    shinisess = 0
    texture_0 = %(tex)s
    texture_1 = none
    texture_2 = none
    texture_3 = none
      event_0 = none
      event_1 = none
      event_2 = none
      event_3 = none
    depthtest = false
    alphatest = false
  faceculling = true
}
    """%{"mat":materialName,"tex":materialName})
    mat_file.close()

##########################
#
#   Writes data to textur file
#
##########################


def write_texturfile(materialName,textureName):
    tex_file = open(materialName+".texture","w")
    tex_file.write("""{
    name= %(tex_name)s
    file= abstract::%(image_name)s
  mipmap= true
}"""%{"tex_name":materialName,"image_name":textureName})
    tex_file.close()






def main():
    
    vertices = []
    normals = []
    vertexcolor = []
    uvmap = []
     
    #  preprocessing
    
    bpy.ops.object.transform_apply()
    
    if os.name == "posix":
         file_path = str.split(bpy.data.filepath,"/") 
         path = '/'.join(file_path[0:-1])+"/"
    else:
        file_path = str.split(bpy.data.filepath,"\\")
        path = '\\'.join(file_path[0:-1])+"\\"
    
    os.chdir(path)
    try:
        os.mkdir(bpy.data.meshes[0].name)
    except:
        print("Warning: Directory already exist.\n")
    os.chdir(path+bpy.data.meshes[0].name)
    
    # data maining
    
    for i in range(len(bpy.data.meshes)):
        filename = bpy.data.meshes[i].name
        file = open(filename+".a3da","w")
        for j in range(len(bpy.data.meshes[i].faces)):
            file.write("{\n")
            vertices,normals = vert(i,j)
            vertexcolor = vert_col(i,j)
            uvmap = uvs(i,j)
            file.write("vA = %(x)s,%(y)s,%(z)s\n"%{"x":vertices[0][0],"y":vertices[0][1],"z":vertices[0][2]})
            file.write("vB = %(x)s,%(y)s,%(z)s\n"%{"x":vertices[1][0],"y":vertices[1][1],"z":vertices[1][2]})
            file.write("vC = %(x)s,%(y)s,%(z)s\n"%{"x":vertices[2][0],"y":vertices[2][1],"z":vertices[2][2]})
            file.write("nA = %(x)f,%(y)f,%(z)f\n"%{"x":normals[0][0],"y":normals[0][1],"z":normals[0][2]})
            file.write("nB = %(x)f,%(y)f,%(z)f\n"%{"x":normals[1][0],"y":normals[1][1],"z":normals[1][2]})
            file.write("nC = %(x)f,%(y)f,%(z)f\n"%{"x":normals[2][0],"y":normals[2][1],"z":normals[2][2]})
            
            if len(vertexcolor) > 0:
                txt_col = "true"
                file.write("cA = %(x)f,%(y)f,%(z)f,1\n"%{"x":vertexcolor[0][0],"y":vertexcolor[0][1],"z":vertexcolor[0][2]})
                file.write("cB = %(x)f,%(y)f,%(z)f,1\n"%{"x":vertexcolor[1][0],"y":vertexcolor[1][1],"z":vertexcolor[1][2]})
                file.write("cC = %(x)f,%(y)f,%(z)f,1\n"%{"x":vertexcolor[2][0],"y":vertexcolor[2][1],"z":vertexcolor[2][2]})
            else :
                txt_col = "false"
                file.write("cA = 1.0,1.0,1.0,1.0\n")
                file.write("cB = 1.0,1.0,1.0,1.0\n")
                file.write("cC = 1.0,1.0,1.0,1.0\n")
            
            if len(uvmap) > 0:
                file.write("uv0A = %(x)f,%(y)f\n"%{"x":uvmap[0][0],"y":uvmap[0][1]*-1})
                file.write("uv0B = %(x)f,%(y)f\n"%{"x":uvmap[1][0],"y":uvmap[1][1]*-1})
                file.write("uv0C = %(x)f,%(y)f\n"%{"x":uvmap[2][0],"y":uvmap[2][1]*-1})
            else :
                file.write("uv0A = 0.0,0.0\n")
                file.write("uv0B = 0.0,0.0\n")
                file.write("uv0C = 0.0,0.0\n")
            
            if (bpy.data.meshes[i].uv_textures):
                textureName = bpy.data.meshes[i].uv_textures[0].data[j].image.name
            else :
                textureName = "Material"
            
            materialName = str.split(textureName,".")[0]
            file.write("mat = %s\n"%materialName)
            file.write("}\n")
            
            write_matfile(materialName,txt_col)
            
            write_texturfile(materialName,textureName)
           
        file.close()            


main()