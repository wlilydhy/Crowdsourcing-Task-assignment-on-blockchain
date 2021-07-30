package main;

import bean.Task;
import bean.Worker;
import util.TasksP;
import util.WorkersP;

import java.io.IOException;
import java.util.*;

public class Framework {
    public static void main(String[] args) throws IOException {

        double startTime = 1615910400; //开始时间片
        double duration = 600; //时间片的时间间隔

        WorkersP wl = new WorkersP();
        TasksP tl = new TasksP();
        Greedy g = new Greedy();
        Diversity d = new Diversity();
        Topk t = new Topk();

        double avgEntropy = 0.0; //平均熵
        double sumYes = 0.0; //任务可以完成的数量
        double sumNo = 0.0; //任务不能完成的数量
        int count = 0; //标记计算了多少个时间片
        double cost = 0.0;
        double workerNum = 0.0;

        //一共是i的范围：0-144
        for (int i = 0; i < 144; i++) {
            double[] doubles = new double[4];
            //1. 检索第i个时间片内的工人给Wp
            ArrayList<Worker> Wp = wl.getWorkersP(startTime, duration);
            //2. 检索第i个时间片内的任务给Tp
            ArrayList<Task> Tp = tl.getTasksP(startTime, duration);
            System.out.println("第"+(i+1)+"个时间片");

            //使用greedy算法
            //doubles = g.greedy(Wp, Tp);
            //使用diversity算法
            //doubles = d.diversity(Wp,Tp);
            //使用topk算法
            doubles = t.topk(Wp,Tp);

            System.out.println();
            startTime += 600;
            avgEntropy += doubles[0];
            sumYes += doubles[1];
            sumNo += doubles[2];
            cost += doubles[3];
            workerNum +=doubles[4];
            count++;
        }
        avgEntropy = avgEntropy/Double.valueOf(count);
        System.out.println("所有时间片下的团队的平均熵为："+avgEntropy);
        System.out.println("所有时间片下可以完成的任务数量为"+sumYes);
        System.out.println("所有时间片下不能完成的任务数量为"+sumNo);
        System.out.println("总成本："+cost);
        System.out.println("参与任务的人数：" +workerNum);

    }
}









