package main;

import bean.Task;
import bean.Worker;
import util.GreedyUtil;
import util.TaskAssignUtil;

import java.util.ArrayList;

public class TaskAssignment {

    /**
     * TaskAssignment算法。
     * 遍历当前时间片下的任务，对比第i个和第i+1个任务。
     * 如果有冲突的工人，则把冲突的工人分配到给任务带来性价比高的团队，每次只确定第i个任务，
     * 就算冲突的工人分给了第i+1个任务，那也不能确定第i+1个任务的团队，
     * 没有到第i+1个任务的轮次，下一次遍历才能确定它
     * @param Wp 第p个时间片下的可用工人s
     * @param Tp 第p个时间片下的任务s
     * @return double[5]数组，
     * doubles[0] : 任务平均熵
     * doubles[1] : 任务的完成数量
     * doubles[2] : 任务不能完成的数量
     * doubles[3] : 任务总成本
     * doubles[4] : 任务参与人数
     */
    public double[] taskAssign(ArrayList<Worker> Wp, ArrayList<Task> Tp) {

        TaskAssignUtil tUtil = new TaskAssignUtil();
        GreedyUtil gUtil = new GreedyUtil();

        int countYes = 0;
        int countNo = 0;
        double sumEntropy = 0.0;
        double avgEntropy = 0.0;
        double TpCost = 0.0;
        int workerNum = 0;//参与任务的人数量

        //找到此时间片下有可能完成的任务：即不考虑冲突时存在团队完成此任务
        ArrayList<Task> Tpc = new ArrayList<>();
        for (Task tj : Tp) {
            //为每个任务tj找到有效工人tjWorkers
            ArrayList<Worker> tjWorkers = gUtil.getTjWorkers(tj,Wp);
            //为每个任务tj形成团队
            ArrayList<Worker> tjTeam = gUtil.formTeam(tjWorkers, tj);
            //判断团队是否可以完成任务
            boolean satisfy = gUtil.isSkillsSatisfy(tj.getSkills(), tjTeam);
            if (satisfy) {
                Tpc.add(tj);
            }else {
                countNo++;
            }
        }
        //每次对第i个和第i+1个任务组队，两两比较，解决冲突，但每次只确定第i个任务
        for (int i = 0; i < Tpc.size(); i++) {
            double tjCost = 0.0;
            //如果是最后一个任务，则没有其他冲突任务，如果能组队就组队，不行就算了
            if (i==Tpc.size()-1) {
                Task tj = Tpc.get(i);
                //为每个任务tj找到有效工人tjWorkers
                ArrayList<Worker> tjWorkers = gUtil.getTjWorkers(tj,Wp);
                //为每个任务tj形成团队
                ArrayList<Worker> tjTeam = gUtil.formTeam(tjWorkers, tj);
                //判断团队是否可以完成任务
                boolean satisfy = gUtil.isSkillsSatisfy(tj.getSkills(), tjTeam);
                if (satisfy) {
                    countYes++;
                    System.out.println("5、最后一个任务");
                    System.out.println(tj);
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
                break;
            }
            /*为第i个任务组队*/
            Task tj = Tpc.get(i);
            //为每个任务tj找到有效工人tjWorkers
            ArrayList<Worker> tjWorkers = gUtil.getTjWorkers(tj,Wp);
            //为每个任务tj形成团队
            ArrayList<Worker> tjTeam = gUtil.formTeam(tjWorkers, tj);
            /*为第i+1个任务组队*/
            Task tj2 = Tpc.get(i+1);
            //为每个任务tj找到有效工人tjWorkers
            ArrayList<Worker> tj2Workers = gUtil.getTjWorkers(tj2,Wp);
            //为每个任务tj形成团队
            ArrayList<Worker> tj2Team = gUtil.formTeam(tj2Workers, tj2);
            boolean tjSatisfy = gUtil.isSkillsSatisfy(tj.getSkills(), tjTeam);
            boolean tj2Satisfy = gUtil.isSkillsSatisfy(tj2.getSkills(), tj2Team);
            if (tjSatisfy) {
                if (tj2Satisfy) {//如果两个团队都可以完成相应的任务，则判断是否冲突
                    /*判断这两个团队是否有冲突*/
                    if (tUtil.isConflict(tjTeam,tj2Team)) { //有冲突
                        //解决冲突
                        tUtil.reconcile(tj,tjTeam,tjWorkers,tj2,tj2Team,tj2Workers);
                        //解决冲突后再判断tj能否完成任务
                        boolean tjjSatisfy = gUtil.isSkillsSatisfy(tj.getSkills(), tjTeam);
                        if (tjjSatisfy) {//可以完成任务
                            countYes++;
                            System.out.println("1、解决冲突后可以完成的任务");
                            System.out.println(tj);
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
                        }else {//再组队，再判断
                            tUtil.formTeamAgain(tj, tjTeam, tjWorkers);
                            boolean tjjjSatisfy = gUtil.isSkillsSatisfy(tj.getSkills(), tjTeam);
                            if (tjjjSatisfy) {
                                countYes++;
                                System.out.println("2、解决冲突后不能完成任务，再组队可以完成的任务");
                                System.out.println(tj);
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
                    }else { //无冲突，确定第i个任务
                        countYes++;
                        System.out.println("3、无冲突就可以完成的任务");
                        System.out.println(tj);
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
                    }
                }else {//只有tjTeam可以完成任务，则确认此团队
                    countYes++;
                    System.out.println("4、只有tjTeam可以完成任务，tj+1不能完成任务");
                    System.out.println(tj);
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
                }
            }else {//此团队不能完成任务，则continue
                countNo++;
                continue;
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
