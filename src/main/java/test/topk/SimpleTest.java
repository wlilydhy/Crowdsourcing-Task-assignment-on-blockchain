package test.topk;

import bean.Task;
import bean.Worker;
import util.DiversityUtil;
import util.TasksP;
import util.WorkersP;
import util.GreedyUtil;

import java.io.IOException;
import java.util.ArrayList;

public class SimpleTest {
    public static void main(String[] args) throws IOException {

        GreedyUtil gUtil = new GreedyUtil();
        DiversityUtil dUtil = new DiversityUtil();

        //1. 检索第一个时间片内的工人给Wp
        WorkersP wl = new WorkersP();
        ArrayList<Worker> Wp = wl.getWorkersP(1615910400, 600);
        //2. 检索第一个时间片内的任务给Tp
        TasksP tl = new TasksP();
        ArrayList<Task> Tp = tl.getTasksP(1615910400, 600);

        //得到第一个任务tj的有效工人tjWorkers
        Task tj = Tp.get(1);
        ArrayList<Worker> tjWorkers = new ArrayList<>();
        tjWorkers = gUtil.getTjWorkers(tj,Wp);
        //找到id为2917的工人
        Worker w2917 = new Worker();
        for (Worker w : Wp) {
            if (w.getId() == 2917.0) {
                w2917 = w;
            }
        }
        ArrayList<Worker> workers = dUtil.formTeam(tjWorkers, tj, w2917);

        for (Worker w : workers) {
            System.out.println(w);
        }


    }
}
