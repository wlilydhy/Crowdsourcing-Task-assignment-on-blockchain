package test.greedy;

import bean.Task;
import bean.Worker;
import main.Diversity;
import main.Greedy;
import util.TasksP;
import util.WorkersP;

import java.io.IOException;
import java.util.ArrayList;

public class FrameworkTest {
    public static void main(String[] args) throws IOException {

        double startTime = 1615910400; //开始时间片
        double duration = 600; //时间片的时间间隔

        WorkersP wl = new WorkersP();
        TasksP tl = new TasksP();
        Greedy g = new Greedy();
        Diversity d = new Diversity();

        double avgEntropy = 0.0; //平均熵
        double sumYes = 0.0; //任务可以完成的数量
        double sumNo = 0.0; //任务不能完成的数量
        int count = 0; //标记计算了多少个时间片

        //一共是i的范围：0-144
        for (int i = 0; i < 1; i++) {
            double[] doubles = new double[2];
            //1. 检索第i个时间片内的工人给Wp
            ArrayList<Worker> Wp = wl.getWorkersP(startTime, duration);
            //2. 检索第i个时间片内的任务给Tp
            ArrayList<Task> Tp = tl.getTasksP(startTime, duration);
            System.out.println("第"+(i+1)+"个时间片");

            //使用greedy算法
            doubles = g.greedy(Wp, Tp);
            //使用diversity算法
            //doubles = d.diversity(Wp,Tp);

            System.out.println();
            startTime += 600;
            avgEntropy += doubles[0];
            sumYes += doubles[1];
            sumNo += doubles[2];
            count++;
        }
        avgEntropy = avgEntropy/Double.valueOf(count);
        System.out.println("所有时间片下的团队的平均熵为："+avgEntropy);
        System.out.println("所有时间片下可以完成的任务数量为"+sumYes);
        System.out.println("所有时间片下不能完成的任务数量为"+sumNo);

    }
}
