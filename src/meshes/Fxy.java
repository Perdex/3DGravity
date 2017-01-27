package meshes;

import java.util.ArrayList;



public class Fxy {
    
    public static Mesh asdf(){
        return build((float x, float y) -> 
                5 * x * y / (Math.abs(x) + Math.abs(y)) / (Math.abs(x) + Math.abs(y) + 1)
        );
    }
    
    public static Mesh asdf2(){
        return build((float x, float y) -> 
                -Math.cos((x*x + y*y)/10) / (0.25 + (x*x + y*y) / 30)// + y*y / 30 + x*x / 30
        );
    }

    private static Mesh build(f f){
        ArrayList<Float> vert = new ArrayList();
        ArrayList<Float> text = new ArrayList();
        ArrayList<Integer> ind = new ArrayList();
        
        float inc = 0.2f, range = 25f;
        int n = (int)(range / inc) + 1;
        
        int id = 0;
        
        for(float i = 0; i < n; i++){
            
            for(int j = 0; j < n; j++){
                
                float x = i * inc - range/2;
                float y = j * inc - range/2;
                
                //coordinates
                vert.add(x); //X
                vert.add(-y); //Y
                vert.add((float)f.z(x, y));
                
                //textures
                text.add((float)(i % 2));
                text.add((float)(j % 2));
//                
//                //connect as triangles
//                if(i % 2 == 0 && j % 2 == 0){
                    //upper left
                    if(i > 0 && j > 0){
                        ind.add(id);
                        ind.add(id - n - 1);
                        ind.add(id - 1);
                        
                        ind.add(id);
                        ind.add(id - n);
                        ind.add(id - n - 1);
                    }
//                    //upper right
//                    if(i > 0 && j < n-1){
//                        ind.add(id);
//                        ind.add(id - n);
//                        ind.add(id - n + 1);
//                        
//                        ind.add(id);
//                        ind.add(id - n + 1);
//                        ind.add(id + 1);
//                    }
//                    //lower right
//                    if(i < n - 1 && j < n - 1){
//                        ind.add(id);
//                        ind.add(id + 1);
//                        ind.add(id + n + 1);
//                        
//                        ind.add(id);
//                        ind.add(id + n + 1);
//                        ind.add(id + n);
//                    }
//                    //lower left
//                    if(i < n - 1 && j > 0){
//                        ind.add(id);
//                        ind.add(id + n);
//                        ind.add(id + n - 1);
//                        
//                        ind.add(id);
//                        ind.add(id + n - 1);
//                        ind.add(id - 1);
//                    }
//                    
//                }
                
                id++;
            }
        }
        return new Mesh(vert, text, ind);
    }
    
    private interface f{
        double z(float x, float y);
    }
}
