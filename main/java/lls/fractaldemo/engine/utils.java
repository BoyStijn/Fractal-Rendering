package lls.fractaldemo.engine;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.joml.Matrix3f;
import org.joml.Matrix3fc;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;

import lls.fractaldemo.Renderer;

public class utils {
  private static float look_x;
  
  private static float look_y;
  
  public static String loadResource(String fileName) throws Exception {
	  try (InputStream is = utils.class.getResourceAsStream(fileName)) {
	        if (is == null) return null;
	        try (InputStreamReader isr = new InputStreamReader(is);
	             BufferedReader reader = new BufferedReader(isr)) {
	            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
	        }
	    }
  }
  
  public static Matrix3f make_rot(float pitch, float yaw) {
    float yaws = (float)Math.sin(yaw);
    float yawc = (float)Math.cos(yaw);
    float pitchs = (float)Math.sin(pitch);
    float pitchc = (float)Math.cos(pitch);
    Matrix3f myaw = new Matrix3f(yawc, 0.0F, yaws, 0.0F, 1.0F, 0.0F, -yaws, 0.0F, yawc);
    Matrix3f mpitch = new Matrix3f(1.0F, 0.0F, 0.0F, 0.0F, pitchc, -pitchs, 0.0F, pitchs, pitchc);
    return mpitch.mul((Matrix3fc)myaw.mul((Matrix3fc)new Matrix3f()));
  }
  
  public static Vector3f sum(Matrix3f m) {
    Vector3f row0 = new Vector3f();
    Vector3f row1 = new Vector3f();
    Vector3f row2 = new Vector3f();
    m.getRow(0, row0);
    m.getRow(1, row1);
    m.getRow(2, row2);
    return new Vector3f(row0.x + row1.x + row2.x, row0.y + row1.y + row2.y, row0.z + row1.z + row2.z);
  }
  
  public static Vector2f getMouseMove(Window window, float elapsedTime) {
    DoubleBuffer mouseX = BufferUtils.createDoubleBuffer(1);
    DoubleBuffer mouseY = BufferUtils.createDoubleBuffer(1);
    int mouseCenterX = window.getWidth() / 2;
    int mouseCenterY = window.getHeight() / 2;
    double newMouseX = -1.0D;
    double newMouseY = -1.0D;
    GLFW.glfwSetInputMode(window.getHandle(), 208897, 212994);
    GLFW.glfwGetCursorPos(window.getHandle(), mouseX, mouseY);
    ((Buffer)mouseX).rewind();
    ((Buffer)mouseY).rewind();
    newMouseX = mouseX.get(0);
    newMouseY = mouseY.get(0);
    double mouseDX = newMouseX - mouseCenterX;
    double mouseDY = newMouseY - mouseCenterY;
    GLFW.glfwSetCursorPos(window.getHandle(), mouseCenterX, mouseCenterY);
    look_x += (float)(mouseDX * elapsedTime);
    look_y += (float)(mouseDY * elapsedTime);
    return new Vector2f(look_y, look_x);
  }
  
  public static void prntscrn(Window window, Renderer rend) {
	  File file = new File(System.getProperty("user.home") + "/Desktop/img.png");
	  String format = "PNG";
	  BufferedImage image = new BufferedImage(window.getWidth(), window.getHeight(), BufferedImage.TYPE_INT_RGB);
	  ByteBuffer buffer = rend.getData(window);
	  
	  for(int x = 0; x < window.getWidth(); x++) 
	  {
	      for(int y = 0; y < window.getHeight(); y++)
	      {
	          int i = (x + (window.getWidth() * y)) * 4;
	          int r = buffer.get(i) & 0xFF;
	          int g = buffer.get(i + 1) & 0xFF;
	          int b = buffer.get(i + 2) & 0xFF;
	          image.setRGB(x, window.getHeight() - (y + 1), (0xFF << 24) | (r << 16) | (g << 8) | b);
	      }
	  }
	     
	  try {
	      ImageIO.write(image, format, file);
	  } catch (IOException e) { e.printStackTrace(); }
  }
  
}
