package com.cas.cnic;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by ouxuan on 2017/5/22.
 * <p>
 * 生成graph的描述文件,格式如下
 * startid-endid-weight
 */
public class PrepareGraph {

    /**
     * @param inputFile
     * @return
     */
    public static Map<Integer, Set<Integer>> readGraph(String inputFile) {
        Map<Integer, Set<Integer>> coMap = new HashMap<Integer, Set<Integer>>();

        File file = new File(inputFile);
        try {
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file));
                BufferedReader bf = new BufferedReader(read);
                String linetxt = null;
                while ((linetxt = bf.readLine()) != null) {
                    String[] arr = linetxt.split("\\|");
                    Integer key = Integer.parseInt(arr[2]) - 1;
                    Integer name = Integer.parseInt(arr[1]) - 1;
                    if (coMap.containsKey(key)) {
                        Set<Integer> users = coMap.get(key);
                        users.add(name);
                        coMap.put(key, users);
                    } else {
                        coMap.put(key, new HashSet<Integer>(name));
                    }
                }
                read.close();
            } else {
                System.out.print("not find");
            }
        } catch (Exception e) {
            System.out.print("flie error");
            e.printStackTrace();
        }
        return coMap;
    }

    /**
     * TODO
     *
     * @param inputFile
     */
    public static void prepare(String inputFile) {
        int numOfUsers = 5964;
        /**
         *  key表示文章的id，用存articleid-1，下标归一到0开始
         *  value表示id对应的作者的id列表，存userid-1，下标归一到0开始
         */
        Map<Integer, Set<Integer>> coMap;
        coMap = readGraph(inputFile);

        /**
         * matrix[i][j] = w；表示i和j合著了w篇文章
         */
        int[][] matrix = new int[numOfUsers][numOfUsers];
        for (int i = 0; i < coMap.size(); ++i) {
            Set<Integer> tmp = coMap.get(i);
            int authorSize = tmp.size();
            /**
             * 一篇文章至少有两个作者才能体现合作关系
             * 两两组合都有合作的边
             */
            if (authorSize > 2) {
                for (Integer j : tmp) {
                    for (Integer k : tmp) {
                        if (j.equals(k)) continue;
                        else matrix[j][k] += 1;
                    }
                }
            }
        }

        /**
         * 输出矩阵到文件中，得到graph.txt
         */
        String out = inputFile.substring(0, inputFile.length() - 4) + "_out.txt";
        writeGraph(matrix, out);

    }

    public static void writeGraph(int[][] matrix, String outfile) {
        try {
            FileWriter fw = new FileWriter(outfile);
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    if (matrix[i][j] == 0) continue;

                    String s = new String("" + i + "_" + j + "_" + matrix[i][j] + "\n");
                    fw.write(s);
                }
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
