package lls.fractaldemo;


import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector2f;
import org.joml.Vector3f;

import lls.fractaldemo.engine.IGameLogic;
import lls.fractaldemo.engine.Window;
import lls.fractaldemo.engine.utils;

public class Demo implements IGameLogic {
  private float color = 0.0F;
  
  private final Renderer renderer;
  
  private Matrix4f mat;
  
  private Matrix4f prevmat;
  
  private Matrix4f projection;
 
  
  private Vector3f pos;
  
  private float movementSpeed = 5.0F;
  
  public Demo() {
    this.renderer = new Renderer();
  }
  
  public void init() throws Exception {
    this.renderer.init();
    this.mat = new Matrix4f();
    this.projection = new Matrix4f();
    this.pos = new Vector3f(0.0F, 14.0F, 0.0F);
    this.prevmat = this.projection;
  }
  
  public Matrix4f input(Window window, float elapsedTime) {
    this.prevmat = this.projection;
    this.projection.zero();
    this.projection = (new Matrix4f()).perspective(60.0F, (window.getWidth() / window.getHeight()), 0.0F, Float.POSITIVE_INFINITY);
    float move = elapsedTime * this.movementSpeed;
    if (window.isKeyPressed(80)) {
    	utils.prntscrn(window, renderer);
    }
    
    if (window.isKeyPressed(340))
      move *= 0.5F; 
    if (window.isKeyPressed(341))
      move *= 2.0F; 
    float lr_force = (window.isKeyPressed(37) || window.isKeyPressed(65) ? -1.0f : 0.0f) +
            (window.isKeyPressed(39) || window.isKeyPressed(68) ? 1.0f : 0.0f);
    float ud_force = (window.isKeyPressed(40) || window.isKeyPressed(83) ? -1.0f : 0.0f) +
            (window.isKeyPressed(38) || window.isKeyPressed(87) ? 1.0f : 0.0f);
    this.updatePlayer(lr_force, ud_force, move);
   
    Vector2f rot = utils.getMouseMove(window, elapsedTime);
    this.mat.identity()
      .rotateX(rot.x)
      .rotateY(rot.y)
      .translate(-this.pos.x, -this.pos.y, -this.pos.z);
    this.mat.invert();
    return this.projection.mul((Matrix4fc)this.mat);
  }
  
  public void update(float interval) {
	  
	  
  }
  
  public void render(Window window) {
    window.setClearColor(this.color, this.color, this.color, 0.0F);
    this.renderer.render(window, this.mat, this.prevmat);
  }
  
  public void cleanup() {
    this.renderer.cleanup();
  }
  
  public void updatePlayer(Float dx, Float dy, Float mult) {
	  
	  Float mag2 = dx*dx + dy*dy;
	  if (mag2 > 1.0f) {
	    Float mag = (float) Math.sqrt(mag2);
	    dx /= mag;
	    dy /= mag;
	  }
	  
	  Matrix3f camview = new Matrix3f();
	  mat.get3x3(camview);
	  Vector3f col2 = new Vector3f();
	 camview.getColumn(2, col2);
	  Vector3f col0 = new Vector3f();
	 camview.getColumn(0, col0);
	  this.pos.add(col2.mul(-dy * mult));
	  this.pos.add(col0.mul(dx * mult));
	  
  }
  
}
