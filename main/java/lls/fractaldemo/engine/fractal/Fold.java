package lls.fractaldemo.engine.fractal;

import org.joml.Vector3f;

public interface Fold {

	Vector3f fold(Vector3f Camera);
	
	Vector3f unfold(Vector3f Camera);
	
	String glsl();
	
}
