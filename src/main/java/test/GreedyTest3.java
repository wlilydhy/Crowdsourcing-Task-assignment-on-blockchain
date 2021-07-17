package test;

import bean.Task;
import bean.Worker;
import util.framework.TasksP;
import util.framework.WorkersP;
import util.greedy.GreedyUtil;

import java.io.IOException;
import java.util.ArrayList;

public class GreedyTest3 {
    public static void main(String[] args) throws IOException {

        //1. 检索第一个时间片内的工人给Wp
        WorkersP wl = new WorkersP();
        ArrayList<Worker> Wp = wl.getWorkersP(1615910400, 600);
        //2. 检索第一个时间片内的任务给Tp
        TasksP tl = new TasksP();
        ArrayList<Task> Tp = tl.getTasksP(1615910400, 600);

        GreedyUtil gUtil = new GreedyUtil();

        //3. 遍历每个任务为其形成团队
        int i = 1; //标记是第几个团队
        int countYes = 0;
        int countNo = 0;
        for (Task tj : Tp) {
            //3.1 为每个任务tj找到有效工人tjWorkers
            System.out.println("第"+i+"个任务tj为："+tj);
            i++;
            ArrayList<Worker> tjWorkers = new ArrayList<>();
            tjWorkers = gUtil.getTjWorkers(tj,Wp);
            //3.2 为每个任务tj形成团队
            ArrayList<Worker> tjTeam = new ArrayList<>();
            tjTeam = gUtil.formTeam(tjWorkers, tj);

            boolean satisfy = gUtil.isSkillsSatisfy(tj.getSkills(), tjTeam);
            if (satisfy) {
                countYes++;
                System.out.println("YesYesYes");
                System.out.println("当前任务团队的工人为：");
                for (Worker w : tjTeam) {
                    System.out.println(w);
                    Wp.remove(w);
                }
                System.out.println();
            }else {
                countNo++;
                System.out.println("No");
            }
        }
        System.out.println("形成团队的任务个数为："+countYes);
        System.out.println("不能形成团队的任务个数为："+countNo);

    }
}
