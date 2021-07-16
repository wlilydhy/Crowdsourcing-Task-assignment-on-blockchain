package test;

import bean.Task;
import bean.Worker;
import util.framework.TasksP;
import util.framework.WorkersP;
import util.greedy.GreedyUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;


public class GreedyTest {
    public static void main(String[] args) throws IOException {
        /*framework 部分*/
        //1. 检索第一个时间片内的工人给Wp
        WorkersP wl = new WorkersP();
        ArrayList<Worker> Wp = wl.getWorkersP(1615910400,600);
        System.out.println("第一个时间片内的工人数量"+Wp.size());
        /*for (Worker wi :
                Wp) {
            System.out.println(wi.toString());
        }
        System.out.println(Wp.size());*/
        //2. 检索第一个时间片内的任务给Tp
        TasksP tl = new TasksP();
        ArrayList<Task> Tp = tl.getTasksP(1615910400,600);
        /*for (Task tj :
                Tp) {
            System.out.println(tj.toString());
        }
        System.out.println(Tp.size());*/
        //3. 为每个任务tj匹配合适的工人，形成工人任务对(这部分就不麻烦了，还是放在Greedy中比较方便)

        /*Greedy算法开始*/
        //1. 先为第一个任务找到有效工人
        GreedyUtil gUtil = new GreedyUtil();
        //Task tj = Tp.get(1);
        //System.out.println(tj);
        //System.out.println();
        int x=1;
        for (Task tj : Tp) {
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
            //输出查看tj的有效工人
        /*for (Worker tjW :
                tjWorkers) {
            System.out.println(tjW);
        }*/
            //输出查看当前时间片内每个任务的有效工人数量
            System.out.println("t"+x+"的有效工人数量"+tjWorkers.size());
            x++;

            //此时得到了每个任务tj的有效工人，保存在tjWorkers中，为每个任务tj挑选最合适的工人并组成团队完成此任务




        }




    }
}












