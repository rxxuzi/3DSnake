package write;

import java.io.File;
import java.util.ArrayList;

public class Clean {
    public static void main(String[] args) {
        String path = "./rsc/log/data.log";
        String dirPath = "./rsc/log/";

        //Clean
        File file = new File(path);
        if(file.delete()){
            System.out.println("file deleted");
        }else {
            System.out.println("file not deleted");
        }

    }

    public static void main(ArrayList<String> args) {

    }
}

