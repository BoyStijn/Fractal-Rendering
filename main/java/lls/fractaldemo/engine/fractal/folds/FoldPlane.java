package lls.fractaldemo.engine.fractal.folds;

import org.joml.Vector3f;

import lls.fractaldemo.engine.fractal.Fold;

public class FoldPlane implements Fold {

	private Float scale;
	private Vector3f loc;
	
	public FoldPlane(Vector3f loc, Float scale) {
		this.scale = scale;
		this.loc = loc;
	}
	
	@Override
	public Vector3f fold(Vector3f Camera) {
		return null;
	}

	@Override
	public Vector3f unfold(Vector3f Camera) {
		return null;
	}

	@Override
	public String glsl() {
		return "planeFold(p, vec3(" + this.loc.x + "," + this.loc.y + "," + this.loc.z + "), " + scale + ");\n";
	}

}
