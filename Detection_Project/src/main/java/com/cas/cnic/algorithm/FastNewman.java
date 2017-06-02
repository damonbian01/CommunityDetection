package com.cas.cnic.algorithm;

import java.io.*;
import java.util.*;

/**
 * Created by ouxuan on 2017/5/29.
 */
public class FastNewman {
    static int authorNum = 5964; //人数
    static int[][] graph = new int[authorNum][authorNum];   //无权图

    //static Map<Double, List<Edge>> map = new HashMap<Double, List<Edge>>();  //Q值及对应删除的边
    List<Edge> list = new ArrayList<Edge>();  //存储边
    List<Edge> ll = new ArrayList<Edge>(); //顺序存储边
    static int count = 0;  //边数
    int[] now = new int[authorNum];

    /**
     * 初始化无权Graph，有合著边为1，无合著边为正无穷
     */

    public void initGraph(String inputFile) {
        for (int i = 0; i < authorNum; i++) {
            for (int j = 0; j < authorNum; j++) {
                graph[i][j] = Integer.MAX_VALUE;
            }
        }

        File file = new File(inputFile);
        try {
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file));
                BufferedReader bf = new BufferedReader(read);
                String linetxt = null;
                while ((linetxt = bf.readLine()) != null) {
                    count += 2;
                    String[] arr = linetxt.split("_");
                    Integer author1 = Integer.parseInt(arr[0]);
                    Integer author2 = Integer.parseInt(arr[1]);
                    graph[author1][author2] = 1;
                    graph[author2][author1] = 1;
                    now[author1] = 1;
                }
                read.close();
            } else {
                System.out.print("not find");
            }
        } catch (Exception e) {
            System.out.print("flie error");
            e.printStackTrace();
        }
    }

    /**
     * List初始化
     */
    public void initList() {
        for (int i = 0; i < authorNum; i++) {
            for (int j = i; j < authorNum; j++) {
                if (graph[i][j] == 1) {
                    list.add(new Edge(i, j));
                }
            }
        }
    }


    /**
     * FastNewman算法
     */
    public void FN(String inputFile, String outfile) {
        initGraph(inputFile); //初始化邻接矩阵
        System.out.println("initGraph");
        initList();  //初始化边的List
        System.out.println("initList");

        int edgeNum = list.size();  //边数
        int x;  //边的端点
        int y;  //边的端点

        /* 生成合成边序列 */
        int[] dotweight = new int[authorNum];  //点权
        for (int i = 0; i < edgeNum; i++) {
            double max = Double.NEGATIVE_INFINITY;  //每一轮最大值
            int pos = -1;  //最大值对应下标
            double t;  //Q增量
            for (int j = 0; j < list.size(); j++) {
                x = list.get(j).x;
                y = list.get(j).y;
                t = 1 / count - (dotweight[x] + 1 / count) * (dotweight[y] + 1 / count);
                if (t > max) {
                    max = t;
                    pos = j;
                }
            }
            dotweight[list.get(pos).x] += 1 / count;
            dotweight[list.get(pos).y] += 1 / count;
            ll.add(list.get(pos));
            list.remove(pos);
        }

        System.out.println("ll");

        /*  计算Q值  */
        int[] newdotpoint = new int[authorNum];  //存储点权
        int deletepos = -1; //存储下标
        double maxQ = 0; //存储最大值
        int[][] newGraph = new int[authorNum][authorNum]; //新的邻接矩阵

        Queue<Integer> first = new LinkedList<Integer>(); // 寻找社区
        Queue<Integer> second = new LinkedList<Integer>(); //社区内处理

        for (int i = 0; i < edgeNum; i++) {
            x = ll.get(i).x;
            y = ll.get(i).y;
            newGraph[x][y] = 1;
            newGraph[y][x] = 1;
            newdotpoint[x]++;
            newdotpoint[y]++;
            int Q = 0;

            int[] flag = new int[authorNum]; //是否被访问过
            for (int j = 0; j < authorNum; j++) flag[j] = 1;

            for (int w = 0; w < authorNum; w++) {
                first.clear();
                second.clear();
                if (flag[w] == 1) {
                    first.add(w);
                    flag[w] = 0;
                    while (first.size() != 0) {
                        int t = first.poll();
                        second.add(t);
                        for (int v = 0; v < authorNum; v++) {
                            if (newGraph[t][v] == 1 && flag[v] == 1) {
                                first.add(v);
                                flag[v] = 0;
                            }
                        }
                    }
                    if (second.size() == 1) continue;
                    for (int p = 0; p < second.size(); p++) {
                        for (int q = 0; q < second.size(); q++) {
                            Q += newGraph[p][q] - newdotpoint[p] * newdotpoint[q] / count;
                        }
                    }
                }
            }

            if (Q > maxQ) {
                maxQ = Q;
                deletepos = i;
            }

            System.out.println("i:" + i);
        }

        System.out.println("calQ");

        int[][] finalMat = new int[authorNum][authorNum];
        if (deletepos != -1) {
            for (int i = 0; i <= deletepos; i++) {
                x = ll.get(i).x;
                y = ll.get(i).y;
                finalMat[x][y] = 1;
                finalMat[y][x] = 1;
            }
        }
        System.out.print("finalMat");

        /*处理成需要的格式*/
        int[] finalflag = new int[authorNum];
        int comunity = -1;

        for (int i = 0; i < authorNum; i++) finalflag[i] = 1;

        try {
            FileWriter fw = new FileWriter(outfile);
            for (int w = 0; w < authorNum; w++) {
                first.clear();
                second.clear();
                if (finalflag[w] == 1 && now[w] == 1) {
                    comunity++;
                    first.add(w);
                    finalflag[w] = 0;
                    while (first.size() != 0) {
                        int t = first.poll();
                        String s = new String("" + t + "_" + comunity + "\n");
                        fw.write(s);
                        for (int v = 0; v < authorNum; v++) {
                            if (finalMat[t][v] == 1 && finalflag[v] == 1) {
                                first.add(v);
                                finalflag[v] = 0;
                            }
                        }
                    }
                }
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("comunity_out");
    }
}
