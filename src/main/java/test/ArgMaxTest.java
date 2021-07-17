package test;

import bean.Task;
import bean.Worker;
import util.framework.TasksP;
import util.framework.WorkersP;
import util.greedy.GreedyUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class ArgMaxTest {
    public static void main(String[] args) throws IOException {
        //1. 检索第一个时间片内的工人给Wp
        WorkersP wl = new WorkersP();
        ArrayList<Worker> Wp = wl.getWorkersP(1615910400, 600);
        //2. 检索第一个时间片内的任务给Tp
        TasksP tl = new TasksP();
        ArrayList<Task> Tp = tl.getTasksP(1615910400, 600);

        GreedyUtil gUtil = new GreedyUtil();

        //先为第一个任务tj找到有效工人tjWorkers
        Task tj = Tp.get(6);
        System.out.println("第一个任务tj为："+tj);
        ArrayList<Worker> tjWorkers = new ArrayList<>();
        tjWorkers = gUtil.getTjWorkers(tj,Wp);

        //3. 输出查看tj的有效工人
        System.out.println("第一个任务tj的有效工人数量为："+tjWorkers.size());
        System.out.println("工人分别为：");
        for (Worker tjW :
                tjWorkers) {
            System.out.println(tjW);
        }
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        /*//测试argMax
        ArrayList<Worker> tjTeam = new ArrayList<>();
        gUtil.argMax(tjWorkers,tj,tjTeam);*/

        //测试formTeam
        ArrayList<Worker> tjTeam = new ArrayList<>();
        tjTeam = gUtil.formTeam(tjWorkers, tj);
        System.out.println("当前任务团队的工人为：");
        for (Worker w : tjTeam) {
            System.out.println(w);
        }
        boolean satisfy = gUtil.isSkillsSatisfy(tj.getSkills(), tjTeam);
        if (satisfy) {
            System.out.println("当前团队可以完成任务");
        }else {
            System.out.println("当前团队不能完成任务");
        }


    }




}
