package main2;

import bean.Task;
import bean.Worker;
import util.framework.TasksP;
import util.framework.WorkersP;

import java.io.IOException;
import java.util.*;

public class Framework {
    public static void main(String[] args) throws IOException {

        //1. 检索第一个时间片内的工人给Wp
        WorkersP wl = new WorkersP();
        ArrayList<Worker> Wp = wl.getWorkersP(1615910400,600);
        for (Worker wi :
                Wp) {
            System.out.println(wi.toString());
        }
        System.out.println(Wp.size());

        //2. 检索第一个时间片内的任务给Tp
        TasksP tl = new TasksP();
        ArrayList<Task> Tp = tl.getTasksP(1615910400,600);
        for (Task tj :
                Tp) {
            System.out.println(tj.toString());
        }
        System.out.println(Tp.size());

        //3. 为每个任务tj匹配合适的工人，形成工人任务对


    }
}









