package lls.fractaldemo.engine.fractal.folds;

import org.joml.Vector3f;

import lls.fractaldemo.engine.fractal.Fold;

public class FoldScaleTranslate implements Fold {

	private float scale;
	private Vector3f Trans;
	
	public FoldScaleTranslate(Float scale, Vector3f Trans) {
		this.scale = scale;
		this.Trans = Trans;
	}
	
	@Override
	public Vector3f fold(Vector3f Camera) {
		Camera.mul(scale);
		Camera.add(Trans);
		return Camera;
	}

	@Override
	public Vector3f unfold(Vector3f Camera) {
		Camera.sub(Trans);
		Camera.div(scale);
		return Camera;
	}

	@Override
	public String glsl() {
		String s = "";
		if (scale != 1) {
			if (scale >= 0) {
				s = s + "p *= " + scale + ";\n";
			} else {
				s = s + "p.xyz *= " + scale + ";\np.w *= abs(" + scale + ");\n";
			}
		}
		if (!(Trans.x == 0 && Trans.y == 0 && Trans.z == 0)) {
			s = s + "p.xyz += vec3(" + this.Trans.x + "," + this.Trans.y + "," + this.Trans.z + ");\n";
		}
		return s;
	}

}
