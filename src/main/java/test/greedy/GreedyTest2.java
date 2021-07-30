package test.greedy;

import bean.Task;
import bean.Worker;
import main.Greedy;
import util.TasksP;
import util.WorkersP;
import util.GreedyUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class GreedyTest2 {
    public static void main(String[] args) throws IOException {
        /*//1. 检索第一个时间片内的工人给Wp
        WorkersP wl = new WorkersP();
        ArrayList<Worker> Wp = wl.getWorkersP(1615910400,600);
        System.out.println(Wp.size());
        System.out.println(Wp.get(0));*/

        GreedyUtil gUtil = new GreedyUtil();
        Greedy g = new Greedy();

        //1. 检索第一个时间片内的工人给Wp
        WorkersP wl = new WorkersP();
        ArrayList<Worker> Wp = wl.getWorkersP(1615910400,600);
        //2. 检索第一个时间片内的任务给Tp
        TasksP tl = new TasksP();
        ArrayList<Task> Tp = tl.getTasksP(1615910400,600);

        //3. 先为第一个任务tj找到所有有效工人tjWorkers
        Task tj = Tp.get(0);
        ArrayList<Worker> tjWorkers = new ArrayList<>();
        for (Worker wi : Wp) {
            Iterator<Double> iSkills = wi.getSkills().iterator();
            while ( iSkills.hasNext() ) {
                //约束1：工人技能是任务所需的
                if (gUtil.isNeededByTask(iSkills.next(),tj)){
                    //约束2：工人在任务范围内
                    //先计算工人和任务的距离
                    long distance = GreedyUtil.calculateTheDistance(wi.getLongitude(), wi.getLatitude(), tj.getLongitude(), tj.getLatitude());
                    //System.out.println(distance);
                    if (distance<8010000) {
                        tjWorkers.add(wi);
                    }
                }
            }
        }




    }
}
