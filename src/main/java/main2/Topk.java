package main2;

import bean.State;
import bean.Task;
import bean.Worker;
import util.exactUtil.ExactUtil;
import util.greedy.GreedyUtil;

import java.util.ArrayList;
import java.util.HashSet;

public class Topk {
    public void topk(ArrayList<Worker> Wp, ArrayList<Task> Tp) {

        GreedyUtil gUtil = new GreedyUtil();
        ExactUtil eUtil = new ExactUtil();

        for (Task tj : Tp) {
            double tjCost = 0.0; //任务tj的贪心成本

            //1 计算任务tj贪心算法的成本
            double tjCg = 0.0;
            //1.1 为每个任务tj找到有效工人tjWorkers
            ArrayList<Worker> tjWorkers = gUtil.getTjWorkers(tj,Wp);
            //1.2 为每个任务tj形成团队
            ArrayList<Worker> tjTeam = gUtil.formTeam(tjWorkers, tj);
            //1.3 判断贪心团队是否可以完成任务
            boolean satisfy = gUtil.isSkillsSatisfy(tj.getSkills(), tjTeam);
            if (satisfy) {
                //计算贪心成本tjCg
                for (Worker w : tjTeam) {
                    tjCg += gUtil.distance(tj,w)/10000.0;
                }

                //2. 开始exact算法
                //2.1 计算出当前任务tj的states
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
                //TODO 此处和singleTaskTest中一模一样，singleTaskTest用来测试
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

    }
}
