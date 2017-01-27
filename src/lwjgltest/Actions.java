package lwjgltest;

import org.joml.Vector3f;
import static org.lwjgl.glfw.GLFW.*;

public class Actions{
//    private static float zoom = 1.2f, x = 0.5f, y = 0;
    public static void applyEvents(long win){
        
        final int[] transKeys = new int[]{
            GLFW_KEY_A, GLFW_KEY_D, GLFW_KEY_LEFT_CONTROL, GLFW_KEY_LEFT_SHIFT, GLFW_KEY_W, GLFW_KEY_S
        };
        final int[] rotKeys = new int[]{
            GLFW_KEY_UP, GLFW_KEY_DOWN, GLFW_KEY_LEFT, GLFW_KEY_RIGHT, GLFW_KEY_Q, GLFW_KEY_E
        };
        
        
        for(int i = 0; i < 6; i++){
            //go through translating keys
            if(glfwGetKey(win, transKeys[i]) == GLFW_PRESS)
                LWJGLtest.camera.translate((2*(i%2)-1), i / 2);
            
            //go through rotating keys
            if(glfwGetKey(win, rotKeys[i]) == GLFW_PRESS)
                LWJGLtest.camera.rotate((2*(i%2)-1) * 0.05f, i / 2);
            
        }
        
//        shader.setUniform("zoom", zoom);
//        shader.setUniform("xCenter", x);
//        shader.setUniform("yCenter", y);
    }
    
    private static double lastx = 0, lasty = 0;
    private static boolean rot = false;

    public static void addCallbacks(long win){
        
        
        glfwSetCursorPosCallback(win, (window, mousex, mousey) -> {
            if(rot && lastx != 0 && lasty != 0){
                LWJGLtest.camera.rotate((float)(mousex - lastx) / 100f, 1);
                LWJGLtest.camera.rotate((float)(mousey - lasty) / 100f, 0);
            }
            lastx = mousex;
            lasty = mousey;
            
//            Vector3f pos = LWJGLtest.camera.getPosition();
//            Vector3f dir = LWJGLtest.camera.getNormal();
            
            
            //LWJGLtest.shader.setUniform("LightPosition", pos.x + dir.x * pos.z / dir.z, pos.y + dir.y * pos.z / dir.z, 0);
            
            //System.out.println((pos.x + dir.x * pos.z / dir.z) + ",     " + (pos.y + dir.y * pos.z / dir.z));
        });
        
        glfwSetMouseButtonCallback(win, (window, button, action, mods) -> {
            if(button == GLFW_MOUSE_BUTTON_1)
                rot = !rot;
            if(button == GLFW_MOUSE_BUTTON_2 && action == GLFW_PRESS){
                Camera.targetMesh++;
            }
        });
        
        glfwSetScrollCallback(win, (window, dx, dy) -> {
            LWJGLtest.camera.translateFree((float)dy * -2f, 2);
        });
//            
//            double dz = Math.pow(1.2, dy / 3);
//            zoom /= dz;
//            shader.setUniform("zoom", zoom);
//            shader.setUniform("maxIterations", (float)(Math.max(0, -Math.log(zoom) * 50.0)));
//            
//            DoubleBuffer xpos = BufferUtils.createDoubleBuffer(1);
//            DoubleBuffer ypos = BufferUtils.createDoubleBuffer(1);
//            glfwGetCursorPos(win, xpos, ypos);
//            double temp = xpos.get();
//            x += (temp/WIDTH * 2 - 1) * (1 - dz) * zoom;
//            y -= (ypos.get()/HEIGHT * 2 - 1) * (1 - dz) * zoom;
//            
//            
//            shader.setUniform("xCenter", x);
//            shader.setUniform("yCenter", y);
//            
//            
//        });
        
        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(win, (window, key, scancode, action, mods) -> {
                if(action == GLFW_RELEASE && key == GLFW_KEY_ESCAPE)
                    glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
                
                
        });
    }
}
