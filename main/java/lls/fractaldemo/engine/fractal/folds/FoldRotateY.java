package lls.fractaldemo.engine.fractal.folds;

import org.joml.Vector3f;

import lls.fractaldemo.engine.fractal.Fold;

public class FoldRotateY implements Fold{

	private Float angle;
	
	public FoldRotateY(Float angle) {
		this.angle = angle;
	}
	
	@Override
	public Vector3f fold(Vector3f Camera) {
		float sin = (float) Math.sin(angle);
		float cos = (float) Math.cos(angle);
		return new Vector3f(Camera.x*cos-sin*Camera.z, Camera.y, Camera.z*cos+sin*Camera.x);
	}

	@Override
	public Vector3f unfold(Vector3f Camera) {
		float sin = (float) Math.sin(-angle);
		float cos = (float) Math.cos(-angle);
		return new Vector3f(Camera.x*cos-sin*Camera.z, Camera.y, Camera.z*cos+sin*Camera.x);
	}

	@Override
	public String glsl() {
		return "rotY(p, " + angle +");";
	}

}
