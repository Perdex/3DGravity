package meshes;

import java.util.ArrayList;
import org.joml.Matrix4f;
import org.joml.Vector3f;



public class Mesh {
    private static final Vector3f uniformScale = new Vector3f(1e-5f);
    private static final float specificScale = 20f;
    
    private final float[] vertices, texture;
    private final int[] indeces;
    
    private Vector3f scale = new Vector3f(1, 1, 1), pos = new Vector3f();
    
    public Mesh(ArrayList<Float> vert, ArrayList<Float> text, ArrayList<Integer> ind){
        
        vertices = new float[vert.size()];
        texture = new float[text.size()];
        indeces = new int[ind.size()];
        
        //convert to native arrays
        for(int i = 0; i < vert.size(); i++)
            vertices[i] = vert.get(i);
        for(int i = 0; i < text.size(); i++)
            texture[i] = text.get(i);
        for(int i = 0; i < ind.size(); i++)
            indeces[i] = ind.get(i);
    }
    public Mesh(float[] vert, float[] text, int[] ind){
        vertices = vert;
        texture = text;
        indeces = ind;
    }
    
    public float[] getTexture(){
        return texture;
    }
    public float[] getVertices(){
        return vertices;
    }
    public int[] getIndeces(){
        return indeces;
    }
    
    public void setPos(Vector3f v){
        pos = v.add(new Vector3f(), new Vector3f());
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
        return pos.mul(uniformScale, new Vector3f());
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
