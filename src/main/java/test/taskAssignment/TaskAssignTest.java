package test.taskAssignment;

import bean.Task;
import bean.Worker;
import util.TaskAssignUtil;
import util.TasksP;
import util.WorkersP;
import util.GreedyUtil;

import java.io.IOException;
import java.util.ArrayList;

public class TaskAssignTest {
    public static void main(String[] args) throws IOException {

        TaskAssignUtil tUtil = new TaskAssignUtil();
        GreedyUtil gUtil = new GreedyUtil();
        WorkersP wl = new WorkersP();
        TasksP tl = new TasksP();

        //MS-SC_Framework
        double startTime = 1615910400; //开始时间片
        double duration = 600; //时间片的时间间隔
        //1. 检索第i个时间片内的工人给Wp
        ArrayList<Worker> Wp = wl.getWorkersP(startTime, duration);
        //2. 检索第i个时间片内的任务给Tp
        ArrayList<Task> Tp = tl.getTasksP(startTime, duration);

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
            }
        }

        //TODO 根据输出结果判断哪里有问题
        for (int i = 0; i < Tpc.size(); i++) {

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
                    System.out.println("最后一个任务");
                    System.out.println(tj);
                    for (Worker w : tjTeam) {
                        System.out.println(w);
                        //如果形成的团队可以完成任务，则把工人移除Wp
                        Wp.remove(w);
                    }
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
                            System.out.println("解决冲突后可以完成的任务");
                            System.out.println(tj);
                            for (Worker w : tjTeam) {
                                System.out.println(w);
                                //如果形成的团队可以完成任务，则把工人移除Wp
                                Wp.remove(w);
                            }
                        }else {//再组队，再判断
                            tUtil.formTeamAgain(tj, tjTeam, tjWorkers);
                            boolean tjjjSatisfy = gUtil.isSkillsSatisfy(tj.getSkills(), tjTeam);
                            if (tjjjSatisfy) {
                                System.out.println("解决冲突后不能完成任务，在组队可以完成的任务");
                                System.out.println(tj);
                                for (Worker w : tjTeam) {
                                    System.out.println(w);
                                    //如果形成的团队可以完成任务，则把工人移除Wp
                                    Wp.remove(w);
                                }
                            }
                        }

                    }else { //无冲突，确定第i个任务
                        for (Worker w : tjTeam) {
                            System.out.println("无冲突就可以完成的任务");
                            System.out.println(tj);
                            System.out.println(w);
                            //如果形成的团队可以完成任务，则把工人移除Wp
                            Wp.remove(w);
                        }

                    }
                }else {//只有tjTeam可以完成任务，则确认此团队
                    System.out.println(tj);
                    for (Worker w : tjTeam) {
                        System.out.println("只有tjTeam可以完成任务，tj+1不能完成任务");
                        System.out.println(tj);
                        System.out.println(w);
                        //如果形成的团队可以完成任务，则把工人移除Wp
                        Wp.remove(w);
                    }
                }
            }else {//此团队不能完成任务，则continue
                continue;
            }



        }




    }




}
