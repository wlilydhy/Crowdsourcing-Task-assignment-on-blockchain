package util;

import bean.Task;
import bean.Worker;

import java.util.ArrayList;

public class TaskAssignUtil {

    //TODO 没有测试
    public void formTeamAgain(Task tj,ArrayList<Worker> tjTeam,ArrayList<Worker> tjWorkers) {
        GreedyUtil gUtil = new GreedyUtil();
        //先形成团队，能招几个人算几个人，当工人们tjWorkers全部遍历完，或者工人能够完成任务时break
        // 最后在外部判断此团队是否可以满足任务要求
        for (Worker tjW : tjWorkers) {
            if (gUtil.isSkillsSatisfy(tj.getSkills(),tjTeam)) {
                break;
            }
            Worker maxWorker = new Worker();
            maxWorker = gUtil.argMax(tjWorkers,tj,tjTeam);
            if (maxWorker.getId() != 0.0) {
                tjTeam.add(maxWorker);
            }
        }
    }

    //TODO 没有测试
    /**
     * 调节冲突
     * @param tj
     * @param tjTeam
     * @param tj2
     * @param tj2Team
     */
    public void reconcile(Task tj,ArrayList<Worker> tjTeam,ArrayList<Worker> tjWorkers,Task tj2,ArrayList<Worker> tj2Team,ArrayList<Worker> tj2Workers) {
        GreedyUtil gUtil = new GreedyUtil();
        //先找到有冲突的worker
        ArrayList<Worker> conflictWs = new ArrayList<>();
        for (Worker w1 : tjTeam) {
            for (Worker w2 : tj2Team) {
                if (w1.equals(w2)) {
                    conflictWs.add(w1);
                }
            }
        }
        for (Worker cw : conflictWs) {
            tjTeam.remove(cw);
            tj2Team.remove(cw);

            int tjMI1 = gUtil.maxItem1(tjTeam,tj.getSkills());
            int tjMI2 = gUtil.maxItem2(tjTeam,cw,tj.getSkills());
            double tjValue = (tjMI2 - tjMI1) / gUtil.distance(tj,cw);

            int tj2MI1 = gUtil.maxItem1(tj2Team,tj2.getSkills());
            int tj2MI2 = gUtil.maxItem2(tj2Team,cw,tj2.getSkills());
            double tj2Value = (tj2MI2 - tj2MI1) / gUtil.distance(tj2,cw);

            if (tjValue >= tj2Value) { //tj的性价比高
                tjTeam.add(cw);
                tj2Workers.remove(cw);
            }else {
                tj2Team.add(cw);
                tjWorkers.remove(cw);
            }
        }
    }



    /**
     * 判断两个团队人员是否有冲突
     * @param tjTeam
     * @param tj2Team
     * @return true则有冲突，否则无冲突
     */
    public boolean isConflict(ArrayList<Worker> tjTeam, ArrayList<Worker> tj2Team) {
        for (Worker w1 : tjTeam) {
            for (Worker w2 : tj2Team) {
                if (w1.equals(w2)) {
                    return true;
                }
            }
        }
        return false;
    }


}
