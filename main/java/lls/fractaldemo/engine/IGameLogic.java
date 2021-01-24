package lls.fractaldemo.engine;

import org.joml.Matrix4f;

public interface IGameLogic {
	
	  void init() throws Exception;
	  
	  Matrix4f input(Window paramWindow, float paramFloat);
	  
	  void update(float paramFloat);
	  
	  void render(Window paramWindow);
	  
	  void cleanup();
	}
