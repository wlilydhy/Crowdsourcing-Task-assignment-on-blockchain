package main2;

import bean.Task;
import bean.Worker;
import util.greedy.GreedyUtil;

import java.util.ArrayList;
import java.util.Iterator;

public class Greedy {

    //TODO: 此方法还有大问题，在GreeeyTest2中测试
    public ArrayList<Worker> greedy(ArrayList<Worker> workers, Task task) {
        GreedyUtil gUtil = new GreedyUtil();
        ArrayList<Worker> tjTeam = new ArrayList<>();
        while ( !gUtil.isSkillsSatisfy(task.getSkills(), tjTeam) ) {
            ArrayList<Worker> teamBreak = new ArrayList<>();
            Iterator<Worker> iteratorWorkers = workers.iterator();
            while ( iteratorWorkers.hasNext() ) {
                teamBreak.add(iteratorWorkers.next());
            }
            if ( !gUtil.isSkillsSatisfy(task.getSkills(), teamBreak) ) {
                break;
            }
            Worker workerNew = new Worker();
            gUtil.argMax(workers,task,tjTeam);
            if ( workerNew!=null ) {
                tjTeam.add(workerNew);
            }
        }
        for (Worker w : tjTeam) {
            System.out.println(w);
        }
        System.out.println("=====================");
        return tjTeam;
    }
}
