package Sim;

import meshes.Mesh;
import lwjgltest.Model;
import org.joml.Vector3f;

public class GravSim implements Runnable{
    
    private static final float G = 6.674e-11f;
    
    private static final float timeStep = 10f;

    private final int n;
    
    private final Vector3f[] pos, v;
    private final Mesh[] meshes;
    
    private final float[] mass;
    
    private final Model[] models;
    
    private boolean execute = true;
    
    public GravSim(){
        
        Planet[] planets = Planet.makePlanets();
        
        n = planets.length;
        
        meshes = new Mesh[n];
        mass = new float[n];
        pos = new Vector3f[n];
        v = new Vector3f[n];
        models = new Model[n];
        
        for(int i = 0; i < n; i++){
            Planet p = planets[i];
            meshes[i] = p.getMesh();
            mass[i] = p.mass;
            pos[i] = p.pos;
            v[i] = p.v;
            models[i] = p.makeModel(i);
        }
        
        
    }
    
    public Model[] getModels(){
        return models;
    }
   
    @Override
    public void run(){
        
        while(execute){
            runStep(timeStep);
            try{
                Thread.sleep(1);
            }catch(InterruptedException e){
                System.err.println(e);
            }
        }
        
    }
    
    private void runStep(float dt){
        
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
    
    public void stop(){
        execute = false;
    }
    
}
