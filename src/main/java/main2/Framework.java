package main2;

import bean.Task;
import bean.Worker;
import util.TaskList;
import util.WorkerList;

import java.io.IOException;
import java.util.*;

public class Framework {
    public static void main(String[] args) throws IOException {

        //1. 检索第一个时间片内的工人
        WorkerList wl = new WorkerList();
        ArrayList<Worker> workers = wl.getWorkers(1615910400,600);
        for (Worker w :
                workers) {
            System.out.println(w.toString());
        }
        System.out.println(workers.size());

        //2. 检索第一个时间片内的任务
        TaskList tl = new TaskList();
        ArrayList<Task> tasks = tl.getTasks(1615910400,600);
        for (Task t :
                tasks) {
            System.out.println(t.toString());
        }
        System.out.println(tasks.size());

    }
}









