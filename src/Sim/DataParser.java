package Sim;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;



public class DataParser {

    //think of reading the data
    
    //make arraylist into a queue
    
    private LinkedList<Entry> parentStack;
    private Entry rootEntry;
    private int lineNumber = 1;
    
    
    public DataParser(String path) throws Exception{
        StringBuilder s = new StringBuilder();
        BufferedReader br;
        
        rootEntry = new Entry("+root String", null);
        
        parentStack.add(rootEntry);
        
        try{
            br = new BufferedReader(new FileReader(new File(path)));
            String line;
            while((line = br.readLine()) != null){
                parseLine(line);
                lineNumber++;
            }
            
            br.close();
        }catch(IOException e){
            e.printStackTrace(System.err);
        }
    }
    
    private void parseLine(String line) throws Exception{
        line = line.trim();
        if(line.isEmpty() || line.startsWith("#"))
            return;
        
        if(line.equals("**")){
            parentStack.poll();
            return;
        }
        
        Entry e = new Entry(line, parentStack.peek());
        
        parentStack.peek().add(e);
        
        if(e.ismap)
            parentStack.addFirst(e);
        
    }
    
    public enum dataType{
        String,
        Integer,
        Double,
        Boolean
    }
    
    public class Entry{
        final boolean ismap;
        final dataType type;
        LinkedList<Entry> map;
        String content;

        private Entry(String line, Entry parent) throws Exception{
            //starts an array
            if(line.startsWith("+")){
                
                line = line.substring(1);
                
                String[] splitString = line.split(" ");
                
                if(splitString.length != 2){
                    System.err.println("Error while parsing .per file! (line " + lineNumber + ")");
                    throw new Exception("Error while parsing .per file! (line " + lineNumber + ")");
                }
                
                content = splitString[0].trim();
                
                try{
                    type = dataType.valueOf(splitString[1].trim());
                }catch(IllegalArgumentException e){
                    throw new Exception("Following error was thrown when parsing .per file on line " + lineNumber + ": " + e.getMessage());
                }
                
                ismap = true;
                map = new LinkedList<>();
                
            }else{//just an entry
                this.ismap = false;
                content = line;
                type = parent.type;
            }
        }
        
        public void add(Entry e){
            if(ismap)
                map.add(e);
            else
                System.err.println("ASDFQWERGT");
        }
        
        public Double getD(){
            if(!ismap && type == dataType.Double)
                return Double.valueOf(content);
            return null;
        }
        public Integer getI(){
            if(!ismap && type == dataType.Integer)
                return Integer.valueOf(content);
            return null;
        }
        public Boolean getB(){
            if(!ismap && type == dataType.Boolean)
                return Boolean.valueOf(content);
            return null;
        }
        public String getS(){
            if(!ismap && type == dataType.String)
                return content;
            return null;
        }
        
        public boolean isDouble(){
            return type == dataType.Double && !ismap;
        }
        public boolean isBoolean(){
            return type == dataType.Boolean && !ismap;
        }
        public boolean isInt(){
            return type == dataType.Integer && !ismap;
        }
        public boolean isString(){
            return type == dataType.String && !ismap;
        }
    }
}
