package lls.fractaldemo.engine;

import org.lwjgl.opengl.GL20;

public class Shaders {
  private final int programId = GL20.glCreateProgram();
  
  private int vertexShaderId;
  
  private int fragmentShaderId;
  
  public Shaders() throws Exception {
    if (this.programId == 0)
      throw new Exception("Could not create Shader"); 
  }
  
  protected int createShader(String shaderCode, int shaderType) throws Exception {
    int shaderId = GL20.glCreateShader(shaderType);
    if (shaderId == 0)
      throw new Exception("Error creating shader. Type: " + shaderType); 
    GL20.glShaderSource(shaderId, shaderCode);
    GL20.glCompileShader(shaderId);
    if (GL20.glGetShaderi(shaderId, 35713) == 0)
      throw new Exception("Error compiling Shader code: " + GL20.glGetShaderInfoLog(shaderId, 1024)); 
    GL20.glAttachShader(this.programId, shaderId);
    return shaderId;
  }
  
  public void link() throws Exception {
    GL20.glLinkProgram(this.programId);
    if (GL20.glGetProgrami(this.programId, 35714) == 0)
      throw new Exception("Error linking Shader code: " + GL20.glGetProgramInfoLog(this.programId, 1024)); 
    if (this.vertexShaderId != 0)
      GL20.glDetachShader(this.programId, this.vertexShaderId); 
    if (this.fragmentShaderId != 0)
      GL20.glDetachShader(this.programId, this.fragmentShaderId); 
    GL20.glValidateProgram(this.programId);
    if (GL20.glGetProgrami(this.programId, 35715) == 0)
      System.err.println("Warning validating Shader code: " + GL20.glGetProgramInfoLog(this.programId, 1024)); 
  }
  
  public void createVertexShader(String shaderCode) throws Exception {
    this.vertexShaderId = createShader(shaderCode, 35633);
  }
  
  public void createFragmentShader(String shaderCode) throws Exception {
    this.fragmentShaderId = createShader(shaderCode, 35632);
  }
  
  public void bind() {
    GL20.glUseProgram(this.programId);
  }
  
  public void unbind() {
    GL20.glUseProgram(0);
  }
  
  public void cleanup() {
    unbind();
    if (this.programId != 0)
      GL20.glDeleteProgram(this.programId); 
  }
  
  public int getProgramID() {
    return this.programId;
  }
}