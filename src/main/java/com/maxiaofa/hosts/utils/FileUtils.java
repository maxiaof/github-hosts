package com.maxiaofa.hosts.utils;

import com.maxiaofa.hosts.RunHosts;

import java.io.*;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * @author MaXiaoFa
 */
public class FileUtils {

    private static final Logger log = Logger.getLogger(RunHosts.class.getName());

    public static String read(File file){
        StringBuilder content = new StringBuilder();
        try (Scanner sc = new Scanner(new FileReader(file.getName()))) {
            while (sc.hasNextLine()) {
                content.append(sc.nextLine())
                        .append("\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    public static void write(File file,String content){
        FileOutputStream fos = null;
        try {
            if(!file.exists()){
                boolean newFile = file.createNewFile();
                if(newFile)log.info("文件不存在正在创建文件...");
            }
            fos = new FileOutputStream(file);
            fos.write(content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                if(null!=fos)fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
