package lls.fractaldemo.engine.fractal.folds;

import org.joml.Vector3f;

import lls.fractaldemo.engine.fractal.Fold;

public class FoldMenger implements Fold{

	@Override
	public Vector3f fold(Vector3f Camera) {
		if (Camera.x < Camera.y) {
			Float tmp = Camera.x;
			Camera.x = Camera.y;
			Camera.y = tmp;
		}
		
		if (Camera.x < Camera.z) {
			Float tmp = Camera.x;
			Camera.x = Camera.z;
			Camera.z = tmp;
		}
		
		if (Camera.y < Camera.z) {
			Float tmp = Camera.y;
			Camera.y = Camera.z;
			Camera.z = tmp;
		}
		return Camera;
	}

	@Override
	public Vector3f unfold(Vector3f Camera) {
		double mx = Math.max(Camera.x, Camera.y);
		if (Math.min(Camera.x,Camera.y) < Math.min(mx, Camera.z)) {
			Float tmp = Camera.y;
			Camera.y = Camera.z;
			Camera.z = tmp;
		}
		if (mx < Camera.z) {
			Float tmp = Camera.x;
			Camera.x = Camera.z;
			Camera.z = tmp;
		}
		if (Camera.x < Camera.y) {
			Float tmp = Camera.x;
			Camera.x = Camera.y;
			Camera.y = tmp;
		}
		return Camera;
	}

	@Override
	public String glsl() {
		return "mengerFold(p);\n";
	}

}
