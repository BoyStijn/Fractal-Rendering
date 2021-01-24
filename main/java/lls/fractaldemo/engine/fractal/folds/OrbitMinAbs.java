package lls.fractaldemo.engine.fractal.folds;

import org.joml.Vector3f;

import lls.fractaldemo.engine.fractal.Fold;

public class OrbitMinAbs implements Fold {

	
	private Vector3f scale;
	private Vector3f origin;
	
	public OrbitMinAbs(Vector3f scale, Vector3f origin) {
		this.scale = scale;
		this.origin = origin;
	}
	
	@Override
	public Vector3f fold(Vector3f Camera) {
		return Camera;
	}

	@Override
	public Vector3f unfold(Vector3f Camera) {
		return Camera;
	}

	@Override
	public String glsl() {
		return "orbit = min(orbit, abs((p.xyz - vec3(" + origin.x + ","  + origin.y + "," + origin.z + "))*vec3(" + scale.x + ","  + scale.y + "," + scale.z + ")));\n";
	}

}
