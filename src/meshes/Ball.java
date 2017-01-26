package meshes;

import java.util.ArrayList;
import org.joml.Vector3f;



public class Ball extends Mesh{
    
    private Ball(ArrayList<Float> vert, ArrayList<Float> text, ArrayList<Integer> ind){
        super(vert, text, ind);
    }
    
    public static Ball makeBall(float r, Vector3f pos, int precision){
        return makeEllipsoid(new Vector3f(r), pos, precision);
    }
        
    public static Ball makeEllipsoid(Vector3f scale, Vector3f pos, int precision){
        
        ArrayList<Float> vert = new ArrayList();
        ArrayList<Float> text = new ArrayList();
        ArrayList<Integer> ind = new ArrayList();
        
        
        float step = (float)Math.PI / precision;
        
        int id = 0;
        
        
        for(int i = 1; i < precision; i++){
            
            for(int j = 0; j < precision; j++){
                
                float z = (float)i * step;
                
                vert.add(f(Math.sin((float)j * step * 2f) * Math.sin(z)));
                vert.add(f(Math.cos((float)j * step * 2f) * Math.sin(z)));
                vert.add(f(Math.cos(z)));
                
                text.add(f(i % 2));
                text.add(f(j % 2));
                
                //upper left
                if(i > 1){
                    
                    int minus = 1;
                    
                    if(j == 0)
                        minus = 1 - precision;
                    
                    ind.add(id);
                    ind.add(id - minus);
                    ind.add(id - precision - minus);

                    ind.add(id);
                    ind.add(id - precision - minus);
                    ind.add(id - precision);
                }
                id++;
            }
            
        }
        
        //low midpoint
        vert.add(0f);
        vert.add(0f);
        vert.add(1f);
        
        //upper midpoint
        vert.add(0f);
        vert.add(0f);
        vert.add(-1f);
        
        //textcoords
        text.add(precision % 2f);
        text.add(1f);
        text.add(precision % 2f);
        text.add(1f);
        
        for(int i = 0; i < precision; i++){
            //lower
            ind.add(id);
            ind.add((i + 1) % precision);
            ind.add(i);
            
            int idd = id - precision;
            
            //upper
            ind.add(id + 1);
            ind.add(idd + i);
            ind.add((i + 1) % precision + idd);
        }
        
        Ball b = new Ball(vert, text, ind);
        b.scale(scale);
        b.setPos(pos);
        return b;
    }
    
    private static float f(double d){
        return (float)d;
    }
}
