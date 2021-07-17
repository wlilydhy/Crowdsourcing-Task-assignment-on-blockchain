package test;

import bean.Task;
import bean.Worker;
import main2.Greedy;
import util.framework.TasksP;
import util.framework.WorkersP;

import java.io.IOException;
import java.util.ArrayList;

public class FrameworkTest {
    public static void main(String[] args) throws IOException {

        double startTime = 1615910400;
        double duration = 600;

        WorkersP wl = new WorkersP();
        TasksP tl = new TasksP();
        Greedy g = new Greedy();

        for (int i = 0; i < 50; i++) {
            //1. 检索第i个时间片内的工人给Wp
            ArrayList<Worker> Wp = wl.getWorkersP(startTime, duration);
            //2. 检索第i个时间片内的任务给Tp
            ArrayList<Task> Tp = tl.getTasksP(startTime, duration);
            System.out.println("第"+(i+1)+"个时间片");
            g.greedy(Wp, Tp);
            System.out.println();
            startTime += 600;
        }


    }
}
