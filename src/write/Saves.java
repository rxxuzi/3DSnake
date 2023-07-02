package write;

import java.awt.*;
import java.io.*;

public final class Saves {
    private int x;
    private int y;
    private int z;

    private File file;

    public Saves(String str){
        this.file = new File(str);
    }

    public void load(){
        FileReader fr = null;
        try {
            fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String str = br.readLine();
            String[] data = str.split(",");
            x = Integer.parseInt(data[1]);
            y = Integer.parseInt(data[2]);
            z = Integer.parseInt(data[3]);
            System.out.println(x + " " + y + " " + z);
            fr.close();
        }catch (IOException e){
            e.printStackTrace();
            Error.write(e);
        }
    }

    public void write(String data ,Color c){
        FileWriter fw;
        String nc = c.toString();
        nc = nc.replaceAll("java.awt.Color" , "");
        // remove "java.awt.Color" from the string
        if(file != null){
            try {
                fw = new FileWriter(file , true);
                fw.write(data +","+ nc + "\n");
                fw.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}