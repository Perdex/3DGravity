package Sim;

import lwjgltest.Model;
import meshes.Ball;

import org.joml.Vector3f;


public class Planet {

    final Vector3f pos, v;
    final float mass, r;
    final int precision;
    final String texture;
    Ball mesh;

    public Planet(Vector3f pos, Vector3f v, float mass, float r, String texture){
        this.pos = pos;
        this.v = v;
        this.mass = mass;
        this.r = r;
        this.precision = 10 + (int)Math.log(r) * 2;
        this.texture = texture;
    }
    
    
    Model makeModel(int i){
        return Model.createFromMesh(getMesh(), i);
    }
    Ball getMesh(){
        if(mesh != null)
            return mesh;
        return Ball.makeBall(r, pos, precision, texture);
    }
    
    static Planet[] makePlanets(){
        
        Vector3f earthPos = new Vector3f(1.496e11f, 0, 0);
        Vector3f earthV = new Vector3f(0, 27.9e3f, 0);
        
        return new Planet[]{
            new Planet(new Vector3f(), new Vector3f(), 1.989e30f, 6.957e8f, "Sun.jpg"),
            new Planet(earthPos, earthV, 5.972e24f, 6.371e6f, "Earth.jpg"),
            new Planet(earthPos.add(new Vector3f(384.4e6f, 0, 0), new Vector3f()),
                        earthV.add(new Vector3f(0, 1.02e3f, 0), new Vector3f()), 7.348e22f, 1.737e6f, "Moon.jpg"),
        };
    }
    
}
