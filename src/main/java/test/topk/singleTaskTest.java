package test.topk;

import bean.State;
import bean.Task;
import bean.Worker;
import util.exactUtil.ExactUtil;
import util.framework.TasksP;
import util.framework.WorkersP;
import util.greedy.GreedyUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class singleTaskTest {
    public static void main(String[] args) throws IOException {

        GreedyUtil gUtil = new GreedyUtil();
        ExactUtil eUtil = new ExactUtil();


        //1. 检索第一个时间片内的工人给Wp
        WorkersP wl = new WorkersP();
        ArrayList<Worker> Wp = wl.getWorkersP(1615910400,600);

        //2. 检索第一个时间片内的任务给Tp
        TasksP tl = new TasksP();
        ArrayList<Task> Tp = tl.getTasksP(1615910400,600);
        //2.1 得到第一个任务tj
        Task tj = Tp.get(5);

        //3 计算任务tj贪心算法的成本
        double tjCg = 0.0;
        //3.1 为每个任务tj找到有效工人tjWorkers
        ArrayList<Worker> tjWorkers = gUtil.getTjWorkers(tj,Wp);
        //3.2 为每个任务tj形成团队
        ArrayList<Worker> tjTeam = gUtil.formTeam(tjWorkers, tj);
        //3.3 判断团队是否可以完成任务
        boolean satisfy = gUtil.isSkillsSatisfy(tj.getSkills(), tjTeam);
        if (satisfy) {
            System.out.println("此任务可以完成！");
            System.out.println(tj);
            System.out.println("工人分别为：");
            for (Worker w : tjTeam) {
                System.out.println(w);
                tjCg += gUtil.distance(tj,w)/10000.0;
            }
            System.out.println("成本为："+tjCg);
        }
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        //4. 开始exact算法
        HashSet<State> states = new HashSet<>();
        for (Worker tjW: tjWorkers) {
            double pw = gUtil.distance(tj,tjW)/10000.0;
            if (pw<=tjCg) {
                HashSet<State> tmpStates = new HashSet<>();
                HashSet<State> workerStates = eUtil.getWorkerStates(tjW,tj);
                for (State c : workerStates) {
                    //System.out.println(s);
                    for (State s : states) {
                        if ((c.getCost()+s.getCost()) <= tjCg) {
                            //先合并
                            State mergeState = eUtil.mergeState(s, c);
                            //不为空，再插入
                            if ( mergeState!=null ) {
                                tmpStates.add(mergeState);
                            }
                        }
                    }
                    tmpStates.add(c);
                }
                //updare方法
                eUtil.updateStates(states,tmpStates);
            }
        }
        //TODO 现在的思路是把states中合适的state的工人拿出来组队tjExactTeam，然后就可以对接framework
        //2.2 判断states集合中是否有state可以完成任务
        boolean statesOk = eUtil.isStatesOk(states, tj);
        if (statesOk) {
            //2.3 组队
            ArrayList<Worker> tjExactTeam = new ArrayList<>();
            for (Worker w :
                    tjExactTeam) {
                System.out.println(w);
            }

        }



    }
}
