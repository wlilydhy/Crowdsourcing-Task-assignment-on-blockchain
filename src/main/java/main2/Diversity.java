package main2;

import bean.Task;
import bean.Worker;
import util.diversity.DiversityUtil;
import util.greedy.GreedyUtil;

import java.util.ArrayList;

public class Diversity {
    /**
     * 此算法与贪心算法最大的不同在于：41-64行，
     * 当之前的贪心算法为一个任务tj形成的团队可以满足任务技能要求时：
     * 再继续判断此团队的熵是否为0，如果为0则重新组队，
     * 重新组队后的团队熵必不为0，这时再判断是否能满足任务技能要求，满足则组队成功否则组队失败
     * @param Wp 第p个时间片下的可用工人s
     * @param Tp 第p个时间片下的任务s
     */
    public double[] diversity(ArrayList<Worker> Wp, ArrayList<Task> Tp) {

        GreedyUtil gUtil = new GreedyUtil();
        DiversityUtil dUtil = new DiversityUtil();

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
            //此出判断团队是否可以完成任务
            boolean satisfy = gUtil.isSkillsSatisfy(tj.getSkills(), tjTeam);
            if (satisfy) {
                //计算当前团队的熵
                double entropy = gUtil.entropy(tjTeam);
                if (entropy == 0.0) { //如果熵为0，则不满足要求，重新组队
                    //重新组队时，只保留当前团队的第一个成员tjTeam.get(0)
                    tjTeam = dUtil.formTeam(tjWorkers,tj,tjTeam.get(0));
                    boolean satisfy2 = gUtil.isSkillsSatisfy(tj.getSkills(), tjTeam);
                    if (satisfy2) {
                        System.out.println(tj);
                        countYes++;
                        //System.out.println(entropy);
                        sumEntropy += entropy;
                        //如果新形成的团队可以完成任务且熵不为0，则把工人移除Wp
                        for (Worker w : tjTeam) {
                            System.out.println(w);
                            Wp.remove(w);
                            tjCost += gUtil.distance(tj,w)/10000.0;
                        }
                        //计算当前团队的人数
                        workerNum += tjTeam.size();
                        //计算当前团队的cost
                        TpCost += tjCost;
                        System.out.println("此团队的熵："+entropy);
                        System.out.println();
                    }else {
                        countNo++;
                    }
                }else { //如果熵不为0，则可以形成团队
                    System.out.println(tj);
                    countYes++;
                    //System.out.println(entropy);
                    sumEntropy += entropy;
                    //如果形成的团队可以完成任务且熵不为0，则把工人移除Wp
                    for (Worker w : tjTeam) {
                        System.out.println(w);
                        Wp.remove(w);
                        tjCost += gUtil.distance(tj,w)/10000.0;
                    }
                    //计算当前团队的人数
                    workerNum += tjTeam.size();
                    //计算当前团队的cost
                    TpCost += tjCost;
                    System.out.println("此团队的熵："+entropy);
                    System.out.println();
                }
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
