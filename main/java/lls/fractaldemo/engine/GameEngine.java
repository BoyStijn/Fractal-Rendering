package lls.fractaldemo.engine;

public class GameEngine implements Runnable {
	  public static final float TARGET_FPS = 1.0F;
	  
	  public static final float TARGET_UPS = 1.0F;
	  
	  private final Window window;
	  
	  private final Timer timer;
	  
	  private final IGameLogic gameLogic;
	  
	  public GameEngine(String windowTitle, int width, int height, boolean vSync, boolean Fullscreen, IGameLogic gameLogic) throws Exception {
	    this.window = new Window(windowTitle, width, height, vSync, Fullscreen);
	    this.gameLogic = gameLogic;
	    this.timer = new Timer();
	  }
	  
	  public void run() {
	    try {
	      init();
	      gameLoop();
	    } catch (Exception excp) {
	      excp.printStackTrace();
	    } 
	  }
	  
	  protected void init() throws Exception {
	    this.window.init();
	    this.timer.init();
	    this.gameLogic.init();
	  }
	  
	  protected void gameLoop() {
	    float accumulator = 0.0F;
	    float interval = 1.0F;
	    boolean running = true;
	    while (running && !this.window.windowShouldClose()) {
	      float elapsedTime = this.timer.getElapsedTime();
	      accumulator += elapsedTime;
	      input(elapsedTime);
	      while (accumulator >= interval) {
	        update(interval);
	        accumulator -= interval;
	      } 
	      render();
	      if (!this.window.isvSync())
	        sync(); 
	    } 
	  }
	  
	  private void sync() {
	    float loopSlot = 1.0F;
	    double endTime = this.timer.getLastLoopTime() + loopSlot;
	    while (this.timer.getTime() < endTime) {
	      try {
	        Thread.sleep(1L);
	      } catch (InterruptedException interruptedException) {}
	    } 
	  }
	  
	  protected void input(float elapsedTime) {
	    this.gameLogic.input(this.window, elapsedTime);
	  }
	  
	  protected void update(float interval) {
	    this.gameLogic.update(interval);
	  }
	  
	  protected void render() {
	    this.gameLogic.render(this.window);
	    this.window.update();
	  }
	}

