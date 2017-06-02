package com.cas.cnic.algorithm;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ouxuan on 2017/5/31.
 */
public class Deal {
    public void dealPoint(String inputFile, String outfile) {
        System.out.println("point deal start");
        File file = new File(inputFile);
        try {
            FileWriter fw = new FileWriter(outfile);
            try {
                if (file.isFile() && file.exists()) {
                    InputStreamReader read = new InputStreamReader(new FileInputStream(file));
                    BufferedReader bf = new BufferedReader(read);
                    String linetxt = null;
                    while ((linetxt = bf.readLine()) != null) {
                        String[] arr = linetxt.split("_");
                        Integer author = Integer.parseInt(arr[0]);
                        Integer comunity = Integer.parseInt(arr[1]);
                        String s = new String("{\"id\":\"" + author + "\",\"group\":" + comunity + "}," + "\n");
                        fw.write(s);
                    }
                    read.close();
                } else {
                    System.out.print("not find");
                }
            } catch (Exception e) {
                System.out.print("flie error");
                e.printStackTrace();
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("point deal end");
    }

    public void dealEdge(String inputFile, String outfile) {
        System.out.println("link deal start");
        File file = new File(inputFile);
        try {
            FileWriter fw = new FileWriter(outfile);
            try {
                if (file.isFile() && file.exists()) {
                    InputStreamReader read = new InputStreamReader(new FileInputStream(file));
                    BufferedReader bf = new BufferedReader(read);
                    String linetxt = null;
                    while ((linetxt = bf.readLine()) != null) {
                        String[] arr = linetxt.split("_");
                        Integer author1 = Integer.parseInt(arr[0]);
                        Integer author2 = Integer.parseInt(arr[1]);
                        Integer weight = Integer.parseInt(arr[2]);
                        //String s = new String("{\"source\":\"" + author1 + "\",\"target\":\"" + author2 + "\",\"value\":" + weight + "}," + "\n");
                        String s = new String("{\"source\":\"" + author1 + "\",\"target\":\"" + author2 + "\",\"value\":" +  "1}," + "\n");
                        fw.write(s);
                    }
                    read.close();
                } else {
                    System.out.print("not find");
                }
            } catch (Exception e) {
                System.out.print("flie error");
                e.printStackTrace();
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("link deal end");
    }
}
