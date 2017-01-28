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

    public static final float uniformScalingFactor = 1e-8f;
    static final int WIDTH = 1600, HEIGHT = 900;
    
    
    private static long win;
    
    private static Model m[], m0;
    
    static Camera camera;
    static Shader shader;
    //private static UI2D ui;
    
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
        
        
        
        //keep base under m[0]
        //m0.getMesh().setPos(m[1].getMesh().getRawPos().add(new Vector3f(0, 0, -1e9f), new Vector3f()));
        
        
        glClear(GL_COLOR_BUFFER_BIT);//init screen to bg color
        
        shader.bind();
        
        Actions.applyEvents(win);
        
        //m0.render(shader);
        
        
        for(int i = 0; i < m.length; i++)
            m[i].render(shader, i);
        
        //glDisable(GL_DEPTH_TEST);

        //ui.render(shader);
        
        //glEnable(GL_DEPTH_TEST);
        
        glfwSwapBuffers(win);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); 
        
        
        // Poll for window events. The key callback above will only be
        // invoked during this call.
        glfwPollEvents();
        
    }
    private static void init(){
        
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
        
        glEnable(GL_TEXTURE_2D);
        
        glEnable(GL_DEPTH_TEST);
        glDepthMask(true);
        glDepthFunc(GL_LEQUAL);
//        glFrontFace(GL_CW);
//        glEnable(GL_CULL_FACE);
        glDisable(GL_DITHER);
        
        //BG color
        glClearColor(0, 0, 0, 1);
        
        
        m0 = Model.createFromMesh(meshes.Fxy.asdf2(), 0);
        m0.getMesh().scale(1e7f);
        m0.getMesh().translate(new Vector3f(0, 0, -1e9f));
        
        
        sim = new GravSim();
        m = sim.getModels();
        
        shader = new Shader("shader");
        
        camera = new Camera(WIDTH, HEIGHT);
        
        //ui = new UI2D();
        
        
        
        new Thread(sim).start();
    }
    
    public static Mesh getMesh(int i){
        return m[i % m.length].getMesh();
    }
    
    
    
    private static void closeDisplay(){
        
        sim.stop();
        
        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(win);
        glfwDestroyWindow(win);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
}
