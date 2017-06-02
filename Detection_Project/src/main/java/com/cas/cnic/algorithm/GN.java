package com.cas.cnic.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ouxuan on 2017/5/25.
 */
public class GN {

    public static void detect() {
        String inputFile = "F:\\workspace\\IdeaProjects\\CommunityDetection\\Detection_Project\\file\\coor_out.txt";
        GnUtil gnUtil = new GnUtil();
        System.out.println("=====1");
        gnUtil.initGraph(inputFile); //无权图
        System.out.println("=====2");
        gnUtil.calWeight(inputFile); //带权图
        System.out.println("=====3");
        while (gnUtil.count != 0) {
            gnUtil.deleteEdge(inputFile);
            System.out.println("=====4");
            gnUtil.calQ();
            System.out.println("=====5");
            System.out.println(GnUtil.count);
        }
        double max = Double.NEGATIVE_INFINITY;
        for (Double key : gnUtil.map.keySet()) {
            if (key.doubleValue() > max) {
                max = key.doubleValue();
            }
        }
        List<Edge> finalList = gnUtil.map.get(max);
        gnUtil.calWeight(inputFile); //带权图
        System.out.println("=====6");

        if (finalList != null)
            for (int i = 0; i < finalList.size(); i++) {
                int x = finalList.get(i).x;
                int y = finalList.get(i).y;
                GnUtil.weight[x][y] = 0;
            }
//
//        for (int i = 0; i < GnUtil.authorNum; i++) {
//            for (int j = 0; j < GnUtil.authorNum; j++) {
//                if (GnUtil.weight[i][j]!=0)
//                System.out.println(i+","+j+","+GnUtil.weight[i][j]);
//            }
//        }
    }
}
