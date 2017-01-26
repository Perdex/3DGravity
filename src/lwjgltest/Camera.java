package lwjgltest;

import meshes.Mesh;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Camera {

    private static final boolean freecamera = false;
    private static final float movementSpeed = 200f;
    
    private static Vector3f pos;
    private static Matrix4f proj, scale;
    private static Quaternionf rot;
    
    static Mesh targetMesh;
    
    public Camera(int width, int height){
        int dist = 10000;
        pos = new Vector3f(-dist, -dist, dist);
        
        proj = new Matrix4f().setPerspective((float)Math.PI/2f, 16f/9f, 0.1f, Float.POSITIVE_INFINITY);
        
        rot = new Quaternionf(0, 0, 0, 1).rotateX(-1).rotateZ(0.8f);
        
        scale = new Matrix4f().scale(5);
    }
    
    
    
    public void translateFree(float amount, int axis){
        amount *= movementSpeed;
        switch(axis){
            case 0:
                pos.add(rot.positiveX(new Vector3f()).mul(amount));
                break;
            case 1:
                pos.add(rot.positiveY(new Vector3f()).mul(amount));
                break;
            case 2:
                pos.add(rot.positiveZ(new Vector3f()).mul(amount));
                break;
        }
    }
    public void translate(float amount, int axis){
        if(freecamera){
            translateFree(amount, axis);
            return;
        }
        amount *= movementSpeed;
        
        switch(axis){
            case 0:
                pos.add(rot.positiveZ(new Vector3f()).cross(new Vector3f(0, 0, -1)).normalize().mul(amount));
                break;
            case 1:
                pos.add(0, 0, amount);
                break;
            case 2:
                pos.add(rot.positiveX(new Vector3f()).cross(new Vector3f(0, 0, 1)).normalize().mul(amount));
                break;
        }
    }
    
    public void rotate(float angle, int axis){
        //System.out.println(rot.positiveZ(new Vector3f()));
        switch(axis){
            case 0:
                rot.rotateAxis(angle, rot.positiveX(new Vector3f()));
                break;
            case 1:
                if(freecamera)
                    rot.rotateAxis(angle, rot.positiveY(new Vector3f()));
                else
                    rot.rotateZ(angle);
                break;
            case 2:
                if(freecamera)
                    rot.rotateAxis(angle, rot.positiveZ(new Vector3f()));
                break;
        }
    }
    
    public Vector3f getNormal(){
        return rot.positiveZ(new Vector3f());
    }
    public Vector3f getPosition(){
        return pos.add(targetMesh.getDrawPos(), new Vector3f());
    }
    
    public Matrix4f getProjection(){
        Matrix4f target = new Matrix4f();
        Matrix4f pos2 = new Matrix4f().setTranslation(getPosition().mul(-1f, new Vector3f()));
        
        target = proj.rotate(rot, target);
        target = target.mul(pos2, target);
        return target.mul(scale);
    }
    
}
