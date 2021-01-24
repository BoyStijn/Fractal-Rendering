package lls.fractaldemo.engine.fractal;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;

public class Object {
	  private String name;
	  
	  private Geometry geo;
	  
	  private List<Fold> folds;
	  
	  private Vector3f col;
	  
	  public Object(String name, Geometry geo, Vector3f Color) {
	    this.geo = geo;
	    this.name = name;
	    this.folds = new ArrayList<Fold>();
	    this.col = Color;
	  }
	  
	  public void Add(Fold fold) {
		  folds.add(fold);
	  }
	  
	  public String compileDE() {
	    String s = "float de_" + this.name + "(vec4 p) {\n";
	    s = s + "vec3 orbit = vec3("+ col.x + "," + col.y + "," + col.z +");\n";
	    
	    for (Fold fold : folds) {
	    	s = s + fold.glsl() + "\n";
	    }
	    
	    s = s + "return " + this.geo.glsl() + ";\n";
	    s = s + "}\n";
	    return s;
	  }
	  
	  public String compileCOL() {
		    String s = "vec4 col_" + this.name + "(vec4 p) {\n";		
		    s = s + "vec3 orbit = vec3("+ col.x + "," + col.y + "," + col.z +");\n";
		    
		    
		    for (Fold fold : folds) {
		    	s = s + fold.glsl() + "\n";
		    }
		    
		    s = s + "return vec4(orbit, " + this.geo.glsl() + ");\n";
		    s = s + "}\n";
		    return s;
		  }
	  
	  public String getName() {
	    return this.name;
	  }
	  
	  public Float DE() {
		  Vector3f pos = geo.getPos();
		  for (Fold fold : folds) {
			  pos = fold.unfold(pos);
		  }
		  return geo.DE(pos);
	  }
	  
	  public Geometry getGeo() {
		  return this.geo;
	  }
	  
	}
