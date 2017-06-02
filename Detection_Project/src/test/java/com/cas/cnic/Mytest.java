package com.cas.cnic;

import com.cas.cnic.algorithm.Deal;
import com.cas.cnic.algorithm.FastNewman;
import com.cas.cnic.algorithm.GN;

import java.awt.*;

/**
 * Created by ouxuan on 2017/5/24.
 */
public class Mytest {
    public static void main(String[] args) {
        //GN.detect();
/*
        FastNewman f = new FastNewman();
        String inputFile = "F:\\workspace\\IdeaProjects\\CommunityDetection\\Detection_Project\\file\\coor_out.txt";
        String outputFile = "F:\\workspace\\IdeaProjects\\CommunityDetection\\Detection_Project\\file\\comunity.txt";
        System.out.println("=====start======");
        f.FN(inputFile,outputFile);
        System.out.println("=====end=======");
        */
        String inputFileEdge = "F:\\workspace\\IdeaProjects\\CommunityDetection\\Detection_Project\\file\\coor_out_new.txt";
        //String inputFilePoint = "F:\\workspace\\IdeaProjects\\CommunityDetection\\Detection_Project\\file\\comunity.txt";
        String outputFileEdge = "F:\\workspace\\IdeaProjects\\CommunityDetection\\Detection_Project\\file\\link_new.txt";
        //String outputFilePoint = "F:\\workspace\\IdeaProjects\\CommunityDetection\\Detection_Project\\file\\node.txt";
        Deal d = new Deal();
        //d.dealPoint(inputFilePoint, outputFilePoint);
        d.dealEdge(inputFileEdge, outputFileEdge);
    }

}
