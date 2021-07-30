package test.taskAssignment;

import bean.Worker;
import util.TaskAssignUtil;

import java.util.ArrayList;

public class SimpleTest {
    public static void main(String[] args) {

        TaskAssignUtil tUtil = new TaskAssignUtil();

        ArrayList<Worker> tjTeam = new ArrayList<>();
        Worker w1 = new Worker(1.0);
        Worker w2 = new Worker(2.0);
        Worker w3 = new Worker(3.0);
        tjTeam.add(w1);
        tjTeam.add(w2);
        tjTeam.add(w3);

        ArrayList<Worker> tj2Team = new ArrayList<>();
        Worker w4 = new Worker(4.0);
        //Worker w5 = new Worker(2.0);
        //Worker w6 = new Worker(3.0);
        tj2Team.add(w4);
        //tj2Team.add(w5);
        //tj2Team.add(w6);

        System.out.println(tUtil.isConflict(tjTeam,tj2Team));


    }
}
