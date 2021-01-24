package lls.fractaldemo;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import lls.fractaldemo.engine.fractal.Object;
import lls.fractaldemo.engine.fractal.folds.FoldAbs;
import lls.fractaldemo.engine.fractal.folds.FoldMenger;
import lls.fractaldemo.engine.fractal.folds.FoldPlane;
import lls.fractaldemo.engine.fractal.folds.FoldRotateY;
import lls.fractaldemo.engine.fractal.folds.FoldScaleTranslate;
import lls.fractaldemo.engine.fractal.folds.OrbitMinAbs;
import lls.fractaldemo.engine.fractal.geometry.Box;
import lls.fractaldemo.engine.Shaders;
import lls.fractaldemo.engine.Window;
import lls.fractaldemo.engine.utils;

public class Renderer {
  private int vboId;
  
  private int vaoId;
  
  private int matId;
  
  private int prevmatId;
  
  private int resId;
  
  private int ipdId;
  
  private Shaders shaderProgram;
  
  public void render(Window window, Matrix4f mat, Matrix4f prevmat) {
    clear();
    if (window.isResized()) {
      GL11.glViewport(0, 0, window.getWidth(), window.getHeight());
      window.setResized(false);
    } 
    this.shaderProgram.bind();
    FloatBuffer matbuf = MemoryUtil.memAllocFloat(16);
    FloatBuffer prevmatbuf = MemoryUtil.memAllocFloat(16);
    GL20.glUniform2fv(this.resId, new float[] { window.getWidth(), window.getHeight() });
    GL20.glUniform1f(this.ipdId, 0.04F);
    GL20.glUniformMatrix4fv(this.matId, false, mat.get(matbuf));
    GL20.glUniformMatrix4fv(this.prevmatId, false, prevmat.get(prevmatbuf));
    MemoryUtil.memFree(matbuf);
    MemoryUtil.memFree(prevmatbuf);
    GL30.glBindVertexArray(this.vaoId);
    GL11.glDrawArrays(5, 0, 4);
    GL30.glBindVertexArray(0);
    this.shaderProgram.unbind();
  }
  
  public void init() throws Exception {
    this.shaderProgram = new Shaders();
    String vertshader = utils.loadResource("/vertex.glsl");
    if (vertshader == null || vertshader == "") {
    	throw new Error("vert shader not found");
    }
    this.shaderProgram.createVertexShader(vertshader);
    
    Object obj = new Object("test", new Box(new Vector3f(4.8F, 4.8F, 4.8F), new Vector3f(0.0F, 0.0F, 0.0F)), new Vector3f(1e20f, 1e20f, 1e20f));
    
    for (int l=0;l<30;l++) {
    	obj.Add(new FoldRotateY(0.44f));
    	obj.Add(new FoldAbs());
    	obj.Add(new FoldMenger());
    	obj.Add(new OrbitMinAbs(new Vector3f(0.24f,2.28f,7.6f), new Vector3f(0,0,0)));
    	obj.Add(new FoldScaleTranslate(1.3f, new Vector3f(-2,-4.8f,0)));
    	obj.Add(new FoldPlane(new Vector3f(0,0,-1), 0.0f));
    }
    
    String fragshader = utils.loadResource("/fragment.glsl");
    if (fragshader == null || fragshader == "") {
    	throw new Error("vert shader not found");
    }
    int split = fragshader.indexOf("// [/define]");
    fragshader = String.valueOf(fragshader.substring(0, split)) + "#define DE de_" + obj.getName() + "\n #define COL col_" + obj.getName() + '\n' + fragshader.substring(split);
    split = fragshader.indexOf("// [/col]");
    fragshader = String.valueOf(fragshader.substring(0, split)) + obj.compileCOL() + fragshader.substring(split);
    split = fragshader.indexOf("// [/object]");
    fragshader = String.valueOf(fragshader.substring(0, split)) + obj.compileDE() + fragshader.substring(split);
    this.shaderProgram.createFragmentShader(fragshader);
    this.shaderProgram.link();
    int program = this.shaderProgram.getProgramID();
    this.matId = GL20.glGetUniformLocation(program, "iMat");
    this.prevmatId = GL20.glGetUniformLocation(program, "iPrevMat");
    this.resId = GL20.glGetUniformLocation(program, "iResolution");
    this.ipdId = GL20.glGetUniformLocation(program, "iIPD");
    float[] vertices = { 
        -1.0F, -1.0F, 0.0F, 
        1.0F, -1.0F, 0.0F, 
        -1.0F, 1.0F, 0.0F, 
        1.0F, 
        1.0F, 0.0F };
    FloatBuffer verticesBuffer = null;
    try {
      verticesBuffer = MemoryUtil.memAllocFloat(vertices.length);
      ((Buffer) verticesBuffer.put(vertices)).flip();
      this.vaoId = GL30.glGenVertexArrays();
      GL30.glBindVertexArray(this.vaoId);
      this.vboId = GL15.glGenBuffers();
      GL15.glBindBuffer(34962, this.vboId);
      GL15.glBufferData(34962, verticesBuffer, 35044);
      GL20.glEnableVertexAttribArray(0);
      GL20.glVertexAttribPointer(0, 3, 5126, false, 0, 0L);
      GL15.glBindBuffer(34962, 0);
      GL30.glBindVertexArray(0);
    } finally {
      if (verticesBuffer != null)
        MemoryUtil.memFree(verticesBuffer); 
    } 
  }
  
  public void clear() {
    GL11.glClear(16640);
  }
  
  public ByteBuffer getData(Window window) {
	  GL11.glReadBuffer(GL11.GL_FRONT);
	  int width = window.getWidth();
	  int height= window.getHeight();
	  int bpp = 4; // Assuming a 32-bit display with a byte each for red, green, blue, and alpha.
	  ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * bpp);
	  GL11.glReadPixels(0, 0, width, height, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer );
	  return buffer;
  }
  
  public void cleanup() {
    if (this.shaderProgram != null)
      this.shaderProgram.cleanup(); 
    GL20.glDisableVertexAttribArray(0);
    GL15.glBindBuffer(34962, 0);
    GL15.glDeleteBuffers(this.vboId);
    GL30.glBindVertexArray(0);
    GL30.glDeleteVertexArrays(this.vaoId);
  }
}