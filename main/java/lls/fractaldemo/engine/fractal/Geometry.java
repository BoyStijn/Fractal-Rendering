package lls.fractaldemo.engine.fractal;

import org.joml.Vector3f;

public interface Geometry {
	  String glsl();
	  
	  float DE(Vector3f paramVector3f);
	  
	  public Vector3f getPos();
	  
	  public void setPos(Vector3f pos);
	}
