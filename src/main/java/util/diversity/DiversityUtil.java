package util.diversity;

import bean.Task;
import bean.Worker;
import util.greedy.GreedyUtil;

import java.util.ArrayList;

public class DiversityUtil {

    /**
     * diversity的形成团队方法，由于greedy的形成团队方法得到的团队熵为0，要优化
     * 先保留greedy形成团队的第一个成员，然后继续在有效工人tjWorkers中寻找合适的工人
     * Attention！此团队不一定可以满足任务的技能要求，有效工人tjWorkers全部遍历完就结束组队
     * @param tjWorkers 任务tj的有效工人
     * @param tj 当前任务
     * @param tjTeam1 greedy中形成团队的第一个成员
     * @return tj的团队，熵不为0
     */
    public ArrayList<Worker> formTeam(ArrayList<Worker> tjWorkers, Task tj, Worker tjTeam1) {

        GreedyUtil gUtil = new GreedyUtil();
        //形成新的tjWorkers，因为要去除第团队第一个工人tjTeam1
        ArrayList<Worker> tjWorkersNew = new ArrayList<>();
        for (Worker tjW : tjWorkers) {
            tjWorkersNew.add(tjW);
        }
        tjWorkersNew.remove(tjTeam1);

        ArrayList<Worker> tjTeam = new ArrayList<>();
        tjTeam.add(tjTeam1);
        //先形成团队，能招几个人算几个人，当工人们tjWorkers全部遍历完，或者工人能够完成任务时break
        // 最后在外部还会判断此团队是否可以满足任务要求
        for (Worker tjW : tjWorkersNew) {
            if (gUtil.isSkillsSatisfy(tj.getSkills(),tjTeam)) {
                break;
            }
            //此处是不同点：如果工人的cluster与团队第一个工人的cluster相同则不考虑
            if (tjW.getCluster() == tjTeam1.getCluster()) {
                continue;
            }
            Worker maxWorker = new Worker();
            maxWorker = gUtil.argMax(tjWorkers,tj,tjTeam);
            //此处是不同点：如果工人maxWorker的cluster与团队第一个工人的cluster相同则不考虑
            if (maxWorker.getCluster() == tjTeam1.getCluster()) {
                continue;
            }
            if (maxWorker.getId() != 0.0) {
                tjTeam.add(maxWorker);
            }
        }
        return tjTeam;
    }
}
