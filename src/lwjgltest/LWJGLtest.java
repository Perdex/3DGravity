package lwjgltest;

import Sim.GravSim;
import meshes.Mesh;

import org.joml.Vector3f;
import org.lwjgl.opengl.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;

import org.lwjgl.glfw.GLFWErrorCallback;

public class LWJGLtest{

    static final int WIDTH = 1600, HEIGHT = 900;
    
    
    private static long win;
    
    private static Texture tex;
    static Camera camera;
     
    private static Model m[];
    
    static Shader shader;
    
    private static GravSim sim;
    
    private static int frames = 0;
    
    public static void main(String[] args){
        init();
    
        double lastTime = getTime();
        
        int lastframes = 0;
        
        while(!glfwWindowShouldClose(win)){
            
            //show FPS every second
            if(getTime() - lastTime >= 1.0){
                lastTime = getTime();
                System.out.println("FPS: " + (frames - lastframes));
                lastframes = frames;
            }
            frames++;
            
            updateDisplay();
        }
        closeDisplay();
    }
    
    private static double getTime(){
        return (double)System.nanoTime() / 1e9;
    }
    
    public static void updateDisplay(){
        
        sim.run(1f/60f);
        
        glClear(GL_COLOR_BUFFER_BIT);//init screen to bg color
        
        shader.bind();
        
        
        shader.setUniform("LightPosition", 0, -10, 10);


        tex.bind(0);
        
        Actions.applyEvents(win);
        
        for(Model m2: m)
            m2.render();
        

        glfwSwapBuffers(win);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); 
        
        
        // Poll for window events. The key callback above will only be
        // invoked during this call.
        glfwPollEvents();
        
    }
    
    
    
    public static void init(){
        
        //set error stream
	GLFWErrorCallback.createPrint(System.err).set();
        
        
        if(!glfwInit()){
            System.err.println("Init errored!");
            System.exit(1);
        }
        
        win = glfwCreateWindow(WIDTH, HEIGHT, "It's (now) 3D!", 0, 0);
        
        Actions.addCallbacks(win);
        
        // Make the OpenGL context current
        glfwMakeContextCurrent(win);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(win);
        
        //init GL environment
        GL.createCapabilities();
        
        //glEnable(GL_TEXTURE_2D);
        
        glEnable(GL_DEPTH_TEST);
        glDepthMask(true);
        glDepthFunc(GL_LEQUAL);
//        glFrontFace(GL_CW);
//        glEnable(GL_CULL_FACE);
        glDisable(GL_DITHER);
        
        glClearColor(0, 0, 0.15f, 1);
        
        
        m = new Model[3];
        
        
        m[0] = Model.createFromMesh(meshes.Ball.makeBall(6.371e6f, new Vector3f(0, 0, 0), 50));
        m[1] = Model.createFromMesh(meshes.Ball.makeBall(1.737e6f, new Vector3f(3.844e8f, 0, 0), 50));
        
        m[2] = Model.createFromMesh(meshes.Fxy.asdf2());
        m[2].getMesh().scale(1e7f);
        m[2].getMesh().translate(new Vector3f(0, 0, -1e9f));
        
        
        Mesh[] meshes = new Mesh[]{
            m[0].getMesh(),
            m[1].getMesh()
        };
        Vector3f[] velocities = new Vector3f[]{
            new Vector3f(0, 0, 0),
            new Vector3f(0, 7.5e3f, 0)
        };
        float[] masses = new float[]{
            5.97e24f,
            7.35e22f
        };
        
        Camera.targetMesh = meshes[0];
        
        sim = new GravSim(meshes, velocities, masses);
        
        
        shader = new Shader("shader");
        tex = new Texture("img.png");
        camera = new Camera(WIDTH, HEIGHT);
    }
    
    
    
    public static float sq(float f){
        return f*f;
    }
    
    public static void closeDisplay(){
        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(win);
        glfwDestroyWindow(win);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
}
