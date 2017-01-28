package lwjgltest;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;



public class UI2D{

    
    private int id, width = 1, height = 1;
    
    public UI2D(){
        this.width = 100;
        this.height = 100;
        id = glGenTextures();
    }
    
    private void draw(Graphics2D g){
        g.setColor(Color.red);
        g.drawString("HEY!!!", 20, 30);
    }
    
    public void render(Shader s){
        
        Matrix4f proj = LWJGLtest.camera.get2DProjection();
        
        s.setUniform("shaderMode", 1);
        
        //LWJGLtest.shader.setUniform("projection", proj);
        
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = img.createGraphics();
        
        draw(g);
        
        int[] pixels_raw = img.getRGB(0, 0, width, height, null, 0, width);

        ByteBuffer pixels = BufferUtils.createByteBuffer(width * height * 4);

        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                int pixel = pixels_raw[i * height + j];
                pixels.put((byte)((pixel >> 16) & 0xFF));   //RED
                pixels.put((byte)((pixel >> 8) & 0xFF));    //GREEN
                pixels.put((byte)(pixel & 0xFF));           //BLUE
                pixels.put((byte)0);//(byte)((pixel >> 24) & 0xFF));   //ALPHA
            }
        }

        pixels.flip();



        glBindTexture(GL_TEXTURE_2D, id);

        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
        
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, id);
        
        glBegin(GL_QUADS);
            glVertex2f(-0.8f, -0.5f);
            glTexCoord2f(0f, 0f);
            glVertex2f(0.8f, -0.5f);
            glTexCoord2f(1f, 0f);
            glVertex2f(0.8f, 0.5f);
            glTexCoord2f(1f, 1f);
            glVertex2f(-0.8f, 0.5f);
            glTexCoord2f(0f, 1f);
        
        glEnd();
    }
}
