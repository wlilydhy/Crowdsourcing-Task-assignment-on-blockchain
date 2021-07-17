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
    public void greedy(ArrayList<Worker> Wp,ArrayList<Task> Tp) {

        GreedyUtil gUtil = new GreedyUtil();

        //1. 遍历每个任务为其形成团队
        int i = 1; //标记是第几个团队
        int countYes = 0;
        int countNo = 0;
        for (Task tj : Tp) {
            //1.1 为每个任务tj找到有效工人tjWorkers
            //System.out.println("第"+i+"个任务tj为："+tj);
            i++;
            ArrayList<Worker> tjWorkers = new ArrayList<>();
            tjWorkers = gUtil.getTjWorkers(tj,Wp);
            //1.2 为每个任务tj形成团队
            ArrayList<Worker> tjTeam = new ArrayList<>();
            tjTeam = gUtil.formTeam(tjWorkers, tj);

            //此出判断团队是否可以完成任务
            boolean satisfy = gUtil.isSkillsSatisfy(tj.getSkills(), tjTeam);
            if (satisfy) {
                countYes++;
                //System.out.println("YesYesYes");
                //System.out.println("当前任务团队的工人为：");
                for (Worker w : tjTeam) {
                    //System.out.println(w);
                    //如果形成的团队可以完成任务，则把工人移除Wp
                    Wp.remove(w);
                }
                //System.out.println();
            }else {
                countNo++;
                //System.out.println("No");
            }
        }
        System.out.println("形成团队的任务个数为："+countYes);
        System.out.println("不能形成团队的任务个数为："+countNo);
    }

}
