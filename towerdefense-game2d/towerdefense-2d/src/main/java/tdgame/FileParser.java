/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tdgame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author ilya
 *
 */
public final class FileParser {
    
    String path;//path of the corresponding file
    String name;
    ArrayList<String> content;
    
    /**
     * Consttructor
     * @param p
     * Tells the parser which information it should gather whithin the right text file.
     * Usage : just pass the name of the class from which you want the information
     */
    public FileParser(String p){
        this.path=classdetector(p);
        parser();
    }
    public double get(String param){
        int i=0;
        double res;
        while(i<content.size() && !(content.get(i).contains(param))){
            i++;
        }
        if(i<content.size()){
            res=Double.parseDouble(content.get(i).substring(param.length()));
        }
        else{
            System.err.println("The given parameter is not applicable for the requested class");
            res=-1;
        }
        return res;
    }
    /**
     * Keeps only the data needed as specified by the class name.
     */
    private void parser(){
        int i=0, c=1;
        ArrayList<String> cont=reader();
        content=new ArrayList();
        while(i<cont.size() && !(cont.get(i).equals("name "+name))){
            i++;
        }
        while((i+c)<cont.size() && !(cont.get(i+c).startsWith("name"))){
            content.add(cont.get(i+c));
            c++;
        }
        content.trimToSize();
    }
    /**
     * Read file and write the content into a array of String
     */
    private ArrayList<String> reader(){
        ArrayList<String> temp=new ArrayList();
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = reader.readLine()) != null){
                temp.add(line);
            }
            reader.close();
        }
        catch (IOException e){
            System.err.format("Exception occurred trying to read '%s'.", path);
            e.printStackTrace();
        }
        return temp;
    }

    private String classdetector(String p) {
        String res;
        ArrayList<String> enemies=new ArrayList(4);
        enemies.add("Minion");enemies.add("Skeleton");enemies.add("Mounted");enemies.add("Armored");enemies.add("Boss");
        ArrayList<String> turrets=new ArrayList(6);
        turrets.add("Archer");turrets.add("Poison");turrets.add("Frost");turrets.add("Blaze");turrets.add("Water");
        if(enemies.contains(p)){
            res="ressources/valuesEnemies.txt";
            this.name=p;
        }
        else if(turrets.contains(p)){
            res="ressources/valuesTurrets.txt";
            this.name=p;
        }
        else{
            System.err.format("The class name given ('%s') is not in the known classes", p);
            res=null;
        }
        return res;
    }
    
    
    
}
