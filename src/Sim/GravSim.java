package Sim;

import meshes.Mesh;
import org.joml.Vector3f;

public class GravSim {
    
    private static final float G = 6.674e-11f;
    
    private static final float timeScale = 1e4f;

    private final int n;
    
    private final Vector3f[] pos, v;
    private final Mesh[] meshes;
    
    private final float[] mass;
    
    public GravSim(Mesh[] meshes, Vector3f[] v, float[] mass){
        
        n = meshes.length;
        
        pos = new Vector3f[n];
        
        for(int i = 0; i < n; i++)
            pos[i] = meshes[i].getRawPos();
        
        this.meshes = meshes;
        this.mass = mass;
        this.v = v;
    }
    
    
    public void run(float dt){
        
        dt *= timeScale;
        
        for(int i = 0; i < n; i++)
            for(int j = i+1; j < n; j++)
                fall(i, j, dt);
            
        for(int i = 0; i < n; i++)
            move(i, dt);
        
    }
    
    public void move(int i, float dt){
        
        pos[i].add(v[i].mul(dt, new Vector3f()));
        
        meshes[i].setPos(pos[i]);
    }
    
    public void fall(int i, int j, float dt){
        Vector3f force = pos[i].negate(new Vector3f()).add(pos[j]);
        
        float dsq = force.lengthSquared();
        
        force.normalize().mul(dt * G / dsq);
        
        v[i].add(force.mul(mass[j], new Vector3f()));
        v[j].add(force.mul(-mass[i]));
        
    }
    
}
