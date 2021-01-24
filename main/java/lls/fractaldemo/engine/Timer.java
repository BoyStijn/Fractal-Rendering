package lls.fractaldemo.engine;

public class Timer {
	  private double lastLoopTime;
	  
	  public void init() {
	    this.lastLoopTime = getTime();
	  }
	  
	  public double getTime() {
	    return System.nanoTime() / 1.0E9D;
	  }
	  
	  public float getElapsedTime() {
	    double time = getTime();
	    float elapsedTime = (float)(time - this.lastLoopTime);
	    this.lastLoopTime = time;
	    return elapsedTime;
	  }
	  
	  public double getLastLoopTime() {
	    return this.lastLoopTime;
	  }
	}

