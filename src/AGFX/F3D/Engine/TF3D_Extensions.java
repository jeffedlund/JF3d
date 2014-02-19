package AGFX.F3D.Engine;

import org.lwjgl.opengl.GLContext;

import AGFX.F3D.F3D;

public class TF3D_Extensions
{
	public Boolean VertexBufferObject;
	public Boolean FrameBufferObject;	
	public Boolean GLSL_VertexProgram; 
	public Boolean GLSL_FragmentProgram; 
	public Boolean GLSL_VertexShader;
	public Boolean GLSL_FragmenShader; 
	
	public TF3D_Extensions()
	{
		F3D.Log.info("TF3D_Extensions", "Create - constructor");
		
		// GL_ARB_vertex_buffer_object
		this.VertexBufferObject =   GLContext.getCapabilities().GL_ARB_vertex_buffer_object;
		F3D.Log.info("TF3D_Extensions","GL_ARB_vertex_buffer_object = "+this.VertexBufferObject.toString());
		
		//GL_ARB_framebuffer_object
		this.FrameBufferObject = GLContext.getCapabilities().GL_ARB_framebuffer_object;
		F3D.Log.info("TF3D_Extensions","GL_ARB_framebuffer_object = "+this.VertexBufferObject.toString());
		
		
	
		//GL_ARB_vertex_program
		this.GLSL_VertexProgram = GLContext.getCapabilities().GL_ARB_vertex_program;
		F3D.Log.info("TF3D_Extensions","GL_ARB_vertex_program = "+this.GLSL_VertexProgram.toString());
		
		//GL_ARB_fragment_program
		this.GLSL_FragmentProgram = GLContext.getCapabilities().GL_ARB_fragment_program;
		F3D.Log.info("TF3D_Extensions","GL_ARB_fragment_program = "+this.GLSL_FragmentProgram.toString());
		
		//GL_ARB_vertex_shader
		this.GLSL_VertexShader = GLContext.getCapabilities().GL_ARB_vertex_shader;
		F3D.Log.info("TF3D_Extensions","GL_ARB_vertex_shader = "+this.GLSL_VertexShader.toString());
		
		//GL_ARB_fragment_shader
		this.GLSL_FragmenShader = GLContext.getCapabilities().GL_ARB_fragment_shader;
		F3D.Log.info("TF3D_Extensions","GL_ARB_fragment_shader = "+this.GLSL_FragmenShader.toString());
	}
}
