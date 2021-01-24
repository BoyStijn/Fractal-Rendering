package lls.fractaldemo.engine;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class Window {
  private final String title;
  
  private int width;
  
  private int height;
  
  private long windowHandle;
  
  private boolean resized;
  
  private boolean vSync;
  
  private boolean Fullscreen;
  
  public Window(String title, int width, int height, boolean vSync, boolean Fullscreen) {
    this.title = title;
    this.width = width;
    this.height = height;
    this.vSync = vSync;
    this.Fullscreen = Fullscreen;
    this.resized = false;
  }
  
  public void init() {
    GLFWErrorCallback.createPrint(System.err).set();
    if (!GLFW.glfwInit())
      throw new IllegalStateException("Unable to initialize GLFW"); 
    GLFW.glfwDefaultWindowHints();
    GLFW.glfwWindowHint(131076, 0);
    GLFW.glfwWindowHint(131075, 1);
    GLFW.glfwWindowHint(139266, 3);
    GLFW.glfwWindowHint(139267, 2);
    GLFW.glfwWindowHint(139272, 204801);
    GLFW.glfwWindowHint(139270, 1);
    if (isFullscreen()) {
      this.windowHandle = GLFW.glfwCreateWindow(this.width, this.height, this.title, GLFW.glfwGetPrimaryMonitor(), 0L);
    } else {
      this.windowHandle = GLFW.glfwCreateWindow(this.width, this.height, this.title, 0L, 0L);
    } 
    if (this.windowHandle == 0L)
      throw new RuntimeException("Failed to create the GLFW window"); 
    GLFW.glfwSetInputMode(this.windowHandle, 208897, 212994);
    GLFW.glfwSetFramebufferSizeCallback(this.windowHandle, (window, width, height) -> {
          this.width = width;
          this.height = height;
          setResized(true);
        });
    GLFW.glfwSetKeyCallback(this.windowHandle, (window, key, scancode, action, mods) -> {
          if (key == 256 && action == 0)
            GLFW.glfwSetWindowShouldClose(window, true); 
        });
    GLFWVidMode vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
    GLFW.glfwSetWindowPos(
        this.windowHandle, (
        vidmode.width() - this.width) / 2, (
        vidmode.height() - this.height) / 2);
    GLFW.glfwMakeContextCurrent(this.windowHandle);
    if (isvSync())
      GLFW.glfwSwapInterval(1); 
    GLFW.glfwShowWindow(this.windowHandle);
    GL.createCapabilities();
    GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
  }
  
  public void setClearColor(float r, float g, float b, float alpha) {
    GL11.glClearColor(r, g, b, alpha);
  }
  
  public boolean isKeyPressed(int keyCode) {
    return (GLFW.glfwGetKey(this.windowHandle, keyCode) == 1);
  }
  
  public boolean windowShouldClose() {
    return GLFW.glfwWindowShouldClose(this.windowHandle);
  }
  
  public String getTitle() {
    return this.title;
  }
  
  public int getWidth() {
    return this.width;
  }
  
  public int getHeight() {
    return this.height;
  }
  
  public boolean isResized() {
    return this.resized;
  }
  
  public void setResized(boolean resized) {
    this.resized = resized;
  }
  
  public boolean isvSync() {
    return this.vSync;
  }
  
  public void setvSync(boolean vSync) {
    this.vSync = vSync;
  }
  
  public boolean isFullscreen() {
    return this.Fullscreen;
  }
  
  public void setFullscreen(boolean Fullscreen) {
    this.Fullscreen = Fullscreen;
  }
  
  public long getHandle() {
    return this.windowHandle;
  }
  
  public void update() {
    GLFW.glfwSwapBuffers(this.windowHandle);
    GLFW.glfwPollEvents();
  }
}
