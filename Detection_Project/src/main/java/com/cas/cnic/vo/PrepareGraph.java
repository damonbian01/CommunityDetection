package com.cas.cnic.vo;

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
     * TODO
     *
     * @return
     */
    public static Map<Integer, Set<Integer>> readGraph() {
        Map<Integer, Set<Integer>> coMap = new HashMap<Integer, Set<Integer>>();
        return coMap;
    }

    /**
     * TODO
     *
     * @param inputFile
     */
    public static void prepare(String inputFile, int numOfUsers) {
        /**
         *  key表示文章的id，用存articleid-1，下标归一到0开始
         *  value表示id对应的作者的id列表，存userid-1，下标归一到0开始
         */
        Map<Integer, Set<Integer>> coMap;
        coMap = readGraph();

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
         * TODO
         * 输出矩阵到文件中，得到graph.txt
         */
    }
}
