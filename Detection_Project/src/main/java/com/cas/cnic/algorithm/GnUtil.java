package com.cas.cnic.algorithm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by ouxuan on 2017/5/22.
 */
public class GnUtil {
    static int authorNum = 5964; //人数
    static int[][] graph = new int[authorNum][authorNum];   //无权图
    static int[][] weight = new int[authorNum][authorNum];  //带权图
    static Map<Double, List<Edge>> map = new HashMap<Double, List<Edge>>();  //Q值及对应删除的边
    List<Edge> list = new ArrayList<Edge>();  //暂时存储一次删除的边
    static int count = 0;  //边数

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
     * 计算带权Graph，有合著边为权值，无合著边为0
     */
    public void calWeight(String inputFile) {

        File file = new File(inputFile);
        try {
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file));
                BufferedReader bf = new BufferedReader(read);
                String linetxt = null;
                while ((linetxt = bf.readLine()) != null) {
                    String[] array = linetxt.split("_");
                    Integer author1 = Integer.parseInt(array[0]);
                    Integer author2 = Integer.parseInt(array[1]);
                    Integer wei = Integer.parseInt(array[2]);
                    weight[author1][author2] = wei;
                    weight[author2][author1] = wei;
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
     * BFS算法，最短路径查找
     */
    public int[][] BFS_minDist(String inputFile) {
        int[][] path = new int[authorNum][authorNum]; //记录两点间最短路径前置

        for (int i = 0; i < authorNum; i++) {  //path矩阵初始化，如果两点相连值为前置，否则为正无穷
            for (int j = 0; j < authorNum; j++) {
                if (graph[i][j] == 1) {
                    path[i][j] = i;
                } else path[i][j] = Integer.MAX_VALUE;
            }
        }

        for (int i = 0; i < authorNum; i++) {
            int[] flag = new int[authorNum]; //记录节点是否被访问
            for (int j = 0; j < authorNum; j++) flag[j] = 1;

            Queue<Integer> queue = new LinkedList<Integer>();
            queue.offer(i);
            flag[i] = 0;
            while (queue.size() != 0) {
                int t = queue.poll();
                for (int k = 0; k < authorNum; k++) {
                    if (graph[t][k] == 1 && flag[k] == 1) {
                        queue.add(k);
                        flag[k] = 0;
                        path[i][k] = t;
                    }
                }

            }
        }
        System.out.println("BFS_minDist end");
        return path;
    }


    /**
     * 计算边介数
     */
    public double[][] calBetweeness(String inputFile) {
        System.out.println("calBetweeness start");
        int[][] path = BFS_minDist(inputFile); //最短路径矩阵

        double[][] betweens = new double[authorNum][authorNum]; //边介数矩阵

        for (int i = 0; i < authorNum; i++) {  //边介数计算，如果不可达为0，否则为边介数
            for (int j = 0; j < authorNum; j++) {
                int t = path[i][j];
                if (t == Integer.MAX_VALUE) {
                    continue;
                } else {
                    betweens[j][t] += 1;
                    while (t != i) {
                        int tmp = path[i][t];
                        betweens[t][tmp] += 1;
                        t = tmp;
                    }

                }
            }
        }
        System.out.println("calBetweeness end");
        return betweens;
    }

    /**
     * 计算除以权值的边介数
     */
    public double[][] calWB(String inputFile) {
        double[][] weibet = calBetweeness(inputFile);//边介数矩阵

        for (int i = 0; i < authorNum; i++) {
            for (int j = 0; j < authorNum; j++) {
                if (weight[i][j] != 0)
                    weibet[i][j] = weibet[i][j] / weight[i][j];
            }
        }
        System.out.println("calWB end");
        return weibet;
    }

    /**
     * 删除最大边介数的边
     */
    public void deleteEdge(String inputFile) {

        list.clear();

        double[][] wbet = calWB(inputFile);

        double max = Double.NEGATIVE_INFINITY;


        for (int i = 0; i < authorNum; i++) {
            for (int j = 0; j < authorNum; j++) {
                if (wbet[i][j] > max) max = wbet[i][j];

            }
        }


        for (int i = 0; i < authorNum; i++) {
            for (int j = 0; j < authorNum; j++) {
                if (wbet[i][j] == max) {
                    count--;
                    graph[i][j] = Integer.MAX_VALUE;
                    weight[i][j] = 0;
                    list.add(new Edge(i, j));
                }
            }
        }
        System.out.println("deleteEdge end");
        System.out.println(max);
    }

    /**
     * 计算Q
     */
    public void calQ() {

        int[] dotweight = new int[authorNum];//点权，每行加和
        double M = 0; //权值和
        double Q = 0;

        for (int i = 0; i < authorNum; i++) {
            for (int j = 0; j < authorNum; j++) {
                dotweight[i] += weight[i][j];
                M += weight[i][j];
            }
        }
        int[] flag = new int[authorNum]; //是否被访问过
        for (int i = 0; i < authorNum; i++) flag[i] = 1;

        Queue<Integer> first = new LinkedList<Integer>(); // 寻找社区
        Queue<Integer> second = new LinkedList<Integer>(); //社区内处理

        System.out.println("deal start");
        for (int i = 0; i < authorNum; i++) {
            first.clear();
            second.clear();
            if (flag[i] == 1) {
                first.add(i);
                flag[i] = 0;
                while (first.size() != 0) {
                    int t = first.poll();
                    second.add(t);
                    for (int j = 0; j < authorNum; j++) {
                        if (graph[t][j] == 1 && flag[j] == 1) {
                            first.add(j);
                            flag[j] = 0;
                        }
                    }
                }
                if(second.size()==1) continue;
                for (int p = 0; p < second.size(); p++) {
                    for (int q = 0; q < second.size(); q++) {
                        Q +=weight[p][q]  - dotweight[p] * dotweight[q] / M;
                    }
                }
            }
        }
        Q = Q / M;
        /*
        System.out.print("init QM");
        if (M != 0) {
            for (int i = 0; i < authorNum; i++) {
                for (int j = 0; j < authorNum; j++) {
                    boolean flag = BFS(i, j);
                    if (flag) Q += weight[i][j] - dotweight[i] * dotweight[j] / M;
                }
                System.out.println("i:" + i);
            }
            System.out.print("cal Q");
            Q = Q / M;*/

        System.out.println("deal end");
        if (!map.containsKey(Q)) {
            List<Edge> ll = new ArrayList<Edge>();
            ll.addAll(list);
            map.put(Q, ll);
        }
    }


    /*
     * BFS
     *
    public boolean BFS(int p, int q) {
        if (p == q) return false;
        int[] flag = new int[authorNum];
        for (int i = 0; i < authorNum; i++) flag[i] = 1;

        Queue<Integer> queue = new LinkedList<Integer>();
        queue.offer(p);
        flag[p] = 0;
        while (queue.size() != 0) {
            int t = queue.poll();
            if (t == q) return true;
            for (int i = 0; i < authorNum; i++) {
                if (graph[t][i] == 1 && flag[i] == 1) {
                    if (i == q) return true;
                    queue.add(i);
                    flag[i] = 0;
                }
            }
        }
        return false;
    } */

}
/*
class Edge {
    int x;
    int y;

    Edge(int x, int y) {
        this.x = x;
        this.y = y;
    }
}*/