package lls.fractaldemo.engine.fractal.folds;

import org.joml.Vector3f;

import lls.fractaldemo.engine.fractal.Fold;

public class FoldAbs implements Fold {

	@Override
	public Vector3f fold(Vector3f Camera) {
		return Camera.absolute();
	}

	@Override
	public Vector3f unfold(Vector3f Camera) {
		if (Camera.x < 0) Camera.x = -Camera.x;
		if (Camera.y < 0) Camera.y = -Camera.y;
		if (Camera.z < 0) Camera.z = -Camera.z;
		return null;
	}

	@Override
	public String glsl() {
		return "p.xyz = abs(p.xyz);\n";
	}

}
