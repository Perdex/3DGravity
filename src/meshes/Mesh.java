package meshes;

import java.util.ArrayList;
import lwjgltest.Texture;
import org.joml.Matrix4f;
import org.joml.Vector3f;



public class Mesh {
    private static final Vector3f uniformScale = new Vector3f(lwjgltest.LWJGLtest.uniformScalingFactor);
    private static final float specificScale = 10f;
    
    private final float[] vertArray, texArray;
    private final int[] indexArray;
    
    protected Texture texture;
    
    private Vector3f scale = new Vector3f(1, 1, 1), pos = new Vector3f();
    
    public Mesh(ArrayList<Float> vert, ArrayList<Float> text, ArrayList<Integer> ind){
        
        vertArray = new float[vert.size()];
        texArray = new float[text.size()];
        indexArray = new int[ind.size()];
        
        //convert to native arrays
        for(int i = 0; i < vert.size(); i++)
            vertArray[i] = vert.get(i);
        for(int i = 0; i < text.size(); i++)
            texArray[i] = text.get(i);
        for(int i = 0; i < ind.size(); i++)
            indexArray[i] = ind.get(i);
    }
    public Mesh(float[] vert, float[] text, int[] ind){
        vertArray = vert;
        texArray = text;
        indexArray = ind;
    }
    
    public float[] getTextArray(){
        return texArray;
    }
    public float[] getVertArray(){
        return vertArray;
    }
    public int[] getIndexArray(){
        return indexArray;
    }
    
    public Texture getTexture(){
        return texture;
    }
    public boolean isTextured(){
        return texture != null;
    }
    
    public void setPos(Vector3f v){
        pos = v;
    }
    public void translate(Vector3f v){
        pos.add(v);
    }
    
    public void scale(float s){
        scale.mul(s);
    }
    public void scale(Vector3f s){
        scale.mul(s);
    }
    public void setScale(float s){
        scale = new Vector3f(s);
    }
    public void setScale(Vector3f s){
        scale = s;
    }
    public Vector3f getDrawPos(){
        return pos.mul(uniformScale, new Vector3f()).mul(5);//WHY DOES IT NEED THIS 5?!?!?
    }
    public Vector3f getRawPos(){
        return pos.add(new Vector3f(), new Vector3f());
    }
    public Vector3f getScale(){
        return scale;
    }
    
    
    public Matrix4f getViewMatrix(){
        return new Matrix4f().scale(uniformScale).translate(pos).scale(scale).scale(specificScale);
    }
}
