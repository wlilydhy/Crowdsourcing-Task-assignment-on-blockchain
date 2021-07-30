package main;

import bean.State;
import bean.Task;
import bean.Worker;
import util.ExactUtil;
import util.GreedyUtil;

import java.util.ArrayList;
import java.util.HashSet;


public class Topk {

    /**
     * topk算法计算形成当前时间片下的任务团队
     * @param Wp 第p个时间片下的可用工人s
     * @param Tp 第p个时间片下的任务s
     * @return double[5]数组，
     * doubles[0] : 任务平均熵
     * doubles[1] : 任务的完成数量
     * doubles[2] : 任务不能完成的数量
     * doubles[3] : 任务总成本
     * doubles[4] : 任务参与人数
     */
    public double[] topk(ArrayList<Worker> Wp, ArrayList<Task> Tp) {

        GreedyUtil gUtil = new GreedyUtil();
        ExactUtil eUtil = new ExactUtil();

        int countYes = 0;
        int countNo = 0;
        double sumEntropy = 0.0;
        double avgEntropy = 0.0;
        double TpCost = 0.0;
        int workerNum = 0;//参与任务的人数量

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
                //2.2 判断states集合中是否有state可以完成任务
                boolean statesOk = eUtil.isStatesOk(states, tj);
                if (statesOk) {
                    System.out.println(tj);
                    countYes++;
                    //2.3 组队
                    ArrayList<Worker> tjExactTeam = new ArrayList<>();
                    tjExactTeam = eUtil.formTeam(states,tj);
                    for (Worker w : tjExactTeam) {
                        System.out.println(w);
                        Wp.remove(w);
                        tjCost += gUtil.distance(tj,w)/10000.0;
                    }
                    //计算当前团队的人数
                    workerNum += tjTeam.size();
                    //计算当前团队的cost
                    TpCost += tjCost;
                    //计算当前团队的熵
                    double entropy = gUtil.entropy(tjTeam);
                    sumEntropy += entropy;
                    System.out.println("此团队的熵："+entropy);
                    System.out.println();
                }else {
                    countNo++;
                }
            } else {
                countNo++;
            }
        }
        if (countYes != 0) {
            avgEntropy = sumEntropy/Double.valueOf(countYes);
        }
        System.out.println("形成团队的任务个数为："+countYes);
        System.out.println("不能形成团队的任务个数为："+countNo);
        System.out.println("当前时间片的平均熵："+avgEntropy);

        double[] doubles = new double[5];
        doubles[0] = avgEntropy;
        doubles[1] = countYes;
        doubles[2] = countNo;
        doubles[3] = TpCost;
        doubles[4] = (double) workerNum;
        return doubles;
    }
}
