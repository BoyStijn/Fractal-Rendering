package lls.fractaldemo.engine.fractal.geometry;

import org.joml.Vector3f;

import lls.fractaldemo.engine.fractal.Geometry;

public class Box implements Geometry {
	  private Vector3f location;
	  
	  private Vector3f size;
	  
	  public Box(Vector3f size, Vector3f location) {
	    this.location = location;
	    this.size = size;
	  }
	  
	  public Vector3f getPos() {
		  return this.location;
	  }
	  
	  public float DE(Vector3f camera) {
	    Vector3f p = camera.sub(this.location);
	    Vector3f q = p.absolute().sub(this.size);
	    float d = q.max(new Vector3f(0.0F, 0.0F, 0.0F)).length() + Math.min(q.maxComponent(), 0);
	    return d;
	  }
	  
	  public String glsl() {
	    return "de_box(p - vec4(vec3(" + this.location.x + "," + this.location.y + "," + this.location.z + "), 0), vec3(" + this.size.x + ", " + this.size.y + ", " + this.size.z + "))";
	  }

	  public void setPos(Vector3f pos) {
		this.location = pos;
	}
}
