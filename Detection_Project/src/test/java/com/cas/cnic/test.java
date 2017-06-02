package com.cas.cnic;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by ouxuan on 2017/5/25.
 */
public class test {
    public static void main(String[] args) {
        try {
            FileWriter fw = new FileWriter("F:\\tt.txt");
            String s = "hello" + "haha" + "\r\n";
            //s = new String("xixi"+"\r\n");
            fw.write(s);
            s = "world";
            fw.write(s);
            s = "pp";
            fw.write(s);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
