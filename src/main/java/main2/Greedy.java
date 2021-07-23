package main2;

import bean.Task;
import bean.Worker;
import util.greedy.GreedyUtil;

import java.util.ArrayList;
import java.util.Iterator;

public class Greedy {

    /**
     * 贪心算法计算形成当前时间片下的任务团队
     * @param Wp 第p个时间片下的可用工人s
     * @param Tp 第p个时间片下的任务s
     */
    public double[] greedy(ArrayList<Worker> Wp,ArrayList<Task> Tp) {

        GreedyUtil gUtil = new GreedyUtil();

        int countYes = 0;
        int countNo = 0;
        double sumEntropy = 0.0;
        double avgEntropy = 0.0;
        double TpCost = 0.0;
        int workerNum = 0;//参与任务的人数量

        //1. 遍历每个任务为其形成团队
        for (Task tj : Tp) {
            double tjCost = 0.0;
            //1.1 为每个任务tj找到有效工人tjWorkers
            ArrayList<Worker> tjWorkers = new ArrayList<>();
            tjWorkers = gUtil.getTjWorkers(tj,Wp);
            //1.2 为每个任务tj形成团队
            ArrayList<Worker> tjTeam = new ArrayList<>();
            tjTeam = gUtil.formTeam(tjWorkers, tj);
            //判断团队是否可以完成任务
            boolean satisfy = gUtil.isSkillsSatisfy(tj.getSkills(), tjTeam);
            if (satisfy) {
                System.out.println(tj);
                countYes++;
                for (Worker w : tjTeam) {
                    System.out.println(w);
                    //如果形成的团队可以完成任务，则把工人移除Wp
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
